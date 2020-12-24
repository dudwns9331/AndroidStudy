package com.example.test2;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath() + "/forgiveness.mp3";
        uri = Uri.parse(path);

        File file = new File(path);
        mp = new MediaPlayer();

        if (file.exists() && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (PermissionHandler.isPermissionGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, "External Storage", 1000)) {

                try {
                    mp.setDataSource(this, uri);
                    mp.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void onButtonClicked(View view) {

        final String uriPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath() + "/forgiveness.mp3";
        final Uri uri=Uri.parse(uriPath);

        MediaMetadataRetriever mmdata = new MediaMetadataRetriever();
      //  mmdata.setDataSource(getApplication(), uri);


        String album = mmdata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String artist = mmdata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String genre = mmdata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        String title = mmdata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

        Intent intent = new Intent(this, MusicInfo.class);

        intent.putExtra("album", album);
        intent.putExtra("artist", artist);
        intent.putExtra("genre", genre);
        intent.putExtra("title", title);

        startActivity(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }



}

class PermissionHandler{
    public static boolean isPermissionGranted(Activity mContext, String Permission, String Text, int PermissionCode) {

        if (ContextCompat.checkSelfPermission(mContext, Permission) != PackageManager.PERMISSION_GRANTED) {
            reqPermission(mContext, Text, PermissionCode, Permission);
            return false;
        }

        return true;
    }

    public static void reqPermission(Activity mContext, String Text, int PermissionCode, String Permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(mContext, Permission)) {
            ActivityCompat.requestPermissions(mContext, new String[]{Permission}, PermissionCode);
        }
    }

}