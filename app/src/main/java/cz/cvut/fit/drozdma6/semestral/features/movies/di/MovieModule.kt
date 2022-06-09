package cz.cvut.fit.drozdma6.semestral.features.movies.di

import cz.cvut.fit.drozdma6.semestral.features.movies.MovieDatabaseDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.MovieRepository
import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieRemoteDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.data.retrofit.MovieApiDescription
import cz.cvut.fit.drozdma6.semestral.features.movies.data.retrofit.MovieRetrofitDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.MovieRoomDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.presentation.MoviesViewModel
import cz.cvut.fit.drozdma6.semestral.shared.data.room.MovieDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val movieModule = module {
    factory{
        get<Retrofit>().create(MovieApiDescription::class.java)
    }
    factory<MovieRemoteDataSource>{
        MovieRetrofitDataSource(movieApiDescription = get())
    }
    factory {
        get<MovieDatabase>().getMovieDao()
    }

    factory<MovieDatabaseDataSource>{
        MovieRoomDataSource(movieDao = get())
    }

    single{
        MovieRepository(movieDatabaseDataSource = get(), movieRemoteDataSource = get())
    }

    viewModel{
        MoviesViewModel(movieRepository = get())
    }
}


