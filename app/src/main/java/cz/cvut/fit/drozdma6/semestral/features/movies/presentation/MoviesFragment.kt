package cz.cvut.fit.drozdma6.semestral.features.movies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import cz.cvut.fit.drozdma6.semestral.databinding.MoviesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {
    private var binding: MoviesFragmentBinding? = null
    private val popularMovieAdapter = MoviesAdapter()
    private val topRatedMovieAdapter = MoviesAdapter()
    private val viewModel by viewModel<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MoviesFragmentBinding.inflate(inflater).also { binding = it }
        viewModel.fetchPopularMovies()
        viewModel.fetchTopRatedMovies()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerMostPopular?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.recyclerMostPopular?.adapter = popularMovieAdapter

        binding?.recyclerTopRated?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.recyclerTopRated?.adapter = topRatedMovieAdapter

        viewModel.popularMoviesStream.observe(viewLifecycleOwner) { moviesState ->
            if (moviesState != null) {
                binding?.progressBar?.isVisible = moviesState.isLoading
                popularMovieAdapter.submitList(moviesState.movies)
                if (moviesState.showError) {
                    Snackbar.make(binding!!.root, "Popular movies fetch failed. ", Snackbar.LENGTH_SHORT)
                        .show()
                    viewModel.hideError()
                }
            }
        }

        viewModel.topRatedMoviesStateStream.observe(viewLifecycleOwner) { moviesState ->
            if (moviesState != null) {
                binding?.progressBar?.isVisible = moviesState.isLoading
                topRatedMovieAdapter.submitList(moviesState.movies)
                if (moviesState.showError) {
                    Snackbar.make(binding!!.root, "Top rated movies fetch failed. ", Snackbar.LENGTH_SHORT)
                        .show()
                    viewModel.hideError()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

