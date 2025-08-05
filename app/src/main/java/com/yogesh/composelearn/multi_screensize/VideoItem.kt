package com.yogesh.composelearn.multi_screensize

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun VideoItem(modifier: Modifier = Modifier, videoData: VideoData, windowSize: WindowSize) {
    if (getScreenWidth(windowSize.width) == Window.COMPACT) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9)
                .clickable(){ /* Handle click */ }
        ) {
            AsyncImage(
                ImageRequest.Builder(LocalContext.current).data(videoData.videoThumbnailUrl)
                    .build(), null, modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            )
            Text(
                videoData.duration,
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(bottom = 6.dp, end = 4.dp)
                    .background(Color(0xa6000000), shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp)
                    .align(Alignment.BottomEnd)
            )
        }
        Row(
            modifier = Modifier.clickable(){ /* Handle click */ }.padding(start = 8.dp, end = 8.dp, top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                ImageRequest.Builder(LocalContext.current).data(videoData.channelLogoUrl).build(),
                null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
            )
            Column(modifier = Modifier.offset(y= (-2).dp).weight(10f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    videoData.title,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    maxLines = 2
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        videoData.channelName,
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500
                    )
                    Spacer(
                        modifier = Modifier
                            .size(2.dp)
                            .background(Color.DarkGray, CircleShape)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        videoData.views,
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500
                    )
                    Spacer(
                        modifier = Modifier
                            .size(2.dp)
                            .background(Color.DarkGray, CircleShape)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        videoData.uploadTime,
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            IconButton({},modifier = Modifier.offset(y= (-6).dp).weight(1f)) {
                Icon(Icons.Default.MoreVert, contentDescription = "More Options", tint = Color.DarkGray)
            }
        }
    }else{
        Row(modifier = Modifier.clickable(){}) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9)
                    .weight(4.5f)
            ) {
                AsyncImage(
                    ImageRequest.Builder(LocalContext.current).data(videoData.videoThumbnailUrl)
                        .build(), null, modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                )
                Text(
                    videoData.duration,
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .padding(bottom = 6.dp, end = 4.dp)
                        .background(Color(0xa6000000), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            Column(modifier = Modifier.weight(7f).padding(horizontal = 8.dp)) {
                Text(
                    videoData.title,
                    color = Color.Black,
                    fontSize = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.W600
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        videoData.views,
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500
                    )
                    Spacer(
                        modifier = Modifier
                            .size(2.dp)
                            .background(Color.DarkGray, CircleShape)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        videoData.uploadTime,
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        ImageRequest.Builder(LocalContext.current).data(videoData.channelLogoUrl).build(),
                        null,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        videoData.channelName,
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    videoData.videoDescription,
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    maxLines = 3,
                    lineHeight = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )

            }
            IconButton({},modifier = Modifier.offset(y= (-8).dp).weight(1f)) {
                Icon(Icons.Default.MoreVert, contentDescription = "More Options", tint = Color.DarkGray)
            }
        }
    }
}