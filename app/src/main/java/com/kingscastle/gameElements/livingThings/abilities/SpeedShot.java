package com.kingscastle.gameElements.livingThings.abilities;





import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.SpeedShotAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.Bonuses;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

public class SpeedShot extends Buff
{
	private static final String name = "SpeedShot";
	private final int ROABonus;


	private static Image iconImage;

	{
		setAliveTime( 10000 );
	}

	/**
	 * @param ROABonus negative value in ms to reduce attack period ex -200 ms
	 */
	public SpeedShot( LivingThing caster,  LivingThing target , int ROABonus ){
		super(caster,target);
		this.ROABonus = ROABonus;
	}


	@Override
	public void doAbility()
	{
		//////Log.d( name , "SpeedShot.doAbility() ");
		Bonuses b = getTarget().getLQ().getBonuses();
		b.setROABonus( b.getROABonus() + ROABonus );
	}

	@Override
	public void undoAbility()
	{
		//////Log.d( name , "SpeedShot.undoAbility() ");
		Bonuses b = getTarget().getLQ().getBonuses();
		b.setROABonus( b.getROABonus() - ROABonus );
	}



	@Override
	public int calculateManaCost(@NotNull  LivingThing aWizard)
	{
		return 0;
	}


    @Override
    public boolean isOver() {
        return isDead();
    }

    @Override
	public void loadAnimation(  MM mm )
	{
		if( getAnim() == null )
		{
			setAnim( new SpeedShotAnim( getTarget().loc ));
			getAnim().setOffs(new vector(0,getTarget().area.height()/2));
			getAnim().setLooping(true);
		}
	}




    @Override
	public String toString() {
		return name;
	}

    public String getName() {
		return name;
	}



    @Override
	public Ability newInstance(@NotNull LivingThing target)	{
		return new SpeedShot(getCaster(),target,ROABonus);
	}


    @Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.multishot_icon);
		}
		return null;
	}

	public int getAttackRateBonus()
	{
		return ROABonus;
	}




    @Override
	public Abilities getAbility()				 {				return Abilities.SPEEDSHOT ; 			}


    @NotNull
    @Override
    public Class<? extends Anim> getAnimClass() {
        return SpeedShotAnim.class;
    }


}
