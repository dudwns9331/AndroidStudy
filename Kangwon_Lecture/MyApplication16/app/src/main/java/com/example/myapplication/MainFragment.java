package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public MainFragment() {
// Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();

        for (int i=0; i<16; i++){
            String buttonId = "button"+(i+1);
            int resID = getResources().getIdentifier(buttonId, "id", activity.getPackageName());
            activity.findViewById(resID).setOnClickListener(new myListener());
        }
    }

    class myListener implements View.OnClickListener {
        public void onClick(View view){
            flagOnClicked(view);
        }
    }

    public void flagOnClicked(View v){
        ImageButton ib = (ImageButton) v;
        String tag = ib.getTag().toString();

        Activity activity = getActivity();
        if(activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(getContext(), CountryActivity.class);
            intent.putExtra("countryName", tag);
            activity.startActivity(intent);
        }else if(activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            CountryFragment frag = (CountryFragment) ((FragmentActivity) activity).getSupportFragmentManager().findFragmentById(R.id.frag_country);
            frag.readCountryInfo(tag);

        }
    }
}
