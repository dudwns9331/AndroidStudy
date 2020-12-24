package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FilenameFilter;

public class MainActivity extends AppCompatActivity {
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath();


        public void updateSongList () {
            File home = new File(path);
            if (home.listFiles(new Mp3Filter()).length > 0) {
                for (File file : home.listFiles(new Mp3Filter())) {
                    songs.add(file.getName());
                }
                ArrayAdapter<String> songList = new ArrayAdapter<String>(this, R.layout.song_item, songs);
                setListAdapter(songList);
            }

            MediaPlayer mp = new MediaPlayer();
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                if (PermissionHandler.isPermissionGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, "External Storage", 1000)) {
                    try {
                        mp.setDataSource(this, uri);
                        mp.prepare();
                        mp.start();
                    } catch (Exception e) {
                    }
                }
            }

        }
    }

    class Mp3Filter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3"));
        }
    }
}
