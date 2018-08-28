package com.app.blockchain.certnetwork.appcommon.base.adapter


import android.support.v7.util.DiffUtil
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem

/**
 * Created by「 The Khaeng 」on 28 Mar 2018 :)
 */
class BaseItemListDiffCallback(private val oldOrderItemList: MutableList<BaseItem>,
                               private val newOrderItemList: MutableList<BaseItem>) : DiffUtil.Callback() {

    override
    fun getOldListSize(): Int {
        return oldOrderItemList.size
    }

    override
    fun getNewListSize(): Int {
        return newOrderItemList.size
    }

    override
    fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldOrderItemList[oldItemPosition].id == newOrderItemList[newItemPosition].id
    }

    override
    fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldOrderItemList[oldItemPosition] == newOrderItemList[newItemPosition]
    }
}
