package com.yasser.moviesapp.features.home

import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yasser.moviesapp.core.ui.viewDelegates.viewBinding
import com.yasser.data.movies.model.MovieModel
import com.yasser.moviesapp.R
import com.yasser.moviesapp.core.ui.BaseFragment
import com.yasser.moviesapp.core.ui.handleError
import com.yasser.moviesapp.core.ui.paginateWithScrolling
import com.yasser.moviesapp.core.ui.resource.Resource
import com.yasser.moviesapp.databinding.FragmentHomeBinding
import com.yasser.moviesapp.features.home.adapter.MovieItemActionType
import com.yasser.moviesapp.features.home.adapter.MoviesItemsAdapter
import com.yasser.moviesapp.features.sortAndFilter.SortAndFilterFragment.Companion.FILTER_AND_SORT
import com.yasser.moviesapp.features.sortAndFilter.SortAndFilterFragment.Companion.SELECTED_SORTING_TYPE
import com.yasser.moviesapp.features.sortAndFilter.adapter.SortAndFilterItem
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding: FragmentHomeBinding by viewBinding()
    override val viewModel: HomeViewModel by viewModels()

    private val moviesAdapter: MoviesItemsAdapter by lazy {
        MoviesItemsAdapter { position, item, actionType ->
            when (actionType) {
                MovieItemActionType.VIEW_MOVIE -> {
                    openMovieDetails(item)
                }
                MovieItemActionType.TOGGLE_FAVORITE -> {
                    viewModel.toggleFavoriteStatus(item)
                }
            }
        }
    }

    private fun openMovieDetails(item: MovieModel?) {
        item?.let {
            val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun init() {
        super.init()
        viewModel.getMoviesList()
        viewModel.loadMovies()

        setupMoviesAdapterRV()
        setupClickListeners()

    }

    private fun setupClickListeners() {
        binding.vSearchAndFilter.cvFilter.setOnClickListener {
            openFilter()
        }
        binding.vSearchAndFilter.edtSearchQuery.isFocusable = false
        binding.vSearchAndFilter.edtSearchQuery.isClickable = true
        binding.vSearchAndFilter.edtSearchQuery.setOnClickListener {
            openSearch()
        }
    }

    private fun openFilter() {
        val action = HomeFragmentDirections.actionHomeFragmentToSortAndFilterFragment()
        findNavController().navigate(action)
    }

    private fun openSearch() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun setupMoviesAdapterRV() {
        binding.rvMovies.apply {
            adapter = moviesAdapter
            paginateWithScrolling({ viewModel.currentPagination.canPaginate() }) {
                viewModel.loadMovies()
            }
        }
    }

    override fun subscribe() {
        super.subscribe()
        subscribeToMoviesListStateFlow()
        subscribeToLoadMoviesListStateFlow()
        subscribeToSortAndFilterResult()
    }

    private fun subscribeToSortAndFilterResult() {
        setFragmentResultListener(FILTER_AND_SORT) { requestKey, bundle ->
            val selectedSortingItem =bundle.getParcelable<SortAndFilterItem>(SELECTED_SORTING_TYPE)
            selectedSortingItem?.let {
                viewModel.setSortingType(it)
            }

        }
    }

    private fun subscribeToLoadMoviesListStateFlow() {
        lifecycleScope.launchWhenCreated {
            viewModel.loadMoviesStateFlow.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        handleLoading(true)
                    }
                    is Resource.SUCCESS -> {
                        handleLoading(false)
                    }
                    is Resource.ERROR -> {
                        handleLoading(false)
                        requireContext().handleError(state.error)
                    }
                    else -> {}
                }
            }
        }
    }


    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun subscribeToMoviesListStateFlow() {
        lifecycleScope.launchWhenCreated {
            viewModel.moviesListStateFlow.collect {
                moviesAdapter.submitList(it)
            }
        }
    }

}