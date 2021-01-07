package yj.p.kotlinclone1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {

    var auth : FirebaseAuth? = null
    var googleSingInClient : GoogleSignInClient? = null

    var email_editText : EditText? = null
    var password_editText : EditText? = null
    var email_login_button : Button? = null
    var google_sign_in_button : Button? = null

    val GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email_editText = findViewById(R.id.email_editText)
        password_editText = findViewById(R.id.password_editText)
        email_login_button = findViewById(R.id.email_login_button)
        google_sign_in_button = findViewById(R.id.google_sign_in_button)

        auth = FirebaseAuth.getInstance()
        email_login_button?.setOnClickListener {
            signinAndSignup()
        }

        google_sign_in_button?.setOnClickListener {
            // Frist Step!
            googleLogin()
        }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSingInClient = GoogleSignIn.getClient(this, gso)
    }

    fun googleLogin() {
        var signInIntent = googleSingInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_LOGIN_CODE) {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result!!.isSuccess) {
                var account = result.signInAccount
                // Second Step!
                firebaseAuthwithGoogle(account)
            }
        }
    }

    fun firebaseAuthwithGoogle(account : GoogleSignInAccount?) {
        var credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    //Login
                    moveMainPage(task.result?.user)
                } else {
                    // Show the error message
                    Toast.makeText(this, task.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
    }

    fun signinAndSignup() {
        auth?.createUserWithEmailAndPassword(email_editText?.text.toString(),
            password_editText?.text.toString())?.addOnCompleteListener {
            task ->
            if (task.isSuccessful) {
                // Creating a user account
                moveMainPage(task.result?.user)
            } else if (task.exception?.message.isNullOrEmpty()) {
                // Show the error message
                Toast.makeText(this, task.exception?.message,Toast.LENGTH_LONG).show()
            } else {
                // Login if you have account
                signinEmail()
            }
        }
    }

    fun signinEmail() {
        auth?.createUserWithEmailAndPassword(email_editText?.text.toString(),
            password_editText?.text.toString())?.addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                //Login
                moveMainPage(task.result?.user)
            } else {
                // Show the error message
                Toast.makeText(this, task.exception?.message,Toast.LENGTH_LONG).show()
            }
        }
    }

    fun moveMainPage(user:FirebaseUser?) {
        if(user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}