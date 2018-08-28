package com.app.blockchain.certnetwork.module.cert.add

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppMvvmActivity
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource
import com.app.blockchain.certnetwork.appcommon.dialog.hideLoading
import com.app.blockchain.certnetwork.appcommon.dialog.showLoading
import com.app.blockchain.certnetwork.appcommon.snackbar.showSnackbarError
import com.lib.nextwork.engine.model.NetworkStatus
import kotlinx.android.synthetic.main.activity_add_cert.add_cert_edt_desc as edtDesc
import kotlinx.android.synthetic.main.activity_add_cert.add_cert_edt_expired as edtExpired
import kotlinx.android.synthetic.main.activity_add_cert.add_cert_edt_industry as edtIndustry
import kotlinx.android.synthetic.main.activity_add_cert.add_cert_edt_name as edtName
import kotlinx.android.synthetic.main.activity_add_cert.add_cert_toolbar_btn_back as btnBack
import kotlinx.android.synthetic.main.activity_add_cert.add_cert_toolbar_tv_submit as tvSubmit


/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

class AddCertActivity : AppMvvmActivity() {

    private lateinit var viewModel: AddCertViewModel

    override
    fun onSetupViewModel(savedInstanceState: Bundle?) {
        super.onSetupViewModel(savedInstanceState)
        viewModel = getViewModel(AddCertViewModel::class.java).apply {
            triggerAddCert.observeData(this@AddCertActivity, onObserverAddCert)
        }
    }

    override
    fun setupLayoutView(): Int = R.layout.activity_add_cert



    override
    fun onSetupView(savedInstanceState: Bundle?) {
        super.onSetupView(savedInstanceState)
        btnBack.setOnClickListener { onBackPressed() }

        tvSubmit.setOnClickListener {
            viewModel.requestAddCertificate(
                    name = edtName.text.toString().trim(),
                    category = edtIndustry.text.toString().trim(),
                    expiredDate = edtExpired.text.toString().trim(),
                    desc = edtDesc.text.toString().trim()
            )
        }
    }

    private val onObserverAddCert: Observer<AppResource<Boolean>> =
            Observer { resource: AppResource<Boolean>? ->
                when {
                    resource?.status == NetworkStatus.SUCCESS -> {
                        hideLoading()
                        resource.data?.let {
                            viewModel.clearAllRateLimiter()
                            finish()
                        }
                    }
                    resource?.status == NetworkStatus.LOADING_FROM_NETWORK -> {
                        showLoading()
                    }
                    resource?.status == NetworkStatus.ERROR -> {
                        hideLoading()
                        showSnackbarError(getString(R.string.alert_service_unavailable))
                    }
                }
            }


}

