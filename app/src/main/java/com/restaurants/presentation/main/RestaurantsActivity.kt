package com.restaurants.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.restaurants.R
import com.restaurants.databinding.ActivityRestaurantsBinding
import com.restaurants.presentation.App
import com.restaurants.presentation.common.BaseActivity
import com.restaurants.presentation.common.ViewModelFactory
import com.restaurants.presentation.filter.FilterActivity
import javax.inject.Inject

class RestaurantsActivity : BaseActivity() {

    private lateinit var adapter: RestaurantAdapter
    private lateinit var viewModel: RestaurantsViewModel
    private lateinit var binding: ActivityRestaurantsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent(this).inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurants)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RestaurantsViewModel::class.java)
        binding.vm = viewModel

        adapter = RestaurantAdapter(viewModel)
        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        linearLayoutManager.initialPrefetchItemCount = 10
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter

        observeEvents()
        observeState()
    }

    private fun observeState() {
        viewModel.restaurantList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun observeEvents() {
        viewModel.uploading.observe(this, Observer {
            showUploading(it)
        })
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        viewModel.showFilterEvent.observe(this, Observer {
            startActivity(Intent(this, FilterActivity::class.java))
        })
    }
}
