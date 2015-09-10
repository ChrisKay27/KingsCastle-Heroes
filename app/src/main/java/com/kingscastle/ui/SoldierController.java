package com.kingscastle.ui;

import android.graphics.Rect;

import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Input;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.ui.buttons.Zoomer;

/**
 * Created by Chris on 9/4/2015 for KingsCastle-Heroes
 */
public class SoldierController implements TouchEventAnalyzer, Paintable {
    private static final String TAG = SoldierController.class.getSimpleName() ;

    private final UI ui;
    private final AbilityCaster ac;
    private final ThumbStick leftThumbStick;



    public SoldierController(final UI ui, AbilityCaster ac) {
        this.ui = ui;
        this.ac = ac;
        float dp = Rpg.getDp();

        /*
          Hack to move the thumb stick when the surface view is zoomed in on.
          Had to do this because creating a view with a touch listener for the thumb stick caused the
          touch listener on the surface view to not receive touch events
         */
        final vector boundsOffs = new vector();
        final int thumbStickWidth = (int) (150 * dp), thumbsStickHeight = (int) (150 * dp);

        final Rect bounds = new Rect(0, ui.getScreenHeight() - thumbStickWidth, thumbsStickHeight, ui.getScreenHeight());
        Zoomer.addZlcl(new Zoomer.onZoomLevelChangedListener() {
            @Override
            public void onZoomLevelChanged(float xScale, float yScale) {

                int left = (ui.getScreenWidthIgnoreZoom() - ui.getScreenWidth())/2;
                int top = (int) ((ui.getScreenHeightIgnoreZoom() - ui.getScreenHeight())/2 + ui.getScreenHeight() - thumbsStickHeight/yScale);
                bounds.left = left;
                bounds.top = top;
                bounds.right = (int) (left + thumbStickWidth/xScale);
                bounds.bottom = (int) (top + thumbsStickHeight/yScale);

                //boundsOffs.set(left, top);
            }
        });

        leftThumbStick = new ThumbStick(bounds, boundsOffs);
        leftThumbStick.setTsl( new ThumbStick.ThumbStickListener() {
            @Override
            public void thumbStickPositionChanged(vector position) {
                //Log.d( TAG , "left thumbStickPositionChanged: " + position);
                ui.selectedThings.getSelectedHumanoid().moveInDirection(position);
            }
            @Override
            public void thumbLeftThumbStick() {
                ui.selectedThings.getSelectedHumanoid().stopMovingInDirection();
            }
        });


    }

    private final vector temp = new vector();



    @Override
    public boolean analyzeTouchEvent(Input.TouchEvent e) {
        Humanoid u = ui.getSelectedHumanoid();

        if( u != null ){

            boolean usedUpTouchEvent = leftThumbStick.analyzeTouchEvent(e);
            if( usedUpTouchEvent )
                return true;

            usedUpTouchEvent = ac.analyzeTouchEvent(e);
            if( usedUpTouchEvent )
                return true;

            ui.getCoordsMapToScreen(u.loc, temp);
            temp.x = e.x - temp.x;
            temp.y = e.y - temp.y;
            u.attackOnceInDirection(temp);

            return true;
        }
        else
            return false;
    }

    @Override
    public void paint(Graphics g) {
        leftThumbStick.paint(g);
    }



}
