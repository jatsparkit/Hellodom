package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var edtemail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth =FirebaseAuth.getInstance()

        edtemail =findViewById(R.id.edt_email)
        edtpassword =findViewById(R.id.edt_password)
        btnLogin =findViewById(R.id.btnLogin)
        btnSignUp =findViewById(R.id.btnSignup)

 btnSignUp.setOnClickListener {
     val intent = Intent(this,SignUp::class.java)
     finish()
     startActivity(intent)
 }

        btnLogin.setOnClickListener {
            val email =edtemail.text.toString()
            val password =edtpassword.text.toString()

            login(email,password);
        }

    }

    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for logging in user
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "user not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}