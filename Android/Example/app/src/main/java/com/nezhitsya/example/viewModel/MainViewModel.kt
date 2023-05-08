package com.nezhitsya.example.viewModel

import com.nezhitsya.example.base.BaseViewModel
import com.nezhitsya.example.base.SingleLiveEvent
import com.nezhitsya.exhibitioninfolib.Crawling
import org.json.JSONArray

class MainViewModel: BaseViewModel() {
    private val crawling = Crawling()

    val offlineExhibition = SingleLiveEvent<Pair<String, JSONArray>>()
    val onlineExhibition = SingleLiveEvent<Pair<String, JSONArray>>()

    init {
        Thread {
            crawling.offlineExhibition()
            crawling.onlineExhibition()

            load()
        }.start()
    }

    private fun load() {
        offlineExhibition.postValue(Pair("offline", crawling.totalOfflineList))
        onlineExhibition.postValue(Pair("online", crawling.totalOnlineList))
    }

}