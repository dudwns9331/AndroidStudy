package com.example.test2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MusicInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_info);

        Intent intent = getIntent();
        String genre = intent.getStringExtra("genre");
        String artist = intent.getStringExtra("artist");
        String album = intent.getStringExtra("album");
        String title = intent.getStringExtra("title");


        TextView tv = findViewById(R.id.textView3);
        if(genre != null)
            tv.setText("Title : " + title + "\n" +
                    "Artist : " + artist + "\n" +
                    "Album : " + album + "\n" +
                    "Genre : " + genre);

    }

}