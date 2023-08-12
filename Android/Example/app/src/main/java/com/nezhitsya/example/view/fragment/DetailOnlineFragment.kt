package com.nezhitsya.example.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nezhitsya.example.R
import com.nezhitsya.example.base.BaseFragment
import org.json.JSONArray

class DetailOnlineFragment: BaseFragment() {

    private var item: String? = null

    companion object {
        fun newInstance(item: JSONArray) = DetailOnlineFragment().apply {
            arguments = Bundle().apply {
                putString("selectedItem", item.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getString("selectedItem")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_online, container, false)
    }

}