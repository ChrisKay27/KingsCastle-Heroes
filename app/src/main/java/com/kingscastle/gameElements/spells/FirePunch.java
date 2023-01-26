package com.kingscastle.gameElements.spells;

import android.graphics.RectF;


import com.kingscastle.effects.animations.FireBallAnim;
import com.kingscastle.effects.animations.FireHitAnim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.heroes.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;





public class FirePunch extends ProjectileSpell {

	private static RectF staticPerceivedArea;
	private static Image iconImage;
	private static final float staticSpeed=10* Rpg.getDp();
	private static final float staticRangeSquared = 40000*Rpg.getDp()*Rpg.getDp(); //200*200 = 40000

    private static final ArrayList<vector> offsets; // 0=N 1=E 2=S 3=W

	static{
		float dp = Rpg.getDp();
		offsets = new ArrayList<>();
		offsets.add(new vector(1*dp,-5*dp));
		offsets.add(new vector(-2*dp,3*dp));
		offsets.add(new vector(-1*dp,5*dp));
		offsets.add(new vector(0,3*dp));
	}


	private Rpg.Direction dir;
	private final Humanoid hCaster;



    @Override
	public Abilities getAbility()				 {				return Abilities.FIREPUNCH ; 			}


    public FirePunch(Humanoid caster){
        hCaster = caster;
    }


	@Override
	public boolean cast(  MM mm )
	{
		super.cast(mm);

		setLoc(getCaster().loc);

		LivingThing caster = getCaster();
		if( caster instanceof Humanoid)
		{
			Humanoid hCaster = (Humanoid) caster;
			hCaster.setLookDirection(dir);
			hCaster.lockLookDirection();
			hCaster.getLegs().setLastMoved(GameTime.getTime());
		}

		if(getDamage() == 0 )
			setDamage( calculateDamage() );

		loadAnimation();
		vector offs;

		switch(dir)
		{
		case E:
			offs = offsets.get(1);
			break;
		case N:
			offs = offsets.get(0);
			break;
		case S:
			offs = offsets.get(2);
			break;
		default:
		case W:
			offs = offsets.get(3);
			break;

		}

		getAnim().setOffs(offs);

		getAnim().restart();
		mm.getEm().add(getAnim(),true);

		return true;

	}



	@Override
	public int calculateDamage()	{
		return 30 + getCaster().getLQ().getLevel() * 3;
	}

	@Override
	public int calculateManaCost( @NotNull  LivingThing aWizard)	{
		return 20 + aWizard.getLQ().getLevel() * 3;
	}




	@Override
	public boolean act()
	{
		super.act();

		hCaster.getLegs().setLastMoved( GameTime.getTime() );

		return isDead();
	}



	@Override
	public void die()
	{
		hCaster.unlockLookDirection();

		super.die();
		getMM().getEm().add(new FireHitAnim(loc),true);
	}


	@Override
	public RectF getPerceivedArea()
	{
		if (staticPerceivedArea == null)
		{
			float sizeDiv2 = 2  * Rpg.getDp();
			staticPerceivedArea = new RectF(-sizeDiv2, -sizeDiv2, sizeDiv2, sizeDiv2);
		}
		return staticPerceivedArea;
	}


	@Override
	public void loadAnimation( vector unit)
	{
		if( getAnim() == null )	{
			dir = vector.getDirection4(unit);
			setAnim( new FireBallAnim(loc, vector.getDirection8(unit).getDir()) );
		}

	}


	@Override
	public void loadAnimation()	{
		if( getAnim() == null )
			if( getVelocity() != null )
				loadAnimation( getVelocity().getHumanoidVector() );
	}


	@Override
	public boolean hitsOnlyOneThing()	{
		return true;
	}




    @Override
	public String toString()	{
		return "Fire Punch";
	}



    @Override
	public String getName()	{
		return "FirePunch";
	}



	@Override
	public float getStaticRangeSquared()	{
		return staticRangeSquared;
	}

	@Override
	public float getStaticSpeed()
	{
		return staticSpeed;
	}




    @Override
	public Spell newInstance(){
		return new FirePunch(hCaster);
	}


	@Override
	public void uncast(){
		if( hCaster != null )
			hCaster.unlockLookDirection();
	}



	@Override
	public Image getIconImage() {
		if( iconImage == null )
			iconImage = Assets.loadImage(R.drawable.wall_of_fire_icon);
		return iconImage;
	}

}
