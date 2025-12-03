package com.example.lokally

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.example.lokally.databinding.FragmentHomeBinding
import com.example.lokally.data.HeaderCard
import com.example.lokally.data.GridItem
import com.example.lokally.adapters.HeaderCardAdapter
import com.example.lokally.adapters.GridItemAdapter

class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val purposeTitle = "SDG 12: Responsible Consumption"
    private val purposeContent = "Lokally is a community-driven marketplace that makes it easy to buy, sell, and exchange goods or services within your local area.\nWe promote sustainability, reuse, and support for local communities by empowering people to trade responsibly."
    private val goalsContent = """
        - Encourage responsible consumption by reducing waste and giving items a second life.
        - Support local communities with easy access to nearby goods and services.
        - Provide a safe, simple marketplace for students, residents, and local sellers.
        - Offer affordable alternatives to buying brand-new items.
        - Build a connected and self-sustaining local economy.
    """.trimIndent()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHeaderCards()
        setupGridItems()
        setupTabLayout()
    }
    
    private fun setupHeaderCards() {
        val headerCards = listOf(
            HeaderCard(
                title = "Hi, [User's Name]!",
                subtitle = "Your community marketplace starts here.",
                backgroundColor = R.color.accent_orange
            ),
            HeaderCard(
                title = purposeTitle,
                subtitle = "Lokally is a community-driven marketplace that makes it easy to buy, sell, and exchange goods or services within your local area.",
                backgroundColor = R.color.sdg_green
            )
        )

        val adapter = HeaderCardAdapter(headerCards)

        binding.rvSwipeableHeader.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
    }
    
    private fun setupGridItems() {
        val gridItems = listOf(
            GridItem("🛒", "Buy & Sell Items"),
            GridItem("🧰", "Find or Offer Local Services"),
            GridItem("💬", "Chat in Real Time"),
            GridItem("👤", "Manage Your Profile"),
            GridItem("🔎", "Explore Your Community’s Resources"),
            GridItem("---", "More")
        )
        
        val adapter = GridItemAdapter(gridItems)

        binding.rvMarketplaceGrid.apply {
            layoutManager = GridLayoutManager(context, 3)
            this.adapter = adapter
            // Disable scrolling as it's inside a NestedScrollView
            isNestedScrollingEnabled = false
        }
    }
    
    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        
        tabLayout.addTab(tabLayout.newTab().setText("Purpose"))
        tabLayout.addTab(tabLayout.newTab().setText("Goal"))
        
        updateTabContent(0)
        
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                updateTabContent(tab.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
    
    private fun updateTabContent(position: Int) {
        when (position) {
            0 -> { 
                binding.tvTabTitle.text = purposeTitle
                binding.tvTabContent.text = purposeContent
            }
            1 -> { 
                binding.tvTabTitle.text = "Our Goals"
                binding.tvTabContent.text = goalsContent
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}