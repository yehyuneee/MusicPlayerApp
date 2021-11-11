package com.example.musicplayerapp

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
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

            lyricsList.observe(this@RxMainMusicActivity, Observer {
                // 가공된 가사 데이터를 세팅한다.
            })
        }

    }

}


