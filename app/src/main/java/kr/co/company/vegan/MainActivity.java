package kr.co.company.vegan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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

//        //버튼 이동
//        Button button2 = findViewById(R.id.btn_log);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
//                startActivity(intent);
//            }
//        });

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
                Intent intent = new Intent(getApplicationContext(), RecipeRegisterActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
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

    }
}
