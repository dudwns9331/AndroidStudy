package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Scanner;

public class CountryFragment extends Fragment {

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        String country = intent.getStringExtra("countryName");
    }

    public void readCountryInfo(String country){
        Activity activity = getActivity();

        String fileName = country.toLowerCase();
        String info = "";
        try {
            int fileId = getResources().getIdentifier(fileName, "raw", activity.getPackageName());
            InputStream is = getResources().openRawResource(fileId);
            Scanner sc = new Scanner(is);
            while (sc.hasNextLine()){
                info += sc.nextLine();
            }
            sc.close();
        }catch(Exception e){
            Toast.makeText(activity.getApplicationContext(), "something's wrong", Toast.LENGTH_LONG).show();
        }
        ImageView iv = activity.findViewById(R.id.flag);
        int imageId = getResources().getIdentifier(fileName, "drawable", activity.getPackageName());
        iv.setImageResource(imageId);
        TextView nameView = activity.findViewById(R.id.name);
        nameView.setText(country);
        TextView infoView = activity.findViewById(R.id.info);
        infoView.setText(info);

    }
}
