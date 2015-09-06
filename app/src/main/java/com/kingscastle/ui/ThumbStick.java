package com.kingscastle.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kingscastle.Game;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.heroes.R;

/**
 * Created by Chris on 9/1/2015 for Heroes
 */
public class ThumbStick extends View implements View.OnTouchListener {

    private static final String TAG = "ThumbStick";
    private static Image rangeCircle;

    //private final Rect bounds;
    private final ThumbStickListener tsl;
    private final vector position = new vector(), center = new vector();
    private int pointerID;
    private Paint paint = new Paint();

    public ThumbStick(Context c , AttributeSet as){
        super(c,as);
       // bounds = new Rect();
        tsl = null;
    }

    public ThumbStick(Game a, ThumbStickListener tsl ) {
        super(a.getActivity());
        //this.bounds = bounds;
        this.tsl = tsl;
        //center.set(bounds.centerX(), bounds.centerY());
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(2);

        if( rangeCircle == null )
            rangeCircle = a.getGraphics().newImage(R.drawable.range_circle, Graphics.ImageFormat.RGB565);
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        //Log.d(TAG, "analyzeTouchEvent " + e );
        switch(e.getAction()){
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                tsl.thumbLeftThumbStick();
                break;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                position.x = e.getX() - v.getWidth()/2;
                position.y = e.getY() - v.getHeight()/2;
                tsl.thumbStickPositionChanged(position);
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), paint);

        canvas.drawBitmap(rangeCircle.getBitmap(), getWidth() / 2, getHeight() / 2, paint);
        canvas.drawBitmap(rangeCircle.getBitmap(),getWidth()/2+position.x,getHeight()/2+position.y,paint);
    }

    public void paint(Graphics g) {
       // g.drawRectBorder(bounds, Color.GREEN, 1);

        g.drawCircle(center, 30);
        g.drawCircle(center.x+position.x, center.y+position.y, 30);
    }



    interface ThumbStickListener {
        void thumbStickPositionChanged(vector position);

        void thumbLeftThumbStick();
    }
}
