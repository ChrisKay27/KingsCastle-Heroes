package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MediumMeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class Saruman extends MediumMeleeSoldier
{

	private static final String TAG = "Saruman";
	private static final String NAME = "";

	//private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;
	//private static ImageFormatInfo imageFormatInfo;


	@NonNull
    private static final AttackerQualities staticAttackerQualities; @NonNull
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@NonNull
    private static final LivingQualities staticLivingQualities; @NonNull
                                                                @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = new Cost( 0 , 0 , 0 , 1 );

	static
	{
		float dp = Rpg.getDp();

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared( 0 );

		staticLivingQualities = new LivingQualities();   staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight(150*dp);
		staticLivingQualities.setHealth(100000);
		staticLivingQualities.setFullHealth(100000);
	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
	}

	public Saruman()
	{
	}


	public Saruman( @NonNull vector loc , Teams team )
	{
		super(team);
		setLoc( loc );

		this.team = team;
	}


	@Override
	public void finalInit( MM mm )
	{

	}

	@Override
	public void loadAnimation( @NonNull MM mm )
	{
	}

	@Nullable
    @Override
	public Image[] getImages()
	{
		return null;
	}

	@Override
	public void loadImages()
	{
	}



	@Override
	public boolean act(){
		return false;
	}

	@Override
	public void die(){

	}

	@Nullable
    @Override
	public ImageFormatInfo getImageFormatInfo()
	{
		return null;
	}



	public void setImageFormatInfo( ImageFormatInfo imageFormatInfo2 )
	{

	}




	@Override
	public RectF getStaticPerceivedArea()
	{
		return Rpg.getNormalPerceivedArea();
	}




	@Override
	public void setStaticPerceivedArea(RectF staticPercArea2) {

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







	@NonNull
    @Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}


	@Override
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		Saruman.cost = cost;
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
