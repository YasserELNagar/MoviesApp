package com.yasser.moviesapp.features.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yasser.data.movies.model.MovieModel
import com.yasser.moviesapp.R
import com.yasser.moviesapp.core.ui.BaseFragment
import com.yasser.moviesapp.core.ui.loadImageByUrl
import com.yasser.moviesapp.core.ui.paginateWithScrolling
import com.yasser.moviesapp.core.ui.viewDelegates.viewBinding
import com.yasser.moviesapp.databinding.FragmentFavoritesBinding
import com.yasser.moviesapp.databinding.FragmentMovieDetailsBinding
import com.yasser.moviesapp.features.favorites.FavoritesViewModel
import com.yasser.moviesapp.features.movieDetails.adapter.GenresItemsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding,MovieDetailsViewModel>(R.layout.fragment_movie_details) {

    override val binding: FragmentMovieDetailsBinding by viewBinding()
    override val viewModel: MovieDetailsViewModel by viewModels()

    private val genresAdapter : GenresItemsAdapter by lazy {
        GenresItemsAdapter{position, item ->
        }
    }

    override fun init() {
        super.init()
        setupClickListeners()
        setupGenresAdapterRV()
    }

    private fun setupClickListeners() {

        binding.ivFavorite.setOnClickListener {
            toggleFavoriteStatus()
            viewModel.toggleFavoriteStatus()
        }
    }

    private fun setupGenresAdapterRV() {
        binding.rvGenres.apply {
            adapter= genresAdapter
        }
    }

    private fun toggleFavoriteStatus() {
        val isFavorite = viewModel.movieDetailsStateFlow.value?.isFavorite
        binding.ivFavorite.setImageResource(
            if (isFavorite == true)
                R.drawable.ic_love_unselected
            else
                R.drawable.ic_love_selected
        )

    }

    override fun subscribe() {
        super.subscribe()
        subscribeToMovieDetails()
    }

    private fun subscribeToMovieDetails() {
        lifecycleScope.launchWhenCreated {
            viewModel.movieDetailsStateFlow.collect{movie->
                if (movie!=null){
                    bindMovieDetails(movie)
                }
            }
        }
    }

    private fun bindMovieDetails(movie: MovieModel) {
        binding.ivBooster.loadImageByUrl(movie.poster_path?:"")
        binding.tvDate.text=movie.release_date
        binding.tvLanguage.text=movie.original_language?.uppercase()
        binding.tvRating.text=movie.vote_average.toString()
        binding.tvName.text=movie.original_title
        binding.tvOverview.text=movie.overview
        binding.ivFavorite.setImageResource(
            if (movie.isFavorite == true)
                R.drawable.ic_love_selected
            else
                R.drawable.ic_love_unselected
        )
        genresAdapter.submitList(movie.genres)
    }

}