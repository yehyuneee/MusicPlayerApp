package com.example.musicplayerapp.viewmodel

import androidx.lifecycle.*
import com.example.musicplayerapp.MusicApplication
import com.example.musicplayerapp.data.LyricsEntity
import com.example.musicplayerapp.data.MusicEntity
import com.example.musicplayerapp.data.RxLyricsRepository

/**
 * Rxjava 구조로 리팩토링하는 중
 */
class RxMusicViewModel(application: MusicApplication) : ViewModel() {
    private val musicData: MutableLiveData<MusicEntity> = MutableLiveData()
    private val repo: RxLyricsRepository = RxLyricsRepository(application)

    var lyricsList: MutableLiveData<LyricsEntity> = MutableLiveData<LyricsEntity>()

    /**
     * 가사 리스트 가공한다.
     */
    fun getLyricsData() {
        repo.getMusicData().subscribe({

        })
    }

    fun musicData(): MutableLiveData<MusicEntity> {
        return musicData
    }


}