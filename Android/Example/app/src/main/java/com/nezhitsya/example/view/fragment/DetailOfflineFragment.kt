package com.nezhitsya.example.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nezhitsya.example.R
import org.json.JSONArray

class DetailOfflineFragment : Fragment() {

    private var item: String? = null

    companion object {
        fun newInstance(item: JSONArray) = DetailOfflineFragment().apply {
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
        return inflater.inflate(R.layout.fragment_detail_offline, container, false)
    }

}