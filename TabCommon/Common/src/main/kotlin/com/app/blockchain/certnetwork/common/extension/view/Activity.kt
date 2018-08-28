package com.app.blockchain.certnetwork.common.extension.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.util.DisplayMetrics
import android.widget.Toast


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

var previousBackTime = 0L

fun Activity.doubleBackPressedFinish() {
    val currentBackTime =  System.currentTimeMillis()
    if (currentBackTime - previousBackTime < 1000) {
        finish()
        return
    }

    Toast.makeText(this, "Please back again to exit.", Toast.LENGTH_SHORT).show()

    previousBackTime = System.currentTimeMillis()
}


fun Activity.restart() {
    val intent = this.intent
    this.overridePendingTransition(0, 0)
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
    this.finish()
    this.overridePendingTransition(0, 0)
    this.startActivity(intent)
}

fun Activity.screenShot(isDeleteStatusBar: Boolean = false): Bitmap {
    val decorView = this.window.decorView
    val bitmap = Bitmap.createBitmap(decorView.measuredWidth, decorView.measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    decorView.layout(0, 0, decorView.measuredWidth, decorView.measuredHeight)
    decorView.draw(canvas)

    val dm = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(dm)
    return if (isDeleteStatusBar) {
        val resources = resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(resourceId)
        Bitmap.createBitmap(bitmap, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight)
    } else {
        Bitmap.createBitmap(bitmap, 0, 0, dm.widthPixels, dm.heightPixels)
    }
}


val Activity?.activityScreenSize: Point
    get() {
        val point = Point()
        this?.windowManager?.defaultDisplay?.getSize(point)
        return point
    }



