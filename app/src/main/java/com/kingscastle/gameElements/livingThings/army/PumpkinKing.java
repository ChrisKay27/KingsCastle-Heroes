package com.kingscastle.gameElements.livingThings.army;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.RectF;



import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.LightEffect2;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedMageSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.spells.LightningBolts;
import com.kingscastle.gameElements.spells.SummoningPortal;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.heroes.R;
import com.kingscastle.level.Level;
import com.kingscastle.teams.Teams;


public class PumpkinKing extends AdvancedMageSoldier
{

	private static final String TAG = "PumpkinKing";
	private static final String NAME = "Pumpkin King";

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;


    private static final AttackerQualities staticAttackerQualities;

    private static final Attributes STATIC_ATTRIBUTES;

	private static Cost cost = new Cost( 2500 , 2500 , 2000 , 3 );

	static
	{
		int dp = (int) Rpg.getDp();

		imageFormatInfo = new ImageFormatInfo( 0  , 0 ,
				0 , 0 , 1 , 1);
		imageFormatInfo.setID( R.drawable.pumkinhead );

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(0);
		staticAttackerQualities.setFocusRangeSquared(15000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp);
		staticAttackerQualities.setDamage( 15 );
		staticAttackerQualities.setROF(300);

		STATIC_ATTRIBUTES = new Attributes();
		STATIC_ATTRIBUTES.setRequiresBLvl(1);
		STATIC_ATTRIBUTES.setRequiresAge(Age.STONE);
		STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 20000 );
		STATIC_ATTRIBUTES.setHealth( 20000 );
		STATIC_ATTRIBUTES.setFullMana(0);
		STATIC_ATTRIBUTES.setMana(0);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setArmor( 15 );
		STATIC_ATTRIBUTES.setSpeed(0.7f * dp);
	}

	private long summonPortalAt;

	{
		setAQ(new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
		setCostsLives(10);
		setGoldDropped(400);
	}

	public PumpkinKing( vector loc, Teams team){
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public PumpkinKing(){
	}

	private Building buildingToDestroy;
	private long tryToDestroyBuildingAt = 0, destroyBuildingAt = Long.MAX_VALUE;
	private Anim tapThis,glow;

	@Override
	public boolean act() {

		if( summonPortalAt < GameTime.getTime() ) {
			summonPortalAt = GameTime.getTime() + 10000;
			Level lvl = getMM().getLevel();

			CD cd = getMM().getUI().getCD();

			vector loc = new vector();
			int attempts=0;
			do{
				loc.set(Math.random()*lvl.getLevelWidthInPx(), Math.random()*lvl.getLevelHeightInPx());
				if( cd.checkPlaceable(loc) )
					break;
				attempts++;
			}while( attempts < 20 );
			if( attempts < 20 ){
				final GameElement sPortal =  new SummoningPortal(loc, this, 1000, 10000,null, PumpkinHead.class);
				getMM().add(sPortal);
				getMM().getUI().addTappable(sPortal, new Runnable() {
					@Override
					public void run() {
						sPortal.die();
					}
				});
			}
		}


		return super.act();
	}

	@Override
	public boolean create( MM mm) {
		boolean superCreate =  super.create(mm);
		getAnim().setScale(1.5f);
		final LightEffect2 le = new LightEffect2(loc);
		le.setScale(2);
		mm.getEm().add(le);
		getAnim().addAnimationListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				le.setOver(true);
			}
		});
		return superCreate;
	}



	@Override
	protected void setupSpells(){
		LightningBolts bolt = new LightningBolts();
		bolt.setDamage(getAQ().getDamage());
		bolt.setCaster(this);
		SpellAttack lBoltAttack = new SpellAttack(getMM(), this , bolt  );
		getAQ().addAttack(lBoltAttack) ;
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
		PumpkinKing.imageFormatInfo = imageFormatInfo;
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




	@Override
	public RectF getStaticPerceivedArea() {
		return Rpg.getNormalPerceivedArea();
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
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		PumpkinKing.cost = cost;
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
		PumpkinKing.redImages = redImages;
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
		PumpkinKing.blueImages = blueImages;
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
		PumpkinKing.greenImages = greenImages;
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
		PumpkinKing.orangeImages = orangeImages;
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
		PumpkinKing.whiteImages = whiteImages;
	}

	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
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
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }



    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }
}
