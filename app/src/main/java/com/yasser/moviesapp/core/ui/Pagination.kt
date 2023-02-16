package com.yasser.moviesapp.core.ui

import timber.log.Timber

data class Pagination(
    private var currentPage: Int = 1,
    private var reachedLastPage :Boolean= false
){
    fun getCurrentPage(): Int {
        return currentPage?:1
    }

    fun increasePage(){
        currentPage=currentPage.inc()
    }

    fun canPaginate(): Boolean {
        Timber.tag("testing_scrolling").v("canPaginate invoke reachedLastPage:$reachedLastPage")

        return !reachedLastPage
    }

    fun setReachedLastPage(){
        reachedLastPage=true
    }

    fun resetPagination(){
        currentPage=1
        reachedLastPage=false
    }
}
