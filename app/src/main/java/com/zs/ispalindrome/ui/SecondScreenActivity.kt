package com.zs.ispalindrome.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.zs.ispalindrome.R
import com.zs.ispalindrome.databinding.ActivitySecondScreenBinding

class SecondScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondScreenBinding
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initState()

        binding.btnBackToFirstScreen.setOnClickListener{
            onBackPressed()
        }

        binding.btnToThirdScreen.setOnClickListener{
            val intent = Intent(this, ThirdScreenActivity::class.java)
            startActivity(intent)
        }


    }

    private fun initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = getColor(R.color.white)
            window.navigationBarColor = getColor(R.color.white)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        user = intent.getStringExtra("user").toString()
        binding.tvUser.text = user
    }

    fun setUser(username: String, email: String) {
        binding.tvUsernameSelected.text = username
        binding.tvEmailSelected.text = username
    }
}