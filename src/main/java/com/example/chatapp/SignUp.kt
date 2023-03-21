package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.health.UidHealthStats
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var edtname: EditText
    private lateinit var edtemail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdbref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth =FirebaseAuth.getInstance()

        edtname =findViewById(R.id.edt_name)
        edtemail =findViewById(R.id.edt_email)
        edtpassword =findViewById(R.id.edt_password)
        btnSignUp =findViewById(R.id.btnSignup)

        btnSignUp.setOnClickListener {
            val name =edtname.text.toString()
            val email =edtemail.text.toString()
            val password = edtpassword.text.toString()

            signup(name,email,password)
        }

    }
    private fun signup(name: String,email: String,password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   // code for jumping to home
                    addusertodatabase(name,email,mAuth.currentUser?.uid!!)

                    val intent = Intent(this@SignUp,MainActivity::class.java)

                   finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp, "Some error", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addusertodatabase(name: String,email: String,uid: String){
mdbref = FirebaseDatabase.getInstance().getReference()

        mdbref.child("user").child(uid).setValue(User(name,email,uid))
    }
}