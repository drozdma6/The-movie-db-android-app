package cz.cvut.fit.drozdma6.semestral.features.movies.presentation

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import cz.cvut.fit.drozdma6.semestral.databinding.MovieDetailFragmentBinding
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie

class MovieDetailFragment : Fragment() {
    private var _binding: MovieDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailFragmentArgs by navArgs()

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
        val imageBaseUrl = "https://image.tmdb.org/t/p/w154"
        val url = imageBaseUrl + movie.poster_path
        txtDetail.text = movie.overview
        txtOriginalLanguage.text = movie.original_language
        //set dynamic background color
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            val builder = Palette.Builder(loadImageAsBitmap(url))
            builder.generate { palette: Palette? ->
                if (palette?.dominantSwatch != null) {
                    val color = palette.dominantSwatch!!.rgb
                    bookDetailBackground.setBackgroundColor(color)
                    addToWatchlist.setBackgroundColor(color)
                    if (color.isDark()) {
                        addToWatchlist.setTextColor(Color.WHITE)
                    } else {
                        addToWatchlist.setTextColor(Color.BLACK)
                    }
                    activity?.window?.statusBarColor = color
                }
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