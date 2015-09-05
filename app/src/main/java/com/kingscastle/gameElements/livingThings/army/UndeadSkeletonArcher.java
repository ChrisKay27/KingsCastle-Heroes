package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MediumRangedSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class UndeadSkeletonArcher extends MediumRangedSoldier
{

	private static final String TAG = "UndeadSkeletonArcher";
	private static final String NAME = "Undead Archer";

	private static Image[] staticImages , redImages , blueImages , greenImages , orangeImages , whiteImages ;
	private static ImageFormatInfo imageFormatInfo;

	@NonNull
    private static final LivingQualities staticLivingQualities; @NonNull
                                                                @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }
	@NonNull
    private static final AttackerQualities staticAttackerQualities; @NonNull
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	private static Cost cost = new Cost( 400 , 400 , 400 , 2 );

	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo(0 , 0,
				0 , 0 , 4 , 2);
		imageFormatInfo.setRedId( R.drawable.skeleton_archer_minor_red );



		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared( 17000 * dp * dp );
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp); staticAttackerQualities.setdRangeSquaredAge(1500 * dp * dp); staticAttackerQualities.setdRangeSquaredLvl(500 * dp * dp);
		staticAttackerQualities.setDamage( 20 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 3 );
		staticAttackerQualities.setROF( 1000 );

		staticLivingQualities = new LivingQualities();  staticLivingQualities.setRequiresBLvl(1); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight( 280 );
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth(225);
		staticLivingQualities.setHealth( 225 ); staticLivingQualities.setdHealthAge( 0 ); staticLivingQualities.setdHealthLvl( 10 );
		staticLivingQualities.setFullMana(0);
		staticLivingQualities.setMana(0);
		staticLivingQualities.setHpRegenAmount( 1 );
		staticLivingQualities.setRegenRate( 2000 );
		staticLivingQualities.setArmor( 5 );  staticLivingQualities.setdArmorAge( 0 ); staticLivingQualities.setdArmorLvl( 1.4f );
		staticLivingQualities.setSpeed( 1.2f * dp );

	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
		setGoldDropped(4);
	}

	public UndeadSkeletonArcher(@NonNull vector loc, Teams team){
		super(team);
		setLoc(loc);
		setTeam(team);
	}


	public UndeadSkeletonArcher() {
	}





	@Override
	public Image[] getImages()
	{
		loadImages();

		Teams teamName = getTeamName();
		if( teamName == null )
		{
			teamName = Teams.BLUE;
		}

		switch( teamName )
		{
		default:
		case RED:
			return redImages;

		case GREEN:
			return greenImages;

		case BLUE:
			return blueImages;

		case ORANGE:
			return orangeImages;

		case WHITE:
			return whiteImages;

		}

	}


	@Override
	public void loadImages()
	{
		if( redImages == null )
		{
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 0, 0, 1, 1);
		}
		if( orangeImages == null )
		{
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 0 , 0 , 1 , 1 );
		}
		if( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 0 , 0 , 1 , 1 );
		}
		if( greenImages == null )
		{
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 0 , 0 , 1 , 1 );
		}
		if( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 0 , 0 , 1 , 1 );
		}
	}




	/**
	 * DO NOT LOAD THE IMAGES HERE, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */
	@Override
	public Image[] getStaticImages()
	{
		return staticImages;
	}



	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages)
	{
		UndeadSkeletonArcher.staticImages = staticImages;
	}




	@Override
	public ImageFormatInfo getImageFormatInfo()
	{
		return imageFormatInfo;
	}


	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo2)
	{
		imageFormatInfo=imageFormatInfo2;
	}



	@Override
	public RectF getStaticPerceivedArea()
	{
		return Rpg.getNormalPerceivedArea();
	}



	@Override
	public void setStaticPerceivedArea(RectF staticPercArea2) {

	}


	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
	}

	@NonNull
    @Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities( staticLivingQualities );
	}


	@Override
	public Cost getCosts()
	{
		return cost;
	}

	public static void setCost(Cost cost)
	{
		UndeadSkeletonArcher.cost = cost;
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
