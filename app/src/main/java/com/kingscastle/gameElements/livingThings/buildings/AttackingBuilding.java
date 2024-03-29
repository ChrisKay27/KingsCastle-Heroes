package com.kingscastle.gameElements.livingThings.buildings;





import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.TargetingParams;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

public abstract class AttackingBuilding extends Building
{

    private LivingThing target;
	private vector atkInDirVector;

	protected AttackingBuilding(){}
	protected AttackingBuilding(Buildings buildingsName , Teams team )	{
		super( buildingsName , team );
	}

	@Override
	public boolean create( MM mm) {
		boolean superCreate = super.create(mm);
		setupAttack();
		return superCreate;
	}


	@Override
	public boolean act()
	{
		if( isDead() )
			return true;

		if ( getLQ().getHealth() < 1 ){
			die();
			return true;
		}

		if( !isStunned() ) {
			LivingThing currTarget = getTarget();
			if (currTarget == null)
				findATarget();
            else {
                armsAct();

                currTarget = getTarget();
                if (currTarget != null) {
                    if (isOutOfRangeOrDead(this, currTarget))
                        setTarget(null);
                }
            }
		}
		return isDead();
	}

	protected boolean armsAct()	{
		vector dir = atkInDirVector;
		if( dir != null )
			return getArms().actFromHumanoidVector(dir);
		return getArms().act();
	}

	@Override
	public void setTarget( LivingThing nTarget) {

		target = nTarget;

		synchronized (ltls){
			for(LivingThingListener tsl : ltls)
				tsl.onTargetSet(this, nTarget);
		}
	}


	@Override
	public LivingThing getTarget() {
		return target;
	}

	protected void setupTargetingParams()
	{
		if( targetingParams == null )
		{
			targetingParams = new TargetingParams();
			targetingParams.setTeamOfInterest(team);
			targetingParams.setWithinRangeSquared(getAQ().getFocusRangeSquared());
			targetingParams.setFromThisLoc(loc);
			targetingParams.lookAtBuildings = false;
		}
	}

	/** Override this if you need to */
	protected abstract void setupAttack();

	public void setAttackInDirectionVector(vector dir) {
		atkInDirVector = dir;
	}
}
