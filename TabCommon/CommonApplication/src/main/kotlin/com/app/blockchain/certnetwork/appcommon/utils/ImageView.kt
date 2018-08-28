package com.app.blockchain.certnetwork.appcommon.utils

import android.support.annotation.DrawableRes
import android.support.media.ExifInterface
import android.widget.ImageView
import com.app.blockchain.certnetwork.appcommon.R
import com.app.blockchain.certnetwork.common.glide.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */
fun ImageView.setCircleImage(@DrawableRes id: Int,
                             cache: DiskCacheStrategy = DiskCacheStrategy.ALL) {
    GlideApp.with(this.context)
            .load(id)
            .transition(withCrossFade())
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.placeholder_circle)
            .diskCacheStrategy(cache)
            .into(this)
}


private fun getDateTimeMeta(url: String?): String? {
    return try {
        val exif = url?.let { ExifInterface(url) }
        exif?.getAttribute(ExifInterface.TAG_DATETIME)
    } catch (e: Exception) {
        null
    }
}

