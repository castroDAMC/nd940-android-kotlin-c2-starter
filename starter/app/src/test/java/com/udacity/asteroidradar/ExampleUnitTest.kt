package com.udacity.asteroidradar

import com.udacity.asteroidradar.api.NasaAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testReturnPictureOfTheDay(){
        runBlocking {
            val status = NasaAPI.retrofitService.getImageOfTheDay(Constants.API_KEY)
            assertTrue( status is PictureOfDay)
        }
    }
}
