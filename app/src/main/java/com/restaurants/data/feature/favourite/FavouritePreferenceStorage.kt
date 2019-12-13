package com.restaurants.data.feature.favourite

import android.content.Context
import com.restaurants.data.common.PreferenceStorage
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FavouritePreferenceStorage
@Inject
constructor(context: Context) {

    private val preferenceStorage: PreferenceStorage =
        PreferenceStorage(context, "favourite")

    fun add(name: String): Completable {
        return getSet().take(1).concatMapCompletable { set ->
            preferenceStorage.update {
                val mutableSet = set.toMutableSet()
                mutableSet.add(name)
                it.putStringSet("list", mutableSet)
            }
        }
    }

    fun getSet(): Observable<Set<String>> {
        return preferenceStorage.get {
            it.getStringSet("list", HashSet<String>())!!
        }
    }

    fun remove(name: String): Completable {
        return getSet().take(1).concatMapCompletable { set ->
            preferenceStorage.update {
                val mutableSet = set.toMutableSet()
                mutableSet.remove(name)
                it.putStringSet("list", mutableSet)
            }
        }
    }
}