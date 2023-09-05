package kr.co.company.vegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountedCompleter;

public class MainActivity extends AppCompatActivity {

//    private ActivityMainBinding mBinding;
//    private Handler sliderHandler = new Handler();
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = findViewById(R.id.btn_camera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),smartLens.class);
                startActivity(intent);
            }
        });
//
//        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(mBinding.getRoot());
//        List<String> sliderItems = new ArrayList<>();
//        sliderItems.add("https://cdn.pixabay.com/photo/2019/05/27/08/19/vegan-4232116_1280.jpg");
//        sliderItems.add("https://cdn.pixabay.com/photo/2016/03/28/00/47/appetite-1284778_1280.jpg");
//        sliderItems.add("https://cdn.pixabay.com/photo/2020/08/04/09/42/fruit-5462303_1280.jpg");
//
//        mBinding.vpImageSlider.setAdapter(new SliderAdapter(this, mBinding.vpImageSlider, sliderItems));
//
//        mBinding.vpImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                sliderHandler.removeCallbacks(sliderRunnable);
//                sliderHandler.postDelayed(sliderRunnable, 2000);
//            }
//        });
//
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            mBinding = null;
//        }
//
//        private Runnable sliderRunnable = new Runnable() {
//            @Override
//            public void run() {
//                mBinding.vpImageSlider.setCurrentItem(mBinding.vpImageSlider.getCurrentItem() + 1);
//            }
//        };
//
//        @Override
//        protected void onPause() {
//            super.onPause();
//            sliderHandler.removeCallbacks(sliderRunnable);
//        }
//
//        @Override
//        protected void onResume() {
//            super.onResume();
//            sliderHandler.postDelayed(sliderRunnable, 2000);
//        }

        //스마트 렌즈 버튼 클릭시 액티비티 전환
        ImageButton developer_info_btn = (ImageButton) findViewById(R.id.btn_camera);
        developer_info_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), smartLens.class);
                startActivity(intent);
            }
        });

        //레시피 버튼 클릭시 액티비티 전환
        developer_info_btn = (ImageButton) findViewById(R.id.btn_recipe);
        developer_info_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), receipe.class);
                startActivity(intent);
            }
        });

        //나의 그린 기록 남기기 버튼 클릭시 액티비티 전환
        developer_info_btn = (ImageButton) findViewById(R.id.btn_green);
        developer_info_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        //제품명 검색 버튼 클릭시 액티비티 전환
        developer_info_btn = (ImageButton) findViewById(R.id.btn_search);
        developer_info_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), search.class);
                startActivity(intent);
            }
        });

        //커뮤니티 버튼 클릭시 액티비티 전환
        developer_info_btn = (ImageButton) findViewById(R.id.btn_community);
        developer_info_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        //챌린지 버튼 클릭시 액티비티 전환
        developer_info_btn = (ImageButton) findViewById(R.id.btn_challenge);
        developer_info_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), challenge.class);
                startActivity(intent);
            }
        });

    }
}