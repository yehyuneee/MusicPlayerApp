package com.example.musicplayerapp.listener

interface ResponseResultListener<T> {
    fun onSuccess(result: T)
    fun onFail()
}