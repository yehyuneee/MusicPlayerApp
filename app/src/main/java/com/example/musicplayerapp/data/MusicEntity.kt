package com.example.musicplayerapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity - Database 안에 있는 테이블을 Java나 Kotlin 클래스로 나타낸 것이다. 데이터 모델 클래스라고 볼 수 있다.
 *
 * @Entity : SQLite table을 나타낸다. Entity라고 선언함에 동시에 테이블 이름을 지정한다.
 * @PrimaryKey : 모든 Entitiy에는 primary key 기본키가 존재한다. 기본키를 선언한다.
 * @ColumnInfo(name = "id")  : 만약 멤버변수와 이름을 다르게 하고 싶으면 이 어노테이션으로 지정한다.
 */
@Entity(tableName = "musiclist")
data class MusicEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "singer") var singer: String,
    @ColumnInfo(name = "album") var album: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "file") var file: String,
    @ColumnInfo(name = "lyrics") var lyrics: String
)