package cz.cvut.fit.drozdma6.semestral.features.movies.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import cz.cvut.fit.drozdma6.semestral.databinding.WatchlistFragmentBinding
import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieRepository
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie

class WatchListFragment(
    private val movieRepository: MovieRepository
) : Fragment() {
    private var binding: WatchlistFragmentBinding? = null
    private val adapter = MoviesAdapter(::navigateToDetail)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return WatchlistFragmentBinding.inflate(inflater).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding?.recyclerWatchList?.layoutManager = GridLayoutManager(requireContext(), 3)
        } else {
            binding?.recyclerWatchList?.layoutManager = GridLayoutManager(requireContext(), 6)
        }
        binding?.recyclerWatchList?.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            movieRepository.getWatchlistMoviesStream().collect { movies ->
                adapter.submitList(movies)
            }
        }
    }

    private fun navigateToDetail(movie: Movie) {
        val directions = WatchListFragmentDirections.toMovieDetailFragment(movie)
        findNavController().navigate(directions)
    }
}