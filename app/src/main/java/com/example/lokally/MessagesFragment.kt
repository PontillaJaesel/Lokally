package com.example.lokally

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MessagesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Setup RecyclerView
        val rvMessages = view.findViewById<RecyclerView>(R.id.rvMessages)
        rvMessages.layoutManager = LinearLayoutManager(context)

        // 2. Create Mock Data (Service Persons & Sellers)
        val mockMessages = listOf(
            Message("Kuya Jepoy (Aircon Repair)", "Sige po ma'am, dadaan ako dyan ng 2pm para icheck yung unit.", "1:45 PM"),
            Message("Robert", "pede pa po bang tumawad hehe.", "11:30 AM"),
            Message("Taho Vendor Mike", "Otw na po ako sa subdivision niyo.", "8:15 AM"),
            Message("Cely's Homemade Crafts", "Thank you! We will ship your woven bag tomorrow morning.", "Yesterday"),
            Message("Mang Juan (Plumber)", "Okay na po yung gripo, pakicheck na lang po paguwi niyo.", "Yesterday"),
            Message("Techy Archy", "Meron pa po kaming 2nd hand iPhone 12. Contact us if you're interested.", "Mon")
        )

        // 3. Attach Adapter
        rvMessages.adapter = MessagesAdapter(mockMessages)
    }

    // --- Simple Data Class for a Message ---
    data class Message(
        val name: String,
        val preview: String,
        val time: String
    )

    // --- Simple Adapter to display the list ---
    inner class MessagesAdapter(private val messages: List<Message>) :
        RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

        inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView = itemView.findViewById(R.id.tvSenderName)
            val tvPreview: TextView = itemView.findViewById(R.id.tvMessagePreview)
            val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_row, parent, false)
            return MessageViewHolder(view)
        }

        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            val message = messages[position]
            holder.tvName.text = message.name
            holder.tvPreview.text = message.preview
            holder.tvTime.text = message.time
        }

        override fun getItemCount(): Int = messages.size
    }
}