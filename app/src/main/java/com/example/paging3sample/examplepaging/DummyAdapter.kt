package com.example.paging3sample.examplepaging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.paging3sample.Movie
import com.example.paging3sample.R
import kotlinx.android.synthetic.main.movie_item.view.*

/**
 *
 *   **Header/Footer methods**
 *
 *   * withLoadStateHeader - if we only want to display header
 *
 *   * withLoadStateFooter - if we only want to display footer
 *
 *   * withLoadStateHeaderAndFooter - if we want to display both header and footer
 *
 *
 * **/

class DummyAdapter() :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            (holder as MoviesViewHolder).bind(it)

        }
    }

    class MoviesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var movieImage = itemView.movie_image


        fun bind(item: Movie?) {
            item?.let { movie ->

                val currentUrl = "https://image.tmdb.org/t/p/original${movie.posterPath}"

                Glide.with(itemView.context)
                    .load(currentUrl)
                    .apply(RequestOptions.overrideOf(400, 600))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(movieImage)
            }
        }


    }


}

class DiffUtilCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}