package com.example.lokally

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val signInButton = findViewById<MaterialButton>(R.id.btn_signup_choice)
        signInButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val registerText = findViewById<TextView>(R.id.loginNow)
        registerText.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}
