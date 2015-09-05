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
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MediumMeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class HumanSoldier extends MediumMeleeSoldier
{

	private static final String TAG = "Soldier";

	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;
	private static ImageFormatInfo imageFormatInfo;


	@NonNull
    private static final AttackerQualities staticAttackerQualities; @NonNull
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    private static final LivingQualities staticLivingQualities; @NonNull
                                                                @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = new Cost( 500 , 500 , 0 , 2 );

	static{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				1 , 1 , 4 , 2 );
		imageFormatInfo.setRedId( R.drawable.soldier_red );
		imageFormatInfo.setBlueId( R.drawable.soldier_blue );

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared( 0 );
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );
		staticAttackerQualities.setDamage( 8 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 2 );
		staticAttackerQualities.setROF( 1000 );

		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresBLvl(4); staticLivingQualities.setRequiresAge(Age.BRONZE); staticLivingQualities.setRequiresTcLvl(6);
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 120 );
		staticLivingQualities.setHealth( 120 ); staticLivingQualities.setdHealthAge( 0 ); staticLivingQualities.setdHealthLvl( 10 );
		staticLivingQualities.setFullMana( 0 );
		staticLivingQualities.setMana( 0 );
		staticLivingQualities.setHpRegenAmount( 1 );
		staticLivingQualities.setRegenRate( 1000 );
		staticLivingQualities.setArmor( 6 );  staticLivingQualities.setdArmorAge( 0 ); staticLivingQualities.setdArmorLvl( 1 );
		staticLivingQualities.setSpeed( 1.0f * dp );
	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
	}

	public HumanSoldier()
	{
	}


	public HumanSoldier( @NonNull vector loc , Teams team )	{
		super(team);
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
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}


	@Override
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		HumanSoldier.cost = cost;
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
