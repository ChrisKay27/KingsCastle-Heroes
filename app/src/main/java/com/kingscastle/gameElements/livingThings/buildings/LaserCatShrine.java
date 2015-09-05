package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.spells.Laser;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class LaserCatShrine extends Shrine
{

	private static final String TAG = "Laser Cat Shrine";

	public static final Buildings name = Buildings.LaserCatShrine;

	private static final Image image     = Assets.loadImage(R.drawable.cat_statue);
	private static final Image deadImage = Assets.loadImage( R.drawable.small_rubble );
	@Nullable
    private static final Image iconImage = null;//Assets.loadImage( R.drawable.cat_statue_icon );

	private static RectF staticPerceivedArea = Rpg.oneByOneArea; // this is only the offset from the mapLocation.

	@NonNull
    private static final AttackerQualities staticAttackerQualities;
	@NonNull
    private static final LivingQualities staticLivingQualities;

	private static final Cost costs = new Cost( 500 , 0 , 500 , 0 );
	@NonNull
    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities;   }


	static
	{

		float dpSquared = Rpg.getDpSquared();
		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared     ( 30000 * dpSquared );
		staticAttackerQualities.setAttackRangeSquared    ( 30000 * dpSquared );
		staticAttackerQualities.setROF( 1000 );
		staticAttackerQualities.setDamage( 14 );

		staticLivingQualities = new LivingQualities();staticLivingQualities.setRequiresAge(Age.IRON); staticLivingQualities.setRequiresTcLvl(14);
		staticLivingQualities.setRangeOfSight( 250 );
		staticLivingQualities.setLevel( 1 ); // 2 );
		staticLivingQualities.setFullHealth( 500 );
		staticLivingQualities.setHealth( 500 );
		staticLivingQualities.setFullMana( 100 );
		staticLivingQualities.setMana( 100 );
		staticLivingQualities.setHpRegenAmount( 2 );
		staticLivingQualities.setRegenRate( 1000 );
		staticLivingQualities.setSpeed( 0 );

		staticAttackerQualities.setdDamageAge(4);
		staticAttackerQualities.setdDamageLvl(1);
		staticAttackerQualities.setdROFAge(-100);
		staticAttackerQualities.setdROFLvl(-20);
		staticAttackerQualities.setdRangeSquaredAge(-3000 * dpSquared);
		staticAttackerQualities.setdRangeSquaredLvl(-100 * dpSquared);
		staticLivingQualities.setAge( Age.STONE );
		staticLivingQualities.setdHealthAge(200);
		staticLivingQualities.setdHealthLvl(40);
		staticLivingQualities.setdRegenRateAge( -100 );
		staticLivingQualities.setdRegenRateLvl(-20);

	}

	{
		setAQ( new AttackerQualities( staticAttackerQualities , getLQ().getBonuses() ) );

		Laser laser = new Laser();
		laser.setCaster( this );
		laser.addOffs( new vector( -Rpg.twoDp , -Rpg.eightDp ));
		laser.addOffs( new vector( Rpg.twoDp , -Rpg.eightDp ));
		getAQ().setCurrentAttack( new SpellAttack(getMM(),this , laser ) ) ;


	}

	public LaserCatShrine()
	{
		super(name);
	}

	public LaserCatShrine( @NonNull vector v , Teams t )
	{
		this();
		setTeam(t);
		setLoc(v);
	}

	@Override
	public boolean act()
	{
		////Log.d( TAG ,"pre act(), target == " + target );
		super.act();
		////Log.d( TAG ,"post act(), target == " + target );
		return isDead();
	}

	@Override
	protected void setupAttack() {

	}

	@Override
	protected void addAnimationToEm(@NonNull Anim a, boolean sorted, @NonNull EffectsManager em)
	{
		em.add( a , true);
		backing.setSize(Backing.TINY);
		em.add( backing, EffectsManager.Position.Behind );
	}
	@Override
	public Image getImage() {
		return image;
	}
	@Override
	public Image getDamagedImage() {
		return image;
	}
	@Override
	public Image getDeadImage() {
		return deadImage;
	}
	@Nullable
    @Override
	public Image getIconImage() {
		return iconImage;
	}

	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
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
	public Cost getCosts(){
		return costs;
	}

	@NonNull
    @Override
	public String getName() {
		return TAG;
	}


	@NonNull
    @Override
	public LivingQualities getNewLivingQualities() {
		return new LivingQualities(staticLivingQualities);
	}



}
