package com.example.diary;

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
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;

class PermissionHandler {
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
    EditText edid, edpw;
    String id, pw;
    int tap_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (PermissionHandler.isPermissionGranted(this, Manifest.permission.CAMERA, "ExternalStorage", 1000)) {
                try {
                    // Toast.makeText(this, "접근권한 허용됨", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                }
            }
        }

        edid = (EditText) findViewById(R.id.idText);
        edpw = (EditText) findViewById(R.id.passwordText);

        File finger = new File(getApplicationContext().getFilesDir(), "finger");
        if (finger.exists()) {
        } else {
            finger.mkdir();
        }
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, finger.class);
        startActivity(intent);
    }

    public void login(View v) {
        id = edid.getText().toString();
        pw = edpw.getText().toString();
        int data;
        char ch;
        String pass = "";
        Toast ts;
        File Directory = new File(getApplicationContext().getFilesDir(), id);
        if (Directory.exists()) {
            System.out.println("Exists");
            String path = "/data/data/com.example.diary/files/" + id;
            File file = new File(path + "/" + id + ".txt");
            FileReader fr = null;
            try {
                fr = new FileReader(file);
                while ((data = fr.read()) != -1) {
                    ch = (char) (data);
                    pass = pass + ch;
                }
                System.out.println(pw + "          " + pass);
                if (pw.equals(pass)) {
                    System.out.println("pass");
                    ts = Toast.makeText(this.getApplicationContext(), "Welcome " + id + " \n Have a Nice Day", Toast.LENGTH_LONG);
                    ts.show();
                    Intent intent = new Intent(this, list.class);
                    intent.putExtra("id", id);
                    startActivity(intent);

                } else {
                    ts = Toast.makeText(this.getApplicationContext(), "Wrong password!", Toast.LENGTH_LONG);
                    ts.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {

            Directory.delete();
            ts = Toast.makeText(this.getApplicationContext(), id + " is Not Corrected ID \n Please Register ID or Input other ID", Toast.LENGTH_LONG);
            ts.show();
        }
    }

    public void register(View v) {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
        this.finish();
    }

    public void onBackPressed() {
        tap_count++;
        // 한번 눌렀을때 종료 안내 메세지
        if (tap_count == 1) {
            Toast.makeText(this, "뒤로가기를 두 번 누르면 종료 됩니다.", Toast.LENGTH_LONG).show();
        } else if (tap_count == 2) {
            list.mediaPlayer.stop();
            // 해당 앱의 루트 엑티비티를 종료한다.
            ActivityCompat.finishAffinity(this);
            // 현재 엑티비티 종료
            System.exit(0);
        }
    }
}
