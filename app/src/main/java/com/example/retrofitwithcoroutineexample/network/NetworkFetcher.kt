package com.example.retrofitwithcoroutineexample.network

import android.content.Context
import com.example.holidayimage.`object`.ImageItem
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto

interface NetworkFetcher {
    @Throws(MyNetworkException::class)
    suspend fun getListImages(context: Context, page: Int): List<ImageItem>

}