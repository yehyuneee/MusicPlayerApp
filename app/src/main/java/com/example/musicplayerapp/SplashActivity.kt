package com.example.musicplayerapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.musicplayerapp.databinding.ActivitySplashBinding
import com.example.musicplayerapp.databinding.ActivitySplashBindingImpl
import com.example.musicplayerapp.viewmodel.RxMusicViewModel
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity<ActivitySplashBindingImpl, ViewModel>() {
    override val layoutId = R.layout.activity_splash
    override val viewModel: RxMusicViewModel
        get() = TODO("Not yet implemented")
    override val bindingVariable: Int
        get() = TODO("Not yet implemented")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(this).load(R.drawable.flo_splash).into(splashImage)

        Timer().schedule(2000) {
            finish()
        }
    }
}
