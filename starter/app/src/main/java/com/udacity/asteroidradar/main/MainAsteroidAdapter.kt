package com.udacity.asteroidradar.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainAsteroidAdapter(
    private val listOfAsteroids: MutableLiveData<List<Asteroid>>,
    private val context: Context
) : RecyclerView.Adapter<MainAsteroidAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: AsteroidListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.asteroid_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listOfAsteroids.value!![position])
    }

    override fun getItemCount(): Int {
        return listOfAsteroids.value!!.size
    }

    fun insertAsteroids(asteroid: List<Asteroid>) {
        listOfAsteroids.value = asteroid
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: AsteroidListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(asteroid: Asteroid) {
            binding.asteroid = asteroid

            itemView.setOnClickListener {
                it.findNavController()
                    .navigate(MainFragmentDirections.actionShowDetail(asteroid))
            }
        }
    }
}


