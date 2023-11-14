package kr.co.company.vegan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class RecipeRegisterActivity extends Activity {

    private EditText editTextMessage;
    private Button buttonChooseImage;
    private ImageView imageViewSelectedImage;
    private Button buttonSend;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeword);

        editTextMessage = findViewById(R.id.editTextMessage);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        imageViewSelectedImage = findViewById(R.id.imageViewSelectedImage);
        buttonSend = findViewById(R.id.buttonSend);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("recipe");
        storageReference = FirebaseStorage.getInstance().getReference("images");

        // Firebase와의 연결 상태 확인
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    // Firebase와 연결됨
                    Log.d("FirebaseConnect", "Firebase 연결됨");
                } else {
                    // Firebase와 연결이 끊어짐
                    Log.d("FirebaseDisconnect", "Firebase 연결안됨");

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // 연결 상태 확인 중 오류가 발생한 경우
            }
        });

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지 선택 인텐트 시작
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString().trim();
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DatabaseReference userMessageReference = databaseReference.child(userId);

                    String messageId = userMessageReference.push().getKey();
                    Map<String, Object> messageData = new HashMap<>();
                    messageData.put("text", message);
                    messageData.put("timestamp", ServerValue.TIMESTAMP);
                    messageData.put("title", getTitle());

                    userMessageReference.child(messageId).setValue(messageData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // 메시지 업로드가 성공적으로 완료됨
                                Log.d("MessageUpload", "메시지 업로드 성공");
                            } else {
                                // 메시지 업로드 중 오류 발생
                                Log.e("MessageUpload", "메시지 업로드 실패: " + task.getException().getMessage());
                            }
                        }
                    });

                    if (selectedImageUri != null) {
                        StorageReference imageReference = storageReference.child(messageId + ".jpg");
                        UploadTask uploadTask = imageReference.putFile(selectedImageUri);
                        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    // 이미지 업로드 완료
                                    // 이미지 URL을 사용하여 메시지와 연결하거나 저장하세요
                                    String imageUrl = task.getResult().getMetadata().getReference().getDownloadUrl().toString();
                                    userMessageReference.child(messageId).child("image").setValue(imageUrl);
                                } else {
                                    // 이미지 업로드 중 오류 발생
                                    Log.e("ImageUpload", "이미지 업로드 실패: " + task.getException().getMessage());
                                }
                            }
                        });
                    }

                    editTextMessage.setText("");
                    imageViewSelectedImage.setVisibility(View.GONE);
                } else {
//                    토스트 메시지 이용
//                    Toast.makeText(RecipeRegisterActivity.this, "로그인이 필요합니다. 로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show();
//
//                    // 로그인 페이지로 이동하는 인텐트 사용
//                    Intent loginIntent = new Intent(RecipeRegisterActivity.this, LoginActivity.class);
//                    startActivity(loginIntent);

                    // 사용자가 로그인되어 있지 않은 경우
                    // 다이얼로그를 표시하여 사용자에게 로그인을 유도
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecipeRegisterActivity.this);
                    builder.setTitle("로그인이 필요한 서비스입니다.");
                    builder.setMessage("로그인 후 이용해주세요.");
                    builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 로그인 화면으로 이동하는 인텐트를 사용하여 로그인 페이지로 이동
//                            Intent loginIntent = new Intent(RecipeRegisterActivity.this, LoginActivity.class);
//                            startActivity(loginIntent);
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imageViewSelectedImage.setImageURI(selectedImageUri);
            imageViewSelectedImage.setVisibility(View.VISIBLE);
        }
    }
}
