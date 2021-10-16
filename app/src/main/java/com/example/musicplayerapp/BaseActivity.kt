package com.example.musicplayerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding>(val bindingFactory: (LayoutInflater) -> T) :
    AppCompatActivity() {

    private lateinit var mInitViewBiding: T
    val mViewBiding: T get() = requireNotNull(mInitViewBiding)
    // requireNotNull을 통해 nullable이 제거된 바인딩을 사용하게 만듬

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mInitViewBiding = bindingFactory(layoutInflater)
        setContentView(mViewBiding.root)
    }
}