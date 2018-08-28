package com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel

import android.arch.lifecycle.ViewModel
import android.os.Bundle


/**
 * Created by「 The Khaeng 」on 23 Aug 2017 :)
 */

abstract class BaseViewModel : ViewModel() {

    open fun onSaveInstanceState(outState: Bundle?) {
    }

    open fun onRestoreInstanceStates(savedInstanceState: Bundle) {
    }

}
