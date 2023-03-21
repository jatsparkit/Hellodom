package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class chatActivity : AppCompatActivity() {


    private lateinit var chatrecyclerview: RecyclerView
    private lateinit var messagebox: EditText
    private lateinit var sendbutton: ImageView
    private lateinit var messageadapter: Messageadapter
    private lateinit var messagelist :ArrayList<Message>
      private lateinit var mdbref : DatabaseReference

var recieverroom : String? = null
    var senderroom : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val name = intent.getStringExtra("name")
        val recieveruid  = intent.getStringExtra("uid")

        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        mdbref = FirebaseDatabase.getInstance().getReference()
        senderroom = recieveruid + senderuid
        recieverroom = senderuid +  recieveruid

        supportActionBar?.title = name

        chatrecyclerview = findViewById(R.id.chatrecyclerview)
        messagebox = findViewById(R.id.message)
        sendbutton = findViewById(R.id.sentbutton)
        messagelist = ArrayList()
        messageadapter =Messageadapter(this,messagelist)

        chatrecyclerview.layoutManager = LinearLayoutManager(this)
        chatrecyclerview.adapter = messageadapter

        mdbref.child("chats").child(senderroom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messagelist.clear()

                   for (postsnapshot in snapshot.children){
                       val message = postsnapshot.getValue(Message::class.java)
                       messagelist.add(message!!)
                   }
                    messageadapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        sendbutton.setOnClickListener{
            val message = messagebox.text.toString()
            val messageobject = Message(message,senderuid)

            mdbref.child("chats").child(senderroom!!).child("messages").push()
                .setValue(messageobject).addOnSuccessListener {
                    mdbref.child("chats").child(senderroom!!).child("messages").push()
                        .setValue(messageobject)
                }
            messagebox.setText("")
        }
    }
}