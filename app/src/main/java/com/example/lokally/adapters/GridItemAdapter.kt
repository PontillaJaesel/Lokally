package com.example.lokally.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lokally.R
import com.example.lokally.data.GridItem

class GridItemAdapter(private val items: List<GridItem>) :
    RecyclerView.Adapter<GridItemAdapter.GridItemViewHolder>() {

    // 1. ViewHolder: Holds references to the views in list_item_grid_item.xml
    inner class GridItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: TextView = itemView.findViewById(R.id.tv_grid_icon)
        private val label: TextView = itemView.findViewById(R.id.tv_grid_label)

        fun bind(item: GridItem) {
            icon.text = item.icon
            label.text = item.label
        }
    }

    // 2. onCreateViewHolder: Called when RecyclerView needs a new ViewHolder instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_grid,
            parent,
            false
        )
        return GridItemViewHolder(view)
    }

    // 3. onBindViewHolder: Called to display the data at the specified position
    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // 4. getItemCount: Returns the total number of items in the list
    override fun getItemCount(): Int = items.size
}