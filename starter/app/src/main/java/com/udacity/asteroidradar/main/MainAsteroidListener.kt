package com.udacity.asteroidradar.main

import android.view.View
import com.udacity.asteroidradar.Asteroid

class MainAsteroidListener (val clickListener: (asteroid: Asteroid, view: View) -> Unit) {
    fun onClick(asteroid: Asteroid, view: View) = clickListener (asteroid, view)
}