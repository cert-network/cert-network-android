package com.app.blockchain.certnetwork.module.create.account.fragment


import android.os.Bundle
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppMvvmFragment
import com.app.blockchain.certnetwork.appcommon.snackbar.showSnackbarError
import com.app.blockchain.certnetwork.appcommon.utils.INVALIDATE_EMPTY
import com.app.blockchain.certnetwork.appcommon.utils.INVALIDATE_MOBILE_NUMBER_FORMAT
import com.app.blockchain.certnetwork.appcommon.utils.validateEmpty
import com.app.blockchain.certnetwork.appcommon.utils.validateMobileNumber
import com.app.blockchain.certnetwork.common.extension.clickToHideKeyboard
import com.app.blockchain.certnetwork.module.create.account.CreateAccountViewModel
import kotlinx.android.synthetic.main.fragment_create_account_confirm_code.create_account_ic_back as btnBack
import kotlinx.android.synthetic.main.fragment_create_account_put_information.put_info_background as background
import kotlinx.android.synthetic.main.fragment_create_account_put_information.put_info_btn_bottom_next as btnNext
import kotlinx.android.synthetic.main.fragment_create_account_put_information.put_info_container as container
import kotlinx.android.synthetic.main.fragment_create_account_put_information.put_info_coordinator_layout as coordinatorLayout
import kotlinx.android.synthetic.main.fragment_create_account_put_information.put_info_edt_layout_phone as edtMobileNumberLayout
import kotlinx.android.synthetic.main.fragment_create_account_put_information.put_info_edt_name as edtName
import kotlinx.android.synthetic.main.fragment_create_account_put_information.put_info_edt_phone as edtMobileNumber
import kotlinx.android.synthetic.main.fragment_create_account_put_information.put_info_phone_number_container as containerMobileNumber



/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

class PutInformationFragment : AppMvvmFragment() {
    companion object {
        fun newInstance(): PutInformationFragment {
            val fragment = PutInformationFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    private var viewModelShared: CreateAccountViewModel? = null

    override
    fun onSetupViewModel(savedInstanceState: Bundle?) {
        super.onSetupViewModel(savedInstanceState)
        viewModelShared = getSharedViewModel(CreateAccountViewModel::class.java)?.apply {
        }
    }


    override
    fun setupLayoutView(): Int = R.layout.fragment_create_account_put_information

    override
    fun onSetupView(savedInstanceState: Bundle?) {
        super.onSetupView(savedInstanceState)
        targetView = coordinatorLayout

        btnBack.setOnClickListener { activity?.onBackPressed() }
        activity?.clickToHideKeyboard(background)

        btnNext.setOnClickListener {
            if (validate()) {
                viewModelShared?.account?.name = edtName.text.toString().trim()
                viewModelShared?.account?.mobileNumber = edtMobileNumber.text.toString().trim()
                openFragmentSlideFromRight(ConfirmCodeFragment.newInstance())
            }
        }

    }


    private fun validate(): Boolean {
        when (edtName?.text.toString().validateEmpty()) {
            INVALIDATE_EMPTY -> {
                showSnackbarError(getString(R.string.alert_error_name_empty))
                return false
            }

        }
        when (edtMobileNumber?.text.toString().validateMobileNumber()) {
            INVALIDATE_EMPTY -> {
                showSnackbarError(getString(R.string.alert_error_mobile_number_empty))
                return false
            }
            INVALIDATE_MOBILE_NUMBER_FORMAT -> {
                showSnackbarError(getString(R.string.alert_error_mobile_number_format))
                return false
            }

        }

        return true
    }


}

