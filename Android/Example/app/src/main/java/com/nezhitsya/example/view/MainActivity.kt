package com.nezhitsya.example.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.nezhitsya.example.R
import com.nezhitsya.example.base.BaseActivity
import com.nezhitsya.example.view.fragment.MainFragment
import com.nezhitsya.example.viewModel.MainViewModel

class MainActivity: BaseActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override val layoutResource = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment()).commit()
    }

    override fun postCreate() {
        super.postCreate()

        setViewModel(listOf(mainViewModel))
    }

}