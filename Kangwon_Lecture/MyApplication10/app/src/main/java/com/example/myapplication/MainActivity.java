package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String[] Boards_contents =  {
            "반갑습니다~~", "첫 글 써봅니다!!\n잘 부탁드릴게요!!!",
            "드라마 추천좀", "요즘 드라마 뭐가 제일 재밌어요??\n보던거 다 끝나서 볼게 없어요 ㅠㅠ\n고수님들 잼난거 추천 부탁합니다...",
            "이거 무슨 게임이에요??", "저거 스트리머분들이 많이 하던데...\n이름이 뭐에요??\n오랜만에 재미있는 게임 찾은 거 같아서 해보고 싶은데",
            "안녕하세요 18학번 컴퓨터과학과 안영준 입니다.", "모바일프로그래밍 정말재밌어요\n근데 너무 어려워요."
    };

    private Map<String, String> BoardMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList boardList = new ArrayList<String >();
        BoardMap = new HashMap<String, String>();

        for(int i = 0; i < this.Boards_contents.length; i+=2) {
            BoardMap.put(Boards_contents[i], Boards_contents[i+1]);
            boardList.add(Boards_contents[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,boardList);
        ListView lv = findViewById(R.id.board_list);
        lv.setAdapter(adapter);
        final Intent showBoard_intent = new Intent(this, ShowBoardText.class);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String content = BoardMap.get(item);

                Bundle bundle = new Bundle();
                bundle.putString("title", item);
                bundle.putString("content", content);

                showBoard_intent.putExtras(bundle);

                startActivity(showBoard_intent);
            }
    });
    }
}
