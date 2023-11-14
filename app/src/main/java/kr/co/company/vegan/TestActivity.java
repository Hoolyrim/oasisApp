package kr.co.company.vegan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private int QuestionIndex = 0;
    private TextView questionTextView;
    private Button oButton;
    private Button xButton;
    private List<Integer> AnswerList = new ArrayList<>();

    private String[] questions = {
            "\n 당신은 채소 섭취를 \n하신 적이 있습니까? \n",
            "\n 당신은 우유, 요거트, 치즈 등의 유제품을 섭취 하신 적이 \n 있습니까?\n",
            "\n 당신은 달걀을 섭취 하신 \n 적이 있습니까?\n",
            "\n 당신은 생선, 조개, 새우 등 \n어패류를 섭취하신 적이 \n있습니까?\n",
            "\n 당신은 닭, 오리 등 품종 \n개량을 하여 육성한 조류를 \n 섭취하신 적이 있습니까?",
            "\n 당신은 육류를 \n 섭취하신 적이 있습니까?\n"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question1);

        questionTextView = findViewById(R.id.Question);
        oButton = findViewById(R.id.button1);
        xButton = findViewById(R.id.button2);

        // 초기 문제를 설정
        setQuestion(QuestionIndex);

        // 누적 응답 리스트를 초기화
        AnswerList = new ArrayList<>();

        // O 버튼 클릭 이벤트 처리
        oButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerList.add(1);
                QuestionIndex++;
                if (QuestionIndex < questions.length) {
                    setQuestion(QuestionIndex);
                } else {
                    showResult();
                }
            }
        });

        // X 버튼 클릭 이벤트 처리
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerList.add(0);
                QuestionIndex++;
                if (QuestionIndex < questions.length) {
                    setQuestion(QuestionIndex);
                } else {
                    showResult();
                }
            }
        });
    }

    private void setQuestion(int questionIndex) {
        if (questionIndex < questions.length) {
            questionTextView.setText(questions[questionIndex]);
        }
    }

    private void showResult() {
        // cumulativeResponseList를 기반으로 결과 페이지를 표시
        if(AnswerList.get(5)==1){
            startActivity(new Intent(TestActivity.this, answer7.class));
        }else if (AnswerList.get(4)==1){
            startActivity(new Intent(TestActivity.this, answer6.class));
        } else if (AnswerList.get(3)==1) {
            startActivity(new Intent(TestActivity.this, answer5.class));
        } else if (AnswerList.get(2)==1 && AnswerList.get(1)==1) {
            // 유제품, 달걀 모두 섭취: 락토-오보
            startActivity(new Intent(TestActivity.this, answer4.class));
        } else if (AnswerList.get(2)==1 && AnswerList.get(1)==0) {
            // 달걀은 섭취하지만 유제품은 섭취하지 않음: 오보
            startActivity(new Intent(TestActivity.this, answer3.class));
        }else if (AnswerList.get(2)==0 && AnswerList.get(1)==1) {
            // 유제품은 섭취하지만 달걀은 섭취하지 않음: 락토
            startActivity(new Intent(TestActivity.this, answer2.class));
        }else if (AnswerList.get(0)==1) {
            startActivity(new Intent(TestActivity.this, answer1.class));
        }else
            startActivity(new Intent(TestActivity.this, answer1.class));


//        switch (AnswerList.toString()) {
//            case "[0, 0, 0, 0, 0, 0]":
//                startActivity(new Intent(TestActivity.this, answer1.class));
//                break;
//            case "[1, 0, 0, 0, 0, 0]":
//                startActivity(new Intent(TestActivity.this, answer1.class));
//                break;
//            case "[1, 1, 0, 0, 0, 0]":
//                startActivity(new Intent(TestActivity.this, answer2.class));
//                break;
//            case "[1, 0, 1, 0, 0, 0]":
//                startActivity(new Intent(TestActivity.this, answer3.class));
//                break;
//            case "[1, 1, 1, 0, 0, 0]":
//                startActivity(new Intent(TestActivity.this, answer4.class));
//                break;
//            case "[1, 1, 1, 1, 0, 0]":
//                startActivity(new Intent(TestActivity.this, answer5.class));
//                break;
//            case "[1, 1, 1, 1, 1, 0]":
//                startActivity(new Intent(TestActivity.this, answer6.class));
//                break;
//            case "[1, 1, 1, 1, 1, 1]":
//                startActivity(new Intent(TestActivity.this, answer7.class));
//                break;
//            default:
//                // 다른 경우에 따른 결과 페이지를 추가할 수 있습니다.
//                startActivity(new Intent(TestActivity.this, answer7.class));
//                break;
//        }

    }
}