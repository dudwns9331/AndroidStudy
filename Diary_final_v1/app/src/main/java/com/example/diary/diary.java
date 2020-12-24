package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class diary extends AppCompatActivity {
    EditText edit;
    TextView text;
    String content_name;
    StringBuffer buffer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        ImageButton btn = (ImageButton) findViewById(R.id.imageButton3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.open_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.m1:
                                if (list.isPlaying == true) {
                                    list.mediaPlayer.pause();
                                    list.isPlaying = false;
                                }
                                break;
                            case R.id.m2:
                                if (list.isPlaying == false) {
                                    list.mediaPlayer.start();
                                    list.isPlaying = true;
                                }
                                break;
                            case R.id.m3:
                                Intent intentHome = new Intent(v.getContext(), MainActivity.class);
                                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intentHome);
                                break;
                            case R.id.m4:
                                Intent reintent = new Intent(v.getContext(), photo.class);
                                startActivity(reintent);

                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        Intent diary_intent = getIntent();
        final String id = diary_intent.getStringExtra("id");
        content_name = diary_intent.getStringExtra("diary_content");

        text = findViewById(R.id.text);
        edit = findViewById(R.id.editText2);
        read_data();
    }

    public void save_button(View v) {
        Intent diary_intent = getIntent();
        final String id = diary_intent.getStringExtra("id");

        File file = new File("/data/data/com.example.diary/files/" + id + "/" + content_name + ".txt");
        String data = edit.getText().toString(); //EditText에서 Text 얻어오기
        edit.setText("");
        try {
            //FileOutputStream 객체생성, 파일명 "data.txt", 새로운 텍스트 추가하기 모드
            read_data();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp = "";
            String st = null;
            while ((st = br.readLine()) != null) {
                temp += st + "\n";
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(temp + data + "\n");
            bw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        read_data();
    }

    public void read_data() {
        buffer = new StringBuffer();
        Intent diary_intent = getIntent();
        final String id = diary_intent.getStringExtra("id");
        try {

            File file = new File("/data/data/com.example.diary/files/" + id + "/" + content_name + ".txt");
            //FileInputStream 객체생성, 파일명 "data.txt"
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String str = reader.readLine();//한 줄씩 읽어오기

            while (str != null) {
                buffer.append(str + " ");
                str = reader.readLine();
            }
            text.setText(buffer.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void edit_button(View v) {
        edit.setText(buffer.toString());
        edit.setSelection(edit.getText().length());
        delete_button(v);
    }

    public void delete_button(View v) {
        Intent diary_intent = getIntent();
        final String id = diary_intent.getStringExtra("id");
        Log.i("TAG", "delete 진행");

        File file = new File("/data/data/com.example.diary/files/" + id + "/" + content_name + ".txt");
        boolean b = file.delete();
        if (b) {
            Toast.makeText(this, "delete 완료", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "delete 실패", Toast.LENGTH_SHORT).show();
        }

        updateTextView("");
    }

    public void updateTextView(String toThis) {
        TextView textView = findViewById(R.id.text);
        textView.setText(toThis);
    }
}
