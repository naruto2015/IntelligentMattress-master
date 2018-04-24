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

import org.w3c.dom.Text;

/**
 * Created by gaodesong on 18/1/21.
 */

public class CircleProgressView extends View{

    private Paint paint,mPaint;

    private int firstColor;

    private int progressColor;

    private Context context;

    private String text="0";

    private int mTextSize;

    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RTextView);
        mTextSize = a.getDimensionPixelSize(R.styleable.RTextView_tSize, 20);
        a.recycle();

        init();

    }


    private void init(){

        firstColor=getResources().getColor(R.color.circlecoloryellow);
        progressColor=getResources().getColor(R.color.circlecolor);
        paint=new Paint();
        paint.setColor(firstColor);
        paint.setStrokeWidth(CommonUtil.dip2px(context,10));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        mPaint=new Paint();
        mPaint.setColor(progressColor);
        mPaint.setStrokeWidth(CommonUtil.dip2px(context,10));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        canvas.translate(getWidth()/2,getWidth()/2);
        canvas.drawCircle(0, 0, getWidth()/2-CommonUtil.dip2px(context,10), paint); //画出圆环

        RectF rectF=new RectF(0-(getWidth()/2-CommonUtil.dip2px(context,10)),
                0-(getWidth()/2-CommonUtil.dip2px(context,10)),
                getWidth()/2-CommonUtil.dip2px(context,10),
                getWidth()/2-CommonUtil.dip2px(context,10));

        mPaint.setStrokeWidth(CommonUtil.dip2px(context,10));
        canvas.drawArc(rectF,-90,270,false,mPaint);


        mPaint.setStrokeWidth(0);
        mPaint.setTextSize(mTextSize);
        Rect rect=new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        float startX = - rect.width() / 2;
        int startY =  - fm.descent + (fm.bottom - fm.top) / 2;
        canvas.drawText(text, startX, startY, mPaint);


    }



}
