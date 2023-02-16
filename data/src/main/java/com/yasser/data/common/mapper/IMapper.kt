package com.yasser.data.common.mapper

interface IMapper<Source,Target> {
    fun mapToTarget(item:Source?):Target?
    fun mapFromTarget(item: Target?):Source?
}