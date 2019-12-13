package com.restaurants.di.module

import android.content.Context
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    internal fun provideContext(): Context = context

    @Provides
    internal fun createGson() = GsonBuilder().create()!!

}
