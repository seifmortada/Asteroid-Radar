package com.udacity.asteroidradar.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.FragmentItemDetailBinding

class   NasaRecyclerAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Asteroid, NasaRecyclerAdapter.NasaViewHolder>(DiffCallback) {

    //create class ViewHolder
    class NasaViewHolder(private var binding: FragmentItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.item = asteroid
            binding.executePendingBindings()
        }
    }


    // create companion object
    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaViewHolder {
        return NasaViewHolder(
            FragmentItemDetailBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false))
    }


    override fun onBindViewHolder(holder: NasaViewHolder, position: Int) {
        val asteroid = getItem(position)
     holder.itemView.setOnClickListener {
            onClickListener.onClick(asteroid)
        }
        holder.bind(asteroid)
    }

    // toSetOnClickListener
    class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}