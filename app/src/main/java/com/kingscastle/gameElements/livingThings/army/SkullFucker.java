package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.heroes.R;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedRangedSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.Bow;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class SkullFucker extends AdvancedRangedSoldier
{

	private static Image[] staticImages;
	private static ImageFormatInfo imageFormatInfo;

	@NonNull
    private static final AttackerQualities staticAttackerQualities; @NonNull
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    private static final LivingQualities staticLivingQualities; @NonNull
                                                                @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	@Nullable
    private static Cost cost = null;

	static
	{
		setImageFormatInfo(new ImageFormatInfo(R.drawable.skull_fucque_red , 0, 0 ,0 , 1 , 1 ));

		float dp = Rpg.getDp();

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(10000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp);
		staticAttackerQualities.setDamage( 10 );  staticAttackerQualities.setdDamageAge( 3 ); staticAttackerQualities.setdDamageLvl( 1 ); //20);
		staticAttackerQualities.setROF(600);

		staticLivingQualities = new LivingQualities();  staticLivingQualities.setRequiresBLvl(1); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setLevel( 1 ); //20);
		staticLivingQualities.setFullHealth(400);
		staticLivingQualities.setHealth( 100 ); staticLivingQualities.setdHealthAge( 50 ); staticLivingQualities.setdHealthLvl( 10 ); //400);
		staticLivingQualities.setFullMana(200);
		staticLivingQualities.setMana(200);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(500);
		staticLivingQualities.setSpeed(3f * dp);

	}

	{
		setAQ(new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
	}


	public SkullFucker(@NonNull vector loc, Teams team)
	{
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public SkullFucker()
	{

	}



	@Override
	public void finalInit( MM mm )
	{
		super.finalInit( mm );
		getAQ().setCurrentAttack( new Bow( mm , this , new Arrow() ) );
		aliveAnim.add(getAQ().getCurrentAttack().getAnimator(),true);
	}








	/**
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null. This method is used to check if the static images are null so they can be loaded.
	 * @return the staticImages
	 */
	@Override
	public Image[] getStaticImages()
	{
		return staticImages;
	}


	/**
	 * @param staticImages2 the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages2) {
		staticImages = staticImages2;
	}







	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return imageFormatInfo;
	}

	public static void setImageFormatInfo(ImageFormatInfo imageFormatInfo) {
		SkullFucker.imageFormatInfo = imageFormatInfo;
	}







	@NonNull
    @Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}




	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
	}


	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
	}

	@Nullable
    @Override
	public Cost getCosts()
	{
		return cost;
	}

	public static void setCost(Cost cost) {
		SkullFucker.cost = cost;
	}
	private static final String TAG = "Ancient Mecha Winged";
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
