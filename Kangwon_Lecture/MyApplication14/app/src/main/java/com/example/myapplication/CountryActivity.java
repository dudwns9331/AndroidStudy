package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Scanner;

public class CountryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        Intent intent = getIntent();
        String country = intent.getStringExtra("countryName");
        assert country != null;
        readCountryInfo(country);


    }


    public void readCountryInfo(String country) {
        String fileName = country.toLowerCase();
        String info = "";
        try {
            int fileId = getResources().getIdentifier(fileName, "raw", getPackageName());
            InputStream is = getResources().openRawResource(fileId);
            Scanner sc = new Scanner(is);
            while (sc.hasNextLine()) {
                info += sc.nextLine();
            }
            sc.close();
        } catch (Exception e) {
            Toast.makeText(this, "something's wrong", Toast.LENGTH_LONG).show();
        }
        ImageView iv = findViewById(R.id.flag);
        int imageId = getResources().getIdentifier(fileName, "drawable", getPackageName());
        iv.setImageResource(imageId);
        TextView nameView = findViewById(R.id.name);
        nameView.setText(country);
        TextView infoView = findViewById(R.id.info);
        infoView.setText(info);

    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "세로모드", Toast.LENGTH_SHORT).show();
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "가로모드", Toast.LENGTH_SHORT).show();
        }
    }
}