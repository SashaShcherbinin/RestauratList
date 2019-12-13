package com.restaurants.domain.feature

import com.restaurants.data.feature.favourite.FavouriteRepository
import com.restaurants.data.feature.restaurant.RestaurantRepository
import com.restaurants.utils.Rx2SchedulersOverrideRule
import com.restaurants.domain.model.Restaurant
import com.restaurants.domain.type.RestaurantStatus
import com.restaurants.domain.type.SortOption
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RestaurantInteractorTest {

    @Rule
    @JvmField
    val rxRule = Rx2SchedulersOverrideRule()

    @Mock
    lateinit var restaurantRepository: RestaurantRepository
    @Mock
    lateinit var favouriteRepository: FavouriteRepository

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
    fun getRestaurants() {
        val interactor = RestaurantInteractor(restaurantRepository, favouriteRepository)
        interactor.sortUtils = spy(interactor.sortUtils)
        whenever(favouriteRepository.getFavouriteSet()).thenReturn(Observable.just(HashSet()))
        val restaurantList = arrayListOf(
            restaurant.copy(),
            restaurant.copy(),
            restaurant.copy()
        )
        whenever(restaurantRepository.getRestaurants())
            .thenReturn(Observable.just(restaurantList))

        interactor.getRestaurants(SortOption.POPULARITY, "any").subscribe()

        val inOrder = inOrder(interactor.sortUtils)

        inOrder.verify(interactor.sortUtils, times(1)).filterBySearchWord(any(), any())
        inOrder.verify(interactor.sortUtils, times(1)).mapToFavorite(any(), any())
        inOrder.verify(interactor.sortUtils, times(1)).sortBySortOption(any(), any())
        inOrder.verify(interactor.sortUtils, times(1)).sortByStatus(any())
        inOrder.verify(interactor.sortUtils, times(1)).sortByFavourite(any())
    }

    @Test
    fun getSortType() {
        // todo simple not sense to do
    }

    @Test
    fun setSortType(sortOption: SortOption) {
        // todo simple not sense to do
    }
}