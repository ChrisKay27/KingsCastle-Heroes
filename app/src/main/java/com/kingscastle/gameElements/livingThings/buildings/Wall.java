package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;


import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

public class Wall extends Building
{
	private static final String TAG = "Wall";

	public static final Buildings name = Buildings.Wall;

	private static  RectF staticPerceivedArea; // this includes the offset from the mapLocation

	private static Image image = Assets.loadImage(R.drawable.wall),deadImage;

	private static Cost cost = new Cost( 5 , 0 , 0 , 0 );


    private static final Attributes STATIC_ATTRIBUTES;


    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES;   }

	private RectF perceivedArea;

	static
	{
		STATIC_ATTRIBUTES = new Attributes();
		STATIC_ATTRIBUTES.setRequiresAge(Age.STONE);
		STATIC_ATTRIBUTES.setRequiresTcLvl(4);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth(250);
		STATIC_ATTRIBUTES.setHealth(250); STATIC_ATTRIBUTES.setdHealthLvl(30);
		STATIC_ATTRIBUTES.setHpRegenAmount(2);
		STATIC_ATTRIBUTES.setRegenRate( 4000 );

		staticPerceivedArea = Rpg.guardTowerArea;
	}

	{
		deadImage = Assets.genericDestroyedBuilding;
	}

	public Wall(){
		super( name );
	}


	public Wall(vector v, Teams t)
	{
		super( name , t );

		setLoc( v );
		setTeam( t );

		setBuildingsName( name );
		loadImages();

		loadPerceivedArea();
		setAttributes();
	}




	@Override
	protected void addAnimationToEm( Anim a, boolean sorted,  EffectsManager em)
	{
		em.add( a , true);
		backing.setSize(Backing.SMALL);
		em.add( backing, EffectsManager.Position.Behind );
	}



	@Override
	public void setSelected ( boolean b )
	{
		super.setSelected( b );
	}








	@Override
	public Image getDeadImage()
	{
		loadImages();
		return deadImage;
	}

	@Override
	public void loadImages(){
	}

	public Image getImage(){
		return image;
	}


	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		loadPerceivedArea();

		if( perceivedArea == null )
			perceivedArea = staticPerceivedArea;

		return perceivedArea;
	}



	public void setPerceivedSpriteArea( RectF perceivedSpriteArea2 ){
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




	@Override
	public Cost getCosts() {
		return cost;
	}

	public static void setCost(Cost cost) {
		Wall.cost = cost;
	}


    @Override
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}



    @Override
	public String toString() {
		return TAG;
	}


    @Override
	public String getName() {
		return TAG;
	}





}
