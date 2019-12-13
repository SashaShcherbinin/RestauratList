package com.restaurants.data.feature.restaurant

import android.content.Context
import com.restaurants.data.network.dto.RestaurantsDto
import com.restaurants.data.network.mapper.RestaurantResponseMapper
import com.restaurants.domain.model.Restaurant
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

class RestaurantNetworkStorage
@Inject
constructor(
    private val context: Context,
    private val mapper: RestaurantResponseMapper,
    private val gson: Gson
) {

    fun getRestaurants(): Single<List<Restaurant>> {
        return Single.create<List<Restaurant>> { emitter ->
            var json: String? = null
            val imputStream: InputStream = context.assets.open("restaurants_source.json")
            val size: Int = imputStream.available()
            val buffer = ByteArray(size)
            imputStream.read(buffer)
            imputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))

            val data = gson.fromJson(json, RestaurantsDto::class.java)
            emitter.onSuccess(data.restaurants!!.map { mapper.map(it!!) })
        }.subscribeOn(Schedulers.io())
    }

}