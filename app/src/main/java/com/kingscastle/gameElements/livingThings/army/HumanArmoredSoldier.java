package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.UpperMeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class HumanArmoredSoldier extends UpperMeleeSoldier
{

	private static final String TAG = "Armored Soldier";

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

	private static Cost cost = new Cost( 1500 , 1500 , 0 , 2 );

	static{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				1 , 1 , 4 , 2 );
		imageFormatInfo.setRedId( R.drawable.armored_soldier_red );
		imageFormatInfo.setBlueId( R.drawable.armored_soldier_blue );

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared( 0 );
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );
		staticAttackerQualities.setDamage( 12 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 2 );
		staticAttackerQualities.setROF( 1000 );

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresBLvl(8); STATIC_ATTRIBUTES.setRequiresAge(Age.IRON); STATIC_ATTRIBUTES.setRequiresTcLvl(11);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 200 );
		STATIC_ATTRIBUTES.setHealth( 200 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 20 );
		STATIC_ATTRIBUTES.setFullMana( 0 );
		STATIC_ATTRIBUTES.setMana( 0 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 1 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );
		STATIC_ATTRIBUTES.setArmor( 8 );  STATIC_ATTRIBUTES.setdArmorAge( 0 ); STATIC_ATTRIBUTES.setdArmorLvl( 1 );
		STATIC_ATTRIBUTES.setSpeed( 1.0f * dp );
	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
	}

	public HumanArmoredSoldier()
	{
	}


	public HumanArmoredSoldier( @NonNull vector loc , Teams team )

	{
		super( team );
		setLoc( loc );

		setAttributes();
		initialize();
		this.team = team;

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







	@Override
	public ImageFormatInfo getImageFormatInfo()
	{
		return imageFormatInfo;
	}



	public void setImageFormatInfo( ImageFormatInfo imageFormatInfo2 )
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




	/**
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */
	@Nullable
    @Override
	public Image[] getStaticImages() {
		return null;
	}

	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {
	}







	@NonNull
    @Override
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}


	@Override
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		HumanArmoredSoldier.cost = cost;
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
