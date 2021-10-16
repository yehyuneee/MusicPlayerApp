package com.example.musicplayerapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database - database holder를 포함하며, 앱에 영구 저장되는 데이터와 기본 연결을 위한 주 액세스 지점이다.
 * RoomDatabase를 extend 하는 추상 클래스여야 하며, 테이블과 버전을 정의하는 곳이다.
 */
@Database(entities = [MusicEntity::class], version = 1)
abstract class MusicRoomDB: RoomDatabase() {
    abstract fun memoDao(): MusicDAO

    // singleton 패턴으로 만들면 접근도 쉽고 메모리에서도 효율적이다.
    companion object {
        private var Instance: MusicRoomDB? = null
        fun getInstance(context: Context): MusicRoomDB? {
            if (Instance == null) {
                // 여러 스레드가 접근하지 못하도록 한다
                synchronized(MusicRoomDB::class) {
                    Instance = Room.databaseBuilder(context.applicationContext,
                        MusicRoomDB::class.java,
                        "musiclist.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }

            }
            return Instance
        }
    }

    fun destoryInstance() {
        Instance = null
    }
}