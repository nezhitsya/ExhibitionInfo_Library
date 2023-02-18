package com.nezhitsya.example.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.nezhitsya.example.R
import com.nezhitsya.example.base.BaseFragment
import com.nezhitsya.example.databinding.FragmentMainBinding
import com.nezhitsya.example.viewModel.MainViewModel
import com.nezhitsya.example.viewModel.PermissionViewModel

class MainFragment: BaseFragment() {

    private lateinit var mainBinding: FragmentMainBinding

    private val mainViewModel : MainViewModel by activityViewModels()
    private val permissionViewModel : PermissionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        return mainBinding.root
    }

    override fun setObserver() {
        mainViewModel.offlineExhibition.observe(this, {

        })

        mainViewModel.onlineExhibition.observe(this, {

        })
    }

    override fun postCreate() {
        checkPermission()
    }

    private fun checkPermission() {
        permissionViewModel.checkPermissionList.postValue(
            arrayListOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.INTERNET,
            )
        )
    }

}