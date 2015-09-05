package com.kingscastle.gameElements.livingThings.SoldierTypes;


import com.kingscastle.teams.Teams;

public abstract class AdvancedMageSoldier extends MageSoldier
{
	public AdvancedMageSoldier() {
		super();
	}
	public AdvancedMageSoldier(Teams team) {
		super(team);
	}

	{
		buildTime = 5*60*1000;
	}

}
