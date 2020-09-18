package com.azp.firebaseauthentication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    private var email: String? = null
    private var password: String? = null

    private var mAuth: FirebaseAuth? = null
    private var mProgress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initLogin()
    }

    private fun initLogin() {
        mProgress = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        email = login_email.text.toString()
        password = login_password.text.toString()

        btnForgot.setOnClickListener {
            var intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        mProgress!!.setMessage("Login user....")
        mProgress!!.show()

        mAuth!!.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful){

                    mProgress!!.hide()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)

                } else{
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                }
            }
    }
}

