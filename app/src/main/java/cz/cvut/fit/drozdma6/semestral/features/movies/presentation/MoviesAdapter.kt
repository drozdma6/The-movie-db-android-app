package cz.cvut.fit.drozdma6.semestral.features.movies.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import cz.cvut.fit.drozdma6.semestral.databinding.MovieItemBinding
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie

class MoviesAdapter(
    private val onMovieClick: (Movie) -> Unit
) : ListAdapter<Movie, MoviesAdapter.MovieHolder>(MoviesDiffCallback()) {
    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    class MovieHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, onMovieClick: (Movie) -> Unit) {
            val imageLoadUrl = "https://image.tmdb.org/t/p"
            val imageSize = "/w154"
            binding.poster.load(imageLoadUrl + imageSize + movie.poster_path)
            binding.root.setOnClickListener { onMovieClick(movie) }
        }
    }

    private class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, onMovieClick)
    }
}