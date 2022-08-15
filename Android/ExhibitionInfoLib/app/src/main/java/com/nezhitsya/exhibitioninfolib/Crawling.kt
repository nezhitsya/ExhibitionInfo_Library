package com.nezhitsya.exhibitioninfolib

import android.util.Log
import org.json.JSONArray
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class Crawling {

    var offTitleList = JSONArray()
    var offImageList = JSONArray()
    var offDateList = JSONArray()
    var offPlaceList = JSONArray()
    var offMapList = JSONArray()

    var onTitleList = JSONArray()
    var onImageList = JSONArray()
    var onDateList = JSONArray()
    var onViewList = JSONArray()

    fun offlineExhibition() {
        var page = 1
        val firstScript = "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9__u1__u4_1_u8_nexearch_where_360_pkid&u1=&u4=&u8=1&where=nexearch&pkid=360"
        val firstDoc = Jsoup.connect(firstScript).ignoreContentType(true).get()
        var texts = firstDoc.toString().split(":")
        texts = texts[1].split(",")
        val totalPage = texts[0].replace(" ", "").toInt()
        Log.d("jsoup", totalPage.toString())

        while (page < totalPage * 4) {
            val exhibitionScript = "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9__u1__u4_${page}_u8_nexearch_where_360_pkid&u1=&u4=&u8=${page}&where=nexearch&pkid=360"
            val doc = StringExtension().htmlEncodedString(Jsoup.connect(exhibitionScript).ignoreContentType(true).get().toString())
            val html = Jsoup.parse(doc)

            val titleLink: Elements = html.select("div.title").select("div.area_text_box").select("strong.this_text").select("a")
            val imageLink: Elements = html.select("a.img_box").select("img")
            val dateLink: Elements = html.select("dl.info_group").select("dd.no_ellip")
            val placeLink: Elements = html.select("dl.info_group").select("dd.no_ellip").select("a")
            val mapLink: Elements = html.select("div.button_area").select("a")

            for (i in titleLink) {
                offTitleList.put(i.text())
            }

            for (i in imageLink) {
                offImageList.put(i.attr("src"))
            }

            for (i in dateLink) {
                if (i.text().startsWith("2") && i.text().endsWith(".")) {
                    offDateList.put(i.text())
                }
            }

            for (i in placeLink) {
                offPlaceList.put(i.text())
            }

            for (i in mapLink) {
                offMapList.put(i.attr("href"))
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
        Log.d("jsoup", totalPage.toString())

        while (page < totalPage * 4) {
            val exhibitionScript =
                "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9_%EC%98%A8%EB%9D%BC%EC%9D%B8_u1__u4_${page}_u8_nexearch_where_360_pkid&u1=%EC%98%A8%EB%9D%BC%EC%9D%B8&u4=&u8=${page}&where=nexearch&pkid=360"
            val doc = StringExtension().htmlEncodedString(Jsoup.connect(exhibitionScript).ignoreContentType(true).get().toString())
            val html = Jsoup.parse(doc)

            val titleLink: Elements = html.select("div.title").select("div.area_text_box").select("strong.this_text").select("a")
            val imageLink: Elements = html.select("div.data_area").select("a.img_box").select("img")
            val dateLink: Elements = html.select("dl.info_group").select("dd.no_ellip")
            val viewLink: Elements = html.select("div.button_area").select("a")

            for (i in titleLink) {
                onTitleList.put(i.text())
            }

            for (i in imageLink) {
                onImageList.put(i.attr("src"))
            }

            for (i in dateLink) {
                if (i.text().startsWith("2") && i.text().endsWith(".")) {
                    onDateList.put(i.text())
                }
            }

            for (i in viewLink) {
                onViewList.put(i.attr("href"))
            }

            page += 4
        }

    }

}