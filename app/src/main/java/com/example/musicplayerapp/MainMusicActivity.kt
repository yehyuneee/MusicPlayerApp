package com.example.musicplayerapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.musicplayerapp.data.model.LyricsEntity
import com.example.musicplayerapp.data.MusicEntity
import com.example.musicplayerapp.data.RequestMusicData
import com.example.musicplayerapp.databinding.ActivityMainBinding
import com.example.musicplayerapp.listener.ResponseResultListener
import com.example.musicplayerapp.viewmodel.MusicViewModel
import java.lang.Exception
import java.util.regex.Pattern

class MainMusicActivity : BaseActivity<ActivityMainBinding, MusicViewModel>(),
    View.OnClickListener {
    override val layoutId = R.layout.activity_main
    override val viewModel: MusicViewModel
        get() = TODO("Not yet implemented")
    override val bindingVariable: Int
        get() = TODO("Not yet implemented")

    lateinit var mContext: Context
    lateinit var mMusicData: MusicEntity
    lateinit var mMusicPlayer: MediaPlayer

    lateinit var mMusicFile: Uri

    var mTotalTime: Long = 0;
    var mCurrentTime: Long = 0;

    var mProgressDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // intro 실행
        startSplashActivity<SplashActivity>()

        mContext = this;

        // 음악 정보 API 통신
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

        binding.musicSeekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean,
            ) {
                mProgressDuration = progress
                Log.d("onProgressChanged : ", mMusicPlayer.currentPosition.toString())
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

        binding.musicPlayPauseBtn.setOnClickListener(this)
    }

    /**
     * 데이터 세팅
     */
    fun setData() {
        mMusicData.title.let {
            binding.musicTitle.text = it
        }
        mMusicData.singer.let {
            binding.musicSinger.text = it
        }
        mMusicData.album.let {
            binding.musicAlbum.text = it
        }
        mMusicData.image.let {
            Glide.with(this).load(it).into(binding.musicAlbumImage)
        }
        mMusicData.file.let {
            mMusicFile = Uri.parse(it)
            mMusicPlayer = MediaPlayer.create(mContext, mMusicFile)
            binding.musicSeekbar.max = mMusicPlayer.duration
            mTotalTime = mMusicPlayer.duration.toLong()
            binding.musicTotalTime.text = milliSecondsToTimer(mTotalTime)
            binding.musicTotalTime.isVisible = true

            mMusicPlayer.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
                override fun onCompletion(p0: MediaPlayer?) {
                    mMusicPlayer.apply {
                        stop()
                        prepare()
                        seekTo(0)
                    }
                    binding.musicPlayPauseBtn.isSelected = false
                }
            })
        }

        mMusicData.lyrics.let {
            var duration: String? = null
            var lyrics: String? = null

            val lycisList = it.split("\n")
            for (i in lycisList.indices) {
                val pattern = Pattern.compile("\\[(.*?)\\]")
                val matcher = pattern.matcher(lycisList.get(i))
                while (matcher.find()) {
                    duration = matcher.group(1)
                }
                var lyricsEntity: LyricsEntity = LyricsEntity(duration, lyrics)
                // TODO test primary key인 id값 확인해야함!!!!

            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.musicPlayPauseBtn.id -> {
                if (binding.musicPlayPauseBtn.isSelected) {
                    mMusicPlayer.apply {
                        stop()
                        prepare()
                        seekTo(mProgressDuration)
                    }
                    binding.musicPlayPauseBtn.isSelected = false
                } else {
                    mMusicPlayer.let {
                        mMusicPlayer.start()
                        MusicThread().start()

                        binding.musicPlayPauseBtn.isSelected = true
                    }
                }
            }
        }
    }

    // TODO Thread 구현부터 필요
    /**
     * 음악 재생에 따른 SeekBar 설정
     */
    inner class MusicThread() : Thread() {
        override fun run() {
            while (mMusicPlayer.isPlaying()) {  // 음악이 실행중일때 계속 돌아가게 함
                try {
                    sleep(1000) // 1초마다 SeekBar 움직이게 함
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                // 현재 재생중인 위치를 가져와 SeekBar에 적용
                runOnUiThread {
                    mCurrentTime = mMusicPlayer.currentPosition.toLong()
                    binding.musicLeftTime.text = milliSecondsToTimer(mCurrentTime)

                    binding.musicSeekbar.setProgress(mCurrentTime.toInt())
                }
            }
        }
    }
}

/**
 * 시간 계산
 */
fun milliSecondsToTimer(milliseconds: Long): String? {
    var finalTimerString = ""
    var secondsString = ""

    // Convert total duration into time
    val hours = (milliseconds / (1000 * 60 * 60)).toInt()
    val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
    val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()

    // Add hours if there
    if (hours > 0) {
        finalTimerString = "$hours:"
    }

    // Prepending 0 to seconds if it is one digit
    secondsString = if (seconds < 10) {
        "0$seconds"
    } else {
        "" + seconds
    }
    finalTimerString = "$finalTimerString$minutes:$secondsString"

    // return timer string
    return finalTimerString
}

/**
 * 인트로 화면 이동
 */
inline fun <reified T : Activity> Context.startSplashActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}


