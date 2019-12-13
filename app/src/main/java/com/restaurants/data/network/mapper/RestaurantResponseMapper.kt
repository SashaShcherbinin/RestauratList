package com.restaurants.data.network.mapper

import androidx.annotation.VisibleForTesting
import com.restaurants.data.network.dto.RestaurantsDto
import com.restaurants.domain.model.Restaurant
import com.restaurants.domain.type.RestaurantStatus
import javax.inject.Inject

class RestaurantResponseMapper
@Inject
constructor() {

    fun map(dto: RestaurantsDto.RestaurantDto): Restaurant {
        return Restaurant(
            name = dto.name!!,
            status = mapStatus(dto.status!!),
            averageProductPrice = dto.sortingValues?.averageProductPrice!!,
            bestMatch = dto.sortingValues.bestMatch!!,
            deliveryCosts = dto.sortingValues.deliveryCosts!!,
            distance = dto.sortingValues.distance!!,
            minCost = dto.sortingValues.minCost!!,
            newest = dto.sortingValues.newest!!,
            popularity = dto.sortingValues.popularity!!,
            ratingAverage = dto.sortingValues.ratingAverage!!
        )
    }

    @VisibleForTesting
    fun mapStatus(status: String): RestaurantStatus {
        return when (status) {
            "open" -> RestaurantStatus.OPEN
            "closed" -> RestaurantStatus.CLOSE
            "order ahead" -> RestaurantStatus.ORDER_AHEAD
            else -> error("unsupported type")
        }
    }
}