package cz.cvut.fit.drozdma6.semestral.features.movies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.drozdma6.semestral.features.movies.MovieRepository
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoviesViewModel(
    val movieRepository: MovieRepository
) : ViewModel() {
    private val _moviesStateStream: MutableLiveData<MoviesState> = MutableLiveData(MoviesState())
    val moviesStream: LiveData<MoviesState> = _moviesStateStream

    fun fetchPopularMovies(){
        viewModelScope.launch {
            movieRepository.getMoviesStream().collect {
                _moviesStateStream.value = _moviesStateStream.value?.copy(movies = it)
            }
        }
        viewModelScope.launch {
            try {
                _moviesStateStream.value = _moviesStateStream.value?.copy(isLoading = true)
                delay(1000)
                movieRepository.fetchPopularMovies()
            } catch (t: Throwable) {
                _moviesStateStream.value = _moviesStateStream.value?.copy(showError = true)
            } finally {
                _moviesStateStream.value = _moviesStateStream.value?.copy(isLoading = false)
            }
        }
    }

    fun hideError() {
        _moviesStateStream.value = _moviesStateStream.value?.copy(showError = false)
    }

}

data class MoviesState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val showError: Boolean = false
)