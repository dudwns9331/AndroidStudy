package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

public class MusicPlayerControl extends AppCompatActivity {

    MediaPlayer mp = new MediaPlayer();
    Boolean button_state = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_control);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        final String music_name = bundle.getString("music");
        final String music_info = bundle.getString("music_info");

        Button play_button = findViewById(R.id.PLAY_button);
        final Button pause_button = findViewById(R.id.PAUSE_button);
        Button stop_buttion = findViewById(R.id.STOP_button);

        ImageView iv = findViewById(R.id.imageView);

        if(music_name.equals("The Awakening"))
            iv.setImageResource(R.drawable.awakening);
        else if(music_name.equals("Forgiveness"))
            iv.setImageResource(R.drawable.forgiveness);
        else
            iv.setImageResource(R.drawable.night);

        TextView tv = findViewById(R.id.Music_name);
        TextView tv2 = findViewById(R.id.Music_info);

        tv.setText(music_name);
        tv2.setText(music_info);

        if (music_name.equals("The Awakening")) {
            play_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mp = MediaPlayer.create(MusicPlayerControl.this, R.raw.the_awakening);
                    mp.start();
                }
            });
        }
        if (music_name.equals("Forgiveness")) {
            play_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mp = MediaPlayer.create(MusicPlayerControl.this, R.raw.forgiveness);
                    mp.start();
                }
            });
        }
        if (music_name.equals("One Night Away")) {
            play_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mp = MediaPlayer.create(MusicPlayerControl.this, R.raw.one_night_away);
                    mp.start();
                }
            });

        }
        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button_state) {
                    mp.pause();
                    button_state = false;
                    pause_button.setText("Resume");
                } else {
                    mp.start();
                    button_state = true;
                    pause_button.setText("Pause");
                }
            }
        });

        stop_buttion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        if(mp != null) {
            mp.release();
            mp = null;
        }
    }
}