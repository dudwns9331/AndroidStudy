package yj.p.challenge10;
// 죽는줄 알았다.
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentCallback {

    Fragment fragment_login;
    Fragment fragment_maps;
    Fragment fragment_pager;

    Toolbar toolbar;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fragment_login = new customer_info();
        fragment_maps = new MapsFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_login).commit();

        //하단 메뉴 생성
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.login:
                                Toast.makeText(MainActivity.this, "로그인", Toast.LENGTH_SHORT).show();
                                getSupportFragmentManager().beginTransaction().
                                        replace(R.id.container, fragment_login).commit();
                                return true;
                            case R.id.select:
                                Toast.makeText(MainActivity.this, "현재 작업 장소", Toast.LENGTH_SHORT).show();
                                getSupportFragmentManager().beginTransaction().
                                        replace(R.id.container, fragment_maps).commit();
                                return true;
                            case R.id.cat:
                                Toast.makeText(MainActivity.this, "귀여운 cat", Toast.LENGTH_SHORT).show();
                                fragment_pager = new pager();
                                getSupportFragmentManager().beginTransaction().
                                        replace(R.id.container, fragment_pager).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.loginMenu) {
            onFragmentSelected(0, null);
        } else if (id == R.id.placeMenu) {
            onFragmentSelected(1, null);
        } else if (id == R.id.catMenu) {
            onFragmentSelected(2, null);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentSelected(int position, Bundle bundle) {

        Fragment curFragment = null;

        if (position == 0) {
            curFragment = fragment_login;
        } else if (position == 1) {
            curFragment = fragment_maps;
        } else if (position == 2) {
            curFragment = new pager();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // 메뉴 아이템의 아이디 가져옴.
        int curId = item.getItemId();
        if (curId == R.id.finish) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
