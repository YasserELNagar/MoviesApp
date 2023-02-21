package com.yasser.moviesapp.core.ui

import org.junit.Assert
import org.junit.Test

class KotlinKtxKtTest{

    @Test
    fun `getPaginationUpdatedList with empty original list and new list of items then return this new items list`(){
        val originalList = emptyList<String>()
        val newList = listOf("Yasser" ,"Mohamed","Ahmed")
        val actualList = getPaginationUpdatedList(originalList, newList)

        Assert.assertEquals(newList.toString(),actualList.toString())
    }


    @Test
    fun `getPaginationUpdatedList with full original list and new list of items then return this list with both lists items`(){
        val originalList =listOf("Khaled" ,"Fathy","Ali")
        val newList = listOf("Yasser" ,"Mohamed","Ahmed")
        val expectedList = arrayListOf<String>().apply {
            addAll(originalList)
            addAll(newList)
        }
        val actualList = getPaginationUpdatedList(originalList, newList)

        Assert.assertEquals(expectedList,actualList)
    }
}