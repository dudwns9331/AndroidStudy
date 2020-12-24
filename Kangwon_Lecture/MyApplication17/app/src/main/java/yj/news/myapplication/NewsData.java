package yj.news.myapplication;

import java.io.Serializable;

public class NewsData implements Serializable { // 직렬화 데이터가 많을 때 정렬하여 데이터 구조를 보내주는 역할

    private String title;
    private String urlToImage;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
