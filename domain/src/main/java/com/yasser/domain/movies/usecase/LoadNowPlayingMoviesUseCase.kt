package com.yasser.domain.movies.usecase

import com.yasser.data.movies.repository.IMovieRepository
import javax.inject.Inject

class LoadNowPlayingMoviesUseCase @Inject constructor(private val repository: IMovieRepository) {
    suspend operator fun invoke(page:Int):Boolean?{
        val loadNowPlayingMovies = repository.loadNowPlayingMovies(page)
        return loadNowPlayingMovies
    }
}