package com.maple.googlemap.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape

/**
 * @author maple
 * @time 2016/11/14
 */
object BitmapUtils {

    fun getCustomTextBitmap(iconSize: Int, color: Int, text: String): Bitmap {
        return getCustomTextBitmap(iconSize, color, iconSize / 15, text)
    }

    /**
     * 获取自定义文本的Bitmap
     *
     * @param iconSize 图形大小
     * @param color    图形颜色
     * @param padding  白边宽度
     * @param text     填充文字
     * @return
     */
    fun getCustomTextBitmap(iconSize: Int, color: Int, padding: Int, text: String): Bitmap {
        val bitmap = Bitmap.createBitmap(iconSize, iconSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val rect = RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat())
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DEV_KERN_TEXT_FLAG)
        // draw background
        paint.color = Color.WHITE
        canvas.drawOval(rect, paint)
        paint.color = color
        canvas.drawOval(RectF(padding.toFloat(), padding.toFloat(), rect.width() - padding, rect.height() - padding), paint)
        // draw text
        paint.color = Color.WHITE
        paint.strokeWidth = 3f
        paint.textSize = iconSize * 0.4f
        paint.textAlign = Paint.Align.CENTER
        val fontMetrics = paint.fontMetricsInt
        val baseline = (rect.bottom + rect.top - fontMetrics.bottom.toFloat() - fontMetrics.top.toFloat()) / 2
        canvas.drawText(text, rect.centerX(), baseline, paint)
        return bitmap
    }


    // 圆背景
    fun makeClusterBackground(mDensity: Float): LayerDrawable {
        val mColoredCircleBackground = ShapeDrawable(OvalShape())
        val outline = ShapeDrawable(OvalShape())
        outline.paint.color = -0x7f000001 // Transparent white.
        mColoredCircleBackground.paint.color = -0xda8b34
        val background = LayerDrawable(arrayOf<Drawable>(outline, mColoredCircleBackground))
        val strokeWidth = (mDensity * 2).toInt()
        background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth)
        return background
    }

    // 方块背景
    fun makeRectBackground(mDensity: Float): LayerDrawable {
        val mColoredCircleBackground = ShapeDrawable(RectShape())
        val outline = ShapeDrawable(RectShape())
        outline.paint.color = -0x44000001 // Transparent white.
        mColoredCircleBackground.paint.color = -0x5ed59e
        val background = LayerDrawable(arrayOf<Drawable>(outline, mColoredCircleBackground))
        val strokeWidth = (mDensity * 2).toInt()
        background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth)
        return background
    }

}
