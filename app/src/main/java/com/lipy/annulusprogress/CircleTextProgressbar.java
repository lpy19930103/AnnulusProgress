package com.lipy.annulusprogress;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * 环形进度条
 * Created by lipy on 2017/3/11 0009.
 */
public class CircleTextProgressbar extends TextView {

    /**
     * 外部轮廓的颜色。
     */
    private int outLineColor = getResources().getColor(R.color.progress);

    private int textColor = getResources().getColor(R.color.text_gray);

    private int errorColor = getResources().getColor(R.color.error_color);//错误圈

    private DrawPaint mDrawPaint;
    /**
     * 圆环的宽度。
     */
    private int lineWidth = DensityUtil.dp2px(getContext(), 1);

    /**
     * 进度条的颜色。
     */
    private int progressLineColor = outLineColor;

    /**
     * 进度条的宽度。
     */
    private int progressLineWidth = DensityUtil.dp2px(getContext(), 11);


    /**
     * 进度条的矩形区域。
     */
    private RectF mArcRect = new RectF();

    /**
     * 进度。
     */
    private int progress = 0;

    /**
     * 结果标识
     */
    private boolean buildResult = false;

    /**
     * 失败标识
     */
    private boolean isError = false;

    /**
     * 进度文字
     */
    private String progressStr = "";
    private String progressText = "";

    /**
     * View的显示区域。
     */
    final Rect bounds = new Rect();

    public CircleTextProgressbar(Context context) {
        this(context, null);
    }

    public CircleTextProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDrawPaint = new DrawPaint();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            mDrawPaint.init();
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 设置圆环的颜色。
     */
    public void setLineColor(int lineColor) {
        this.outLineColor = lineColor;
        invalidate();
    }

    /**
     * 设置外部轮廓的颜色。
     */
    public void setOutLineWidth(int outLineWidth) {
        this.lineWidth = outLineWidth;
        invalidate();
    }


    /**
     * 设置进度条颜色。
     */
    public void setProgressColor(int progressLineColor) {
        this.progressLineColor = progressLineColor;
        invalidate();
    }

    /**
     * 设置进度条线的宽度。
     */
    public void setProgressLineWidth(int progressLineWidth) {
        this.progressLineWidth = progressLineWidth;
        invalidate();
    }

    /**
     * 设置进度。
     */
    public void setProgress(int progress_) {
        this.progress = validateProgress(progress_);
        progressStr = "文件生成中";
        progressText = progress + "%";
        invalidate();
    }

    /**
     * 验证进度。
     */
    private int validateProgress(int progress) {
        if (progress > 100)
            progress = 100;
        else if (progress < 0)
            progress = 0;
        return progress;
    }

    /**
     * 获取此时的进度。
     */
    public int getProgress() {
        return progress;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //获取view的边界
        getDrawingRect(bounds);
        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
        float outerRadius = size / 2;

        //画内部背景
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - lineWidth, mDrawPaint.backgroudPaint);

        if (!buildResult) {
            //画边框圆
            canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - lineWidth / 2 - progressLineWidth,
                    mDrawPaint.framePaint);

            //画字
            float textY1 = bounds.centerY() + (mDrawPaint.textPaint1.descent() + mDrawPaint.textPaint1.ascent()) / 2 - DensityUtil.dp2px(getContext(), 1);
            canvas.drawText(progressText, bounds.centerX(), textY1, mDrawPaint.textPaint1);

            //画字
            float textY2 = bounds.centerY() + DensityUtil.dp2px(getContext(), 20) - ((mDrawPaint.textPaint2.descent() + mDrawPaint.textPaint2.ascent()) / 2);
            canvas.drawText(progressStr, bounds.centerX(), textY2, mDrawPaint.textPaint2);

            //画进度条
            int deleteWidth = progressLineWidth + lineWidth;
            mArcRect.set(bounds.left + deleteWidth / 2, bounds.top + deleteWidth / 2, bounds.right - deleteWidth / 2, bounds.bottom - deleteWidth / 2);
            canvas.drawArc(mArcRect, -90, 360 * progress / 100, false, mDrawPaint.progressPaint);
        } else {
            if (isError) {
                canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - lineWidth / 2 - progressLineWidth / 2, mDrawPaint.errorPaint);
            } else {
                canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - lineWidth / 2 - progressLineWidth / 2, mDrawPaint.progressPaint);
            }
        }


    }


    private float getProgressAngle() {
        return getProgress() / (float) 100 * 360f;
    }

    public void setResult(boolean isSuccess) {
        buildResult = true;
        isError = !isSuccess;
        invalidate();
    }

    public void reSet() {
        buildResult = false;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int lineWidth_ = 4 * (lineWidth + progressLineWidth);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = (width > height ? width : height) + lineWidth_;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }

    private class DrawPaint {

        public Paint backgroudPaint;
        public Paint framePaint;
        public Paint textPaint1;
        public Paint textPaint2;
        public Paint errorPaint;
        public Paint progressPaint;

        public void init() {

            //画内部背景
            int circleColor = getResources().getColor(R.color.white);
            backgroudPaint = new Paint();
            backgroudPaint.setStyle(Paint.Style.FILL);
            backgroudPaint.setColor(circleColor);

            //画边框圆
            framePaint = new Paint();
            framePaint.setStyle(Paint.Style.STROKE);
            framePaint.setStrokeWidth(lineWidth);
            framePaint.setColor(outLineColor);

            //画字
            textPaint1 = new Paint();
            textPaint1.setColor(progressLineColor);
            textPaint1.setAntiAlias(true);
            textPaint1.setTextSize(DensityUtil.sp2px(getContext(), 30));
            textPaint1.setTextAlign(Paint.Align.CENTER);

            //画字
            textPaint2 = new Paint();
            textPaint2.setColor(textColor);
            textPaint2.setAntiAlias(true);
            textPaint2.setTextAlign(Paint.Align.CENTER);
            textPaint2.setTextSize(DensityUtil.sp2px(getContext(), 14));

            //画进度条
            progressPaint = new Paint();
            progressPaint.setColor(progressLineColor);
            progressPaint.setStyle(Paint.Style.STROKE);
            progressPaint.setAntiAlias(true);
            progressPaint.setStrokeWidth(progressLineWidth);

            //错误圈
            errorPaint = new Paint();
            errorPaint.setStyle(Paint.Style.STROKE);
            errorPaint.setStrokeWidth(progressLineWidth);
            errorPaint.setColor(errorColor);
        }

    }

}
