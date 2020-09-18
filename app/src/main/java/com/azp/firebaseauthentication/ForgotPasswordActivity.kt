package com.azp.firebaseauthentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_login.*

class ForgotPasswordActivity : AppCompatActivity() {

//    private var email: String? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btnForgot.setOnClickListener {
            sendEmail()
        }
    }

    fun sendEmail(){
        val email = forgot_email?.text.toString()

        mAuth!!.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val message = "Email sent."

                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this, "No user found", Toast.LENGTH_LONG).show()
                }

            }
    }
}
