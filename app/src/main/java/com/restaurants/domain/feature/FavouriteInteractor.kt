package com.restaurants.domain.feature

import com.restaurants.data.feature.favourite.FavouriteRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FavouriteInteractor
@Inject
constructor(
    private val favouriteRepository: FavouriteRepository
) {
    fun addFavourite(name: String): Completable {
        return favouriteRepository.addFavourite(name)
    }

    fun getFavourites(): Observable<Set<String>> {
        return favouriteRepository.getFavouriteSet()
    }

    fun removeFavourite(name: String): Completable {
        return favouriteRepository.removeFavourite(name)
    }
}