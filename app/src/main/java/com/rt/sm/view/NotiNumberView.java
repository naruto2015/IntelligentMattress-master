package com.rt.sm.view;

import android.content.Context;
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

public class NotiNumberView extends View{


    private Paint paint,tPaint;

    private String text="0";

    private Context context;

    public NotiNumberView(Context context) {
        this(context,null);
    }

    public NotiNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NotiNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }


    private void init() {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);
        paint.setColor(getResources().getColor(R.color.colorRed));
        paint.setTextSize(1);

        tPaint = new Paint();
        tPaint.setAntiAlias(true);
        tPaint.setStrokeWidth(1);
        tPaint.setColor(getResources().getColor(R.color.colorWhite));
        tPaint.setTextSize(CommonUtil.dip2px(context,10));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF mrectF=new RectF(0,0,getWidth(),getHeight());
        canvas.drawRoundRect(mrectF, CommonUtil.dip2px(context,10),CommonUtil.dip2px(context,10),paint);

        Rect mrect = new Rect();
        tPaint.getTextBounds(text, 0, text.length(), mrect);
        Paint.FontMetricsInt fm = tPaint.getFontMetricsInt();
        float startX = getWidth() / 2  - mrect.width() / 2;
        int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;
        canvas.drawText(text, startX, startY, tPaint);

    }




}
