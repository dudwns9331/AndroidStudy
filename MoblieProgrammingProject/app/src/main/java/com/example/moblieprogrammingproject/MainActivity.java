package com.example.moblieprogrammingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * PermissionHandler 안드로이드 앱의 외부 저장소에 대한 권한을 얻기 위해 사용함.
 */
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

public class MainActivity extends AppCompatActivity {

    int tap_count = 0;          // 뒤로가기 버튼을 눌렀을 때 올라가는 카운트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 접근권한이 제대로 허용되었는지 묻는 조건문
         */
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (PermissionHandler.isPermissionGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, "ExternalStorage", 1000)) {
                try {
                    Toast.makeText(this,"접근권한 허용됨",Toast.LENGTH_LONG).show();
                } catch (Exception e) { }}}

        ImageButton ib = findViewById(R.id.load);                       // 이미지를 넣을 버튼
        Glide.with(this).load(R.raw.musicismylife).into(ib);    // Glide 클래스를 활용하여 R.raw 폴더에 저장되어있는 GIF 파일을 ib 객체에 삽입한다.

        final Intent Music_Library = new Intent(this, MusicLibrary.class);     // 메인에서 버튼을 눌렀을때 넘어갈 엑티비티인 MusicLibrary를 지정해준다.
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Music_Library);
            }
        });
    }

    /**
     * 뒤로가기 키를 눌렀을 때.
     */
    public void onBackPressed() {
        tap_count++;
        // 한번 눌렀을때 종료 안내 메세지
        if(tap_count == 1) {
            Toast.makeText(this, "뒤로가기를 두 번 누르면 종료 됩니다.", Toast.LENGTH_LONG).show();
        }
        // 두번 눌렀을때, 만약 백그라운드가 실행되어 고정알림이 떠있는 상태라면 고정알림 제거, 그렇지 않으면 그대로 음악을 멈추고 종료.
        else if(tap_count == 2) {
            if(MusicLibrary.is_background) {
                // MusicLibrary의 id가 1인 notification을 종료시킨다.
                MusicLibrary.notificationManager.cancel(1);
            }
            MusicLibrary.mp.stop();
            // 해당 앱의 루트 엑티비티를 종료한다.
            ActivityCompat.finishAffinity(this);
            // 현재 엑티비티 종료
            System.exit(0);
        }

    }
}
