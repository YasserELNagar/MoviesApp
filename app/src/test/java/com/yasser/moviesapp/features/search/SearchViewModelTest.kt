package com.yasser.moviesapp.features.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yasser.data_remote.common.AppException
import com.yasser.domain.movies.usecase.SearchForMovieUseCase
import com.yasser.moviesapp.helper.MainDispatcherRule
import com.yasser.moviesapp.core.ui.Pagination
import com.yasser.moviesapp.core.ui.resource.Resource
import com.yasser.moviesapp.helper.assertInitializedAndLoading
import com.yasser.moviesapp.helper.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest{

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var searchForMovieUseCase: SearchForMovieUseCase

    lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SearchViewModel(searchForMovieUseCase)
    }

    @Test
    fun `searchForMovie() with successful api response then expects Success`()= runTest{
        val moviesListStateFlow = viewModel.searchForMoviesStateFlow

        coEvery { searchForMovieUseCase(any(), any()) } returns Pair(emptyList(), true)


        moviesListStateFlow.test {
            viewModel.searchForMovie("", Pagination())

            it.assertInitializedAndLoading()
            assertTrue(it[2] is Resource.SUCCESS<*>)
        }
    }

    @Test
    fun `searchForMovie() with failed api response then expects Error`()= runTest{
        val moviesListStateFlow = viewModel.searchForMoviesStateFlow

        coEvery { searchForMovieUseCase(any(), any()) } throws AppException.NetworkException


        moviesListStateFlow.test {
            viewModel.searchForMovie("", Pagination())

            it.assertInitializedAndLoading()
            assertTrue(it[2] is Resource.ERROR)
        }
    }
}