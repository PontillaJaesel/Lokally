package com.example.lokally.com.example.lokally.repository

interface MarketplaceRepository {
    /** Fetches a list of items based on filters (optional) */
    suspend fun browseItems(
        category: ItemCategory? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        query: String? = null
    ): List<MarketplaceItem>

    /** Posts a new item for sale */
    suspend fun postItem(item: MarketplaceItem): Result<Unit>

    /** Retrieves the details of a specific item */
    suspend fun viewItemDetails(itemId: String): MarketplaceItem?

    /** Fetches a list of services based on filters (optional) */
    suspend fun browseServices(
        category: ServiceCategory? = null,
        rateFilter: String? = null, // Custom filter for rate strings
        query: String? = null
    ): List<MarketplaceService>

    /** Posts a new service offering */
    suspend fun postService(service: MarketplaceService): Result<Unit>

    /** Starts a message thread with a user (seller/provider) */
    fun startMessageThread(userId: String)
}
