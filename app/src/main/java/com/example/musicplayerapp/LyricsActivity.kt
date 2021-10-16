package com.example.musicplayerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayerapp.databinding.ActivityLyricsBinding
import com.example.musicplayerapp.databinding.ActivityMainBinding

class LyricsActivity : BaseActivity<ActivityLyricsBinding>({ ActivityLyricsBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}