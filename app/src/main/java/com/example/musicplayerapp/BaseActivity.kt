package com.example.musicplayerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<Binding : ViewDataBinding, VM : ViewModel> :
    AppCompatActivity(), LifecycleObserver {

    private var _binding: Binding? = null
    val binding get() = _binding!!

    abstract val layoutId: Int
    abstract val viewModel: VM
    abstract val bindingVariable: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<Binding>(this, layoutId).apply {
            lifecycleOwner = this@BaseActivity
            setVariable(bindingVariable, viewModel)
            _binding = this
        }
    }
}