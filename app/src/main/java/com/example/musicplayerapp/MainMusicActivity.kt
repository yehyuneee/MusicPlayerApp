package com.example.musicplayerapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock.sleep
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.musicplayerapp.data.MusicEntity
import com.example.musicplayerapp.data.RequestMusicData
import com.example.musicplayerapp.databinding.ActivityMainBinding
import com.example.musicplayerapp.databinding.ActivitySplashBinding
import com.example.musicplayerapp.listener.ResponseResultListener
import java.lang.Exception
import java.lang.Thread.sleep

class MainMusicActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }),
    View.OnClickListener {

    lateinit var mContext: Context
    lateinit var mMusicData: MusicEntity
    lateinit var mMusicPlayer: MediaPlayer

    lateinit var mMusicFile: Uri
    lateinit var mMusicThread: Thread

    lateinit var mPlayDrawable: Drawable
    lateinit var mPauseDrawable: Drawable

    var mProgressDuration: Int = 0

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

        mViewBiding.musicSeekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean,
            ) {
                mProgressDuration = progress
                if (fromUser) {
                    mMusicPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mMusicPlayer.seekTo(mProgressDuration)
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
        mMusicData.file.let {
            mMusicFile = Uri.parse(it)
            mMusicPlayer = MediaPlayer.create(mContext, mMusicFile)
            mViewBiding.musicSeekbar.max = mMusicPlayer.duration

            mPlayDrawable = ContextCompat.getDrawable(mContext, R.drawable.play_icon)!!
            mPauseDrawable = ContextCompat.getDrawable(mContext, R.drawable.pause_icon)!!
        }

        mViewBiding.musicPlayPauseBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            mViewBiding.musicPlayPauseBtn.id -> {
                if (mViewBiding.musicPlayPauseBtn.isSelected) {
                    mMusicPlayer.let {
                        mMusicPlayer.stop()
                        mMusicPlayer.prepare()
                        Log.d("테스트", "3 : " + mProgressDuration)
                        mMusicPlayer.seekTo(mProgressDuration)
                    }
                    mViewBiding.musicPlayPauseBtn.isSelected = false
                } else {
                    mMusicPlayer.start()
                    MusicThread().start()

                    mViewBiding.musicPlayPauseBtn.isSelected = true
                }
            }
        }
    }

    inner class MusicThread() : Thread() {
        override fun run() {
            while (mMusicPlayer.isPlaying()) {  // 음악이 실행중일때 계속 돌아가게 함
                Log.d("테스트", "1")
                try {
                    Log.d("테스트", "2")
                    Thread.sleep(1000) // 1초마다 시크바 움직이게 함
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                // 현재 재생중인 위치를 가져와 시크바에 적용
                mViewBiding.musicSeekbar.setProgress(mMusicPlayer.getCurrentPosition())
            }
        }
    }

    //// seekbar 안움직임 , duration 수정필요
}


inline fun <reified T : Activity> Context.startSplashActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}


