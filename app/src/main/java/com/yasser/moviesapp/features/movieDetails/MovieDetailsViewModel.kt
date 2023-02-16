package com.yasser.moviesapp.features.movieDetails


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yasser.data.movies.model.MovieModel
import com.yasser.domain.movies.usecase.*
import com.yasser.moviesapp.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : BaseViewModel() {

    private val _movieDetailsStateFlow = MutableStateFlow<MovieModel?>(null)
    val movieDetailsStateFlow: StateFlow<MovieModel?> = _movieDetailsStateFlow

    init {
        val movie= savedStateHandle.get<MovieModel>("movie")
        setMovieDetails(movie)
    }

    private fun setMovieDetails(movie: MovieModel?) {
        _movieDetailsStateFlow.value = movie
    }

    fun toggleFavoriteStatus() {
        val item = movieDetailsStateFlow.value ?: return
        if (item.isFavorite == true) {
            removeFromFavorites(item)
            setMovieDetails(item.copy(isFavorite = false))
        } else {
            addToFavorites(item)
            setMovieDetails(item.copy(isFavorite = true))
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

}