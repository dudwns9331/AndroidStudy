package com.example.myapplication3;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int answer[] = new int[3];               // 랜덤숫자 정답 3개
    private int player[] = new int[3];               // 사용자가 입력하는 숫자 3개
    private int count ,usercount ,_try = 0;          // count : 버튼이 입력되는 횟수 ... usercount: 번호판을 초기화 하기 위한 변수 ... _try : 시도횟수
    private int Strike, Ball, Out = 0;               // 스트라이크, 볼, 아웃 카운트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * GENERATE 버튼 랜덤 숫자를 생성한다.
     * @param v 뭔지 모르겠다. 할일 없는듯.
     */
    public void onButtonClicked_random_number(View v) {
        initGame();             // 게임 초기화

        Random rnd = new Random();

        while(true) {
            answer[0] = rnd.nextInt(10);
            answer[1] = rnd.nextInt(10);
            answer[2] = rnd.nextInt(10);

            if(answer[0] != answer[1] && answer[1] != answer[2] && answer[0] != answer[2])
                break;
        }


        String s = Integer.toString(answer[0]) +Integer.toString(answer[1]) + Integer.toString(answer[2]);

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * GENERATE 버튼을 누르면 실행. 게임을 초기화 시킨다.
     */
    public void initGame(){

        TextView set = findViewById(R.id.textView5);
        set.setText("0 Strike 0 Ball 0 Out");

        set = findViewById(R.id.textView2);
        set.setText("0");

        set = findViewById(R.id.textView5);
        set.setText("0");

        set = findViewById(R.id.textView4);
        set.setText("0");

        player[0] = 0;
        player[1] = 0;
        player[2] = 0;
        _try = 0;
        Strike = 0; Ball = 0; Out = 0;
        count = 0;
    }

    /**
     * 스트라이크, 볼, 아웃 카운트를 센다. 그리고 출력함.
     * _try 시도횟수도 센다.
     */
    public void printcount() {

        TextView three_count = findViewById(R.id.textView5);

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                if(answer[i] == player[j] && i == j)
                    Strike++;
                if(answer[i] == player[j] && i != j)
                    Ball++;
            }
        Out = 3 - (Strike + Ball);
            three_count.setText(Strike + " Strike " + Ball + " Ball " + Out + " Out");

            _try++; // 시도 횟수

        if(Strike == 3)
            Toast.makeText(this, "야구게임 승리! 시도횟수 : " +_try ,Toast.LENGTH_LONG).show();

            Strike = 0; Ball = 0; Out = 0;
    }

    /**
     * 버튼을 통해 입력된 숫자들을 텍스트로 표시한다.
     * textView2, 8, 7에 카운트 순서대로 텍스트를 변경한다.
     */
    public void show() {
        TextView tv = findViewById(R.id.textView2);
        TextView tv2 = findViewById(R.id.textView5);
        TextView tv3 = findViewById(R.id.textView4);

        if (count == 0)
            tv.setText(Integer.toString(player[count]));
        else if(count == 1)
            tv2.setText(Integer.toString(player[count]));
        else if (count == 2)
            tv3.setText(Integer.toString(player[count]));
    }

    /**
     * 입력 카운트가 3이되면 0으로 초기화 시킨다.
     * 유저 카운트가 3이되면 0으로 초기화 시킨다.
     */
    public void check() {
        if(count == 3) {
            count = 0;
        }
        if(usercount == 3) usercount = 0;
    }

    /**
     * 숫자 버튼을 눌렀을 때 실행되는 함수들과 변수를 모아놓은 함수
     * check()와 printcount(), show()를 실행시킨다.
     * @param n 버튼에 적힌 숫자
     */

    public void ButtonAction(int n) {
        check();
        player[count] = n;
        if (usercount == 2)
            printcount();
        show();
        count++;
        usercount++;
    }

    public void onButtonClicked_random_number_0(View v) {
        ButtonAction(0);
    }
    public void onButtonClicked_random_number_1(View v) {
        ButtonAction(1);
    }
    public void onButtonClicked_random_number_2(View v) {
        ButtonAction(2);
    }
    public void onButtonClicked_random_number_3(View v) {
        ButtonAction(3);
    }
    public void onButtonClicked_random_number_4(View v) {
        ButtonAction(4);
    }
    public void onButtonClicked_random_number_5(View v) {
        ButtonAction(5);
    }
    public void onButtonClicked_random_number_6(View v) {
        ButtonAction(6);
    }
    public void onButtonClicked_random_number_7(View v) {
        ButtonAction(7);
    }
    public void onButtonClicked_random_number_8(View v) {
        ButtonAction(8);
    }
    public void onButtonClicked_random_number_9(View v) {
        ButtonAction(9);
    }
}
