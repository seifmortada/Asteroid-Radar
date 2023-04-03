package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid


@Database(entities = [Asteroid::class], version = 2)
abstract class NasaDatabase : RoomDatabase(){

    abstract fun NasaDao(): NasaDAO

    companion object {
        private var instance: NasaDatabase? = null

        fun getInstance(context: Context): NasaDatabase {
            if (instance == null) {
                instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        NasaDatabase::class.java,
                        "asteroid-db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }


}