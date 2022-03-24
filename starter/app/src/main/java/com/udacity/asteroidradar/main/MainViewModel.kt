package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.NasaAPI
import com.udacity.asteroidradar.persistence.AsteroidsRepository
import com.udacity.asteroidradar.persistence.getAsteroidsDataBase
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getAsteroidsDataBase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    init {
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroidsList()
        }
    }

    private val _listOfAsteroids: LiveData<List<Asteroid>> = asteroidsRepository.asteroidsList

    val listOfAsteroids: LiveData<List<Asteroid>>
        get() = _listOfAsteroids

    val _pictureOfDay: MutableLiveData<String> = MutableLiveData()
    val pictureOfDay: MutableLiveData<String>
        get() = _pictureOfDay


}