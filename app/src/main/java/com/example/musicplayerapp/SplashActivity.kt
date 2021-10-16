package com.example.musicplayerapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.musicplayerapp.databinding.ActivitySplashBinding
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity<ActivitySplashBinding>({ ActivitySplashBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(this).load(R.drawable.flo_splash).into(mViewBiding.splashImage)

        Timer().schedule(2000) {
            startMainActivity<MainMusicActivity>()
            finish()
        }
    }
}

inline fun <reified T : Activity> Context.startMainActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}
