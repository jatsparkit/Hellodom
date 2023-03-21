package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class Messageadapter(val context: Context,val messagelist:ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIEVE = 1;
    val ITEM_SENT = 2;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            // inflate recieve
            val view: View = LayoutInflater.from(context).inflate(R.layout.recieve,parent,false)
            return Recieveviewholder(view)
        }

        else{
            // inflate sent
            val view: View =LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return Sentviewholder(view)
        }
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmessage = messagelist[position]
        if (holder.javaClass == Sentviewholder::class.java) {
            // do the stuff for sent view holder

            val viewHolder = holder as Sentviewholder
            holder.sentmessage.text = currentmessage.message
        } else {
            // do stuff for Recieve
            val viewHolder = holder as Recieveviewholder
            holder.recievemessage.text = currentmessage.message

        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentmessage = messagelist[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderid)) {
           return  ITEM_SENT
        }
        else{
            return ITEM_RECIEVE
        }

    }

    override fun getItemCount(): Int {
      return  messagelist.size
    }
    class Sentviewholder(itemView: View): RecyclerView.ViewHolder(itemView){
  val sentmessage = itemView.findViewById<TextView>(R.id.txtsent)
    }
    class Recieveviewholder(itemView: View): RecyclerView.ViewHolder(itemView){
        val recievemessage = itemView.findViewById<TextView>(R.id.txtrecieve)
    }
}