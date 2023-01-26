package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;


import com.kingscastle.heroes.R;
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

public class UndeadStoneTower extends AttackingBuilding
{
	private static final String TAG = "Undead Stone Tower";
	private static final String NAME = "Stone Tower";

	public static final Buildings name = Buildings.UndeadStoneTower;

	private static RectF staticPerceivedArea = new RectF();

	private static final int aliveId = R.drawable.undead_stone_tower;


	private static Image image,damagedImage,deadImage , iconImage;

	private static final Cost cost = new Cost( 1000 , 0 , 1000 , 0 );



    private static final AttackerQualities staticAttackerQualities;

    private static final Attributes STATIC_ATTRIBUTES;

	private static ArrayList<vector> staticDamageOffsets;


    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }

    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES;   }

	static
	{
		float dp = Rpg.getDp();

		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(36000 * dp * dp);
		staticAttackerQualities.setAttackRangeSquared(36000 * dp * dp);
		staticAttackerQualities.setDamage(50);
		staticAttackerQualities.setROF(600);

		STATIC_ATTRIBUTES = new Attributes();  STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight(350);
		STATIC_ATTRIBUTES.setLevel( 1 ); //15);
		STATIC_ATTRIBUTES.setFullHealth(1000);
		STATIC_ATTRIBUTES.setHealth(1000);
		STATIC_ATTRIBUTES.setFullMana(125);
		STATIC_ATTRIBUTES.setMana(125);
		STATIC_ATTRIBUTES.setHpRegenAmount(2);
		STATIC_ATTRIBUTES.setRegenRate(2000);



		image = Assets.loadImage( aliveId );

		staticPerceivedArea = Rpg.guardTowerArea;


	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
	}


	public UndeadStoneTower()
	{
		super( name , null );
	}

	public UndeadStoneTower(  vector v , Teams t )
	{
		super( name , t );
		setTeam( t );
		setLoc( v );
		loadPerceivedArea();
		loadImages();
	}

	@Override
	public void loadAnimation( @NotNull MM mm )
	{
		boolean justCreatedAnim = buildingAnim == null;

		super.loadAnimation(mm);

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
		staticDamageOffsets.add(new vector(Math.random() * 5 * dp, -15 * dp + Math.random() * 30 * dp));
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
	protected void setupAttack() {

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
	public void loadImages()
	{
		if ( image == null || damagedImage == null || deadImage == null )
		{
			image = Assets.loadImage( aliveId );
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
	public Cost getCosts() {
		return cost;
	}




    @Override
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}

	@Override
	public Image getIconImage()
	{

		return iconImage;
	}

	private static void setIconImage(Image iconImage)
	{
		UndeadStoneTower.iconImage = iconImage;
	}


    @Override
	public String toString() {
		return TAG;
	}

    @Override
	public String getName() {
		return NAME;
	}















}