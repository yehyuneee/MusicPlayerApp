package com.example.musicplayerapp

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayerapp.databinding.ActivityRxMainBinding
import com.example.musicplayerapp.viewmodel.RxMusicViewModel

class RxMainMusicActivity :
    BaseActivity<ActivityRxMainBinding, RxMusicViewModel>() {
    override val layoutId = R.layout.activity_rx_main
    override val viewModel: RxMusicViewModel by viewModels()
    override val bindingVariable: Int = BR.rxMainViewModel

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // intro 실행
        startSplashActivity<SplashActivity>()

        mContext = this;

        with(viewModel) {
            getLyricsData()

            musicPlayer.observe(this@RxMainMusicActivity, Observer {
                it.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
                    override fun onCompletion(p0: MediaPlayer?) {
                        it.apply {
                            stop()
                            prepare()
                            seekTo(0)
                        }
                        binding.musicPlayPauseBtn.isSelected = false
                    }
                })
            })

            musicPlayerData.observe(this@RxMainMusicActivity, Observer {
                binding.musicSeekbar.max = it.duration.toInt()
                binding.musicTotalTime.text = milliSecondsToTimers(it.duration)
                binding.musicTotalTime.isVisible = true
            })

            lyricsList.observe(this@RxMainMusicActivity, Observer {
                // 가공된 가사 데이터를 세팅한다.
            })
        }

    }

}

/**
 * 시간 계산
 */
fun milliSecondsToTimers(milliseconds: Long): String? {
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

