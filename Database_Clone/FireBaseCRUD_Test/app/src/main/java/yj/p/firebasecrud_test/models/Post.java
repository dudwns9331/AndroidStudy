package yj.p.firebasecrud_test.models;

import java.util.HashMap;
import java.util.Map;

/**
 * 파이어베이스에 올라가는 Post 객체 모델
 */
public class Post {

    public String uid;                                                  // UID
    public String author;                                               // Author
    public String title;                                                // 글 제목
    public String body;                                                 // 글 내용
    public int starCount = 0;                                           // 글 옆에 나타나는 별표
    public Map<String, Boolean> stars = new HashMap<>();                // 글에 따라 표시되는 별표?

    public Post() {

    }

    public Post(String uid, String author, String title, String body) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    /**
     * Map을 통해서 각 변수들을 저장한다.
     * @return Post 모델(데이터베이스 스키마) 에 들어가는 정보들
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
}
