package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;



import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class Mummy extends MeleeSoldier {

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] images = Assets.loadImages( R.drawable.mummy  , 0 , 0 , 1 , 1 );


	
    private static final AttackerQualities staticAttackerQualities; 
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	
    private static final Attributes STATIC_ATTRIBUTES; 
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 4000 , 4000 , 0 , 2 );

	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0  , 0 ,
				0 , 0 , 1 , 1);

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(0);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );
		staticAttackerQualities.setDamage( 60 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 5 );
		staticAttackerQualities.setROF(1000);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresBLvl(10); STATIC_ATTRIBUTES.setRequiresAge(Age.STEEL); STATIC_ATTRIBUTES.setRequiresTcLvl(16);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 800 );
		STATIC_ATTRIBUTES.setHealth( 800 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 30 );
		STATIC_ATTRIBUTES.setFullMana(0);
		STATIC_ATTRIBUTES.setMana(0);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setArmor( 10 );  STATIC_ATTRIBUTES.setdArmorAge( 0 ); STATIC_ATTRIBUTES.setdArmorLvl( 2 );
		STATIC_ATTRIBUTES.setSpeed(2.3f * dp);
	}

	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
	}

	public Mummy( vector loc, Teams team){
		super(team);
		setLoc(loc);

		setTeam(team);
	}


	public Mummy(){
	}


	@Override
	public void finalInit( MM mm )

	{
		super.finalInit(mm);

		if( hasFinalInited )
			return;

		hasFinalInited = true;
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

		return images;

	}

	@Override
	public void loadImages()
	{
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
		Mummy.imageFormatInfo = imageFormatInfo;
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
	public void setStaticPerceivedArea(RectF staticPercArea) {
	}
	
    @Override
	public Attributes getNewLivingQualities() {
		return new Attributes(STATIC_ATTRIBUTES);
	}



	@Override
	public Cost getCosts() {
		return cost;
	}
	public static void setCost(Cost cost) {
		Mummy.cost = cost;
	}



	private static final String TAG = "Knight";

	
    @Override
	public String toString() {
		return TAG;
	}
	
    @Override
	public String getName() {
		return TAG;
	}
}
