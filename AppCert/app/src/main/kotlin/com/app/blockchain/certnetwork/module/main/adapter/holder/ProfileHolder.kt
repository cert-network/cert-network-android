package com.app.blockchain.certnetwork.module.main.adapter.holder

import android.view.ViewGroup
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.appcommon.utils.setCircleImage
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder
import com.app.blockchain.certnetwork.module.main.adapter.item.ProfileItem
import kotlinx.android.synthetic.main.holder_profile.view.holder_profile_image as imgProfile
import kotlinx.android.synthetic.main.holder_profile.view.holder_profile_tv_address as tvAddress
import kotlinx.android.synthetic.main.holder_profile.view.holder_profile_tv_mobile_number as tvMobileNumber
import kotlinx.android.synthetic.main.holder_profile.view.holder_profile_tv_name as tvName


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

class ProfileHolder(parent: ViewGroup)
    : BaseViewHolder<ProfileItem>(parent, R.layout.holder_profile) {

    override
    fun onBind(item: ProfileItem) {
        super.onBind(item)

        itemView.apply {
            imgProfile.setCircleImage(R.drawable.mock_user)
            tvName.text = item.name ?: ""
            tvMobileNumber.text = item.mobileNumber ?: ""
            tvAddress.text = item.address ?: ""
        }
    }
}
