package com.app.blockchain.certnetwork.common.base.view

import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import android.widget.FrameLayout

/**
 * Created by「 The Khaeng 」on 20 Feb 2018 :)
 */

abstract class BaseViewGroup : FrameLayout {

    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        setup(attrs, defStyleAttr, 0)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        setup(attrs, defStyleAttr, defStyleRes)
    }


    private fun setup(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        inflateLayout()
        if (attrs != null) setupStyleables(attrs, defStyleAttr, defStyleRes)
        bindView()
        setupInstance()
        setupView()
    }

    /**
     * Custom handle SavedState child to "fix restore state in same resource id"
     * when use ViewGroup more than one.
     *
     *
     * the method must be call in subclass.
     */
    protected fun onSaveChildInstanceState(ss: ChildSavedState): Parcelable {
        ss.childrenStates = SparseArray()
        for (i in 0 until childCount) {
            val id = getChildAt(i).id
            if (id != 0) {
                val childrenState = SparseArray<Parcelable>()
                getChildAt(i).saveHierarchyState(childrenState)
                ss.childrenStates?.put(id, childrenState)
            }

        }
        return ss
    }

    override
    fun onRestoreInstanceState(state: Parcelable) {
        if (state !is ChildSavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        onRestoreChildInstanceState(state)
    }


    override
    fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override
    fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    private fun inflateLayout() {
        View.inflate(context, getLayoutRes(), this)
    }

    @Suppress("UNCHECKED_CAST")
    private fun onRestoreChildInstanceState(ss: ChildSavedState) {
        for (i in 0 until childCount) {
            val id = getChildAt(i).id
            if (id != 0) {
                if (ss.childrenStates?.get(id) != null) {
                    val childrenState = ss.childrenStates?.get(id) as SparseArray<Parcelable?>
                    getChildAt(i).restoreHierarchyState(childrenState)
                }
            }
        }
    }


    protected abstract fun getLayoutRes(): Int

    protected abstract fun setupView()

    protected open fun setupStyleables(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {}

    protected open fun bindView() {}

    protected open fun setupInstance() {}


    abstract class ChildSavedState : View.BaseSavedState {
        internal var childrenStates: SparseArray<Any>? = null

        constructor(superState: Parcelable) : super(superState)

        constructor(`in`: Parcel, classLoader: ClassLoader) : super(`in`) {
            childrenStates = `in`.readSparseArray(classLoader)
        }

        override
        fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeSparseArray(childrenStates)
        }
    }


}
