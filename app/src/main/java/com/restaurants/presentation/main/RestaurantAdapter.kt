package com.restaurants.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.restaurants.R
import com.restaurants.databinding.ItemRestaurantBinding
import com.restaurants.domain.model.Favourite

class RestaurantAdapter(private var viewModel: RestaurantsViewModel) :
    ListAdapter<Favourite, RestaurantAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object
            : DiffUtil.ItemCallback<Favourite>() {

            override fun areItemsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
                return oldItem.restaurant.name == newItem.restaurant.name
            }

            override fun areContentsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_restaurant, parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.vm = viewModel
        }

        fun bind(favourite: Favourite) {
            binding.item = favourite.restaurant
            binding.isFavorite = favourite.isFavourite
            binding.executePendingBindings()
        }
    }
}