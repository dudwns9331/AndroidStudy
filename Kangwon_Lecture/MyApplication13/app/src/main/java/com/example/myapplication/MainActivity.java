package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    String s;
    int count = 0;
    int burgercount, sandwichcount, ramencount, chickencount, pizzacount;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addWidget(View v){
        TextView tv = new TextView(this);
        tv.setText(s + "\t");

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, // width
                ViewGroup.LayoutParams.WRAP_CONTENT); // height
        tv.setLayoutParams(params);
        GridLayout layout = findViewById(R.id.mylayout);
        layout.addView(tv, count);
        count++;
        //layout.addView(tv);
    }

    public void orderClick(View v) {

        int btn_id = v.getId();

        if(btn_id == R.id.burgerbutton) {
            s = "햄버거\t\t";
            addWidget(v);
            burgercount++;
        }
        if(btn_id == R.id.sandwichbutton) {
            s ="샌드위치\t";
            addWidget(v);
            sandwichcount++;
        }
        if(btn_id == R.id.ramenbutton) {
            s ="라면\t\t\t";
            addWidget(v);
            ramencount++;
        }
        if(btn_id == R.id.chickenbutton) {
            s ="치킨\t\t\t";
            addWidget(v);
            chickencount++;
        }
        if(btn_id == R.id.pizzabutton) {
            s = "피자\t\t\t";
            addWidget(v);
            pizzacount++;
        }

        if(btn_id == R.id.orderbutton) {
            intent =  new Intent(this, Main2Activity.class);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("주문 완료");
            builder.setMessage("이대로 주문을 완료하시겠습니까?");
            builder.setNegativeButton("아니요", null);

            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    intent.putExtra("햄버거", burgercount);
                    intent.putExtra("샌드위치", sandwichcount);
                    intent.putExtra("라면", ramencount);
                    intent.putExtra("치킨", chickencount);
                    intent.putExtra("피자", pizzacount);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
