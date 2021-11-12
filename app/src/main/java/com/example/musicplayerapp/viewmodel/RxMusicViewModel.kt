package com.example.musicplayerapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.*
import com.example.musicplayerapp.MusicApplication
import com.example.musicplayerapp.data.model.LyricsEntity
import com.example.musicplayerapp.data.MusicEntity
import com.example.musicplayerapp.data.RxLyricsRepository
import com.example.musicplayerapp.data.model.MusicPlayerEntity
import java.util.regex.Pattern

/**
 * Rxjava 구조로 리팩토링하는 중
 */
class RxMusicViewModel(application: Application) : AndroidViewModel(application) {
    private val musicData: MutableLiveData<MusicEntity> = MutableLiveData()
    private val repo: RxLyricsRepository = RxLyricsRepository()

    private val context = application

    val musicPlayer: MutableLiveData<MediaPlayer> = MutableLiveData()
    val musicPlayerData: MutableLiveData<MusicPlayerEntity> = MutableLiveData()
    var lyricsList: MutableLiveData<LyricsEntity> = MutableLiveData<LyricsEntity>()

    /**
     * 가사 리스트 가공한다.
     */
    @SuppressLint("CheckResult")
    fun getLyricsData() {
        // Repository를 통해 Api 호출하고 RxJava통해 데이터 가공한다
        repo.getMusicData().subscribe {
            musicData.value = it
            val lyrics = it.lyrics
            val file = it.file

            lyrics.let {
                var duration: String? = null
                var lyrics: String? = null

                val lycisList = it.split("\n")
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

            file.let {
                val resultFile = Uri.parse(it)
                musicPlayer.value = MediaPlayer.create(context, resultFile)
                musicPlayerData.value =
                    MusicPlayerEntity(musicPlayer.value!!.currentPosition.toLong(),
                        musicPlayer.value!!.duration.toLong(),
                        false)
            }

        }
    }

    fun musicData(): MutableLiveData<MusicEntity> {
        return musicData
    }
}