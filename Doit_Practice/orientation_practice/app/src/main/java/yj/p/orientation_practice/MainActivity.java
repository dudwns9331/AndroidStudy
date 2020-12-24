package yj.p.orientation_practice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String name;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // savedInstanceState 메모리에 생성하면서 변수의 값들 자동 저장 번들 객체로 복원이 가능하다.
        setContentView(R.layout.activity_main);
        ShowToast("onCreate() 호출 됨.");

        editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                name = editText.getText().toString();
                ShowToast("입력된 값을 변수에 저장했습니다.");
            }

        });

        if(savedInstanceState != null) {
            name = savedInstanceState.getString("name");
            ShowToast("값을 복원했습니다 : " + name);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShowToast("onStart() 호출 됨.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShowToast("onStop() 호출 됨.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShowToast("onDestroy() 호출 됨.");
    }

    public void ShowToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}