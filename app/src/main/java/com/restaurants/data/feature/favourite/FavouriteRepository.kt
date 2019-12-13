package com.restaurants.data.feature.favourite

import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteRepository
@Inject
constructor(private val preferenceStorage: FavouritePreferenceStorage) {

    fun getFavouriteSet(): Observable<Set<String>> {
        return preferenceStorage.getSet()
    }

    fun addFavourite(name: String): Completable {
        return preferenceStorage.add(name)
    }

    fun removeFavourite(name: String): Completable {
        return preferenceStorage.remove(name)
    }
}