package com.rt.sm.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.rt.sm.R;
import com.rt.sm.utils.CommonUtil;

/**
 * Created by gaodesong on 18/1/19.
 */


public class CircleTexView extends View{

    private Paint paint=null;

    private Paint mPaint=null;

    private Context context;

    private String text;

    private Rect rect=null;

    private int mTextSize;

    private int tColor,tbgColor;

    public CircleTexView(Context context) {
        this(context,null);
    }

    public CircleTexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleTexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RTextView);
        mTextSize = a.getDimensionPixelSize(R.styleable.RTextView_tSize, 20);
        tColor=a.getColor(R.styleable.RTextView_tColor,getResources().getColor(R.color.colorBg));
        tbgColor=a.getColor(R.styleable.RTextView_tbgColor,getResources().getColor(R.color.colorBg));
        text=a.getString(R.styleable.RTextView_tText);
        a.recycle();
        init(context);
    }


    public void init(Context context){
        this.context=context;

        rect=new Rect();
        paint=new Paint();
        paint.setColor(tColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setTextSize(mTextSize);
        paint.getTextBounds(text, 0, text.length(), rect);

        mPaint=new Paint();
        mPaint.setColor(tbgColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(text.equals("升") || text.equals("降")){
            LinearGradient lg=new LinearGradient(0,0,getWidth(),getHeight(),
                    getResources().getColor(R.color.adjustbg),
                    getResources().getColor(R.color.adjustbgdark),
                    Shader.TileMode.MIRROR);
            mPaint.setShader(lg);
        }
        canvas.drawCircle(getWidth()/2,getWidth()/2, getWidth()/2,mPaint);

        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        float startX = getWidth() / 2 - rect.width() / 2;
        int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;
        canvas.drawText(text, startX, startY, paint);



    }



}
