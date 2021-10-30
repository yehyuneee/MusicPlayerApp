package com.example.musicplayerapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.musicplayerapp.data.MusicEntity
import com.example.musicplayerapp.data.RequestMusicData
import com.example.musicplayerapp.databinding.ActivityMainBinding
import com.example.musicplayerapp.databinding.ActivitySplashBinding
import com.example.musicplayerapp.listener.ResponseResultListener

class MainMusicActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {

    lateinit var mContext: Context
    lateinit var mMusicData: MusicEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // intro 실행
        startSplashActivity<SplashActivity>()

        mContext = this;

        RequestMusicData(object : ResponseResultListener<MusicEntity> {
            override fun onSuccess(result: MusicEntity) {
                result.let {
                    mMusicData = it
                    setData()
                }
            }

            override fun onFail() {
                Toast.makeText(mContext, "음악 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setData() {
        mMusicData.title.let {
            mViewBiding.musicTitle.text = it
        }
        mMusicData.singer.let {
            mViewBiding.musicSinger.text = it
        }
        mMusicData.album.let {
            mViewBiding.musicAlbum.text = it
        }
        mMusicData.image.let {
            Glide.with(this).load(it).into(mViewBiding.musicAlbumImage)
        }
    }
}


inline fun <reified T : Activity> Context.startSplashActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}