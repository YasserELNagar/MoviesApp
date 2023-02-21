package com.yasser.moviesapp.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yasser.data.movies.model.MovieModel
import com.yasser.data_remote.common.AppException
import com.yasser.domain.movies.usecase.*
import com.yasser.moviesapp.helper.MainDispatcherRule
import com.yasser.moviesapp.core.ui.Pagination
import com.yasser.moviesapp.core.ui.resource.Resource
import com.yasser.moviesapp.helper.assertInitializedAndLoading
import com.yasser.moviesapp.helper.test
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var loadNowPlayingMoviesUseCase: LoadNowPlayingMoviesUseCase

    @MockK
    lateinit var loadPopularMoviesUseCase: LoadPopularMoviesUseCase

    @MockK
    lateinit var loadTopRatedMoviesUseCase: LoadTopRatedMoviesUseCase

    @MockK
    lateinit var getMoviesListUseCase: GetMoviesListUseCase

    @MockK
    lateinit var addToFavoritesUseCase: AddToFavoritesUseCase

    @MockK
    lateinit var removeFromFavoritesUseCase: RemoveFromFavoritesUseCase

    lateinit var viewModel: HomeViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(
            loadNowPlayingMoviesUseCase,
            loadPopularMoviesUseCase,
            loadTopRatedMoviesUseCase,
            getMoviesListUseCase,
            addToFavoritesUseCase,
            removeFromFavoritesUseCase
        )
    }

    @Test
    fun `loadNowPlayingMovies() with successful api response then expects Success`()= runTest{
        val moviesListStateFlow = viewModel.loadMoviesStateFlow


        coEvery { loadNowPlayingMoviesUseCase(any()) } returns true


        moviesListStateFlow.test {
            viewModel.loadNowPlayingMovies(Pagination())

            it.assertInitializedAndLoading()
            assertTrue(it[2] is Resource.SUCCESS)
        }
    }

    @Test
    fun `loadNowPlayingMovies() with failed api response then expects Error`()= runTest{
        val moviesListStateFlow = viewModel.loadMoviesStateFlow


        coEvery { loadNowPlayingMoviesUseCase(any()) } throws AppException.NetworkException

        moviesListStateFlow.test {
            viewModel.loadNowPlayingMovies(Pagination())

            it.assertInitializedAndLoading()
            assertTrue(it[2] is Resource.ERROR)
        }
    }

}