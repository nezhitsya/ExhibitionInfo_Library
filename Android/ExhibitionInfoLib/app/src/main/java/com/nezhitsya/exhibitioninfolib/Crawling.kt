package com.nezhitsya.exhibitioninfolib

import android.util.Log
import org.json.JSONArray
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class Crawling {

    private var offlineList = JSONArray()
    var totalOfflineList = JSONArray()

    private var onlineList = JSONArray()
    var totalOnlineList = JSONArray()

    fun offlineExhibition() {
        var page = 1
        val firstScript = "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9__u1__u4_1_u8_nexearch_where_360_pkid&u1=&u4=&u8=1&where=nexearch&pkid=360"
        val firstDoc = Jsoup.connect(firstScript).ignoreContentType(true).get()
        var texts = firstDoc.toString().split(":")
        texts = texts[1].split(",")
        val totalPage = texts[0].replace(" ", "").toInt()

        while (page < totalPage * 4) {
            val exhibitionScript = "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9__u1__u4_${page}_u8_nexearch_where_360_pkid&u1=&u4=&u8=${page}&where=nexearch&pkid=360"
            val doc = StringExtension().htmlEncodedString(Jsoup.connect(exhibitionScript).ignoreContentType(true).get().toString())
            val html = Jsoup.parse(doc)

            val titleLink: Elements = html.select("div.title").select("div.area_text_box").select("strong.this_text").select("a")
            val imageLink: Elements = html.select("a.img_box").select("img")
            val dateLink: Elements = html.select("dl.info_group").select("dd.no_ellip")
            val placeLink: Elements = html.select("dl.info_group").select("dd.no_ellip").select("a")
            val mapLink: Elements = html.select("div.button_area").select("a.btn_place")
            val priceLink: Elements = html.select("div.button_area")

            try {
                for (i in 0 until titleLink.size) {
                    offlineList.put(titleLink[i].text())
                    offlineList.put(imageLink[i].attr("src"))
                    if (dateLink[i].text().startsWith("2") && dateLink[i].text().endsWith(".")) {
                        offlineList.put(dateLink[i].text())
                    }
                    offlineList.put(placeLink[i].text())
                    offlineList.put(mapLink[i].attr("href"))
                    if (priceLink[i]?.select("a.btn_booking") != null) {
                        offlineList.put(priceLink[i].select("a.btn_booking").attr("href"))
                    } else {
                        offlineList.put("")
                    }

                    totalOfflineList.put(offlineList)
                    offlineList = JSONArray()
                }
            } catch (e: Exception) {
                Log.d("crawling error", e.stackTraceToString())
            }

            page += 4
        }
    }

    fun onlineExhibition() {
        var page = 1
        val firstScript = "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9_%EC%98%A8%EB%9D%BC%EC%9D%B8_u1__u4_1_u8_nexearch_where_360_pkid&u1=%EC%98%A8%EB%9D%BC%EC%9D%B8&u4=&u8=1&where=nexearch&pkid=360"
        val firstDoc = Jsoup.connect(firstScript).ignoreContentType(true).get()
        var texts = firstDoc.toString().split(":")
        texts = texts[1].split(",")
        val totalPage = texts[0].replace(" ", "").toInt()

        while (page < totalPage * 4) {
            val exhibitionScript =
                "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9_%EC%98%A8%EB%9D%BC%EC%9D%B8_u1__u4_${page}_u8_nexearch_where_360_pkid&u1=%EC%98%A8%EB%9D%BC%EC%9D%B8&u4=&u8=${page}&where=nexearch&pkid=360"
            val doc = StringExtension().htmlEncodedString(Jsoup.connect(exhibitionScript).ignoreContentType(true).get().toString())
            val html = Jsoup.parse(doc)

            val titleLink: Elements = html.select("div.title").select("div.area_text_box").select("strong.this_text").select("a")
            val imageLink: Elements = html.select("div.data_area").select("a.img_box").select("img")
            val dateLink: Elements = html.select("dl.info_group").select("dd.no_ellip")
            val viewLink: Elements = html.select("div.button_area").select("a")

            try {
                for (i in 0 until titleLink.size) {
                    onlineList.put(titleLink[i].text())
                    onlineList.put(imageLink[i].attr("src"))
                    if (dateLink[i].text().startsWith("2") && dateLink[i].text().endsWith(".")) {
                        onlineList.put(dateLink[i].text())
                    }
                    onlineList.put(viewLink[i].attr("href"))

                    totalOnlineList.put(onlineList)
                    onlineList = JSONArray()
                }
            } catch (e: Exception) {
                Log.d("crawling error", e.stackTraceToString())
            }

            page += 4
        }
    }

}