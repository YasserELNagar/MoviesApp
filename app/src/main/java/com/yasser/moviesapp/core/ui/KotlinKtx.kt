package com.yasser.moviesapp.core.ui

fun <T> getPaginationUpdatedList(originalList:List<T>,apiList:List<T>): MutableList<T> {
    val newList = mutableListOf<T>()
    if (!originalList.isNullOrEmpty() && !apiList.isNullOrEmpty()) {
        newList.addAll(originalList)
        newList.addAll(apiList)
    } else if (!apiList.isNullOrEmpty()) {
        newList.addAll(apiList)
    }
    return newList
}