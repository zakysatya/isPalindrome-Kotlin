package com.zs.ispalindrome.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.zs.ispalindrome.R
import com.zs.ispalindrome.databinding.ActivityFirstScreenBinding

class FirstScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        binding.etName.setText(sharedPreferences.getString("user", ""))
        initState()

        binding.btnCheck.setOnClickListener {
            val user = binding.etName.text.toString()
            val palindrome = binding.etPalindrome.text.toString()

            if (user.isEmpty()) {
                // Snackbar.make(binding.root, "Username cannot be empty", Snackbar.LENGTH_SHORT).show()
                binding.etName.error = "Username can't empty"
                return@setOnClickListener
            } else if (palindrome.isEmpty()) {
                binding.etPalindrome.error = "Palindrome cannot be empty"
                return@setOnClickListener
            } else if (palindrome.contains("[^a-zA-Z0-9 ]".toRegex())) {
                binding.etPalindrome.error = "Palindrome contain symbols"
                return@setOnClickListener
            }

            val isPalindrome = isPalindrome(palindrome)
            val message =
                if (isPalindrome) "$palindrome isPalindrome"
                else "$palindrome is not palindrome"

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Result")
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

        }

        binding.btnNext.setOnClickListener {
            val user = binding.etName.text.toString()
            if (user.isEmpty()) {
                binding.etName.error = "Username can't empty"
                return@setOnClickListener
            }

            val editor = sharedPreferences.edit()
            editor.putString("user", user).apply()

            val intent = Intent(this, SecondScreenActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

    }

    private fun isPalindrome(str: String): Boolean {
        val cleanString = str.replace("[^a-zA-Z0-9]".toRegex(), "").lowercase()
        return cleanString == cleanString.reversed()
    }

    private fun initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.navigationBarColor = getColor(R.color.primary_color)
        }

    }

    fun setPhoto() {

    }
}