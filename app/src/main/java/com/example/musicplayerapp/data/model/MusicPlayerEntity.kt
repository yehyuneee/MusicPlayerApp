package com.example.musicplayerapp.data.model

import com.google.gson.annotations.SerializedName

data class MusicPlayerEntity(
    @SerializedName("currentPosition")
    var currentPosition: Long,
    @SerializedName("duration")
    var duration: Long,
    @SerializedName("isPlaying")
    var isPlaying: Boolean,
)