package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final String[] Genres = {
            "소설", "200자 원고지 1000장 안팎 / 줄거리 10장 별도",
            "시", "연 수 , 글자 수 제한 없음",
            "수필", "200자 원고지 20장 안팎으로 2편",
            "산문", "글자 수 제한 없음",
            "극본", "200자 원고지 400장 안팎 / 시놉시스 10장 별도"
    };

    private Map<String, String> genreMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genreMap = new HashMap<String, String>();
        final ArrayList genreList = new ArrayList<String>();

        for (int i = 0; i < this.Genres.length; i += 2) {
            genreMap.put(Genres[i], Genres[i + 1]);
            genreList.add(Genres[i]);
        }
        Spinner sp = findViewById(R.id.spinner);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String info = genreMap.get(item);

                TextView tv = findViewById(R.id.textView3);
                tv.setText(">> " + info);
                TextView tv2 = findViewById(R.id.textView4);
                tv2.setText("장르 : " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do not need to care
            }
        });
    }

    public void onButtonClicked(View v) {
        EditText et = findViewById(R.id.editText2);
        String fileName = et.getText().toString();
        String data = readFileData(fileName);
        TextView tv = findViewById(R.id.textView6);
        tv.setText(data);
    }

    public String readFileData(String fileName) {
        InputStream is =
                getResources().openRawResource(getResources().getIdentifier(
                        fileName, "raw", getPackageName()));
        Scanner fs = new Scanner(is);
        String data = "";
        while (fs.hasNextLine()) {
            data += fs.nextLine();
        }
        fs.close();
        return data;
    }
}
