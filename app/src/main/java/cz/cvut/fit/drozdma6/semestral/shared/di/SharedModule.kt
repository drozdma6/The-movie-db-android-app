package cz.cvut.fit.drozdma6.semestral.shared.di

import androidx.room.Room
import cz.cvut.fit.drozdma6.semestral.shared.data.room.MovieDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory



val sharedModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    single {
        Room.databaseBuilder(androidContext(), MovieDatabase::class.java, "movies")
            .fallbackToDestructiveMigration()
            .build()
    }
}
