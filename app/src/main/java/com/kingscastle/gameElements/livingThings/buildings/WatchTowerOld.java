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
import com.kingscastle.gameElements.livingThings.attacks.ProjectileAttack;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import java.util.ArrayList;

public class WatchTowerOld extends AttackingBuilding
{

	private static final String TAG = "Watch Tower";
	private static final String GUARD_TOWER = "Guard Tower";
	private static final String STONE_TOWER = "Stone Tower";

	public static final Buildings name = Buildings.WatchTower;

	private static RectF staticPerceivedArea;

	private static Image damagedImage , deadImage , iconImage;
	private static final Image watchTowerImage = Assets.loadImage(R.drawable.watch_tower);
	private static final Image guardTowerImage = Assets.loadImage(R.drawable.round_tower);
	private static final Image stoneTowerImage = Assets.loadImage(R.drawable.stone_tower);

	private static final Cost cost = new Cost( 10 , 0 , 0 , 0 );

	
    private static final AttackerQualities staticAttackerQualities;
	
    private static final Attributes STATIC_ATTRIBUTES;
	private static ArrayList<vector> staticDamageOffsets;


	
    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	
    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES;   }



	static
	{
		float dpSquared = Rpg.getDp()*Rpg.getDp();

		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(30000 * dpSquared);
		staticAttackerQualities.setAttackRangeSquared(30000 * dpSquared);
		staticAttackerQualities.setDamage(15);
		staticAttackerQualities.setROF(1000);

		STATIC_ATTRIBUTES = new Attributes();
        STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight(250);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth(250);
		STATIC_ATTRIBUTES.setHealth(250);
		STATIC_ATTRIBUTES.setFullMana(125);
		STATIC_ATTRIBUTES.setMana(125);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setArmor( 2 );  STATIC_ATTRIBUTES.setdArmorAge( 0 ); STATIC_ATTRIBUTES.setdArmorLvl( 2 );

		staticPerceivedArea = new RectF(Rpg.guardTowerArea);

		staticAttackerQualities.setdDamageAge(0);
		staticAttackerQualities.setdDamageLvl(4);
		staticAttackerQualities.setdROFAge(0);
		staticAttackerQualities.setdROFLvl(0);
		staticAttackerQualities.setdRangeSquaredAge(2000 * dpSquared);
		staticAttackerQualities.setdRangeSquaredLvl(80 * dpSquared);
		STATIC_ATTRIBUTES.setAge( Age.STONE );
		STATIC_ATTRIBUTES.setdHealthAge(0);
		STATIC_ATTRIBUTES.setdHealthLvl(50);
		STATIC_ATTRIBUTES.setdRegenRateAge( 0 );
		STATIC_ATTRIBUTES.setdRegenRateLvl( 0 );

	}


	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
	}



	public WatchTowerOld()
	{
		super( name , null );
		loadPerceivedArea();
	}

	public WatchTowerOld( vector v, Teams t)
	{
		super( name , t );
		setLoc(v);
		setTeam(t);
	}



	@Override
	public void upgrade(){
		super.upgrade();
		adjustAnimForLevel( attributes.getLevel());
	}

	protected void setupAttack() {
		getAQ().setCurrentAttack(new ProjectileAttack(getMM(), this, new Arrow())) ;
	}

	@Override
	protected void addAnimationToEm( Anim a, boolean sorted,  EffectsManager em)
	{
		backing.setSize(Backing.TINY);
		super.addAnimationToEm(a, sorted, em);
		//		em.add( a , true);
		//
		//		em.add( backing, EffectsManager.Position.Behind );
	}

	void loadDamageOffsets()
	{
		float dp = Rpg.getDp();

		staticDamageOffsets = new ArrayList<vector>();
		staticDamageOffsets.add( new vector( Math.random()*-5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*-5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp , -15*dp + Math.random()*30*dp ) );
	}

	@Override
	public ArrayList<vector> getDamageOffsets()
	{
		if( staticDamageOffsets == null )
		{
			loadDamageOffsets();
		}
		return staticDamageOffsets;
	}



	protected void adjustAnimForLevel( int lvl ){
		BuildingAnim bAnim = getBuildingAnim();
		if( bAnim != null )
			bAnim.setImage(watchTowerImage);
//		BuildingAnim bAnim = getBuildingAnim();
//		if( bAnim != null ){
//			if( lvl < 7 )
//				bAnim.setImage(watchTowerImage);
//			else if( lvl < 14 )
//				bAnim.setImage(guardTowerImage);
//			else if( lvl >= 14 )
//				bAnim.setImage(stoneTowerImage);
//
//			//			float scale = lvl%7;
//			//			//bAnim.setScale(1.1f);
//			//			bAnim.setScale(1f+((scale-1)/10f));
//		}
//		backing.setSize(Backing.MEDIUM);
	}





	@Override
	public Image getImage() {
		loadImages();
		int lvl = attributes.getLevel();

		if( lvl < 7 )
			return watchTowerImage;
		else if( lvl < 14 )
			return guardTowerImage;
		else if( lvl >= 14 )
			return stoneTowerImage;

		return watchTowerImage;
	}
	@Override
	public Image getDamagedImage() {
		loadImages();
		int lvl = attributes.getLevel();

		if( lvl < 7 )
			return watchTowerImage;
		else if( lvl < 14 )
			return guardTowerImage;
		else if( lvl >= 14 )
			return stoneTowerImage;

		return watchTowerImage;
	}

	@Override
	public Image getDeadImage() {
		loadImages();
		return deadImage;
	}
	@Override
	public Image getIconImage() {
		loadImages();
		return iconImage;
	}
	@Override
	public void loadImages()
	{
		damagedImage = watchTowerImage;
		deadImage = Assets.smallDestroyedBuilding;
		iconImage = watchTowerImage;
	}







	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		loadPerceivedArea();
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
	public String toString()
	{
		return TAG;
	}

	
    @Override
	public String getName()
	{
		BuildingAnim bAnim = getBuildingAnim();
		if( bAnim != null ){
			if( bAnim.getImage() == guardTowerImage )
				return GUARD_TOWER;
			else if( bAnim.getImage() == stoneTowerImage )
				return STONE_TOWER;
		}


		return TAG;
	}





	
    @Override
	public Cost getCosts() {
		return cost;
	}




	
    @Override
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}




}
