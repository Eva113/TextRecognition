package com.example.macbook.myapplication.util

import android.content.Context
import android.hardware.Camera
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets

fun View.applyWindowInsets(callback: (WindowInsets) -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
        setOnApplyWindowInsetsListener { _, insets -> insets.apply { callback(insets) } }
    }
}

var View.bottomMargin: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0
    set(value) {
        layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams)?.also {
            it.bottomMargin = value
        }
    }

fun Camera.Parameters.setOptimalPreviewSize(width: Int, height: Int) {
    val ASPECT_TOLERANCE = 0.5
    val targetRatio = height.toDouble() / width.toDouble()

    var optimalSize: Camera.Size = supportedPreviewSizes[0]
    var minDiff = java.lang.Double.MAX_VALUE

    val targetHeight = height

    for (size in supportedPreviewSizes) {
        val ratio = size.width.toDouble() / size.height.toDouble()
        if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue
        if (Math.abs(ratio - targetRatio) < minDiff) {
            optimalSize = size
            minDiff = Math.abs(ratio - targetRatio)
        }
    }

    setPreviewSize(optimalSize.width, optimalSize.height)


}