package com.app.blockchain.certnetwork.appcommon.widgets

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.app.blockchain.certnetwork.appcommon.R
import com.app.blockchain.certnetwork.common.extension.bouncedAction
import com.app.blockchain.certnetwork.common.extension.view.addPaddingBottom
import com.app.blockchain.certnetwork.common.extension.view.hide
import com.app.blockchain.certnetwork.common.extension.view.show

@Suppress("UNUSED_PARAMETER")
class AppRecyclerView
@JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
    : RecyclerView(context, attrs, defStyleAttr) {


    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var loading: Boolean = false

    init {
        init(context, attrs)
        initWithAttrs(attrs, defStyleAttr, 0)
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    private fun init(context: Context, attrs: AttributeSet?) {}

    private fun initWithAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {}

    fun enablePerformanceMode() {
        setHasFixedSize(true)
        setItemViewCacheSize(5)
    }



    fun registerScrollTopButton(container: ViewGroup, view: View) {
        view.hide()
        addPaddingBottom(context.resources.getDimension(R.dimen.recycler_view_scroll_to_top_margin).toInt())
        view.setOnClickListener { smoothScrollToPosition(0) }
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override
            fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                updateScrollToTopView(container, view)
            }
        })
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener?, visibleThreshold: Int = 3) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override
            fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = layoutManager
                if (linearLayoutManager is LinearLayoutManager) {
                    totalItemCount = linearLayoutManager.itemCount
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    if (!loading
                            && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        listener?.onLoadMore()
                        loading = true
                    }
                }

            }

        })
    }

    private fun updateScrollToTopView(container: ViewGroup, view: View) {
        val layout = layoutManager
        if (layout is LinearLayoutManager) {
            if (layout.findFirstCompletelyVisibleItemPosition() == 0) {
                view.hide()
            } else {
                bouncedAction("scroll_to_top:fade_in", 50) {
                    view.show()
                }
            }
        }
    }

    override
    fun canScrollVertically(direction: Int): Boolean {
        // check if scrolling up
        if (direction < 1) {
            val original = super.canScrollVertically(direction)
            return !original && getChildAt(0) != null && getChildAt(0).top < 0 || original
        }
        return super.canScrollVertically(direction)

    }


}