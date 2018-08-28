package com.app.blockchain.certnetwork.common.base.mvvm.layer1View.holder

import android.view.ViewGroup
import com.app.blockchain.certnetwork.common.R
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder
import com.app.blockchain.certnetwork.common.extension.view.setHeightViewSize
import com.app.blockchain.certnetwork.common.extension.view.show
import kotlinx.android.synthetic.main.holder_space.view.holder_space as space



/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

class SpaceHolder(itemView: ViewGroup)
    : BaseViewHolder<SpaceItem>(itemView, R.layout.holder_space) {

    override
    fun onBind(item: SpaceItem) {
        super.onBind(item)
        itemView.space.show(item.isShow)
        if (item.heightPx > 0) {
            itemView.space.setHeightViewSize(item.heightPx)
        }
    }
}
