package com.restaurants.di.module

import androidx.lifecycle.ViewModelProvider
import com.restaurants.presentation.common.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * User: Sasha Shcherbinin
 * Date : 4/3/19
 */
@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory)
            : ViewModelProvider.Factory
}