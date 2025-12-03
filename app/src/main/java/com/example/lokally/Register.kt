package com.example.lokally

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {

    private lateinit var editTextUsername: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextConfirmPassword: TextInputEditText
    private lateinit var editTextMobile: TextInputEditText
    private lateinit var buttonReg: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var progressBar: ProgressBar
    private lateinit var loginNow: TextView
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialize views
        editTextUsername = findViewById(R.id.username)
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        editTextConfirmPassword = findViewById(R.id.confirm_password)
        editTextMobile = findViewById(R.id.mobileNumber)
        buttonReg = findViewById(R.id.btn_register)
        progressBar = findViewById(R.id.progressBar)
        loginNow = findViewById(R.id.loginNow)
        backButton = findViewById(R.id.back_button)

        loginNow.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        buttonReg.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = editTextUsername.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString()
        val confirmPassword = editTextConfirmPassword.text.toString()
        val mobile = editTextMobile.text.toString().trim()

        // Validate input
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
            TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) ||
            TextUtils.isEmpty(mobile)
        ) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.visibility = View.VISIBLE

        // Create Auth user
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user?.uid

                    if (userId != null) {
                        // Save profile to Firestore
                        val userData = hashMapOf(
                            "fullName" to username,
                            "email" to email,
                            "mobileNumber" to mobile,
                            "createdAt" to Timestamp.now()
                        )

                        db.collection("users").document(userId)
                            .set(userData)
                            .addOnSuccessListener {
                                progressBar.visibility = View.GONE
                                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, Login::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                progressBar.visibility = View.GONE
                                Toast.makeText(this, "Account created but failed to save profile: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                    } else {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "Account created but user ID is missing.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
