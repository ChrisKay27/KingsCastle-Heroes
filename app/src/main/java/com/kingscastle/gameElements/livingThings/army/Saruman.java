package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;



import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MediumMeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;


public class Saruman extends MediumMeleeSoldier
{

	private static final String TAG = "Saruman";
	private static final String NAME = "";

	//private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;
	//private static ImageFormatInfo imageFormatInfo;



    private static final AttackerQualities staticAttackerQualities;
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }

    private static final Attributes STATIC_ATTRIBUTES;
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 0 , 0 , 0 , 1 );

	static
	{
		float dp = Rpg.getDp();

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared( 0 );

		STATIC_ATTRIBUTES = new Attributes();   STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight(150*dp);
		STATIC_ATTRIBUTES.setHealth(100000);
		STATIC_ATTRIBUTES.setFullHealth(100000);
	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
	}

	public Saruman()
	{
	}


	public Saruman(  vector loc , Teams team )
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
	public void loadAnimation( @NotNull  MM mm )
	{
	}


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
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}


	@Override
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		Saruman.cost = cost;
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
