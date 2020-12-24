package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
}

    public void RadioButtonMan(View v) {
        ImageView iv = findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.man_120);
    }


    public void RadioButtonWoman(View v) {
        ImageView iv = findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.woman_120);
    }

    public void ButtonClicked (View v) {
        EditText et = findViewById(R.id.editText);
        String age = et.getText().toString();
        EditText et2 = findViewById(R.id.editText2);
        String name = et2.getText().toString();

        Toast.makeText(this,  name + "살 " + age + "님 정보가 입력되었습니다.", Toast.LENGTH_LONG).show();
    }


}
