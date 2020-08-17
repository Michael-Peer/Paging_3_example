package com.example.paging3sample.examplePagingFotterImpl

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 *
 * Adapter for footer loading state
 *
 * For the state method - see the comments in the PagingDataAdapter(DummyAdapter)
 *
 * **/

class MoviesStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<FooterViewHolder>() {
    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        return FooterViewHolder.create(parent, retry)
    }

}