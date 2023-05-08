package com.nezhitsya.example.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nezhitsya.example.R
import com.nezhitsya.example.base.BaseFragment
import com.nezhitsya.example.databinding.FragmentMainOnlineBinding
import com.nezhitsya.example.view.adapter.OnlineAdapter
import com.nezhitsya.example.viewModel.MainViewModel

class MainOnlineFragment : BaseFragment() {

    private lateinit var mainBinding: FragmentMainOnlineBinding
    private lateinit var adapter: OnlineAdapter

    private val mainViewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main_online, container, false)

        mainBinding.recyclerviewOnline.setHasFixedSize(true)
        mainBinding.recyclerviewOnline.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        mainBinding.recyclerviewOnline.layoutManager = linearLayoutManager

        return mainBinding.root
    }

    override fun setObserver() {
        mainViewModel.onlineExhibition.observe(this, {
            Log.d("leedy", it.toString())

        })
    }

}