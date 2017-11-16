package com.ssj.hulijie.widget.dialog;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ssj.hulijie.R;


/**
 * @author vic_zhang .
 *         on 2017/11/15
 */

public class BesselProgressView extends View {

    /**
     * 自定义画笔
     */
    private Paint mPanit;

    /**
     * 固定圆的半径
     */
    private float radius;

    /**
     * 移动圆的半径
     */
    private float animateRadius;


    /**
     * 一次动画的时间
     */
    private int mDuration = 2800;

    /**
     * 控件宽
     */
    private int w;

    /**
     * 控件高
     */
    private int h;

    /**
     * 贝赛尔路径
     */
    private Path mPath;

    /**
     * 移动圆的x
     */
    private float floatX;

    /**
     * 动画圆顶部y
     *
     * @param context
     */
    private float animateY;

    /**
     * 固定圆顶部的y
     */
    private float circleY;

    private float firstX;
    private float secondX;
    private float thirdX;

    /**
     * 圆的颜色
     */
    private int color;


    public BesselProgressView(Context context) {
        super(context);
    }

    public BesselProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BesselProgressView);
        color = a.getColor(R.styleable.BesselProgressView_circleColor, context.getResources().getColor(R.color.colorAccent));
        mDuration = a.getInt(R.styleable.BesselProgressView_animateDuration, mDuration);
        a.recycle();
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        w = getWidth();
        h = getHeight();
        radius = h / 3;
        animateRadius = radius * 0.8f;
        animateY = h / 2 - animateRadius;
        circleY = h / 2 - radius;

        firstX = w / 4;
        secondX = w * 2 / 4;
        thirdX = w * 3 / 4;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(firstX - radius * 3, thirdX + radius * 3);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                floatX = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 初始化
     */
    private void init() {
        mPanit = new Paint();
        mPanit.setColor(color);
        mPanit.setAntiAlias(true);

        mPath = new Path();


    }


    /**
     * 定义控件宽高
     * 如果有定义高宽就按定义的高和宽来设置，如果没有定义的话，默认 ：宽* 高  = 480 * 100
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSIze = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            h = heightSIze;
        } else {
            h = getPaddingTop() + 100 + getPaddingBottom();
        }

        /**
         * 控制宽度，保证左右滑动不会出界
         */
        if (widthMode == MeasureSpec.EXACTLY) {
            w = widthSize;
            double v = w / 4 - (h / 3 * 3 + h / 3 * 0.8);
            if (v < 0) {
                v = -v;
            }
            w = (int) (w + v * 4);
        } else {
            w = getPaddingLeft() + 480 + getPaddingRight();
        }
        setMeasuredDimension(w, h);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawThreeCircle(canvas);
        drawMoveCircle(canvas);
        drawBesel(canvas);
        super.onDraw(canvas);
    }

    /**
     * 画贝赛尔曲线
     * <p>
     * 分成六个曲线来画，分别为   1.第一个圆的左边
     * 2.第一个圆的右边
     * 3.第二个圆的左边
     * 4.第二个圆的右边
     * 5.第三个圆的左边
     * 6.第三个圆的右边
     *
     * @param canvas
     */
    private void drawBesel(Canvas canvas) {

        //1.第一个圆的左边
        if (floatX < firstX) {
            canvas.drawCircle(firstX, h / 2, radius * 1.1f, mPanit);
            //称动到动画圆的顶部
            mPath.moveTo(floatX, animateY);
            mPath.quadTo((firstX - floatX) / 2 + floatX, h / 2, firstX, circleY);
            mPath.lineTo(firstX, circleY + radius * 2);
            mPath.quadTo((firstX - floatX) / 2 + floatX, h / 2, floatX, animateY + animateRadius * 2);
            mPath.close();
        }
        //2.第一个圆的右边
        if (floatX > firstX && floatX < firstX + radius) {
            canvas.drawCircle(firstX, h / 2, radius * 1.1f, mPanit);
            //称动到动画圆的顶部
            mPath.moveTo(floatX, animateY);
            mPath.quadTo((floatX - firstX) / 2 + firstX, h / 2, firstX, circleY);
            mPath.lineTo(firstX, circleY + radius * 2);
            mPath.quadTo((floatX - firstX) / 2 + firstX, h / 2, floatX, animateY + animateRadius * 2);
            mPath.close();
        }
        //3.第二个圆的左边
        if (floatX > secondX - radius && floatX < secondX) {
            canvas.drawCircle(secondX, h / 2, radius * 1.1f, mPanit);
            //称动到动画圆的顶部
            mPath.moveTo(floatX, animateY);
            mPath.quadTo((secondX - floatX) / 2 + secondX, h / 2, secondX, circleY);
            mPath.lineTo(secondX, circleY + radius * 2);
            mPath.quadTo((secondX - floatX) / 2 + secondX, h / 2, floatX, animateY + animateRadius * 2);
            mPath.close();
        }
        //4.第二个圆的右边
        if (floatX > secondX && floatX < secondX + radius) {
            canvas.drawCircle(secondX, h / 2, radius * 1.1f, mPanit);
            //称动到动画圆的顶部
            mPath.moveTo(floatX, animateY);
            mPath.quadTo((floatX - secondX) / 2 + secondX, h / 2, secondX, circleY);
            mPath.lineTo(w * 2 / 4, circleY + radius * 2);
            mPath.quadTo((floatX - secondX) / 2 + secondX, h / 2, floatX, animateY + animateRadius * 2);
            mPath.close();
        }
        //5.第三个圆的左边
        if (floatX > thirdX - radius && floatX < thirdX) {
            canvas.drawCircle(thirdX, h / 2, radius * 1.1f, mPanit);
            //称动到动画圆的顶部
            mPath.moveTo(floatX, animateY);
            mPath.quadTo((thirdX - floatX) / 2 + thirdX, h / 2, thirdX, circleY);
            mPath.lineTo(thirdX, circleY + radius * 2);
            mPath.quadTo((thirdX - floatX) / 2 + thirdX, h / 2, floatX, animateY + animateRadius * 2);
            mPath.close();
        }
        //6.第三个圆的右边
        if (floatX > thirdX && floatX < thirdX + 3 * radius) {
            canvas.drawCircle(thirdX, h / 2, radius * 1.1f, mPanit);
            //称动到动画圆的顶部
            mPath.moveTo(floatX, animateY);
            mPath.quadTo((floatX - thirdX) / 2 + thirdX, h / 2, thirdX, circleY);
            mPath.lineTo(thirdX, circleY + radius * 2);
            mPath.quadTo((floatX - thirdX) / 2 + thirdX, h / 2, floatX, animateY + animateRadius * 2);
            mPath.close();

        }
        canvas.drawPath(mPath, mPanit);
        mPath.reset();
    }

    /**
     * 画移动的圆
     *
     * @param canvas
     */
    private void drawMoveCircle(Canvas canvas) {
        canvas.drawCircle(floatX, h / 2, animateRadius, mPanit);
    }

    /**
     * 画固定三个圆
     *
     * @param canvas
     */
    private void drawThreeCircle(Canvas canvas) {
        canvas.drawCircle(firstX, h / 2, radius, mPanit);
        canvas.drawCircle(secondX, h / 2, radius, mPanit);
        canvas.drawCircle(thirdX, h / 2, radius, mPanit);
    }
}
