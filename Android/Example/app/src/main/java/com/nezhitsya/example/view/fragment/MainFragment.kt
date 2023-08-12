package com.nezhitsya.example.view.fragment

import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nezhitsya.example.R
import com.nezhitsya.example.base.BaseFragment
import com.nezhitsya.example.databinding.FragmentMainBinding
import com.nezhitsya.example.view.MainActivity
import com.nezhitsya.example.view.adapter.OnlineAdapter
import com.nezhitsya.example.viewModel.MainViewModel
import com.nezhitsya.example.viewModel.PermissionViewModel
import org.json.JSONArray

class MainFragment: BaseFragment() {

    private lateinit var mainBinding: FragmentMainBinding

    private val mainViewModel : MainViewModel by activityViewModels()
    private val permissionViewModel : PermissionViewModel by activityViewModels()
    private var offlineData: Pair<String, JSONArray>? = null
    private var onlineData: Pair<String, JSONArray>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        mainBinding.categoryAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        mainBinding.categoryAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        mainBinding.categoryDetailAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        mainBinding.categoryDetailAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        mainBinding.recyclerviewAll.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        mainBinding.recyclerviewAll.layoutManager = gridLayoutManager

        return mainBinding.root
    }

    override fun setObserver() {
        mainViewModel.offlineExhibition.observe(this) {
            offlineData = it
        }

        mainViewModel.onlineExhibition.observe(this) {
            onlineData = it
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
            mainBinding.categoryAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            mainBinding.categoryOffline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOffline.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryOnline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOnline.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
        }

        mainBinding.categoryOffline.setOnClickListener {
            mainBinding.categoryAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryOffline.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryOffline.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            mainBinding.categoryOnline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOnline.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()

            val adapter = OnlineAdapter(requireContext(), offlineData!!) { selectedItem ->
                val fragment = DetailOfflineFragment.newInstance(selectedItem)
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            mainBinding.recyclerviewAll.adapter = adapter
        }

        mainBinding.categoryOnline.setOnClickListener {
            mainBinding.categoryAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryOffline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOffline.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryOnline.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryOnline.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            val adapter = OnlineAdapter(requireContext(), onlineData!!) { selectedItem ->
                val fragment = DetailOnlineFragment.newInstance(selectedItem)
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            mainBinding.recyclerviewAll.adapter = adapter
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