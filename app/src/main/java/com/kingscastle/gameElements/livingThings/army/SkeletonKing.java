package com.kingscastle.gameElements.livingThings.army;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.LightEffect2;
import com.kingscastle.effects.animations.TapAnim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedMageSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.livingThings.attacks.SummonAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.spells.LightningBolts;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import java.util.ArrayList;
import java.util.List;


public class SkeletonKing extends AdvancedMageSoldier
{

	private static final String TAG = "SkeletonKing";
	private static final String NAME = "Skeleton King";

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;


	@NonNull
    private static final AttackerQualities staticAttackerQualities; @NonNull
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES; @NonNull
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 2500 , 2500 , 2000 , 3 );

	static
	{
		int dp = (int) Rpg.getDp();

		imageFormatInfo = new ImageFormatInfo( 0  , 0 ,
				0 , 0 , 1 , 1);
		imageFormatInfo.setRedId( R.drawable.skeleton_king );

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(0);
		staticAttackerQualities.setFocusRangeSquared(15000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp);
		staticAttackerQualities.setDamage( 100 );
		staticAttackerQualities.setROF(300);

		STATIC_ATTRIBUTES = new Attributes();
		STATIC_ATTRIBUTES.setRequiresBLvl(1);
		STATIC_ATTRIBUTES.setRequiresAge(Age.STONE);
		STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 50000 );
		STATIC_ATTRIBUTES.setHealth( 50000 );
		STATIC_ATTRIBUTES.setFullMana(0);
		STATIC_ATTRIBUTES.setMana(0);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setArmor( 15 );
		STATIC_ATTRIBUTES.setSpeed(0.5f * dp);
	}

	@NonNull
    private List<SummonAttack> summonAtks = new ArrayList<>();
	private long checkSummonsAt = GameTime.getTime() + 5000;

	{
		setAQ(new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
		setCostsLives(100);
	}



	public SkeletonKing(@NonNull vector loc, Teams team){
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public SkeletonKing(){
	}

	private long summonAt = Long.MAX_VALUE,stopSummoningAt;
	private final List<Anim> anims = new ArrayList<>();


	@Override
	public boolean act() {

		if( checkSummonsAt < GameTime.getTime() ) {
			summonAt = 0;
			checkSummonsAt = GameTime.getTime() + 2500+ (int)(Math.random()*5000);
			stopSummoningAt = GameTime.getTime() + 2000;

			final Anim tapAnim = new TapAnim(loc);
			tapAnim.setAliveTime(2000);
			getMM().getUI().addTappable(this, new Runnable() {
				@Override
				public void run() {
					summonAt = Long.MAX_VALUE;
					tapAnim.setOver(true);
					stopSummoningAt = 0;
				}
			});
			synchronized (anims) {
				anims.add(tapAnim);
			}
			getMM().getEm().add(tapAnim, EffectsManager.Position.InFront);
		}

		if( stopSummoningAt < GameTime.getTime() )
			summonAt = Long.MAX_VALUE;


		if( summonAt < GameTime.getTime() && stopSummoningAt > GameTime.getTime() ){
			for (SummonAttack sa : summonAtks)
				sa.act();
			summonAt = GameTime.getTime() + 500;
		}

		if( summonAt != Long.MAX_VALUE ) {
//			vector v = new vector(loc);
//			v.randomize((int) Rpg.sixtyFourDp);
//			getMM().getEm().add(new BlackSummonSmokeAnim(v));
//			getMM().getEm().add(new LightningStrikeAnim(v));
			return isDead();
		}

		return super.act();
	}

	@Override
	public boolean create(@NonNull MM mm) {
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
		summonAtks.add(new SummonAttack(getMM(), this, new FullMetalJacket(new vector(), team), 1));
		summonAtks.add(new SummonAttack(getMM(), this, new VampLord(new vector(), team), 1));
		summonAtks.add(new SummonAttack(getMM(), this, new ZombieFast(new vector(), team), 1));
		summonAtks.add(new SummonAttack(getMM(), this, new UndeadDeathKnight(new vector(), team), 1));

		LightningBolts bolt = new LightningBolts();
		bolt.setDamage(getAQ().getDamage());
		bolt.setCaster(this);
		SpellAttack lBoltAttack = new SpellAttack(getMM(), this , bolt  );
		getAQ().addAttack(lBoltAttack) ;
	}

	@Override
	public void die() {
		super.die();
		synchronized (anims){
			for(Anim a : anims)
				a.setOver(true);
			anims.clear();
		}
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
		SkeletonKing.imageFormatInfo = imageFormatInfo;
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
		SkeletonKing.cost = cost;
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
		SkeletonKing.redImages = redImages;
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
		SkeletonKing.blueImages = blueImages;
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
		SkeletonKing.greenImages = greenImages;
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
		SkeletonKing.orangeImages = orangeImages;
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
		SkeletonKing.whiteImages = whiteImages;
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
