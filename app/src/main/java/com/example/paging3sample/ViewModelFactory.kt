package com.example.paging3sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paging3sample.examplepaging.DummyRepo
import com.example.paging3sample.examplepaging.DummyViewModel


/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val repository: DummyRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DummyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DummyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}