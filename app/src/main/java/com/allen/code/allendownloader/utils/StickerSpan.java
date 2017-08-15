package com.allen.code.allendownloader.utils;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

public class StickerSpan extends ImageSpan {

    private final TextView view;
    private float space;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public StickerSpan(View view, Drawable b, int verticalAlignment) {
        super(b, verticalAlignment);
        this.view = (TextView) view;
        space = ((TextView) view).getLineSpacingExtra() * 0.8f;



    }

    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        canvas.save();
        float transY =  bottom - b.getBounds().bottom - space;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            int textLength = text.length();
            for (int i = 0; i < textLength; i++) {
                if (Character.isLetterOrDigit(text.charAt(i))) {
                    transY -= paint.getFontMetricsInt().descent;
                    break;
                }
            }
        }
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}  