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
import com.kingscastle.heroes.R;
import com.kingscastle.teams.Teams;

import java.util.ArrayList;
import java.util.List;


public class SkeletonKing extends AdvancedMageSoldier
{

	private static final String TAG = "SkeletonKing";
	private static final String NAME = "Skeleton King";

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages = Assets.loadImages(R.drawable.skeleton_king,0,0,1,1), blueImages , greenImages , orangeImages , whiteImages ;


	
    private static final AttackerQualities staticAttackerQualities; 
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	
    private static final Attributes STATIC_ATTRIBUTES; 
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
		staticAttackerQualities.setDamage( 20 );
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
		STATIC_ATTRIBUTES.setSpeed(0.7f * dp);
	}

	
    private List<SummonAttack> summonAtks = new ArrayList<>();
	private long checkSummonsAt = GameTime.getTime() + 5000;

	{
		setAQ(new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
		setCostsLives(100);
	}



	public SkeletonKing( vector loc, Teams team){
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public SkeletonKing(){
	}

	private final List<Anim> anims = new ArrayList<>();


	@Override
	public boolean act() {

		if( checkSummonsAt < GameTime.getTime() ) {
			checkSummonsAt = GameTime.getTime() + 2500+ (int)(Math.random()*5000);
            for (SummonAttack sa : summonAtks)
                sa.act();
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
		Teams teamName = getTeamName();
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




	/**
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */
	
    @Override
	public Image[] getStaticImages() {
		return null;
	}
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
		SkeletonKing.cost = cost;
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
}
