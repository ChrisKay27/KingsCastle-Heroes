package com.kingscastle.gameElements.livingThings.abilities;




import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Input.TouchEvent;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;

public abstract class Buff implements Ability
{
	private static final String TAG = Buff.class.getSimpleName();

	private boolean dead;
	private LivingThing caster;

    private final LivingThing target;

	private Anim anim;
	private long startTime;
	private long aliveTime=10000;

	private int refreshEvery;
	private long lastRefreshed;

	private boolean casted;


	private EffectsManager em;

    /**
     * Always add a constructor to subclasses with the same signature as this one! Classes are sometimes stored in lists and new instances are created
     * via the getConstructor(LivingThing.class, LivingThing.class).newInstance( lt1, lt2 ) methods.
     */
	public Buff(@NotNull LivingThing caster,@NotNull LivingThing target ){
		this.caster = caster;
		this.target = target;
	}

	@Override
	public final boolean act()
	{
		if( target.isDead() ) {
			die();
			return true;
		}

		if( getLastRefreshed() + getRefreshEvery() < GameTime.getTime() ){
			refresh(em);
			setLastRefreshed(GameTime.getTime());
		}

		if( getStartTime() + getAliveTime() < GameTime.getTime() ){
			die();
			return true;
		}
		return dead;
	}



	void refresh( EffectsManager em ){
	}



	@Override
	public final boolean cast(  MM mm )
	{
		//Log.d( TAG , "Trying to cast a :" + this );
        if( canCastOn(target) ){

            //Log.d( TAG , "Able to cast, trying to add to em" );
			getTarget().getActiveAbilities().add(this);

            em = mm.getEm();
            loadAnimation( mm );
			addAnimationToManager( mm , getAnim() );

			setStartTime(GameTime.getTime());

			return true;
        }
//		if( mm.getTM().getTeam( getTarget().getTeamName() ).getAbm().canCastOn( getTarget() , this ) )
//		{
//			em = mm.getEm();
//			//////Log.d( TAG , "Able to cast, trying to add to em" );
//			getTarget().getActiveAbilities().add(this);
//			doBuff();
//			loadAnimation( mm );
//
//			addAnimationToManager( mm , getAnim() );
//
//			setStartTime(GameTime.getTime());
//
//			return true;
//		}
		else
			die();

		return false;
	}


    public boolean canCastOn( LivingThing potentialTarget){
        return isStackable() || !potentialTarget.getActiveAbilities().containsInstanceOf(this);
    }


	protected void addAnimationToManager(  MM mm,  Anim anim2)	{
        mm.getEm().add( anim2 , false );
	}

	@Override
	public final void die()
	{
		dead = true;
		uncast();
		Anim a = getAnim();
		if( a != null )
			a.setOver(true);
        subDie();
	}

    @Override
    public boolean isOver() {
        return isDead();
    }

    public void subDie(){
    }


	@Override
	public boolean cast(@NotNull  MM mm, @NotNull LivingThing target)	{
		return false;
	}

    final void uncast()	{
		getTarget().getActiveAbilities().remove(this);
	}


	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}


	long getStartTime() {
		return startTime;
	}
	void setStartTime(long startTime) {
		this.startTime = startTime;
	}



	long getAliveTime() {
		return aliveTime;
	}
	void setAliveTime(long aliveTime) {
		this.aliveTime = aliveTime;
	}


    @Override
    public void setTarget( @NotNull LivingThing target) {

    }

    @Override
	public LivingThing getTarget() {
		return target;
	}



	public boolean isCasted() {
		return casted;
	}
	public void setCasted(boolean casted) {
		this.casted = casted;
	}



	@Override
	public final Anim getAnim() {
		return anim;
	}
	void setAnim(@NotNull Anim anim) {
		this.anim = anim;
	}

    @NotNull
    public abstract Class<? extends Anim> getAnimClass();




	int getRefreshEvery() {
		return refreshEvery;
	}
	void setRefreshEvery(int refreshEvery) {
		this.refreshEvery = refreshEvery;
	}




	long getLastRefreshed() {
		return lastRefreshed;
	}
	void setLastRefreshed(long lastRefreshed) {
		this.lastRefreshed = lastRefreshed;
	}





	@Override
	public LivingThing getCaster() {
		return caster;
	}
	@Override
	public void setCaster( LivingThing caster) {
		this.caster = caster;
	}



	@Override
	public void saveYourSelf( BufferedWriter b) {

	}

	protected void loadAnimation(@NotNull MM mm ) {
	}


	@Override
	public boolean isStackable() {
		return false;
	}
	@Override
	public boolean analyseTouchEvent(@NotNull TouchEvent event) {
		return false;
	}

	@Override
	public void setTeam(@NotNull Teams teamName) {
	}


}
