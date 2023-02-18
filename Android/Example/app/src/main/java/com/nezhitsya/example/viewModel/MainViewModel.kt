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
        crawling.offlineExhibition()
        crawling.onlineExhibition()

        load()
    }

    private fun load() {
        offlineExhibition.postValue(Pair("title", crawling.offTitleList))
        offlineExhibition.postValue(Pair("image", crawling.offImageList))
        offlineExhibition.postValue(Pair("date", crawling.offDateList))
        offlineExhibition.postValue(Pair("place", crawling.offPlaceList))
        offlineExhibition.postValue(Pair("map", crawling.offMapList))
        offlineExhibition.postValue(Pair("price", crawling.offPriceList))

        onlineExhibition.postValue(Pair("title", crawling.onTitleList))
        onlineExhibition.postValue(Pair("image", crawling.onImageList))
        onlineExhibition.postValue(Pair("date", crawling.onDateList))
        onlineExhibition.postValue(Pair("view", crawling.onViewList))
    }

}