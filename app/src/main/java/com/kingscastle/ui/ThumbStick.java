package com.kingscastle.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Input.TouchEvent;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.heroes.R;
import com.kingscastle.ui.buttons.Zoomer;

/**
 * Created by Chris on 9/1/2015 for Heroes
 */
public class ThumbStick implements TouchEventAnalyzer {

    private static final String TAG = "ThumbStick";

    private static Image backgroundImg = Rpg.getGame().getGraphics().newImage(R.drawable.thumb_stick_background, Graphics.ImageFormat.RGB565);
    private static Image centerImg = Rpg.getGame().getGraphics().newImage(R.drawable.thumb_stick_center, Graphics.ImageFormat.RGB565);
    private static Image positionImg = Rpg.getGame().getGraphics().newImage(R.drawable.thumb_stick_position, Graphics.ImageFormat.RGB565);

    private final Rect bounds;
    private final vector boundsOffs;

    private ThumbStickListener tsl;
    private final vector position = new vector(), center = new vector();
    private int pointerID;

    private Paint paint = new Paint();
    {
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(4);
    }


    /**
     * @param bounds The bounds of the thumb stick. This is relative to the surface view so these bounds don't change with zoom on their own.
     * @param boundsOffs These offs are used to offset the bounds when the screen is zoomed in
     */
    public ThumbStick(Rect bounds, vector boundsOffs){
        this.bounds = bounds;
        this.boundsOffs = boundsOffs;
        tsl = null;
    }



    @Override
    public boolean analyzeTouchEvent(TouchEvent e) {
        float x = e.x;
        float y = e.y;

        if( x < boundsLeft() || x > boundsRight() || y < boundsTop() || y > boundsBottom() ) {
            Log.d(TAG, "e out of bounds returning false");
            tsl.thumbLeftThumbStick();
            pointerID = -1;

            return false;
        }

        if( e.pointer != pointerID && pointerID != -1 ) {
            Log.d(TAG, "Wrong pointer");
            return false;
        }
        pointerID = e.pointer;

        switch(e.type){
            case TouchEvent.TOUCH_UP:
                tsl.thumbLeftThumbStick();
                pointerID = -1;
                break;

            case TouchEvent.TOUCH_DRAGGED:
            case TouchEvent.TOUCH_DOWN:
                position.x = x - boundsCenterX();
                position.y = y - boundsCenterY();
                tsl.thumbStickPositionChanged(position);
                break;
            default:
                return false;
        }


        return true;
    }

    private final Rect dstCenterImg = new Rect(),dstPositionImg = new Rect();


    public void paint(Graphics g) {
       // g.drawRectBorder(bounds, Color.GREEN, 1);
//        dstCenterImg.set((int) boundsLeft(), (int) boundsTop(),
//                (int) (boundsLeft() + backgroundImg.getWidth()/Zoomer.getxScale()),(int) (boundsTop() + backgroundImg.getHeight()/Zoomer.getyScale()));
        float xScale = Zoomer.getxScale();
        float yScale = Zoomer.getyScale();

        dstCenterImg.set(0, 0, (int) (centerImg.getWidth() / xScale), (int) (centerImg.getHeight() / yScale));
        dstCenterImg.offsetTo(-dstCenterImg.width() / 2, -dstCenterImg.height() / 2);
        dstCenterImg.offset(boundsCenterX(), boundsCenterY());

        dstPositionImg.set(0, 0, (int) (positionImg.getWidth() / xScale), (int) (positionImg.getHeight() / yScale));
        dstPositionImg.offsetTo(-dstPositionImg.width() / 2, -dstPositionImg.height() / 2);
        dstPositionImg.offset(boundsCenterX(), boundsCenterY());


        g.drawImage(backgroundImg, backgroundImg.getSrcRect(), bounds);
        g.drawImage(centerImg, centerImg.getSrcRect(), dstCenterImg);
        g.drawImage(positionImg, positionImg.getSrcRect(), dstPositionImg);

    }


    private float boundsLeft(){
        return bounds.left + boundsOffs.x;
    }
    private float boundsTop(){
        return bounds.top - boundsOffs.y;
    }
    private float boundsRight(){
        return bounds.right + boundsOffs.x;
    }
    private float boundsBottom(){
        return bounds.bottom - boundsOffs.y;
    }
    private int boundsCenterX(){
        return (int) (bounds.centerX() + boundsOffs.x);
    }
    private int boundsCenterY(){
        return (int) (bounds.centerY() - boundsOffs.y);
    }






    public void setTsl(ThumbStickListener tsl) {
        this.tsl = tsl;
    }



    interface ThumbStickListener {
        void thumbStickPositionChanged(vector position);

        void thumbLeftThumbStick();
    }
}
