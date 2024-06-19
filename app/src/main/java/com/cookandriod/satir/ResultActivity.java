package com.cookandriod.satir;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);  // 결과를 보여주는 레이아웃

        // Intent에서 결과 데이터 받기
        int psyResult = getIntent().getIntExtra("psyResult", 0);  // 기본값은 0

        String[] psyTypes = new String[5];
        psyTypes[0] = "회유형";
        psyTypes[1] = "비난형";
        psyTypes[2] = "초이성형";
        psyTypes[3] = "산만형";
        psyTypes[4] = "일치형";

        // 결과를 사용자에게 표시
        TextView resultView = findViewById(R.id.result_text);
        String resultText = "당신의 유형은 " + (psyTypes[psyResult]) + "입니다.";
        resultView.setText(resultText);
    }
}