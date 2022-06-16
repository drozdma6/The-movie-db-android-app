package cz.cvut.fit.drozdma6.semestral.features.movies.di

import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieDatabaseDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieRemoteDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieRepository
import cz.cvut.fit.drozdma6.semestral.features.movies.data.retrofit.MovieApiDescription
import cz.cvut.fit.drozdma6.semestral.features.movies.data.retrofit.MovieRetrofitDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.MovieRoomDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.presentation.MovieDetailFragment
import cz.cvut.fit.drozdma6.semestral.features.movies.presentation.MoviesViewModel
import cz.cvut.fit.drozdma6.semestral.features.movies.presentation.SearchFragment
import cz.cvut.fit.drozdma6.semestral.features.movies.presentation.WatchListFragment
import cz.cvut.fit.drozdma6.semestral.shared.data.room.MovieDatabase
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val movieModule = module {
    factory {
        get<Retrofit>().create(MovieApiDescription::class.java)
    }
    factory<MovieRemoteDataSource> {
        MovieRetrofitDataSource(movieApiDescription = get())
    }
    factory {
        get<MovieDatabase>().getPopularMovieDao()
    }

    factory {
        get<MovieDatabase>().getTopRatedMovieDao()
    }

    factory{
        get<MovieDatabase>().getWatchlistMovieDao()
    }

    factory<MovieDatabaseDataSource> {
        MovieRoomDataSource(popularMovieDao = get(), topRatedMovieDao = get(), watchlistMovieDao = get())
    }

    single {
        MovieRepository(movieDatabase = get(), movieRemoteDataSource = get())
    }

    viewModel {
        MoviesViewModel(movieRepository = get())
    }

    fragment{
        MovieDetailFragment(movieRepository = get())
    }

    fragment{
        WatchListFragment(movieRepository = get())
    }

    fragment{
        SearchFragment(movieRepository = get())
    }
}


