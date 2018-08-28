package com.app.blockchain.certnetwork.common.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import timber.log.Timber
import java.io.File
import java.util.*


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */


private val cachedFontMap = hashMapOf<String, Typeface>()

@ColorInt
fun Context.getColorFromId(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}

@ColorInt
fun Context.getAttrColor(attributeId: Int): Int {
    return ContextCompat.getColor(this, getAttr(attributeId))
}

fun Context.getAttr(attributeId: Int): Int {
    return try {
        val typedValue = TypedValue()
        this.theme.resolveAttribute(attributeId, typedValue, true)
        typedValue.resourceId

    } catch (e: Resources.NotFoundException) {
        -1
    }
}

fun Context.inflate(layoutResId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = true): View =
        LayoutInflater.from(this).inflate(layoutResId, parent, attachToRoot)

fun Context.getFloatDimen(@DimenRes resId: Int): Float {
    val outValue = TypedValue()
    this.resources.getValue(resId, outValue, true)
    return outValue.float
}


fun Context.getStringResByName(name: String): String {
    return this.resources.getString(this.resources.getIdentifier(
            name,
            "string",
            this.packageName))
}

fun Context.getColorResByName(name: String): Int {
    return ContextCompat.getColor(this, this.resources.getIdentifier(
            name,
            "color",
            this.packageName))
}

fun Context.findFont(fontPath: String?, defaultFontPath: String? = null): Typeface {

    if (fontPath == null) {
        return Typeface.DEFAULT
    }

    val fontName = File(fontPath).name
    var defaultFontName = ""
    if (!TextUtils.isEmpty(defaultFontPath)) {
        defaultFontName = File(defaultFontPath).name
    }

    return if (cachedFontMap.containsKey(fontName)) {
        cachedFontMap[fontName] ?: Typeface.DEFAULT
    } else {
        try {
            val assets = this.resources.assets

            if (Arrays.asList(*assets.list("")).contains(fontPath)) {
                val typeface = Typeface.createFromAsset(this.assets, fontName)
                cachedFontMap.put(fontName, typeface)
                typeface
            } else if (Arrays.asList(*assets.list("fonts")).contains(fontName)) {
                val typeface = Typeface.createFromAsset(this.assets, String.format("fonts/%s", fontName))
                cachedFontMap.put(fontName, typeface)
                typeface
            } else if (Arrays.asList(*assets.list("iconfonts")).contains(fontName)) {
                val typeface = Typeface.createFromAsset(this.assets, String.format("iconfonts/%s", fontName))
                cachedFontMap.put(fontName, typeface)
                typeface
            } else if (!TextUtils.isEmpty(defaultFontPath) && Arrays.asList(*assets.list("")).contains(defaultFontPath)) {
                val typeface = Typeface.createFromAsset(this.assets, defaultFontPath)
                cachedFontMap.put(defaultFontName, typeface)
                typeface
            } else {
                throw Exception("Font not Found")
            }

        } catch (e: Exception) {
            Timber.e(String.format("Unable to find %s font. Using Typeface.DEFAULT instead.", fontName))
            cachedFontMap[fontName] = Typeface.DEFAULT
            Typeface.DEFAULT
        }

    }
}

val Context?.deviceScreenSize: Point
    get() {
        val wm = this?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)

        val outMetrics = DisplayMetrics()
        display.getRealMetrics(outMetrics)

        val resolutionX = outMetrics.widthPixels
        val resolutionY = outMetrics.heightPixels

        val point = Point()
        point.x = resolutionX
        point.y = resolutionY
        return point
    }

