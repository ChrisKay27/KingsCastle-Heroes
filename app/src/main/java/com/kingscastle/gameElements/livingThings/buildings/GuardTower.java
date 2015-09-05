package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.heroes.R;
import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.ProjectileAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GuardTower extends AttackingBuilding
{

	private static final String TAG = "Guard Tower";
	public static final Buildings name = Buildings.GuardTower;

	private static RectF staticPerceivedArea;


	private static Image image = Assets.loadImage(R.drawable.round_tower);
	private static Image damagedImage = image;
	private static Image deadImage = Assets.smallDestroyedBuilding;


	private static final Cost cost = new Cost ( 1000 , 0 , 1000 , 0 );


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
		float dpSquared = Rpg.getDp() * Rpg.getDp();


		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(35000 * dpSquared);
		staticAttackerQualities.setAttackRangeSquared(35000 * dpSquared);
		staticAttackerQualities.setDamage(25);
		staticAttackerQualities.setROF(700);

		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresAge(Age.BRONZE); staticLivingQualities.setRequiresTcLvl(8);
		staticLivingQualities.setRangeOfSight(300);
		staticLivingQualities.setLevel( 1 ); //10);
		staticLivingQualities.setFullHealth(500);
		staticLivingQualities.setHealth(500);
		staticLivingQualities.setFullMana(125);
		staticLivingQualities.setMana(125);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);

		staticAttackerQualities.setdDamageAge(4);
		staticAttackerQualities.setdDamageLvl(1);
		staticAttackerQualities.setdROFAge(-100);
		staticAttackerQualities.setdROFLvl(-20);
		staticAttackerQualities.setdRangeSquaredAge(-3000 * dpSquared);
		staticAttackerQualities.setdRangeSquaredLvl(-100 * dpSquared);
		staticLivingQualities.setAge( Age.STONE );
		staticLivingQualities.setdHealthAge(100);
		staticLivingQualities.setdHealthLvl(30);
		staticLivingQualities.setdRegenRateAge( -100 );
		staticLivingQualities.setdRegenRateLvl(-20);



		staticPerceivedArea = Rpg.guardTowerArea;

		//staticPerceivedArea = new RectF( -image.getWidthDiv2() , 0 , image.getWidthDiv2() , image.getHeightDiv2() );

	}


	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));

	}


	public GuardTower()
	{
		super( name , null );
	}

	public GuardTower( @NonNull vector v , Teams t )
	{
		super( name , t );
		setTeam( t );
		setLoc( v );
		loadPerceivedArea();
		loadImages();
	}

	@Override
	public boolean create(@NonNull MM mm) {
		getAQ().setCurrentAttack(new ProjectileAttack(mm, this, new Arrow())) ;
		return super.create(mm);
	}
	@Override
	protected void setupAttack() {

	}

	@Override
	public void loadAnimation( @NotNull MM mm )
	{
		boolean justCreatedAnim = buildingAnim == null;

		super.loadAnimation( mm );

		if( buildingAnim != null && justCreatedAnim )
			buildingAnim.setOffs( 0 , -Rpg.sixTeenDp );
	}

	@Override
	protected void addAnimationToEm(@NonNull Anim a, boolean sorted, @NonNull EffectsManager em){
		em.add( a , true);
		backing.setSize(Backing.TINY);
		em.add( backing, EffectsManager.Position.Behind );
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
		return image;
	}
	@Override
	public Image getDamagedImage() {
		return damagedImage;
	}

	@Override
	public Image getDeadImage() {
		return deadImage;
	}
	@Override
	public void loadImages()
	{
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



	public void setPerceivedSpriteArea( RectF perceivedSpriteArea2 )
	{
		boolean areaWasNull = staticPerceivedArea == null;

		staticPerceivedArea = perceivedSpriteArea2;


		if( areaWasNull && staticPerceivedArea != null )
			staticPerceivedArea.top = staticPerceivedArea.top + staticPerceivedArea.height()/1.5f;

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
	public Cost getCosts() {
		return cost;
	}



	@NonNull
    @Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}

	@Override
	public Image getIconImage() {
		return image;
	}



	@NonNull
    @Override
	public String toString() {
		return TAG;
	}
	@NonNull
    @Override
	public String getName() {
		return TAG;
	}


}