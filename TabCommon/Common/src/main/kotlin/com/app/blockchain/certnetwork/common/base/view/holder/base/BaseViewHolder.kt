package com.app.blockchain.certnetwork.common.base.view.holder.base

import android.graphics.Bitmap
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.app.blockchain.certnetwork.common.glide.GlideApp

/**
 * Created by「 The Khaeng 」on 23 Aug 2017 :)
 */

abstract class BaseViewHolder<I>(parent: ViewGroup, layout: Int)
    : RecyclerView.ViewHolder(LayoutInflater
        .from(parent.context)
        .inflate(layout, parent, false)) {

    var item: I? = null

    interface OnClickViewHolderListener {
        fun onClick(vh: RecyclerView.ViewHolder)
    }

    interface OnClickListener {
        fun onClick(view: View?, position: Int)
    }

    interface OnLongClickListener {
        fun onLongClick(view: View?, position: Int): Boolean
    }


    val recyclerView: RecyclerView = parent as RecyclerView

    val layoutManager: RecyclerView.LayoutManager? = recyclerView.layoutManager

    open var clickListener: BaseViewHolder.OnClickListener? = null
        set(value) {
            field = value
            itemView.setOnClickListener { v ->
                clickListener?.onClick(v, adapterPosition)
            }
        }

    open var longClickListener: BaseViewHolder.OnLongClickListener? = null
        set(value) {
            field = value
            itemView.setOnLongClickListener { v ->
                longClickListener?.onLongClick(v, adapterPosition)
                true
            }
        }

    init {
        onBindView(itemView)
    }

    open fun onBindView(view: View) {}


    open fun onBind(item: I) {
        this.item = item
    }

    fun getDimension(@DimenRes resId: Int): Float {
        return itemView.context.resources.getDimension(resId)
    }

    fun getString(@StringRes resId: Int): String {
        return itemView.context.resources.getString(resId)
    }


    fun setImage(imgView: ImageView,
                 url: String,
                 @DrawableRes placeholderId: Int) {
        GlideApp.with(itemView.context)
                .load(url)
                .placeholder(placeholderId)
                .centerCrop()
                .into(imgView)
    }

    fun setCircleImage(imgView: ImageView,
                       url: String,
                       @DrawableRes placeholderId: Int) {
        GlideApp.with(itemView.context)
                .load(url)
                .centerCrop()
                .placeholder(placeholderId)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(imgView)
    }

    fun setImage(imgView: ImageView,
                 transformation: Transformation<Bitmap>,
                 url: String,
                 @DrawableRes placeholderId: Int) {
        GlideApp.with(itemView.context)
                .load(url)
                .centerCrop()
                .placeholder(placeholderId)
                .transform(transformation)
                .into(imgView)
    }

}
