package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String[] Boards_contents = {
            "The Awakening" , "P.Pos",
            "Forgiveness", "Patrikios",
            "One Night Away", "Patrick"
    };
    private Map<String, String> MusicMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList music_list = new ArrayList<String>();
        MusicMap = new HashMap<String, String>();

        for(int i = 0; i < this.Boards_contents.length; i+=2) {
            MusicMap.put(Boards_contents[i], Boards_contents[i+1]);
            music_list.add(Boards_contents[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,music_list);

        ListView lv = findViewById(R.id.music_list_value);
        final Intent musicplayer_intent = new Intent(this, MusicPlayerControl.class);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String info = MusicMap.get(item);

                musicplayer_intent.putExtra("music", item);
                musicplayer_intent.putExtra("music_info",info);

                startActivity(musicplayer_intent);
            }
        });

    }
}
