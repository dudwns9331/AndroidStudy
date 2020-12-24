package yj.p.challenge04;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText textViewinput;
    TextView textViewbyte;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewinput = findViewById(R.id.textView);
        textViewbyte = findViewById(R.id.textViewbyte);
        textViewinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = textViewinput.length();
                textViewbyte.setText(length + "/ 80바이트");
                if(length == 80) {
                    Toast.makeText(getApplicationContext(), "더이상 입력할 수 없습니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    public void SendButton(View view) {
        Toast.makeText(getApplicationContext(), textViewinput.getText(), Toast.LENGTH_LONG).show();
    }

    public void CloseButton(View view) {
        textViewinput.setText("");
        textViewbyte.setText("0 / 바이트");
    }

    public void ExitButton(View view) {
        onBackPressed();
    }


    private long time = 0;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }

    }
}