package com.example.paging3sample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paging3sample.examplePagingFotterImpl.MoviesStateAdapter
import com.example.paging3sample.examplepaging.DummyAdapter
import com.example.paging3sample.examplepaging.DummyViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val TAG = "DEBUG"

    private lateinit var viewModel: DummyViewModel
    private lateinit var movieAdapter: DummyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: Activty created")

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(DummyViewModel::class.java)
        Log.d(TAG, "onCreate: ViewModel created")

        initAdapter()

        initSearch()




    }

//    private fun initSearch() {
//        Log.d(TAG, "initSearch")
//        lifecycleScope.launch {
//            viewModel.searchMovies().collectLatest {
//                Log.d(TAG, "initSearch: collectLatest")
//                it.let {pagingData ->
//                    pagingData.map { movie ->
//                        Log.d(TAG, "initSearch map movie: $movie")
//                    }
//
//                }
//                movieAdapter.submitData(it)
//            }
//        }
//    }

    private fun initSearch() {
        lifecycleScope.launch {
            viewModel.searchMovies().collectLatest { it ->
                it?.let {
                    movieAdapter.submitData(it)
                }
            }
        }
    }

    private fun initAdapter() {
        Log.d(TAG, "initAdapter: ")
        movies_recycler_view?.apply {
            context?.let {
                layoutManager = GridLayoutManager(this@MainActivity, 3)
                movieAdapter = DummyAdapter()
                adapter
            }
        }

        /**
         *
         * Init states
         *
         * We set the "main" adapter(DummyAdapter) with load states.
         * Inside the load state we specify header/footer(depend what we are choosing), and set the MovieStateAdapter to each one of then
         *
         * Remember: The purpose of MoviesStateAdapter which state to show/which items will be visible depend on the current state
         *
         * **/
        movies_recycler_view.adapter = movieAdapter.withLoadStateHeaderAndFooter(
            header = MoviesStateAdapter { movieAdapter.retry() }, // retry  is a paging library func
            footer = MoviesStateAdapter { movieAdapter.retry() }
        )

        /**
         *
         * Listener to retry button in main activity
         *
         *
         * **/
        retry_button.setOnClickListener {
            movieAdapter.retry()
        }

        /**
         *
         * Listen and react to load state changes
         *
         * **/
        movieAdapter.addLoadStateListener { loadState ->

            // Only show the list if refresh succeeds
            movies_recycler_view.isVisible = loadState.source.refresh is LoadState.NotLoading

            // Only show progress bar when we initial load -t refresh
            progress_bar.isVisible = loadState.source.refresh is LoadState.Loading

            // Only show if initial load or refresh fails
            retry_button.isVisible = loadState.source.refresh is LoadState.Error

            //Show toast on error
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(this, "Error occurred ${it.error}", Toast.LENGTH_SHORT).show()
            }


        }
    }


}