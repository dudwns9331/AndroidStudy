package yj.p.challenge08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class itemActivity extends AppCompatActivity {

    Button go_login, go_menu;
    Intent intent, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        go_login = findViewById(R.id.button6);
        go_menu = findViewById(R.id.button5);

        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                data = new Intent();
                data.putExtra("data", "item");
                setResult(MenuActivity.LOGIN, data);
                finish();
            }
        });

        go_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(MenuActivity.ITEM_CODE);
                finish();
            }
        });
    }
}