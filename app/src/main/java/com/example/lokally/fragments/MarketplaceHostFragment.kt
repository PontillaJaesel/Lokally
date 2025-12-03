package com.example.lokally.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.lokally.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MarketplaceHostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout with Tabs and ViewPager
        return inflater.inflate(R.layout.fragment_marketplace_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        // Setup the Adapter
        // We use 'childFragmentManager' because we are inside a Fragment
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // Connect Tabs to ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "ITEMS" else "SERVICES"
        }.attach()
    }

    // Inner Adapter Class to handle the switching
    inner class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            // Position 0 = Items Tab
            // Position 1 = Services Tab
            return MarketplaceFragment.newInstance(isService = (position == 1))
        }
    }
}