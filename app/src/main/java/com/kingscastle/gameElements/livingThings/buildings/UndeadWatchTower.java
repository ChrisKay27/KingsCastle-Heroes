package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;


import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UndeadWatchTower extends AttackingBuilding
{

	private static final String TAG = "UndeadWatchTower";
	private static final String NAME = "Watch Tower";

	public static final Buildings name = Buildings.UndeadWatchTower;

	private static RectF staticPerceivedArea;

	private static Image image , damagedImage , deadImage , iconImage;


	private static final Cost cost = new Cost( 500 , 0 , 500 , 0 );


    private static final AttackerQualities staticAttackerQualities;

    private static final Attributes STATIC_ATTRIBUTES;

	private static ArrayList<vector> staticDamageOffsets;


    @Override
	protected AttackerQualities getStaticAQ() {
		return staticAttackerQualities;
	}

    @Override
	protected Attributes getStaticLQ() {
		return STATIC_ATTRIBUTES;
	}
	static
	{
		float dpSquared = Rpg.getDpSquared();

		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(30000 * dpSquared);
		staticAttackerQualities.setAttackRangeSquared(30000 * dpSquared);
		staticAttackerQualities.setDamage(15);
		staticAttackerQualities.setROF(800);

		STATIC_ATTRIBUTES = new Attributes();  STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight(250);
		STATIC_ATTRIBUTES.setLevel( 1 ); //1);
		STATIC_ATTRIBUTES.setFullHealth(250);
		STATIC_ATTRIBUTES.setHealth(250);
		STATIC_ATTRIBUTES.setFullMana(125);
		STATIC_ATTRIBUTES.setMana(125);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(4000);

		staticAttackerQualities.setdDamageAge(4);
		staticAttackerQualities.setdDamageLvl(1);
		staticAttackerQualities.setdROFAge(-100);
		staticAttackerQualities.setdROFLvl(-20);
		staticAttackerQualities.setdRangeSquaredAge(-3000 * dpSquared);
		staticAttackerQualities.setdRangeSquaredLvl(-100 * dpSquared);
		STATIC_ATTRIBUTES.setAge( Age.STONE );
		STATIC_ATTRIBUTES.setdHealthAge(300);
		STATIC_ATTRIBUTES.setdHealthLvl(50);
		STATIC_ATTRIBUTES.setdRegenRateAge( -100 );
		STATIC_ATTRIBUTES.setdRegenRateLvl(-20);
		staticPerceivedArea = Rpg.guardTowerArea;

	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
	}



	public UndeadWatchTower()
	{
		super( name , null );
		loadPerceivedArea();
	}

	public UndeadWatchTower( vector v, Teams t)
	{
		super( name , t );
		setLoc(v);
		setTeam(t);
	}

	@Override
	protected void setupAttack() {

	}

	@Override
	public void loadAnimation( @NotNull MM mm )
	{
		boolean justCreatedAnim = buildingAnim == null;

		super.loadAnimation( mm );

		if( buildingAnim != null && justCreatedAnim )

			buildingAnim.setOffs( 0 , -Rpg.sixTeenDp );
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


	@Override
	public Image getImage() {
		loadImages();
		return image;
	}
	@Override
	public Image getDamagedImage() {
		loadImages();
		return damagedImage;
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
		if (image == null)
		{
			int aliveId = 0;//R.drawable.evil_watch_tower;
			image = Assets.loadImage(aliveId);
			damagedImage = image;
			deadImage = Assets.smallDestroyedBuilding;
		}
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
		return NAME;
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
