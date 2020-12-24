package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        int count = bundle.getInt("햄버거");
        int count2 = bundle.getInt("샌드위치");
        int count3 = bundle.getInt("라면");
        int count4 = bundle.getInt("치킨");
        int count5 = bundle.getInt("피자");

        TextView tv = findViewById(R.id.textView2);
        tv.setText("햄버거 " + count + "개");
        tv = findViewById(R.id.textView3);
        tv.setText("샌드위치" + count2 + "개");
        tv = findViewById(R.id.textView4);
        tv.setText("라면 " + count3 + "개");
        tv = findViewById(R.id.textView5);
        tv.setText("치킨 " + count4 + "개");
        tv = findViewById(R.id.textView6);
        tv.setText("피자 " + count5 + "개");

    }
}
