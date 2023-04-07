package com.example.contact_permission_display

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ContactAdapter(val arraylist : ArrayList<Contact_Modal>,ctx: Context):
    RecyclerView.Adapter<ContactAdapter.viewHolder>() {
    var context : Context = ctx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list,parent,false)
        val viewHolder : viewHolder = viewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return  arraylist.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.Cname.text = arraylist[position].name
        holder.Cnumber.text = arraylist[position].number
        if (arraylist[position].image !=null) {
            holder.profilepic.setImageBitmap(arraylist[position].image)
        }
        else
            holder.profilepic.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_image))
    }




    class viewHolder(itemView : View):RecyclerView.ViewHolder(itemView){

        val Cname = itemView.findViewById<TextView>(R.id.contactname)
        val Cnumber = itemView.findViewById<TextView>(R.id.contactnumber)
        val profilepic = itemView.findViewById<ImageView>(R.id.imageview)
    }

}