package com.yasser.moviesapp.features.sortAndFilter

import com.yasser.domain.movies.usecase.*
import com.yasser.moviesapp.core.ui.BaseViewModel
import com.yasser.moviesapp.features.home.SortingType
import com.yasser.moviesapp.features.sortAndFilter.adapter.SortAndFilterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SortAndFilterViewModel @Inject constructor(
    private val searchForMovieUseCase: SearchForMovieUseCase
) : BaseViewModel() {

    private val _sortingListStateFlow = MutableStateFlow<List<SortAndFilterItem>?>(null)
    val sortingListStateFlow: StateFlow<List<SortAndFilterItem>?> = _sortingListStateFlow

    private var selectedSortingPosition =0


    init {
        _sortingListStateFlow.value=getSortingList()
    }

    private fun getSortingList(): List<SortAndFilterItem> {
        return arrayListOf(
            SortAndFilterItem(
                SortingType.PLAYING_NOW.value,
                "Playing Now",
                true
            ),
            SortAndFilterItem(
                SortingType.POPULAR.value,
                "Popular Movies"
            ),
            SortAndFilterItem(
                SortingType.TOP_RATED.value,
                "TopRated"
            )
        )
    }


    fun setSelectedItem(position: Int) {
        val sortAndFilterItems = sortingListStateFlow.value?.toMutableList()
        sortAndFilterItems?.set(position, sortAndFilterItems[position].copy(isSelected = true))
        sortAndFilterItems?.set(selectedSortingPosition, sortAndFilterItems[selectedSortingPosition].copy(isSelected = false))
        selectedSortingPosition=position
        _sortingListStateFlow.value=sortAndFilterItems
    }

    fun getSelectedSortingItem(): SortAndFilterItem? {
        return sortingListStateFlow.value?.get(selectedSortingPosition)
    }

}