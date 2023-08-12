package com.nezhitsya.example.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
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
//        val linearLayoutManager = LinearLayoutManager(context)
//        linearLayoutManager.reverseLayout = true
//        linearLayoutManager.stackFromEnd = true
//        mainBinding.recyclerviewOnline.layoutManager = linearLayoutManager

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        mainBinding.recyclerviewOnline.layoutManager = gridLayoutManager

        return mainBinding.root
    }

    override fun setObserver() {
        mainViewModel.onlineExhibition.observe(this, {
            adapter = OnlineAdapter(requireContext(), it)
            mainBinding.recyclerviewOnline.adapter = adapter
        })
    }

}