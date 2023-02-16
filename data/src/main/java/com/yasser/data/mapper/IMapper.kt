package com.yasser.data.mapper

interface IMapper<Source,Target> {
    fun mapToTarget(item:Source?):Target?
    fun mapFromTarget(item: Target?):Source?
}