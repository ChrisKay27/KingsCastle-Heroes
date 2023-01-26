package com.kingscastle.gameElements.livingThings.abilities;

import android.graphics.Color;



import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.HexBubbleAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.Bonuses;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;


public class ArmorBuff extends Buff
{
	private final static String name = "ArmorBuff";

	private final float armorBonus = 30f;

	{
		setAliveTime( 10000 );
		setRefreshEvery( 1000 );
	}

	public ArmorBuff(@NotNull LivingThing caster,@NotNull LivingThing target) {
		super(caster, target);
	}


    @Override
	public Abilities getAbility()				 {				return Abilities.ARMOR_BUFF ; 			}

	@Override
	public void doAbility()
	{
		Bonuses b = getTarget().getLQ().getBonuses();
		b.setArmorBonus( b.getArmorBonus() + armorBonus );
	}

	@Override
	public void undoAbility()
	{
		Bonuses b = getTarget().getLQ().getBonuses();
		b.setArmorBonus( b.getArmorBonus() - armorBonus );
	}

	@Override
	protected void refresh( EffectsManager em )
	{
		getAnim().reset();
	}

	@Override
	public int calculateManaCost(@NotNull  LivingThing aWizard)
	{
		return 0;
	}


	@Override
	public void loadAnimation(  MM mm )
	{
		if( getAnim() == null )
		{
			setAnim( new HexBubbleAnim( getTarget().loc , Color.WHITE ));
			getAnim().setOffs(new vector( Rpg.twoDp , -getTarget().area.height()/4 ));
		}
	}

	@Override
	protected void addAnimationToManager(  MM mm ,  Anim anim2)
	{
		mm.getEm().add( anim2 , EffectsManager.Position.InFront );
	}

    @Override
    public boolean isOver() {
        return isDead();
    }



    @Override
	public String toString() {
		return name;
	}

    public String getName() {
		return name;
	}




    @Override
	public Ability newInstance(@NotNull LivingThing target) {
		return new ArmorBuff(getCaster(),target);
	}


	
    @Override
	public Image getIconImage()
	{
		return null;
	}

	public float getArmorBonus() {
		return armorBonus;
	}



    @NotNull
    @Override
    public Class<? extends Anim> getAnimClass() {
        return HexBubbleAnim.class;
    }




}
