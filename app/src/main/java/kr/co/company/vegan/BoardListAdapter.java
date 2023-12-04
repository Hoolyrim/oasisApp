package kr.co.company.vegan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private Context context;
    private ArrayList<Recipe> recipeList;

    public RecipeListAdapter(Context context) {
        this.context = context;
        this.recipeList = new ArrayList<>();
        loadAllRecipesFromDatabase();
    }

    private void loadAllRecipesFromDatabase() {
        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipe");

        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 데이터베이스에서 가져온 데이터를 처리하는 코드
                recipeList.clear(); // 기존 데이터를 지우고 새로 불러온 데이터로 갱신

                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        // 레시피 객체에 ID를 설정
                        recipe.setId(recipeSnapshot.getKey());
                        recipeList.add(recipe);
                    }
                }

                notifyDataSetChanged(); // 어댑터에 데이터가 변경되었음을 알림
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 가져오기 실패 시 처리
                // 여기에 적절한 실패 처리를 추가할 수 있습니다.
            }
        });
    }

    public void addRecipe(Recipe recipe) {
        // 새로운 레시피를 추가하고 RecyclerView 갱신
        recipeList.add(recipe);
        notifyDataSetChanged();
    }

    public void clearRecipes() {
        recipeList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);

        // 글 번호 표시
        if (holder.itemNumber != null) {
            holder.itemNumber.setText(String.valueOf(position + 1));
        }

        holder.recipeTitle.setText(recipe.getTitle());

        // 레시피 제목 클릭 이벤트 처리
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레시피 상세 화면으로 이동하는 인텐트
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recipeId", recipe.getId()); // 레시피 ID 전달
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder의 내부 클래스로서 레시피 아이템의 각 요소들을 정의
        TextView itemNumber;
        TextView recipeTitle;

        public RecipeViewHolder(final View itemView) {
            super(itemView);
            // itemNumber 초기화
            itemNumber = itemView.findViewById(R.id.itemNumber);
            recipeTitle = itemView.findViewById(R.id.recipeTitle);

            // 레시피 작성 버튼 설정
            Button regBtn = itemView.findViewById(R.id.reg_button);
            if (regBtn != null) {
                regBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 레시피 작성 버튼이 클릭되었을 때 수행할 동작 정의
                        // 새로운 화면으로 이동하는 인텐트를 사용하여 레시피 작성 화면으로 이동
                        Intent intent = new Intent(itemView.getContext(), RecipeRegisterActivity.class);
                        itemView.getContext().startActivity(intent);
                    }
                });
            }
        }
    }
}


