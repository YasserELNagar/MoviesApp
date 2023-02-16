package com.yasser.data.movies.mapper


import com.yasser.data.common.mapper.IMapper
import com.yasser.data.genres.mapper.GenresLocalMapperMapper
import com.yasser.data.genres.model.GenreModel
import com.yasser.data.movies.model.MovieModel
import com.yasser.data_local.genres.entities.GenreEntity
import com.yasser.data_local.movies.entities.MovieEntity
import com.yasser.data_remote.movies.dto.MovieDTO


object MoviesDataMapper : IMapper<MovieDTO, MovieModel> {
    override fun mapToTarget(item: MovieDTO?): MovieModel {
        return MovieModel(
            id = item?.id,
            adult = item?.adult,
            backdrop_path = item?.backdrop_path,
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

    override fun mapFromTarget(item: MovieModel?): MovieDTO {
        return MovieDTO(
            id = item?.id,
            adult = item?.adult,
            backdrop_path = item?.backdrop_path,
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
            genre_ids = item?.genres?.map { it.id ?: 0 }
        )
    }
}
object MoviesRemoteMapper : IMapper<MovieDTO, MovieEntity> {
    override fun mapToTarget(item: MovieDTO?): MovieEntity {
        return MovieEntity(
            id = item?.id,
            adult = item?.adult,
            backdrop_path = item?.backdrop_path,
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
            genres = item?.genre_ids?.map { GenreEntity(id = it,name = null) }
        )
    }
    override fun mapFromTarget(item: MovieEntity?): MovieDTO {
        return MovieDTO(
            id = item?.id,
            adult = item?.adult,
            backdrop_path = item?.backdrop_path,
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
            genre_ids = item?.genres?.map { it.id ?: 0 }
        )
    }
}


object MoviesLocalMapper : IMapper<MovieEntity, MovieModel> {
    override fun mapToTarget(item: MovieEntity?): MovieModel {
        return MovieModel(
            id = item?.id,
            adult = item?.adult,
            backdrop_path = item?.backdrop_path,
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
            genres = item?.genres?.map {
                GenresLocalMapperMapper
                    .mapToTarget(it)
            },
            isFavorite = item?.addedToFavoriteDate != null,
            loadedAtDate = item?.loadedAtDate
        )
    }

    override fun mapFromTarget(item: MovieModel?): MovieEntity {
        return MovieEntity(
            id = item?.id,
            adult = item?.adult,
            backdrop_path = item?.backdrop_path,
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
            addedToFavoriteDate = System.currentTimeMillis(),
            genres = item?.genres?.map {
                GenresLocalMapperMapper
                    .mapFromTarget(it)
            },
            loadedAtDate = item?.loadedAtDate
        )
    }
}