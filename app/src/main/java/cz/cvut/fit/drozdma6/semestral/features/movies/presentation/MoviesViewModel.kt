package cz.cvut.fit.drozdma6.semestral.features.movies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieRepository
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoviesViewModel(
    val movieRepository: MovieRepository
) : ViewModel() {
    private val _popularMoviesStateStream: MutableLiveData<MoviesState> = MutableLiveData(MoviesState())
    val popularMoviesStream: LiveData<MoviesState> = _popularMoviesStateStream
    private val _topRatedMoviesStateStream: MutableLiveData<MoviesState> = MutableLiveData(MoviesState())
    val topRatedMoviesStateStream: LiveData<MoviesState> = _topRatedMoviesStateStream

    fun fetchPopularMovies() {
        viewModelScope.launch {
            movieRepository.getPopularMoviesStream().collect {
                _popularMoviesStateStream.value = _popularMoviesStateStream.value?.copy(movies = it)
            }
        }
        viewModelScope.launch {
            try {
                _popularMoviesStateStream.value = _popularMoviesStateStream.value?.copy(isLoading = true)
                delay(1000)
                movieRepository.synchronizePopularMovies()
            } catch (t: Throwable) {
                _popularMoviesStateStream.value = _popularMoviesStateStream.value?.copy(showError = true)
            } finally {
                _popularMoviesStateStream.value = _popularMoviesStateStream.value?.copy(isLoading = false)
            }
        }
    }

    fun fetchTopRatedMovies() {
        viewModelScope.launch {
            movieRepository.getTopRatedMoviesStream().collect {
                _topRatedMoviesStateStream.value = _topRatedMoviesStateStream.value?.copy(movies = it)
            }
        }
        viewModelScope.launch {
            try {
                _topRatedMoviesStateStream.value = _topRatedMoviesStateStream.value?.copy(isLoading = true)
                delay(1000)
                movieRepository.synchronizeTopRatedMovies()
            } catch (t: Throwable) {
                _topRatedMoviesStateStream.value = _topRatedMoviesStateStream.value?.copy(showError = true)
            } finally {
                _topRatedMoviesStateStream.value = _topRatedMoviesStateStream.value?.copy(isLoading = false)
            }
        }
    }

    fun hideError() {
        _popularMoviesStateStream.value = _popularMoviesStateStream.value?.copy(showError = false)
        _topRatedMoviesStateStream.value = _topRatedMoviesStateStream.value?.copy(showError = false)
    }
}

data class MoviesState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val showError: Boolean = false
)