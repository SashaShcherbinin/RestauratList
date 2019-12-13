package com.restaurants.presentation.filter

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.restaurants.R
import com.restaurants.databinding.ActivityFilterBinding
import com.restaurants.presentation.App
import com.restaurants.presentation.common.BaseActivity
import com.restaurants.presentation.common.ViewModelFactory
import javax.inject.Inject

class FilterActivity : BaseActivity() {

    private lateinit var viewModel: FilterViewModel
    private lateinit var binding: ActivityFilterBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent(this).inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FilterViewModel::class.java)
        binding.vm = viewModel

        observeEvents()
    }

    private fun observeEvents() {
        viewModel.uploading.observe(this, Observer {
            showUploading(it)
        })
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

}
