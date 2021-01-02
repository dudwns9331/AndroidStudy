package yj.p.firebasecrud_test;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import yj.p.firebasecrud_test.models.Post;
import yj.p.firebasecrud_test.models.User;

/**
 * 새로운 게시글을 만들 때 사용되는 엑티비티
 */
public class NewPostActivity extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    FloatingActionButton fabSubmitPost;             // 오른쪽 밑에 동그라미 체크 버튼
    EditText fieldTitle;                            // title 입력하는 창
    EditText fieldBody;                             // 밑에 게시물 내용 적는 창
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        fabSubmitPost = findViewById(R.id.fabSubmitPost);
        fieldTitle = findViewById(R.id.fieldTitle);
        fieldBody = findViewById(R.id.fieldBody);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        fabSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPost();
            }
        });
    }

    /**
     * 오른쪽 아래 동그라미 체크 버튼을 눌렀을 때 수행되는 함수
     */
    private void submitPost() {

        // 입력한 제목, 내용 가져옴 title, body
        final String title = fieldTitle.getText().toString();
        final String body = fieldBody.getText().toString();

        // [내용 있는지 없는지 검사 시작]
        if(TextUtils.isEmpty(title)) {
            fieldTitle.setError(REQUIRED);
            return;
        }

        if(TextUtils.isEmpty(body)) {
            fieldBody.setError(REQUIRED);
            return;
        }
        // [내용 있는지 없는지 검사 끝]

        setEditingEnabled(false);
        Toast.makeText(this, "Postring...", Toast.LENGTH_SHORT).show();

        // Uid를 가져온다.
        final String userId = getUid();

        /**
         * submitPost() 함수가 실행될 때마다 데이터베이스에 값 추가 하는 리스너
         * users 에트리뷰트의 자식 -> userId의 자식 -> 값추가
         */
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                // 현재 유저의 snapShot을 가져온다. -> 데이터베이스 상태 가져옴
                User user = datasnapshot.getValue(User.class);

                if(user == null) {
                    Log.e(TAG, "User " + userId + " is unexpectedly null");
                    Toast.makeText(NewPostActivity.this,
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 이 함수를 통해서 데이터베이스 내용 업데이트 시킨다. -> Posts -> User-Posts -> 내용추가
                    writeNewPost(userId, user.username, title, body);
                }

                setEditingEnabled(true);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                setEditingEnabled(true);
            }
        });

    }

    private void setEditingEnabled(boolean enabled) {
        fieldTitle.setEnabled(enabled);
        fieldBody.setEnabled(enabled);
        if(enabled)
            fabSubmitPost.show();
        else fabSubmitPost.hide();
    }

    /**
     * 새로운 게시글 작성
     * @param userId --> 현재 로그인 되어 있는 Auth에 등록된 UserId
     * @param username --> 로그인 하면서 작성했던 이메일 @ 앞부분에 들어간 유저이름
     * @param title --> 게시글의 제목
     * @param body --> 게시글의 내용
     */
    private void writeNewPost(String userId, String username, String title, String body) {

        String key = mDatabase.child("posts").push().getKey();      // posts라는 key 값을 추가한다.
        Post post = new Post(userId, username, title, body);        // post를 작성하면서 사용한 값들 데이터베이스 모델에 입력
        Map<String, Object> postValues = post.toMap();              // postValues로 저장, 초기화

        Map<String, Object> childUpdates = new HashMap<>();
        // /posts/ + key를 통해서 posts라는 에트리뷰트에 + 각자의 key를 자식으로 생성,
        // 생성한 key값에 나머지 postVaue를 자식으로 추가해준다. 여러 자식을 한번에 추가
        // Posts라는 키(스키마) 생성 -> posts의 하위에 key값으로 구분 -> 그 key값의 자식에 postValue 요소 추가
        childUpdates.put("/posts/" + key, postValues);

        // 위 과정 반복, 하지만 user-posts로 userId를 통해서 유저마다 post를 관리한다.
        childUpdates.put("/user-posts/" + userId + "/" +key, postValues);

        // childeren 추가된거 데이터베이스에서 업데이트
        mDatabase.updateChildren(childUpdates);
    }
}
