package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;



import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MediumMage;
import com.kingscastle.gameElements.livingThings.abilities.SpeedShot;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.BuffAttack;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.spells.FireBall;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class WhiteWizard extends MediumMage
{
	private static final String TAG = "WhiteWizard";
	private static final String NAME = "Mage";

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;



    private static final AttackerQualities staticAttackerQualities;
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }

    private static final Attributes STATIC_ATTRIBUTES;
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 2500 , 2500 , 2500 , 2 );


	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				0 , 0 , 1 , 1);
		imageFormatInfo.setOrangeId( R.drawable.white_wizard_red );
		imageFormatInfo.setRedId( R.drawable.white_wizard_red );
		imageFormatInfo.setBlueId( R.drawable.white_wizard_red );

		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(10000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp);
		staticAttackerQualities.setDamage( 20 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 4 );
		staticAttackerQualities.setROF(1000);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresCLvl( 4 ); STATIC_ATTRIBUTES.setRequiresAge(Age.BRONZE); STATIC_ATTRIBUTES.setRequiresTcLvl(8);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth(200);
		STATIC_ATTRIBUTES.setHealth( 200 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 13 );
		STATIC_ATTRIBUTES.setFullMana(200);
		STATIC_ATTRIBUTES.setMana(200);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setSpeed(2.1f * dp);
	}


	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
	}

	//Accessed via reflection
	public WhiteWizard( vector loc, Teams team){
		super(team);
		setLoc(loc);

		setTeam(team);
	}

	public WhiteWizard() {
	}




	@Override
	protected void upgrade(){
		super.upgrade();

	}



	protected void setupSpells(){
		int lvl = attributes.getLevel();

		FireBall fireBall = new FireBall();
		fireBall.setDamage(getAQ().getDamage());
		fireBall.setCaster(this);
		SpellAttack fireBallAttack = new SpellAttack(getMM(), this , fireBall  );
		getAQ().setCurrentAttack( fireBallAttack ) ;


		buffSpell = new SpeedShot(this, null, -(200+lvl*15) );
		getAQ().setFriendlyAttack( new BuffAttack(getMM(), this , buffSpell ) );
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
		{
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 0, 0, 1, 1);
		}
//		if( orangeImages == null )
//		{
//			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 0 , 0 , 1 , 1 );
//		}
		if( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 0 , 0 , 1 , 1 );
		}
//		if( greenImages == null )
//		{
//			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 0 , 0 , 1 , 1 );
//		}
//		if( whiteImages == null )
//		{
//			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 0 , 0 , 1 , 1 );
//		}
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
		WhiteWizard.imageFormatInfo = imageFormatInfo;
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
		return null;
	}

	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {

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
		WhiteWizard.redImages = redImages;
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
		WhiteWizard.blueImages = blueImages;
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
		WhiteWizard.greenImages = greenImages;
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
		WhiteWizard.orangeImages = orangeImages;
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
		WhiteWizard.whiteImages = whiteImages;
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
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}

	@Override
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		WhiteWizard.cost = cost;
	}










}
