package kr.co.company.vegan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class RecommendActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_recommend );
    }

//    ImageButton heartbtn = findViewById(R.id.imageButton8);
//
//    public void onClick(View view){
//
//        // 현재 상태를 가져옴
//        boolean isSelected = view.isSelected();
//
//        // 상태를 토글 (반전)
//        isSelected = !isSelected;
//
//        // 새로운 상태를 설정
//        view.setSelected(isSelected);
//
//        // 상태에 따라 다른 동작 수행
//        if (isSelected) {
//            heartbtn.setSelected(true); // 선택
////            isselected = true;
//        } else {
//            heartbtn.setSelected(false); // 선택아님
////            selected = false;
//        }
//    }
}
