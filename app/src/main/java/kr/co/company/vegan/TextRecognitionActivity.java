package kr.co.company.vegan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class TextRecognitionActivity extends AppCompatActivity {
    static final int REQUEST_CODE = 2;

    ImageView imageView;    // 갤러리에서 가져온 이미지를 보여줄 뷰
    Uri uri;                // 갤러리에서 가져온 이미지에 대한 Uri
    Bitmap bitmap;          // 갤러리에서 가져온 이미지를 담을 비트맵
    InputImage image;       // ML 모델이 인식할 인풋 이미지
    TextView text_info;     // ML 모델이 인식한 텍스트를 보여줄 뷰
    Button btn_get_image, btn_detection_image, btn_result, btn_YN, btn_allergy,btn_recom;  // 이미지 가져오기 버튼, 이미지 인식 버튼
    TextRecognizer recognizer;    //텍스트 인식에 사용될 모델

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);

        Button button3 = findViewById(R.id.btn_YN);
        String buttonText = button3.getText().toString();

        Button button4 = findViewById(R.id.btn_allergy);
        String buttonText2 = button4.getText().toString();

        // 텍스트 중에서 특정 부분의 시작과 끝 인덱스를 찾습니다.
        int startIndex = buttonText.indexOf("비건");
        int endIndex = startIndex + "비건".length();

        int startIndex2 = buttonText2.indexOf("대두");
        int endIndex2 = startIndex2 + "대두".length();

        // SpannableStringBuilder를 사용하여 스타일을 적용합니다.
        SpannableStringBuilder builder = new SpannableStringBuilder(buttonText);
        SpannableStringBuilder builder2 = new SpannableStringBuilder(buttonText2);

        // 시작부터 끝까지의 글자에 대한 스팬을 설정하여 색상을 변경합니다.
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#006400")), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder2.setSpan(new ForegroundColorSpan(Color.RED), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 시작부터 끝까지의 글자에 대한 스팬을 설정하여 글자를 굵게 표시합니다.
        builder.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder2.setSpan(new StyleSpan(Typeface.BOLD), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 시작부터 끝까지의 글자에 대한 크기 조절 스팬을 설정하여 글자 크기를 크게 만듭니다.
        builder.setSpan(new RelativeSizeSpan(1.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder2.setSpan(new RelativeSizeSpan(1.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 스팬을 적용한 텍스트를 버튼에 설정합니다.
        button3.setText(builder);
        button4.setText(builder2);

//        //버튼 이동
//        Button button = findViewById(R.id.btn_result);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
//                startActivity(intent);
//            }
//        });

        //감춰진 부분 버튼 클릭시 나타나기
        Button btn_result = findViewById(R.id.btn_result);
        final Button btn_YN = findViewById(R.id.btn_YN);
        final Button btn_allergy = findViewById(R.id.btn_allergy);
//        final TextView list = findViewById(R.id.list);
//        final ListView list_item = findViewById(R.id.list_item);
        final Button MList = findViewById(R.id.MList);
        final Button btn_recom = findViewById(R.id.btn_recom);
        final TextView m1 = findViewById(R.id.m1);
        final TextView m2 = findViewById(R.id.m2);
        final TextView m3 = findViewById(R.id.m3);
        final Handler handler = new Handler();


        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 버튼을 숨기기 위한 딜레이를 설정합니다. 여기서는 3000 밀리초(3초)로 설정합니다.
                int delayMillis = 3000;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 버튼을 숨깁니다.
                        btn_get_image.setVisibility(View.GONE);
                        btn_result.setVisibility(View.GONE);


                        // 다른 요소를 나타냅니다.
                        btn_YN.setVisibility(View.VISIBLE);
                        btn_allergy.setVisibility(View.VISIBLE);
//                        list.setVisibility(View.VISIBLE);
//                        list_item.setVisibility(View.VISIBLE);
                        btn_recom.setVisibility(View.VISIBLE);
                        MList.setVisibility(View.VISIBLE);
                    }
                }, delayMillis);
            }

//                btn_get_image.setVisibility(View.GONE);
//                btn_result.setVisibility(View.GONE);
//
//                btn_YN.setVisibility(View.VISIBLE);
//                btn_allergy.setVisibility(View.VISIBLE);
//                list.setVisibility(View.VISIBLE);
//                list_item.setVisibility(View.VISIBLE);
//                btn_recom.setVisibility(View.VISIBLE);
//            }
        });

        imageView = findViewById(R.id.imageView);
//        text_info = findViewById(R.id.text_info);
//        recognizer = TextRecognition.getClient();    //텍스트 인식에 사용될 모델

        // When using Korean script library
        TextRecognizer recognizer =
                TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        // GET IMAGE 버튼
        btn_get_image = findViewById(R.id.btn_get_image);
        btn_get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }

        });
        // 성분보기 버튼 클릭 시 나타나고, 사라짐
        Button button2 = findViewById(R.id.MList);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭 시 textView를 숨기거나 다시 표시
                if (m1.getVisibility() == View.VISIBLE) {
                    // textView가 보이는 경우, 숨김
                    m1.setVisibility(View.INVISIBLE);
                    m2.setVisibility(View.INVISIBLE);
                    m3.setVisibility(View.INVISIBLE);
                } else {
                    // textView가 숨겨진 경우, 다시 표시
                    m1.setVisibility(View.VISIBLE);
                    m2.setVisibility(View.VISIBLE);
                    m3.setVisibility(View.VISIBLE);
                }
            }
        });

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


        // IMAGE DETECTION 버튼
//        btn_detection_image = findViewById(R.id.btn_detection_image);
//        btn_detection_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextRecognition(recognizer);
//            }
//        });

    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_CODE) {
            // 갤러리에서 선택한 사진에 대한 uri를 가져온다.
            uri = data.getData();

            setImage(uri);
        }
    }

    // uri를 비트맵으로 변환시킨후 이미지뷰에 띄워주고 InputImage를 생성하는 메서드
    private void setImage(Uri uri) {
        try{
            InputStream in = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);

            image = InputImage.fromBitmap(bitmap, 0);
            Log.e("setImage", "이미지 to 비트맵");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void TextRecognition(TextRecognizer recognizer){
        Task<Text> result = recognizer.process(image)
                // 이미지 인식에 성공하면 실행되는 리스너
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        Log.e("텍스트 인식", "성공");
                        // Task completed successfully
                        String resultText = visionText.getText();
                        text_info.setText(resultText);  // 인식한 텍스트를 TextView에 세팅
                    }
                })
                // 이미지 인식에 실패하면 실행되는 리스너
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("텍스트 인식", "실패: " + e.getMessage());
                            }
                        });
    }

}