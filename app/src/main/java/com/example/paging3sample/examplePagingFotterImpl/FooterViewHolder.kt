package com.example.paging3sample.examplePagingFotterImpl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.R
import kotlinx.android.synthetic.main.footer_item.view.*

/**
 *
 * This ViewHolder responsible for the footer item.
 * The footer item have several states - loading, retry etc
 * This ViewHolder will determine which item will be visible in which state
 *
 * We pass to the view holder callback function, and we invoke this function when retry button clicked
 * **/


class FooterViewHolder(
    itemView: View,
    retry: () -> Unit
) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.retry_button.setOnClickListener { retry.invoke() }
    }

    /**
     *
     * The "core" function of the view holder.
     * will actually decide which item we'll be visible
     * We pass current state to this function
     *
     * **/
    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            itemView.error_msg.text = loadState.error.localizedMessage
        }
        itemView.apply {
            progress_bar.isVisible = loadState is LoadState.Loading
            retry_button.isVisible = loadState !is LoadState.Loading
            error_msg.isVisible = loadState !is LoadState.Loading
        }
    }


    /**
     * which layout to inflate.
     * We'll be use inside the onCreateViewHolder
     *
     * **/
    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): FooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.footer_item, parent, false)
            return FooterViewHolder(view, retry)
        }
    }

}