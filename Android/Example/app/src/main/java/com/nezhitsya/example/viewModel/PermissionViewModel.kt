package com.nezhitsya.example.viewModel

import com.nezhitsya.example.base.BaseViewModel
import com.nezhitsya.example.base.SingleLiveEvent

class PermissionViewModel: BaseViewModel() {
    val checkPermissionList = SingleLiveEvent<ArrayList<String>>()
    val requestPermission = SingleLiveEvent<() -> Unit>()
    val isRequestPermission = SingleLiveEvent<Boolean>()
}