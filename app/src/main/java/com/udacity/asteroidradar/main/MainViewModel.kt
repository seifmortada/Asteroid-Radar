package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NASA_API
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.NasaDatabase
import com.udacity.asteroidradar.repository.NasaRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = NasaDatabase.getInstance(application)
    private val repository = NasaRepository(database)

    private val list = repository.asteroidList
    private val today= repository.todayAsteroidList


    val allAstro: MediatorLiveData<List<Asteroid>> = MediatorLiveData()

    private val _pic_of_the_day = MutableLiveData<PictureOfDay>()
    val pic_of_the_day: LiveData<PictureOfDay>
        get() = _pic_of_the_day


    private val _navigateToSelectedProperty = MutableLiveData<Asteroid?>()
    val navigateToSelectedProperty: MutableLiveData<Asteroid?>
        get() = _navigateToSelectedProperty


    init {
        getallAstro()
    }

    private fun getallAstro() {
        viewModelScope.launch {
            repository.refreshAsteroidList()
            _pic_of_the_day.value = NASA_API.retrofitService.getPicOfTheDay()
            allAstro.addSource(list){allAstro.value=it}
        }
    }


    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedProperty.value = asteroid
    }


    fun displayAsteroidComplete() {
        _navigateToSelectedProperty.value = null
    }


    fun TodayAsteroid() {
        removeAsteroid()
       val today= database.NasaDao().getTodayAsteroid(Calendar.DAY_OF_YEAR.toString())

        allAstro.addSource(today) {
            allAstro.value = it
        }

    }

    fun WeekAsteroid() {
        removeAsteroid()
        allAstro.addSource(list) {
            allAstro.value = it
        }

    }

    fun saveAsteroid() {
        removeAsteroid()
        allAstro.addSource(list) {
            allAstro.value = it
        }

    }

    private fun removeAsteroid() {
        allAstro.removeSource(today)
        allAstro.removeSource(list)
    }
}
