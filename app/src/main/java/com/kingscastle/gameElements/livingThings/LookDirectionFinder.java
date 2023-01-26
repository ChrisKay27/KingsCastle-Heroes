package com.kingscastle.gameElements.livingThings;




import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

public class LookDirectionFinder
{

	public static Rpg.Direction getDir(  vector from ,  vector towards )
	{		
		return vector.getDirection4(new vector(from, towards).turnIntoHumanoidVector());
	}

}
