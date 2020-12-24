package yj.p.challenge12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import yj.p.challenge12.MyService;
import yj.p.challenge12.R;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    String data2;

    @Override
    protected void onNewIntent(Intent intent) {
        data2 = intent.getStringExtra("data2");
        textView.setText(data2);
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.data);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("data", data);
                startService(intent);
            }
        });

    }
}