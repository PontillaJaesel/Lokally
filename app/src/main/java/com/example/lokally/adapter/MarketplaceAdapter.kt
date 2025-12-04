package com.example.lokally.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lokally.R
import com.example.lokally.model.MarketplaceItem

class MarketplaceAdapter(
    private val items: List<MarketplaceItem>,
    private val isService: Boolean // <--- NEW: Pass this flag to the adapter
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // constants to help us identify the view type
    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_SERVICE = 1
    }

    // Determine which layout to use based on the isService flag
    override fun getItemViewType(position: Int): Int {
        return if (isService) TYPE_SERVICE else TYPE_ITEM
    }

    // ViewHolder for ITEMS
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivItemImage)
        val tvName: TextView = view.findViewById(R.id.tvItemName)
        val tvPrice: TextView = view.findViewById(R.id.tvItemPrice)
        val tvDesc: TextView = view.findViewById(R.id.tvItemDesc)
        val tvLocation: TextView = view.findViewById(R.id.tvLocation)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
    }

    // ViewHolder for SERVICES (Using your new layout IDs)
    class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivServiceImage) // Note: ID from service_card
        val tvName: TextView = view.findViewById(R.id.tvServiceName)
        val tvRate: TextView = view.findViewById(R.id.tvServiceRate)
        val tvDesc: TextView = view.findViewById(R.id.tvServiceDesc)
        val tvLocation: TextView = view.findViewById(R.id.tvServiceLocation)
        val tvTime: TextView = view.findViewById(R.id.tvServiceTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SERVICE) {
            // Inflate Service Layout
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.service_marketplace_card, parent, false)
            ServiceViewHolder(view)
        } else {
            // Inflate Item Layout
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_marketplace_card, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (getItemViewType(position) == TYPE_SERVICE) {
            // Bind SERVICE Data
            val serviceHolder = holder as ServiceViewHolder
            serviceHolder.tvName.text = item.name
            serviceHolder.tvRate.text = item.priceOrRate // This will show Rate/hr
            serviceHolder.tvDesc.text = item.description
            serviceHolder.tvLocation.text = item.location
            serviceHolder.tvTime.text = item.timeUploaded

            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(serviceHolder.ivImage)

        } else {
            // Bind ITEM Data
            val itemHolder = holder as ItemViewHolder
            itemHolder.tvName.text = item.name
            itemHolder.tvPrice.text = item.priceOrRate // This will show Price
            itemHolder.tvDesc.text = item.description
            itemHolder.tvLocation.text = item.location
            itemHolder.tvTime.text = item.timeUploaded

            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(itemHolder.ivImage)
        }
    }

    override fun getItemCount() = items.size
}