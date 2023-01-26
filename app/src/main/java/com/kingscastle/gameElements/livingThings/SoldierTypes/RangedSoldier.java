package com.kingscastle.gameElements.livingThings.SoldierTypes;




import com.kingscastle.gameElements.livingThings.attacks.Bow;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.movement.pathing.Path;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

public abstract class RangedSoldier extends Humanoid {

	public RangedSoldier() {
	}

	public RangedSoldier(Teams team) {
		super(team);
	}


	@Override
	protected boolean armsAct()
	{
		boolean armsActed = super.armsAct();
		if( armsActed ){
			Path path = getPathToFollow();
			if( path != null && !path.isHumanOrdered() ){
				setPathToFollow(null);
			}
		}
		return armsActed;
	}


	@Override
	public void loadAnimation(@NotNull  MM mm) {
		super.loadAnimation(mm);
		getAQ().setCurrentAttack( new Bow( mm, this , new Arrow() ) );
		aliveAnim.add(getAQ().getCurrentAttack().getAnimator(), true);
	}


}
