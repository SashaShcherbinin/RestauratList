package com.restaurants.data.feature.restaurant

import com.restaurants.domain.model.Restaurant
import com.restaurants.domain.type.SortOption
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepository
@Inject
constructor(private val networkStorage: RestaurantNetworkStorage,
            private val preferenceStorage: FilterPreferenceStorage) {

    fun getRestaurants(): Observable<List<Restaurant>> {
        return networkStorage.getRestaurants().toObservable()
    }

    fun setSortOption(sortOption: SortOption): Completable {
        return preferenceStorage.saveFilter(sortOption)
    }

    fun getSortOption(): Observable<SortOption> {
        return preferenceStorage.getFilter()
    }
}