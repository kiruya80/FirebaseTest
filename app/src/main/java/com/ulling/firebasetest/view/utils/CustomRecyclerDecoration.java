package com.ulling.firebasetest.view.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 리사이클러뷰
 */
public class CustomRecyclerDecoration extends RecyclerView.ItemDecoration {
    private final int divHeight;

    public CustomRecyclerDecoration(int divHeight) {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = divHeight;
        outRect.left = divHeight;
        outRect.right = divHeight;
        outRect.bottom = divHeight;
    }
}