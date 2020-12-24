package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class register extends AppCompatActivity {
    private ArrayAdapter adapter;
    private Spinner spinner;
    EditText edid, edpw;
    String id, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinner = (Spinner) findViewById(R.id.agespinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.age, android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        edid = (EditText) findViewById(R.id.idText);
        edpw = (EditText) findViewById(R.id.passwordText);

    }

    public void register(View v) {
        {
            id = edid.getText().toString();
            pw = edpw.getText().toString();
            Toast ts;

            File Directory = new File(getApplicationContext().getFilesDir(), id);
            if (Directory.exists()) {
                ts = Toast.makeText(this.getApplicationContext(), id + " is already Exist \n Please input Other id!", Toast.LENGTH_LONG);
                ts.show();
            } else {
                Directory.mkdir();
            }
            try {
                String path = "/data/data/com.example.diary/files/" + id;
                File file = new File(path + "/" + id + ".txt");
                if (file.exists()) {
                    ts = Toast.makeText(this.getApplicationContext(), id + " is already Exist \n Please input Other id!", Toast.LENGTH_LONG);
                    ts.show();
                } else {
                    FileWriter fw = new FileWriter(file);
                    if (pw.equals("")) {
                        ts = Toast.makeText(this.getApplicationContext(), " Password is not Correctly", Toast.LENGTH_LONG);
                        ts.show();
                        file.delete();
                    } else {
                        fw.write(pw);
                        fw.close();
                        ts = Toast.makeText(this.getApplicationContext(), id + " is created", Toast.LENGTH_LONG);
                        ts.show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        this.finish();
                    }
                }

            } catch (Exception e) {
            }
        }
    }
}
