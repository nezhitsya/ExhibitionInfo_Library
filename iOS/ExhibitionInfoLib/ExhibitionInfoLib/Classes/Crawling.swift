//
//  Crawling.swift
//  ExhibitionInfoLib
//
//  Created by nezhitsya on 08/15/2022.
//  Copyright (c) 2022 nezhitsya. All rights reserved.
//

import SwiftSoup

public class Crawling: NSObject {
    
    public static var offTitleList = [String]()
    public static var offImageList = [String]()
    public static var offDateList = [String]()
    public static var offPlaceList = [String]()
    public static var offMapList = [String]()
    
    public static var onTitleList = [String]()
    public static var onImageList = [String]()
    public static var onDateList = [String]()
    public static var onViewList = [String]()
    
    // MARK:- Methods
    public static func offlineExhibition() {
        
        DispatchQueue.main.async {
            UIApplication.shared.isNetworkActivityIndicatorVisible = true
        }
        
        var totalPage = 0
        let firstScript = "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9__u1__u4_1_u8_nexearch_where_360_pkid&u1=&u4=&u8=1&where=nexearch&pkid=360"
        guard let firstUrl = URL(string: firstScript) else { return }
        do {
            let html = try String(contentsOf: firstUrl, encoding: .utf8)
            var texts = html.split(separator: ":")
            texts = texts[1].split(separator: ",")
            
            totalPage = Int(texts[0].replacingOccurrences(of: " ", with: "")) ?? 1
            print(totalPage)
        } catch let error {
            print("Parsing Error: ", error)
        }
        
        var page = 1
        while (page < totalPage * 4) {
            let exhibitionScript = "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9__u1__u4_\(page)_u8_nexearch_where_360_pkid&u1=&u4=&u8=\(page)&where=nexearch&pkid=360"
            
            guard let url = URL(string: exhibitionScript) else { return }
            do {
                let html = try String(contentsOf: url, encoding: .utf8)
                let doc: Document = try SwiftSoup.parse(String(htmlEncodedString: html)!)
                
                let titleLink: Elements = try doc.select("div.title").select("div.area_text_box").select("strong.this_text").select("a")
                let imageLink: Elements = try doc.select("a.img_box").select("img")
                let dateLink: Elements = try doc.select("dl.info_group").select("dd.no_ellip")
                let placeLink: Elements = try doc.select("dl.info_group").select("dd.no_ellip").select("a")
                let mapLink: Elements = try doc.select("div.button_area").select("a")
                
                for i in titleLink.array() {
                    try offTitleList.append(i.text())
                }
                
                for i in imageLink.array() {
                    try offImageList.append(i.attr("src"))
                }
                
                for i in dateLink.array() {
                    if try i.text().hasPrefix("2") && i.text().hasSuffix(".") {
                        try offDateList.append(i.text())
                    }
                }
                
                for i in placeLink.array() {
                    try offPlaceList.append(i.text())
                }
                
                for i in mapLink.array() {
                    try offMapList.append(i.attr("href"))
                }
                
                page += 4
            } catch let error {
                print("Parsing Error: ", error)
            }
        }
    }
    
    public static func onlineExhibition() {
        DispatchQueue.main.async {
            UIApplication.shared.isNetworkActivityIndicatorVisible = true
        }
        
        var totalPage = 0
        let firstScript = "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9_%EC%98%A8%EB%9D%BC%EC%9D%B8_u1__u4_1_u8_nexearch_where_360_pkid&u1=%EC%98%A8%EB%9D%BC%EC%9D%B8&u4=&u8=1&where=nexearch&pkid=360"
        
        guard let firstUrl = URL(string: firstScript) else { return }
        do {
            let html = try String(contentsOf: firstUrl, encoding: .utf8)
            var texts = html.split(separator: ":")
            texts = texts[1].split(separator: ",")
            
            totalPage = Int(texts[0].replacingOccurrences(of: " ", with: "")) ?? 1
            print(totalPage)
        } catch let error {
            print("Parsing Error: ", error)
        }
        
        var page = 1
        while (page < totalPage * 4) {
            let exhibitionScript = "https://m.search.naver.com/p/csearch/content/nqapirender.nhn?fileKey=exhibitionKBListAPI&u9=4&_callback=_exhibitionKBListAPI_fileKey_4_u9_%EC%98%A8%EB%9D%BC%EC%9D%B8_u1__u4_\(page)_u8_nexearch_where_360_pkid&u1=%EC%98%A8%EB%9D%BC%EC%9D%B8&u4=&u8=\(page)&where=nexearch&pkid=360"
            
            guard let url = URL(string: exhibitionScript) else { return }
            do {
                let html = try String(contentsOf: url, encoding: .utf8)
                let doc: Document = try SwiftSoup.parse(String(htmlEncodedString: html)!)
                
                let titleLink: Elements = try doc.select("div.title").select("div.area_text_box").select("strong.this_text").select("a")
                let imageLink: Elements = try doc.select("div.data_area").select("a.img_box").select("img")
                let dateLink: Elements = try doc.select("dl.info_group").select("dd.no_ellip")
                let viewLink: Elements = try doc.select("div.button_area").select("a")
                
                for i in titleLink.array() {
                    try onTitleList.append(i.text())
                }
                
                for i in imageLink.array() {
                    try onImageList.append(i.attr("src"))
                }
                
                for i in dateLink.array() {
                    if try i.text().hasPrefix("2") && i.text().hasSuffix(".") {
                        try onDateList.append(i.text())
                    }
                }
                
                for i in viewLink.array() {
                    try onViewList.append(i.attr("href"))
                }
                
                page += 4
            } catch let error {
                print("Parsing Error: ", error)
            }
        }
    }
    
}
