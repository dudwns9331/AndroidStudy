package com.example.lecture6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String param = intent.getStringExtra("name");
    }

    public void showPlanetClicked(View v){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void addPlanetClicked(View v) {
        Intent intent = new Intent(this, Main3Activity.class);
        //startActivity(intent);
        startActivityForResult(intent,1111);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1111) {
            String newPlanet = data.getStringExtra("newPlanet");
            Toast.makeText(this, newPlanet + "is successfully added", Toast.LENGTH_LONG).show();
        }
    }
}
