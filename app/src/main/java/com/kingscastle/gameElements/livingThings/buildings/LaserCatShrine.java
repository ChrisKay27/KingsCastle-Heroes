package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;



import com.kingscastle.heroes.R;
import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.spells.Laser;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class LaserCatShrine extends Shrine
{

	private static final String TAG = "Laser Cat Shrine";

	public static final Buildings name = Buildings.LaserCatShrine;

	private static final Image image     = Assets.loadImage(R.drawable.cat_statue);
	private static final Image deadImage = Assets.loadImage( R.drawable.small_rubble );

    private static final Image iconImage = null;//Assets.loadImage( R.drawable.cat_statue_icon );

	private static RectF staticPerceivedArea = Rpg.oneByOneArea; // this is only the offset from the mapLocation.

	
    private static final AttackerQualities staticAttackerQualities;
	
    private static final Attributes STATIC_ATTRIBUTES;

	private static final Cost costs = new Cost( 500 , 0 , 500 , 0 );
	
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
		staticAttackerQualities.setDamage( 14 );

		STATIC_ATTRIBUTES = new Attributes();
        STATIC_ATTRIBUTES.setRequiresAge(Age.IRON); STATIC_ATTRIBUTES.setRequiresTcLvl(14);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 ); // 2 );
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
		STATIC_ATTRIBUTES.setdHealthAge(200);
		STATIC_ATTRIBUTES.setdHealthLvl(40);
		STATIC_ATTRIBUTES.setdRegenRateAge( -100 );
		STATIC_ATTRIBUTES.setdRegenRateLvl(-20);

	}

	{
		setAQ( new AttackerQualities( staticAttackerQualities , getLQ().getBonuses() ) );

		Laser laser = new Laser();
		laser.setCaster( this );
		laser.addOffs( new vector( -Rpg.twoDp , -Rpg.eightDp ));
		laser.addOffs( new vector( Rpg.twoDp , -Rpg.eightDp ));
		getAQ().setCurrentAttack( new SpellAttack(getMM(),this , laser ) ) ;


	}

	public LaserCatShrine()
	{
		super(name);
	}

	public LaserCatShrine(  vector v , Teams t )
	{
		this();
		setTeam(t);
		setLoc(v);
	}

	@Override
	public boolean act()
	{
		////Log.d( TAG ,"pre act(), target == " + target );
		super.act();
		////Log.d( TAG ,"post act(), target == " + target );
		return isDead();
	}

	@Override
	protected void setupAttack() {

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
		return iconImage;
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
