package com.android.technicalexercise.di

import androidx.room.Room
import com.android.technicalexercise.BuildConfig
import com.android.technicalexercise.data.local.WeatherDao
import com.android.technicalexercise.data.local.WeatherDatabase
import com.android.technicalexercise.data.remote.WeatherApi
import com.android.technicalexercise.data.repository.WeatherHistoryRepositoryImpl
import com.android.technicalexercise.data.repository.WeatherRepositoryImpl
import com.android.technicalexercise.domain.repository.WeatherHistoryRepository
import com.android.technicalexercise.domain.repository.WeatherRepository
import com.android.technicalexercise.domain.usecase.AddWeather
import com.android.technicalexercise.domain.usecase.GetWeather
import com.android.technicalexercise.domain.usecase.GetWeathers
import com.android.technicalexercise.domain.usecase.WeatherUseCase
import com.android.technicalexercise.presentation.screen.auth.AuthViewModel
import com.android.technicalexercise.presentation.screen.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single<WeatherApi> {
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            clientBuilder.addInterceptor(logging)
        }

        val client = clientBuilder.build()

        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    single<WeatherDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = WeatherDatabase::class.java,
            name = WeatherDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration(false).build()
    }
    single<WeatherDao> { get<WeatherDatabase>().weatherDao }

    single<WeatherHistoryRepository> { WeatherHistoryRepositoryImpl(dao = get()) }

    single<WeatherRepository> { WeatherRepositoryImpl(weatherApi = get()) }

    single {
        WeatherUseCase(
            getWeather = GetWeather(repository = get()),
            getWeathers = GetWeathers(repository = get()),
            addWeather = AddWeather(repository = get())
        )
    }

    viewModel { HomeViewModel(weatherUseCase = get()) }
    viewModel { AuthViewModel() }

}

