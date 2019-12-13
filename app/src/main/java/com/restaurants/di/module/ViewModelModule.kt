package com.restaurants.di.module

import androidx.lifecycle.ViewModel
import com.restaurants.di.ViewModelKey
import com.restaurants.presentation.filter.FilterViewModel
import com.restaurants.presentation.main.RestaurantsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantsViewModel::class)
    abstract fun bindRestautsViewModel(viewModel: RestaurantsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterViewModel::class)
    abstract fun bindFilterViewModel(viewModel: FilterViewModel): ViewModel
}