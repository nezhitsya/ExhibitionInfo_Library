package com.nezhitsya.example.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nezhitsya.example.R
import com.nezhitsya.example.base.BaseFragment

class MyExhibitionFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_exhibition, container, false)
        return view
    }

}