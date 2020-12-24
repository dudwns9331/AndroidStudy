package com.example.lecture6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.PrintStream;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    public void addNewItemClicked(View v) {
        EditText etName = findViewById(R.id.editText);
        EditText etInfo =findViewById(R.id.editText2);

        String name = etName.getText().toString();
        String info = etInfo.getText().toString();

        try {
            File file = new File(getFilesDir(), "planet");
            PrintStream out = new PrintStream(openFileOutput("planet", MODE_APPEND));
            out.write((name + "," + info + "\n").getBytes());
            out.close();

            etName.setText("");
            etInfo.setText("");
           // Toast.makeText(this, name + "is successfully added" , Toast.LENGTH_LONG).show();
        }catch (Exception e) {}

        Intent toMain = new Intent();
        toMain.putExtra("newPlanet", name);
        setResult(RESULT_OK, toMain);
        finish();
    }
}
