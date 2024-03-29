package com.kingscastle.gameElements.spells;

import android.graphics.RectF;



import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.QuickBurstAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;


public class PoisonSpell extends InstantSpell{

	private static final String toString = "Poison Spell";
	private static final String TAG = "PoisonSpell";

	private static RectF staticPerceivedArea;

	private static Image iconImage;

	private int damageMinusPlusOrMinus = 15;
	private int extra = 5;


	public PoisonSpell(){}


	public PoisonSpell(int damage_, int plusOrMinus){
		damageMinusPlusOrMinus = damage_ - plusOrMinus;
		extra = 2*plusOrMinus;

	}


	@Override
	public int calculateDamage(){
		return damageMinusPlusOrMinus + (int)(Math.random()*extra);
	}


	@Override
	public int calculateManaCost(@NotNull  LivingThing aWizard)
	{
		return 0;
		//return 20 + aWizard.getLQ().getLevel() * 3;
	}




	@Override
	public boolean hitsOnlyOneThing() {
		return true;
	}





    @Override
	public String toString() {
		return toString ;
	}



    @Override
	public String getName() {
		return TAG;
	}



	@Override
	public void uncast() {
	}

	@Override
	public boolean cast(  MM mm )
	{
		super.cast(mm);
		//		area.set(getStaticPerceivedArea());
		//		area.offset( loc.x , loc.y );

		if( mm.getSdac().stillDraw(loc) ){
			loadAnimation();
			mm.getEm().add( getAnim() , EffectsManager.Position.InFront );
		}

		//boolean added = ManagerManager.getInstance().getEm().add( getAnim() , EffectsManager.Position.InFront );
		////Log.d( TAG , "Casting, added to Em? " + added );
		GameElement ge = checkSingleHit( getTeamName() );
		if( ge instanceof LivingThing )
			hitCreature( (LivingThing) ge );
		////Log.d( TAG , "hit thing: " + ge );
		return true;
	}



    GameElement checkSingleHit( Teams teams)
	{
		return cd.checkSingleHit( teams , area , false );
	}



    @Override
	public Abilities getAbility()				 {				return Abilities.TEMPORAL_SHOCK ; 			}



	void hitCreature(  LivingThing lt)	{
		if( lt == null )
			return;
		else
			lt.takeDamage( getDamage() , caster );
	}

	@Override
	public void loadAnimation()	{
		setAnim( new QuickBurstAnim( getTarget().loc ));
	}




    @Override
	public Spell newInstance()
	{
		return new PoisonSpell();
	}



	@Override
	public RectF getStaticPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{
			int sizeDiv2 = (int) (Rpg.getDp()*15);
			staticPerceivedArea = new RectF(-sizeDiv2,-sizeDiv2,sizeDiv2,sizeDiv2);
		}
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
		staticPerceivedArea = staticPercArea;
	}


	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage(R.drawable.lightning_icon);
		}

		return iconImage;
	}



}
