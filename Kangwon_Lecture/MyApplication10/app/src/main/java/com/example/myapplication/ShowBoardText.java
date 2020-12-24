package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowBoardText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_board_text);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String title = bundle.getString("title");
        String content = bundle.getString("content");

        TextView title_text = findViewById(R.id.board_title);
        TextView content_text = findViewById(R.id.board_content);

        title_text.setText(title);
        content_text.setText(content);
    }
}
