package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;



import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.UpperRangedSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.Bow;
import com.kingscastle.gameElements.livingThings.attacks.BowAnimator;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class HumanBoresArcher extends UpperRangedSoldier
{
	private static final String TAG = "Bores Archer";

	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;
	private static ImageFormatInfo imageFormatInfo;

	
    private static final AttackerQualities staticAttackerQualities; 
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	
    private static final Attributes STATIC_ATTRIBUTES; 
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 1000 , 1000 , 1000 , 2 );

	static
	{
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				1 , 1 , 4 , 2);
		imageFormatInfo.setRedId( R.drawable.bores_red );

		float dp = Rpg.getDp();

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(10000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp);
		staticAttackerQualities.setDamage( 40 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 5 );
		staticAttackerQualities.setROF(1000);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresBLvl(10); STATIC_ATTRIBUTES.setRequiresAge(Age.IRON); STATIC_ATTRIBUTES.setRequiresTcLvl(11);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 400 );
		STATIC_ATTRIBUTES.setHealth( 400 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 16 );
		STATIC_ATTRIBUTES.setFullMana( 125 );
		STATIC_ATTRIBUTES.setMana(125);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setArmor( 8 );  STATIC_ATTRIBUTES.setdArmorAge( 0 ); STATIC_ATTRIBUTES.setdArmorLvl( 1.8f );
		STATIC_ATTRIBUTES.setSpeed( 2.1f * dp );
	}

	{
		setAQ(new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
	}


	public HumanBoresArcher( vector loc, Teams team)

	{super(team);
	setLoc(loc);
	setTeam(team);
	}

	public HumanBoresArcher()
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

			getAQ().setCurrentAttack( new Bow( mm, this , new Arrow() , BowAnimator.BowTypes.GreatBow ) );
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
		HumanBoresArcher.imageFormatInfo = imageFormatInfo;
	}







	
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
		HumanBoresArcher.cost = cost;
	}


	
    @Override
	public String toString() {
		return TAG;
	}
	
    @Override
	public String getName() {
		return TAG;
	}
}
