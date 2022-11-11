package com.nezhitsya.example.base

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import com.nezhitsya.example.R
import com.nezhitsya.example.utils.CommonDialog
import com.nezhitsya.example.viewModel.PermissionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseActivity: AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var dataBinding: ViewDataBinding
    lateinit var progressBar: ProgressBar

    private var requestPermission: (() -> Unit)? = null
    private var requestPermissionList = arrayListOf<String>()
    private val permissionList = arrayListOf<String>()
    private val permissionViewModel: PermissionViewModel by viewModels()

    open val layoutResource = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        job = Job()
        dataBinding = DataBindingUtil.setContentView(this, layoutResource)
        dataBinding.lifecycleOwner = this

        setProgress()
        postCreate()
        setObserver()

        permissionViewModel.checkPermissionList.observe(this, {
            requestPermissionList = it
            checkPermission()
        })

        permissionViewModel.requestPermission.observe(this, {
            requestPermission = it
            requestPermission()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun navigation(id: Int) {
//        Navigation.findNavController(this, R.layout.fragment_container).navigate(id)
    }

    fun setViewModel(viewModelList: List<BaseViewModel>) {
        viewModelList.forEach { viewModel ->
            viewModel.showToast.observe(this, {
                showToast(it)
            })
            viewModel.showDialog.observe(this, {
                showDialog(it)
            })
            viewModel.showActionDialog.observe(this, {
                showActionDialog(it)
            })
            viewModel.showProgress.observe(this, {
                showProgress(it)
            })

        }
    }

    open fun setObserver() { }
    open fun postCreate() { }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showDialog(message: String) {
        CommonDialog(this, message, Pair("OK", "")).show()
    }

    private fun showActionDialog(pair: Pair<String, () -> Unit>) {
        CommonDialog(
            context = this,
            msg = pair.first,
            doneAction = pair.second
        ).show()
    }

    private fun setProgress() {
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)

        val layoutParam = ConstraintLayout.LayoutParams(100, 100)
        layoutParam.topToTop = ConstraintSet.PARENT_ID
        layoutParam.bottomToBottom = ConstraintSet.PARENT_ID
        layoutParam.leftToLeft = ConstraintSet.PARENT_ID
        layoutParam.rightToRight = ConstraintSet.PARENT_ID

        (dataBinding.root as ConstraintLayout).addView(progressBar, layoutParam)
        progressBar.indeterminateTintList = ColorStateList.valueOf(Color.LTGRAY)
        progressBar.visibility = View.GONE
    }

    private fun showProgress(isShow: Boolean) {
        if (isShow) {
            progressBar.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            progressBar.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun checkPermission() {
        permissionList.clear()
        requestPermissionList.forEach {
            if (it != Manifest.permission.WRITE_EXTERNAL_STORAGE || Build.VERSION_CODES.Q > Build.VERSION.SDK_INT) {
                if (ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(it)
                }
            }
        }

        permissionViewModel.isRequestPermission.run {
            if (permissionList.isNotEmpty()) {
                postValue(true)
            } else {
                postValue(false)
            }
        }
    }

    private fun requestPermission() {
        if (permissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionList.toArray(arrayOfNulls(permissionList.size)),
                5000
            )
        } else {
            requestPermission?.invoke()
        }
    }

}