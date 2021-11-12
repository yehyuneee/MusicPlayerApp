package com.example.musicplayerapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class LyricsEntity(
    @SerializedName("duration")
    var duration: String?,
    @SerializedName("lyrics")
    var lyrics: String?,
)