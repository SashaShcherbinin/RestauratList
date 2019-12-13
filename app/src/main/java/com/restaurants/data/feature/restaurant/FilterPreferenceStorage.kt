package com.restaurants.data.feature.restaurant

import android.content.Context
import com.restaurants.data.common.PreferenceStorage
import com.restaurants.domain.type.SortOption
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FilterPreferenceStorage
@Inject
constructor(context: Context) {

    private val preferenceStorage: PreferenceStorage =
        PreferenceStorage(context, "restaurantFilter")

    fun saveFilter(sortOption: SortOption): Completable {
        return preferenceStorage.update {
            it.putString("sortType", sortOption.name)
        }
    }

    fun getFilter(): Observable<SortOption> {
        return preferenceStorage.get {
            val value = it.getString("sortType", null)
            if (value != null) {
                SortOption.valueOf(value)
            } else {
                SortOption.NEWEST
            }
        }
    }
}