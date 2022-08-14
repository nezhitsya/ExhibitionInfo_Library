//
//  String+Extension.swift
//  ExhibitionInfoLib
//
//  Created by 이다영 on 2022/08/15.
//

import Foundation

import Foundation

extension String {
    
    init?(htmlEncodedString: String) {
        self.init()
        var yourNewString = String(htmlEncodedString.replacingOccurrences(of: "\\n", with: ""))
        yourNewString = String(yourNewString.replacingOccurrences(of: "\\t", with: ""))
        yourNewString = String(yourNewString.replacingOccurrences(of: "\\", with: ""))
        yourNewString = yourNewString.replacingOccurrences(of: "\"", with: "", options: NSString.CompareOptions.literal, range: nil)
        yourNewString = yourNewString.replacingOccurrences(of: "\'", with: "'", options: NSString.CompareOptions.literal, range: nil)
        self = yourNewString
    }
    
}
