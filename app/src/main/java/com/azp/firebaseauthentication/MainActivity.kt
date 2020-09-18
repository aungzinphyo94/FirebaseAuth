package com.azp.firebaseauthentication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    private var mDatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null

    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null
    private var mProgress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()
    }

    private fun initFirebase() {
        mProgress = ProgressDialog(this)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")

        mAuth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            firstName = etfirstName.text.toString()
            lastName = etlastName.text.toString()
            email = etemail.text.toString()
            password = etpassword.text.toString()

            createAccount()
        }
    }

    private fun createAccount() {
        mProgress!!.setMessage("Register user.....")
        mProgress!!.show()

        mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success signup", Toast.LENGTH_LONG).show()

                    val userId = mAuth!!.currentUser!!.uid

                    verifyEmail()

                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("firstName").setValue(firstName)
                    currentUserDb.child("lastName").setValue(lastName)

                    updateUI()

                } else {
                    Toast.makeText(this, "Fail signup", Toast.LENGTH_LONG).show()

                }
            }
    }

    private fun updateUI(){
        val intent = Intent(this,
            LoginActivity::class.java)
        startActivity(intent)
    }

    private fun verifyEmail(){
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Verification successful",Toast.LENGTH_LONG).show()
                }
            }
    }

}
