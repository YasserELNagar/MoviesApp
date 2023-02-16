package com.yasser.domain.movies.usecase

import com.yasser.data.movies.model.MovieModel
import com.yasser.data.movies.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(private val repository: IMovieRepository) {
    suspend operator fun invoke(): Flow<List<MovieModel>?> {
        return repository.getFavoriteMovies()
    }
}