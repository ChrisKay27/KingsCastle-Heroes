package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.LivingQualities;
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
		float dp = Rpg.getDp();

		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(36000 * dp * dp);
		staticAttackerQualities.setAttackRangeSquared(36000 * dp * dp);
		staticAttackerQualities.setDamage(50);
		staticAttackerQualities.setROF(600);

		staticLivingQualities = new LivingQualities();  staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight(350);
		staticLivingQualities.setLevel( 1 ); //15);
		staticLivingQualities.setFullHealth(1000);
		staticLivingQualities.setHealth(1000);
		staticLivingQualities.setFullMana(125);
		staticLivingQualities.setMana(125);
		staticLivingQualities.setHpRegenAmount(2);
		staticLivingQualities.setRegenRate(2000);



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

	public UndeadStoneTower( @NonNull vector v , Teams t )
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

	@Override
	public Image getIconImage()
	{

		return iconImage;
	}

	private static void setIconImage(Image iconImage)
	{
		UndeadStoneTower.iconImage = iconImage;
	}

	@NonNull
    @Override
	public String toString() {
		return TAG;
	}
	@NonNull
    @Override
	public String getName() {
		return NAME;
	}















}