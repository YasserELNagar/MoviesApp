package com.yasser.data.mapper


import com.yasser.data.model.movies.MovieModel
import com.yasser.data_local.movies.entities.MovieEntity
import com.yasser.data_remote.movies.dto.MovieDTO


object RemoteMapper:IMapper<MovieDTO,MovieModel>{
    override fun mapToTarget(item: MovieDTO?): MovieModel? {
        return MovieModel(
            id = item?.id,
            adult = item?.adult,
            backdrop_path = item?.backdrop_path,
            genre_ids = item?.genre_ids,
            original_title = item?.original_title,
            overview = item?.overview,
            popularity = item?.popularity,
            poster_path = item?.poster_path,
            release_date = item?.release_date,
            title = item?.title,
            video = item?.video,
            original_language = item?.original_language,
            vote_average = item?.vote_average,
            vote_count = item?.vote_count,
            addedToFavoriteDate = null
        )
    }

    override fun mapFromTarget(item: MovieModel?): MovieDTO? {
        return MovieDTO(
            id = item?.id,
            adult = item?.adult,
            backdrop_path = item?.backdrop_path,
            genre_ids = item?.genre_ids,
            original_title = item?.original_title,
            overview = item?.overview,
            popularity = item?.popularity,
            poster_path = item?.poster_path,
            release_date = item?.release_date,
            title = item?.title,
            video = item?.video,
            original_language = item?.original_language,
            vote_average = item?.vote_average,
            vote_count = item?.vote_count
        )
    }
}


object LocalMapper:IMapper<MovieEntity,MovieModel>{
    override fun mapToTarget(item: MovieEntity?): MovieModel? {
        return MovieModel(
            id = item?.id,
            adult = item?.adult,
            backdrop_path = item?.backdrop_path,
            genre_ids = item?.genre_ids,
            original_title = item?.original_title,
            overview = item?.overview,
            popularity = item?.popularity,
            poster_path = item?.poster_path,
            release_date = item?.release_date,
            title = item?.title,
            video = item?.video,
            original_language = item?.original_language,
            vote_average = item?.vote_average,
            vote_count = item?.vote_count,
            addedToFavoriteDate = item?.addedToFavoriteDate
        )
    }

    override fun mapFromTarget(item: MovieModel?): MovieEntity? {
        return MovieEntity(
            id = item?.id,
            adult = item?.adult,
            backdrop_path = item?.backdrop_path,
            genre_ids = item?.genre_ids,
            original_title = item?.original_title,
            overview = item?.overview,
            popularity = item?.popularity,
            poster_path = item?.poster_path,
            release_date = item?.release_date,
            title = item?.title,
            video = item?.video,
            original_language = item?.original_language,
            vote_average = item?.vote_average,
            vote_count = item?.vote_count,
            addedToFavoriteDate = item?.addedToFavoriteDate
        )
    }
}