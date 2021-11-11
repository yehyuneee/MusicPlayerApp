package com.example.musicplayerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.musicplayerapp.databinding.ActivityLyricsBinding
import com.example.musicplayerapp.databinding.ActivityLyricsBindingImpl
import com.example.musicplayerapp.databinding.ActivityMainBinding
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.compat.ViewModelCompat.viewModel

class LyricsActivity : BaseActivity<ActivityLyricsBindingImpl, ViewModel>() {
    override val layoutId = R.layout.activity_lyrics
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")
    override val bindingVariable: Int
        get() = TODO("Not yet implemented")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}