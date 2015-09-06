package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedRangedSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.Bow;
import com.kingscastle.gameElements.livingThings.attacks.BowAnimator;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class HumanLongBowMan extends AdvancedRangedSoldier
{
	private static final String TAG = "LongBowMan";

	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;
	private static ImageFormatInfo imageFormatInfo;

	@NonNull
    private static final AttackerQualities staticAttackerQualities; @NonNull
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES; @NonNull
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 4000 , 4000 , 4000 , 2 );

	static
	{
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				1 , 1 , 4 , 2);
		imageFormatInfo.setRedId( R.drawable.bad_ass_knight_red );

		float dp = Rpg.getDp();

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(10000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp);
		staticAttackerQualities.setDamage( 60 );  staticAttackerQualities.setdDamageAge(0); staticAttackerQualities.setdDamageLvl( 8 );
		staticAttackerQualities.setROF(1000);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresBLvl(12); STATIC_ATTRIBUTES.setRequiresAge(Age.STEEL); STATIC_ATTRIBUTES.setRequiresTcLvl(16);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 600 );
		STATIC_ATTRIBUTES.setHealth( 600 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 20 );
		STATIC_ATTRIBUTES.setFullMana( 125 );
		STATIC_ATTRIBUTES.setMana(125);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setArmor( 12 );  STATIC_ATTRIBUTES.setdArmorAge( 0 ); STATIC_ATTRIBUTES.setdArmorLvl( 2 );
		STATIC_ATTRIBUTES.setSpeed( 2.0f * dp );

	}

	{
		setAQ(new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
	}


	public HumanLongBowMan(@NonNull vector loc, Teams team)

	{super(team);
	setLoc(loc);
	setTeam(team);
	}

	public HumanLongBowMan()
	{

	}



	@Override
	public void finalInit( MM mm )
	{
		super.finalInit( mm );

		if( hasFinalInited )
		{
			return;
		}

		if( getAQ().getCurrentAttack() == null )
		{

			getAQ().setCurrentAttack( new Bow( mm, this , new Arrow() , BowAnimator.BowTypes.RecurveBow ) );
			aliveAnim.add(getAQ().getCurrentAttack().getAnimator(),true);
			hasFinalInited = true;
		}
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
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null. This method is used to check if the static images are null so they can be loaded.
	 * @return the staticImages
	 */
	@Nullable
    @Override
	public Image[] getStaticImages()
	{
		return null;
	}


	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages2) {
	}







	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return imageFormatInfo;
	}

	public static void setImageFormatInfo(ImageFormatInfo imageFormatInfo) {
		HumanLongBowMan.imageFormatInfo = imageFormatInfo;
	}







	@NonNull
    @Override
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}







	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
	}

	@Override
	public Cost getCosts()
	{
		return cost;
	}

	public static void setCost(Cost cost) {
		HumanLongBowMan.cost = cost;
	}


	@NonNull
    @Override
	public String toString() {
		return TAG;
	}
	@NonNull
    @Override
	public String getName() {
		return TAG;
	}
}
