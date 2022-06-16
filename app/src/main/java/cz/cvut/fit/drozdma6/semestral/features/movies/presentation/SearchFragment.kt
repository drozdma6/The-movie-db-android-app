package cz.cvut.fit.drozdma6.semestral.features.movies.presentation

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import cz.cvut.fit.drozdma6.semestral.databinding.SearchFragmentBinding
import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieRepository
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import java.util.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class SearchFragment(
    private val movieRepository: MovieRepository
) : Fragment(), SearchView.OnQueryTextListener {
    private var binding: SearchFragmentBinding? = null
    private val adapter = MoviesAdapter(::navigateToDetail)
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val text = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                firebaseAnalytics.logEvent(
                    "speech_recognition_words",
                    bundleOf(Pair("word", text?.get(0).toString()))
                )

                binding?.searchBar?.setQuery(text?.get(0).toString(), false)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return SearchFragmentBinding.inflate(inflater).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.bind()
    }

    private fun askSpeechInput() {
        if (!SpeechRecognizer.isRecognitionAvailable(requireContext())) {
            Toast.makeText(requireContext(), "Speech is not available", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!")
            resultLauncher.launch(i)
        }
    }

    private fun SearchFragmentBinding.bind() {
        btn.setOnClickListener {
            firebaseAnalytics.logEvent("speech_recognizer_used", bundleOf())
            searchBar.setQuery("", false)
            askSpeechInput()
        }
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerSearch.layoutManager = GridLayoutManager(requireContext(), 3)
        } else {
            recyclerSearch.layoutManager = GridLayoutManager(requireContext(), 6)
        }
        recyclerSearch.adapter = adapter
        searchBar.setOnQueryTextListener(this@SearchFragment)
    }

    private fun navigateToDetail(movie: Movie) {
        val directions = SearchFragmentDirections.toMovieDetailFragment(movie)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query != "") {
            searchDatabase(query)
        } else {
            adapter.submitList(listOf())
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null && query != "") {
            searchDatabase(query)
        } else {
            adapter.submitList(listOf())
        }
        return true
    }

    private fun searchDatabase(query: String?) {
        val searchQuery = "%$query%"
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            movieRepository.getMovieWithTitle(searchQuery).collect { movies ->
                adapter.submitList(movies.sortedBy { movie ->
                    movie.id
                })
            }
        }
    }
}

