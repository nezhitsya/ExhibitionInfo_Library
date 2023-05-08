package com.nezhitsya.example.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nezhitsya.example.R
import com.nezhitsya.example.base.SingleLiveEvent
import com.nezhitsya.example.model.OnlineExhibition
import org.json.JSONArray

class OnlineAdapter(val context: Context, private val onlineLiveData: SingleLiveEvent<Pair<String, JSONArray>>) : RecyclerView.Adapter<OnlineAdapter.Holder>() {

    private lateinit var callback: (OnlineExhibition) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exhibition_item, parent, false)
//        return Holder(view).also {
//            it.itemView.setOnClickListener { _ ->
//                callback(onlineLiveData)
//            }
//        }
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(onlineLiveData.value!!.second.getJSONArray(position))
    }

    override fun getItemCount(): Int {
        return onlineLiveData.value!!.second.length()
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
//        var title = itemView?.findViewById<TextView>(R.id.title)
//        var coverImage = itemView?.findViewById<ImageView>(R.id.cover_image)

        fun bind(title: JSONArray) {

        }
    }

}