package kr.co.company.vegan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = findViewById(R.id.btn_camera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TextRecognitionActivity.class);
                startActivity(intent);
            }
        });

        //버튼 이동
//        Button button2 = findViewById(R.id.btn_log);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });
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
        developer_info_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TextRecognitionActivity.class);
                startActivity(intent);
            }
        });

        //레시피 버튼 클릭시 액티비티 전환
        developer_info_btn = (ImageButton) findViewById(R.id.btn_recipe);
        developer_info_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), receipe.class);
                startActivity(intent);
            }
        });

        //나의 그린 기록 남기기 버튼 클릭시 액티비티 전환
        developer_info_btn = (ImageButton) findViewById(R.id.btn_green);
        developer_info_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardRegisterActivity.class);
                startActivity(intent);
            }
        });

        //제품명 검색 버튼 클릭시 액티비티 전환
        developer_info_btn = (ImageButton) findViewById(R.id.btn_search);
        developer_info_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), search.class);
                startActivity(intent);
            }
        });

        //커뮤니티 버튼 클릭시 액티비티 전환
        developer_info_btn = (ImageButton) findViewById(R.id.btn_community);
        developer_info_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        //챌린지 버튼 클릭시 액티비티 전환
        developer_info_btn = (ImageButton) findViewById(R.id.btn_challenge);
        developer_info_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), challenge.class);
                startActivity(intent);
            }
        });

//        ImageButton button2 = findViewById(R.id.btn_my);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
//                startActivity(intent);
//            }
//        });

//        //banner
//        viewPager = findViewById(R.id.viewPager);
////        recyclerView = findViewById(R.id.recyclerView);
//
//        // 슬라이드 아이템 이미지 추가 (예시 이미지 리소스 ID)
//        images.add(R.drawable.banner1);
//        images.add(R.drawable.banner2);
//        images.add(R.drawable.banner3);
//
//        slideAdapter = new InfiniteSlideAdapter(this, images, viewPager);
//        recyclerView.setAdapter(slideAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        viewPager.setAdapter(slideAdapter);
//
//        slideAdapter.startAutoSlider(); // 자동 슬라이딩 시작
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        slideAdapter.stopAutoSlider(); // 액티비티가 종료될 때 자동 슬라이딩 중지
//    }

    }
}
