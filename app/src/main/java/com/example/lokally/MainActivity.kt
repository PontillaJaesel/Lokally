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
import com.example.lokally.fragments.MarketplaceHostFragment
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
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
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

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Assuming HomeFragment exists
                    // replaceFragment(HomeFragment())
                    drawerLayout.closeDrawers()
                    true
                }
                // R.id.nav_theme case removed
                R.id.nav_logout -> {
                    drawerLayout.closeDrawers()

                    firebaseAuth.signOut()

                    val intent = Intent(this, WelcomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                    startActivity(intent)
                    finish()
                    true
                }

                else -> {
                    drawerLayout.closeDrawers()
                    true
                }
            }
        }

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

        // Sets the Toolbar to act as the ActionBar for this Activity window.
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

        // Theme DrawerListener removed

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_layout, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        bottomNavigationView.background = null
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                 R.id.home -> replaceFragment(HomeFragment())
                 R.id.marketplace -> replaceFragment(MarketplaceHostFragment())
                 R.id.messages -> replaceFragment(MessagesFragment())
                 R.id.profile -> replaceFragment(ProfileFragment())
                else -> true
            }
            true
        }

        fab.setOnClickListener {
            showBottomDialog()
        }
    }

    // Theme toggling functions removed

    // =========================================================================
    // MENU IMPLEMENTATION START
    // =========================================================================

    // 1. INFLATE: Load the icons from 'menu_toolbar.xml' into the top bar
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    // 2. CLICK: Handle what happens when the user taps the Cart
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                // Feedback for the user
                Toast.makeText(this, "Opening Cart...", Toast.LENGTH_SHORT).show()
                // TODO: startActivity(Intent(this, CartActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // =========================================================================
    // MENU IMPLEMENTATION END
    // =========================================================================

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun showBottomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // Make sure this matches the filename of the XML I gave you
        dialog.setContentView(R.layout.bottomsheetlayout)

        // 1. Setup Item Listing Click
        val layoutItemListing = dialog.findViewById<LinearLayout>(R.id.layoutItemListing)
        layoutItemListing.setOnClickListener {
            dialog.dismiss()
            // Navigate to your "Add Item" screen
            // Example: startActivity(Intent(this, AddItemActivity::class.java))
            Toast.makeText(this, "Create Item Listing Clicked", Toast.LENGTH_SHORT).show()
        }

        // 2. Setup Service Listing Click
        val layoutServiceListing = dialog.findViewById<LinearLayout>(R.id.layoutServiceListing)
        layoutServiceListing.setOnClickListener {
            dialog.dismiss()
            // Navigate to your "Add Service" screen
            // Example: startActivity(Intent(this, AddServiceActivity::class.java))
            Toast.makeText(this, "Create Service Listing Clicked", Toast.LENGTH_SHORT).show()
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