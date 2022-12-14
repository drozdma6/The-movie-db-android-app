package cz.cvut.fit.drozdma6.semestral.features.movies.presentation

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import cz.cvut.fit.drozdma6.semestral.R
import cz.cvut.fit.drozdma6.semestral.databinding.MovieDetailFragmentBinding
import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieRepository
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie

class MovieDetailFragment(
    private val movieRepository: MovieRepository
) : Fragment() {
    private var _binding: MovieDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private var movieIsInWatchlist = false
    private val args: MovieDetailFragmentArgs by navArgs()
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
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bind(args.movie)
    }

    private fun MovieDetailFragmentBinding.bind(movie: Movie) {
        backBtn.setOnClickListener {
            activity?.onBackPressed()
        }
        txtTitle.text = movie.title
        txtDetail.text = movie.overview
        txtOriginalLanguage.text = movie.original_language
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            val imageBaseUrl = "https://image.tmdb.org/t/p/w154"
            val url = imageBaseUrl + movie.poster_path
            setDynamicBackgrounds(url)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            movieIsInWatchlist = if (movieRepository.isInWatchlist(movie.id)) {
                likeBtn.setImageResource(R.drawable.ic_like_selected)
                true
            } else {
                likeBtn.setImageResource(R.drawable.ic_like_unselected)
                false
            }
        }
        likeBtn.addToWatchlistListener(movie)
        btnAddToWatchlist.setOnClickListener {
            firebaseAnalytics.logEvent("browser_movie_detail_used", bundleOf())
            val uri =  "https://www.themoviedb.org/movie/" + movie.id
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(browserIntent)
        }
    }

    private fun ImageButton.addToWatchlistListener(movie: Movie) {
        setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                try {
                    if (movieIsInWatchlist) {
                        movieRepository.deleteWatchlistMovie(movie)
                        setImageResource(R.drawable.ic_like_unselected)
                        movieIsInWatchlist = false
                    } else {
                        movieRepository.insertWatchlistMovie(movie)
                        setImageResource(R.drawable.ic_like_selected)
                        movieIsInWatchlist = true
                    }
                } catch (e: SQLiteConstraintException) {
                    Snackbar.make(
                        binding.root,
                        "Movie is already in watchlist.",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private suspend fun MovieDetailFragmentBinding.setDynamicBackgrounds(url: String) {
        val builder = Palette.Builder(loadImageAsBitmap(url))
        builder.generate { palette: Palette? ->
            if (palette?.dominantSwatch != null) {
                val color = palette.dominantSwatch!!.rgb
                bookDetailBackground.setBackgroundColor(color)
                btnAddToWatchlist.setBackgroundColor(color)
                if (color.isDark()) {
                    btnAddToWatchlist.setTextColor(Color.WHITE)
                } else {
                    btnAddToWatchlist.setTextColor(Color.BLACK)
                }
                activity?.window?.statusBarColor = color
            }
        }
    }

    private suspend fun loadImageAsBitmap(url: String): Bitmap {
        val loader = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(url)
            .allowHardware(false)
            .target(imageView = binding.imageView)
            .build()
        val result = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun Int.isDark(): Boolean {
        val darkness: Double =
            1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
        return darkness >= 0.5
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.statusBarColor = Color.BLACK
        _binding = null
    }
}