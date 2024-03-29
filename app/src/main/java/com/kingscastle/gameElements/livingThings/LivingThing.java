package com.kingscastle.gameElements.livingThings;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;



import com.kingscastle.effects.Palette;
import com.kingscastle.effects.SpecialEffects;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Bar;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.SoldierTypes.RangedSoldier;
import com.kingscastle.gameElements.livingThings.abilities.Ability;
import com.kingscastle.gameElements.livingThings.abilities.ActiveAbilities;
import com.kingscastle.gameElements.livingThings.attacks.Arms;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.buildings.AttackingBuilding;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.orders.Order;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.targeting.TargetFinder;
import com.kingscastle.gameUtils.LevelUpChecker;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.level.GridUtil;
import com.kingscastle.teams.Teams;
import com.kingscastle.teams.races.Races;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class LivingThing extends GameElement
{
	private static final String TAG = "LivingThing";
    private final List<Runnable> runnables = new ArrayList<>();


    public enum DamageTypes{
		Exploding, Normal, Burning, Ice, Lightning
	}

    protected Teams team;


	public final Attributes attributes;
    public AttackerQualities aq = new AttackerQualities();

    protected final Arms arms = new Arms( this );
    private final List<Ability> abilities = new ArrayList<>();
    private final ActiveAbilities activeAbilities = new ActiveAbilities();

	protected boolean hasFinalInited = false;

    protected GridUtil gUtil;


	protected Animator aliveAnim;
	protected Anim dyingAnim;
	 protected Bar healthBar;



	protected long startTargetingAgainAt;
	 protected TargetingParams targetingParams;
	protected TargetFinder targetFinder;

	 protected LivingThing target;
	 protected LivingThing attacker;
	 protected LivingThing highThreadTarget;
	 protected LivingThing lastHurter;

	protected float targetDistanceSquared;


	  protected DamageTypes lastDamageType = DamageTypes.Normal;


	private final long spawnedAt = GameTime.getTime();
	private long nextRegen = spawnedAt;

	protected long stunnedUntil;

	private int selectedColor;

	protected long checkedBeingStupidAt;
	protected int waitLength = 50;
	protected int buildTime = 10000;



	protected LivingThing(Teams team){
		this.team = team;
		attributes = getNewLivingQualities();
	}

	protected LivingThing(){
		attributes = getNewLivingQualities();
	}

	//	private final void loadLQAndLegs()
	//	{
	//		//		if( attributes == null )
	//		//			setLQ( getNewLivingQualities() );
	//
	//		loadLegs();
	//	}


	@Override
	public boolean act()
	{
		if ( attributes.getHealth() <= 0 )
			die();
		else
		{
			if( !isStunned() ) {
				regen();

				checkBeingStupid();
			}
		}
		return isDead();
	}

    /**
     * Used to do work that must be done every game cycle.
     * @return true if this LivingThing must be removed from the Manager
     */
    public final boolean update(){
        activeAbilities.act();
        synchronized (runnables){
            for( Runnable r : runnables ){
                r.run();
            }
            runnables.clear();
        }
        return act();
    }


	@Override
	public void initialize(){
	}

	protected void setAttributes(){
	}

	@Override
	public boolean create( @NotNull MM mm )
	{
		super.create(mm);

		targetFinder = mm.getTargetFinder();
		gUtil = mm.getGridUtil();

        //Null asserts... lazy coder
		cd.toString();
		targetFinder.toString();
		gUtil.toString();

		if( created ){
			Animator anim = getAnim();
			if( anim != null )
				anim.setVisible( true );
			else
				finalInit( mm );

			return true;
		}
		////Log.d( TAG , "create()" );


		//finalInit(mm);
		////Log.d( TAG , "finalInit() completed" );
		updateArea();
		////Log.d( TAG , "updateArea() complete" );
		created = true;

		loadAnimation(mm);
		upgrade();

        int expGiven = (int) (attributes.getFullHealth()/5 + attributes.getHealAmount() + attributes.getSpeed()*5);
        if( getAQ() != null )
            expGiven += getAQ().getDamage()/2;
        attributes.setExpGiven(expGiven/4);


		if( aliveAnim != null )
			aliveAnim.setVisible(true);

		return true;
	}



	public void finalInit( MM mm)
	{
//
//		////Log.d( TAG , "finalInit()" );
//		loadAnimation(mm);
//		////Log.d( TAG , "done loadAnimation()" );
//		upgrade();
//
//		aliveAnim.setVisible(true);
	}



	@Override
	 public void loadAnimation( @NotNull MM mm ){
}

	protected void addHealthBarToAnim( Races race )
	{
		////Log.d( TAG , "addHealthBarToAnim()" );
		if( healthBar == null )
		{
			////Log.d( TAG , "healthBar == null" );
			if( getTeamName() == null )
				throw new IllegalStateException( " getTeamName() == null " );


			if( getTeamName() == Teams.BLUE )
			{
				healthBar = new Bar( getLQ().getHealthObj() , -8 * Rpg.getDp() , -12 * Rpg.getDp() );
				Paint nonDistOverPaint = Palette.getPaint(Align.CENTER, Color.RED, Rpg.getTextSize());
				healthBar.setBarPaint( nonDistOverPaint );
				healthBar.setTextPaint( nonDistOverPaint );
				healthBar.setShowCurrentAndMax( false );
			}
			else
			{
				float yOffs = 12 * Rpg.getDp();

				if( getImages() != null && getImages().length > 0 )
				{
					Image img = getImages()[0];
					if( img != null )
					{
						yOffs = img.getHeightDiv2() + Rpg.twoDp;
					}
				}

				healthBar = new Bar( getLQ().getHealthObj() , -8 * Rpg.getDp() , - yOffs );
				healthBar.setShowCurrentAndMax( false );
			}

			if( race == Races.UNDEAD )
				healthBar.setColor( Color.BLACK );

		}

		aliveAnim.add(healthBar, false);

	}



	public synchronized void upgradeToLevel( int lvl ){
		while( attributes.getLevel() < lvl )
			upgradeLevel();
	}

	public synchronized void upgradeLevel(){
		attributes.setLevel(attributes.getLevel() + 1);
		SpecialEffects.onCreatureLvledUp(loc.x, loc.y);
        upgrade();

        synchronized (ltls){
            for( LivingThingListener ltl : ltls )
                ltl.onLevelUp(this);
        }
	}


	protected void upgrade() {
		AttackerQualities saq = getStaticAQ();
		AttackerQualities aq = getAQ();

		Attributes slq = getStaticLQ();

		int lvl = attributes.getLevel()-1;


		if( aq != null && saq != null ){

			aq.setROF( saq.getROF() + aq.getdROFLvl()*lvl );
			aq.setDamage( saq.getDamage() + aq.getdDamageLvl()*lvl );
			aq.setAttackRange( saq.getAttackRange() + aq.getdRangeLvl()*lvl );
		}



		float healthPerc = attributes.getHealthPercent();

		attributes.setFullHealth(slq.getFullHealth() + attributes.getdHealthLvl() * lvl);
		attributes.setHealth((int) (healthPerc * attributes.getFullHealth()));


		attributes.setRegenRate(slq.getRegenRate() + attributes.getdRegenRateLvl() * lvl);

		attributes.setArmor(slq.getArmor() + attributes.getdArmorLvl() * lvl);

		attributes.setHealAmount(slq.getHealAmount() + attributes.getdHealLvl() * lvl);

        attributes.setSpeed(slq.getSpeed() + attributes.getdSpeedLevel()*lvl);
	}


	public boolean canLevelUp() {
		return attributes.getLevel() < attributes.getMaxLevel();

		/* @FIXME Not used in Tower Defense
		Team t = MM.get().getTeam(team);
		if( t != null )
			if( this instanceof Building)
				return t.canThisLevelUp( (Building) this );
			else{
				return false;
				String s = t.canThisLevelUp( this );
				String[] srhtybh = s.split(":");
				return srhtybh[0].equals("Yes");
			}

		else
			return false;*/
	}




    protected AttackerQualities getStaticAQ(){
		return null;
	}
	@NotNull
    protected abstract Attributes getStaticLQ();




	protected void checkBeingStupid()
	{
		if ( checkedBeingStupidAt < GameTime.getTime() )
		{
			checkedBeingStupidAt = GameTime.getTime() + 2000;

			final LivingThing highThreadTarget = this.highThreadTarget;
			if( highThreadTarget != null )
			{
				if( highThreadTarget.isDead() )
					this.highThreadTarget = null;
				else
					if( target != highThreadTarget )
					{
						target = highThreadTarget;
						return;
					}
			}

			final LivingThing lastHurter = this.lastHurter;
			if( lastHurter != null )
			{
				if( lastHurter.isDead() )
					this.lastHurter = null;
				else
				{
					if( target != null )
					{
						if( target == highThreadTarget )
							return;
						else if( target instanceof Building && !( target instanceof AttackingBuilding) )
						{
							if( !(lastHurter instanceof Building) )
								target = lastHurter;
						}
						else
						{
							if( target instanceof RangedSoldier)
								if( !(lastHurter instanceof RangedSoldier) )
									setTarget(lastHurter);
						}

						if( isOutOfRangeOrDead( this , target ))
							setTarget(null);
					}
					else{
						if( !(lastHurter instanceof Building) )
							setTarget(lastHurter);
					}
				}
			}
		}
	}



	protected void regen()
	{
		if ( nextRegen < GameTime.getTime())
		{
			if (attributes.getHealth() < attributes.getFullHealth())
				takeHealing( attributes.getHpRegenAmount() , false );

			//			if (livingQualities.getHealth() > livingQualities.getFullHealth())
			//			{
			//				livingQualities.setHealth( livingQualities.getFullHealth() );
			//			}
			if ( attributes.getMana() < attributes.getFullMana() )
				attributes.addMana( attributes.getMpRegenAmount() );

			if ( attributes.getMana() > attributes.getFullMana() )
				attributes.setMana( attributes.getFullMana() );

			nextRegen = GameTime.getTime() + (getTarget() == null && team != Teams.RED ? attributes.getRegenRate()/5 : attributes.getRegenRate()) ;
		}
	}





    public LivingThing getLastHurter(){
		return lastHurter;
	}


	public void setLastHurter( LivingThing lastHurter ){
		this.lastHurter = lastHurter;
	}



	public void takeDamage( int dam, LivingThing enemy )	{
		this.takeDamage( dam, enemy, DamageTypes.Normal );
	}

	//private final SparksAnim bloodSplatter = new SparksAnim( loc , Color.RED );

	public void takeDamage(int dam,  LivingThing enemy,  DamageTypes damageType)	{
		if( isDead() )
			return;

		float dam2 = dam;

		switch( damageType ){
			case Exploding:
				break;
			case Normal: dam2 -= Math.min( dam2/2 , dam2* attributes.getArmor()/100 );
				break;
			case Burning: dam2 -= attributes.getFireResistancePerc()*dam2;
				break;
			case Ice: dam2 -= attributes.getIceResistancePerc()*dam2;
				break;
			case Lightning: dam2 -= attributes.getLightningResistancePerc()*dam2;
				break;
		}

		int damage = (int) dam2;

		SpecialEffects.onCreatureDamaged( loc.x , loc.y, damage );

		getLQ().setHealth( getLQ().getHealth() - damage );

		if( enemy != null )
			lastHurter = enemy;

		lastDamageType = damageType;
	}



	@Override
	public void die()
	{
		if ( !isDead() )
		{
			createDyingAnimation();
			setDead(true);

			Animator anim = aliveAnim;
			if( anim != null )
				anim.setOver( true );


			activeAbilities.die();

			synchronized (ltls){
				for( LivingThingListener ltl : ltls ){
                    ltl.onDeath(this);
				}
			}
		}
	}


	protected void findATarget()
	{
		//long act = System.nanoTime();
		if( startTargetingAgainAt > GameTime.getTime() )
			return;


		if( target == null )
		{
			//			if( team == Teams.RED && this instanceof TownCenter )
			//				Log.v(getClass().getSimpleName(), "Finding a target");
			//			if ( squad != null )
			//			{
			//				target = getSquad().getATarget(this);
			//
			//				if( target != null )
			//				{
			//					//lastLookedForTarget = GameTime.getTime();
			//					return;
			//				}
			//				LivingThing lastHurter = this.lastHurter;
			//				if( lastHurter != null && !lastHurter.isDead()
			//						&& loc.distanceSquared( lastHurter.loc ) < getAQ().getFocusRangeSquared() )
			//				{
			//
			//					//lastLookedForTarget=GameTime.getTime();
			//					target = lastHurter;
			//					return;
			//				}
			//			}
			//If the target is still null find a target from the level.
			//			if( target == null )
			//			{

			setupTargetingParams();
			//long act = System.nanoTime();
			TargetFinder tf = this.targetFinder;
			if( tf != null )
				tf.findTargetAsync(targetingParams,this);
			//target = tf.findTarget( targetingParams );

			//			if( target != null ){
			//				long ms = (System.nanoTime()-act)/1000000;
			//				if( ms > 500 )
			//					Log.e(getClass().getSimpleName(), "Time to find a "+target+":" + ms + " millisec");
			//			}

			startTargetingAgainAt = GameTime.getTime() + 100;
		}
	}


	public Animator getAnim()
	{
		return aliveAnim;
	}

	protected void createDyingAnimation()	{
		getMM().getEm().add(dyingAnim = getDyingAnimation().newInstance(loc));
	}




	protected int getLevel()
	{
		return 0;
	}

	public int getDamage()
	{
		//LivingThing atker = this;
//		if( bonuses != null )
//			return (int) (aq.getDamage() * bonuses.getDamageBonus());
//		else
		return aq.getDamage();
	}

//
//
//	public void setTeam(Teams team)
//	{
//		this.team = team;
//		if( legs != null )
//			legs.getMovingTechnique().setTeam( team );
//	}




	public void useMana(int manaCost)
	{
		getLQ().setMana(getLQ().getMana() - manaCost);
	}

	public void respawn()
	{
		getLQ().setHealth(getLQ().getFullHealth());
	}

	@Override
	public Teams getTeamName() {
		return team;
	}
	public void setTeam(Teams team) {
		this.team = team;
	}

	public void setArea(RectF r) {
		area.set(r) ; //CHANGED jan 12
	}



	public vector getImageOffset() {
		return offset;
	}



	//******************************** Targeting Methods *****************************************//



	protected void setupTargetingParams()
	{
		if( targetingParams == null )
		{
			targetingParams = new TargetingParams(){

                @Override
				public TargetFinder.CondRespon postRangeCheckCondition( LivingThing target) {
					if( target.getAttacker() != null )
						return TargetFinder.CondRespon.FALSE;
					if( target.getTarget() != null && target.getTarget() != LivingThing.this )
						return TargetFinder.CondRespon.FALSE;

					return TargetFinder.CondRespon.TRUE;
				}
			};
			targetingParams.setTeamOfInterest(team);
			targetingParams.setWithinRangeSquared(getAQ().getFocusRangeSquared());
			targetingParams.setFromThisLoc(loc);
			targetingParams.lookAtBuildings = false;
		}
	}

	private boolean ignoreRange = false;

	protected void checkTargetsSituation( LivingThing target)
	{
		if( target == null )
			return;

		else if( target == highThreadTarget && !target.isDead() )
			return;

		else if( isOutOfRangeOrDead( this , target ) )
			setTarget( null );
	}


	protected boolean isOutOfRangeOrDead(LivingThing thing1, LivingThing thing_a)
	{
		return isOutOfRangeOrDead( thing1 , thing_a , ignoreRange );
	}


	private static boolean isOutOfRangeOrDead( LivingThing thing1,  LivingThing thing_a, boolean ignoreRange)
	{
		if( thing1 == null || thing_a == null || thing_a.isDead() )
			return true;

		if( ignoreRange )
			return false;

		if( thing1 == thing_a )
			return thing1.isDead();

		if( thing1.loc.distanceSquared( thing_a.loc ) > thing1.getAQ().getFocusRangeSquared() )
			return true;
		else
			return false;
	}


	public boolean getIgnoringRange() {
		return ignoreRange;
	}
	public void setIgnoreRange(boolean ignoreRange) {
		this.ignoreRange = ignoreRange;
	}


	public void setAttacker( LivingThing attacker) {
		this.attacker = attacker;
	}

    public LivingThing getAttacker() {
		return attacker;
	}


	public void setTarget( LivingThing nTarget) {
		//Log.d( TAG , "setTarget("+hit+")");

		if( nTarget instanceof Building )
			throw new IllegalStateException("Cannot target buildings in tower defence mode!");


		LivingThing oldTarget = target;
		//If we are clearing our target
		if( nTarget == null ) {
			//And we did have a target and it was being attacked by me then set his attacker to not be me
			if( oldTarget != null && oldTarget.getAttacker() == this )
				oldTarget.setAttacker(null);

		}
		else { //else set his attacker to be me
			if( nTarget.getTeamName() == team )
				return;
			nTarget.setAttacker(this);
		}
		target = nTarget;

		synchronized (ltls){
			for(LivingThingListener ltl : ltls)
				ltl.onTargetSet(this, nTarget);
		}
	}


	public LivingThing getTarget() {
		return target;
	}


	public void setHighThreadTarget(  LivingThing enemy )
	{
		highThreadTarget = enemy;
		target = highThreadTarget;
	}


	//******************************** End Targeting Methods *************************************//











	public boolean isWalking() {
		return false;
	}

	public vector getVelocity() {
		return null;
	}




	/**
	 * If the selectedColor is not yellow it WILL be drawn even if isSelected() is false.
	 * @param color
	 */
	public void setSelectedColor( int color )	{
		selectedColor = color;
	}

	/**
	 * if true, if getSquad()!=null the color will be set to red for the leader, and blue for a soldier. If false or getSquad()==null the color will be set to yellow.
	 * @param b
	 */
	@Override
	public void setSelected ( boolean b )	{
		selected = b;
		if( b )
		{
		}
		else
			setSelectedColor( Color.TRANSPARENT );
	}



	public int getSelectedColor()	{
		return selectedColor;
	}
	@Override
	public boolean isSelected()	{
		return selected;
	}



	public void showHealthPercentage()	{
		if( healthBar != null )
			healthBar.setShowCurrentAndMax( true );

	}

	public void hideHealthPercentage()	{
		if( healthBar != null )
			healthBar.setShowCurrentAndMax( false );

	}

	protected void takeHealing(int healAmount, boolean graphics)
	{
		if( getLQ().getHealth() == getLQ().getFullHealth() )
			return;


		int prevHealth = getLQ().getHealth();

		getLQ().addHealth( healAmount );

		int healedAmount = getLQ().getHealth() - prevHealth;


		if ( graphics &&  healedAmount != 0 )
		{
			SpecialEffects.onCreatureHealed( loc.x , loc.y, healAmount );
			////////Log.v(TAG, "///LivingThing.takeHealing()///");
			//	//////Log.v(TAG, "Creating Healing Text as healedAmount = " + healedAmount);
			//	ManagerManager.getInstance().getRtm()
			//.add( new HealingText( healedAmount + "", this ) );
		}

	}



	public void takeHealing( int healAmount ){
		takeHealing( healAmount , true );
	}



	public Bonuses getBonuses(){
		return attributes.getBonuses();
	}




	public List<Ability> getAbilities(){
		return abilities;
	}
    public void addAbility(@NotNull Ability a)	{
        abilities.add(a);
    }
    public void setAbilities(@NotNull List<Ability> castableSpells)	{
        abilities.clear();
        abilities.addAll(castableSpells);
	}




	/** 	WTF     */

    public LivingThing newInstance(vector at, Teams teams) {
		return null;
	}






	public boolean isWithinRange(  LivingThing lt )	{
		if(lt != null)
		{
			AttackerQualities aq = this.getAQ();
			return loc.distanceSquared(lt.loc) < aq.getFocusRangeSquared();
		}
		return false;
	}






    public Attributes getLQ()
	{
		return attributes;
	}
	public AttackerQualities getAQ() {
		return aq;
	}
	protected void setAQ(AttackerQualities attackerQualities) {
		aq = attackerQualities;
	}





    public Arms getArms() {
		return arms;
	}





	@Override
	public RectF getPerceivedArea(){
		return Rpg.getNormalPerceivedArea();
	}
	@Override
	public RectF getStaticPerceivedArea(){
		return Rpg.getNormalPerceivedArea();
	}






    public ActiveAbilities getActiveAbilities()
	{
		return activeAbilities;
	}






    public List<Order> getPossibleOrders(){
		return null;
	}




	private final Cost costs = new Cost( 0,0,0,0,0 );





    public Cost getCosts()	{
		return costs;
	}

	public void setCosts( Cost costs) {
		costs.set(costs);
	}





	public int getBuildTime(){
		return buildTime;
	}
    public void setBuildTime( int bt ){
		buildTime = bt;
	}




	public void forgetAboutTargetingUntil( long startTargetingAgainAt )	{
		this.startTargetingAgainAt = startTargetingAgainAt;
	}




    public Cost getLvlUpCost() {
		return getCosts();
		//if( getCosts() == null ){
			//Log.e( this.toString() , "getCosts() == null" );
		//	return null;
		//}

		//return null;//KcParams.getCostToLvlUp( this );
	}



	public void setStunnedUntil(long stunnedUntil) {
		this.stunnedUntil = stunnedUntil;
	}
	public boolean isStunned() {
		return stunnedUntil > GameTime.getTime();
	}






    protected abstract Anim getDyingAnimation();

    protected abstract Attributes getNewLivingQualities();



    public void addExp(int exp){
        attributes.addExp(exp);
        SpecialEffects.onExperienceGained(loc.getIntX(), loc.getIntY() , exp);
        if(LevelUpChecker.getLevelForExp(attributes.getExp()) > attributes.getLevel() ){
            upgradeLevel();
        }
    }


    public void doOnYourThread(Runnable r){
        synchronized (runnables){
            runnables.add(r);
        }
    }




    //************************************    Listeners  *****************************************//


    //LivingThing Listener
    protected final List<LivingThingListener> ltls = new LinkedList<>();

    public interface LivingThingListener{
        void onLevelUp(LivingThing lt);
        void onDeath(LivingThing lt);
        void onTargetSet(LivingThing forThis, LivingThing target);
    }

    public void addLTL(LivingThingListener ltl)		   		{	synchronized( ltls ){	ltls.add( ltl );				}  	}
    public boolean removeLTL(LivingThingListener ltl)		{	synchronized( ltls ){	return ltls.remove( ltl );		}	}


}
