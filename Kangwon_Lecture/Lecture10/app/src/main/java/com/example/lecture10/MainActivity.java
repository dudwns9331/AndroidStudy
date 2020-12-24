package com.example.lecture10;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private static String[] countries = {
            "country", "austrailia", "canada", "korea"
    };

    public static String countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.countryName = "country";
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                MainActivity.countryName = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void OnButtonClicked(View v) {
        if(!MainActivity.countryName.equals("country")) {
            ImageView iv = findViewById(R.id.image);
            String filename = "http://icn.kangwon.ac.kr/images/" + MainActivity.countryName + ".png";
            Picasso.get().load(filename).into(iv);
           // YoYo.with(Techniques.Bounce).duration()
        }
    }
}