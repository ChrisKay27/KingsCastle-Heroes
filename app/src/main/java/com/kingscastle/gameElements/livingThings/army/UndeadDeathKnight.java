package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedMeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class UndeadDeathKnight extends AdvancedMeleeSoldier
{

	private static final String TAG = "UndeadDeathKnight";
	private static final String NAME = "Death Knight";

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;


	@NonNull
    private static final AttackerQualities staticAttackerQualities; @NonNull
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    private static final LivingQualities staticLivingQualities; @NonNull
                                                                @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = new Cost( 2500 , 2500 , 2000 , 3 );

	static
	{
		int dp = (int) Rpg.getDp();

		imageFormatInfo = new ImageFormatInfo( 0  , 0 ,
				0 , 0 , 1 , 1);
		imageFormatInfo.setRedId( R.drawable.demon_soldier_red );

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(0);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );
		staticAttackerQualities.setDamage( 60 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 7 );
		staticAttackerQualities.setROF(1000);

		staticLivingQualities = new LivingQualities();  staticLivingQualities.setRequiresBLvl(1); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 2500 );
		staticLivingQualities.setHealth( 2500 ); staticLivingQualities.setdHealthAge( 0 ); staticLivingQualities.setdHealthLvl( 25 );
		staticLivingQualities.setFullMana(0);
		staticLivingQualities.setMana(0);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);
		staticLivingQualities.setArmor(50);
		staticLivingQualities.setSpeed(0.6f * dp);
	}

	{
		setAQ(new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
		setGoldDropped(60);
	}



	public UndeadDeathKnight(@NonNull vector loc, Teams team){
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public UndeadDeathKnight(){
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
	 * @return the imageFormatInfo
	 */
	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return imageFormatInfo;
	}


	/**
	 * @param imageFormatInfo the imageFormatInfo to set
	 */
	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo) {
		UndeadDeathKnight.imageFormatInfo = imageFormatInfo;
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




	@Override
	public RectF getStaticPerceivedArea() {
		return Rpg.getNormalPerceivedArea();
	}

	@NonNull
    @Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {

	}


	@Override
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		UndeadDeathKnight.cost = cost;
	}

	public static Image[] getRedImages()
	{
		if ( redImages == null )
		{
			redImages = Assets.loadImages( imageFormatInfo.getRedId()  , 3 , 4, 0 , 0 , 1 , 1 );
		}
		return redImages;
	}

	public static void setRedImages(Image[] redImages) {
		UndeadDeathKnight.redImages = redImages;
	}

	public static Image[] getBlueImages()
	{
		if ( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 3 , 4, 0 , 0 , 1 , 1 );
		}
		return blueImages;
	}

	public static void setBlueImages(Image[] blueImages) {
		UndeadDeathKnight.blueImages = blueImages;
	}

	public static Image[] getGreenImages()
	{
		if ( greenImages == null )
		{
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		return greenImages;
	}

	public static void setGreenImages(Image[] greenImages) {
		UndeadDeathKnight.greenImages = greenImages;
	}

	public static Image[] getOrangeImages()
	{
		if ( orangeImages == null )
		{
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		return orangeImages;
	}

	public static void setOrangeImages(Image[] orangeImages) {
		UndeadDeathKnight.orangeImages = orangeImages;
	}

	public static Image[] getWhiteImages()
	{
		if ( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		return whiteImages;
	}

	public static void setWhiteImages(Image[] whiteImages) {
		UndeadDeathKnight.whiteImages = whiteImages;
	}

	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
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