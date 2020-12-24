package yj.p.challenge08;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_MENU_CODE = 8;

    EditText idText;
    EditText passwordText;
    Button login;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        idText.setText("");
        passwordText.setText("");

        if(resultCode == MenuActivity.LOGIN) {
            String info = data != null ? data.getStringExtra("data") : null;
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        }

        if(requestCode == REQUEST_MENU_CODE)
            Toast.makeText(this, "MenuActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idText = findViewById(R.id.idText);
        passwordText = findViewById(R.id.passwordText);
        login = findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idText.getText().toString().equals("") || passwordText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디와 패스워드를 올바르게 입력하세요",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivityForResult(intent, REQUEST_MENU_CODE);
                }
            }
        });
    }
}