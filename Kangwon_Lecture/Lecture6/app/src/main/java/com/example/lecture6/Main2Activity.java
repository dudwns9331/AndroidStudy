package com.example.lecture6;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main2Activity extends AppCompatActivity {

    private static final String[] Planets = {
            "Mercury", "Mercury is the smallest and innermost planet in the Solar System.",
            "Venus", "Venus is the second planet from the Sun, orbiting it every 224.7 Earth days.",
            "Earth", "Where we are",
            "Mars", "Mars is the fourth planet from the Sun and the second-smallest planet in the Solar System after Mercury",
            "Jupiter", "Jupiter is the fifth planet from the Sun and the largest in the Solar System.",
            "Saturn", "Saturn is the sixth planet from the Sun and the second-largest in the Solar System, after Jupiter.",
            "Uranus", "Uranus (from the Latin name Ūranus for the Greek god Οὐρανός) is the seventh planet from the Sun.",
            "Neptune", "Neptune is the eighth and farthest known planet from the Sun in the Solar System.",
            "Pluto", "Pluto (minor planet designation: 134340 Pluto) is a dwarf planet in the Kuiper belt, a ring of bodies beyond Neptune"
    };

    private Map<String, String> planetMap;
    private ArrayList planetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        planetMap = new HashMap<String, String>();
        planetList = new ArrayList<String> ();

        readDatafromFiles();

        ArrayAdapter<String> adapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, planetList);
        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                String item = parent.getItemAtPosition(position).toString();
                TextView tv = (TextView) v;
                if(tv.getText().length() > 10){
                    tv.setText(item);
                }else {
                    String info = planetMap.get(item);
                    tv.setText(item + " : " + info);
                }
            }
        });
    }

    public void readDatafromFiles() {
        InputStream is = getResources().openRawResource(R.raw.planet);
        Scanner scan = new Scanner(is);
        Scanning(scan);

        File file = new File(getFilesDir(), "planet");
        if(file.exists()) {
            try {
                FileInputStream fis = openFileInput("planet");
                scan = new Scanner(fis);
                Scanning(scan);
            } catch (Exception e) {}
        }
    }

    public void Scanning(Scanner scan) {
        while(scan.hasNextLine()) {
            String item = scan.nextLine();
            StringTokenizer st = new StringTokenizer(item);
            String name = st.nextToken(",");
            String info = st.nextToken(",");

            planetMap.put(name, info);
            planetList.add(name);
        }
    }
}
