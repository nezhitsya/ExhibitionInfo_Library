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

    private var mainCategory = "all"
    private var detailCategory = "all"

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
            sortData()
            mainBinding.categoryAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            mainBinding.categoryOffline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOffline.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryOnline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOnline.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()

            mainCategory = "all"

            val adapter = OnlineAdapter(requireContext(), Pair("", sortData())) { selectedItem ->
                val fragment = DetailOfflineFragment.newInstance(selectedItem)
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            mainBinding.recyclerviewAll.adapter = adapter
        }

        mainBinding.categoryOffline.setOnClickListener {
            sortData()
            mainBinding.categoryAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryOffline.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryOffline.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            mainBinding.categoryOnline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOnline.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()

            mainCategory = "offline"

            val adapter = OnlineAdapter(requireContext(), Pair("", sortData())) { selectedItem ->
                val fragment = DetailOfflineFragment.newInstance(selectedItem)
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            mainBinding.recyclerviewAll.adapter = adapter
        }

        mainBinding.categoryOnline.setOnClickListener {
            sortData()
            mainBinding.categoryAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryOffline.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryOffline.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryOnline.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryOnline.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            mainCategory = "online"

            val adapter = OnlineAdapter(requireContext(), Pair("", sortData())) { selectedItem ->
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
            mainBinding.categoryDetailAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            mainBinding.categoryFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryFree.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryPayment.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryPayment.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()

            detailCategory = "all"

            val adapter = OnlineAdapter(requireContext(), Pair("", sortData())) { selectedItem ->
                val fragment = DetailOfflineFragment.newInstance(selectedItem)
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            mainBinding.recyclerviewAll.adapter = adapter
        }

        mainBinding.categoryFree.setOnClickListener {
            mainBinding.categoryDetailAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryDetailAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryFree.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            mainBinding.categoryPayment.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryPayment.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()

            detailCategory = "free"

            val adapter = OnlineAdapter(requireContext(), Pair("", sortData())) { selectedItem ->
                val fragment = DetailOfflineFragment.newInstance(selectedItem)
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            mainBinding.recyclerviewAll.adapter = adapter
        }

        mainBinding.categoryPayment.setOnClickListener {
            mainBinding.categoryDetailAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryDetailAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text_color))
            mainBinding.categoryFree.paintFlags = Paint.UNDERLINE_TEXT_FLAG.dec()
            mainBinding.categoryPayment.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            mainBinding.categoryPayment.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            detailCategory = "pay"

            val adapter = OnlineAdapter(requireContext(), Pair("", sortData())) { selectedItem ->
                val fragment = DetailOfflineFragment.newInstance(selectedItem)
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            mainBinding.recyclerviewAll.adapter = adapter
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

    private fun sortData(): JSONArray {
        when (mainCategory) {
            "all" -> {
                val dataList = JSONArray()
                for (i in 0 until onlineData!!.second.length()) {
                    dataList.put(onlineData!!.second.getJSONArray(i))
                }
                for (i in 0 until offlineData!!.second.length()) {
                    dataList.put(offlineData!!.second.getJSONArray(i))
                }

                when (detailCategory) {
                    "all" -> {
                        val comparator = Comparator<JSONArray> { array1, array2 ->
                            val value1 = array1.getString(0)
                            val value2 = array2.getString(0)
                            value1.compareTo(value2)
                        }
                        val innerArrays = (0 until dataList.length())
                            .map { dataList.getJSONArray(it) }
                            .sortedWith(comparator)
                        return JSONArray(innerArrays)
                    }
                    "pay" -> {
                        val filteredArray = JSONArray()
                        for (i in 0 until dataList.length()) {
                            val innerArray = dataList.getJSONArray(i)
                            if (innerArray.length() > 5 && innerArray.getString(5) != "") {
                                filteredArray.put(innerArray)
                            }
                        }
                        return filteredArray
                    }
                    "free" -> {
                        val filteredArray = JSONArray()
                        for (i in 0 until dataList.length()) {
                            val innerArray = dataList.getJSONArray(i)
                            if (innerArray.length() > 5 && innerArray.getString(5) == "") {
                                filteredArray.put(innerArray)
                            }
                        }
                        return filteredArray
                    }
                }
            }
            "offline" -> {
                val offlineList = offlineData!!.second
                when (detailCategory) {
                    "all" -> {
                        return offlineList
                    }
                    "pay" -> {
                        val filteredArray = JSONArray()
                        for (i in 0 until offlineList.length()) {
                            val innerArray = offlineList.getJSONArray(i)
                            if (innerArray.length() > 5 && innerArray.getString(5) != "") {
                                filteredArray.put(innerArray)
                            }
                        }
                        return filteredArray
                    }
                    "free" -> {
                        val filteredArray = JSONArray()
                        for (i in 0 until offlineList.length()) {
                            val innerArray = offlineList.getJSONArray(i)
                            if (innerArray.length() > 5 && innerArray.getString(5) == "") {
                                filteredArray.put(innerArray)
                            }
                        }
                        return filteredArray
                    }
                }
            }
            "online" -> {
                return onlineData!!.second
            }
        }
        return JSONArray()
    }

}