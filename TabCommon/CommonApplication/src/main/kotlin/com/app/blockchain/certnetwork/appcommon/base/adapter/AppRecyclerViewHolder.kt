package com.app.blockchain.certnetwork.appcommon.base.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.app.blockchain.certnetwork.appcommon.R
import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppMvvmRecyclerViewFragment
import com.app.blockchain.certnetwork.common.base.mvvm.exception.TypeNotMatchInAdapterException
import com.app.blockchain.certnetwork.common.base.utils.DefaultLinearLayoutManager
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder
import kotlinx.android.synthetic.main.holder_recycler_view.view.holder_nested_recycler_view as nestRecyclerView


/**
 * Created by「 The Khaeng 」on 23 Aug 2017 :)
 */

@Suppress("LeakingThis")
abstract class AppRecyclerViewHolder<VH : BaseViewHolder<*>, I>(
        parent: ViewGroup,
        layout: Int = R.layout.holder_recycler_view)
    : BaseViewHolder<I>(parent, layout) {

    var adapter: RecyclerView.Adapter<VH>
    var itemList: MutableList<BaseItem> = mutableListOf()

    init {
        adapter = setupAdapter()
        (setupRecyclerView() ?: itemView.nestRecyclerView)?.apply {
            layoutManager = setupLayoutManager()
            adapter = this@AppRecyclerViewHolder.adapter
        }
        onSetupRecyclerView(setupRecyclerView() ?: itemView.nestRecyclerView, adapter)
    }

    open fun setupRecyclerView(): RecyclerView? = null

    open fun setupLayoutManager(): RecyclerView.LayoutManager = DefaultLinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

    open fun setupAdapter(): RecyclerView.Adapter<VH> = HolderAdapter(this)

    open fun onSetupRecyclerView(recyclerView: RecyclerView?, adapter: RecyclerView.Adapter<VH>) {

    }

    fun setupItemList(itemList: MutableList<BaseItem>) {
        this.itemList = itemList
    }

    open fun getItemCount(): Int {
        return itemList.size
    }

    open fun getItemViewType(position: Int): Int {
        return itemList[position].type
    }

    open fun getItemId(position: Int): Long {
        return itemList[position].id
    }

    open fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        throw TypeNotMatchInAdapterException(AppMvvmRecyclerViewFragment.TAG, viewType)
    }

    open fun onBindViewHolder(itemList: MutableList<*>, holder: BaseViewHolder<*>, pos: Int) {
    }

    fun notifyDataSetChangedWithDiffUtil(
            oldItemList: MutableList<BaseItem>?,
            newItemList: MutableList<BaseItem>?) {
        val diffResult = DiffUtil.calculateDiff(
                BaseItemListDiffCallback(
                        oldItemList ?: mutableListOf(),
                        newItemList ?: mutableListOf()))
        diffResult.dispatchUpdatesTo(adapter)
    }


    fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    fun notifyItemChanged(position: Int, payload: Any? = null) {
        adapter.notifyItemChanged(position, payload)
    }

    fun notifyItemInserted(position: Int) {
        adapter.notifyItemInserted(position)
    }

    fun notifyItemRemoved(position: Int) {
        adapter.notifyItemRemoved(position)
    }

    fun notifyItemMoved(fromPosition: Int, toPosition: Int) {
        adapter.notifyItemMoved(fromPosition, toPosition)
    }

    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any? = null) {
        adapter.notifyItemRangeChanged(positionStart, itemCount, payload)
    }

    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int) {
        adapter.notifyItemRangeRemoved(positionStart, itemCount)
    }

    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int) {
        adapter.notifyItemRangeInserted(positionStart, itemCount)
    }

    class HolderAdapter<VH : BaseViewHolder<*>>(
            private val holderRecyclerView: AppRecyclerViewHolder<VH, *>)
        : AppAdapter<VH>(holderRecyclerView.itemView.context) {

        override
        fun getItemCount(loadmoreCount: Int): Int   = loadmoreCount + holderRecyclerView.getItemCount()

        override
        fun getItemViewTypeWithLoadmore(position: Int): Int = holderRecyclerView.getItemViewType(position)

        override
        fun getItemId(position: Int): Long = holderRecyclerView.getItemId(position)

        override
        fun onCreateViewHolderWithLoadmore(parent: ViewGroup, viewType: Int): VH {
            return holderRecyclerView.onCreateViewHolder(parent, viewType)
        }

        override
        fun onBindViewHolderWithLoadmore(holder: VH, pos: Int) {
            super.onBindViewHolderWithLoadmore(holder,pos)
            this.holderRecyclerView.onBindViewHolder(this.holderRecyclerView.itemList, holder, pos)
        }

    }

}
