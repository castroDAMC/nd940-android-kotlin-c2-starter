package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {

    private val _listOfAsteroids : MutableLiveData<List<Asteroid>> = MutableLiveData()
    val listOfAsteroids: LiveData<List<Asteroid>>
        get() = _listOfAsteroids

    private val _pictureOfDay : MutableLiveData<String> = MutableLiveData()
    val pictureOfDay: LiveData<String>
        get() = _pictureOfDay

    init {
        _listOfAsteroids.value = mutableListOf()
        _pictureOfDay.value = ""
    }

}