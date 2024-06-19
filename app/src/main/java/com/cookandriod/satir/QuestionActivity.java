package com.cookandriod.satir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {
    private TextView[] questionViews;
    private RadioButton[] radioButtons;
    private Button nextButton;
    private int currentQuestionSetIndex = 0;
    private String[][] questionSets;
    private int[] psyTypes = new int[5];
    int psyResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        // 텍스트뷰와 라디오버튼 초기화
        questionViews = new TextView[]{
                findViewById(R.id.question1),
                findViewById(R.id.question2),
                findViewById(R.id.question3),
                findViewById(R.id.question4),
                findViewById(R.id.question5)
        };

        radioButtons = new RadioButton[]{
                findViewById(R.id.radio_button1),
                findViewById(R.id.radio_button2),
                findViewById(R.id.radio_button3),
                findViewById(R.id.radio_button4),
                findViewById(R.id.radio_button5)
        };

        nextButton = findViewById(R.id.next_button);
        loadQuestions();  // 질문 세트 로드

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다음 버튼일 때
                if (currentQuestionSetIndex < questionSets.length - 1) {
                    addCurrentTypeStatus();
                    updateQuestions();  // 질문 텍스트 업데이트
                    resetRadioBtns();   // 라디오 버튼 리셋
                }
                // 종료 버튼일 때
                else {
                    addCurrentTypeStatus();
                    psyResult = calcType(psyTypes);
                    showResults();  // 결과 화면으로 이동
                }
            }
        });
    }

    private void loadQuestions() {
        // strings.xml에서 모든 질문 세트 로드
        questionSets = new String[][]{
                getResources().getStringArray(R.array.questions1),
                getResources().getStringArray(R.array.questions2),
                getResources().getStringArray(R.array.questions3),
                getResources().getStringArray(R.array.questions4),
                getResources().getStringArray(R.array.questions5),
                getResources().getStringArray(R.array.questions6),
                getResources().getStringArray(R.array.questions7),
                getResources().getStringArray(R.array.questions8)
        };
        updateQuestions();  // 초기 질문 세트 로드
    }

    private void updateQuestions() {
        if (currentQuestionSetIndex < questionSets.length) {
            for (int i = 0; i < questionViews.length; i++) {
                questionViews[i].setText(questionSets[currentQuestionSetIndex][i]);
                animateTextView(questionViews[i]);
            }
            // 마지막 문항 전까지는 버튼 텍스트를 '다음'으로 설정
            if (currentQuestionSetIndex == questionSets.length - 2) {
                nextButton.setText("제출");
            } else {
                nextButton.setText("다음");
            }
            currentQuestionSetIndex++;  // 다음 질문 세트 인덱스로 이동
        }
    }

    private void animateTextView(TextView textView) {
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        textView.startAnimation(fadeIn);
    }

    private void resetRadioBtns() {
        for(int i=0; i<radioButtons.length; i++) {
            radioButtons[i].setChecked(false);
        }
    }

    private void addCurrentTypeStatus() {
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isChecked()) {
                psyTypes[i]++;  // 해당 인덱스의 psyType 값을 증가
            }
        }
    }

    private int findMaxIndex(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int maxIndex = 0;  // 최대 값을 갖는 인덱스를 저장할 변수
        int maxValue = array[0];  // 최대 값을 저장할 변수, 첫 번째 요소로 초기화

        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    private int calcType(int[] psyTypes) {
        return findMaxIndex(psyTypes);
    }

    private void showResults() {
        // 결과 화면으로 이동
        Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
        intent.putExtra("psyResult", psyResult);
        startActivity(intent);
    }
}