package com.udacity.asteroidradar.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class MainAsteroidAdapter(
    private val listOfAsteroids: MutableLiveData<List<Asteroid>>,
    private val context: Context
) : RecyclerView.Adapter<MainAsteroidAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.asteroid_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listOfAsteroids.value!![position])
    }

    override fun getItemCount(): Int {
        return listOfAsteroids.value!!.size
    }

    fun insertAsteroids(asteroid: List<Asteroid>){
        listOfAsteroids.value = asteroid
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val asteroidName: TextView = itemView.findViewById(R.id.txt_resume_asteroid_name)
        private val asteroidDate: TextView = itemView.findViewById(R.id.txt_resume_asteroid_date)
        private val asteroidDangerous : ImageView = itemView.findViewById(R.id.img_is_hazardous)

        fun onBind(asteroid: Asteroid) {
            asteroidName.text = asteroid.codename
            asteroidDate.text = asteroid.closeApproachDate
            asteroidDangerous.setImageResource(
                when(asteroid.isPotentiallyHazardous){
                    true -> R.drawable.ic_status_potentially_hazardous
                    false -> R.drawable.ic_status_normal
                }
            )

            itemView.setOnClickListener {
                it.findNavController()
                    .navigate(MainFragmentDirections.actionShowDetail(asteroid))
            }
        }
    }
}


