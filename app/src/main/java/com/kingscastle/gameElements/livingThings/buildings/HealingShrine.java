package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;



import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.TargetingParams;
import com.kingscastle.gameElements.livingThings.abilities.HealingSpell;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.HealingAttack;
import com.kingscastle.gameElements.targeting.TargetFinder;
import com.kingscastle.gameElements.targeting.TargetFinder.CondRespon;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class HealingShrine extends Shrine
{
	private static final String TAG = "Healing Shrine";

	public static final Buildings name = Buildings.HealingShrine;

	private static final Image image = Assets.loadImage(R.drawable.cross_statue);
	private static final Image deadImage = Assets.loadImage( R.drawable.small_rubble );
	//private static final Image iconImage = Assets.loadImage( R.drawable.cross_statue_icon );

	private static RectF staticPerceivedArea = Rpg.oneByOneArea; // this is only the offset from the mapLocation.


    private static final AttackerQualities staticAttackerQualities;

    private static final Attributes STATIC_ATTRIBUTES;

	private static final Cost costs = new Cost( 300 , 0 , 300 , 300 , 0 );

    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }

    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES;   }


	static
	{

		float dpSquared = Rpg.getDpSquared();
		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared     ( 30000 * dpSquared );
		staticAttackerQualities.setAttackRangeSquared    ( 30000 * dpSquared );
		staticAttackerQualities.setROF( 1000 );

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresAge(Age.IRON); STATIC_ATTRIBUTES.setRequiresTcLvl(12);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 ); // 1 );
		STATIC_ATTRIBUTES.setFullHealth( 500 );
		STATIC_ATTRIBUTES.setHealth( 500 );
		STATIC_ATTRIBUTES.setFullMana( 100 );
		STATIC_ATTRIBUTES.setMana( 100 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 2 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );
		STATIC_ATTRIBUTES.setSpeed( 0 );

		staticAttackerQualities.setdDamageAge(4);
		staticAttackerQualities.setdDamageLvl(1);
		staticAttackerQualities.setdROFAge(-100);
		staticAttackerQualities.setdROFLvl(-20);
		staticAttackerQualities.setdRangeSquaredAge(-3000 * dpSquared);
		staticAttackerQualities.setdRangeSquaredLvl(-100 * dpSquared);
		STATIC_ATTRIBUTES.setAge( Age.STONE );

		STATIC_ATTRIBUTES.setdHealthAge(100);
		STATIC_ATTRIBUTES.setdHealthLvl(40);
		STATIC_ATTRIBUTES.setdRegenRateAge( -100 );
		STATIC_ATTRIBUTES.setdRegenRateLvl(-20);

	}

    private final HealingSpell hs;
	{
		setAQ( new AttackerQualities( staticAttackerQualities , getLQ().getBonuses() ) );
		hs = new HealingSpell(this,null);
		hs.setCaster( this );
		getAQ().setCurrentAttack( new HealingAttack(getMM(), this , hs ) );
	}

	public HealingShrine()
	{
		super(name);
	}

	public HealingShrine(  vector v, Teams t )
	{
		this();
		setTeam(t);
		setLoc(v);
	}

	@Override
	protected void setupAttack() {

	}
	private TargetingParams params;

	private void createTargetingParams()
	{
		if( params == null )
		{
			params = new TargetingParams()
			{

                @Override
				public CondRespon postRangeCheckCondition(  LivingThing target )
				{
					if( target.attributes.getHealth() == target.attributes.getFullHealth() )
						return CondRespon.FALSE;

					if( hs.canCastOn(target))
						return CondRespon.TRUE;

					return CondRespon.FALSE;
				}
			};
			params.setLookAtBuildings( false );
			params.setTeamOfInterest( getTeamName() );
			params.setOnThisTeam( true );
			params.setWithinRangeSquared( getAQ().getAttackRangeSquared() );
			params.setFromThisLoc ( loc );
		}
	}

	@Override
	public void findATarget()
	{
		if( startTargetingAgainAt < GameTime.getTime() )
		{
			createTargetingParams();

			TargetFinder tf = this.targetFinder;
			if( tf != null )
				setTarget(tf.findTarget( params ));

			startTargetingAgainAt = GameTime.getTime() + 2000;

			////Log.d( TAG ,"Looking for target, found a " + target );
		}
	}



	@Override
	public boolean isOutOfRangeOrDead( LivingThing thing1 , LivingThing thingA )
	{
		boolean outOfRange = isOutOfRangeOrDeadORFullHealth( thing1 , thingA );
		////Log.d( TAG ,"Target is out of range or dead or full health" );
		return outOfRange;


		//return isOutOfRangeOrDeadORFullHealth( thing1 , thingA );
	}

	private boolean isOutOfRangeOrDeadORFullHealth(  LivingThing healer ,
			 LivingThing healingTarget2 )
	{

		if( healer == null || healingTarget2 == null )
		{
			return true;
		}
		if( healer == healingTarget2 )
		{
			return healingTarget2.attributes.getHealth() == healingTarget2.attributes.getFullHealth();
		}
		if ( super.isOutOfRangeOrDead ( healer, healingTarget2 ))
		{
			return true;
		}
		else
		{
			if( healingTarget2.attributes.getHealth() == healingTarget2.attributes.getFullHealth())
			{
				return true;
			}
		}

		return false;
	}



	@Override
	protected void addAnimationToEm( Anim a, boolean sorted,  EffectsManager em)
	{
		em.add( a , true);
		backing.setSize(Backing.TINY);
		em.add( backing, EffectsManager.Position.Behind );
	}

	@Override
	public Image getImage() {
		return image;
	}
	@Override
	public Image getDamagedImage() {
		return image;
	}
	@Override
	public Image getDeadImage() {
		return deadImage;
	}
	@Override
	public Image getIconImage() {
		return image;
	}

	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		return staticPerceivedArea;
	}


	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2)
	{
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea()
	{
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea)
	{
		staticPerceivedArea = staticPercArea;
	}




    @Override
	public Cost getCosts(){
		return costs;
	}


    @Override
	public String getName() {
		return TAG;
	}



    @Override
	public Attributes getNewLivingQualities() {
		return new Attributes(STATIC_ATTRIBUTES);
	}



}
