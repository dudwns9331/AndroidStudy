package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int answer[] = new int[3];
    int player[] = new int[3];

    TextView tv = findViewById(R.id.textView2;
    TextView tv2 = findViewById(R.id.textView8);
    TextView tv3 = findViewById(R.id.textView7);


    int count = 0;
    int Strike, Ball, Out = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClicked_random_number(View v) {
        Random rnd = new Random();

        while(true) {
            answer[0] = rnd.nextInt(10);
            answer[1] = rnd.nextInt(10);
            answer[2] = rnd.nextInt(10);

            if(answer[0] != answer[1] && answer[1] != answer[2] && answer[0] != answer[2])
                break;
        }
        String s = Integer.toString(answer[0]) +Integer.toString(answer[1]) + Integer.toString(answer[2]);;

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }

    public void printcount() {

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                if(answer[i] == player[j] && i == j)
                    Strike++;
                if(answer[i] == player[j] && i != j)
                    Ball++;
            }
        Out = 3 - (Strike + Ball);
    }

    public void check() {
        if(count == 3) {
            printcount();
            count = 0;
        }
        if(Strike == 3)
            Toast.makeText(this, "야구게임 승리!",Toast.LENGTH_LONG).show();
    }

    public void show(View v) {
        if (count == 0)
            tv.setText(player[count]);
        else if(count == 1)
            tv2.setText(player[count]);
        else if (count == 2)
            tv3.setText(player[count]);
    }

    public void onButtonClicked_random_number_0(View v) {
        check();
        player[count++] = 0;
        show(v);
    }
    public void onButtonClicked_random_number_1(View v) {
        check();
        player[count++] = 1;
        show(v);
    }
    public void onButtonClicked_random_number_2(View v) {
        check();
        player[count++] = 2;
        show(v);
    }
    public void onButtonClicked_random_number_3(View v) {
        check();
        player[count++] = 3;
        show(v);
    }
    public void onButtonClicked_random_number_4(View v) {
        check();
        player[count++] = 4;
        show(v);
    }
    public void onButtonClicked_random_number_5(View v) {
        check();
        player[count++] = 5;
        show(v);
    }
    public void onButtonClicked_random_number_6(View v) {
        check();
        player[count++] = 6;
        show(v);
    }
    public void onButtonClicked_random_number_7(View v) {
        check();
        player[count++] = 7;
        show(v);
    }
    public void onButtonClicked_random_number_8(View v) {
        check();
        player[count++] = 8;
        show(v);
    }
    public void onButtonClicked_random_number_9(View v) {
        check();
        player[count++] = 9;
        show(v);
    }
}
