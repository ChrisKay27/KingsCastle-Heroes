package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.effects.animations.LightEffect;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.spells.Icicle;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import java.util.ArrayList;

public class IceDragonTower extends AttackingBuilding
{

	private static final String TAG = "Ice Dragon Tower";

	public static final Buildings name = Buildings.IceDragonTower;

	private static RectF staticPerceivedArea;

	private static Image damagedImage , deadImage , iconImage;
	private static final Image image = Assets.loadImage(R.drawable.dragon_statue_blue);

	private static final Cost cost = new Cost( 150 , 0 , 0 , 0 );

	@NonNull
    private static final AttackerQualities staticAttackerQualities;
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;
	private static ArrayList<vector> staticDamageOffsets;


	@NonNull
    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES;   }



	static
	{
		float dpSquared = Rpg.getDp()*Rpg.getDp();

		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(15000 * dpSquared);
		staticAttackerQualities.setAttackRangeSquared(15000 * dpSquared);
		staticAttackerQualities.setDamage(30);
		staticAttackerQualities.setROF(1000);

		STATIC_ATTRIBUTES = new Attributes();
		STATIC_ATTRIBUTES.setRangeOfSight(250);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth(250);
		STATIC_ATTRIBUTES.setHealth(250);
		STATIC_ATTRIBUTES.setFullMana(125);
		STATIC_ATTRIBUTES.setMana(125);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setArmor( 2 );

		staticPerceivedArea = new RectF(Rpg.guardTowerArea);

		staticAttackerQualities.setdDamageLvl(10);
		staticAttackerQualities.setdROFLvl(-150);
		staticAttackerQualities.setdRangeSquaredLvl(1000 * dpSquared);
		STATIC_ATTRIBUTES.setMaxLevel(10);
		STATIC_ATTRIBUTES.setdHealthLvl(50);
	}

	//********************************** End Static ***********************************************//

//
//	private final List<Arms> arms = new ArrayList<>();
//	private final List<LivingThing> targets = new LinkedList<>();

	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));


	}



	public IceDragonTower()
	{
		super(name, null);
		loadPerceivedArea();
	}

	public IceDragonTower(@NonNull vector v, Teams t)
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
//
//		if( !isStunned() ) {
//			synchronized (targets) {
//				synchronized (arms) {
//					for (int i = targets.size() - 1; i > -1; --i) {
//						LivingThing lt = targets.get(i);
//						if (isOutOfRangeOrDead(this, lt)) {
//							targets.remove(i);
//							continue;
//						}
//						arms.get(i).act(loc.distanceSquared(lt.loc), lt);
//					}
//				}
//			}
//
//			if (targets.size() < attributes.getLevel())
//				findATarget();
//		}
//		return isDead();
//	}
//
//
//	@Override
//	public void setTarget(LivingThing lt){
//		synchronized(targets){
//			if (targets.size() <= attributes.getLevel())
//				targets.add(lt);
//		}
//	}


	@Override
	public void upgrade(){
		super.upgrade();
		adjustAnimForLevel(attributes.getLevel());
		setupAttack();

	}


	@Override
	public void setupAttack() {

		getAQ().setCurrentAttack(new SpellAttack(getMM(), this, new Icicle(aq.getDamage(),0.5f + (getLevel()-1)*0.1f)));
	}


	@Override
	protected void addAnimationToEm(@NonNull Anim a, boolean sorted, @NonNull EffectsManager em)
	{
		backing.setSize(Backing.TINY);

		a.add(new LightEffect(loc, LightEffect.LightEffectColor.LIGHT_BLUE), true);

		super.addAnimationToEm(a, sorted, em);
		//		em.add( a , true);
		//
		//		em.add( backing, EffectsManager.Position.Behind );
	}

	void loadDamageOffsets()
	{
		float dp = Rpg.getDp();

		staticDamageOffsets = new ArrayList<>();
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
	protected void adjustAnimForLevel( int lvl ){
		BuildingAnim bAnim = getBuildingAnim();
		if( bAnim != null )
			bAnim.setImage(image);
//		BuildingAnim bAnim = getBuildingAnim();
//		if( bAnim != null ){
//			if( lvl < 7 )
//				bAnim.setImage(watchTowerImage);
//			else if( lvl < 14 )
//				bAnim.setImage(guardTowerImage);
//			else if( lvl >= 14 )
//				bAnim.setImage(stoneTowerImage);
//
//			//			float scale = lvl%7;
//			//			//bAnim.setScale(1.1f);
//			//			bAnim.setScale(1f+((scale-1)/10f));
//		}
//		backing.setSize(Backing.MEDIUM);
	}





	@Override
	public Image getImage() {
		loadImages();
		int lvl = attributes.getLevel();

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
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}




}
