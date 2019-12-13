package com.restaurants.presentation.filter

import androidx.annotation.VisibleForTesting
import com.restaurants.R
import com.restaurants.domain.feature.RestaurantInteractor
import com.restaurants.domain.type.SortOption
import com.restaurants.presentation.common.BaseViewModel
import com.restaurants.presentation.common.error.DefaultErrorHandler
import com.restaurants.presentation.common.livedata.DebounceMutableLiveData
import com.restaurants.presentation.utils.RxDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FilterViewModel
@Inject
constructor(
    private val restaurantInteractor: RestaurantInteractor,
    private val errorHandler: DefaultErrorHandler
) : BaseViewModel() {

    val checkedId = DebounceMutableLiveData<Int>(200)

    init {
        observeFilter()
        checkedId.observeForever { id ->
            RxDisposable.manage(
                this, "save",
                restaurantInteractor.setSortType(mapIdToSortTyp(id))
                    .subscribe({}, { errorHandler.handleError(it) })
            )
        }
    }

    @VisibleForTesting
    fun mapSortTypeToId(sortOption: SortOption): Int {
        return when (sortOption) {
            SortOption.BEST_MATCH -> R.id.bestMatchRb
            SortOption.NEWEST -> R.id.newestRb
            SortOption.RATING_AVARAGE -> R.id.ratingAverageRb
            SortOption.DISTANCE -> R.id.distanceRb
            SortOption.POPULARITY -> R.id.popularityRb
            SortOption.AVARAGE_PRODACT_PRICE -> R.id.averageProductPriceRb
            SortOption.DELIVERY_COST -> R.id.deliveryCostsRb
            SortOption.MINIMUM_COST -> R.id.minCostRb
        }
    }

    @VisibleForTesting
    fun mapIdToSortTyp(id: Int): SortOption {
        return when (id) {
            R.id.bestMatchRb -> SortOption.BEST_MATCH
            R.id.newestRb -> SortOption.NEWEST
            R.id.ratingAverageRb -> SortOption.RATING_AVARAGE
            R.id.distanceRb -> SortOption.DISTANCE
            R.id.popularityRb -> SortOption.POPULARITY
            R.id.averageProductPriceRb -> SortOption.AVARAGE_PRODACT_PRICE
            R.id.deliveryCostsRb -> SortOption.DELIVERY_COST
            R.id.minCostRb -> SortOption.MINIMUM_COST
            else -> error("unsupported id")
        }
    }

    private fun observeFilter() {
        RxDisposable.manage(
            this, "filter",
            restaurantInteractor.getSortType().take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    checkedId.value = mapSortTypeToId(it)
                }, {
                    errorHandler.handleError(it)
                })
        )
    }

}