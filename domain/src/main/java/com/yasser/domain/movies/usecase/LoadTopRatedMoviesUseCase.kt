package com.yasser.domain.movies.usecase

import com.yasser.data.movies.repository.IMovieRepository
import javax.inject.Inject

class LoadTopRatedMoviesUseCase @Inject constructor(private val repository: IMovieRepository) {
    suspend operator fun invoke(page:Int):Boolean?{
        return repository.loadTopRatedMovies(page)
    }
}