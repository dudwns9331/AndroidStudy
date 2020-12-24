package yj.atoko.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login_result.*
import org.w3c.dom.Text

class LoginResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_result)


        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")

        result.setText("""Email : $email
            |Password : $password
        """.trimMargin())

    }
}
