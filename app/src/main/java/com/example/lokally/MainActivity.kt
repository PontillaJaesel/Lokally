package com.example.lokally

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(this, Register::class.java))
            finish()
            return
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        drawerLayout = findViewById(R.id.drawer_layout)
        fab = findViewById(R.id.fab)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0) // get the header
        val navGreeting = headerView.findViewById<TextView>(R.id.textViewGreeting)
        val navEmail = headerView.findViewById<TextView>(R.id.textViewEmail)

        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()

        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val name = document.getString("fullName") ?: "User"
                        val email = document.getString("email") ?: "No Email"

                        // Create greeting with colored username
                        val greeting = "Hello, $name!"
                        val spannable = android.text.SpannableString(greeting)
                        val start = greeting.indexOf(name)
                        val end = start + name.length
                        spannable.setSpan(
                            android.text.style.ForegroundColorSpan(
                                resources.getColor(R.color.sdg_green, null) // username color
                            ),
                            start,
                            end,
                            android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                        navGreeting.text = spannable
                        navEmail.text = email
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to load profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }


        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_layout, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        bottomNavigationView.background = null
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.marketplace -> replaceFragment(MarketplaceFragment())
                R.id.messages -> replaceFragment(MessagesFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
            }
            true
        }

        fab.setOnClickListener {
            showBottomDialog()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun showBottomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheetlayout)

        val videoLayout = dialog.findViewById<LinearLayout>(R.id.layoutVideo)
        val shortsLayout = dialog.findViewById<LinearLayout>(R.id.layoutShorts)
        val liveLayout = dialog.findViewById<LinearLayout>(R.id.layoutLive)
        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)

        videoLayout.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity, "Upload a Video is clicked", Toast.LENGTH_SHORT)
                .show()
        }

        shortsLayout.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity, "Create a short is Clicked", Toast.LENGTH_SHORT)
                .show()
        }

        liveLayout.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity, "Go live is Clicked", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}