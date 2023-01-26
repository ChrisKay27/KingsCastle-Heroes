package com.kingscastle.gameElements.livingThings.abilities;





import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.AuraAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

public class HotBuff extends Buff
{
	private final static String name = "HotBuff";
	private final static String printableString = "Healing Buff";


	private static Image iconImage;
	private int healAmount  = 10;

	{
		setAliveTime( 10000 );
		setRefreshEvery(1000);
	}

	public HotBuff(@NotNull LivingThing caster, @NotNull LivingThing target) {
		super(caster, target);
	}



    @Override
	public Abilities getAbility()				 {				return Abilities.HOT_BUFF ; 			}

	@Override
	public void doAbility()	{

	}

	@Override
	public void undoAbility()	{

	}

	@Override
	public void refresh(  EffectsManager em )
	{
		getTarget().takeHealing(healAmount);
	}


	@Override
	public int calculateManaCost(@NotNull  LivingThing aWizard)	{
		return 0;
	}



	@Override
	public void loadAnimation(  MM mm )	{
		if( getAnim() == null )
		{
			setAnim( new AuraAnim( getTarget().loc ));
			getAnim().setOffs(new vector(0,-getTarget().area.height()/3));
			getAnim().setLooping(true);
		}
	}

	@Override
	protected void addAnimationToManager(  MM mm ,  Anim anim2)	{
		mm.getEm().add( anim2 , EffectsManager.Position.InFront );
	}

    @Override
    public boolean isOver() {
        return isDead();
    }



    @Override
	public String toString() {
		return printableString;
	}

    public String getName() {
		return name;
	}





    @Override
	public Ability newInstance(@NotNull LivingThing target) {
        HotBuff hb = new HotBuff(getCaster(),target);
        hb.setHealAmount(healAmount);
        return hb;
	}



	
    @Override
	public Image getIconImage()
	{
		if( iconImage == null ) {
			//iconImage = Assets.loadImage(R.drawable.multishot_icon);
		}
		return null;
	}



	public int getHealingPerSecond()
	{
		return healAmount;
	}
	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}


    @NotNull
    @Override
    public Class<? extends Anim> getAnimClass() {
        return AuraAnim.class;
    }
}
