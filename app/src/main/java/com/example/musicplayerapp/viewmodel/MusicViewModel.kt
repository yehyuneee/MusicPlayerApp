package com.example.musicplayerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicViewModel : ViewModel() {
    lateinit var lyricsData: MutableLiveData<String>

    init {

    }

    // https://kangmin1012.tistory.com/39 참고
}