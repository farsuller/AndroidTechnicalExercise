package com.android.technicalexercise.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.technicalexercise.domain.model.WeatherData

@Database(
    entities = [WeatherData::class],
    version = 1,
    exportSchema = true,
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val weatherDao: WeatherDao

    companion object {
        const val DATABASE_NAME = "weather_db"
    }
}
