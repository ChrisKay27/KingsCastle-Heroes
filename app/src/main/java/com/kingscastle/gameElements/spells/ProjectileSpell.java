package com.kingscastle.gameElements.spells;




import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Input;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;


public abstract class ProjectileSpell extends Spell {

	protected static int damageBonus = 0;
	private static final float velocityFramerateBonus = 1.2f ;

	private vector startLoc;
	private vector velocity , force;

	private final vector endPosIfBelow = new vector( Float.MIN_VALUE , Float.MIN_VALUE );
	private final vector endPosIfAbove = new vector( Float.MAX_VALUE , Float.MAX_VALUE );

	protected long lastMoved = GameTime.getTime();
	private float rangeSquared;



	//private final long endFlightTime = GameTime.getTime() + 1000;
	public abstract float getStaticSpeed();
	public abstract float getStaticRangeSquared();

	public abstract void loadAnimation(vector unit);


	@Override
	public boolean act()
	{
		if( isDead() )
			return true;


		//adjustVelocityAndForce(velocity,force);

		loc.x += velocity.x;
		loc.y += velocity.y;

		//lastMoved = GameTime.getTime();


		area.offset( velocity.x , velocity.y );


		if( loc.x < endPosIfBelow.x || loc.y < endPosIfBelow.y )
			die();
		else if( loc.x > endPosIfAbove.x || loc.y > endPosIfAbove.y )
			die();



		doDamage( cd.checkSingleHit( getTeamName() , getArea() , false, false ) );

		return false;
	}



	@Override
	public int calculateDamage()
	{
		if( getCaster() != null )
		{
			Attributes lq = getCaster().getLQ();

			int damage = (int) (( getDamage() + (int) ( Math.random()*10 ) ) * lq.getBonuses().getDamageBonus() );
			//Log.e(getClass().getSimpleName() , "baseDamage= " +baseDamage+ " damage=" + damage + " attributes.getBonuses().getDamageBonus()=" + attributes.getBonuses().getDamageBonus());
			return damage;
		}

		return 0;
	}




	public void calculateFlight( int flightTime )
	{
		float dx = flightTime*velocity.x;
		if( dx < 0 )
			endPosIfBelow.setX( loc.x + dx );
		else
			endPosIfAbove.setX( loc.x + dx );

		float dy = flightTime*velocity.y;
		if( dy < 0 )
			endPosIfBelow.setY( loc.y + dy );

		else
			endPosIfAbove.setY( loc.y + dy );
	}



	void doDamage(  LivingThing lt){
		if( lt != null ){
			die();
			lt.takeDamage(getDamage(), getCaster());
		}
	}


	public void setRange( float  range ){
		rangeSquared=range*range;
	}

	public void setRangeSquared(float f){
		rangeSquared=f;
	}

	public void setVelocity(vector velocity2){
		velocity=velocity2;
		velocity.times( velocityFramerateBonus );
	}

	public vector getVelocity(){
		return velocity;
	}

	/**
	 * Creates a new Vector.
	 * @param startLoc
	 */
	public void setStartLoc(vector startLoc){
		this.startLoc = new vector(startLoc);
	}


	@Override
	public void saveYourSelf(  BufferedWriter b) throws IOException
	{
		int rangeRemaining = (int) (rangeSquared-startLoc.distanceSquared(loc));
		String s = "<"+getName()+" team=\""+ getTeamName() + "\" x=\"" + loc.getIntX() + "\" y=\"" + loc.getIntY() + "\"" +
				" velocityX=\"" + velocity.getIntX() + "\" velocityY=\"" + velocity.getIntY() + "\" damage=\""+ getDamage() + "\" rangeRemaining=\""+ rangeRemaining + "\"/>";
		b.write(s,0,s.length());b.newLine();
	}


	public vector getForce(){
		return force;
	}
	public void setForce(vector force){
		this.force = force;
	}


	@Override
	public boolean analyseTouchEvent(@NotNull Input.TouchEvent event) {
		return false;
	}

	@Override
	public boolean cast(@NotNull  MM mm, @NotNull LivingThing target) {
		return false;
	}


}
