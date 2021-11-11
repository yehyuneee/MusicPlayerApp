package com.example.musicplayerapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.example.musicplayerapp.MusicApplication
import com.example.musicplayerapp.data.LyricsEntity
import com.example.musicplayerapp.data.MusicEntity
import com.example.musicplayerapp.data.RxLyricsRepository
import java.util.regex.Pattern

/**
 * Rxjava 구조로 리팩토링하는 중
 */
class RxMusicViewModel() : ViewModel() {
    private val musicData: MutableLiveData<MusicEntity> = MutableLiveData()
    private val repo: RxLyricsRepository = RxLyricsRepository()

    var lyricsList: MutableLiveData<LyricsEntity> = MutableLiveData<LyricsEntity>()

    /**
     * 가사 리스트 가공한다.
     */
    @SuppressLint("CheckResult")
    fun getLyricsData() {
        repo.getMusicData().subscribe {
            val file = it.lyrics

            var duration: String? = null
            var lyrics: String? = null

            val lycisList = file.split("\n")
            for (i in lycisList.indices) {
                val pattern = Pattern.compile("\\[(.*?)\\]")
                val matcher = pattern.matcher(lycisList.get(i))
                while (matcher.find()) {
                    duration = matcher.group(1)
                }
                var lyricsEntity = LyricsEntity(duration, lyrics)
                lyricsList.value = lyricsEntity
            }
        }
    }

    fun musicData(): MutableLiveData<MusicEntity> {
        return musicData
    }
}