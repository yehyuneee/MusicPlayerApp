package com.example.musicplayerapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import java.lang.Exception
import java.util.concurrent.Flow

class MusicRepository(application: Application) {
    private val musicDAO: MusicDAO
    private val musicList: LiveData<List<MusicEntity>>
    // ViewModel이 가지고있는 데이터가 아니라 DB에 있는 데이터를 수정하므로 MutableLiveData 사용하지 않음
    // LiveData는 앱의 생명주기가 활성화(STARTED, RESUME) 상태인 옵저버들에게만 최신 상태의 데이터를 줘서 UI가 항상 최신 데이터만 갖도록 한다. 이 값은 변하지 않는다. 즉, LiveData를 쓰는 목적은 옵저버가 활성화 상태일 때 최신 값을 UI로 보내기 위함이다.
    // MutableLiveData는 LiveData와 달리 변할 수 있는 값을 가지며, 메인 쓰레드 또는 백그라운드 쓰레드에서 MutableLiveData의 값을 바꿀 수 있다.
    // MutableLiveData는 LiveData를 상속한 클래스다. 또한 LiveData에 없는 setValue(), postValue()를 갖고 있으며 개발자는 이를 사용할 수 있다

    init {
        var db = MusicRoomDB.getInstance(application)
        musicDAO = db!!.memoDao()
        musicList = musicDAO.getAll()
    }

    fun insert(memodata: MusicEntity) {
        try {
            val thread = Thread(Runnable {
                musicDAO.insert(memodata)
            })
            thread.start()
        } catch (e: Exception) {

        }
    }

    fun getAll(): LiveData<List<MusicEntity>> {
        return musicList
    }
}