package com.app.blockchain.certnetwork.common.base.mvvm.layer1View


import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.app.blockchain.certnetwork.common.base.mvvm.exception.TypeNotMatchInAdapterException
import com.app.blockchain.certnetwork.common.base.mvvm.layer1View.holder.LoadmoreHolder
import com.app.blockchain.certnetwork.common.base.mvvm.layer1View.holder.LoadmoreItem
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder


/**
 * Created by「 The Khaeng 」on 20 Sep 2017 :)
 */

abstract class BaseLoadmoreListAdapter<VH : BaseViewHolder<*>>
    : RecyclerView.Adapter<VH>() {

    private val TAG: String = javaClass.simpleName
    private var loadmoreLayout: Int = -1
        get() {
            if (field == -1) {
                field = setupLoadmoreLayout()
            }
            return field
        }

    val itemCountNoLoadmore: Int
        get() = if (isShowLoadmore && loadmoreLayout != -1) itemCount - 1 else itemCount


    open fun setupLoadmoreLayout(): Int = -1

    private var isShowLoadmore: Boolean = false

    override
    fun getItemCount(): Int = if (isShowLoadmore && loadmoreLayout != -1) getItemCount(1) else getItemCount(0)

    abstract fun getItemCount(loadmoreCount: Int): Int


    @Deprecated("use getItemViewTypeWithLoadmore()")
    override
    fun getItemViewType(position: Int): Int {
        if (isShowLoadmore && position >= itemCount - 1 && loadmoreLayout != -1) {
            return LoadmoreItem.TYPE
        }
        return getItemViewTypeWithLoadmore(position)
    }

    abstract fun getItemViewTypeWithLoadmore(position: Int): Int

    @Deprecated("use onCreateViewHolderWithLoadmore()")
    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return if (isShowLoadmore && viewType == LoadmoreItem.TYPE && loadmoreLayout != -1) {
            LoadmoreHolder(parent, loadmoreLayout) as VH
        } else {
            onCreateViewHolderWithLoadmore(parent, viewType)
        }
    }

    open fun onCreateViewHolderWithLoadmore(parent: ViewGroup, viewType: Int): VH {
        throw TypeNotMatchInAdapterException(TAG, viewType)
    }

    override
    fun onBindViewHolder(holder: VH, position: Int) {
        if (isShowLoadmore && position >= itemCount - 1 && loadmoreLayout != -1) {
            return
        }
        onBindViewHolderWithLoadmore(holder, position)
    }

    abstract fun onBindViewHolderWithLoadmore(holder: VH, position: Int)


    fun showLoadmore(show: Boolean) {
        isShowLoadmore = show
        if (show) {
            notifyItemInserted(itemCount)
        } else {
            notifyItemRemoved(itemCount)
        }
    }

}
