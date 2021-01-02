package yj.p.firebasecrud_test;

import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * LoginActivity에 쓰인다.
 * 프로그래스바 보여주고 사라지게 해줌
 * getUid() 메소드를 통해서 -> 유저의 id를 가져옴 해쉬값
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    public void setProgressBar(int resId) { mProgressBar = findViewById(resId);}


    // 프로그래스바 보여줌
    public void showProgressBar() {
        if(mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    // 프로그래스바 숨김
    public void hiedProgressBar() {
        if(mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    public String getUid() { return FirebaseAuth.getInstance().getCurrentUser().getUid(); }
}
