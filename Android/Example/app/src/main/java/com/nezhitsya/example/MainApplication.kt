package com.nezhitsya.example

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("debug hash key", Utility.getKeyHash(this))

        KakaoSdk.init(this, resources.getString(R.string.kakao_app_key))
    }

}