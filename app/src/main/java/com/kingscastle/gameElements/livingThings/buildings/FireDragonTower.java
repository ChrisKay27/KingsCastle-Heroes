package com.kingscastle.gameElements.livingThings.buildings;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.effects.animations.FlameZone;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.spells.FireBall;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import java.util.ArrayList;

public class FireDragonTower extends AttackingBuilding
{

	private static final String TAG = "Fire Dragon Tower";

	public static final Buildings name = Buildings.FireDragonTower;

	private static RectF staticPerceivedArea;

	private static Image damagedImage , deadImage , iconImage;
	private static final Image image = Assets.loadImage(R.drawable.dragon_statue_red);

	private static final Cost cost = new Cost( 100 , 0 , 0 , 0 );

	@NonNull
    private static final AttackerQualities staticAttackerQualities;
	@NonNull
    private static final LivingQualities staticLivingQualities;
	private static ArrayList<vector> staticDamageOffsets;


	@NonNull
    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities;   }



	static
	{
		float dpSquared = Rpg.getDp()*Rpg.getDp();

		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(20000 * dpSquared);
		staticAttackerQualities.setAttackRangeSquared(20000 * dpSquared);
		staticAttackerQualities.setDamage(20);
		staticAttackerQualities.setROF(500);

		staticLivingQualities = new LivingQualities();staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight(250);
		staticLivingQualities.setLevel(1);
		staticLivingQualities.setFullHealth(250);
		staticLivingQualities.setHealth(250);
		staticLivingQualities.setFullMana(125);
		staticLivingQualities.setMana(125);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);
		staticLivingQualities.setArmor(2);  staticLivingQualities.setdArmorAge(0); staticLivingQualities.setdArmorLvl(2);

		staticPerceivedArea = new RectF(Rpg.guardTowerArea);

		staticAttackerQualities.setdDamageAge(0);
		staticAttackerQualities.setdDamageLvl(5);
		staticAttackerQualities.setdROFAge(0);
		staticAttackerQualities.setdROFLvl(-75);
		staticAttackerQualities.setdRangeSquaredAge(2000 * dpSquared);
		staticAttackerQualities.setdRangeSquaredLvl(1000 * dpSquared);
		staticLivingQualities.setAge(Age.STONE);
		staticLivingQualities.setMaxLevel(6);
		staticLivingQualities.setdHealthAge(0);
		staticLivingQualities.setdHealthLvl(50);
		staticLivingQualities.setdRegenRateAge( 0 );
		staticLivingQualities.setdRegenRateLvl( 0 );
	}

//	private final List<Arms> arms = new ArrayList<>();
//	private final List<LivingThing> targets = new LinkedList<>();

	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
		//arms.add(new Arms(this));
	}



	public FireDragonTower()
	{
		super( name , null );
		loadPerceivedArea();
	}

	public FireDragonTower(@NonNull vector v, Teams t)
	{
		super( name , t );
		setLoc(v);
		setTeam(t);
	}


//	@Override
//	public boolean act(){
//		if( isDead() )
//			return true;
//
//		if ( getLQ().getHealth() < 1 ){
//			die();
//			return true;
//		}
////		if( !isStunned() ) {
////
////			synchronized (targets) {
////				synchronized (arms) {
////					for (int i = targets.size() - 1; i > -1; --i) {
////						LivingThing lt = targets.get(i);
////						if (isOutOfRangeOrDead(this, lt)) {
////							targets.remove(i);
////							continue;
////						}
////						arms.get(i).act(loc.distanceSquared(lt.loc), lt);
////					}
////				}
////			}
////
////			if (targets.size() < lq.getLevel())
////				findATarget();
////		}
//		return isDead();
//	}


//	@Override
//	public void setTarget(LivingThing lt){
//		synchronized(targets){
//			if (targets.size() <= lq.getLevel())
//				targets.add(lt);
//		}
//	}

	@Override
	public void upgrade(){
		super.upgrade();
		setupAttack();
	}


	@Override
	public void setupAttack() {
		getAQ().clearAttacks();
		getAQ().setCurrentAttack(new SpellAttack(getMM(), this, new FireBall(aq.getDamage())));
	}


	@Override
	protected void addAnimationToEm(@NonNull Anim a, boolean sorted, @NonNull EffectsManager em)
	{
		backing.setSize(Backing.TINY);
		//final LightEffect le = new LightEffect(loc, LightEffect.LightEffectColor.LIGHT_ORANGE);
		//em.add(le);
		final FlameZone fz = new FlameZone(new vector(loc));
		fz.setLooping(true);
		//Log.v(TAG, "Adding fire animation: " + fz);
		fz.loc.y += image.getHeightDiv2()/2;
		em.add(fz);
		a.addAnimationListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				//Log.v(TAG,"Removing fire animation");
				fz.setOver(true);
			}
		});

		super.addAnimationToEm(a, sorted, em);
	}

	void loadDamageOffsets()
	{
		float dp = Rpg.getDp();

		staticDamageOffsets = new ArrayList<vector>();
		staticDamageOffsets.add( new vector( Math.random()*-5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*-5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp , -15*dp + Math.random()*30*dp ) );
	}

	@Override
	public ArrayList<vector> getDamageOffsets()
	{
		if( staticDamageOffsets == null )
			loadDamageOffsets();

		return staticDamageOffsets;
	}





	@Override
	public Image getImage() {
		loadImages();
		int lvl = lq.getLevel();

//		if( lvl < 7 )
//			return watchTowerImage;
//		else if( lvl < 14 )
//			return guardTowerImage;
//		else if( lvl >= 14 )
//			return stoneTowerImage;

		return image;
	}
	@Override
	public Image getDamagedImage() {
		loadImages();
		return image;
	}

	@Override
	public Image getDeadImage() {
		loadImages();
		return deadImage;
	}
	@Override
	public Image getIconImage() {
		loadImages();
		return iconImage;
	}
	@Override
	public void loadImages()
	{
		damagedImage = image;
		deadImage = Assets.smallDestroyedBuilding;
	}







	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		loadPerceivedArea();
		return staticPerceivedArea;
	}

	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2)
	{
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea()
	{
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea)
	{
		staticPerceivedArea = staticPercArea;
	}



	@NonNull
    @Override
	public String toString()
	{
		return TAG;
	}

	@NonNull
    @Override
	public String getName()
	{
		BuildingAnim bAnim = getBuildingAnim();
//		if( bAnim != null ){
//			if( bAnim.getImage() == guardTowerImage )
//				return GUARD_TOWER;
//			else if( bAnim.getImage() == stoneTowerImage )
//				return STONE_TOWER;
//		}


		return TAG;
	}





	@NonNull
    @Override
	public Cost getCosts() {
		return cost;
	}




	@NonNull
    @Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}




}
