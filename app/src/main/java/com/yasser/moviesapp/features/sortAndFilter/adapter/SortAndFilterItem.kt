package com.yasser.moviesapp.features.sortAndFilter.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SortAndFilterItem(
    val id :Int?=null,
    val name:String?=null,
    val isSelected:Boolean=false
):Parcelable
