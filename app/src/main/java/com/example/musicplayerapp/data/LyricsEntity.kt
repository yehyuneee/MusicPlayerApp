package com.example.musicplayerapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lyricsList")
data class LyricsEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "duration")
    var duration: String?,

    @ColumnInfo(name = "lyrics")
    var lyrics: String?,
)