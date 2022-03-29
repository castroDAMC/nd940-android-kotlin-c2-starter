package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding

class MainAsteroidAdapter(
    private val listOfAsteroids: MutableLiveData<List<Asteroid>>,
    private val onClickListener: (asteroid: Asteroid) -> Unit
) : RecyclerView.Adapter<MainAsteroidAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: AsteroidListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.asteroid_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = listOfAsteroids.value!![position]
        holder.onBind(asteroid, onClickListener)
    }

    override fun getItemCount(): Int {
        return listOfAsteroids.value!!.size
    }

    fun insertAsteroids(asteroid: List<Asteroid>) {
        listOfAsteroids.value = asteroid
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: AsteroidListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(asteroid: Asteroid, onClickListener: (asteroid: Asteroid) -> Unit) {
            binding.asteroid = asteroid
            binding.executePendingBindings()

            binding.txtResumeAsteroidName.contentDescription = String.format(itemView.context.getString(R.string.tool_name_description), asteroid.codename)
            binding.txtResumeAsteroidDate.contentDescription = String.format(itemView.context.getString(R.string.tool_date_description), asteroid.closeApproachDate)

            itemView.setOnClickListener { onClickListener(asteroid) }
        }
    }
}


