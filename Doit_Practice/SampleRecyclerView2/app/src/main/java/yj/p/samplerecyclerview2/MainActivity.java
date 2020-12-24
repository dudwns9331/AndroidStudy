package yj.p.samplerecyclerview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import yj.p.samplerecyclerview2.R;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PersonAdapter();

        adapter.addItem(new Person("안영준", "010-0000-0000"));
        adapter.addItem(new Person("안일준", "010-1111-1111"));
        adapter.addItem(new Person("안두준", "010-2222-2222"));
        adapter.addItem(new Person("안삼준", "010-3333-3333"));
        adapter.addItem(new Person("안사준", "010-4444-4444"));
        adapter.addItem(new Person("안오준", "010-5555-5555"));
        adapter.addItem(new Person("안육준", "010-6666-6666"));
        adapter.addItem(new Person("안칠준", "010-7777-7777"));
        adapter.addItem(new Person("안팔준", "010-8888-8888"));
        adapter.addItem(new Person("안구준", "010-9999-9999"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(PersonAdapter.ViewHolder holder, View view, int position) {

                Person item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨 : " + item.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}