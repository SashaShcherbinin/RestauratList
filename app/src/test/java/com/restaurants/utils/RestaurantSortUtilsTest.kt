package com.restaurants.utils

import com.restaurants.domain.model.Favourite
import com.restaurants.domain.model.Restaurant
import com.restaurants.domain.type.RestaurantStatus
import com.restaurants.domain.type.SortOption
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.junit.Test

class RestaurantSortUtilsTest {

    private val sortUtils = RestaurantSortUtils()
    private val restaurant =
        Restaurant(
            "",
            RestaurantStatus.ORDER_AHEAD,
            0,
            0.0,
            0,
            0,
            0,
            0.0,
            0.0,
            0.0
        )

    @Test
    fun sortByFavouriteCase1() {
        val list = arrayListOf(
            Favourite(mock(), false),
            Favourite(mock(), true),
            Favourite(mock(), false)
        )
        val actualList = sortUtils.sortByFavourite(list)
        Assert.assertEquals(actualList[0].isFavourite, true)
        Assert.assertEquals(actualList[1].isFavourite, false)
        Assert.assertEquals(actualList[2].isFavourite, false)
    }

    @Test
    fun sortByFavouriteCase2() {
        val list = arrayListOf(
            Favourite(mock(), true),
            Favourite(mock(), true),
            Favourite(mock(), false),
            Favourite(mock(), true)
        )
        val actualList = sortUtils.sortByFavourite(list)
        Assert.assertEquals(actualList[0].isFavourite, true)
        Assert.assertEquals(actualList[1].isFavourite, true)
        Assert.assertEquals(actualList[2].isFavourite, true)
        Assert.assertEquals(actualList[3].isFavourite, false)
    }

    @Test
    fun sortByFavouriteCase3() {
        val list = arrayListOf(
            Favourite(mock(), false),
            Favourite(mock(), true),
            Favourite(mock(), false),
            Favourite(mock(), true)
        )
        val actualList = sortUtils.sortByFavourite(list)
        Assert.assertEquals(actualList[0].isFavourite, true)
        Assert.assertEquals(actualList[1].isFavourite, true)
        Assert.assertEquals(actualList[2].isFavourite, false)
        Assert.assertEquals(actualList[3].isFavourite, false)
    }


    @Test
    fun mapToFavoriteCase1() {
        val favouriteList = arrayListOf(
            "name2",
            "name3"
        ).toSet()
        val list = arrayListOf(
            restaurant.copy(name = "name1"),
            restaurant.copy(name = "name2"),
            restaurant.copy(name = "name3"),
            restaurant.copy(name = "name4")
        )
        val actualList = sortUtils.mapToFavorite(list, favouriteList)
        Assert.assertEquals(actualList[0].isFavourite, false)
        Assert.assertEquals(actualList[1].isFavourite, true)
        Assert.assertEquals(actualList[2].isFavourite, true)
        Assert.assertEquals(actualList[3].isFavourite, false)
    }

    @Test
    fun mapToFavoriteCase2() {
        val favouriteList = arrayListOf(
            "name1",
            "name4"
        ).toSet()
        val list = arrayListOf(
            restaurant.copy(name = "name1"),
            restaurant.copy(name = "name2"),
            restaurant.copy(name = "name3"),
            restaurant.copy(name = "name4")
        )
        val actualList = sortUtils.mapToFavorite(list, favouriteList)
        Assert.assertEquals(actualList[0].isFavourite, true)
        Assert.assertEquals(actualList[1].isFavourite, false)
        Assert.assertEquals(actualList[2].isFavourite, false)
        Assert.assertEquals(actualList[3].isFavourite, true)
    }

    @Test
    fun filterBySearchWordCase1() {
        val list = arrayListOf(
            restaurant.copy(name = "name1"),
            restaurant.copy(name = "name2"),
            restaurant.copy(name = "test1"),
            restaurant.copy(name = "test2")
        )
        val actualList = sortUtils.filterBySearchWord("na", list)
        Assert.assertEquals(actualList.size, 2)
        Assert.assertEquals(actualList[0].name, "name1")
        Assert.assertEquals(actualList[1].name, "name2")
    }

    @Test
    fun filterBySearchWordCase2() {
        val list = arrayListOf(
            restaurant.copy(name = "name1"),
            restaurant.copy(name = "Name2"),
            restaurant.copy(name = "test1"),
            restaurant.copy(name = "test2")
        )
        val actualList = sortUtils.filterBySearchWord("na", list)
        Assert.assertEquals(actualList.size, 2)
        Assert.assertEquals(actualList[0].name, "name1")
        Assert.assertEquals(actualList[1].name, "Name2")
    }

    @Test
    fun filterBySearchWordCase3() {
        val list = arrayListOf(
            restaurant.copy(name = "name1"),
            restaurant.copy(name = "Name2"),
            restaurant.copy(name = "test1"),
            restaurant.copy(name = "test2")
        )
        val actualList = sortUtils.filterBySearchWord("test3", list)
        Assert.assertEquals(actualList.size, 0)
    }

    @Test
    fun filterBySearchWordCase4() {
        val list = arrayListOf(
            restaurant.copy(name = "name1"),
            restaurant.copy(name = "Name2"),
            restaurant.copy(name = "test1"),
            restaurant.copy(name = "test2")
        )
        val actualList = sortUtils.filterBySearchWord("1", list)
        Assert.assertEquals(actualList.size, 2)
        Assert.assertEquals(actualList[0].name, "name1")
        Assert.assertEquals(actualList[1].name, "test1")
    }

    @Test
    fun sortByStatusCase1() {
        val list = arrayListOf(
            Favourite(restaurant.copy(status = RestaurantStatus.CLOSE), false),
            Favourite(restaurant.copy(status = RestaurantStatus.OPEN), false),
            Favourite(restaurant.copy(status = RestaurantStatus.ORDER_AHEAD), false),
            Favourite(restaurant.copy(status = RestaurantStatus.OPEN), false)
        )
        val actualList = sortUtils.sortByStatus(list)
        Assert.assertEquals(actualList[0].restaurant.status, RestaurantStatus.OPEN)
        Assert.assertEquals(actualList[1].restaurant.status, RestaurantStatus.OPEN)
        Assert.assertEquals(actualList[2].restaurant.status, RestaurantStatus.ORDER_AHEAD)
        Assert.assertEquals(actualList[3].restaurant.status, RestaurantStatus.CLOSE)
    }

    @Test
    fun sortByStatusCase2() {
        val list = arrayListOf(
            Favourite(restaurant.copy(status = RestaurantStatus.ORDER_AHEAD), false),
            Favourite(restaurant.copy(status = RestaurantStatus.OPEN), false),
            Favourite(restaurant.copy(status = RestaurantStatus.CLOSE), false),
            Favourite(restaurant.copy(status = RestaurantStatus.OPEN), false)
        )
        val actualList = sortUtils.sortByStatus(list)
        Assert.assertEquals(actualList[0].restaurant.status, RestaurantStatus.OPEN)
        Assert.assertEquals(actualList[1].restaurant.status, RestaurantStatus.OPEN)
        Assert.assertEquals(actualList[2].restaurant.status, RestaurantStatus.ORDER_AHEAD)
        Assert.assertEquals(actualList[3].restaurant.status, RestaurantStatus.CLOSE)
    }

    @Test
    fun sortByStatusCase3() {
        val list = arrayListOf(
            Favourite(restaurant.copy(status = RestaurantStatus.CLOSE), false),
            Favourite(restaurant.copy(status = RestaurantStatus.CLOSE), false),
            Favourite(restaurant.copy(status = RestaurantStatus.OPEN), false),
            Favourite(restaurant.copy(status = RestaurantStatus.OPEN), false)
        )
        val actualList = sortUtils.sortByStatus(list)
        Assert.assertEquals(actualList[0].restaurant.status, RestaurantStatus.OPEN)
        Assert.assertEquals(actualList[1].restaurant.status, RestaurantStatus.OPEN)
        Assert.assertEquals(actualList[2].restaurant.status, RestaurantStatus.CLOSE)
        Assert.assertEquals(actualList[3].restaurant.status, RestaurantStatus.CLOSE)
    }

    @Test
    fun sortBySortOptionNewest() {
        val list = arrayListOf(
            Favourite(restaurant.copy(newest = 30.0), false),
            Favourite(restaurant.copy(newest = 13.0), false),
            Favourite(restaurant.copy(newest = 20.0), false),
            Favourite(restaurant.copy(newest = 40.0), false)
        )
        val actualList = sortUtils.sortBySortOption(SortOption.NEWEST, list)
        Assert.assertEquals(actualList[0].restaurant.newest, 40.0,0.0)
        Assert.assertEquals(actualList[1].restaurant.newest, 30.0,0.0)
        Assert.assertEquals(actualList[2].restaurant.newest, 20.0,0.0)
        Assert.assertEquals(actualList[3].restaurant.newest, 13.0, 0.0)
    }


    @Test
    fun sortBySortOptionBestMatch() {
        val list = arrayListOf(
            Favourite(restaurant.copy(bestMatch = 30.0), false),
            Favourite(restaurant.copy(bestMatch = 13.0), false),
            Favourite(restaurant.copy(bestMatch = 20.0), false),
            Favourite(restaurant.copy(bestMatch = 40.0), false)
        )
        val actualList = sortUtils.sortBySortOption(SortOption.BEST_MATCH, list)
        Assert.assertEquals(actualList[0].restaurant.bestMatch, 40.0,0.0)
        Assert.assertEquals(actualList[1].restaurant.bestMatch, 30.0,0.0)
        Assert.assertEquals(actualList[2].restaurant.bestMatch, 20.0,0.0)
        Assert.assertEquals(actualList[3].restaurant.bestMatch, 13.0, 0.0)
    }

    @Test
    fun sortBySortOptionPopularity() {
        val list = arrayListOf(
            Favourite(restaurant.copy(popularity = 30.0), false),
            Favourite(restaurant.copy(popularity = 13.0), false),
            Favourite(restaurant.copy(popularity = 20.0), false),
            Favourite(restaurant.copy(popularity = 40.0), false)
        )
        val actualList = sortUtils.sortBySortOption(SortOption.POPULARITY, list)
        Assert.assertEquals(actualList[0].restaurant.popularity, 40.0,0.0)
        Assert.assertEquals(actualList[1].restaurant.popularity, 30.0,0.0)
        Assert.assertEquals(actualList[2].restaurant.popularity, 20.0,0.0)
        Assert.assertEquals(actualList[3].restaurant.popularity, 13.0, 0.0)
    }

    @Test
    fun sortBySortOption() {
        // todo todo finish sorting checking
    }
}