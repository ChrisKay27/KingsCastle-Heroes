package com.kingscastle.gameElements.livingThings.buildings;


import android.support.annotation.Nullable;

import com.kingscastle.gameElements.livingThings.LivingThing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildableSoldiers
{

	private List<LivingThing> buildableHumanoids;
	
	
	
	public BuildableSoldiers(@Nullable LivingThing... units)
	{
		if( units == null || units.length == 0 ){
        }
		else
		{		
			buildableHumanoids = new ArrayList<>();
            Collections.addAll(buildableHumanoids, units);
		}
	}



	public List<LivingThing> getBuildableSoldiers() {
		return buildableHumanoids;
	}



	public void setBuildableSoldiers(List<LivingThing> buildableHumanoids) {
		this.buildableHumanoids = buildableHumanoids;
	}
	
	
	

	
}
