package com.example.musicplayerapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.musicplayerapp.databinding.ActivitySplashBinding
import com.example.musicplayerapp.viewmodel.MusicViewModel
import com.example.musicplayerapp.viewmodel.RxMusicViewModel
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity<ActivitySplashBinding, ViewModel>() {
    override val layoutId = R.layout.activity_splash
    override val viewModel: MusicViewModel by viewModels()
    override val bindingVariable: Int = BR.splashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(this).load(R.drawable.flo_splash).into(binding.splashImage)

        Timer().schedule(2000) {
            finish()
        }
    }
}
