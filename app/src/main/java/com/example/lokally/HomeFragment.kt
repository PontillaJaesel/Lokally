package com.example.lokally

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokally.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchUserName()
        setupCategoryLists()
    }

    private fun fetchUserName() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val fullName = document?.getString("fullName") ?: "Neighbor"
                val firstName = fullName.split(" ").firstOrNull() ?: fullName
                binding.tvGreetingName.text = "Heya, $firstName!"
            }
            .addOnFailureListener {
                binding.tvGreetingName.text = "Heya, Neighbor!"
            }
    }

    private fun setupCategoryLists() {
        // --- 1. ITEMS LIST ---
        val itemCategories = listOf(
            CategoryItem("Clothing", R.drawable.ic_shopping_cart), // Using your existing cart icon
            CategoryItem("Furniture", R.drawable.outline_family_home_24),
            // FIX: Changed from 'outline_' to 'baseline_' to match your file
            CategoryItem("Electronics", R.drawable.baseline_cast_connected_24),
            CategoryItem("Stationery", R.drawable.outline_credit_card_24),
            CategoryItem("Books", R.drawable.outline_clear_all_24),
            CategoryItem("Handmade Crafts", R.drawable.outline_lightbulb_24)
        )

        val itemsAdapter = HorizontalCategoryAdapter(itemCategories)
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = itemsAdapter
        }

        // --- 2. SERVICES LIST ---
        val serviceCategories = listOf(
            CategoryItem("Home Services", R.drawable.outline_family_home_24),
            CategoryItem("Health & Care", R.drawable.outline_local_fire_department_24),
            CategoryItem("Creative Media", R.drawable.outline_music_video_24),
            CategoryItem("Academics", R.drawable.baseline_info_24),
            CategoryItem("Technology", R.drawable.outline_display_settings_24)
        )

        val servicesAdapter = HorizontalCategoryAdapter(serviceCategories)
        binding.rvServices.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = servicesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // --- Data Class ---
    data class CategoryItem(val name: String, val iconRes: Int)

    // --- Reusable Horizontal Adapter ---
    inner class HorizontalCategoryAdapter(private val list: List<CategoryItem>) :
        RecyclerView.Adapter<HorizontalCategoryAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView = itemView.findViewById(R.id.tvName)
            val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_horizontal, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = list[position]
            holder.tvName.text = item.name.uppercase()
            holder.ivIcon.setImageResource(item.iconRes)
        }

        override fun getItemCount() = list.size
    }
}