package com.app.blockchain.certnetwork.appcommon.base.mvvm

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.app.blockchain.certnetwork.appcommon.R
import com.app.blockchain.certnetwork.appcommon.base.adapter.AppMvvmAdapter
import com.app.blockchain.certnetwork.appcommon.base.adapter.BaseItemListDiffCallback
import com.app.blockchain.certnetwork.common.base.mvvm.exception.TypeNotMatchInAdapterException
import com.app.blockchain.certnetwork.common.base.utils.DefaultLinearLayoutManager
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_recycler_view.fragment_recycler_view as fragmentRecyclerView
import kotlinx.android.synthetic.main.fragment_recycler_view.fragment_recycler_view_container as background


/**
 * Created by「 The Khaeng 」on 25 Aug 2017 :)
 */

@Suppress("SortModifiers")
abstract class AppMvvmRecyclerViewFragment<VH : BaseViewHolder<*>>
    : AppMvvmFragment() {

    companion object {
        val TAG: String = javaClass::class.java.simpleName
    }

    lateinit var layoutManager: RecyclerView.LayoutManager

    override
    fun setupLayoutView(): Int = R.layout.fragment_recycler_view

    lateinit var adapter: FragmentAdapter<VH>


    var recyclerView: RecyclerView? = null

    var container: ViewGroup? = null
        get() = background

    override
    final fun onSetupView(savedInstanceState: Bundle?) {
        super.onSetupView(savedInstanceState)
        layoutManager = setupLayoutManager()
        adapter = setupAdapter()
        this.recyclerView = setupRecyclerView() ?: fragmentRecyclerView
        onSetupRecyclerView(recyclerView, adapter)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter

    }

    open fun setupLoadmoreLayout(): Int = -1

    open fun setupRecyclerView(): RecyclerView? = null

    open fun setupLayoutManager(): RecyclerView.LayoutManager = DefaultLinearLayoutManager(context)

    open fun setupAdapter(): FragmentAdapter<VH> = FragmentAdapter(this)

    open fun onSetupRecyclerView(recyclerView: RecyclerView?, adapter: FragmentAdapter<VH>) {
    }

    abstract fun registerItemList(): MutableList<BaseItem>

    open fun getItemCount(): Int {
        return registerItemList().size
    }

    open fun getItemViewType(position: Int): Int {
        return registerItemList()[position].type
    }

    open fun getItemId(position: Int): Long {
        return registerItemList()[position].id
    }

    open fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        throw TypeNotMatchInAdapterException(TAG, viewType)
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

    fun showLoadmore(show:Boolean){
        adapter.showLoadmore(show)
    }


    class FragmentAdapter<VH : BaseViewHolder<*>>(
            private val recyclerFragment: AppMvvmRecyclerViewFragment<VH>)
        : AppMvvmAdapter<VH>(recyclerFragment) {

        override
        fun setupLoadmoreLayout(): Int = recyclerFragment.setupLoadmoreLayout()

        override
        fun getItemCount(loadmoreCount: Int): Int = loadmoreCount + recyclerFragment.getItemCount()

        override
        fun getItemViewTypeWithLoadmore(position: Int): Int = recyclerFragment.getItemViewType(position)

        override
        fun getItemId(position: Int): Long = recyclerFragment.getItemId(position)


        override
        fun onCreateViewHolderWithLoadmore(parent: ViewGroup, viewType: Int): VH {
            return recyclerFragment.onCreateViewHolder(parent, viewType)
        }

        override
        fun onBindViewHolderWithLoadmore(holder: VH, pos: Int) {
            super.onBindViewHolderWithLoadmore(holder,pos)
            recyclerFragment.onBindViewHolder(recyclerFragment.registerItemList(), holder, pos)
        }

    }
}

