package com.lipy.annulusprogress;


import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 环形进度条
 * Created by lipy on 2017/3/13 0013.
 */
public class AnnulusProgress extends RelativeLayout {
    private CircleTextProgressbar mProgressBar;
    private View mResultLL;
    private View mResultError;
    private View mResultSuccess;
    private TextView mResultTv;
    private int mScreenWidth;

    public AnnulusProgress(Context context) {
        super(context);
        initView();
    }

    public AnnulusProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AnnulusProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        RelativeLayout viewGroup =
                (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.annulus_progress, this);
        mProgressBar = (CircleTextProgressbar) viewGroup.findViewById(R.id.tv_red_progress_text2);

        mResultLL = viewGroup.findViewById(R.id.result_ll);
        mResultError = viewGroup.findViewById(R.id.result_error);
        mResultSuccess = viewGroup.findViewById(R.id.result_success);
        mResultTv = (TextView) viewGroup.findViewById(R.id.result_tv);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        LayoutParams params = new LayoutParams(mScreenWidth, mScreenWidth);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        viewGroup.setLayoutParams(params);

    }

    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

//    public void setText(String text) {
//        mProgressBar.setText(text);
//    }

    public int getProgress() {
        return mProgressBar.getProgress();
    }

    public void setResult(boolean isSuccess) {
        if (isSuccess) {
            mResultLL.setVisibility(View.VISIBLE);
            mResultError.setVisibility(View.GONE);
            mResultSuccess.setVisibility(View.VISIBLE);
            mResultTv.setText("生成成功");
        } else {
            mResultLL.setVisibility(View.VISIBLE);
            mResultError.setVisibility(View.VISIBLE);
            mResultSuccess.setVisibility(View.GONE);
            mResultTv.setText("生成失败");
        }
        mProgressBar.setResult(isSuccess);
    }

    public void reSet(){
        mResultLL.setVisibility(View.GONE);
        mProgressBar.reSet();
    }


}
