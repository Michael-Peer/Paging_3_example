package com.example.paging3sample.examplepaging

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paging3sample.Movie
import kotlinx.coroutines.flow.Flow

class DummyViewModel(private val repo: DummyRepo) : ViewModel() {
    private val TAG = "DEBUG"


    /**
     *
     * In Paging 3 library, we don't need to convert our Flow to LiveData.
     * Instead, we'll have a private Flow<PagingData<Movies>>(The result we are getting from repository function)
     *
     * Instead of using LiveData for each new query, we can use String.
     * This will help us ensure that whenever we get a new search query that is the same as the current query, we'll just return the existing Flow, and not a new one.
     * We only need to call repo.getResult(repo.getMoviesResultsStream in our case) if, and only if, the new search query is different from the previous one.
     *
     * ALSO Flow<PagingData> has a cachedIn() method, that allows us to cache the content of a Flow<PagingData> in a CoroutineScope(Since we're in viewModel, we'll use viewModelScope)
     *
     * NOTE(word by word from google lab):
     * If you're doing any operations on the Flow, like map or filter, make sure you call cachedIn after you execute these operations to ensure you don't need to trigger them again.
     *
     * **/

    init {
        Log.d(TAG, ": View Model Created")
    }




    /**
     *
     * Query
     *
     * **/
    private var currentQueryValue: String? = null

    /**
     *
     * Result
     *
     * **/
    private var currentSearchResult: Flow<PagingData<Movie>>? = null

    /**
     *
     * Actual operation
     *
     * **/
    fun searchMovies(): Flow<PagingData<Movie>> {
        Log.d(TAG, "searchMovies: ")
        val lastResult = currentSearchResult
        /**
         *
         * Here we check if it is the same query as the previous one AND the last result we got NOT equal to null.
         * If it is same query and not null, we just returning the last result.
         *
         * **/
//        if (queryString == currentQueryValue && lastResult != null) {
//            return lastResult
//        }

        /**
         *
         * Set the new result
         *
         * **/
//        currentQueryValue = queryString

//        val newResult: Flow<PagingData<Movie>> = repo.getMoviesResultsStream(queryString)
        val newResult: Flow<PagingData<Movie>> = repo.getMoviesResultsStream()
            .cachedIn(viewModelScope) //cached the result inside CoroutineScope

        return newResult
    }

}