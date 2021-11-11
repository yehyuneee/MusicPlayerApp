package com.example.musicplayerapp.data

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.Flow

/**
 * Rxjava 구조로 리팩토링하는 중
 * Model에서 데이터를 요청가서 가져온다 (RestApi형태)
 * 가져온 데이터 viewModel로 전달
 */
class RxLyricsRepository(application: Application) {
    private val api = RxMusicAPI.create()
    private var musicData: MutableLiveData<MusicEntity> = MutableLiveData()

    fun getMusicData(): io.reactivex.rxjava3.core.Observable<MusicEntity> = api
        .getMusicInfo()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}