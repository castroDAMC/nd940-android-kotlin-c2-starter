package com.udacity.asteroidradar.persistence

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaAPI
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    val asteroidsList: LiveData<List<Asteroid>> = Transformations.map(database.userDao().getAll()) {
        it.asDomainModel()
    }

    var pictureOfDay: MutableLiveData<PictureOfDay> = MutableLiveData(PictureOfDay("","",""))

    var pictureOfDayStatus: MutableLiveData<Int>  = MutableLiveData(Constants.PICTURE_LOADING)


    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun refreshAsteroidsList() {
        withContext(Dispatchers.IO) {
            try {
                val asteroidsListFromNetwork =
                    NasaAPI.retrofitService.getAsteroidsState(Constants.API_KEY)
                val parsedList = parseAsteroidsJsonResult(JSONObject(asteroidsListFromNetwork))
                database.userDao().insertAsteroidsInBatch(*parsedList.asDAOModel().toTypedArray())

                refreshPicturesOfTheDay()
            } catch (e: Exception) {
                Log.e("AsteroidsRepository", e.toString())
            }

        }
    }

    suspend fun deleteOldAsteroids(){
        withContext(Dispatchers.IO){
            database.userDao().deleteOldAsteroids(getToday())
        }
    }

    suspend fun refreshPicturesOfTheDay() {
        withContext(Dispatchers.IO) {
            pictureOfDayStatus.postValue(Constants.PICTURE_LOADING)
            try {
                pictureOfDay.postValue(NasaAPI.retrofitService.getImageOfTheDay(Constants.API_KEY))
                pictureOfDayStatus.postValue(Constants.PICTURE_DONE)
            } catch (e: Exception){
                pictureOfDayStatus.postValue(Constants.PICTURE_ERROR)
            }
        }
    }

}