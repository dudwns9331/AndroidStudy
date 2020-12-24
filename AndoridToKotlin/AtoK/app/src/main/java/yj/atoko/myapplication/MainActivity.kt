package yj.atoko.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    // 정해진 아이디 비밀번호
    var emailOK = "123@gmail.com"
    var passwordOk ="1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // 입력되는 이메일값과 비밀번호
        var inputEmail = ""
        var inputPassword = ""

        // 아이디를 123@gmail.com / 1234
        // 클릭을 감지
        // 다음 엑티비티로 아이디와 비밀번호를 넘긴다.

        //addTextChangedListener를 사용할 때 클릭리스너 TextWatcher 사용하므로 유의한다.

        //코틀린은 override를 통해서 메소드를 덮어쓰기 가능하다.

        //코틀린에서는 setClickable이라는 메소드는 존재하지만, class의 isClickable에서 모두 해결가능한것 같다.


        RelativeLayout_Login.isClickable = false



        // 이메일 입력하는 editText
        TextInputEditText_Email.addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Email", "${s.toString()}")

                if(s != null) {
                    inputEmail = s.toString()
                    // if else문을 간략하게 줄여서 저장이 가능하다.
                    RelativeLayout_Login.isClickable = (inputEmail == emailOK && inputPassword == passwordOk)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        // 비밀번호 입력하는 editText
        TextInputEditText_password.addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            // 텍스트가 변할 때,
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Password", "${s.toString()}")
                if(s != null) {
                    inputPassword= s.toString()
                    RelativeLayout_Login.isClickable = (inputEmail == emailOK && inputPassword == passwordOk)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


        // 로그인 Layout 레이아웃 자체를 눌렀을때 작동되는 리스너
        RelativeLayout_Login.setOnClickListener() {

            val intent = Intent(this,LoginResultActivity::class.java)

            val email = TextInputEditText_Email.text.toString()
            val password = TextInputEditText_password.text.toString()


            intent.putExtra("email", email)
            intent.putExtra("password", password)

            Toast.makeText(this,"$email $password",Toast.LENGTH_LONG).show()

            startActivity(intent)
        }
    }
}