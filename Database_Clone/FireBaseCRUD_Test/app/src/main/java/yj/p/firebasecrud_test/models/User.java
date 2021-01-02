package yj.p.firebasecrud_test.models;


/**
 * 유저 데이터베이스 모델
 * 파이어베이스에 올라가는 객체
 *
 * 파이어베이스 제공 Authentication 기능 사용 이메일/비밀번호
 * 데이터베이스 스키마 User에 들어가는 내용
 */
public class User {

    public String username;             // 사용자 이름
    public String email;                // 사용자 이메일 -> Authentication에 등록되는 이메일

    public User() {

    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
