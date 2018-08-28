package com.app.blockchain.certnetwork.module.cert.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppMvvmActivity
import com.app.blockchain.certnetwork.appcommon.utils.setCircleImage
import com.app.blockchain.certnetwork.common.extension.toast
import com.app.blockchain.certnetwork.common.extension.view.hide
import com.app.blockchain.certnetwork.common.extension.view.show
import com.app.blockchain.certnetwork.module.main.adapter.item.CertCardItem
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlinx.android.synthetic.main.activity_cert_detail.cert_detail_btn_save as btnSave
import kotlinx.android.synthetic.main.activity_cert_detail.cert_detail_container as container
import kotlinx.android.synthetic.main.activity_cert_detail.cert_detail_ic_back as btnBack
import kotlinx.android.synthetic.main.activity_cert_detail.cert_detail_ic_share as btnShare
import kotlinx.android.synthetic.main.activity_cert_detail.cert_detail_image as imgProfile
import kotlinx.android.synthetic.main.activity_cert_detail.cert_detail_tv_approve_by as tvApproveBy
import kotlinx.android.synthetic.main.activity_cert_detail.cert_detail_tv_cert_name as tvCertName
import kotlinx.android.synthetic.main.activity_cert_detail.cert_detail_tv_name as tvName
import kotlinx.android.synthetic.main.activity_cert_detail.cert_detail_tv_status as tvStatus






/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

class CertDetailActivity : AppMvvmActivity() {

    companion object {
        const val KEY_CERT = "CertDetailActivity:key_cert"
    }

    private lateinit var viewModel: CertDetailViewModel

    val certItem: CertCardItem? get() = intent?.extras?.getParcelable(KEY_CERT)

    override
    fun onSetupViewModel(savedInstanceState: Bundle?) {
        super.onSetupViewModel(savedInstanceState)
        viewModel = getViewModel(CertDetailViewModel::class.java)
    }

    override
    fun setupLayoutView(): Int = R.layout.activity_cert_detail


    @SuppressLint("SetTextI18n")
    override
    fun onSetupView(savedInstanceState: Bundle?) {
        super.onSetupView(savedInstanceState)
        btnBack.setOnClickListener { onBackPressed() }
        btnSave.setOnClickListener { toast(R.string.save) }
        imgProfile.setCircleImage(R.drawable.mock_user)

        if (certItem?.isApprove == true) {
            tvStatus.text = getString(R.string.approve)
        } else {
            tvStatus.text = getString(R.string.reject)
        }

        tvName?.text = certItem?.name ?: ""
        tvCertName?.text = certItem?.certificateName ?: ""

        val approveBy =
                if (certItem?.approveBy.isNullOrEmpty())
                    "Unknown"
                else
                    certItem?.approveBy
        tvApproveBy?.text = getString(R.string.cert_detail_approve_by) + " " + approveBy

        btnShare.setOnClickListener {
            takeScreenShot("certificate", container)
        }
    }

    private fun takeScreenShot(name: String, viewGroup: ViewGroup?) {
        if (viewGroup != null) {

            btnBack.hide()
            btnSave.hide()

            val bitmap = Bitmap.createBitmap(viewGroup.measuredWidth, viewGroup.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            viewGroup.layout(0, 0, viewGroup.measuredWidth, viewGroup.measuredHeight)
            viewGroup.draw(canvas)
            viewGroup.requestLayout()

            try {
                val file = writeFile(viewGroup.context, name, bitmap)
                val intent = getIntent(viewGroup.context, file)
                if (intent.resolveActivity(viewGroup.context.applicationContext.packageManager) != null) {
                    viewGroup.context.startActivity(Intent.createChooser(intent, "Share image via"))
                } else {
                    Toast.makeText(viewGroup.context, "No application can share capture screen :(", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            btnBack.show()
            btnSave.show()

        }
    }


    @Throws(IOException::class)
    private fun writeFile(context: Context, name: String, bitmap: Bitmap): File {
        val file = File(context.cacheDir, "$name.png")
        val fOut = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
        fOut.flush()
        fOut.close()
        return file
    }

    private fun getIntent(context: Context, file: File): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/png"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.putExtra(Intent.EXTRA_STREAM, CertProvider.getUriForFile(context, context.applicationContext.packageName + ".provider.certificate", file))
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        }

        return intent
    }


}

