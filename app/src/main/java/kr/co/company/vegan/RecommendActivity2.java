package kr.co.company.vegan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecommendActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private List<String> recognizedIngredients;
    private List<Product> recommendedProducts;
    private RecyclerView recyclerView;
    private RecommendAdapter recommendAdapter;
    private TextView recommendationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        firestore = FirebaseFirestore.getInstance();
        recognizedIngredients = new ArrayList<>();
        recommendedProducts = new ArrayList<>();
//        recyclerView = findViewById(R.id.recyclerView);
//        recommendationTextView = findViewById(R.id.textView37);

        // Recognized ingredients 설정 (임의의 데이터 사용)
        setRecognizedIngredients();

        // RecyclerView 초기화 및 설정
        initRecyclerView();

        // 추천 알고리즘 실행
        recommendProducts();
    }

    // Recognized ingredients 설정 (임의의 데이터 사용)
    private void setRecognizedIngredients() {
        recognizedIngredients.add("감자");
        recognizedIngredients.add("당근");
        recognizedIngredients.add("양파");
        // ... 인식된 재료를 여기에 추가
    }

    // RecyclerView 초기화 및 설정
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recommendAdapter = new RecommendAdapter(recommendedProducts);
        recyclerView.setAdapter(recommendAdapter);
    }

    // 제품 추천 알고리즘
    private void recommendProducts() {
        CollectionReference productsCollection = firestore.collection("products");
        productsCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Product product = document.toObject(Product.class);

                    // 각 제품과 인식된 재료 간의 유사도를 계산
                    double similarity = calculateSimilarity(product.getProductName(), recognizedIngredients);

                    // 유사도가 0.4 이상인 제품만을 추천
                    if (similarity >= 0.4) {
                        recommendedProducts.add(product);
                    }
                }

                // 추천된 제품 목록을 출력
                showRecommendations();
            }
        });
    }

    // 문자열 간의 유사도를 계산
    private double calculateSimilarity(String str1, List<String> strList2) {
        double maxSimilarity = 0;

        for (String str2 : strList2) {
            double similarity = SimilarityUtil.calculateJaccardSimilarity(str1, str2);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
            }
        }

        return maxSimilarity;
    }

    // 추천된 제품 목록을 출력
    private void showRecommendations() {
        StringBuilder recommendationText = new StringBuilder("추천된 제품:\n");
        for (Product recommendedProduct : recommendedProducts) {
            recommendationText.append(recommendedProduct.getProductName()).append("\n");
        }

        // TextView에 출력
        recommendationTextView.setText(recommendationText.toString());

        // RecyclerView 갱신
        recommendAdapter.notifyDataSetChanged();
    }
}
