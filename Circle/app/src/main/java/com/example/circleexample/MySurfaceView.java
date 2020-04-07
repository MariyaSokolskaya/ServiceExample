package com.example.circleexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Thread t;
    boolean isRun = true;
    SurfaceHolder surfaceHolder;
    Paint paint;
    float r = 20;

    public MySurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(3);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                Canvas canvas;
                while (isRun) {
                    canvas = surfaceHolder.lockCanvas();
                    //synchronized (surfaceHolder){
                      //  canvas.drawCircle(200, 200, r, paint);
                    //}
                    if (canvas != null)
                        canvas.drawCircle(200, 200, r, paint);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    r += 5;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry){
            try {
                t.join();
                retry = false;
                isRun = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
