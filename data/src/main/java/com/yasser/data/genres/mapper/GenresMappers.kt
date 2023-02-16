package com.yasser.data.genres.mapper


import com.yasser.data.common.mapper.IMapper
import com.yasser.data.genres.model.GenreModel
import com.yasser.data_local.genres.entities.GenreEntity
import com.yasser.data_remote.genres.dto.GenreDTO


object GenresRemoteMapperMapper : IMapper<GenreDTO, GenreEntity> {
    override fun mapToTarget(item: GenreDTO?): GenreEntity {
        return GenreEntity(
            id = item?.id,
            name = item?.name
        )
    }

    override fun mapFromTarget(item: GenreEntity?): GenreDTO {
        return GenreDTO(
            id = item?.id,
            name = item?.name
        )
    }

}

object GenresLocalMapperMapper : IMapper<GenreEntity, GenreModel> {
    override fun mapToTarget(item: GenreEntity?): GenreModel {
        return GenreModel(
            id = item?.id,
            name = item?.name
        )
    }

    override fun mapFromTarget(item: GenreModel?): GenreEntity {
        return GenreEntity(
            id = item?.id,
            name = item?.name
        )
    }

}

