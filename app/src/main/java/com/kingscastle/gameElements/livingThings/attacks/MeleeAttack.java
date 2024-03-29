package com.kingscastle.gameElements.livingThings.attacks;

import android.graphics.RectF;



import com.kingscastle.effects.SpecialEffects;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.attacks.MeleeAnimator.MeleeTypes;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;


public class MeleeAttack extends Attack
{


    private final AttackAnimator weapon;

	private static final RectF weaponStrikePercArea = new RectF( -8* Rpg.getDp() , -8*Rpg.getDp() , 8*Rpg.getDp() , 8*Rpg.getDp() );


    private final vector checkHitHere;


    private final RectF inThisArea;

	
    private LivingThing possibleVictum;

	private final MeleeTypes weaponType;

	private final CD cd;


	public MeleeAttack(  MM mm ,  Humanoid owner ,  MeleeTypes weaponType , CD cd )
	{
		super( mm, owner );
		this.weaponType = weaponType;
		switch( weaponType )
		{

		default:
			weapon = new MeleeAnimator( owner , weaponType , this );
			break;
		case PickAxe:
			weapon = new PickAxeAnimator( owner , this );
			break;
		case Axe:
			weapon = new AxeAnimator( owner , this );
			break;

		}
		this.cd = cd;
		checkHitHere = new vector();
		inThisArea = new RectF();
	}


	public MeleeAttack(  MM mm,  Humanoid owner , CD cd )
	{
		super(mm, owner);

		if( Math.random() < 0.25 ) {
            this.weaponType = MeleeTypes.LongSword;
            weapon = new MeleeAnimator(owner, weaponType, this);
        }else if( Math.random() < 0.5 ) {
            this.weaponType = MeleeTypes.Sabre;
            weapon = new MeleeAnimator(owner, weaponType, this);
        }else if( Math.random() < 0.75 ) {
            this.weaponType = MeleeTypes.Axe;
            weapon = new AxeAnimator(owner, this);
        }else {
            this.weaponType = MeleeTypes.PickAxe;
            weapon = new PickAxeAnimator(owner, this);
        }

		this.cd = cd;
		inThisArea = new RectF();
		checkHitHere = new vector();
		//ManagerManager.getInstance().getEm().add(weapon,true);
	}



	@Override
	public void attack( vector inDirection ) {
		weapon.attack( inDirection );
		doAttackAt = GameTime.getTime() + weapon.getTimeFromAttackStartTillDoAttack();
	}



	@Override
	public void attackFromHumanoidVector( vector unitVector ) {
		weapon.attackFromHumanoidVector(unitVector);
	}



	@Override
	public boolean attack( LivingThing target )
	{
		possibleVictum = target;
		weapon.attack();
		doAttackAt = GameTime.getTime() + weapon.getTimeFromAttackStartTillDoAttack();
		return true;
	}



	@Override
	public void checkHit(  vector inDirection )
	{
		//System.out.println("MeleeAttack: checkHit() starting");
		if( possibleVictum != null && owner.loc.distanceSquared(possibleVictum.loc) < Rpg.getMeleeAttackRangeSquared() ){
			possibleVictum.takeDamage(owner.getDamage(),owner);
			return;
		}

		if( inDirection != null )
		{
			checkHitHere.set( inDirection );
			checkHitHere.times( Rpg.getMeleeAttackRange()).add( owner.loc );

			//Log.d( "MeleeAttack" , "checkHit(): checkHitHere = " + checkHitHere);

			inThisArea.set( weaponStrikePercArea );

			inThisArea.offset( checkHitHere.x , checkHitHere.y );

			//Log.d( "MeleeAttack" , "inThisArea=" + inThisArea);
			//System.out.println("owner.getTeam()=" + owner.getTeam());

			possibleVictum = cd.checkSingleHit( owner.getTeamName() , inThisArea );


			if( possibleVictum != null )
			{
				possibleVictum.takeDamage( owner.getDamage() , owner );

				if( weapon.wasDrawnThisFrame() )
					playHitSound( weaponType , owner.loc.x , owner.loc.y );
			}
			else
			{
				if( weapon.wasDrawnThisFrame() )
					playMissSound( weaponType , owner.loc.x , owner.loc.y );
			}
		}

		//System.out.println("MeleeAttack: checkHit() ending");
	}




	public void checkHit(  LivingThing target )
	{
		if( target == null )
			return;


		if( owner.loc.distanceSquared( target.loc ) <  owner.getAQ().getAttackRangeSquared() )
			target.takeDamage( owner.getDamage() , owner );
	}




	@Override
	public void removeAnim()
	{
		weapon.setOver( true );
	}




    @Override
	public Anim getAnimator()
	{
		return weapon;
	}




	private long doAttackAt;

	@Override
	public void act()
	{
		if( doAttackAt < GameTime.getTime() )
		{
			doAttack();
			doAttackAt = Long.MAX_VALUE;
		}
	}



	private void doAttack()
	{
		checkHit( weapon.getAttackingInDirectionHumanoidVector() );
	}



	private static void playHitSound(  MeleeTypes weaponType , float x , float y )
	{
		if( weaponType == null )
			return;

		switch( weaponType )
		{
		case Mace:
		case Sabre:
		case LongSword:
			SpecialEffects.playHitSound(x, y);
			return;

		case Hammer:
			SpecialEffects.playHammerSound( x , y );
			return;

		case Axe:
			SpecialEffects.playAxeSound( x , y );
			return;

		case Hoe:
			return;

		case PickAxe:
			SpecialEffects.playPickaxeSound( x , y );
			return;

		default:
			return;

		}


	}




	private static void playMissSound(  MeleeTypes weaponType , float x , float y )
	{
		if( weaponType == null )
			return;

		switch( weaponType )
		{
		case Mace:
		case Sabre:
		case LongSword:
			SpecialEffects.playMissSound( x , y );
			return;

		case Hammer:
			SpecialEffects.playHammerSound( x , y );
			return;

		case Axe:
			SpecialEffects.playAxeSound( x , y );
			return;

		case Hoe:
			return;

		case PickAxe:
			SpecialEffects.playPickaxeSound( x , y );
			return;

		default:
			return;
		}

	}








}
