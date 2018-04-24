package com.rt.sm.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


import com.rt.sm.R;
import com.rt.sm.utils.CommonUtil;


/**
 * Created by gaodesong on 17/6/7.
 */

public class ChrysanthemumView extends View {

    private int width,height;

    private int paintWidth;

    private Paint paint,linePaint;

    private Context context;


    private int[] indicators={0xFFFFFFFF,0xFF686868};


    private int sweep=0;

    private ValueAnimator animator=null;


    public ChrysanthemumView(Context context) {
        this(context,null);
    }

    public ChrysanthemumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context=context;

        init();




    }


    private void init()
    {

        // 初始化默认参数
        width= CommonUtil.dip2px(context,60);
        height= CommonUtil.dip2px(context,60);

        paintWidth= CommonUtil.dip2px(context,1);

        paint=new Paint();
        paint.setStrokeWidth(paintWidth);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.black));

        linePaint=new Paint();
        linePaint.setStrokeWidth(2);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        // linePaint.setColor(getResources().getColor(R.color.white));
        intAnimation();



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF=new RectF(paintWidth,paintWidth,width-paintWidth,width-paintWidth);

        canvas.drawRoundRect(rectF,10,10,paint);

        canvas.translate(width/2,height/2);

        canvas.rotate(sweep);

        //canvas.drawLine(0,(width-paintWidth)/4,0,(width-paintWidth)/8,linePaint);

//        Shader shader=new SweepGradient(0,0,indicators,null);
//        linePaint.setShader(shader);

        for(int i=0;i<12;i++){

            //canvas.save();
            linePaint.setColor(calculateColor(i));
            canvas.drawLine(0,(width-paintWidth)/4,0,(width-paintWidth)/8,linePaint);
            canvas.rotate(30);
            //canvas.restore();

        }


    }

    public void intAnimation(){

        if(animator==null){
            animator= ValueAnimator.ofInt(360);
            animator.setDuration(90*200);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value= (int) animation.getAnimatedValue();
                    sweep=value;
                    postInvalidate();
                }
            });
            animator.start();
        }

    }

    public void start(){
        intAnimation();
    }

    public void stop(){

        if(animator!=null){
            animator.end();
            animator=null;
        }
    }


    private int calculateColor(int value){

        ArgbEvaluator evealuator = new ArgbEvaluator();
        float fraction = 0;
        int color = 0;
        fraction = (float)value/12;
        color = (int) evealuator.evaluate(fraction,0xFFFFFFFF,0xFF686868);
        return color;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width,height);
    }





}
