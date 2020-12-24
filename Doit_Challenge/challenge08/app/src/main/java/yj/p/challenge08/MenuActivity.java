package yj.p.challenge08;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    Button customer, sales, item;
    Intent intent;

    public static final int CUSTOMER_CODE = 1;
    public static final int SALES_CODE = 2;
    public static final int ITEM_CODE = 3;

    public static final int LOGIN = 4;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == MenuActivity.SALES_CODE)
            Toast.makeText(this, "SalesActivity", Toast.LENGTH_SHORT).show();

        if(resultCode == MenuActivity.ITEM_CODE)
            Toast.makeText(this, "ItemActivity", Toast.LENGTH_SHORT).show();

        if(resultCode == MenuActivity.CUSTOMER_CODE) {
            Toast.makeText(this, "CustomerActivity", Toast.LENGTH_SHORT).show();
        }

        if(resultCode == LOGIN) {
            setResult(LOGIN, data);
            finish();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        customer = findViewById(R.id.button3);
        sales = findViewById(R.id.button2);
        item = findViewById(R.id.button4);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), customerActivity.class);
                startActivityForResult(intent,CUSTOMER_CODE);
            }
        });

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), salesActivity.class);
                startActivityForResult(intent, SALES_CODE);
            }
        });

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), itemActivity.class);
                startActivityForResult(intent, ITEM_CODE);
            }
        });
    }

}