package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val  context:Context,val userlist: ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
      val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
         val CurrentUser =userlist[position]
        holder.textName.text = CurrentUser.name
        holder.itemView.setOnClickListener{
            val intent = Intent(context,chatActivity::class.java)

            intent.putExtra("name",CurrentUser.name)
            intent.putExtra("uid",CurrentUser.uid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
  val textName =itemView.findViewById<TextView>(R.id.txt_name)
    }
}