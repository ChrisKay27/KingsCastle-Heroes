package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;



import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.BasicHealer;
import com.kingscastle.gameElements.livingThings.abilities.HealingSpell;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.HealingAttack;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class UndeadHealer extends BasicHealer
{

	private static final String TAG = "UndeadHealer";
	private static final String NAME = "Daemon";

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;


    private static final AttackerQualities staticAttackerQualities;
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }

    private static final Attributes STATIC_ATTRIBUTES;
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 75 , 50 , 50 , 1 );

	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				0 , 0 , 1 , 1 );
		imageFormatInfo.setRedId( R.drawable.skeleton_healer_red );

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(12000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(20000 * dp * dp); staticAttackerQualities.setdRangeSquaredAge(1500 * dp * dp); staticAttackerQualities.setdRangeSquaredLvl(500 * dp * dp);
		staticAttackerQualities.setROF(1000);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresCLvl( 1 );  STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 50 );
		STATIC_ATTRIBUTES.setHealth( 50 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 10 );
		STATIC_ATTRIBUTES.setFullMana( 100 );
		STATIC_ATTRIBUTES.setMana( 100 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 2 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );
		STATIC_ATTRIBUTES.setSpeed( 1.0f * dp );
		STATIC_ATTRIBUTES.setHealAmount( 15 ); STATIC_ATTRIBUTES.setdHealLvl( 4 );
	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
		setGoldDropped(10);
	}


	public UndeadHealer( vector loc,Teams team)
	{
		super(team);
		setLoc(loc);
		super.team = team;
	}

	public UndeadHealer() {

	}



	@Override
	protected void setupSpells(){
		HealingSpell hs = new HealingSpell(this,null);
		hs.setHealAmount( attributes.getHealAmount() );
		hs.setShowEvilAnimation( true );
		hs.setCaster( this );
		HealingAttack ha = new HealingAttack(getMM(), this , hs );
		ha.setShowEvilAnimation(true);
		getAQ().setFriendlyAttack(ha);

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
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 3, 4, 0, 0, 1, 1);
		}
		if( orangeImages == null )
		{
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		if( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		if( greenImages == null )
		{
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		if( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId() , 3 , 4 , 0 , 0 , 1 , 1 );
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
		UndeadHealer.imageFormatInfo = imageFormatInfo;
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
	public Image[] getStaticImages()
	{
		return null;
	}


	/**
	 * @param staticImages2 the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages2) {

	}


	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
	}





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
		UndeadHealer.cost = cost;
	}




	public static Image[] getRedImages()
	{
		return redImages;
	}

	public static void setRedImages(Image[] redImages) {
		UndeadHealer.redImages = redImages;
	}

	public static Image[] getBlueImages()
	{
		return blueImages;
	}

	public static void setBlueImages(Image[] blueImages) {
		UndeadHealer.blueImages = blueImages;
	}

	public static Image[] getGreenImages()
	{
		return greenImages;
	}

	public static void setGreenImages(Image[] greenImages) {
		UndeadHealer.greenImages = greenImages;
	}

	public static Image[] getOrangeImages()
	{
		return orangeImages;
	}

	public static void setOrangeImages(Image[] orangeImages) {
		UndeadHealer.orangeImages = orangeImages;
	}

	public static Image[] getWhiteImages()
	{
		return whiteImages;
	}

	public static void setWhiteImages(Image[] whiteImages) {
		UndeadHealer.whiteImages = whiteImages;
	}


    @Override
	public String toString() {
		return TAG;
	}

    @Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getAbilityMessage()	{
		return buffMessage;
	}

}
