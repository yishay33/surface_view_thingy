package com.example.surfaceviewthingy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawSurfaceView extends SurfaceView implements Runnable{

    Context context;
    SurfaceHolder holder;
    boolean threadRunning = true;
    boolean isRunning = true;

    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.peak);



    float cordX = 100;
    float cordY = 100;
    float deltaX = 10;
    float deltaY = 10;



    public DrawSurfaceView(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();

        bitmap = resizeBitmap(bitmap,100,100);




    }


    @Override
    public void run() {
        while(threadRunning) {
            if (isRunning) {

                if (!holder.getSurface().isValid())
                    continue;
                Canvas c = null;

                try {
                    c = this.getHolder().lockCanvas();
                    synchronized (this.getHolder()) {
                        c.drawRGB(150, 255, 50);
                        c.drawBitmap(bitmap,cordX,cordY,null);

                        cordX += deltaX;
                        cordY += deltaY;
                        if(cordX < 0 || cordX > this.getWidth())
                            deltaX =  -deltaX;
                        if(cordY < 0 || cordY > this.getHeight())
                            deltaY =  -deltaY;



                    }
                }
                catch (Exception e)
                {

                }

                finally
                {
                    if (c != null)
                    {
                        this.getHolder().unlockCanvasAndPost(c);

                    }
                }

            }
        }
    }


    public void pause(){
        isRunning = false;
    }

    public void resume(){
        isRunning = true;
    }

    public void destroy(){
        isRunning = false;
        ((GameActivity)context).finish();
    }


    public int convertDpToPixels(int dp){
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public Bitmap resizeBitmap(Bitmap bitmap,int toWidth,int toHeight){
        return Bitmap.createScaledBitmap(bitmap, convertDpToPixels(toWidth),convertDpToPixels(toHeight),false);
    }

}