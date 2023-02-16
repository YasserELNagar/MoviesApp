package com.yasser.domain.movies.usecase

import com.yasser.data.movies.model.MovieModel
import com.yasser.data.movies.repository.IMovieRepository
import javax.inject.Inject

class SearchForMovieUseCase @Inject constructor(private val repository: IMovieRepository) {
    suspend operator fun invoke(query:String,page:Int):Pair<List<MovieModel>?,Boolean>?{
        return repository.searchForMovie(query, page)
    }
}