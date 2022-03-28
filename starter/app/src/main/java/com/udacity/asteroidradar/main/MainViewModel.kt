package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.persistence.AsteroidsRepository
import com.udacity.asteroidradar.persistence.getAsteroidsDataBase
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getAsteroidsDataBase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    val pictureOfDayStatus: MutableLiveData<Int> = asteroidsRepository.pictureOfDayStatus

    private val _listOfAsteroids: MutableLiveData<List<Asteroid>> =
        asteroidsRepository.asteroidsList as MutableLiveData<List<Asteroid>>
    val listOfAsteroids: LiveData<List<Asteroid>>
        get() = _listOfAsteroids

    private var _pictureOfDay: MutableLiveData<PictureOfDay> = asteroidsRepository.pictureOfDay
    val pictureOfDay: MutableLiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroidsList()
            asteroidsRepository.refreshPicturesOfTheDay()
        }
    }

}