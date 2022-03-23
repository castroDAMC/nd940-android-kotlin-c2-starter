package com.udacity.asteroidradar.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val asteroidName: TextView = itemView.findViewById(R.id.txt_resume_asteroid_name)
        private val asteroidDate: TextView = itemView.findViewById(R.id.txt_resume_asteroid_date)

        init {
            itemView.setOnClickListener { doSomething() }
        }

        private fun doSomething() {
            Toast.makeText(itemView.context,"CLICADO = " + asteroidName.text, Toast.LENGTH_LONG).show()
        }

        fun onBind(asteroid: Asteroid) {
            asteroidName.text = asteroid.codename
            asteroidDate.text = asteroid.closeApproachDate
        }
    }
}


