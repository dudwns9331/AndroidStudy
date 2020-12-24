package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int likeit[];
    int like;
    int curImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        curImage = 0;
        likeit = new int[2];
    }

    public void onButtonClicked(View v) {
        EditText et = findViewById(R.id.editText);
        String it = et.getText().toString();
        //Toast.makeText(this, it, Toast.LENGTH_LONG).show();

        if(it.equals("cat2")) {
            ImageView iv = findViewById(R.id.imageView);
            iv.setImageResource(R.drawable.cat2);
            curImage = 2;
        }

        if(it.equals("cat")) {
            ImageView iv = findViewById(R.id.imageView);
            iv.setImageResource(R.drawable.cat);
            curImage = 1;
        }
    }

    public void onRadioClicked(View v) {
        TextView tv = findViewById(R.id.textView);
        if(curImage == 1){
            tv.setText(Integer.toString(likeit[0]));
            likeit[0]++;
        }
        else if (curImage == 2) {
            tv.setText(Integer.toString(likeit[1]));
            likeit[1]++;
        }

    }

    public void onRadioGroupClicked (View v) {
        ImageView iv = findViewById(R.id.imageView);

        int id = v.getId();
        if(id == R.id.radioButton2) {
            iv.setImageResource(R.drawable.cat);
            curImage = 1;
        }else if (id == R.id.radioButton3) {
            iv.setImageResource(R.drawable.cat2);
            curImage = 2;
        }
    }

//    public void onRadioClicked2(View v) {
//        ImageView iv = findViewById(R.id.imageView);
//        iv.setImageResource(R.drawable.cat);
//    }
//
//    public void onRadioClicked3(View v) {
//        ImageView iv = findViewById(R.id.imageView);
//        iv.setImageResource(R.drawable.cat2);
//
//    }

}
