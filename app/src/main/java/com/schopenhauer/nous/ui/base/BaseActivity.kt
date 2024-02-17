package com.schopenhauer.nous.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
  lateinit var binding: VB
  abstract val bindingInflater: (LayoutInflater) -> VB

  override fun onCreate(savedInstanceState: Bundle?) {
    onBeforeCreate()
    super.onCreate(savedInstanceState)
    binding = bindingInflater.invoke(layoutInflater)
    setContentView(binding.root)
    initViews()
  }

  open fun onBeforeCreate() {}
  open fun initViews() {}
}
