package yj.p.firebasecrud_test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import yj.p.firebasecrud_test.databinding.ActivitySignInBinding;
import yj.p.firebasecrud_test.models.User;

/**
 * 로그인 화면을 책임지는 엑티비티
 * 처음 로그인화면이나 로그아웃을 했을 때, 보여주는 엑티비티
 */

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private DatabaseReference mDatabase;            // 연동된 파이어베이스 상태 가져오기
    private FirebaseAuth mAuth;                     // 연동된 파이어베이스 Authentication 가져오기

    private ActivitySignInBinding binding;          // 바인딩을 통해서 해당 엑티비티의 xml에 있는 요소 가져오기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());       // 바인딩 된 xml inflate 시킴
        setContentView(binding.getRoot());                                  // setContentView를 통해서 바인딩된거 보여주기

        mDatabase = FirebaseDatabase.getInstance().getReference();          // 파이어베이스 인스턴스의 레퍼런스 참조
        mAuth = FirebaseAuth.getInstance();                                 // mAuth에 파이어베이스 Auth Instance 가져오기

        // Views
        setProgressBar(R.id.progressBar);                           // progressBar 보여주기 로그인 시 걸리는 시간 프로그래스바로 보여주기

        // Click listeners
        binding.buttonSignIn.setOnClickListener(this);          // signIn 일때 실행되는 클릭 리스너 지정
        binding.buttonSignUp.setOnClickListener(this);          // signUn 일때 실행되는 클릭 리스너 지정
    }

    @Override
    public void onStart() {
        super.onStart();

        // 자동 로그인 기능? -> 최근에 접속한 유저는 auth를 체크하지 않는다.
        // Check auth on Activity start             // 엑티비티가 실행될 때 auth를 체크한다.
        if (mAuth.getCurrentUser() != null) {       // auth의 최근의 값이 null이 아닌경우 -> 첫 로그인이 아닌경우
            onAuthSuccess(mAuth.getCurrentUser());  // onAuthSuccess -> mAuth.getCurrentUser()를 통해서 최근 이용한 유저 Auth 인증 성공
        }
    }

    /**
     * 로그인
     */
    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressBar();                                                                              // 로그인 시 프로그래스바 보여주기
        String email = binding.fieldEmail.getText().toString();                                         // email 입력한거 가져오기
        String password = binding.fieldPassword.getText().toString();                                   // password 입력한거 가져오기

        /**
         * FirebaseAuth는 인증 방법에 대해서 정의한다.
         * signInWithEmailAndPassword -> 이메일과 패스워드를 활성화 했기 때문에 이 메소드를 이용.
         */
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { // OnCompleteListener<AuthResult>
                    // AuthResult 타입의 데이터를 이용해서 완료된 사항을 함수로 정의
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hiedProgressBar();                                              // signIn이 완료 되었을때, 프로그래스바 숨김

                        // 만약에 task가 성공적이다 AuthResult가 성공이다?
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());              // onAuthSuccess를 통해서 성공을 알린다.
                        } else {
                            Toast.makeText(yj.p.firebasecrud_test.SignInActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * signUP 회원가입 버튼 눌렸을 때 이용되는 함수
     */
    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressBar();
        String email = binding.fieldEmail.getText().toString();
        String password = binding.fieldPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hiedProgressBar();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(yj.p.firebasecrud_test.SignInActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * onAuthSuccess를 통해서 User를 추가한다.
     * @param user
     */
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());             // 새로운 유저를 Auth에 추가한다. Uid, 유저이름, 이메일 올라감

        // Go to MainActivity
        startActivity(new Intent(yj.p.firebasecrud_test.SignInActivity.this, MainActivity.class));
        finish();
    }

    /**
     * 유저 이메일로부터 이름을 얻는다.
     * @param email
     * @return
     */
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    /**
     * 유효 데이터 체크
     * @return
     */
    private boolean validateForm() {
        boolean result = true;

        // 텍스트가 비었을 경우 경고 메시지 출력
        if (TextUtils.isEmpty(binding.fieldEmail.getText().toString())) {
            binding.fieldEmail.setError("Required");
            result = false;
        } else {
            binding.fieldEmail.setError(null);
        }
        // 패스워드가 비었을 경우 경고 메시지 출력
        if (TextUtils.isEmpty(binding.fieldPassword.getText().toString())) {
            binding.fieldPassword.setError("Required");
            result = false;
        } else {
            binding.fieldPassword.setError(null);
        }

        return result;
    }

    // [START basic_write]
    // 새로운 유저 추가
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);                          // 데이터베이스에 추가되는 유저 모델 저장

        mDatabase.child("users").child(userId).setValue(user);      // users라는 에트리뷰트로 userId의 값 user로 저장
        // users -> userid(user)
    }
    // [END basic_write]

    // 버튼 눌렸을 때 진행
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonSignIn) {
            signIn();
        } else if (i == R.id.buttonSignUp) {
            signUp();
        }
    }
}
