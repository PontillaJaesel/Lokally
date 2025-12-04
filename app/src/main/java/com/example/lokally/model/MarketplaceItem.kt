package com.example.lokally.model

data class MarketplaceItem(
    val name: String,
    val priceOrRate: String,
    val description: String,
    val location: String,
    val imageUrl: String,
    val timeUploaded: String
)