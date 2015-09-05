package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MediumRangedSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.Bow;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class HumanArcher extends MediumRangedSoldier
{
	private static final String TAG = "Archer";

	private static Image[] staticImages , redImages , blueImages , greenImages , orangeImages , whiteImages ;
	private static ImageFormatInfo imageFormatInfo;

	@NonNull
    private static final LivingQualities staticLivingQualities; @NonNull
                                                                @Override
	protected LivingQualities getStaticLQ()   { return staticLivingQualities; }
	@NonNull
    private static final AttackerQualities staticAttackerQualities; @NonNull
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }

	private static Cost cost = new Cost( 400 , 400 , 400 , 2 );

	static{
		int dp = (int) Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo(0 , 0,
				0 , 0 , 4 , 2);
		imageFormatInfo.setRedId( R.drawable.archer_red );


		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared( 17000 * dp * dp );
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp); staticAttackerQualities.setdRangeSquaredAge(0); staticAttackerQualities.setdRangeSquaredLvl(500 * dp * dp);
		staticAttackerQualities.setDamage( 20 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 3 );
		staticAttackerQualities.setROF(1000);

		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresBLvl(6); staticLivingQualities.setRequiresAge(Age.BRONZE); staticLivingQualities.setRequiresTcLvl(6);
		staticLivingQualities.setRangeOfSight( 350 );
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 200 );
		staticLivingQualities.setHealth( 200 ); staticLivingQualities.setdHealthAge( 0 ); staticLivingQualities.setdHealthLvl( 13 );
		staticLivingQualities.setFullMana(0);
		staticLivingQualities.setMana(0);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);
		staticLivingQualities.setArmor( 6 );  staticLivingQualities.setdArmorAge( 0 ); staticLivingQualities.setdArmorLvl( 1.4f );
		staticLivingQualities.setSpeed(2.1f * dp);
	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
	}

	public HumanArcher(@NonNull vector loc, Teams team){
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public HumanArcher() {

	}


	@Override
	public void finalInit( MM mm )
	{
		super.finalInit( mm );

		if( getAQ().getCurrentAttack() != null )
			return;

		else if( getAQ().getCurrentAttack() == null )
		{
			getAQ().setCurrentAttack( new Bow( mm, this , new Arrow() ) );
			aliveAnim.add( getAQ().getCurrentAttack().getAnimator() , true );
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
		HumanArcher.staticImages = staticImages;
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
		HumanArcher.cost = cost;
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
