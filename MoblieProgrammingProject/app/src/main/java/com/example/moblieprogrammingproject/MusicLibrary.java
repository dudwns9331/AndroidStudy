package com.example.moblieprogrammingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.support.v4.app.*;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;


public class MusicLibrary extends AppCompatActivity {

    public  static MediaPlayer mp = new MediaPlayer();              //음악이 재생될 객체
    public  static NotificationManager notificationManager;        //상단바에 알림을 생서하기 위한 객체
    public static boolean is_background = false;                    //백그라운드 상태인지 아닌지 구분하기 위한 변수

    String album ,artist, genre ,title ,image;                         //기기에 저장된 음악 정보들을 저장하기 위한 변수

    boolean is_play = false;                                          //음악이 현재 플레이되고 있는지 알기위한 변수
    public static boolean button_state = true;                      //pause, resume 버튼을 사용하기 위한 변수

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_library);

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath(); // 안드로이드 기기에 음악이 들은 주소 /mnt/sdcard/Music 의 주소를 저장함

        File dir = new File(path); // path에 저장된 경로의 파일을 dir에 저장함

        int i = 0;

        // 음악파일 내부에 있는 파일들의 개수가 몇개인지 세는 반복문
        for (File file : (dir.listFiles())) { // dir = /mnt/sdcard/Music  listFiles() 함수를 통해 리스트를 넘기면서 반복문을 돌린다.
            if (file.isFile()) { // 만약 파일이면 i를 하나씩 증가.
                i++;
            }
        }

        String[] music_list = new String[i]; // musiclist 음악 폴더에 저장된 파일들을 저장하는 배열 i만큼 크기의 배열이 생성된다.

        i = 0;

        // 음악 폴더에 저장된 파일들을 music_list 배열에 넣음. nmae.substring  substirng 함수를 이용하여, 리스트에 .mp3를 제외한 이름을 넣음
        for (File file : (dir.listFiles())) {
            if (file.isFile()) {
                String name = file.getName();
                music_list[i] = name.substring(0,name.lastIndexOf('.')); // .mp3를 제외한 파일의 이름이 저장된다.
                i++;
            }
        }


        // 어탭터를 이용하여 만들어진 music_list를 listView에 넣는다.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, music_list);
        ListView lv= findViewById(R.id.music_list_value);
        lv.setAdapter(adapter);

        // 리스트뷰에 있는 노래 이름을 눌렀을 때 InfoActivity로 넘어가기 위해 선언된 intent변수.
        final Intent music_player_intent = new Intent(this, InfoActivity.class);

        // 만약 리스트가 눌렸을 때 실행되는 무명클래스
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                String music_name = parent.getItemAtPosition(position).toString(); // 음악의 리스트에 있는 얻어온다.
                try {
                    List_Clicked(music_name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                music_player_intent.putExtra("album",album);
                music_player_intent.putExtra("artist",artist);
                music_player_intent.putExtra("genre",genre);
                music_player_intent.putExtra("title",title);
                music_player_intent.putExtra("image",image);
                startActivity(music_player_intent);
            }
        });

    }

    /**
     * pause버튼, 일시정지 기능이 탑재되어 있다.
     */
    public void pause_button(View v) {
        Button pause = findViewById(R.id.pause_button);
        if(button_state) {
            mp.pause();
            button_state = false;
            pause.setText("Resume");
        } else {
            mp.start();
            button_state = true;
            pause.setText("Pause");
        }
    }

    /**
     * 백그라운드로 재생하기
     */
    public void background_button(View v) {

        is_background = true; // 백그라운드 버튼이 눌리면 true
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        final Intent Library_intent = new Intent(this,MusicLibrary.class); // notification에 띄워진 알림을 클릭했을때 돌아올 엑티비티


        //PendingIntent는 Intent를 가지고 있는 클래스로, 기본 목적은 다른 애플리케이션(다른 프로세스)의 권한을 허가하여 가지고 있는 Intent를 마치 본인 앱의 프로세스에서 실행하는 것처럼 사용하게 하는 것입니다.
        //PendingIntent를 이용하여 notificaiton이 실행될 때 FLAG_UPDATE_CURRENT 를 통해서 라이브러리 엑티비티의 상태를 업데이트 시킨다.
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, Library_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //notification.Builder를 통해서 각종 설정을 한다.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.raw.images);
        builder.setContentTitle("MusicLibrary");                    //알림의 텍스트 설정
        builder.setContentText("음악이 플레이되고 있습니다.");         //알림의 텍스트 설정
        builder.setContentIntent(mPendingIntent);
        builder.setOngoing(true);                                   //알림이 사라지지 않고 음악이 실행되는 동안 계속 유지한다.
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);     // #알림이 생성될 때 소리를 내지 않고 생성하게 하려 했으나, 실패함.
        builder.setAutoCancel(false);                               //알림이 자동으로 사라지지 않도록 한다.

        notificationManager.notify(1, builder.build());         //알림의 id값을 1로하고 생성한다.
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);                       // 백그라운드 버튼이 눌리면 안드로이드에서 제공하는 homekey버튼을 실행하도록 한다.
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    /**
     * 만약 리스트가 눌린다면, 각종 정보이 InfoActiviy로 넘어가도록 설정하고, 음악을 재생한다.
     * 만약 info에서 되돌아왔을 경우 음악이 계속 재생되고,
     * 음악이 재생되는 상태에서 다른 음악을 선택하면 그 음악을 재생하도록 한다.
     */
    public void List_Clicked(String item) throws IOException {

        // 경로 + / + item(파라미터) + .mp3 를 통해서 리스트를 눌렀을 경우 넘어온 item을 통해 재생할 음악 파일의 주소를 얻는다.
        String music = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath() + "/" + item + ".mp3";
        Uri music_uri = Uri.parse(music);   // MediaPlayer에 들어갈 Uri를 새로 만들어준다.

        //각종 정보들을 음악파일로부터 추출하기 위해 사용함
        MediaMetadataRetriever music_data = new MediaMetadataRetriever();

        music_data.setDataSource(getApplication(), music_uri);                                  // 선택한 리스트를 받아서 source를 지정함.

        album = music_data.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);       // 앨범
        artist = music_data.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);     // 아티스트
        genre = music_data.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);       // 장르
        title = music_data.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);       // 타이틀
        image = music;                                                                      // 이미지는 파일의 주소를 받는다. Bitmap을 활용하기 위함.

        // is_ play 음악이 재생되고 있으면 true
            if (!is_play) {
                music_stop();
                mp.setDataSource(this, music_uri);
                mp.prepare();
                mp.start();
                mp.setLooping(true);        // 재생이 계속 돌아가도록
                is_play = true;
            } else {
                music_stop();
                mp.setDataSource(this, music_uri);
                mp.prepare();
                mp.start();
                mp.setLooping(true);
                is_play = true;
            }

    }

    /**
     * 멈추는 기능을 추가함.
     */
    public void music_stop() {
        is_play = false;
        mp.stop();
        mp.reset();
    }

    /**
     * 종료 버튼
     */
    public void quit_button(View v) {
        if(is_background) {                     // 만약 quit버튼이 눌렸을 때 백그라운드가 실행되고 있으면 알림 삭제
            notificationManager.cancel(1);
        }
        ActivityCompat.finishAffinity(this);
        System.exit(0);
    }

    /**
     * 메인 엑티비티를 실행한다.
     */

    public void go_main(View v) {
        final Intent Main_Activity = new Intent(this, MainActivity.class);
        startActivity(Main_Activity);
    }

    /**
     * 뒤로가기 키를 눌렀을 때 엑티비티가 겹치는것을 방지하기 위함.
     */
    @Override
    public void onBackPressed() {}
}
