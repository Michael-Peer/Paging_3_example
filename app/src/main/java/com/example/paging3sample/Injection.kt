package com.example.paging3sample

import androidx.lifecycle.ViewModelProvider
import com.example.paging3sample.api.RetrofitService
import com.example.paging3sample.examplepaging.DummyRepo


object Injection {

    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(): DummyRepo {
        return DummyRepo(RetrofitService)
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository())
    }
}