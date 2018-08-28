package com.app.blockchain.certnetwork.common.base.mvvm.layer1View


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder


/**
 * Created by「 The Khaeng 」on 20 Sep 2017 :)
 */

@Suppress("PrivatePropertyName")
abstract class BaseListAdapter<VH : BaseViewHolder<*>>(
        val context:Context?
) : BaseLoadmoreListAdapter<VH>() {

    private val TAG: String = javaClass.simpleName

    protected var clickListener: BaseViewHolder.OnClickViewHolderListener? = null

    var recyclerView: RecyclerView? = null
        private set

    fun setOnClickHolderItemListener(listener: BaseViewHolder.OnClickViewHolderListener) {
        this.clickListener = listener
    }


    override
    fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }




    override
    fun onBindViewHolderWithLoadmore(holder: VH, pos: Int) {
        if (clickListener != null) {
            holder.itemView.rootView.setOnClickListener(setOnClickItem(holder))
        }
    }

    private fun setOnClickItem(holder: VH): View.OnClickListener {
        return View.OnClickListener {
            clickListener?.onClick(holder)
        }
    }

}
