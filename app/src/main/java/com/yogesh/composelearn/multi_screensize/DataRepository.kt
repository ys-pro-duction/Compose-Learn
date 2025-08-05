package com.yogesh.composelearn.multi_screensize

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

class DataRepository {
    private val data = ArrayList<VideoData>()
    fun getData(count: Int = 10): List<VideoData> {
        if (data.size < count){
            generateData(count)
        }
        return data
    }
    private fun generateData(count: Int) {
        data.clear()
        for (i in 1..count){
            data.add( VideoData(
                i,
                LoremIpsum((3..20).random()).values.joinToString(),
                LoremIpsum((1..5).random()).values.joinToString(),
                LoremIpsum((10..30).random()).values.joinToString(),
                "${(1..999).random()}K views",
                "${(1..23).random()} hours ago",
                "${(0..59).random().toString().padStart(2,'0')}:${(0..59).random().toString().padStart(2,'0')}",
                "https://picsum.photos/100/100?random=${(1..999).random()}",
                "https://picsum.photos/910/512?random=${(1..999).random()}"
            ))
        }
    }




}