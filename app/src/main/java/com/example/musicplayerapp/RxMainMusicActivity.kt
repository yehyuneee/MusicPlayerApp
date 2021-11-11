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
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.musicplayerapp.data.LyricsEntity
import com.example.musicplayerapp.data.MusicEntity
import com.example.musicplayerapp.data.RequestMusicData
import com.example.musicplayerapp.databinding.ActivityMainBinding
import com.example.musicplayerapp.databinding.ActivityRxMainBinding
import com.example.musicplayerapp.databinding.ActivityRxMainBindingImpl
import com.example.musicplayerapp.listener.ResponseResultListener
import com.example.musicplayerapp.viewmodel.RxMusicViewModel
import java.lang.Exception
import java.util.regex.Pattern

class RxMainMusicActivity :
    BaseActivity<ActivityRxMainBindingImpl, RxMusicViewModel>() {
    override val layoutId = R.layout.activity_rx_main
    override val viewModel: RxMusicViewModel
        get() = TODO("Not yet implemented")
    override val bindingVariable: Int
        get() = TODO("Not yet implemented")

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this;

        viewModel.lyricsList.observe(this, Observer {
            // 가공된 가사 데이터를 세팅한다.
        })
    }

}


