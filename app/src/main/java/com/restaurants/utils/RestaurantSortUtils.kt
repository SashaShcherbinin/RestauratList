package com.restaurants.utils

import com.restaurants.domain.model.Favourite
import com.restaurants.domain.model.Restaurant
import com.restaurants.domain.type.RestaurantStatus
import com.restaurants.domain.type.SortOption

class RestaurantSortUtils {

    fun sortByFavourite(list: List<Favourite>) =
        list.sortedByDescending { it.isFavourite }

    fun mapToFavorite(
        list: List<Restaurant>,
        favouriteList: Set<String>
    ): List<Favourite> {
        return list.map {
            Favourite(
                restaurant = it,
                isFavourite = favouriteList.contains(it.name)
            )
        }
    }

    fun filterBySearchWord(
        searchValue: String,
        list: List<Restaurant>
    ): List<Restaurant> {
        return if (!searchValue.isBlank()) {
            val lowerCase = searchValue.toLowerCase()
            list.filter { it.name.toLowerCase().contains(lowerCase) }
        } else {
            list
        }
    }

    fun sortByStatus(list: List<Favourite>): List<Favourite> {
        return list.sortedBy {
            when (it.restaurant.status) {
                RestaurantStatus.OPEN -> 1
                RestaurantStatus.ORDER_AHEAD -> 2
                RestaurantStatus.CLOSE -> 3

            }
        }
    }

    fun sortBySortOption(
        sortOption: SortOption,
        list: List<Favourite>
    ): List<Favourite> {
        return when (sortOption) {
            SortOption.BEST_MATCH -> list.sortedByDescending {
                it.restaurant.bestMatch
            }
            SortOption.NEWEST -> list.sortedByDescending {
                it.restaurant.newest
            }
            SortOption.RATING_AVARAGE -> list.sortedByDescending {
                it.restaurant.ratingAverage
            }
            SortOption.DISTANCE -> list.sortedByDescending {
                it.restaurant.distance
            }
            SortOption.POPULARITY -> list.sortedByDescending {
                it.restaurant.popularity
            }
            SortOption.AVARAGE_PRODACT_PRICE -> list.sortedByDescending {
                it.restaurant.averageProductPrice
            }
            SortOption.DELIVERY_COST -> list.sortedByDescending {
                it.restaurant.deliveryCosts
            }
            SortOption.MINIMUM_COST -> list.sortedByDescending {
                it.restaurant.minCost
            }
        }
    }

}