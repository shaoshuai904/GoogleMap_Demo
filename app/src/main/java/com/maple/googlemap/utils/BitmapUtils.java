package com.maple.googlemap.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;

/**
 * @author maple
 * @time 2016/11/14
 */
public class BitmapUtils {

    public static Bitmap getCustomTextBitmap(int iconSize, int color, String text) {
        return getCustomTextBitmap(iconSize, color, iconSize / 15, text);
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
    public static Bitmap getCustomTextBitmap(int iconSize, int color, int padding, String text) {
        Bitmap bitmap = Bitmap.createBitmap(iconSize, iconSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        RectF rect = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        // draw background
        paint.setColor(Color.WHITE);
        canvas.drawOval(rect, paint);
        paint.setColor(color);
        canvas.drawOval(new RectF(padding, padding, rect.width() - padding, rect.height() - padding), paint);
        // draw text
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);
        paint.setTextSize(iconSize * 0.4f);
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        float baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(text, rect.centerX(), baseline, paint);
        return bitmap;
    }


    // 圆背景
    private static LayerDrawable makeClusterBackground(float mDensity) {
        ShapeDrawable mColoredCircleBackground = new ShapeDrawable(new OvalShape());
        ShapeDrawable outline = new ShapeDrawable(new OvalShape());
        outline.getPaint().setColor(0x80ffffff); // Transparent white.
        mColoredCircleBackground.getPaint().setColor(0xFF2574CC);
        LayerDrawable background = new LayerDrawable(new Drawable[]{outline, mColoredCircleBackground});
        int strokeWidth = (int) (mDensity * 2);
        background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        return background;
    }

    // 方块背景
    private static LayerDrawable makeRectBackground(float mDensity) {
        ShapeDrawable mColoredCircleBackground = new ShapeDrawable(new RectShape());
        ShapeDrawable outline = new ShapeDrawable(new RectShape());
        outline.getPaint().setColor(0xBBffffff); // Transparent white.
        mColoredCircleBackground.getPaint().setColor(0xFFa12a62);
        LayerDrawable background = new LayerDrawable(new Drawable[]{outline, mColoredCircleBackground});
        int strokeWidth = (int) (mDensity * 2);
        background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        return background;
    }

}
