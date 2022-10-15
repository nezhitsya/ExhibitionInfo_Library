package com.nezhitsya.example.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class BaseViewModel: ViewModel() {
    val showProgress = SingleLiveEvent<Boolean>()
    val showToast = SingleLiveEvent<String>()
    val showDialog = SingleLiveEvent<String>()
    val showActionDialog = SingleLiveEvent<Pair<String, () -> Unit>>()
    val eventLog = SingleLiveEvent<Pair<String, String>>()
    val enableSecureFlag = SingleLiveEvent<Boolean>()

    private fun showProgress() {
        showProgress.value = true
    }

    private fun hideProgress() {
        showProgress.value = false
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            showProgress()
            block.invoke(this)
            hideProgress()
        }
    }

    fun launch(dispatcher: CoroutineContext, block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(dispatcher) {
            block.invoke(this)
        }
    }

    fun launchNoneProgress(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            block.invoke(this)
        }
    }

    fun sendEvent(eventType: String, log: String) {
        eventLog.postValue(Pair(eventType, log))
    }

}