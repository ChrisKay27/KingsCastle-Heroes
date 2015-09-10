package com.kingscastle.teams.races;


import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.SoldierType;
import com.kingscastle.gameElements.livingThings.buildings.BuildableSoldiers;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.gameUtils.Age;

public abstract class Race
{
	public abstract BuildableSoldiers getHumanoidsFor( Buildings building , Age age );

	public abstract SoldierType getMy( GeneralSoldierType advancedHealer ) ;

	public abstract LivingThing getMyVersionOfA( GeneralSoldierType basicMeleeSoldier );

	public abstract Building getMyVersionOfA( Buildings building );

	public abstract Races getRace();

	public abstract Buildings getMyRacesBuildingType(Buildings building);
}
