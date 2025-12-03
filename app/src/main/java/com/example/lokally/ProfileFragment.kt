package com.example.lokally

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var profileImage: ImageView
    private lateinit var itemsSoldCount: TextView
    private lateinit var activeListingsCount: TextView
    private lateinit var ratingValue: TextView
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    // Use onViewCreated to safely access views inflated from the layout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileName = view.findViewById(R.id.profile_name)
        profileEmail = view.findViewById(R.id.profile_email)
        profileImage = view.findViewById(R.id.profile_image)
        itemsSoldCount = view.findViewById(R.id.items_sold_count)
        activeListingsCount = view.findViewById(R.id.active_listings_count)
        ratingValue = view.findViewById(R.id.rating_value)
        tabLayout = view.findViewById(R.id.tab_layout)

        loadUserProfile()

        // Sample data
        itemsSoldCount.text = "450"
        activeListingsCount.text = "12"
        ratingValue.text = "4.9/5"


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // handle tab selection
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun loadUserProfile() {
        val users = auth.currentUser

        if (users != null) {
            profileEmail.text = users.email ?: "Email Not Found"

            val userId = users.uid
            val db = FirebaseFirestore.getInstance()

            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {

                        val fullName = document.getString("fullName")
                        if (!fullName.isNullOrEmpty()) {
                            profileName.text = fullName
                        } else {
                            profileName.text = "No name found"
                        }

                    } else {
                        profileName.text = "User Profile Missing"
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }

        } else {
            Toast.makeText(requireContext(), "User not logged in.", Toast.LENGTH_LONG).show()
        }
    }

}
