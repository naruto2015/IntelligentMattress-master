package com.rt.sm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.rt.sm.R;
import com.rt.sm.utils.CommonUtil;

/**
 * Created by gaodesong on 18/1/20.
 */

public class ProgressView extends View{


    private Paint paint,tPaint,hPaint,mPaint,whiteLine;

    private Context context;

    private String close,hot;

    /*
    字的宽度, 字与进度条的间隙
     */
    private float tvWidth,tvInterWidth;

    private Rect rect=null;

    float intervallineWitdh;

    private float circlePointPositionX;


    private int stalls=1;

    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }


    private void init() {

        close="关";
        hot="热";
        rect=new Rect();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(CommonUtil.dip2px(context,4));
       // paint.setColor(getResources().getColor(R.color.colorRed));
        paint.setTextSize(1);

        whiteLine = new Paint();
        whiteLine.setAntiAlias(true);
        whiteLine.setStrokeWidth(CommonUtil.dip2px(context,4));
        whiteLine.setTextSize(1);
        whiteLine.setColor(getResources().getColor(R.color.white));

        tPaint = new Paint();
        tPaint.setAntiAlias(true);
        tPaint.setStrokeWidth(1);
        tPaint.setColor(getResources().getColor(R.color.progressstart));
        tPaint.setTextSize(CommonUtil.dip2px(context,10));

        tvInterWidth=CommonUtil.dip2px(context,20);
        tPaint.getTextBounds(close, 0, close.length(), rect);

        hPaint = new Paint();
        hPaint.setAntiAlias(true);
        hPaint.setStrokeWidth(1);
        hPaint.setColor(getResources().getColor(R.color.progressend));
        hPaint.setTextSize(CommonUtil.dip2px(context,10));
        tPaint.getTextBounds(hot, 0, close.length(), rect);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(CommonUtil.dip2px(context,1));
        mPaint.setColor(getResources().getColor(R.color.circle));

        tvWidth=rect.width();
        circlePointPositionX=tvWidth+tvInterWidth;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //RectF rect=new RectF(0,0,getWidth(),getHeight());


        Paint.FontMetricsInt fm = tPaint.getFontMetricsInt();
        int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;
        canvas.drawText(close, 0, startY, tPaint);

        int colorStart = getResources().getColor(R.color.progressstart);
        int colorEnd = getResources().getColor(R.color.progressend);
        LinearGradient backGradient = new LinearGradient(0, 0, getWidth(), getHeight(), new int[]{colorStart ,colorEnd}, null, Shader.TileMode.CLAMP);
        paint.setShader(backGradient);

        float lineWitdh=getWidth()-2*(tvWidth+tvInterWidth);
        intervallineWitdh=lineWitdh/4;
        float whiteLineStart=tvInterWidth+tvWidth;
        canvas.drawLine(tvWidth+tvInterWidth,getHeight()/2,getWidth()-tvInterWidth-tvWidth,getHeight()/2,paint);

        for(int i=1;i<=3;i++){
            canvas.drawLine(i*intervallineWitdh+whiteLineStart,getHeight()/2,i*intervallineWitdh+whiteLineStart+CommonUtil.dip2px(context,2),getHeight()/2,whiteLine);

        }
        tPaint.setColor(getResources().getColor(R.color.progressend));
        canvas.drawText(hot, getWidth()-(tvWidth+tvInterWidth)+tvInterWidth, startY, tPaint);
         //canvas.drawText(hot, getWidth()-(tvWidth+tvInterWidth)+tvInterWidth, startY, hPaint);

        canvas.drawCircle(circlePointPositionX,getHeight()/2,CommonUtil.dip2px(context,10),mPaint);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                float startx=tvInterWidth+tvWidth;

                float x=event.getX();

                //第一个位置

                if(x<startx+intervallineWitdh/2){
                    circlePointPositionX=startx;
                    stalls=1;
                }else if(x>startx+intervallineWitdh/2 && x<startx+intervallineWitdh*3/2){
                    circlePointPositionX=startx+intervallineWitdh;
                    stalls=2;
                }else if(x>startx+intervallineWitdh*3/2 && x<startx+intervallineWitdh*5/2){
                    circlePointPositionX=startx+intervallineWitdh*2;
                    stalls=3;
                }else if(x>startx+intervallineWitdh*5/2 && x<startx+intervallineWitdh*7/2){
                    circlePointPositionX=startx+intervallineWitdh*3;
                    stalls=4;
                }else if(x>startx+intervallineWitdh*7/2 ){
                    circlePointPositionX=startx+intervallineWitdh*4;
                    stalls=5;

                }
                myStalls.setStalls(stalls);

                invalidate();
                break;

        }

        return super.onTouchEvent(event);
    }

    private MyStalls myStalls;

    public void setMyStalls(MyStalls myStalls) {
        this.myStalls = myStalls;
    }

    public interface MyStalls{

        void getStalls(int stalls);

        void setStalls(int stalls);
    }


}
