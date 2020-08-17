package com.example.paging3sample.examplepaging

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paging3sample.Movie
import com.example.paging3sample.api.RetrofitService
import kotlinx.coroutines.flow.Flow


/**
 *
 * Just placeholder for now.
 * We're getting the actual page size from the network(TODO: Check response)
 *
 * **/
private const val NETWORK_PAGE_SIZE = 20

class DummyRepo(private val movieService: RetrofitService) {
    private  val TAG = "DEBUG"



    /**
     *
     * We can return the following APIs: Pager.Flow, Pager.LiveData, Pager.Flowable(RxJava), Pager.Observable(RxJava)
     * In this case, I'll return Flow, so we'll return from the function Flow<PagingData<Movie>>
     *
     * Also, we always need to pass PagingConfig class(No matter which builder we use!)
     *
     * PagingConfig -> This class give us the options to decide how to load content from a PagingSource
     * For example: How far ahead to load, the size request for the initial load, and others.
     *
     * The only REQUIRED parameter we need to pass is the page size - how many items should be loaded in each page.
     *
     * BY DEFAULT Paging library will keep in memory all the pages we load
     * So if we want to ensure that we are not wasting memory, we need to set "maxSize" parameter in PagingConfig.
     *
     * BY DEFAULT Paging library will return null items as a placeholder for content that is not it loaded, if the Paging library can count the unloaded items(movies in our case) and if the enablePlaceholders flag inside PagingConfig set tp true
     * Because of this, we can display a placeholder view inside our adapter(like loading spinner for example)
     *
     *
     * Word by Word notes from google labs:
     *
     * Note: The PagingConfig.pageSize should be enough for several screens' worth of items. If the page is too small, your list might flicker as pages' content doesn't cover the full screen.
     * Larger page sizes are good for loading efficiency, but can increase latency when the list is updated.
     *
     * Note: By default PagingConfig.maxSize is unbounded, so pages are never dropped.
     * If you do want to drop pages, make sure that you keep maxSize to a high enough number that it doesn't result in too many network requests when the user changes the scroll direction.
     * The minimum value is pageSize + prefetchDistance * 2.
     *
     * **/

    fun getMoviesResultsStream(): Flow<PagingData<Movie>> {
        Log.d(TAG, "getMoviesResultsStream: ")
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { MoviePagingSource(movieService) }
        ).flow
    }
}