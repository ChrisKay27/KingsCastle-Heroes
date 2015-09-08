package com.kingscastle.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.heroes.R;

/**
 * Created by Chris on 9/1/2015 for Heroes
 */
public class ThumbStick2 extends View implements View.OnTouchListener {

    private static final String TAG = "ThumbStick";

    //private static Image backgroundImg = Rpg.getGame().getGraphics().newImage(R.drawable.thumb_stick_background, Graphics.ImageFormat.RGB565);
    private static Image centerImg = Rpg.getGame().getGraphics().newImage(R.drawable.thumb_stick_center, Graphics.ImageFormat.RGB565);
    private static Image positionImg = Rpg.getGame().getGraphics().newImage(R.drawable.thumb_stick_position, Graphics.ImageFormat.RGB565);

    //private final Rect bounds;
    private ThumbStickListener tsl;
    private final vector position = new vector(), center = new vector();
    private int pointerID;
    private Paint paint = new Paint();

    {
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(4);
    }



    public ThumbStick2(Context c, AttributeSet as){
        super(c,as);
       // bounds = new Rect();
        tsl = null;
        setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent e) {


        //e.getActionIndex()

        Log.d(TAG, "onTouch event:" + e);

        int pID = 0;
//
//        for (int index = 0; index < e.getPointerCount(); index++) {
//
            float x = e.getX();
            float y = e.getY();
//
//
//            if( e.getX() == e.getX(index) ) {
//                pID = index;
//                break;
//            }
//        }


        if( x < 0 || x > getWidth() || y < 0 || y > getHeight() ) {
            Log.d(TAG,"e out of bounds returning false");
            tsl.thumbLeftThumbStick();
            pointerID = -1;

            return false;
        }
//
//        pointerID = pID;



        switch(e.getActionMasked()){
            case MotionEvent.ACTION_UP:
                tsl.thumbLeftThumbStick();
                pointerID = -1;
                break;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                position.x = e.getX() - v.getWidth()/2;
                position.y = e.getY() - v.getHeight()/2;
                tsl.thumbStickPositionChanged(position);
                invalidate();
                break;
            default:
                Log.d(TAG,"returning false e.getActionMasked()=" + e.getActionMasked());
                return false;
        }

        Log.d(TAG,"returning true");
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.d(TAG, "onDraw ");

        if( !isInEditMode() ) {
           // canvas.drawBitmap(backgroundImg.getBitmap(), 0, 0, paint);
            canvas.drawBitmap(centerImg.getBitmap(), (getWidth() / 2) - centerImg.getWidthDiv2(), (getHeight() / 2) - centerImg.getHeightDiv2(), paint);
            canvas.drawBitmap(positionImg.getBitmap(),
                    (getWidth() / 2) + position.x - positionImg.getWidthDiv2(), (getHeight() / 2) + position.y - positionImg.getHeightDiv2(), paint);
        }
    }

    public void paint(Graphics g) {
       // g.drawRectBorder(bounds, Color.GREEN, 1);


        g.drawImage(centerImg, getLeft(), getTop(), paint);
        g.drawImage(positionImg, getLeft(), getTop(), paint);
        g.drawCircle(center.x+position.x, center.y+position.y, 30);
    }


    public void setTsl(ThumbStickListener tsl) {
        this.tsl = tsl;
    }


    interface ThumbStickListener {
        void thumbStickPositionChanged(vector position);

        void thumbLeftThumbStick();
    }
}
