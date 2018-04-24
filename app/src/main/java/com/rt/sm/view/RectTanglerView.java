package com.rt.sm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rt.sm.R;
import com.rt.sm.bean.ResultData;
import com.rt.sm.internet.RxSchedulers;
import com.rt.sm.utils.CommonUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by gaodesong on 18/1/22.
 */

public class RectTanglerView extends View{


    private int rwidth;

    private Context context;

    private Path path;

    private  int space=0;

    private int offset=0;

    private int rcolor;


    public RectTanglerView(Context context) {
        this(context,null);
    }

    public RectTanglerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RectTanglerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.RectTanglerView);
        offset=ta.getInt(R.styleable.RectTanglerView_offset, 5);
        rcolor = ta.getColor(R.styleable.RectTanglerView_rColor, Color.GREEN);
        ta.recycle();
        init();
    }

    private Paint paint=null;

    private void init(){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(CommonUtil.dip2px(context,1));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(rcolor);

        rwidth= CommonUtil.dip2px(context,15);
        path=new Path();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w=getWidth();
        int h=getHeight();

        space=(w-2*rwidth)/3;

        for(int i=0;i<w;i++){

            if(i==0){
                if(offset <= -space && offset>=-(space+rwidth)){
                    path.moveTo(0,10);
                }else {
                    path.moveTo(0,h);
                }

            }else if((i>0 && i<space+offset)
                    || (i>(rwidth+space)+offset && i<(rwidth+space*2)+offset)
                    || (i>2*rwidth+2*space+offset && i<2*rwidth+3*space+offset)
                    || (i>3*rwidth+3*space+offset && i<3*rwidth+4*space+offset)
                    || (i>4*rwidth+4*space+offset && i<4*rwidth+5*space+offset)){
                path.lineTo(i,h);
            }else if(i==space+offset
                    || i==rwidth+space*2+offset
                    || i==2*rwidth+3*space+offset
                    || i==3*rwidth+4*space+offset
                    || i==4*rwidth+5*space+offset){
                if(offset == -space){

                }else {
                    for(int j=h;j<10;j--){
                        path.lineTo(i,j);
                    }
                }


            }else if((i>space+offset && i<rwidth+space+offset)
                    || (i>rwidth+space*2+offset && i<2*rwidth+2*space+offset)
                    || (i>2*rwidth+3*space+offset && i<3*rwidth+3*space+offset)
                    || (i>3*rwidth+4*space+offset && i<4*rwidth+4*space+offset
                    || (i>4*rwidth+5*space+offset && i<5*rwidth+5*space+offset))){
                path.lineTo(i,10);
            }else if(i==rwidth+space+offset || i==2*(rwidth+space)+offset || i==3*rwidth+3*space+offset || i==4*rwidth+4*space+offset){
                for(int j=10;j<h;j++){
                    path.lineTo(i,j);
                }
            }
        }



        canvas.drawPath(path, paint);


    }

    public synchronized void setRectViewMove(){

        path.reset();
        offset-=3;
        if (offset<-(getWidth()/2-space/2)){
            offset=0;
        }
        postInvalidate();

//        Observable.create(new ObservableOnSubscribe<ResultData<String>>() {
//            @Override
//            public void subscribe(ObservableEmitter<ResultData<String>> resultData) throws Exception {
//
//            }
//        })
//                .compose(RxSchedulers.<ResultData<String>>compose())
//                .subscribe(new Consumer<ResultData<String>>() {
//                    @Override
//                    public void accept(ResultData<String> stringResultData) throws Exception {
//
//                    }
//
//                });

    }



}
