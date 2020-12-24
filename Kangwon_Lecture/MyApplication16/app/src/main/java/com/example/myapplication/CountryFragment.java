package com.example.myapplication;


import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.InputStream;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountryFragment extends Fragment {


    public CountryFragment() {
// Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent intent = getActivity().getIntent();
            String country = intent.getStringExtra("countryName");
            if(country.isEmpty()){
                country = "Korea";
            }
            readCountryInfo(country);
        }
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
        YoYo.with(Techniques.Bounce).duration(2000).repeat(5).playOn(iv);
        int imageId = getResources().getIdentifier(fileName, "drawable", activity.getPackageName());
        iv.setImageResource(imageId);
        TextView nameView = activity.findViewById(R.id.name);
        nameView.setText(country);
        TextView infoView = activity.findViewById(R.id.info);
        infoView.setText(info);

        YoYo.with(Techniques.Bounce).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}
            @Override
            public void onAnimationEnd(Animator animator) {}
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}}).duration(2000).repeat(5).playOn(iv);
        }

}