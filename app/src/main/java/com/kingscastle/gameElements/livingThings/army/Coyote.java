package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;



import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedMageSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.Bow;
import com.kingscastle.gameElements.livingThings.attacks.BowAnimator;
import com.kingscastle.gameElements.livingThings.attacks.SummonAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.heroes.R;
import com.kingscastle.teams.Teams;

import java.util.ArrayList;
import java.util.List;


public class Coyote extends AdvancedMageSoldier
{

	private static final String TAG = "Coyote";

	private static Image[] images = Assets.loadImages(R.drawable.coyote, 0, 0, 1, 1);


	
    private static final AttackerQualities staticAttackerQualities; 
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	
    private static final Attributes STATIC_ATTRIBUTES; 
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 12000 , 12000 , 12000 , 3 );

	static
	{
		float dp = Rpg.getDp();


		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(10000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(10000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(10000 * dp * dp);
		staticAttackerQualities.setDamage( 80 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 15 );
		staticAttackerQualities.setROF( 2000 );

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresCLvl( 11 ); STATIC_ATTRIBUTES.setRequiresAge(Age.STEEL); STATIC_ATTRIBUTES.setRequiresTcLvl(16);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 8000 );
		STATIC_ATTRIBUTES.setHealth( 8000 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 20 );
		STATIC_ATTRIBUTES.setFullMana( 200 );
		STATIC_ATTRIBUTES.setMana( 200 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 1 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );
		STATIC_ATTRIBUTES.setArmor( 15 );
		STATIC_ATTRIBUTES.setSpeed( .6f * dp );
	}

	
    private List<SummonAttack> summonAtks = new ArrayList<>();
	private long checkSummonsAt;

	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
		startTargetingAgainAt = Long.MAX_VALUE;
		setGoldDropped(300);
		setCostsLives(10);
	}

	public Coyote( vector loc, Teams team){
		super(team);
		setLoc(loc);

		setTeam(team);
	}

	public Coyote() {
	}

	@Override
	public boolean act() {
		if( checkSummonsAt < GameTime.getTime() ) {
			checkSummonsAt = GameTime.getTime() + 4000;
			for (SummonAttack sa : summonAtks)
				sa.act();
		}

		return super.act();
	}

	@Override
	public boolean create( MM mm) {
		boolean superCreate =  super.create(mm);
		getAnim().setScale(1.5f);
//		final LightEffect2 le = new LightEffect2(loc);
//		le.setScale(3);
//		mm.getEm().add(le);
//		getAnim().addAnimationListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				le.setOver(true);
//			}
//		});
		return superCreate;
	}

	@Override
	protected void setupSpells(){
		summonAtks.add(new SummonAttack(getMM(), this, new Rat(new vector(), team), 1));
		summonAtks.add(new SummonAttack(getMM(), this, new RatGrey(new vector(), team),1));
        aq.setCurrentAttack(new Bow(getMM(), this, new Arrow(), BowAnimator.BowTypes.RecurveBow));
	}



	@Override
	public Image[] getImages(){
		return images;
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




	
    @Override
	public String toString() {
		return TAG;
	}
	
    @Override
	public String getName() {
		return TAG;
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
		Coyote.cost = cost;
	}









}
