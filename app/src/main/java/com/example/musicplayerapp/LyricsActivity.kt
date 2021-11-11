package com.example.musicplayerapp

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.musicplayerapp.databinding.ActivityLyricsBinding

class LyricsActivity : BaseActivity<ActivityLyricsBinding, ViewModel>() {
    override val layoutId = R.layout.activity_lyrics
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")
    override val bindingVariable: Int
        get() = TODO("Not yet implemented")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}