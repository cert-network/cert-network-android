package com.app.blockchain.certnetwork.common.base.mvvm.layer1View

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.app.blockchain.certnetwork.common.base.delegate.RxDelegation
import com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.BaseViewModel
import io.reactivex.disposables.Disposable


/**
 * Created by「 The Khaeng 」on 24 Aug 2017 :)
 */

abstract class BaseMvvmFragment : BaseFragment() {

    private val rxDelegation = RxDelegation()
    private var viewModels: Array<out BaseViewModel> = arrayOf()

    override
    fun onCreate(savedInstanceState: Bundle?) {
        onSetupViewModel(savedInstanceState)
        viewModels.forEach {
            if (savedInstanceState!=null) {
                it.onRestoreInstanceStates(savedInstanceState)
            }
        }
        super.onCreate(savedInstanceState)
    }


    open fun onSetupViewModel(savedInstanceState: Bundle?) {

    }

    fun registerSavedState(vararg viewModels: BaseViewModel){
        this.viewModels = viewModels
    }

    fun <VM : ViewModel> getViewModel(viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(this).get(viewModelClass)

    fun <VM : ViewModel> getViewModel(key: String, viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(this).get(key, viewModelClass)


    fun <VM : ViewModel> getSharedViewModel(viewModelClass: Class<VM>): VM?
            = activity?.let { ViewModelProviders.of(it).get(viewModelClass) }


    override
    fun onDestroy() {
        super.onDestroy()
        rxDelegation.clearAllDisposables()
        viewModels = arrayOf()
    }

    override
    fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModels.forEach { it.onSaveInstanceState(outState) }
    }

    fun addDisposable(d: Disposable) {
        rxDelegation.addDisposable(d)
    }

}

