package com.restaurants.domain.model

import com.restaurants.domain.type.RestaurantStatus

data class Restaurant(
    val name: String,
    val status: RestaurantStatus,
    val averageProductPrice: Int,
    val bestMatch: Double,
    val deliveryCosts: Int,
    val distance: Int,
    val minCost: Int,
    val newest: Double,
    val popularity: Double,
    val ratingAverage: Double
)