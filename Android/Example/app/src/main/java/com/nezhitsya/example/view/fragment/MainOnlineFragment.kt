package com.nezhitsya.example.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nezhitsya.example.R
import com.nezhitsya.example.databinding.FragmentMainOnlineBinding

class MainOnlineFragment : Fragment() {

    private lateinit var mainBinding: FragmentMainOnlineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main_online, container, false)
        return mainBinding.root
    }

}