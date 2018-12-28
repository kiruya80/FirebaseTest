package com.ulling.firebasetest.view.utils;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * 피카소 비율유지
 */
public class PicassoTransformations {
    private static PicassoTransformations SINGLE_U;
    public int targetWidth = 200;

    public static synchronized PicassoTransformations getInstance() {

        if (SINGLE_U == null) {
            SINGLE_U = new PicassoTransformations();
        }
        return SINGLE_U;
    }

    public Transformation getTransformation(int targetWidth_) {
        this.targetWidth = targetWidth_;
        return new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "resizeTransformation#" + System.currentTimeMillis();
            }
        };
    }
}
