package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.army.Catapult;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.CatapultAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Stone;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CatapultTower extends AttackingBuilding
{

	private static final String TAG = "Catapult Tower";

	public static final Buildings name = Buildings.CatapultTower;

	private static RectF staticPerceivedArea;

	private static Image damagedImage , deadImage , iconImage;
	private static final Image image = Catapult.eastImages[0];

	private static final Cost cost = new Cost( 50 , 0 , 0 , 0 );

	@NonNull
    private static final AttackerQualities staticAttackerQualities;
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;
	private static ArrayList<vector> staticDamageOffsets;
	private boolean firing;
	private CatapultAttack catapultAttack;


	@NonNull
    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES;   }



	static
	{
		float dpSquared = Rpg.getDp()*Rpg.getDp();

		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(20000 * dpSquared);
		staticAttackerQualities.setAttackRangeSquared(20000 * dpSquared);
		staticAttackerQualities.setDamage(12);
		staticAttackerQualities.setROF(1500);
		staticAttackerQualities.setdDamageLvl(15);
		staticAttackerQualities.setdROFLvl(-100);
		staticAttackerQualities.setdRangeSquaredLvl(2000 * dpSquared);


		STATIC_ATTRIBUTES = new Attributes();
		STATIC_ATTRIBUTES.setRangeOfSight(250);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth(250);
		STATIC_ATTRIBUTES.setHealth(250);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setMaxLevel(5);

		staticPerceivedArea = new RectF(Rpg.guardTowerArea);
	}

	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
	}



	public CatapultTower()
	{
		super(name, null);
		loadPerceivedArea();
	}

	public CatapultTower(@NonNull vector v, Teams t)
	{
		super( name , t );
		setLoc(v);
		setTeam(t);
	}


	@NonNull
    private vector targetDirHumanoidVector = new vector();

	@Override
	protected boolean armsAct()
	{
		if( super.armsAct() )
		{
//			LivingThing targ = getTarget();
//			if( targ != null ){
//
//				targetDirHumanoidVector.set(targ.loc).minus(loc).turnIntoHumanoidVector();
//				lookDir = Vector.getDirection4(targetDirHumanoidVector);
			firing = true;
			//}
			return true;
		}
		return false;
	}

	@Override
	protected void setupAttack() {
		catapultAttack = new CatapultAttack(getMM(),this,new Stone());
		getAQ().setCurrentAttack(catapultAttack) ;
	}


	@Override
	public void loadAnimation(@NotNull MM mm) {
		buildingAnim = new BuildingAnim( this )
		{
			private Image lastImageReturned;
			private long changeImageAt;
			private int onFrame;
			@Override
			public Image getImage()
			{
				if( catapultAttack == null ) return lastImageReturned;

				Image[] images = getImagesForDir( catapultAttack.getLookDir() );
				if( firing )
				{
					if( changeImageAt < GameTime.getTime() )
					{
						//Log.d( TAG , "time to change image, onFrame=" + onFrame );
						++onFrame;

						if( onFrame >= images.length )
						{
							onFrame = 0;
							firing = false;
							////Log.d( TAG , "done attacking animation");
						}

						changeImageAt = GameTime.getTime() + Catapult.animsFramePeriod;
						lastImageReturned = images[ onFrame ];

						return lastImageReturned;
					}
					else
						return lastImageReturned;
				}
				else
					return images[0];

			}

			private Image[] getImagesForDir( @NonNull Rpg.Direction lookDir )
			{
				switch( lookDir )
				{
					default:
					case E:
						return Catapult.eastImages;
					case N:
						return Catapult.northImages;
					case S:
						return Catapult.southImages;
					case W:
						return Catapult.westImages;
				}
			}
		};
	}

	@Override
	public void upgrade(){
		super.upgrade();
		adjustAnimForLevel(attributes.getLevel());
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
		int lvl = attributes.getLevel();

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
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}




}
