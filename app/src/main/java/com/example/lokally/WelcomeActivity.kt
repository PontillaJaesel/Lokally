package com.example.lokally

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)
        val btnSignUp = findViewById<MaterialButton>(R.id.btn_signup_choice)
        val btnSignIn = findViewById<MaterialButton>(R.id.btn_signin_choice)

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        btnSignIn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}