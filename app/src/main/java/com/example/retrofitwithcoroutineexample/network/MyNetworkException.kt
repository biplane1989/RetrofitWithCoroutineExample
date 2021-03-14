package com.example.retrofitwithcoroutineexample.network

import java.lang.Exception

const val SERVER_NOT_REACH = 2
const val NOT_CONNECTED = 4
const val DATA_NULL = 5
class MyNetworkException(val code: Int, message: String) : Exception(message) {}