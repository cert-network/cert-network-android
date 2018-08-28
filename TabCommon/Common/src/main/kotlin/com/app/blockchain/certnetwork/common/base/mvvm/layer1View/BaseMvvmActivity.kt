package com.app.blockchain.certnetwork.common.base.mvvm.layer1View

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.app.blockchain.certnetwork.common.base.delegate.RxDelegation
import com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.BaseViewModel
import io.reactivex.disposables.Disposable

/**
 * Created by「 The Khaeng 」on 27 Aug 2017 :)
 */

abstract class BaseMvvmActivity : BaseActivity() {

    private val rxDelegation = RxDelegation()

    private var viewModels: Array<out BaseViewModel> = arrayOf()

    override
    fun onCreate(savedInstanceState: Bundle?) {
        onSetupViewModel(savedInstanceState)
        viewModels.forEach {
            if (savedInstanceState != null) {
                it.onRestoreInstanceStates(savedInstanceState)
            }
        }
        super.onCreate(savedInstanceState)
    }

    open fun onSetupViewModel(savedInstanceState: Bundle?) {

    }

    override
    fun onDestroy() {
        super.onDestroy()
        rxDelegation.clearAllDisposables()
        viewModels = arrayOf()
    }

    fun <VM : BaseViewModel> getViewModel(viewModelClass: Class<VM>): VM
            = ViewModelProviders
            .of(this)
            .get(viewModelClass)

    fun <VM : BaseViewModel> getViewModel(key:String, viewModelClass: Class<VM>): VM
            = ViewModelProviders
            .of(this)
            .get(key,viewModelClass)


    fun registerSavedState(vararg viewModels: BaseViewModel){
        this.viewModels = viewModels
    }


    override
    fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        viewModels.forEach { it.onSaveInstanceState(outState) }
    }


    fun addDisposable(d: Disposable) {
        rxDelegation.addDisposable(d)
    }

}

