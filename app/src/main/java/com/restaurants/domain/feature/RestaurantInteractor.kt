package com.restaurants.domain.feature

import androidx.annotation.VisibleForTesting
import com.restaurants.data.feature.favourite.FavouriteRepository
import com.restaurants.data.feature.restaurant.RestaurantRepository
import com.restaurants.domain.model.Favourite
import com.restaurants.domain.type.SortOption
import com.restaurants.utils.RestaurantSortUtils
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RestaurantInteractor
@Inject
constructor(
    private val restaurantRepository: RestaurantRepository,
    private val favouriteRepository: FavouriteRepository
) {

    @VisibleForTesting
    var sortUtils = RestaurantSortUtils()

    fun getRestaurants(
        sortOption: SortOption,
        searchValue: String
    ): Observable<List<Favourite>> {
        return favouriteRepository.getFavouriteSet().concatMap { favouriteList ->
            restaurantRepository.getRestaurants()
                .map { sortUtils.filterBySearchWord(searchValue, it) }
                .map { sortUtils.mapToFavorite(it, favouriteList) }
                .map { sortUtils.sortBySortOption(sortOption, it) }
                .map { sortUtils.sortByStatus(it) }
                .map { sortUtils.sortByFavourite(it) }
        }
    }

    fun getSortType(): Observable<SortOption> {
        return restaurantRepository.getSortOption()
    }

    fun setSortType(sortOption: SortOption): Completable {
        return restaurantRepository.setSortOption(sortOption)
    }

}