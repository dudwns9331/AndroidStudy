package com.example.moblieprogrammingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView tv_title = findViewById(R.id.title);       //intent된 title값을 표시하기 위한 변수
        TextView tv_album = findViewById(R.id.Album);       //intent된 album값을 표시하기 위한 변수
        TextView tv_artist = findViewById(R.id.artist);     //intent된 artist값을 표시하기 위한 변수
        TextView tv_genre = findViewById(R.id.genre);       //intent된 genre값을 표시하기 위한 변수
        ImageView iv_image = findViewById(R.id.image);      //intent된 image값을 표시하기 위한 변수

        String title, album, artist, genre;           // intent값을 string으로 받기 위한 변수

        Intent intent = getIntent();
        String image = (intent.getExtras()).getString("image");         //MusicLibrary에서 보낸 image값을 받음. 음악이 저장된 경로.
        Uri uri = Uri.parse(image);                                           //Uri를 넣음

        MediaMetadataRetriever music_data = new MediaMetadataRetriever();
        music_data.setDataSource(getApplication(), uri);

        //만약 음악 데이터에 이미지가 없지 않으면, InputStream에 ByteArray로 input_stream에 저장한다.
        if(music_data.getEmbeddedPicture() != null) {
            InputStream input_stream = new ByteArrayInputStream(music_data.getEmbeddedPicture());
            Bitmap bitmap = BitmapFactory.decodeStream(input_stream);
            music_data.release();
            iv_image.setImageBitmap(bitmap);                        // iv에 bitmap형식으로 저장된 이미지를 넣는다.
        }else {
            iv_image.setImageResource(R.drawable.default_image);   // 만약 아무것도 들어있지 않으면 default이미지를 넣는다.
        }

        /**
         * 각각 MusicLibrary에서 넘어온 intent를 저장한다.
         * 각 값을 나머지 veiw에 지정한다.
         */

        title = (intent.getExtras()).getString("title");
        album = intent.getExtras().getString("album");
        artist = intent.getExtras().getString("artist");
        genre = intent.getExtras().getString("genre");

        tv_title.setText(title);
        tv_album.setText((album));
        tv_artist.setText(artist);
        tv_genre.setText(genre);

        seek_bar(); // SeekBar를 보여줌

    }

    /**
     * 일지정지 버튼을 이용함
     * 일지정지를 누르더라도 seekbar가 움직이도록함
     */
    public void pause_button(View v) {
        seek_bar();
        Button pause = findViewById(R.id.pause);
        if(MusicLibrary.button_state) {
            MusicLibrary.mp.pause();
            MusicLibrary.button_state = false;
            pause.setText("Resume");
        } else {
            MusicLibrary.mp.start();
            MusicLibrary.button_state = true;
            pause.setText("Pause");
        }
    }
    /**
     * seek_bar()
     */
    public void seek_bar() {
        final SeekBar sb = findViewById(R.id.SeekBar);
        sb.setMax(MusicLibrary.mp.getDuration());  // 시크바에 음악의 총 길이를 적용시킨다.
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean user_point) {
                if(user_point)  // 시크바를 움직인다면 재생위치를 바꿔준다.
                    MusicLibrary.mp.seekTo(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        MusicLibrary.mp.start();
        new Thread(new Runnable(){  // 쓰레드 생성
            @Override
            public void run() {
                while(MusicLibrary.mp.isPlaying()){  // 음악이 실행중일때 계속 돌아가게 함
                    try{
                        Thread.sleep(1000); // 1초마다 시크바 움직이게 함
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    // 현재 재생중인 위치를 가져와 시크바에 적용
                    sb.setProgress(MusicLibrary.mp.getCurrentPosition());
                }
            }
        }).start();

        }
}
