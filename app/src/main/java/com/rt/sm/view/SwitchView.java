package com.rt.sm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rt.sm.R;
import com.rt.sm.utils.CommonUtil;

/**
 * Created by gaodesong on 18/1/20.
 */

public class SwitchView extends View{


    private Paint paint,mPaint,wPaint,twPaint,tmPaint;

    private Context context;

    private String mText="男";

    private String wText="女";

    private String selected="男";

    private Rect mrect=null;

    private int mTextSize;

    public SwitchView(Context context) {
        this(context,null);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RTextView);
        mTextSize = a.getDimensionPixelSize(R.styleable.RTextView_tSize, 20);
        a.recycle();
        init();
    }


    private void init(){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);
        paint.setColor(getResources().getColor(R.color.colorTvH));
        paint.setTextSize(mTextSize);


        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);


        wPaint=new Paint();
        wPaint.setAntiAlias(true);
        wPaint.setStrokeWidth(1);

        twPaint=new Paint();
        twPaint.setAntiAlias(true);
        twPaint.setStrokeWidth(1);
        twPaint.setTextSize(mTextSize);

        tmPaint=new Paint();
        tmPaint.setAntiAlias(true);
        tmPaint.setStrokeWidth(1);
        tmPaint.setTextSize(mTextSize);


    }

    public void setSex(String sex){
        selected=sex;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rect=new RectF(0,0,getWidth(),getHeight());
        canvas.drawRoundRect(rect, CommonUtil.dip2px(context,20),CommonUtil.dip2px(context,20),paint);

        if (selected.equals(mText)){
            mPaint.setColor(getResources().getColor(R.color.colorTv));
            RectF mrectF=new RectF(0,0,getWidth()/2,getHeight());
            canvas.drawRoundRect(mrectF, CommonUtil.dip2px(context,20),CommonUtil.dip2px(context,20),mPaint);

            mrect=new Rect();
            tmPaint.setColor(getResources().getColor(R.color.black));
            tmPaint.getTextBounds(mText, 0, mText.length(), mrect);
            Paint.FontMetricsInt fm = tmPaint.getFontMetricsInt();
            float startX = getWidth() / 2 / 2 - mrect.width() / 2;
            int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;
            canvas.drawText(mText, startX, startY, tmPaint);


            mrect=new Rect();
            twPaint.setColor(getResources().getColor(R.color.colorTv));
            twPaint.getTextBounds(wText, 0, wText.length(), mrect);
            float wstartX = getWidth()/2+getWidth() / 2 / 2 - mrect.width() / 2;
            canvas.drawText(wText, wstartX, startY, twPaint);
        }else {


            RectF mrectF=new RectF(getWidth()/2,0,getWidth(),getHeight());
            wPaint.setColor(getResources().getColor(R.color.colorRed));
            canvas.drawRoundRect(mrectF, CommonUtil.dip2px(context,20),CommonUtil.dip2px(context,20),wPaint);

            tmPaint.setColor(getResources().getColor(R.color.colorTv));
            mrect=new Rect();
            tmPaint.getTextBounds(mText, 0, mText.length(), mrect);
            Paint.FontMetricsInt fm = tmPaint.getFontMetricsInt();
            float startX = getWidth() / 2 / 2 - mrect.width() / 2;
            int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;
            canvas.drawText(mText, startX, startY, tmPaint);


            twPaint.setColor(getResources().getColor(R.color.black));
            mrect=new Rect();
            twPaint.getTextBounds(wText, 0, wText.length(), mrect);
            float wstartX = getWidth()/2+getWidth() / 2 / 2 - mrect.width() / 2;
            canvas.drawText(wText, wstartX, startY, twPaint);


        }






    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
