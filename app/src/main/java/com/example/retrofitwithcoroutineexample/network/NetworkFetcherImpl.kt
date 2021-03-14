package com.example.retrofitwithcoroutineexample.network

import android.content.Context
import com.example.holidayimage.`object`.ImageItem
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import okhttp3.internal.Util

class NetworkFetcherImpl() : NetworkFetcher {
    val serversService: NetworkServer = NetworkServer.create()
    override suspend fun getListImages(context: Context, page: Int): List<ImageItem> {
        val result = serversService.getPhotos(page)
        when (result) {
            is Result.Success -> {
                if (result.data != null) {
                    return result.data.map { ImageItem(it.id, it.urls.thumb!!, it.urls.raw!!) }
                } else {
                    throw MyNetworkException(DATA_NULL, "data null")
                }
            }
            is Result.Failure -> {
                throw MyNetworkException(SERVER_NOT_REACH, "not reach")
            }
            is Result.NetworkError -> {
                throw MyNetworkException(NOT_CONNECTED, "no connected")
            }
        }
    }

}