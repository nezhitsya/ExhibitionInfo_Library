package com.nezhitsya.exhibitioninfolib

class StringExtension {

    fun htmlEncodedString(s: String): String {
        var yourNewString = s.replace("\\n", "")
        yourNewString = yourNewString.replace("\\t", "")
        yourNewString = yourNewString.replace("&quot;", "")
        yourNewString = yourNewString.replace("&amp;", "&")
        yourNewString = yourNewString.replace("&lt;", "<")
        yourNewString = yourNewString.replace("&gt;", ">")
        yourNewString = yourNewString.replace("\\", "")
        return yourNewString
    }

}