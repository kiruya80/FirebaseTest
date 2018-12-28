package com.ulling.firebasetest.view.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ulling.firebasetest.R;

import java.util.HashMap;

public class PicassoUtils {
    private static PicassoUtils SINGLE_U;

    public static synchronized PicassoUtils getInstance() {

        if (SINGLE_U == null) {
            SINGLE_U = new PicassoUtils();
        }
        return SINGLE_U;
    }

    public void RatioPicasso(String imgUrl, ImageView view,
                             int targetWidth,
                             int imgWidth, int imgHeight) {
        if (targetWidth != 0 && imgWidth != 0 && imgHeight != 0) {
            HashMap<String, Integer> size = getRatioImgSize(targetWidth,
                    imgWidth, imgHeight);

            Picasso.get()
                    .load(imgUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .resize(size.get("width"), size.get("height"))
                    .onlyScaleDown()
                    .into(view);
        } else {
            if (targetWidth > 0) {
                Picasso.get()
                        .load(imgUrl)
//                        .placeholder(R.drawable.ic_empty_img)
                        .placeholder(R.mipmap.ic_launcher)
                        .transform(PicassoTransformations.getInstance()
                                .getTransformation(targetWidth))
                        .into(view);

            } else {
                Picasso.get()
                        .load(imgUrl)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(view);
            }
        }
    }

    private HashMap<String, Integer> getRatioImgSize(int targetWidth,
                                                     int imgWidth, int imgHeight) {
        HashMap<String, Integer> size = new HashMap<>();
        double aspectRatio = (double) imgHeight / imgWidth;

        int targetHeight = (int) (targetWidth * aspectRatio);

        size.put("width", targetWidth);
        size.put("height", targetHeight);

        return size;
    }
}
