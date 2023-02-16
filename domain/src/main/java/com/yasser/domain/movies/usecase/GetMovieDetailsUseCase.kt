package com.yasser.domain.movies.usecase

import com.yasser.data.movies.model.MovieModel
import com.yasser.data.movies.repository.IMovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: IMovieRepository) {
    suspend operator fun invoke(movieId:Int): MovieModel? {
        return repository.getMovieDetails(movieId)
    }
}