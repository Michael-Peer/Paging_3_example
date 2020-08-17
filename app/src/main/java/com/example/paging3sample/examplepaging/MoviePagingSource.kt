package com.example.paging3sample.examplepaging

import android.util.Log
import androidx.paging.PagingSource
import com.example.paging3sample.Constants
import com.example.paging3sample.Movie
import com.example.paging3sample.api.RetrofitService
import kotlinx.coroutines.FlowPreview
import retrofit2.HttpException
import java.io.IOException


private const val MOVIES_STARTING_PAGE_INDEX = 1

/**
 *
 * The PagingSource implementation defines the source of data and how to retrieve data from that source
 *
 *
 * Int - The type of the paging key
 *
 * Movies - the type of the object
 *
 * MovieService - Where is the data retrieved from
 *
 * **/

@FlowPreview
class MoviePagingSource(
    private val movieService: RetrofitService
) : PagingSource<Int, Movie>() {
    private val TAG = "DEBUG"

    /**
     *
     * LoadParams keeps info related to the load operation, including:
     * 1)The key of the page to be loaded -> if this the first time we load, LoadParams.Key will be null
     * 2)Load size -> the requested number of items to load
     *
     * LoadResult can hold the follows:
     * 1) LoadResult.Page, if the result was successful.
     * 2) LoadResult.Error, in case of error.
     *
     *
     *
     * **/
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        Log.d(TAG, "load: ")
        /**
         * Here we set the position("Page Number"), if it is null, it's mean it is the first page
         *
         * **/
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX

        Log.d(TAG, "load: position $position")

        return try {

            /**
             *
             * Network call
             *
             * **/
            val response =
                movieService.apiService.getAllMovies(Constants.API_KEY, position)

            Log.d(TAG, "load: response ${response.page}")

            /**
             *
             * Actual Data
             *
             * **/
            //TODO: !!!!! REMOVE THE "!!" MARK AND SET IT TO EMPTY LIST IN THE MODEL
            val movies = response.movies!!


            Log.d(TAG, "load: movies ${movies.size}")

            /**
             *
             * As I mentioned above, LoadResult.Page will hold the actual data, the previous key and the next key
             * prev key: if the current position == to starter position, it's mean we haven't prev key so it's null
             * OTHERWISE it will be the current position -1
             *
             * next key: if the data(movie in this case) is empty, it's mean there is no data so there is no next key, and we set it to null
             * OTHERWISE it will be the current position +1
             *
             *
             * **/
            LoadResult.Page(
                //TODO: !!!!! REMOVE THE "!!" MARK AND SET IT TO EMPTY LIST IN THE MODEL
                data = movies,
                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )

        }

        // Handle errors in this blocks

        catch (e: IOException) {
            Log.d(TAG, "IOException: ")
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException: ")
            return LoadResult.Error(e)
        }
    }
//
//    override suspend fun load(
//        params: LoadParams<Int>,
//        stateEvent: StateEvent
//    ): LoadResult<Int, DataState<MainScreenViewState>> {
//        Log.d(TAG, "load: ")
//        /**
//         * Here we set the position("Page Number"), if it is null, it's mean it is the first page
//         *
//         * **/
//        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX
//
//        Log.d(TAG, "load: position $position")
//
//        return try {
//
//            /**
//             *
//             * Network call
//             *
//             * **/
//            val response =
//                movieService.apiService.getAllMovies(Constants.API_KEY, position)
//
//            val apiResult = safeApiCall(Dispatchers.IO) { response }
//
//            val result: Flow<DataState<ViewState>> = flow {
//                when (apiResult) {
//                    is ApiResult.GenericError -> {
//                        emit(
//                            buildError<ViewState>(
//                                apiResult.errorMessage?.let { it } ?: UNKNOWN_ERROR,
//                                UIComponentType.Dialog,
//                                stateEvent
//                            )
//                        )
//                    }
//
//                    is ApiResult.NetworkError -> {
//                        emit(
//                            buildError<ViewState>(
//                                NETWORK_ERROR,
//                                UIComponentType.Dialog,
//                                stateEvent
//                            )
//                        )
//                    }
//                    is ApiResult.Success -> {
//                        if (apiResult.value == null) {
//                            emit(
//                                buildError<ViewState>(
//                                    UNKNOWN_ERROR,
//                                    UIComponentType.Dialog,
//                                    stateEvent
//                                )
//                            )
//                        }
//                    }
//                }
//            }
//
//
//            /**
//             *
//             * Actual Data
//             *
//             * **/
//            //TODO: !!!!! REMOVE THE "!!" MARK AND SET IT TO EMPTY LIST IN THE MODEL
//            val movies = response.movies!!
//
//
//            Log.d(TAG, "load: movies ${movies.size}")
//
//            /**
//             *
//             * As I mentioned above, LoadResult.Page will hold the actual data, the previous key and the next key
//             * prev key: if the current position == to starter position, it's mean we haven't prev key so it's null
//             * OTHERWISE it will be the current position -1
//             *
//             * next key: if the data(movie in this case) is empty, it's mean there is no data so there is no next key, and we set it to null
//             * OTHERWISE it will be the current position +1
//             *
//             *
//             * **/
//            LoadResult.Page(
//                //TODO: !!!!! REMOVE THE "!!" MARK AND SET IT TO EMPTY LIST IN THE MODEL
//                data = result,
//                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position - 1,
//                nextKey = if (movies.isEmpty()) null else position + 1
//            )
//
//        }
//    }

}