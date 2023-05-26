package com.nezhitsya.example.view.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        mainBinding.categoryAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        mainBinding.categoryDetailAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        return mainBinding.root
    }

    override fun setObserver() {
        mainViewModel.offlineExhibition.observe(this) {

        }

        mainViewModel.onlineExhibition.observe(this) {

        }

        object : CountDownTimer(1000 * 3, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                mainBinding.incIntro.root.visibility = View.GONE
                mainBinding.main.visibility = View.VISIBLE
            }
        }.start()

    }

    override fun postCreate() {
        checkPermission()

        mainBinding.categoryAll.setOnClickListener {
            mainBinding.categoryAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryOffline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOnline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
        }

        mainBinding.categoryOffline.setOnClickListener {
            mainBinding.categoryAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOffline.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryOnline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
        }

        mainBinding.categoryOnline.setOnClickListener {
            mainBinding.categoryAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOffline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOnline.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        }

        mainBinding.categoryDetailAll.setOnClickListener {
            mainBinding.categoryDetailAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryPayment.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
        }

        mainBinding.categoryFree.setOnClickListener {
            mainBinding.categoryDetailAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryPayment.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
        }

        mainBinding.categoryPayment.setOnClickListener {
            mainBinding.categoryDetailAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryPayment.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        }
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