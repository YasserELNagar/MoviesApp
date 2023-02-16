package com.yasser.moviesapp.features.search

import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yasser.data.movies.model.MovieModel
import com.yasser.moviesapp.R
import com.yasser.moviesapp.core.ui.BaseFragment
import com.yasser.moviesapp.core.ui.handleError
import com.yasser.moviesapp.core.ui.openKeyBoard
import com.yasser.moviesapp.core.ui.paginateWithScrolling
import com.yasser.moviesapp.core.ui.resource.Resource
import com.yasser.moviesapp.core.ui.viewDelegates.viewBinding
import com.yasser.moviesapp.databinding.FragmentSearchBinding
import com.yasser.moviesapp.features.search.adapter.SearchMoviesItemsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding,SearchViewModel>(R.layout.fragment_search) {

    override val binding: FragmentSearchBinding by viewBinding()
    override val viewModel: SearchViewModel by viewModels()

    private val  searchMoviesItemsAdapter: SearchMoviesItemsAdapter by lazy {
        SearchMoviesItemsAdapter{position, item ->
            openMovieDetails(item)
        }
    }

    private fun openMovieDetails(item: MovieModel?) {
        item?.let {
            val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(item)
            findNavController().navigate(action)
        }
    }

    override fun init() {
        super.init()
        handleSearchEvent()
        setupSearchMoviesItemsRV()
    }

    private fun handleSearchEvent() {
        binding.vSearchAndFilter.edtSearchQuery.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchForMovie(binding.vSearchAndFilter.edtSearchQuery.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
        requireContext().openKeyBoard(
            binding.vSearchAndFilter.edtSearchQuery
        )
        binding.vSearchAndFilter.cvFilter.isVisible=false
    }

    private fun searchForMovie(queryText: String?) {
        if (!queryText.isNullOrEmpty()){
            viewModel.searchForMovie(queryText)
        }
    }

    private fun setupSearchMoviesItemsRV() {
        binding.rvMovies.apply {
            adapter= searchMoviesItemsAdapter
            paginateWithScrolling(
                { viewModel.currentPagination.canPaginate()
                        && binding.vSearchAndFilter.edtSearchQuery.text.toString().isNotEmpty() }){

                viewModel.searchForMovie(binding.vSearchAndFilter.edtSearchQuery.text.toString())
            }
        }
    }

    override fun subscribe() {
        super.subscribe()
        subscribeToSearchForMoviesStateFlow()
        subscribeToMoviesStateFlow()
    }

    private fun subscribeToMoviesStateFlow() {
        lifecycleScope.launchWhenCreated {
            viewModel.moviesStateFlow.collect{
                searchMoviesItemsAdapter.submitList(it)
            }
        }
    }

    private fun subscribeToSearchForMoviesStateFlow() {
        lifecycleScope.launchWhenCreated {
            viewModel.searchForMoviesStateFlow.collect{state->
                when(state){
                    is Resource.Loading->{
                        handleLoading(true)
                    }
                    is Resource.SUCCESS->{
                        handleLoading(false)
                    }
                    is Resource.ERROR->{
                        handleLoading(false)
                        requireContext().handleError(state.error)
                    }
                    else ->{}
                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible=isLoading
    }


}