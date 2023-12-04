package kr.co.company.vegan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView dateTextView;
    private ImageView contentImageView;
    private TextView contentTextView;
    private Button rewriteButton;
    private Button deleteButton;

    private FirebaseAuth auth;
    private DatabaseReference recipeReference;
    private String recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        titleTextView = findViewById(R.id.title_tv);
        dateTextView = findViewById(R.id.date_tv);
        contentImageView = findViewById(R.id.content_image);
        contentTextView = findViewById(R.id.content_tv);
        rewriteButton = findViewById(R.id.rewrite_btn);
        deleteButton = findViewById(R.id.delete_btn);

        auth = FirebaseAuth.getInstance();
        recipeReference = FirebaseDatabase.getInstance().getReference("recipe");
        recipeId = getIntent().getStringExtra("recipeId");

        if (recipeId != null) {
            // Firebase Realtime Database에서 레시피 정보 가져오기
            loadRecipeData();
        } else {
            Toast.makeText(this, "레시피를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        rewriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 수정 버튼 클릭 시 RecipeRegisterActivity로 이동하고 recipeId 전달
                Intent intent = new Intent(RecipeDetailActivity.this, RecipeRegisterActivity.class);
                intent.putExtra("recipeId", recipeId);
                startActivity(intent);
            }
        });

        // 삭제 버튼 클릭 시 레시피 삭제
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 삭제 확인 다이얼로그 또는 바로 삭제 여부를 묻는 로직을 구현할 수 있습니다.
                // 여기에서는 단순히 삭제 함수를 호출하는 예제를 보여줍니다.
                deleteRecipe();
            }
        });

        // 기타 초기화 코드 및 기능 추가
    }

    // 레시피 데이터 로드
    // 레시피 데이터 로드
    private void loadRecipeData() {
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference userRecipeReference = recipeReference.child(userId).child(recipeId);

        userRecipeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        // 레시피 정보 표시
                        titleTextView.setText(recipe.getTitle());
                        dateTextView.setText(recipe.getFormattedDate());
                        contentTextView.setText(recipe.getText());

                        // 이미지가 있는 경우 표시
                        if (recipe.getImageUrl() != null && !recipe.getImageUrl().isEmpty()) {
                            Picasso.get().load(recipe.getImageUrl()).into(contentImageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // 이미지 로드 성공 시 처리
                                    contentImageView.setVisibility(View.VISIBLE);

                                    // UI 업데이트를 강제로 호출
                                    contentImageView.requestLayout();
                                    contentImageView.invalidate();
                                }

                                @Override
                                public void onError(Exception e) {
                                    // 이미지 로드 실패 시 처리
                                    contentImageView.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            contentImageView.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 가져오기 실패 시 처리
                Toast.makeText(RecipeDetailActivity.this, "레시피 데이터를 불러오지 못했습니다. 오류: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 레시피 삭제 메서드
    private void deleteRecipe() {
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference userRecipeReference = recipeReference.child(userId).child(recipeId);

        userRecipeReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    // 삭제 성공 메시지 표시
                    Toast.makeText(RecipeDetailActivity.this, "레시피가 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    finish(); // 현재 액티비티 종료
                } else {
                    // 삭제 실패 메시지 표시
                    Toast.makeText(RecipeDetailActivity.this, "레시피 삭제 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 기타 메서드 및 이벤트 처리 추가
}

