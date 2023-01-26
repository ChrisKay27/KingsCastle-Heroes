package com.kingscastle.ui;




import android.util.Log;

import com.kingscastle.framework.Input.TouchEvent;
import com.kingscastle.gameElements.livingThings.abilities.Ability;
import com.kingscastle.gameElements.livingThings.abilities.Buff;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.teams.Teams;

public class AbilityCaster implements TouchEventAnalyzer
{
    private static final String TAG = AbilityCaster.class.getSimpleName();
    private final MM mm;



    private Ability pendingAbility;


	AbilityCaster(MM mm){
        this.mm = mm;
    }



    @Override
    public boolean analyzeTouchEvent(TouchEvent e) {
        //Log.d(TAG, "analyzeTouchEvent pendingAbility=" + pendingAbility + " " + e);
        if( pendingAbility == null )
            return false;


        if ( pendingAbility.analyseTouchEvent( e ) )  {
            Log.d(TAG, pendingAbility + " used up touch event");
            pendingAbility = null;
            return true;
        }

        return false;
    }



	public void setPendingAbility(  Ability ab ) {
        if( ab instanceof Buff ){
            mm.add(ab.newInstance(ab.getCaster()));
        }
        else {
            pendingAbility = ab;
            ab.setTeam(Teams.BLUE);
        }
	}





	public boolean isThereAPendingAbility(){
		return pendingAbility != null;
	}


	public void cancel(){
		pendingAbility = null;
	}



    public Ability getPendingAbility(){
		return pendingAbility;
	}


	public void clearAbility(){
		pendingAbility = null;
	}

}
