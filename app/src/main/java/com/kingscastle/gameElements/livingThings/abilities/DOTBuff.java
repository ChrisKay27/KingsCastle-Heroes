package com.kingscastle.gameElements.livingThings.abilities;





import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.FlameZone;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

public class DOTBuff extends Buff
{
	private final static String name = DOTBuff.class.getSimpleName();
	private final static String printableString = "Damage Over Time Buff";

	private int damage  = 10;

	{
		setAliveTime( 10000 );
		setRefreshEvery(2000);
	}

    public DOTBuff(@NotNull LivingThing caster, @NotNull LivingThing target) {
        super(caster, target);
    }

	public DOTBuff(@NotNull LivingThing caster, @NotNull LivingThing target, int damage) {
		super(caster, target);
		this.damage = damage;
	}


    @Override
	public Abilities getAbility()				 {				return Abilities.DOT_BUFF ; 			}


	@Override
	public void doAbility()	{
	}

	@Override
	public void undoAbility()	{
	}


	@Override
	public void refresh(  EffectsManager em )	{
		getTarget().takeDamage(damage, getCaster());
	}

	@Override
	protected void addAnimationToManager(@NotNull MM mm,@NotNull Anim anim2) {
	}

    @Override
    public boolean isOver() {
        return isDead();
    }


    @Override
	public int calculateManaCost(@NotNull  LivingThing aWizard)	{
		return 0;
	}



	@Override
	public String toString() {
		return printableString;
	}

    public String getName() {
		return name;
	}




	@Override
	public Ability newInstance(@NotNull LivingThing target)	{
		return new DOTBuff(getCaster(),target,damage);
	}




	
    @Override
	public Image getIconImage()	{
		return null;
	}


	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}


    @NotNull
    @Override
    public Class<? extends Anim> getAnimClass() {
        return FlameZone.class;
    }
}
