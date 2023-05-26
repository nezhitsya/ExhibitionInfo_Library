package com.nezhitsya.example.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.nezhitsya.example.R
import com.nezhitsya.example.base.BaseFragment
import com.nezhitsya.example.databinding.FragmentMainWebBinding
import com.nezhitsya.example.viewModel.WebViewModel

class MainWebFragment: BaseFragment() {

    private lateinit var webBinding: FragmentMainWebBinding

    private val webViewViewModel: WebViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        webBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main_web, container, false)
        return webBinding.root
    }

    override fun setObserver() {
        super.setObserver()
    }

}