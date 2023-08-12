package com.nezhitsya.example.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nezhitsya.example.R
import com.squareup.picasso.Picasso
import org.json.JSONArray

class OnlineAdapter(
    val context: Context,
    private val onlineLiveData: Pair<String, JSONArray>,
    private val onItemClick: (JSONArray) -> Unit) : RecyclerView.Adapter<OnlineAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_exhibition_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(onlineLiveData.second.getJSONArray(position))
    }

    override fun getItemCount(): Int {
        return onlineLiveData.second.length()
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        private val title = itemView?.findViewById<TextView>(R.id.title)
        private val place = itemView?.findViewById<TextView>(R.id.place)
        private val coverImage = itemView?.findViewById<ImageView>(R.id.cover_image)

        fun bind(exhibition: JSONArray) {
            title!!.text = exhibition.optString(0)
            place!!.text = exhibition.optString(3)
            Picasso.get().load(exhibition.optString(1)).into(coverImage)

            itemView.setOnClickListener {
                onItemClick(exhibition)
            }
        }
    }

}