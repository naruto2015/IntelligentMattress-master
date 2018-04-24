package com.rt.sm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rt.sm.R;


/**
 * Created by Administrator on 2018/3/16.
 */

public class BodyMoveView extends View{



    private Paint paint;

    Path path;

    private int rectCount=10;

    private int rectWidth;

    private int intervalWidth;

    private RectF rectF=null;

    private int TranX=0;

    private int width=0;

    private int x1,x2;

    private int[] pointsHeight;

    private int height;

    public BodyMoveView(Context context) {
        this(context,null);
    }

    public BodyMoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public BodyMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint=new Paint();
        paint.setColor(getResources().getColor(R.color.actioncolor));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.FILL);
        //在view可视区内的点数
        path=new Path();
        rectWidth= 10;

        pointsHeight= new int[]{40, 10, 30, 20, 40, 50, 10, 10, 40, 10, 10};

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
        intervalWidth=(w-rectWidth*rectCount)/(rectCount+1);
        int init=height-5;
        //pointsHeight= new int[]{init, init, init, init, init, init, init, init, init, init, init};
    }


    public synchronized void setData(int lastPosition){

        if(lastPosition<50){
            lastPosition+=50;
        }

        if(lastPosition>100){
            lastPosition= (int) (Math.random()*50+50);
        }

        lastPosition=(height-10)*(1-lastPosition/100);
        if(lastPosition==0){
            lastPosition=height-10;
        }

        for(int i=0;i<pointsHeight.length-1;i++){
            pointsHeight[i]=pointsHeight[i+1];


        }
        pointsHeight[10]=lastPosition;

        postInvalidate();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //intervalWidth=(getWidth()-rectWidth*rectCount)/6;


        x1=intervalWidth+TranX;
        x2=x1+rectWidth;
        rectF=new RectF(x1,pointsHeight[0],x2,height-10);
        canvas.drawRoundRect(rectF,10,10,paint);

        for(int i=1;i<pointsHeight.length-1;i++){
            x1=x2+intervalWidth;
            x2=x1+rectWidth;
            rectF=new RectF(x1,pointsHeight[i],x2,height-10);
            canvas.drawRoundRect(rectF,10,10,paint);
        }


        /*
        x1=intervalWidth+TranX;
        x2=x1+rectWidth;
        rectF=new RectF(x1,10,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,20,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,30,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,15,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,10,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);



        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,10,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,20,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);



        //

        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,10,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,20,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,30,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,15,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,10,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);



        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,10,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,20,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);






//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);


        //canvas.translate(x,0);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                TranX-=10;
                if(TranX<=-width+intervalWidth){
                    TranX=0;
                }
                postInvalidate();
            }
        },200);



        */



    }









}
