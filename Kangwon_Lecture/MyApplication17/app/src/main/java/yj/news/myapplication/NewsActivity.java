package yj.news.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] mDataset = {"1", "2"};

        RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        queue = Volley.newRequestQueue(this);
        getNews();


        //1. 화면이 로딩 -> 뉴스 정보를 받아온다
        //2. 정보 -> 어댑터 넘겨준다
        //3. 어댑터 -> 셋팅
    }

    public void getNews() {
        // Instantiate the RequestQueue. 네트워크 통신을 하기 위해서 큐를 사용한다.
        String url ="http://newsapi.org/v2/top-headlines?country=kr&apiKey=c42ec5e37b4445d1a9d538a1104dba72"; // 이 주소로 volly가 접속을 하게 된다.

        // Request a string response from the provided URL. 요청을 하면 문자열 형태의 반응
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,            // get post 방식중에 get방식을 사용한다.
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {                               // 모든 스트링 값이 response로 들어가게 된다.

                     //   Log.d("NEWS", response);

                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray arrayArticles = jsonObj.getJSONArray("articles");

                            //response -> NewsData Class 분류
                            List<NewsData> news = new ArrayList<>();

                          for(int i = 0, j = arrayArticles.length(); i< j; i++) {
                              JSONObject obj = arrayArticles.getJSONObject(i);

                              Log.d("NEWS", obj.toString());

                              NewsData newsData = new NewsData();
                              newsData.setTitle(obj.getString("title"));         // get 을 쓰는 이유는 value값을 가져오기 위함.
                              newsData.setUrlToImage(obj.getString("urlToImage"));
                              newsData.setContent(obj.getString("content"));
                              news.add(newsData);
                          }

                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(news, NewsActivity.this);            // mDataset의 값들을 가져와서 보여주게 된다. 이 값들이 어뎁터로 들어가게 된다. 만약 오류가 없다면.
                            recyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue. 큐에다가 뉴스를 넣게 된다.
        queue.add(stringRequest);
    }
}
