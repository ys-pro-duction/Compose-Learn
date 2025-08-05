package com.yogesh.composelearn.multi_screensize

data class VideoData(
    val id: Int,
    val title: String,
    val channelName: String,
    val videoDescription: String,
    val views: String,
    val uploadTime: String,
    val duration: String,
    val channelLogoUrl: String,
    val videoThumbnailUrl: String
) {
}