package com.example.musicplayerapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO - Database Access Object, 데이터베이스에 접근해서 실질적으로 insert, delete 등을 수행하는 메소드를 포함한다.
 *
 * @Dao : Room을 위한 DAO클래스를 나타낸다.
 * @Query : 이 메서드가 수행할 SQL 문을 정의한다.
 * @Insert : Insert 어노테이션은 별도의 SQL문을 작성할 필요가 없다. 왜냐면 Word 클래스에 맞게 데이터가 들어간다.
 * onConflict = OnConflictStrategy.IGNORE : 중복되는 값은 무시한다.
 * suspend : 코루틴을 사용할 수 있도록 suspend를 붙여 백그라운드에서 실행되도록 한다.
 * Flow : 비동기로 동작하면서 여러개의 값을 반환하는 Function을 만들 때 사용하는 Builder
 */
@Dao
interface MusicDAO {
    @Query("SELECT * FROM musiclist")
    fun getAll(): LiveData<List<MusicEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(music: MusicEntity)
}