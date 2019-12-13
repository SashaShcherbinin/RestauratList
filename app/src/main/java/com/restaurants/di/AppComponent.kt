package com.restaurants.di

import com.restaurants.di.module.AppModule
import com.restaurants.di.module.ViewModelFactoryModule
import com.restaurants.di.module.ViewModelModule
import com.restaurants.presentation.App
import com.restaurants.presentation.filter.FilterActivity
import com.restaurants.presentation.main.RestaurantsActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(activity: RestaurantsActivity)
    fun inject(activity: FilterActivity)

    class Initializer private constructor() {
        companion object {

            fun init(app: App): AppComponent {

                return DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()
            }
        }
    }
}