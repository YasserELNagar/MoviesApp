package com.yasser.moviesapp.features.favorites


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
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : BaseViewModel() {

    private val _favoriteMoviesListStateFlow = MutableStateFlow<List<MovieModel>?>(null)
    val favoriteMoviesListStateFlow: StateFlow<List<MovieModel>?> = _favoriteMoviesListStateFlow



    init {
        getMoviesList()
    }

    private fun getMoviesList() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect {
                _favoriteMoviesListStateFlow.value = it
            }
        }
    }

    fun removeFromFavorites(item: MovieModel?) {
        if (item==null) return
        viewModelScope.launch {
            removeFromFavoritesUseCase(item)
        }
    }
}