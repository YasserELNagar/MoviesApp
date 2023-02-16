package com.yasser.moviesapp.features.sortAndFilter

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yasser.moviesapp.R
import com.yasser.moviesapp.core.ui.BaseBottomSheetDialogFragment
import com.yasser.moviesapp.core.ui.viewDelegates.viewBinding
import com.yasser.moviesapp.databinding.FragmentSortAndFilterBinding
import com.yasser.moviesapp.features.home.SortingType
import com.yasser.moviesapp.features.sortAndFilter.adapter.SortAndFilterItem
import com.yasser.moviesapp.features.sortAndFilter.adapter.SortAndFilterItemsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SortAndFilterFragment :
    BaseBottomSheetDialogFragment<FragmentSortAndFilterBinding, SortAndFilterViewModel>(R.layout.fragment_sort_and_filter) {

    override val binding: FragmentSortAndFilterBinding by viewBinding()
    override val viewModel: SortAndFilterViewModel by viewModels()

    private val sortingAdapter: SortAndFilterItemsAdapter by lazy {
        SortAndFilterItemsAdapter { position, item ->
            viewModel.setSelectedItem(position)
        }
    }

    override fun init() {
        super.init()
        setClickListners()
        setupSortingRV()
    }

    private fun setClickListners() {
        binding.btnReset.setOnClickListener {
            dismiss()
        }
        binding.btnApply.setOnClickListener {
            returnSelectedFilterData()
            dismiss()
        }
    }

    private fun returnSelectedFilterData() {
        val selectedSortingItem = viewModel.getSelectedSortingItem()
        setFragmentResult(
            FILTER_AND_SORT,
            bundleOf(
                SELECTED_SORTING_TYPE to selectedSortingItem
            )
        )
    }

    private fun setupSortingRV() {
        binding.rvSorting.apply {
            adapter = sortingAdapter
        }
    }

    override fun subscribe() {
        super.subscribe()
        subscribeToSortingList()
    }

    private fun subscribeToSortingList() {
        lifecycleScope.launchWhenCreated {
            viewModel.sortingListStateFlow.collect {
                sortingAdapter.submitList(it)
            }
        }
    }

    companion object {
        const val FILTER_AND_SORT = "filter_and_sort"
        const val SELECTED_SORTING_TYPE = "selected_sorting"
    }
}