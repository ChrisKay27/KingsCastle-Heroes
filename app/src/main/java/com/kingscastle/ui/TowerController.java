package com.kingscastle.ui;



import com.kingscastle.framework.Input.TouchEvent;
import com.kingscastle.gameElements.livingThings.buildings.AttackingBuilding;
import com.kingscastle.gameUtils.CoordConverter;
import com.kingscastle.gameUtils.vector;

/**
 * Created by Chris on 8/2/2015 for Tower Defence
 */
public class TowerController implements TouchEventAnalyzer{

    private static final String TAG = TowerController.class.getSimpleName();

    private final CoordConverter cc;
    private AttackingBuilding selectedBuilding;

    public TowerController(CoordConverter cc){
        this.cc = cc;
    }


    @Override
    public boolean analyzeTouchEvent( TouchEvent e) {
        AttackingBuilding sb = selectedBuilding;
        if( sb == null )
            return false;

        if( e.type == TouchEvent.TOUCH_UP ){
            sb.setAttackInDirectionVector(null);
            return false;
        }

        vector dir = new vector(e.x,e.y);
        cc.getCoordsScreenToMap(dir, dir);
        if( dir.distanceSquared(sb.loc) < sb.aq.getAttackRangeSquared() ) {
            dir.minus(sb.loc);
            dir.turnIntoHumanoidVector();
            sb.setAttackInDirectionVector(dir);
            return true;
        }
        else{
            sb.setAttackInDirectionVector(null);
        }

        return false;
    }


    public void setSelectedBuilding(AttackingBuilding sb) {
        AttackingBuilding sb_local = selectedBuilding;
        if( sb_local != null )
            sb_local.setAttackInDirectionVector(null);
        this.selectedBuilding = sb;
    }

}
