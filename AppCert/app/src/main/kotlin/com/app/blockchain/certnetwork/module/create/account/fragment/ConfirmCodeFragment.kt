package com.app.blockchain.certnetwork.module.create.account.fragment


import android.arch.lifecycle.Observer
import android.os.Bundle
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppMvvmFragment
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource
import com.app.blockchain.certnetwork.appcommon.dialog.hideLoading
import com.app.blockchain.certnetwork.appcommon.dialog.showLoading
import com.app.blockchain.certnetwork.appcommon.snackbar.showSnackbarError
import com.app.blockchain.certnetwork.common.extension.clickToHideKeyboard
import com.app.blockchain.certnetwork.common.extension.delay
import com.app.blockchain.certnetwork.common.extension.notnull
import com.app.blockchain.certnetwork.module.create.account.CreateAccountViewModel
import com.app.blockchain.certnetwork.module.main.MainActivity
import com.app.blockchain.certnetwork.widget.ConfirmCodeView
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.lib.nextwork.engine.model.NetworkStatus
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.fragment_create_account_confirm_code.confirm_code as confirmCode
import kotlinx.android.synthetic.main.fragment_create_account_confirm_code.confirm_code_background as background
import kotlinx.android.synthetic.main.fragment_create_account_confirm_code.confirm_code_btn_bottom_next as btnNext
import kotlinx.android.synthetic.main.fragment_create_account_confirm_code.confirm_code_edt_phone as edtMobileNumber
import kotlinx.android.synthetic.main.fragment_create_account_confirm_code.create_account_ic_back as btnBack


/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

class ConfirmCodeFragment : AppMvvmFragment() {

    companion object {
        fun newInstance(): ConfirmCodeFragment {
            val fragment = ConfirmCodeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    val countdownTimeSec: Long = 30L
    var viewModelShared: CreateAccountViewModel? = null
    val firebaseAuth = FirebaseAuth.getInstance()
    private var verificationId: String? = null


    override
    fun onSetupViewModel(savedInstanceState: Bundle?) {
        super.onSetupViewModel(savedInstanceState)
        viewModelShared = getSharedViewModel(CreateAccountViewModel::class.java)?.apply {
            triggerAddUser.observeData(this@ConfirmCodeFragment, onObserverAddUser)
        }
    }


    override
    fun setupLayoutView(): Int = R.layout.fragment_create_account_confirm_code

    override
    fun onInitialize(savedInstanceState: Bundle?) {
        super.onInitialize(savedInstanceState)
        viewModelShared?.apply {
            requestConfirmCode(account.mobileNumber)
        }
    }

    override
    fun onSetupView(savedInstanceState: Bundle?) {
        super.onSetupView(savedInstanceState)
        btnBack.setOnClickListener { activity?.onBackPressed() }
        activity?.clickToHideKeyboard(background)


        btnNext.setOnClickListener {
            if (validate()) {
                confirmCode(confirmCode.code)
            }
        }

        edtMobileNumber?.setText(viewModelShared?.account?.mobileNumber)
    }

    fun validate(): Boolean {
        when (confirmCode.validateCode()) {
            ConfirmCodeView.INVALIDATE -> {
                return false
            }
        }
        return true
    }

    fun requestConfirmCode(mobileNumber: String) {
        viewModelShared?.apply {
            activity?.let {
                verificationId = null
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        mobileNumber,
                        countdownTimeSec,
                        TimeUnit.SECONDS,
                        it,
                        phoneAuthListener)
            }
        }
    }


    fun confirmCode(code: String) {
        try {
            if (verificationId != null) {
                val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
                signInWithCredential(credential)
            } else {
                showSnackbarError(getString(R.string.alert_service_unavailable))
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun signInWithCredential(credential: AuthCredential?, delay: Long = 0) {
        showButtonProgress()
        showLoading()
        delay({
            (activity to credential)
                    .notnull { activity, auth ->
                        firebaseAuth.signInWithCredential(auth)
                                .addOnCompleteListener(activity) { task ->
                                    if (task.isSuccessful) {
                                        Timber.d("signInWithCredential:success")
                                        viewModelShared?.account?.firebaseUserId = firebaseAuth.currentUser?.uid ?: ""
                                        viewModelShared?.createEthAddress()
                                        viewModelShared?.createAccount()
                                    } else {
                                        confirmCode?.setError(getString(R.string.alert_error_confirm_code_invalid))
                                        Timber.w(task.exception)
                                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                        }
                                        hideLoading()
                                        hideButtonProgress()
                                    }
                                }

                    }
        }, delay)
    }

    private fun showButtonProgress() {
        btnNext?.isEnabled = false
        btnNext?.setText(R.string.please_wait)
    }

    private fun hideButtonProgress() {
        btnNext?.isEnabled = true
        btnNext?.setText(R.string.next)
    }

    private val phoneAuthListener = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override
        fun onVerificationCompleted(auth: PhoneAuthCredential?) {
            val smsCode = auth?.smsCode
            if (smsCode != null) {
                confirmCode?.code = smsCode
                signInWithCredential(auth, 100)
            } else {
                signInWithCredential(auth)
            }
        }

        override
        fun onVerificationFailed(e: FirebaseException?) {
            if (e is FirebaseTooManyRequestsException) {

            }
            Timber.e(e)
        }

        override
        fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(verificationId, token)
            Timber.i("verificationId: $verificationId")
            this@ConfirmCodeFragment.verificationId = verificationId
        }

    }

    private val onObserverAddUser: Observer<AppResource<Boolean>> =
            Observer { resource: AppResource<Boolean>? ->
                when {
                    resource?.status == NetworkStatus.SUCCESS -> {
                        resource.data?.let {
                            openActivityWithAllFinish(MainActivity::class.java)
                        }
                    }
                    resource?.status == NetworkStatus.LOADING_FROM_NETWORK -> {
                        showLoading()
                    }
                    resource?.status == NetworkStatus.ERROR -> {
                        hideLoading()
                        hideButtonProgress()
                        showSnackbarError(getString(R.string.alert_service_unavailable))
                        openActivityWithFinish(MainActivity::class.java)

                    }
                }
            }


}

