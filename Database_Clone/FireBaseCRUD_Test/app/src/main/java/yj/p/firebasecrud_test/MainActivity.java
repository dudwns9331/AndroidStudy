package yj.p.firebasecrud_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import yj.p.firebasecrud_test.fragment.MyPostFragment;
import yj.p.firebasecrud_test.fragment.MyTopPostsFragment;
import yj.p.firebasecrud_test.fragment.RecentPostsFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /**
     * 로그인 후에 뜨는 화면
     * 오른쪽 위 메뉴 선택버튼
     * tab으로 프래그먼트 구현 -> Recent, MyPost, My Top Posts 세 개의 탭 존재
     * 오른쪽 아래 FloatingActcionButton을 통해서 NewPostActivity로 연계 가능
     * 생성된 cardView로
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager container = findViewById(R.id.container);                 // 뷰페이저 이용해서 스와이프
        TabLayout tabs = findViewById(R.id.tabs);                           // 뷰페이저에 적용되는 탭
        FloatingActionButton fabNewPost = findViewById(R.id.fabNewPost);    // 오른쪽 밑에 뜨는 + 버튼


        /**
         * 프래그먼트 페이저 어댑터 -> 프래그먼트 페이저를 이용하면서 사용되는 기능 정의
         * 프래그먼트는 보여주기만 한다. 프래그먼트를 띄우기 위해서 적용되는 것들은 어댑터로 정의
         */
        FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            // 프래그먼트 상태 저장 및 복원 실시 여러개의 프래그먼트 있을 때 사용한다.
            private final Fragment[] mFragments = new Fragment[]{
                    new RecentPostsFragment(),
                    new MyPostFragment(),
                    new MyTopPostsFragment(),
            };

            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_recent),
                    getString(R.string.heading_my_posts),
                    getString(R.string.heading_my_top_posts)
            };

            @Override
            public Fragment getItem(int position) { return mFragments[position]; }

            @Override
            public int getCount() { return mFragments.length; }

            @Override
            public CharSequence getPageTitle(int position) { return mFragmentNames[position]; }

        };

        // 컨테이너에 어댑터 적용
        container.setAdapter(mPagerAdapter);
        // 탭에 뷰페이저 적용
        tabs.setupWithViewPager(container);

        // 오른쪽 하단에 + 버튼 눌렸을 때 실행되는 함수
        fabNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 포스트 작성하는 NewPostActivity 실행한다.
                startActivity(new Intent(MainActivity.this, NewPostActivity.class));
            }
        });
    }

    // 오른쪽 위 메뉴 버튼을 눌렀을때 나오는 메뉴 인플레이트 해주는 함수
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 오른쪽 위 메뉴 버튼 눌렀을때 나오는 메뉴에 대해서 선택된 값을 처리해주는 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();                           // 메뉴의 아이템을 가져옴
        if(i == R.id.action_logout) {                       // 메뉴의 아이템이 로그아웃이면
            FirebaseAuth.getInstance().signOut();           // FirebaseAuth의 signOut을 실행한다.
            startActivity(new Intent(this, SignInActivity.class));      // 로그아웃 후 나오는 화면인 SignInActivity를 실행
            finish();                                       // 엑티비티가 겹치지 않게 메인 엑티비티 종료
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}