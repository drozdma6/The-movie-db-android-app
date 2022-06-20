package cz.cvut.fit.drozdma6.semestral.features.movies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import cz.cvut.fit.drozdma6.semestral.databinding.MoviesFragmentBinding
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {
    private var binding: MoviesFragmentBinding? = null
    private val popularMovieAdapter = MoviesAdapter(::navigateToDetail)
    private val topRatedMovieAdapter = MoviesAdapter(::navigateToDetail)
    private val viewModel by viewModel<MoviesViewModel>()
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MoviesFragmentBinding.inflate(inflater).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerMostPopular?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.recyclerMostPopular?.adapter = popularMovieAdapter

        binding?.recyclerTopRated?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.recyclerTopRated?.adapter = topRatedMovieAdapter

        viewModel.popularMoviesStream.observe(viewLifecycleOwner) { moviesState ->
            if (moviesState != null) {
                binding?.progressBar?.isVisible = moviesState.isLoading
                popularMovieAdapter.submitList(moviesState.movies)
                if (moviesState.showError) showFailSnackbar()
            }
        }

        viewModel.topRatedMoviesStateStream.observe(viewLifecycleOwner) { moviesState ->
            if (moviesState != null) {
                binding?.progressBar?.isVisible = moviesState.isLoading
                topRatedMovieAdapter.submitList(moviesState.movies)
                if (moviesState.showError){
                    firebaseAnalytics.logEvent("failed_to_fetch_movies", bundleOf())
                    showFailSnackbar()
                }
            }
        }
    }

    private fun showFailSnackbar() {
        Snackbar.make(
            binding!!.root,
            "Failed to load movies. ",
            Snackbar.LENGTH_SHORT
        )
            .show()
        viewModel.hideError()
    }

    private fun navigateToDetail(movie: Movie) {
        val directions = MoviesFragmentDirections.toMovieDetailFragment(movie)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

