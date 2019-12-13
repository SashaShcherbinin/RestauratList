package com.restaurants.presentation.main

import androidx.lifecycle.MutableLiveData
import com.restaurants.domain.feature.FavouriteInteractor
import com.restaurants.domain.feature.RestaurantInteractor
import com.restaurants.domain.model.Favourite
import com.restaurants.domain.model.Restaurant
import com.restaurants.domain.type.ContentState
import com.restaurants.domain.type.SortOption
import com.restaurants.presentation.common.BaseViewModel
import com.restaurants.presentation.common.error.DefaultErrorHandler
import com.restaurants.presentation.common.livedata.SingleLiveEvent
import com.restaurants.presentation.utils.RxDisposable
import com.restaurants.presentation.utils.observeForever
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class RestaurantsViewModel
@Inject
constructor(
    private val restaurantInteractor: RestaurantInteractor,
    private val favouriteInteractor: FavouriteInteractor,
    private val errorHandler: DefaultErrorHandler
) : BaseViewModel() {

    val restaurantList = MutableLiveData<List<Favourite>>()
    val contentState = MutableLiveData<ContentState>()
    val searchValue = MutableLiveData<String>()
    val sortOption = MutableLiveData<SortOption>()

    val showFilterEvent = SingleLiveEvent<Unit>()

    init {
        searchValue.value = ""
        observeFilter()
        observeForever(sortOption, searchValue) { sortOption, searchValue ->
            observeRestaurants(sortOption, searchValue)
        }
    }

    private fun observeFilter() {
        RxDisposable.manage(
            this, "filter",
            restaurantInteractor.getSortType()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    sortOption.value = it
                }, {
                    errorHandler.handleError(it)
                })
        )
    }

    private fun observeRestaurants(
        sortOption: SortOption,
        searchValue: String
    ) {
        RxDisposable.manage(this, "restaurants",
            restaurantInteractor.getRestaurants(sortOption, searchValue)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    contentState.value = ContentState.LOADING
                }
                .subscribe(
                    {
                        restaurantList.value = it
                        if (it.isEmpty()) {
                            contentState.value = ContentState.EMPTY
                        } else {
                            contentState.value = ContentState.CONTENT
                        }
                    },
                    { error ->
                        errorHandler.handleError(error) { errorMessage.value = it }
                    })
        )
    }

    fun toggleFavorite(isFavourite: Boolean, restaurant: Restaurant) {
        if (isFavourite) {
            RxDisposable.manage(
                this, restaurant.name,
                favouriteInteractor
                    .removeFavourite(restaurant.name)
                    .subscribe()
            )
        } else {
            RxDisposable.manage(
                this, restaurant.name,
                favouriteInteractor.addFavourite(restaurant.name)
                    .subscribe()
            )
        }
    }

    fun showFilterClicked() {
        showFilterEvent.call()
    }
}