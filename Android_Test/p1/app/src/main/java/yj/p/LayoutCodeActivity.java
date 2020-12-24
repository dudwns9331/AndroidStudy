package yj.p;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class LayoutCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this); // 리니어 레이아웃 설정해주기 이름 mainLayout
        mainLayout.setOrientation(LinearLayout.VERTICAL);        // 레이아웃 오리엔테이션 설정해주기 -> 수직

        LinearLayout.LayoutParams params =                         // 레이아웃 파라미터 지정해주기 뷰이기 때문에 무조건 파라미터는 두 개가 필요하다.
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,     // match
                        LinearLayout.LayoutParams.WRAP_CONTENT      // wrap
                );

        Button button1 = new Button(this);          // button 만들어주기
        button1.setText("Button1");                         // 텍스트 지정
        button1.setLayoutParams(params);                    // 메인 레이아웃에 뷰 추가(버튼)
        mainLayout.addView(button1);

        setContentView(mainLayout);                         // 새로 만든 레이아웃을 화면에 설정하기

    }
}
