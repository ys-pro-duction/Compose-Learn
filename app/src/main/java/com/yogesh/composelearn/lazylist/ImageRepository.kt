package com.yogesh.composelearn.lazylist

import org.json.JSONArray
import org.json.JSONObject

class ImageRepository {
    val images = with(
        JSONArray(
            """[
            
      {"name": "SunsetSunsetSunset Beach", "url": "https://picsum.photos/id/101/200/300"},
      {"name": "Mountain View", "url": "https://picsum.photos/id/102/200/300"},
      {"name": "City Skyline", "url": "https://picsum.photos/id/103/200/300"},
      {"name": "Forest Path", "url": "https://picsum.photos/id/104/200/300"},
      {"name": "Ocean Waves", "url": "https://picsum.photos/id/105/200/300"},
      {"name": "Desert Dunes", "url": "https://picsum.photos/id/106/200/300"},
      {"name": "Snowy Peaks", "url": "https://picsum.photos/id/107/200/300"},
      {"name": "Flower Garden", "url": "https://picsum.photos/id/108/200/300"},
      {"name": "Rainy Street", "url": "https://picsum.photos/id/109/200/300"},
      {"name": "Starry Night", "url": "https://picsum.photos/id/110/200/300"}
    ]
    """
        )
    ) {
        val list = ArrayList<ImageModel>(length())
        for (i in 0 until length()){
            val jo = get(i) as JSONObject
            list.add(ImageModel(jo.getString("name"),jo.getString("url")))
        }
        return@with list
    }
}