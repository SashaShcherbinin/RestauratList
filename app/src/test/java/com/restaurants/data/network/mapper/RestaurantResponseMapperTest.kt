package com.restaurants.data.network.mapper

import com.restaurants.data.network.dto.RestaurantsDto
import com.restaurants.domain.model.Restaurant
import com.restaurants.domain.type.RestaurantStatus
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import org.junit.Test

class RestaurantResponseMapperTest : BaseMapperTest() {

    @Test
    fun map() {
        val type = object :
            TypeToken<RestaurantsDto>() {}.type
        val data: RestaurantsDto =
            getJsonByResource("restaurants_source.json", type!!)
        val mapper = RestaurantResponseMapper()
        val restaurant: Restaurant = mapper.map(data.restaurants!![0]!!)

        Assert.assertEquals("Tanoshii Sushi", restaurant.name)
        Assert.assertEquals(0.0, restaurant.bestMatch, 0.0)
        Assert.assertEquals(96.0, restaurant.newest, 0.0)
        Assert.assertEquals(RestaurantStatus.OPEN, restaurant.status)
        Assert.assertEquals(1536, restaurant.averageProductPrice)
        Assert.assertEquals(1190, restaurant.distance)
        Assert.assertEquals(1000, restaurant.minCost)
        Assert.assertEquals(200, restaurant.deliveryCosts)
        Assert.assertEquals(17.0, restaurant.popularity, 0.0)
        Assert.assertEquals(4.5, restaurant.ratingAverage, 0.0)
    }

    @Test
    fun mapStatus() {
        val mapper = RestaurantResponseMapper()
        Assert.assertEquals(RestaurantStatus.OPEN, mapper.mapStatus("open"))
        Assert.assertEquals(RestaurantStatus.CLOSE, mapper.mapStatus("closed"))
        Assert.assertEquals(RestaurantStatus.ORDER_AHEAD, mapper.mapStatus("order ahead"))
    }
}