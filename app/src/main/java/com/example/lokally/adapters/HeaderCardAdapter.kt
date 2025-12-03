package com.example.lokally.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lokally.R
import com.example.lokally.data.HeaderCard

class HeaderCardAdapter(private val cards: List<HeaderCard>) :
    RecyclerView.Adapter<HeaderCardAdapter.HeaderCardViewHolder>() {

    // 1. ViewHolder: Holds references to the views in list_item_header_card.xml
    inner class HeaderCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Use View Binding for better performance and safety if available,
        // otherwise use itemView.findViewById()
        private val title: TextView = itemView.findViewById(R.id.tv_header_title)
        private val subtitle: TextView = itemView.findViewById(R.id.tv_header_subtitle)
        private val cardBackground: ConstraintLayout = itemView.findViewById(R.id.card_background)

        fun bind(card: HeaderCard) {
            title.text = card.title
            subtitle.text = card.subtitle
            // Dynamically set the background color using the color resource ID
            cardBackground.setBackgroundResource(card.backgroundColor)
        }
    }

    // 2. onCreateViewHolder: Called when RecyclerView needs a new ViewHolder instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_header,
            parent,
            false
        )
        return HeaderCardViewHolder(view)
    }

    // 3. onBindViewHolder: Called to display the data at the specified position
    override fun onBindViewHolder(holder: HeaderCardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    // 4. getItemCount: Returns the total number of items in the list
    override fun getItemCount(): Int = cards.size
}