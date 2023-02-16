package com.yasser.moviesapp.features.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yasser.data.movies.model.MovieModel
import com.yasser.domain.movies.usecase.*
import com.yasser.moviesapp.core.ui.BaseViewModel
import com.yasser.moviesapp.core.ui.Pagination
import com.yasser.moviesapp.core.ui.getPaginationUpdatedList
import com.yasser.moviesapp.core.ui.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchForMovieUseCase: SearchForMovieUseCase
) : BaseViewModel() {

    private val _moviesStateFlow = MutableStateFlow<List<MovieModel>?>(null)
    val moviesStateFlow: StateFlow<List<MovieModel>?> = _moviesStateFlow

    private val _searchForMoviesStateFlow = MutableStateFlow<Resource<List<MovieModel>>>(Resource.Initial())
    val searchForMoviesStateFlow: StateFlow<Resource<List<MovieModel>>> = _searchForMoviesStateFlow


    val currentPagination = Pagination()


    fun searchForMovie(query:String,pagination: Pagination=currentPagination) {
        if (!pagination.canPaginate()) return
        viewModelScope.launch {
            try {
                _searchForMoviesStateFlow.value = Resource.Loading()
                searchForMovieUseCase(query,pagination.getCurrentPage())?.let { response ->
                    val paginationUpdatedList = getPaginationUpdatedList(
                        _moviesStateFlow.value ?: emptyList(),
                        response.first as List<MovieModel>
                    )
                    _moviesStateFlow.value=paginationUpdatedList
                    _searchForMoviesStateFlow.value = Resource.SUCCESS(paginationUpdatedList)
                    pagination.increasePage()
                    if (!response.second) {
                        pagination.setReachedLastPage()
                    }
                }
            } catch (t: Throwable) {
                _searchForMoviesStateFlow.value = Resource.ERROR(t)
            }
        }
    }

}