package com.yasser.moviesapp.features.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yasser.data.movies.model.MovieModel
import com.yasser.domain.movies.usecase.*
import com.yasser.moviesapp.core.ui.BaseViewModel
import com.yasser.moviesapp.core.ui.Pagination
import com.yasser.moviesapp.core.ui.resource.Resource
import com.yasser.moviesapp.features.sortAndFilter.adapter.SortAndFilterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadNowPlayingMoviesUseCase: LoadNowPlayingMoviesUseCase,
    private val loadPopularMoviesUseCase: LoadPopularMoviesUseCase,
    private val loadTopRatedMoviesUseCase: LoadTopRatedMoviesUseCase,
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : BaseViewModel() {

    private val _moviesListStateFlow = MutableStateFlow<List<MovieModel>?>(null)
    val moviesListStateFlow: StateFlow<List<MovieModel>?> = _moviesListStateFlow

    private val _loadMoviesStateFlow = MutableStateFlow<Resource<Boolean?>>(Resource.Initial())
    val loadMoviesStateFlow: StateFlow<Resource<Boolean?>> = _loadMoviesStateFlow

    val currentPagination = Pagination()

    private var selectedSortingType = SortingType.PLAYING_NOW

    fun getMoviesList() {
        viewModelScope.launch {
            getMoviesListUseCase().collect {
                _moviesListStateFlow.value = it
            }
        }
    }

    fun loadMovies() {
        when (selectedSortingType) {
            SortingType.PLAYING_NOW -> {
                loadNowPlayingMovies(currentPagination)
            }
            SortingType.POPULAR -> {
                loadPopularMovies(currentPagination)
            }
            SortingType.TOP_RATED -> {
                loadTopRatedMovies(currentPagination)
            }
        }
    }

    fun loadNowPlayingMovies(pagination: Pagination) {
        if (!pagination.canPaginate()) return
        viewModelScope.launch {
            try {
                _loadMoviesStateFlow.value = Resource.Loading()
                loadNowPlayingMoviesUseCase(pagination.getCurrentPage())?.let { stillCanPaginate ->
                    _loadMoviesStateFlow.value = Resource.SUCCESS(stillCanPaginate)
                    pagination.increasePage()
                    if (!stillCanPaginate) {
                        pagination.setReachedLastPage()
                    }
                }
            } catch (t: Throwable) {
                _loadMoviesStateFlow.value = Resource.ERROR(t)
            }
        }
    }

    private fun loadPopularMovies(pagination: Pagination) {
        if (!pagination.canPaginate()) return
        viewModelScope.launch {
            try {
                _loadMoviesStateFlow.value = Resource.Loading()
                loadPopularMoviesUseCase(pagination.getCurrentPage())?.let { stillCanPaginate ->
                    _loadMoviesStateFlow.value = Resource.SUCCESS(stillCanPaginate)
                    pagination.increasePage()
                    if (!stillCanPaginate) {
                        pagination.setReachedLastPage()
                    }
                }
            } catch (t: Throwable) {
                _loadMoviesStateFlow.value = Resource.ERROR(t)
            }
        }
    }

    private fun loadTopRatedMovies(pagination: Pagination) {
        if (!pagination.canPaginate()) return
        viewModelScope.launch {
            try {
                _loadMoviesStateFlow.value = Resource.Loading()
                loadTopRatedMoviesUseCase(pagination.getCurrentPage())?.let { stillCanPaginate ->
                    _loadMoviesStateFlow.value = Resource.SUCCESS(stillCanPaginate)
                    pagination.increasePage()
                    if (!stillCanPaginate) {
                        pagination.setReachedLastPage()
                    }
                }
            } catch (t: Throwable) {
                _loadMoviesStateFlow.value = Resource.ERROR(t)
            }
        }
    }

    fun toggleFavoriteStatus(item: MovieModel?) {
        if (item == null) return
        if (item.isFavorite == true) {
            removeFromFavorites(item)
        } else {
            addToFavorites(item)
        }
    }

    private fun addToFavorites(item: MovieModel) {
        viewModelScope.launch {
            addToFavoritesUseCase(item)
        }
    }

    private fun removeFromFavorites(item: MovieModel) {
        viewModelScope.launch {
            removeFromFavoritesUseCase(item)
        }
    }

    fun setSortingType(it: SortAndFilterItem) {
        selectedSortingType= when(it.id){
            SortingType.POPULAR.value->SortingType.POPULAR
            SortingType.TOP_RATED.value->SortingType.TOP_RATED
            else->{SortingType.PLAYING_NOW}
        }
        currentPagination.resetPagination()
        loadMovies()
    }
}