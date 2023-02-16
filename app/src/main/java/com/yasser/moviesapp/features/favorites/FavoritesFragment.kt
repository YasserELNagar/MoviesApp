package com.yasser.moviesapp.features.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yasser.data.movies.model.MovieModel
import com.yasser.moviesapp.R
import com.yasser.moviesapp.core.ui.BaseFragment
import com.yasser.moviesapp.core.ui.paginateWithScrolling
import com.yasser.moviesapp.core.ui.viewDelegates.viewBinding
import com.yasser.moviesapp.databinding.FragmentFavoritesBinding
import com.yasser.moviesapp.databinding.FragmentHomeBinding
import com.yasser.moviesapp.features.favorites.adapter.MovieItemActionType
import com.yasser.moviesapp.features.favorites.adapter.MoviesItemsAdapter
import com.yasser.moviesapp.features.home.HomeFragmentDirections
import com.yasser.moviesapp.features.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding,FavoritesViewModel>(R.layout.fragment_favorites) {

    override val binding: FragmentFavoritesBinding by viewBinding()
    override val viewModel: FavoritesViewModel by viewModels()

    private val moviesAdapter : MoviesItemsAdapter by lazy {
        MoviesItemsAdapter{ position, item, actionType ->
            when(actionType){
                MovieItemActionType.VIEW_MOVIE->{
                    openMovieDetails(item)
                }
                MovieItemActionType.REMOVE_FAVORITE->{
                    viewModel.removeFromFavorites(item)
                }
            }
        }
    }

    private fun openMovieDetails(item: MovieModel?) {
        item?.let {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun init() {
        super.init()
        setupMoviesAdapterRV()
    }

    private fun setupMoviesAdapterRV() {
        binding.rvMovies.apply {
            adapter= moviesAdapter
        }
    }
    override fun subscribe() {
        super.subscribe()
        subscribeToMoviesListStateFlow()
    }

    private fun subscribeToMoviesListStateFlow() {
        lifecycleScope.launchWhenCreated {
            viewModel.favoriteMoviesListStateFlow.collect{
                moviesAdapter.submitList(it)
            }
        }
    }

}