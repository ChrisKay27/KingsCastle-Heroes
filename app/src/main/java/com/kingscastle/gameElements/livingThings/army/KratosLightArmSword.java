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
import com.kingscastle.gameElements.livingThings.SoldierTypes.MeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class KratosLightArmSword extends MeleeSoldier
{

	private static final String TAG = KratosLightArmSword.class.getSimpleName();
	private static final String NAME = "Kratos";

	private static Image[] staticImages , redImages , blueImages , greenImages , orangeImages , whiteImages ;
	private static ImageFormatInfo imageFormatInfo;


	@NonNull
    private static final AttackerQualities staticAttackerQualities; @NonNull
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    private static final LivingQualities staticLivingQualities; @NonNull
                                                                @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = new Cost( 30 , 0 , 0 , 1 );


	static
	{
		float dp = Rpg.getDp();

		imageFormatInfo = new ImageFormatInfo( 0 , 0 , 2 , 0 , 4 , 2 );
		imageFormatInfo.setID(R.drawable.kratos_3);

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared( 0 );
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );
		staticAttackerQualities.setDamage( 18 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 1 );
		staticAttackerQualities.setROF( 1000 );

		staticLivingQualities = new LivingQualities();  staticLivingQualities.setRequiresBLvl(1); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight( 300 );
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 60 );
		staticLivingQualities.setHealth( 60 ); staticLivingQualities.setdHealthAge( 0 ); staticLivingQualities.setdHealthLvl( 10 ); //
		staticLivingQualities.setFullMana( 0 );
		staticLivingQualities.setMana( 0 );
		staticLivingQualities.setHpRegenAmount( 1 );
		staticLivingQualities.setRegenRate( 1000 );
		staticLivingQualities.setArmor( 3 );  staticLivingQualities.setdArmorAge( 3 ); staticLivingQualities.setdArmorLvl( 1 );
		staticLivingQualities.setSpeed( 1.2f * dp );
	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
		setGoldDropped(3);
	}


	public KratosLightArmSword(@NonNull vector loc, Teams team)
	{
		super(team);
		setLoc( loc );
		super.team = team;
	}

	public KratosLightArmSword()
	{
	}




	@Override
	public Image[] getImages()
	{
		loadImages();
		Teams teamName = getTeamName();
		if( teamName == null )		
			teamName = Teams.BLUE;
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
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 0, 0, 1, 1);
		if( orangeImages == null )
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 0 , 0 , 1 , 1 );
		if( blueImages == null )
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 0 , 0 , 1 , 1 );
		if( greenImages == null )
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 0 , 0 , 1 , 1 );
		if( whiteImages == null )
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 0 , 0 , 1 , 1 );		
	}

	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
	}



	@Override
	public ImageFormatInfo getImageFormatInfo(){
		return imageFormatInfo;
	}

	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo2){
		imageFormatInfo=imageFormatInfo2;
	}


	@Override
	public RectF getStaticPerceivedArea(){
		return Rpg.getNormalPerceivedArea();
	}



	@Override
	public void setStaticPerceivedArea(RectF staticPercArea2) {

	}


	/**
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */
	@Override
	public Image[] getStaticImages() {
		return staticImages;
	}

	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {
		KratosLightArmSword.staticImages = staticImages;
	}





	@NonNull
    @Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities( staticLivingQualities );
	}

	@Override
	public Cost getCosts() {
		return cost;
	}
	public static void setCost(Cost cost) {
		KratosLightArmSword.cost = cost;
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
