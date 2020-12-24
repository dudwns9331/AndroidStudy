package com.example.diary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class list extends AppCompatActivity {

    String diary_name;
    private ArrayList diary_list;
    ArrayAdapter<String> adapter;
    ListView lv;
    final Context context = this;
    AlertDialog.Builder alert;
    int mposition;

    static MediaPlayer mediaPlayer = new MediaPlayer();
    public static boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final CharSequence[] oItems = {"우울", "기쁨", "짜증", "행복"};

        AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        oDialog.setTitle("기분을 선택하세요")
                .setSingleChoiceItems(oItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            if (isPlaying == false) {
                                mediaPlayer = MediaPlayer.create(list.this, R.raw.down);
                                mediaPlayer.start();
                                isPlaying = true;
                            } else {
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer = MediaPlayer.create(list.this, R.raw.down);
                                mediaPlayer.start();
                                isPlaying = true;
                            }
                        }
                        if (which == 1) {
                            if (isPlaying == false) {
                                mediaPlayer = MediaPlayer.create(list.this, R.raw.shine);
                                mediaPlayer.start();
                                isPlaying = true;
                            } else {
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer = MediaPlayer.create(list.this, R.raw.shine);
                                mediaPlayer.start();
                                isPlaying = true;
                            }
                        }
                        if (which == 2) {
                            if (isPlaying == false) {
                                mediaPlayer = MediaPlayer.create(list.this, R.raw.angry);
                                mediaPlayer.start();
                                isPlaying = true;
                            } else {
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer = MediaPlayer.create(list.this, R.raw.angry);
                                mediaPlayer.start();
                                isPlaying = true;
                            }
                        }
                        if (which == 3) {
                            if (isPlaying == false) {
                                mediaPlayer = MediaPlayer.create(list.this, R.raw.up);
                                mediaPlayer.start();
                                isPlaying = true;
                            } else {
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer = MediaPlayer.create(list.this, R.raw.up);
                                mediaPlayer.start();
                                isPlaying = true;
                            }
                        }
                    }
                })
                .setNeutralButton("선택", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 2)
                            Toast.makeText(getApplicationContext(),
                                    oItems[1], Toast.LENGTH_LONG).show();
                    }
                })
                .setCancelable(false)
                .show();


        diary_list = new ArrayList<String>();

        readDatafromFiles();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diary_list);
        lv = findViewById(R.id.list);
        lv.setAdapter(adapter);
        Intent intent = getIntent();
        final String ide = intent.getStringExtra("id");


        final Intent diary_intent = new Intent(this, diary.class);
        final Intent photo_intent = new Intent(this, photo.class);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String diary_content = parent.getItemAtPosition(position).toString();
                photo_intent.putExtra("path", position);
                diary_intent.putExtra("music", item);
                diary_intent.putExtra("diary_content", diary_content);
                diary_intent.putExtra("id", ide);
                startActivity(diary_intent);
            }
        });


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                final String s = (String) diary_list.get(position);
                alert.setTitle("삭제");

                alert
                        .setMessage("삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                delete_list(position);
                                diary_list.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                return true;
            }
        });
    }

    public void plus_button(View v) {
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("다이어리 추가");
        alert.setMessage("일기의 제목을 입력하세요.");


        final EditText name = new EditText(this);
        alert.setView(name);

        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String path = "/data/data/com.example.diary/files/" + id;
                diary_name = name.getText().toString();
                try {
                    File file = new File(path + "/" + "diarylist");
                    File diary = new File(path + "/" + diary_name + ".txt");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    if (!diary.exists()) {
                        file.createNewFile();
                    }
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String temp = "";
                    String st = null;
                    while ((st = br.readLine()) != null) {
                        temp += st + "\n";
                    }

                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    bw.write(temp + diary_name + "\n");
                    br.close();
                    bw.close();
                    name.setText("");
                    diary_list.add(diary_name);
                    adapter.notifyDataSetChanged();


                } catch (Exception e) {
                }
            }
        });
        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }

    public void readDatafromFiles() {
        Scanner scan;
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        File file = new File("/data/data/com.example.diary/files/" + id + "/diarylist");
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                scan = new Scanner(fis);
                Scanning(scan);
            } catch (Exception e) {
            }
        }
    }

    public void Scanning(Scanner scan) {
        while (scan.hasNextLine()) {
            String item = scan.nextLine();
            StringTokenizer st = new StringTokenizer(item);
            String name = st.nextToken(",");
            diary_list.add(name);
        }
    }

    public void delete_list(int position) {
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        File file = new File("/data/data/com.example.diary/files/" + id + "/diarylist");

        String newlist = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            //1. 삭제하고자 하는 position 이전까지는 이동하며 newlist에 저장

            String line;

            for (int i = 0; i < position; i++) {
                line = br.readLine(); //읽으며 이동
                newlist += (line + "\r\n");
            }

            //2. 삭제하고자 하는 데이터는 건너뛰기
            String delData = br.readLine();
            Log.d("mstag", "삭제되는 데이터 = " + delData);
            File garbage = new File("/data/data/com.example.diary/files/" + id + "/" + delData + ".txt");
            garbage.delete();

            //3. 삭제하고자 하는 position 이후부터 newlist에 저장
            while ((line = br.readLine()) != null) {
                newlist += (line + "\r\n");
            }

            //4. FileWriter를 이용해서 덮어쓰기
            FileWriter fw = new FileWriter("/data/data/com.example.diary/files/" + id + "/diarylist");
            fw.write(newlist);
            fw.close();
            br.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

