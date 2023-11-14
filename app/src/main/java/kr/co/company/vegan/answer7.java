package kr.co.company.vegan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class answer7 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer7);

        Button returnButton = findViewById(R.id.button1);
        Button mainButton = findViewById(R.id.button);

        // '다시하기' 버튼 클릭 이벤트 처리
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TestActivity로 이동하는 인텐트 생성
                Intent intent = new Intent(answer7.this, TestActivity.class);
                startActivity(intent);
            }
        });

        // '메인 화면으로' 버튼 클릭 이벤트 처리
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity로 이동하는 인텐트 생성
                Intent intent = new Intent(answer7.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
