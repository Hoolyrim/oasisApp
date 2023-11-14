package kr.co.company.vegan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TextRecognitionActivity extends AppCompatActivity {
    static final int REQUEST_CODE = 2;
    ImageView imageView;
    Uri uri;
    Bitmap bitmap;
    InputImage image;
    TextView text_info;
    Button btn_get_image, btn_result, btn_YN, btn_allergy, btn_recom;
    TextRecognizer recognizer;
    DatabaseReference databaseReference;
    DatabaseReference ingredientNamesReference;
    DatabaseReference allergyReference; // 알러지 정보를 가져오기 위한 레퍼런스


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);

        // Firebase 데이터베이스 레퍼런스 설정
        databaseReference = FirebaseDatabase.getInstance().getReference("material");
        ingredientNamesReference = FirebaseDatabase.getInstance().getReference("ingredientNames");
        allergyReference = FirebaseDatabase.getInstance().getReference("allergy");

        // "text_info" TextView와 "btn_YN" Button과 "btn_allergy"에 대한 정의를 레이아웃과 연결
        text_info = findViewById(R.id.text_info);
        btn_YN = findViewById(R.id.btn_YN);
        btn_allergy = findViewById(R.id.btn_allergy);

        // 버튼 이동
        Button button5 = findViewById(R.id.cameraButton);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
            }
        });

        // 감춰진 부분 버튼 클릭시 나타나기
        Button btn_result = findViewById(R.id.btn_result);
        final Button cameraButton = findViewById(R.id.cameraButton);
        final Button btn_camera = findViewById(R.id.cameraButton);
        final Button btn_get_image = findViewById(R.id.btn_get_image);
//        final Button text_info = findViewById(R.id.text_info);
        final Button btn_YN = findViewById(R.id.btn_YN);
        final Button btn_allergy = findViewById(R.id.btn_allergy);
        final Button btn_recom = findViewById(R.id.btn_recom);
        final LinearLayout ingredientsLayout = findViewById(R.id.ingredientsLayout);
        final TextView MList = findViewById(R.id.MList);
        final Handler handler = new Handler();

        // TextRecognizer 객체 초기화
        recognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 이미지 판독을 수행
                TextRecognition(recognizer);

                // 버튼을 숨기기 위한 딜레이를 설정합니다. 여기서는 3000 밀리초(3초)로 설정합니다.
                int delayMillis = 3000;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 버튼을 숨깁니다.
                        cameraButton.setVisibility(View.GONE);
                        btn_camera.setVisibility(View.GONE);
                        btn_get_image.setVisibility(View.GONE);
                        btn_result.setVisibility(View.GONE);
                        text_info.setVisibility(View.GONE);

                        // 다른 요소를 나타냅니다.
                        MList.setVisibility(View.VISIBLE);
                        btn_YN.setVisibility(View.VISIBLE);
                        btn_allergy.setVisibility(View.VISIBLE);
                        btn_recom.setVisibility(View.VISIBLE);
                        ingredientsLayout.setVisibility(View.VISIBLE);
                    }
                }, delayMillis);
            }
        });

        imageView = findViewById(R.id.imageView);

        // GET IMAGE 버튼
//        Button btn_get_image = findViewById(R.id.btn_get_image);
        btn_get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // btn_recom 버튼 클릭 이벤트 핸들러
        Button button = findViewById(R.id.btn_recom);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 딜레이를 설정합니다. 여기서는 3초(3000 밀리초)로 설정합니다.
                int delayMillis = 3000;

                // 딜레이 후에 RecommendActivity를 시작합니다.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), RecommendActivity.class);
                        startActivity(intent);
                    }
                }, delayMillis);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 갤러리에서 선택한 사진에 대한 uri를 가져옵니다.
            uri = data.getData();
            setImage(uri);
        }
    }

    // uri를 비트맵으로 변환시켜서 이미지뷰에 띄워주고 InputImage를 생성하는 메서드
    private void setImage(Uri uri) {
        try {
            InputStream in = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);

            image = InputImage.fromBitmap(bitmap, 0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private boolean isVegan = true; // isVegan 변수를 전역 변수로 변경
    private void TextRecognition(TextRecognizer recognizer) {
        final boolean[] isVegan = {true}; // isVegan 변수를 배열로 변경

        Task<Text> result = recognizer.process(image)
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        String resultText = visionText.getText();
                        text_info.setText(resultText);
                        // text_info TextView의 가시성을 GONE으로 설정하여 숨깁니다.
                        text_info.setVisibility(View.GONE);

                        Log.d("TextRecognition", "판독 결과: " + resultText);

                        // 정규 표현식을 사용하여 특수문자, 괄호 및 숫자를 제외한 부분을 가져옵니다.
                        String cleanedText = resultText.replaceAll("[\\p{Punct}\\d]", " ").trim();

                        // '원재료명' 또는 '원재료명 및 함량'을 찾아서 그 뒤의 부분을 가져옵니다.
                        String afterIngredients = null;

                        if (cleanedText.contains("원재료명 및 함량")) {
                            afterIngredients = cleanedText.split("원재료명 및 함량")[1].trim();
                        } else if (cleanedText.contains("원재료명")) {
                            afterIngredients = cleanedText.split("원재료명")[1].trim();
                        }

                        if (afterIngredients != null) {
                            String[] recognizedIngredients = afterIngredients.split("[,\\s]+"); // 쉼표 또는 공백으로 분할

                            final List<String> nonVeganIngredients = new ArrayList<>();
                            final List<String> matchingAllergies = new ArrayList<>(); // 일치하는 알러지 정보 저장

                            // Firebase Realtime Database에 접근하여 재료명을 수정하고 비건 또는 논비건 판단
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (int i = 0; i < recognizedIngredients.length; i++) {
                                        String recognizedIngredient = recognizedIngredients[i];
                                        // Firebase Realtime Database 경로에서 허용되지 않는 문자를 제거
                                        String safePath = recognizedIngredient.replaceAll("[.#$\\[\\]]", "");

                                        // recognizedIngredients를 수정한 경우 수정된 재료명으로 비교
                                        DataSnapshot ingredientSnapshot = dataSnapshot.child("ingredientNames").child(safePath.toLowerCase().trim());

                                        if (ingredientSnapshot.exists()) {
                                            // Firebase에서 해당 재료의 비건 여부 가져오기
                                            boolean isIngredientVegan = ingredientSnapshot.getValue(Boolean.class);

                                            if (!isIngredientVegan) {
                                                // Update the outer 'isVegan' variable
                                                isVegan[0] = false;
                                                nonVeganIngredients.add(recognizedIngredient);
                                            }
                                        } else {
                                            // 재료가 데이터베이스에 없는 경우에 대한 처리
                                            nonVeganIngredients.add(recognizedIngredient);
                                        }
                                    }

                                    // Firebase Cloud Firestore에 알러지 정보를 가져오고 버튼 텍스트에 추가
                                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                    CollectionReference allergyCollection = firestore.collection("allergy");

                                    for (String recognizedIngredient : recognizedIngredients) {
                                        allergyCollection.whereEqualTo("ingredient", recognizedIngredient)
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                                            String allergyIngredient = document.getString("ingredient");
                                                            matchingAllergies.add(allergyIngredient);
                                                        }

                                                        // 일치하는 알레르기 정보를 버튼 텍스트에 추가
                                                        String resultMessage = "";
                                                        if (isVegan[0]) {
                                                            resultMessage = "이 제품은 '비건'입니다.";
                                                        } else {
                                                            resultMessage = "이 제품은 '논비건'입니다.";
                                                        }

                                                        if (!matchingAllergies.isEmpty()) {
                                                            resultMessage += " | " + TextUtils.join(", ", matchingAllergies) + " 함유";
                                                        }

                                                        btn_YN.setText(resultMessage);

                                                        LinearLayout ingredientsLayout = findViewById(R.id.ingredientsLayout);

                                                        // Recognized non-vegan ingredients 출력
                                                        if (ingredientsLayout.getChildCount() == 0) {
                                                            for (String nonVeganIngredient : nonVeganIngredients) {
                                                                TextView ingredientTextView = new TextView(TextRecognitionActivity.this);
                                                                ingredientTextView.setText(nonVeganIngredient);

                                                                ingredientsLayout.addView(ingredientTextView);
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e("Firebase 오류", databaseError.getMessage());
                                }
                            });
                        } else {
                            // 판독 불가의 경우
                            btn_YN.setText("판독하기 어렵습니다. \n다른 이미지로 인식해주세요.");
                            btn_allergy.setText(" ");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TextRecognition", "판독 실패: " + e.getMessage());
                    }
                });
    }
}