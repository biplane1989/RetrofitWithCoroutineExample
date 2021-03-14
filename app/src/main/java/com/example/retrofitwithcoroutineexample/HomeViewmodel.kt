package com.example.actionlistenerexample.home

import android.app.Application
import androidx.lifecycle.*
import com.example.holidayimage.`object`.ImageItem
import com.example.retrofitwithcoroutineexample.network.*
import kotlinx.coroutines.*
import okhttp3.internal.Util

enum class ExceptionNetwork {
    SERVER_NOT_REACH, NOT_CONNECTED, DATA_NULL
}

class HomeViewmodel(application: Application) : AndroidViewModel(application)  {

    private var _networkData = MutableLiveData<List<ImageItem>>()
    val networkData: LiveData<List<ImageItem>> get() = _networkData

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> get() = _loading

    private val _exceptionNetwork = MutableLiveData<ExceptionNetwork>()
    val exceptionNetWork: LiveData<ExceptionNetwork> get() = _exceptionNetwork

    val network = NetworkFetcherImpl()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(1000)
                val listImage = network.getListImages(application,1000000)

                if (isActive) {
                    withContext(Dispatchers.Main) {
                        _networkData.value = listImage
                        _loading.value = false
                    }
                }
            } catch (e: MyNetworkException) {           // host
                withContext(Dispatchers.Main){
                    when (e.code) {
                        SERVER_NOT_REACH -> {
                            _exceptionNetwork.value = (ExceptionNetwork.SERVER_NOT_REACH)
                        }
                        NOT_CONNECTED -> {
                            _exceptionNetwork.value = (ExceptionNetwork.NOT_CONNECTED)
                        }
                        DATA_NULL -> {
                            _exceptionNetwork.value = (ExceptionNetwork.DATA_NULL)
                        }
                    }
                }
            }
        }

    }

}