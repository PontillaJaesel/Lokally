package com.example.lokally.com.example.lokally.data.model

data class MarketplaceItem(
    val id: String,
    val name: String,
    val price: Double,
    val photoUrl: String?,
    val description: String,
    val location: String,
    val category: ItemCategory,
    val sellerId: String,
    val datePosted: Long
)

enum class ItemCategory {
    CLOTHES,
    FURNITURE_HOME_FINDS,
    APPLIANCES,
    GADGETS,
    STATIONERY,
    BOOKS,
    HANDMADE_CRAFTS,
    HOBBY_ITEMS
}