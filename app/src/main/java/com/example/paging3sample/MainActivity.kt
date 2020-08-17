package com.example.paging3sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
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
                adapter = movieAdapter
            }

        }
    }


}