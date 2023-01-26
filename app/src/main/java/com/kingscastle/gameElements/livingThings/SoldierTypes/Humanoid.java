package com.kingscastle.gameElements.livingThings.SoldierTypes;




import android.util.Log;

import com.kingscastle.Game;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Rpg;
import com.kingscastle.framework.WtfException;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.Animator;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.TargetingParams;
import com.kingscastle.gameElements.livingThings.WalkToLocationFinder;
import com.kingscastle.gameElements.livingThings.army.WhiteWizard;
import com.kingscastle.gameElements.livingThings.buildings.AttackingBuilding;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.orders.Order;
import com.kingscastle.gameElements.livingThings.orders.Stance;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.movement.Legs;
import com.kingscastle.gameElements.movement.pathing.Path;
import com.kingscastle.gameElements.movement.pathing.PathFinder;
import com.kingscastle.gameElements.movement.pathing.PathFinderParams;
import com.kingscastle.gameElements.movement.pathing.PathFoundListener;
import com.kingscastle.gameElements.targeting.TargetFinder;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Team;
import com.kingscastle.teams.Teams;
import com.kingscastle.teams.races.Races;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public abstract class Humanoid extends LivingThing {
    private static final String TAG = Humanoid.class.getSimpleName();

    protected final Legs legs = new Legs(this);

	protected long checkTargetsSituationAt;
	private int costsLives = 1;
	private int goldDropped = 2;


    private static final float nearDistanceSquared = 15000 * Rpg.getDpSquared();
    private static final float closeEnoughDist = 30*30* Rpg.getDpSquared();
    private static final float closeEnoughDistWithTarget = 60*60* Rpg.getDpSquared();


    private final List<Order> possibleOrders;

    {
        possibleOrders = new ArrayList<>();
        //possibleOrders.add( ChangeStance.getInstance() );
        //possibleOrders.add( StayNearHere.getInstance() );
    }
    @NotNull
    private Stance stance = Stance.FREE;

    private long checkDestAt;
    private long checkTargetDistAt;
    private long checkTargetDistAt2;
    private float nearDistSquared = nearDistanceSquared;

    private long checkStayHereDistAt;



	Humanoid(){
	}

	public Humanoid(Teams team) {
		super(team);
	}





	@Override
	public boolean act()
	{
		boolean superAct = super.act();

		if( isDead() )
			return true;

		LivingThing currTarget = getTarget();
		if( currTarget == null )
			findATarget();

		legsAct();
		armsAct();

		return superAct;
	}


    protected void legsAct()
    {

//		switch( stance )
//		{
//		default:
//		case PLAYING_THE_OBJECTIVE:
//		case FREE:
//
//			break;
//
//
//		case HOLD_POSITION:
//			if( highThreadTarget == null )
//			{
//				if( legs.getPathToFollow() != null )
//					legs.followPath();
//
//				else if( destination != null )
//				{
//					if( isAt( loc , destination ) )
//					{
//						loc.set( destination );
//						destinationReached();
//					}
//					else
//						legs.moveTowards( destination );
//				}
//				else if( casualDestination != null )
//				{
//					if( checkDestAt < GameTime.getTime() )
//					{
//						if( loc.distanceSquared( casualDestination ) < closeEnoughDist )
//							casualDestination = null;
//
//						else
//							legs.moveTowards( casualDestination );
//					}
//				}
//			}
//			return;
//
//		case GUARD_LOCATION:
//			if( stayHere == null )
//				break;
////			if( stayHere == null )
////				stayHere = new Vector( loc );
//
//			if( highThreadTarget == null )
//			{
//				if( checkStayHereDistAt < GameTime.getTime() )
//				{
//					if( loc.distanceSquared( stayHere ) > nearDistSquared )
//					{
//						////Log.d( TAG , "To far from stayHere, clearing target and setting dest=stayHere");
//						clearTarget();
//						////Log.d( TAG , "post clearTarget(), target is " + target );
//						walkTo( stayHere );
//					}
//					checkStayHereDistAt = GameTime.getTime() + 1000;
//				}
//			}
//			break;
//		}
        vector stayHere_local = getStayHere();
//		if (stayHere_local != null && loc.distanceSquared(stayHere_local) > closeEnoughDist) {
//			if( getTarget() == null ){
//				if( loc.distanceSquared(stayHere_local) > closeEnoughDist )
//					legs.moveTowards(stayHere_local);
//			}
//			else if (loc.distanceSquared(stayHere_local) > closeEnoughDistWithTarget){
//
//			}
//		}

//		else
        Path p = legs.getPathToFollow();
        if( p != null && p.isHumanOrdered() )
        {
            //Log.d( TAG , "destination = " + destination );
            //Log.d( TAG , "stayHere = " + stayHere );
            legs.followPath();
        }
        else if( team == Teams.BLUE && !getMoveInDirectionV().equals(0,0) ){
            legs.act(getMoveInDirectionV(), true);
        }
        else if( getTarget() != null || (this instanceof Healer && ((Healer)this).getHealingTarget() != null))
            legsActWithRespectToTarget();

        else if (checkStayHereDistAt < GameTime.getTime()) {
            if (stayHere_local != null && loc.distanceSquared(stayHere_local) > closeEnoughDist) {
                PathFinderParams pfp = new PathFinderParams();
                pfp.fromHere = new vector(loc);
                pfp.toHere = new vector(stayHere_local);
                pfp.mapWidthInPx = getMM().getLevel().getLevelWidthInPx();
                pfp.mapHeightInPx = getMM().getLevel().getLevelHeightInPx();
                pfp.grid = getMM().getGridUtil().getGrid();
                pfp.pathFoundListener = new PathFoundListener() {
                    @Override
                    public void onPathFound(Path path) {
                        setPathToFollow(path);
                    }
                    @Override
                    public void cannotPathToThatLocation(String reason) {
                    }
                };
                PathFinder.findPath(pfp);
                checkStayHereDistAt = GameTime.getTime() + 1000;
                //legs.moveTowards(stayHere_local);
            }
        }
//		else if( destination != null )
//		{
//			if( isAt( loc , destination ) )
//			{
//				loc.set( destination );
//				destinationReached();
//			}
//			else
//			{
//				legs.moveTowards( destination );
//				return;
//			}
//		}
//
//		if( !legsActWithRespectToTarget() )
//		{
//			if( casualDestination != null )
//			{
//				if( checkDestAt < GameTime.getTime() )
//				{
//					if( loc.distanceSquared( casualDestination ) < closeEnoughDist )
//						casualDestination = null;
//					else
//						legs.moveTowards( casualDestination );
//				}
//			}
//			else if( getTarget() == null )
//				wander();
//		}

    }

    boolean legsActWithRespectToTarget()
    {
        LivingThing currTarget = getTarget();
        if( currTarget != null )
        {
            if( checkTargetDistAt2 < GameTime.getTime() )
            {
                if( currTarget.area.contains( loc.x , loc.y ) )
                {
                    targetDistanceSquared = 1;
                    legs.moveAwayFrom( currTarget.loc );
                }
                else
                {
                    targetDistanceSquared = loc.distanceSquared( currTarget.area );
                    checkTargetDistAt2 = GameTime.getTime() + 200;
                }
            }

            if( targetDistanceSquared > getAQ().getAttackRangeSquared()  ){
                legs.moveTowards( currTarget.loc );
                return true;
            }
        }
        return false;
    }

    void clearTarget() {
        setTarget( null );
    }


    private boolean isAt(  vector loc,  vector dest )
    {

        if( checkDestAt < GameTime.getTime() )
        {
            if( this instanceof WhiteWizard)
                Log.e(TAG, "Checking distanceof to destination (" + loc.distanceSquared(dest) + ")");

            if( loc.distanceSquared( dest ) < Rpg.fiveDpSquared ){
                if( this instanceof WhiteWizard )
                    Log.e(TAG, " is at destination");
                return true;
            }

            checkDestAt = GameTime.getTime() + 200;
        }
        return false;
    }

    protected boolean armsAct()	{

        LivingThing currTarget = getTarget();
        if( currTarget != null )
        {
            switch( stance )
            {
                default:
                case PLAYING_THE_OBJECTIVE:
                case GUARD_LOCATION:
                case FREE: {
                    boolean attacked = false;

                    if( targetDistanceSquared == 0 )
                        attacked = getArms().act();
                    else
                        attacked = getArms().act( targetDistanceSquared );


                    if( checkTargetsSituationAt < GameTime.getTime() )
                    {
                        checkTargetsSituation( currTarget );
                        checkTargetsSituationAt = GameTime.getTime() + 300;
                    }


                    return attacked;
                }
                case HOLD_POSITION:
                    boolean attacked = getArms().act();

                    if( checkTargetsSituationAt < GameTime.getTime() )
                    {
                        checkTargetsSituation( currTarget );
                        checkTargetsSituationAt = GameTime.getTime() + 300;
                    }

                    return attacked;
            }
        }
        return false;
    }


    @Override
    public void setupTargetingParams()
    {
        if( targetingParams == null )
        {
            //targetingParams = TargetingParams.newInstance();
            targetingParams = new TargetingParams()
            {
                //Vector mLoc = new Vector();
                //Vector tLoc = new Vector();


                @Override
                public TargetFinder.CondRespon postRangeCheckCondition( LivingThing target) {


//					Vector stayHere = getStayHere();
//					if( stayHere != null && stayHere.distanceSquared(target.loc) > aq.getFocusRangeSquared() )
//						return CondRespon.FALSE;

                    return TargetFinder.CondRespon.TRUE;
//					try {
//						tLoc.set(target.loc);
//						mLoc.set(loc);
//
//
//						Vector closestTargetLoc = gUtil.getNearestWalkableLocNextToThis(target.area, tLoc);
//						Path p = PathFinder.heyINeedAPath( gUtil.getGrid(), mLoc, closestTargetLoc, 1000);
//						if( p != null ){
//							//startTargetingAgainAt = 0;
//							setPathToFollow(p);
//							return CondRespon.RETURN_THIS_NOW;
//						}
//						else{
//							//startTargetingAgainAt = GameTime.getTime() + 5000;
//							return CondRespon.FALSE;
//						}
//					} catch( Exception e ) {
//						//Log.e(TAG, "Could not find path to target.");
//						return CondRespon.FALSE;
//					}
                }
            };
            targetingParams.lookAtBuildingsFirst = true;
            targetingParams.soldierPriority = true;
            targetingParams.setTeamOfInterest(team);
            targetingParams.setWithinRangeSquared( getAQ().getFocusRangeSquared() );


        }

        switch( stance )
        {
            default:
                targetingParams.setWithinRangeSquared( getAQ().getFocusRangeSquared() );
                break;
            case HOLD_POSITION:
                targetingParams.setWithinRangeSquared( getAQ().getAttackRangeSquared() );
                break;
        }

        vector stayHere_local = getStayHere();
        if( team == Teams.BLUE && stayHere_local != null)
            targetingParams.setFromThisLoc( stayHere_local );
        else
            targetingParams.setFromThisLoc( loc );
    }

//	protected boolean armsAct()
//	{
//		boolean attacked = false;
//		LivingThing currTarget = getTarget();
//		if( currTarget != null )
//		{
//			if( targetDistanceSquared == 0 )
//				attacked = getArms().act();
//			else
//				attacked = getArms().act( targetDistanceSquared );
//		}
//
//		if( checkTargetsSituationAt < GameTime.getTime() )
//		{
//			checkTargetsSituation( currTarget );
//			checkTargetsSituationAt = GameTime.getTime() + 300;
//		}
//
//
//		return attacked;
//	}

	@Override
	public boolean create( MM mm) {
		legs.setMm(mm);
        if( team == Teams.RED )
            stance = Stance.FREE;
        else if( team == Teams.BLUE )
            stance = Stance.GUARD_LOCATION;
		return super.create(mm);
	}

	@Override
	public void die()
	{
		if ( !isDead() )
		{
			super.die();

			getAQ().removeAllAttackAnims();
		}
	}


	@Override
	public void setTarget( LivingThing nTarget) {
		super.setTarget(nTarget);
		unlockLookDirection();
	}


	@Override
	public void loadAnimation( @NotNull  MM mm )
	{
		Team team = mm.getTM().getTeam( getTeamName() );
		Races race = Races.HUMAN;
		if( team != null )
			race = team.getRace().getRace();

		////Log.d( TAG , "loadAnimation()" );
		if( aliveAnim == null )
		{
			////Log.d( TAG , "aliveAnim == null, creating new animator" );
			mm.getEm().add( aliveAnim = new Animator( this , getImages() ) , true );
			////Log.d( TAG , "after em.add( aliveAnim )");

			addHealthBarToAnim( race );

			////Log.d( TAG , "after addHealthBarToAnim()");
		}
		else if( aliveAnim.isOver() )
		{
			////Log.d( TAG , "aliveAnim != null");
			aliveAnim.setOver( false );

			healthBar = null;

			addHealthBarToAnim( race );
			////Log.d( TAG , "after addHealthBarToAnim()");
			mm.getEm().add( aliveAnim );
			////Log.d( TAG , "after em.add( aliveAnim )");
		}
	}



    //Methods used to communicate from the UI to a soldier.

    private final vector attackInDirection = new vector();
    private boolean onlyAttackInDirectionOnce = false;

    /** @param direction non-Normalized vector */
    public void attackOnceInDirection(vector direction) {
        //since the UI thread will mess with this vector we must ensure its not being used by the Team thread while its being fucked with
        synchronized (attackInDirection) {
            attackInDirection.set(direction).turnIntoHumanoidVector();
            onlyAttackInDirectionOnce = true;
        }
    }
    /** @param direction non-Normalized vector */
    public void attackInDirection(@NotNull vector direction) {
        //since the UI thread will mess with this vector we must ensure its not being used by the Team thread while its being fucked with
        synchronized (attackInDirection) {
            attackInDirection.set(direction).turnIntoHumanoidVector();
        }
    }
    public void stopAttackingInDirection() {
        //since the UI thread will mess with this vector we must ensure its not being used by the Team thread while its being fucked with
        synchronized (attackInDirection) {
            attackInDirection.set(0, 0);
        }
    }

    @NotNull
    public vector getAttackInDirectionVector(vector atkInDirVector) {
        //since the UI thread will mess with this vector we must ensure its not being used by the Team thread while its being fucked with
        synchronized (attackInDirection) {
            return atkInDirVector.set(attackInDirection);
        }
    }

    public boolean onlyAttackOnceInDirection() {
        return onlyAttackInDirectionOnce;
    }
    public void setOnlyAttackOnceInDirection(boolean b) {
        onlyAttackInDirectionOnce = b;
    }




//****************** Movement Methods *******************//

	private boolean movingIntoFormation;
	private boolean inFormation;

    protected vector destination;

    private vector stayHere;

    private vector holdThisPosition;

	protected Rpg.Direction lookDir = Rpg.Direction.S;

    private vector lookDirLockedInDirection;
	private boolean lookDirLocked;


	public boolean walkTo(  vector walkTo )
	{
		if( walkTo == null ) {
			destination = null;
			return true;
		}

		vector v = WalkToLocationFinder.walkTo(walkTo, cd);
		if( v != null )
			return walkToAlreadyCheckedPlaceable( v );
		else
			return walkToAlreadyCheckedPlaceable( walkTo );
	}

    private final vector moveInDirectionV = new vector();
    public void moveInDirection(vector inDirection) {
        //Log.d(TAG,"moveInDirection: "+ inDirection);
        moveInDirectionV.set(inDirection);
    }
    public void stopMovingInDirection(){
        //Log.d(TAG,"stopMovingInDirection");
        moveInDirectionV.set(0,0);
    }
    public vector getMoveInDirectionV() {
        return moveInDirectionV;
    }

    protected boolean walkToAlreadyCheckedPlaceable(vector walkTo)	{
		destination = walkTo;
		return true;
	}


	public boolean walkToAndStayHere(  vector walkTo )	{
		if( walkTo == null )
		{
			destination = null;
			stayHere = null;
			return true;
		}
		vector v = WalkToLocationFinder.walkTo( walkTo , cd );
		if( v != null )
			return walkToAndStayHereAlreadyCheckedPlaceable(v);
		else
			return walkToAndStayHereAlreadyCheckedPlaceable(walkTo);

	}

	public boolean walkToAndStayHereAlreadyCheckedPlaceable(vector walkTo)	{
		destination = walkTo;
		stayHere = walkTo;
		return true;
	}

	public void walkToAndStayHere( vector unitsDest , boolean movingIntoFormation )	{
		if( walkToAndStayHere(unitsDest) )
			this.movingIntoFormation = movingIntoFormation;
	}
	public void walkToAndStayHereAlreadyCheckedPlacement(vector dest, boolean movingIntoFormation)	{
		this.movingIntoFormation = movingIntoFormation;
		walkToAndStayHereAlreadyCheckedPlaceable(dest);
	}

	protected vector casualDestination;

	public void setCasualDestination( vector nearTc )	{
		casualDestination = nearTc;
	}

	public void pathDestinationReached(){
		//Log.v(TAG, this + " pathDestinationReached");
		destinationReached();
		legs.setPathToFollow(null);
		synchronized (pdrls){
			for( OnPathDestinationReachedListener pdrl : pdrls ){
				pdrl.onPathDestinationReached();
			}
			pdrls.clear();
		}
	}

	public void destinationReached(){
		//Log.v(TAG,this+" destinationReached");
		destination = null;
		synchronized (drls){
			for( OnDestinationReachedListener drl : drls ){
				drl.onDestinationReached();
			}
			drls.clear();
		}
	}


    public Legs getLegs() {
		return legs;
	}


	public Path getPathToFollow(){		return legs.getPathToFollow();	}
	public void setPathToFollow(  Path pathToFollow ){
		//Log.d(TAG,this+" setting path to " + pathToFollow);
		//destination = null;
		//stayHere = null;
		legs.setPathToFollow( pathToFollow );
	}

	@Override
	public boolean isWalking(){
		return legs.areYouStillWalking();
	}

	@Override
	public vector getVelocity() {
		return legs.getVelocity();
	}




	public vector getDestination(){
		return destination;
	}
	public void setDestination( vector bestCerealEver){
		destination = bestCerealEver;
	}



	public vector getStayHere() {
		return stayHere;
	}
	public void setStayHere(  vector v ){
		stayHere = v;
	}




	public vector getHoldThisPosition() {
		return holdThisPosition;
	}
	public void setHoldThisPosition(  vector holdThisPosition ) {
		this.holdThisPosition = holdThisPosition;
	}



	public boolean isMovingIntoFormation() {
		return movingIntoFormation;
	}
	public void setMovingIntoFormation(boolean b){
		movingIntoFormation = b;
	}



	public boolean isInFormation() {
		return inFormation;
	}
	protected void setInFormation(boolean inFormation) {
		this.inFormation = inFormation;
	}




	public Rpg.Direction getLookDirection() {	return lookDir;	}
	public void setLookDirection(Rpg.Direction inDirection){
		if(!isLookDirectionLocked())
			lookDir = inDirection;
	}
	public void setLookDirectionFromHumanoid( vector unitVectorInDirection) {
		if(!isLookDirectionLocked()) {
			Rpg.Direction d = vector.getDirection4(unitVectorInDirection);
			setLookDirection(d);
		}
	}
	public void lockLookDirectionFromHumanoidVector( vector unitVectorInDirection) {
		if(unitVectorInDirection!=null) {
			setLookDirectionFromHumanoid(unitVectorInDirection);
			lookDirLocked=true;
		}
		else {
			lookDirLocked=false;
		}
		lookDirLockedInDirection = unitVectorInDirection;
	}

	public void lockLookDirection()
	{
		lookDirLocked=true;
	}
	public void unlockLookDirection()
	{
		lookDirLocked=false;
	}
	public boolean isLookDirectionLocked()
	{
		return lookDirLocked;
	}

    public vector getLookDirLockedInDirection() {	return lookDirLockedInDirection;	}




	//Path Destination Reached
	private final List<OnPathDestinationReachedListener> pdrls = new ArrayList<>();




    public interface OnPathDestinationReachedListener{
		void onPathDestinationReached();
	}

	public void addPDRL(OnPathDestinationReachedListener pdrl)		   		{	synchronized(pdrls){	pdrls.add(pdrl);				}  	}
	public boolean removeRDRL(OnPathDestinationReachedListener pdrl)		{	synchronized(pdrls){	return pdrls.remove( pdrl );		}	}


	//Destination Reached
	private final List<OnDestinationReachedListener> drls = new ArrayList<>();

	public interface OnDestinationReachedListener{
		void onDestinationReached();
	}

	public void addDRL(OnDestinationReachedListener drl)		   		{	synchronized(drls){	drls.add(drl);				}  	}
	public boolean removeDRL(OnDestinationReachedListener drl)		{	synchronized(drls){	return drls.remove( drl );		}	}

	//****************** End Movement Methods *******************//




	@Override
	protected void checkBeingStupid()
	{
		if ( checkedBeingStupidAt < GameTime.getTime() )
		{
			checkedBeingStupidAt = GameTime.getTime() + 2000;
			if( destination != null && !movingIntoFormation )
			{
				GameElement ge = cd.checkPlaceableOrTarget( destination );
				//Log.d( TAG , "checkBeingStupid(), found a " + ge + " at destination");
				if( ge != null && ge != this )//&& !(ge instanceof Farm) && !( ge instanceof PendingBuilding && ((PendingBuilding)ge).getBuildingToBuild() instanceof Farm ) )
					walkTo( destination );
			}

			final LivingThing highThreadTarget_local = this.highThreadTarget;
			if( highThreadTarget_local != null )
			{
				if( highThreadTarget_local.isDead() )
					this.highThreadTarget = null;
				else
				{
					if( target != highThreadTarget_local )
					{
						PathFinder.findPath(gUtil.getGrid(), loc, highThreadTarget_local.loc, new PathFoundListener() {
							@Override
							public void onPathFound(Path path) {
								setTarget(highThreadTarget_local);
								setPathToFollow(path);
							}

							@Override
							public void cannotPathToThatLocation(String reason) {
								Humanoid.this.highThreadTarget = null;
							}
						}, getMM().getLevel().getLevelWidthInPx(), getMM().getLevel().getLevelHeightInPx());
						return;
					}
				}
			}

			final LivingThing lastHurter_local = this.lastHurter;
			if( lastHurter_local != null )
			{
				if( lastHurter_local.isDead() )
					this.lastHurter = null;
				else
				{
					if( target != null )
					{
						if( target == highThreadTarget_local )
							return;
						else if( target instanceof Building && !( target instanceof AttackingBuilding) )
						{
							if( !(lastHurter_local instanceof Building) ){
								PathFinder.findPath( gUtil.getGrid() , loc , lastHurter_local.loc , new PathFoundListener() {
									@Override
									public void onPathFound(Path path) {
										setTarget(lastHurter_local);
										setPathToFollow(path);
									}
									@Override
									public void cannotPathToThatLocation(String reason) {
										Humanoid.this.lastHurter = null;
									}
								}, getMM().getLevel().getLevelWidthInPx(), getMM().getLevel().getLevelHeightInPx());
							}
						}
						else
						{
							if( target instanceof RangedSoldier)
								if( !(lastHurter_local instanceof RangedSoldier) )
									setTarget(lastHurter_local);
						}

						if( isOutOfRangeOrDead( this , target ))
							setTarget(null);
					}
					else{
						if( !(lastHurter_local instanceof Building) )
							setTarget(lastHurter_local);
					}
				}
			}
		}
	}


	@Override
	public Anim getDyingAnimation(){
		return Assets.genericDyingAnimation;
	}


	public final int getGoldDropped(){		return goldDropped;	}
	public final void setGoldDropped(int goldDropped) {		this.goldDropped = goldDropped;	}

	public final void setCostsLives(int costsLives) {
		this.costsLives = costsLives;
	}
	public final int getCostsLives(){
		return costsLives;
	}




    @NotNull
    public Stance getStance() {
        return stance;
    }

    public void setStance(@NotNull Stance stance)  {this.stance = stance;    }



    @Override
    public List<Order> getPossibleOrders(){
        return possibleOrders;
    }


    public float getNearDistSquared() {
        return nearDistSquared;
    }
    public void setNearDistSquared(float nearDistSquared) {
        this.nearDistSquared = nearDistSquared;
    }





    public static LivingThing getHumanoidByName( String unitName,  LivingThing createdFrom,
                                                 Teams team)    {

        vector newLoc = new vector( createdFrom.loc );
        newLoc.translate(
                (float) (-createdFrom.getPerceivedArea().width() / 2 + Math
                        .random() * createdFrom.getPerceivedArea().width()),
                createdFrom.getPerceivedArea().height());

        return getFromString(unitName, team, newLoc);
    }



    private static final ArrayList<String> packagesToLookForHumanoids = new ArrayList<String>();
    static{
        addPackageToFindHumanoids("com.kingscastle.gameElements.livingThings.army.");
    }
    public static void addPackageToFindHumanoids(String pkg){
        synchronized(packagesToLookForHumanoids){
            packagesToLookForHumanoids.add(pkg);
        }
    }


    @NotNull
    public static Humanoid getFromString(@NotNull String unitName,@NotNull Teams team,@NotNull vector newLoc) {
        synchronized (packagesToLookForHumanoids) {
            for (String s : packagesToLookForHumanoids) {
                try {
                    Class<?> aHumanoid = Class.forName(s + unitName);

                    Humanoid unit;

                    Constructor<?>[] constrs = aHumanoid.getConstructors();

                    for (Constructor<?> c : constrs) {
                        try {
                            Class<?>[] params = c.getParameterTypes();
                            if (params.length == 2)
                                if (params[1] == vector.class && params[0] == Teams.class) {
                                    unit = (Humanoid) c.newInstance(newLoc, team);
                                    return unit;
                                }
                        } catch (Exception e) {
                            if (Game.testingVersion) {
                                e.printStackTrace();
                            }
                            //Log.v( TAG , "" );
                        }
                    }

                    //throw new WtfException("Cannot find unit:" + unitName + " team:" + team + " at " + newLoc);
                    unit = (Humanoid) aHumanoid.newInstance();
                    unit.setTeam(team);
                    unit.setLoc(newLoc);

                    return unit;
                } catch (NoClassDefFoundError e) {
                    if (Game.testingVersion) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    if (Game.testingVersion) {
                        e.printStackTrace();
                    }
                    //Log.v( TAG , "Did not find the Class in the livingThing.army. folder " );
                }
            }
        }
        throw new WtfException("Could not find class given!");
    }
//
//
//	protected void loadLegs()
//	{
//		if( legs == null )
//			legs = new Legs( this );
//	}


}
