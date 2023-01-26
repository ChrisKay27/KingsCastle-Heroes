package com.kingscastle.gameElements.movement.pathing;



import com.kingscastle.gameUtils.vector;

/**
 * Created by chris_000 on 7/5/2015.
 */
public class PathFinderParams {

    public Grid grid;
    public int mapWidthInPx,mapHeightInPx;
    public vector fromHere,toHere;

    
    public PathFoundListener pathFoundListener;
}
