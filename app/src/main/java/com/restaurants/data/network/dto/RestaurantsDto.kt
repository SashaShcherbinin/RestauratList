package com.restaurants.data.network.dto


import com.google.gson.annotations.SerializedName

data class RestaurantsDto(
    @SerializedName("restaurants")
    val restaurants: List<RestaurantDto?>?
) {
    data class RestaurantDto(
        @SerializedName("name")
        val name: String?,
        @SerializedName("sortingValues")
        val sortingValues: SortingValues?,
        @SerializedName("status")
        val status: String?
    ) {
        data class SortingValues(
            @SerializedName("averageProductPrice")
            val averageProductPrice: Int?,
            @SerializedName("bestMatch")
            val bestMatch: Double?,
            @SerializedName("deliveryCosts")
            val deliveryCosts: Int?,
            @SerializedName("distance")
            val distance: Int?,
            @SerializedName("minCost")
            val minCost: Int?,
            @SerializedName("newest")
            val newest: Double?,
            @SerializedName("popularity")
            val popularity: Double?,
            @SerializedName("ratingAverage")
            val ratingAverage: Double?
        )
    }
}