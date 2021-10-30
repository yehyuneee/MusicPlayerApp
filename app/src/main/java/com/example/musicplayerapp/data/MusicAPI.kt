package com.example.musicplayerapp.data

import retrofit2.Call
import retrofit2.http.GET

/**
 * Retrofit API Interface 정의
 */
interface MusicAPI {
    @GET("/2020-flo/song.json")
    fun getMusicInfo() : Call<MusicEntity>
}