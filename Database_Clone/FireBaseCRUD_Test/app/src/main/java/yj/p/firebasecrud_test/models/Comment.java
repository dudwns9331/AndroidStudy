package yj.p.firebasecrud_test.models;

/**
 * 파이어베이스에 올라가는 comment내용 -> 데이터베이스 스키마 comment
 */
public class Comment {

    public String uid;          // 외래키(기본키?)
    public String author;       // 외래키(기본키?)
    public String text;         // text : comment의 내용

    public Comment() {

    }

    public Comment(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }
}
