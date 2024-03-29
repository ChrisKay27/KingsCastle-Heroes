package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;



import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.FourFrameAnimator;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.BasicHealer;
import com.kingscastle.gameElements.livingThings.abilities.HealingSpell;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.HealingAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Team;
import com.kingscastle.teams.Teams;
import com.kingscastle.teams.races.Races;

import org.jetbrains.annotations.NotNull;


public class Medic extends BasicHealer
{
	private static final String TAG = "Medic";


	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;



    private static final AttackerQualities staticAttackerQualities;
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }

    private static final Attributes STATIC_ATTRIBUTES;
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 1500 , 1500 , 0 , 2 );


	static
	{

		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				0 , 0 , 1 , 1 );
		imageFormatInfo.setRedId( R.drawable.early_healer_red );
		imageFormatInfo.setWhiteId( R.drawable.early_healer_white );
		imageFormatInfo.setBlueId( R.drawable.early_healer_white );



		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(12000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(20000 * dp * dp);
		staticAttackerQualities.setROF( 1000 );

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresCLvl(1); STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(4);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 100 );
		STATIC_ATTRIBUTES.setHealth( 100 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 10 );
		STATIC_ATTRIBUTES.setArmor( 1 );  STATIC_ATTRIBUTES.setdArmorAge( 0 ); STATIC_ATTRIBUTES.setdArmorLvl( 0.5f );
		STATIC_ATTRIBUTES.setHealAmount( 17 );  STATIC_ATTRIBUTES.setdHealAge( 0 ); STATIC_ATTRIBUTES.setdHealLvl( 3 );
		STATIC_ATTRIBUTES.setFullMana(100);
		STATIC_ATTRIBUTES.setMana(100);
		STATIC_ATTRIBUTES.setHpRegenAmount( 2 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );

		STATIC_ATTRIBUTES.setSpeed(2.3f * dp);
	}

	{
		setAQ( new AttackerQualities( staticAttackerQualities , getLQ().getBonuses() ) );

	}


	public Medic(  vector loc , Teams team )
	{
		super(team);
		setLoc(loc);
		super.team = team;
	}

	public Medic() {

	}


	@Override
	protected void upgrade(){
		super.upgrade();

		int lvl = attributes.getLevel();

		HealingSpell hs = new HealingSpell(this,null);
		hs.setHealAmount( getLQ().getHealAmount() + getLQ().getdHealLvl()*lvl );
		hs.setCaster( this );
		getAQ().setCurrentAttack( new HealingAttack(getMM(), this , hs ) );
	}

	protected void setupSpells(){
		HealingSpell hs = new HealingSpell(this,null);
		hs.setHealAmount( getLQ().getHealAmount() );
		hs.setCaster( this );
		//getAQ().setCurrentAttack( new HealingAttack( this , hs ) );

		buffMessage = "Heals: ~"+ attributes.getHealAmount()+"hp";
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
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 4, 4, 0, 0, 1, 1);
		}
//		if( orangeImages == null )
//		{
//			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId() , 4 , 4 , 0 , 0 , 1 , 1 );
//		}
		if( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId() , 4 , 4 , 0 , 0 , 1 , 1 );
		}
//		if( greenImages == null )
//		{
//			greenImages = Assets.loadImages( imageFormatInfo.getGreenId() , 4 , 4 , 0 , 0 , 1 , 1 );
//		}
		if( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId() , 4 , 4 , 0 , 0 , 1 , 1 );
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
		Medic.imageFormatInfo = imageFormatInfo;
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
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}

	@Override
	public Cost getCosts() {
		return cost;
	}

	public static void setCost(Cost cost) {
		Medic.cost = cost;
	}


	@Override
	public void loadAnimation( @NotNull  MM mm )
	{
		if ( aliveAnim == null )
		{
			mm.getEm().add( aliveAnim = new FourFrameAnimator(this, getImages()) , true );

			Races race = Races.HUMAN;
			Team t = mm.getTeam( team );
			if( t != null )
				race = t.getRace().getRace();

			addHealthBarToAnim( race );

			aliveAnim.add( healthBar ,true );

		}
	}

	public static Image[] getRedImages()
	{
		return redImages;
	}

	public static void setRedImages(Image[] redImages) {
		Medic.redImages = redImages;
	}

	public static Image[] getBlueImages()
	{
		return blueImages;
	}

	public static void setBlueImages(Image[] blueImages) {
		Medic.blueImages = blueImages;
	}

	public static Image[] getGreenImages()
	{
		return greenImages;
	}

	public static void setGreenImages(Image[] greenImages) {
		Medic.greenImages = greenImages;
	}

	public static Image[] getOrangeImages()
	{
		return orangeImages;
	}

	public static void setOrangeImages(Image[] orangeImages) {
		Medic.orangeImages = orangeImages;
	}

	public static Image[] getWhiteImages()
	{
		return whiteImages;
	}

	public static void setWhiteImages(Image[] whiteImages) {
		Medic.whiteImages = whiteImages;
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
