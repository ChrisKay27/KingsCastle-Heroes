package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.ProjectileAttack;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import java.util.ArrayList;

public class WatchTower extends AttackingBuilding
{
	private static final String TAG = "Watch Tower";

	public static final Buildings name = Buildings.WatchTower;

	private static RectF staticPerceivedArea;

	private static Image damagedImage , deadImage , iconImage;
	private static final Image image = Assets.loadImage(R.drawable.watch_tower);

	private static final Cost cost = new Cost( 40 , 0 , 0 , 0 );

	@NonNull
    private static final AttackerQualities staticAttackerQualities;
	@NonNull
    private static final LivingQualities staticLivingQualities;
	private static ArrayList<vector> staticDamageOffsets;




	@NonNull
    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities;   }



	static
	{
		float dpSquared = Rpg.getDpSquared();

		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(25000 * dpSquared);
		staticAttackerQualities.setAttackRangeSquared(25000 * dpSquared);
		staticAttackerQualities.setDamage(8);
		staticAttackerQualities.setROF(900);

		staticLivingQualities = new LivingQualities();staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight(250);
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth(250);
		staticLivingQualities.setHealth(250);
		staticLivingQualities.setFullMana(125);
		staticLivingQualities.setMana(125);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);
		staticLivingQualities.setArmor( 2 );  staticLivingQualities.setdArmorAge( 0 ); staticLivingQualities.setdArmorLvl( 2 );

		staticPerceivedArea = new RectF(Rpg.guardTowerArea);


		staticAttackerQualities.setdDamageAge(0);
		staticAttackerQualities.setdDamageLvl(10);
		staticAttackerQualities.setdROFAge(0);
		staticAttackerQualities.setdROFLvl(-60);
		staticAttackerQualities.setdRangeSquaredAge(2000 * dpSquared);
		staticAttackerQualities.setdRangeSquaredLvl(80 * dpSquared);
		staticLivingQualities.setAge( Age.STONE );
		staticLivingQualities.setMaxLevel(4);
		staticLivingQualities.setdHealthAge(0);
		staticLivingQualities.setdHealthLvl(50);
		staticLivingQualities.setdRegenRateAge( 0 );
		staticLivingQualities.setdRegenRateLvl( 0 );
	}


	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
	}



	public WatchTower()
	{
		super(name, null);
		loadPerceivedArea();
	}

	public WatchTower(@NonNull vector v, Teams t)
	{
		super( name , t );
		setLoc(v);
		setTeam(t);
	}

	
	@Override
	public void upgrade(){
		super.upgrade();
		adjustAnimForLevel( lq.getLevel());
	}

	@Override
	protected void setupAttack() {
		getAQ().setCurrentAttack(new ProjectileAttack(getMM(), this, new Arrow())) ;
	}


	@Override
	protected void addAnimationToEm(@NonNull Anim a, boolean sorted, @NonNull EffectsManager em)
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
			loadDamageOffsets();

		return staticDamageOffsets;
	}



	@Override
	protected void adjustAnimForLevel( int lvl ){
		BuildingAnim bAnim = getBuildingAnim();
		if( bAnim != null )
			bAnim.setImage(image);
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
		int lvl = lq.getLevel();

//		if( lvl < 7 )
//			return watchTowerImage;
//		else if( lvl < 14 )
//			return guardTowerImage;
//		else if( lvl >= 14 )
//			return stoneTowerImage;

		return image;
	}
	@Override
	public Image getDamagedImage() {
		loadImages();
		return image;
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
		damagedImage = image;
		deadImage = Assets.smallDestroyedBuilding;
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



	@NonNull
    @Override
	public String toString()
	{
		return TAG;
	}

	@NonNull
    @Override
	public String getName()
	{
		BuildingAnim bAnim = getBuildingAnim();
//		if( bAnim != null ){
//			if( bAnim.getImage() == guardTowerImage )
//				return GUARD_TOWER;
//			else if( bAnim.getImage() == stoneTowerImage )
//				return STONE_TOWER;
//		}


		return TAG;
	}





	@NonNull
    @Override
	public Cost getCosts() {
		return cost;
	}




	@NonNull
    @Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}




}
