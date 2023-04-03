package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NASA_API
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.NasaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class NasaRepository(private val database: NasaDatabase){
    val asteroidList: LiveData<List<Asteroid>>
        get() = database.NasaDao().getAll()

    val todayAsteroidList: LiveData<List<Asteroid>>
        get() = database.NasaDao().getTodayAsteroid(Constants.getCurrentDate())


    suspend fun refreshAsteroidList() {
        withContext(Dispatchers.IO) {
            try {
                val data = parseAsteroidsJsonResult(JSONObject(NASA_API.retrofitService.getAsteroid()))
                database.NasaDao().updateData(data)
                Log.e("data", "")
            } catch (e: Exception) {
                Log.e("data", "")
            }
        }
    }
}