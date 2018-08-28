package com.app.blockchain.certnetwork.module.main.adapter.holder

import android.view.ViewGroup
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder
import com.app.blockchain.certnetwork.module.main.adapter.item.CertCardItem
import kotlinx.android.synthetic.main.holder_cert_card.view.holder_cert_tv_sub_title as tvSubTitle
import kotlinx.android.synthetic.main.holder_cert_card.view.holder_cert_tv_title as tvTitle


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

class CertCardHolder(parent: ViewGroup)
    : BaseViewHolder<CertCardItem>(parent, R.layout.holder_cert_card) {

    override
    fun onBind(item: CertCardItem) {
        super.onBind(item)
        itemView.apply {
            if (item.isApprove) {
                tvTitle.text = getString(R.string.approve)
//                tvTitle.setTextColor(context.getColorFromId(R.color.text_active_white))
            } else {
//                tvTitle.setTextColor(context.getColorFromId(R.color.alert_snackbar_error))
                tvTitle.text = getString(R.string.reject)
            }
            tvSubTitle.text = item.certificateName ?: ""
        }
    }
}
