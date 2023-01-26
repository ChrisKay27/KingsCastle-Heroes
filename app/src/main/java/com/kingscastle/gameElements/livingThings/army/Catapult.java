package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;


import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.framework.Rpg.Direction;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Animator;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.LookDirectionFinder;
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedRangedSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.ProjectileAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Projectile;
import com.kingscastle.gameElements.projectiles.Stone;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Team;
import com.kingscastle.teams.Teams;
import com.kingscastle.teams.races.Races;

import org.jetbrains.annotations.NotNull;


public class Catapult extends AdvancedRangedSoldier
{
	private static final String TAG = "Catapult";
	private static final String NAME = TAG;

	public static Image[] northImages;
	public static Image[] eastImages ;
	public static Image[] southImages;
	public static Image[] westImages ;


    public static final Image[] movingImages;
	public static final long animsFramePeriod = 20;

	private static ImageFormatInfo imageFormatInfo;

	//private static final int imageId = R.drawable.catapult;


    private static final Attributes STATIC_ATTRIBUTES;
                                                                  @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

    private static final AttackerQualities staticAttackerQualities;
                                                                    @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }

	private static Cost cost = new Cost( 2000 , 2000 , 2000 , 3 );


	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo(0 , 0,
				0 , 0 , 4 , 2);

		northImages = new Image[12];
		eastImages  = new Image[12];
		southImages = new Image[12];
		westImages  = new Image[12];


		northImages = Assets.loadAnimationImages(R.drawable.catapult_north, 12).toArray(northImages);
		eastImages  = Assets.loadAnimationImages( R.drawable.catapult_east  , 12 ).toArray(eastImages);
		southImages = Assets.loadAnimationImages( R.drawable.catapult_south , 12 ).toArray(southImages);
		westImages  = Assets.loadAnimationImages( R.drawable.catapult_west  , 12 ).toArray(westImages);


		movingImages = new Image[12];

		int i = 0;
		for( ; i < 3 ; ++i )
			movingImages[i] = southImages[0];

		for( ; i < 6 ; ++i )
			movingImages[i] = westImages[0];

		for( ; i < 9 ; ++i )
			movingImages[i] = eastImages[0];

		for( ; i < 12 ; ++i )
			movingImages[i] = northImages[0];



		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(20000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(30000 * dp * dp);
		staticAttackerQualities.setDamage( 50 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 20 );
		staticAttackerQualities.setROF(2000);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresBLvl(11); STATIC_ATTRIBUTES.setRequiresAge(Age.IRON); STATIC_ATTRIBUTES.setRequiresTcLvl(11);
		STATIC_ATTRIBUTES.setRangeOfSight( 300 );
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth(400);
		STATIC_ATTRIBUTES.setHealth( 400 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 20 );
		STATIC_ATTRIBUTES.setFullMana(0);
		STATIC_ATTRIBUTES.setMana(0);
		STATIC_ATTRIBUTES.setHpRegenAmount( 1 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );
		STATIC_ATTRIBUTES.setArmor( 10 );  STATIC_ATTRIBUTES.setdArmorAge( 0 ); STATIC_ATTRIBUTES.setdArmorLvl( 2 );
		STATIC_ATTRIBUTES.setSpeed( 1.5f * dp );
	}

	private boolean firing = false;

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
	}


	public Catapult(  vector loc, Teams team ){
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public Catapult() {
	}



	@Override
	protected void legsAct(){
		if( !firing )
			super.legsAct();
	}

	@Override
	protected boolean armsAct()
	{
		if( getPathToFollow() == null && destination == null )
		{
			if( super.armsAct() )
			{
				firing = true;
				////Log.d( TAG , "attacking");
				return true;
			}
		}
		return false;
	}

	@Override
	public void loadAnimation( @NotNull  MM mm )
	{
		if ( aliveAnim == null )
		{
			aliveAnim = new Animator( this , getImages() )
			{
				private long changeImageAt;
				@Override
				public Image getImage()
				{
					Image[] images = getImagesForDir( lookDir );
					if( firing )
					{
						if( changeImageAt < GameTime.getTime() )
						{
							////Log.d( TAG , "time to change image, onFrame=" + onFrame );
							++onFrame;

							if( onFrame >= images.length )
							{
								onFrame = 0;
								firing = false;
								////Log.d( TAG , "done attacking animation");
							}

							changeImageAt = GameTime.getTime() + animsFramePeriod;
							lastImageReturned = images[ onFrame ];

							return lastImageReturned;
						}
						else
						{
							return lastImageReturned;
						}
					}
					else
					{
						return images[0];
					}
				}

				private Image[] getImagesForDir(  Direction lookDir )
				{

					switch( lookDir )
					{
					default:
					case E:
						return eastImages;
					case N:
						return northImages;
					case S:
						return southImages;
					case W:
						return westImages;
					}
				}
			};

			mm.getEm().add( aliveAnim , true );

			Races race = Races.HUMAN;
			Team t = mm.getTeam( team );
			if( t != null )
				race = t.getRace().getRace();

			addHealthBarToAnim( race );

		}
		else if( aliveAnim.isOver() )
		{

			aliveAnim.setOver( false );
			healthBar = null;

			Races race = Races.HUMAN;
			Team t = mm.getTeam( team );
			if( t != null )
				race = t.getRace().getRace();

			addHealthBarToAnim( race );
			mm.getEm().add( aliveAnim );
		}
	}



	@Override
	public void finalInit(  MM mm )
	{
		super.finalInit( mm );

		if( hasFinalInited )
			return;

		getAQ().setCurrentAttack( new ProjectileAttack( mm ,this , new Stone() ){
			private long doAttackAt;
			private LivingThing target;
			@Override
			public void act()
			{
				if( doAttackAt < GameTime.getTime() )
				{
					doAttack();
					doAttackAt = Long.MAX_VALUE;
				}
			}
			@Override
			public boolean attack(  LivingThing target )
			{
				doAttackAt = GameTime.getTime() + animsFramePeriod*8;
				this.target = target;
				lookDir = LookDirectionFinder.getDir(loc, target.loc);
				return true;
			}
			private void doAttack()
			{
				Projectile p = proj.newInstance( owner , null , target );
				p.loc.add( 0 , -Rpg.sixTeenDp );
				mm.add( p );
			}

		});

		hasFinalInited = true;
	}




    @Override
	public Image[] getImages()
	{
		return movingImages;
	}


	@Override
	public void loadImages()
	{
	}


	/**
	 * DO NOT LOAD THE IMAGES HERE, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */

    @Override
	public Image[] getStaticImages() {
		return movingImages;
	}

	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {
	}




	@Override
	public ImageFormatInfo getImageFormatInfo(){
		return imageFormatInfo;
	}

	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo2){
		imageFormatInfo=imageFormatInfo2;
	}



	@Override
	public RectF getStaticPerceivedArea(){
		return Rpg.getNormalPerceivedArea();
	}



	@Override
	public void setStaticPerceivedArea(RectF staticPercArea2) {

	}





    @Override
	public Attributes getNewLivingQualities()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}


	@Override
	public Cost getCosts()
	{
		return cost;
	}

	public static void setCost(Cost cost) {
		Catapult.cost = cost;
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
