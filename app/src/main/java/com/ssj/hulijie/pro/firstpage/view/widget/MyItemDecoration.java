package com.ssj.hulijie.pro.firstpage.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ssj.hulijie.R;


/**
 * @author vic_zhang .
 *         on 2017/11/17
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;


    public MyItemDecoration(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.shape_rv_decor);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizantial(c, parent);
        drawVertical(c, parent);
    }

    /**
     * 画水平线
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int left = childAt.getRight() + layoutParams.rightMargin;
            int top = childAt.getTop() + layoutParams.topMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int bottom = childAt.getBottom() + layoutParams.bottomMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 画水平间隔线
     *
     * @param c
     * @param parent
     */
    private void drawHorizantial(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            if (isLastRow(parent, parent.getChildAdapterPosition(child))) {
                bottom = top;
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int right = mDivider.getIntrinsicWidth();
        int bottom = mDivider.getIntrinsicHeight();
        if (isLastColumn(parent, parent.getChildAdapterPosition(view)) || getSpanCount(parent) == 1) {
            right = 0;
        }
        if (isLastRow(parent, parent.getChildAdapterPosition(view))) {
            bottom = 0;
        }
        outRect.set(0, 0, right, bottom);
    }

    /**
     * 判断是否最后一列
     * <p>
     * 思路：
     *
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int itemPosition) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //横竖都要判断
        if (layoutManager instanceof GridLayoutManager) {
            //是最后一列
            if ((itemPosition + 1) % getSpanCount(parent) == 0) {
                return true;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否最后一行
     * <p>
     * 思路：  1 当count%span ==0:   position>=count-span
     * 2 当count%span!=0 : position > = count -count%span
     *
     * @return
     */
    private boolean isLastRow(RecyclerView parent, int itemPosition) {
        int spanCount = getSpanCount(parent);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int childCount = parent.getAdapter().getItemCount();
        if (layoutManager instanceof GridLayoutManager) {
            if (childCount % spanCount == 0) {
                if (itemPosition >= childCount - spanCount) {
                    return true;
                }
            } else {
                if (itemPosition >= childCount - childCount % spanCount) {
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (itemPosition == childCount - 1) {
                return true;
            }
        }
        return false;
    }

    private int getSpanCount(RecyclerView parent) {
        int count = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //横竖都要判断
        if (layoutManager instanceof GridLayoutManager) {
            count = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return count;
    }
}
