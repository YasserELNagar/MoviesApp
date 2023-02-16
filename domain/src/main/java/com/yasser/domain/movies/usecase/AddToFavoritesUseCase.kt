package com.yasser.domain.movies.usecase

import com.yasser.data.movies.model.MovieModel
import com.yasser.data.movies.repository.IMovieRepository

import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(private val repository: IMovieRepository) {
    suspend operator fun invoke(movie: MovieModel){
        repository.addToFavorites(movie)
    }
}