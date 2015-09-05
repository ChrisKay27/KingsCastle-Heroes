package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.heroes.R;
import com.kingscastle.Game;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameUtils.Age;


public class StorageDepot extends Building
{

	private static final String TAG = "StorageDepot";
	private static final String NAME = "Storage Depot";

	public static final Buildings name = Buildings.StorageDepot;

	private static RectF staticPerceivedArea; // this includes the offset from the mapLocation


	private static Image image , deadImage;  //, damagedImage ;


	private static final Cost costs = new Cost( 100 , 0 , 100 , 0 );

	@NonNull
    private static final LivingQualities staticLivingQualities;

	@NonNull
    @Override
	protected LivingQualities getStaticLQ() {
		return staticLivingQualities;
	}

	static
	{
		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(2);

		staticLivingQualities.setLevel( 1 ); //1);
		staticLivingQualities.setFullHealth(300);
		staticLivingQualities.setHealth(300);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);

		staticLivingQualities.setdHealthLvl(30);
		staticLivingQualities.setdArmorLvl(1.5f);

		staticPerceivedArea = Rpg.fourByTwoArea;
	}



	public StorageDepot()
	{
		super( name );

		loadImages();
		loadPerceivedArea();
		setBuildingsName(name);
		setAttributes();
	}


	public StorageDepot( Game UseThisConstructorForAllowedBuildings )
	{
		super( name );
	}


	StorageDepot(Buildings name2) {
		super( name2 );
	}


	@Override
	public void loadImages()
	{
		if (image == null)
		{
			int aliveId = R.drawable.lumber_mill;
			image = Assets.loadImage(aliveId);
			//damagedImage = image;
			deadImage = Assets.genericDestroyedBuilding;
		}

	}



	@Override
	public boolean canLevelUp() {
		return false;
	}

	@Override
	public Image getImage()
	{
		if( image == null )
		{
			loadImages();
		}

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

	@NonNull
    @Override
	public Cost getCosts() {
		return costs;
	}


	@Override
	public String toString() {
		return NAME;
	}
	@Override
	public String getName() {
		return NAME;
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

	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2) {
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea() {
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
		staticPerceivedArea = staticPercArea;

	}





	@NonNull
    @Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}





}
