package yj.p.fragmentexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = (MainFragment) getSupportFragmentManager().
                findFragmentById(R.id.mainFragment); // why? 왜 쓴거지 무슨 차이지 밑에거랑?
        menuFragment = new MenuFragment();
    }

    public void onFragmentChanged(int index) {
        if(index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    menuFragment).commit(); // container 에서 menuFragment 로 변경한다 commit() 으로 실행
        } else if(index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    mainFragment).commit();
        }
    }
}