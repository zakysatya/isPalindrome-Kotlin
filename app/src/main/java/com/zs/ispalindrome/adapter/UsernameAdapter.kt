package com.zs.ispalindrome.adapter

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zs.ispalindrome.R
import com.zs.ispalindrome.model.User

class UsernameAdapter(private val usernames: ArrayList<User>,private val sharedPreferences: SharedPreferences): RecyclerView.Adapter<UsernameAdapter.UsernameViewHolder>() {
    class UsernameViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tv_usernameCombined)
        val emailTextView: TextView = itemView.findViewById(R.id.tv_email)
        val avatarImageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onBindViewHolder(holder: UsernameViewHolder, position: Int) {
        val user = usernames[position]

        val username = "${user.firstName} ${user.lastName}"
        val email = user.email
        holder.nameTextView.text = username
        holder.emailTextView.text = email

        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .fitCenter()
            .into(holder.avatarImageView)

        holder.itemView.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("username", username)
            editor.putString("email", email)
            editor.putString("avatar", user.avatar)
            editor.apply()

            Toast.makeText(it.context, "Your Username: $username", Toast.LENGTH_SHORT).show()
            (it.context as Activity).finish()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsernameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UsernameViewHolder(view)
    }

    override fun getItemCount(): Int {
        return usernames.size
    }
}