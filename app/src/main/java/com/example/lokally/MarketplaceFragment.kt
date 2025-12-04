package com.example.lokally.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokally.R
import com.example.lokally.adapter.MarketplaceAdapter
import com.example.lokally.model.MarketplaceItem
import kotlin.random.Random

class MarketplaceFragment : Fragment() {

    private var isServiceTab: Boolean = false

    companion object {
        private const val ARG_IS_SERVICE = "is_service"

        fun newInstance(isService: Boolean): MarketplaceFragment {
            val fragment = MarketplaceFragment()
            val args = Bundle()
            args.putBoolean(ARG_IS_SERVICE, isService)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isServiceTab = arguments?.getBoolean(ARG_IS_SERVICE) ?: false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_marketplace_list, container, false)

        setupSpinners(view)
        setupRecyclerView(view)

        return view
    }

    private fun setupSpinners(view: View) {
        // Mock data for filters
        val categories = if(isServiceTab) arrayOf("All Services", "Repair", "Cleaning", "Tutor") else arrayOf("All Items", "Electronics", "Clothing", "Food")
        val prices = arrayOf("Price: Low to High", "Price: High to Low")

        val catAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
        view.findViewById<Spinner>(R.id.spinnerCategory).adapter = catAdapter

        val priceAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, prices)
        view.findViewById<Spinner>(R.id.spinnerPrice).adapter = priceAdapter
    }

    private fun setupRecyclerView(view: View) {
        val rv = view.findViewById<RecyclerView>(R.id.rvMarketplace)
        rv.layoutManager = GridLayoutManager(context, 2) // 2 Columns Grid
        rv.adapter = MarketplaceAdapter(generateMockData(), isServiceTab)
    }

    // HCI Principle: Use realistic mock data to simulate actual usage feel
    private fun generateMockData(): List<MarketplaceItem> {
        val list = mutableListOf<MarketplaceItem>()
        val count = 20

        for (i in 1..count) {
            // Randomly generate content based on Tab Type
            val name = if (isServiceTab) "Service #${100 + i}" else "Item Product #${100 + i}"
            val price = if (isServiceTab) "₱ ${Random.nextInt(100, 1000)}/hr" else "₱ ${Random.nextInt(50, 5000)}"
            val location = listOf("Batangas City", "Lipa", "Tanauan").random()

            // Random image from Picsum
            val imgUrl = "https://picsum.photos/300/300?random=$i"

            list.add(MarketplaceItem(
                name = name,
                priceOrRate = price,
                description = "This is a great description for $name. Good quality and trusted seller.",
                location = location,
                imageUrl = imgUrl,
                timeUploaded = "${Random.nextInt(1, 24)}h ago"
            ))
        }
        return list
    }
}