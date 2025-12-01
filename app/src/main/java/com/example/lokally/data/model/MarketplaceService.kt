package com.example.lokally.com.example.lokally.data.model

data class MarketplaceService(
    val id: String,
    val description: String,
    val rate: String,
    val category: ServiceCategory,
    val providerId: String,
    val datePosted: Long
)

enum class ServiceCategory {
    EDUCATION,
    HOME_SERVICES,
    HEALTH_AND_PERSONAL_CARE,
    CREATIVE_AND_MEDIA_PRODUCTION,
    BUSINESS_OPERATIONS,
    FINANCIAL_SERVICES,
    TECHNOLOGY,
    HRM,
    AUTOMOTIVE
}