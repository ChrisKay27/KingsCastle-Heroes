package com.kingscastle.teams;





import com.kingscastle.framework.Settings;
import com.kingscastle.framework.WtfException;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.buildings.BuildingManager;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class TeamManager
{

	private static final String TAG = "TeamManager";

	private boolean gameRunning = false;
	private final ReentrantLock gameRunningLock = new ReentrantLock();



    private final ArrayList<Team> teams = new ArrayList<>();

	private boolean loseOnNoTc = true;


    private final MM mm;

	public TeamManager(  MM mm) {
		this.mm = mm;
	}

	public void add(  Team t )
	{
		if( t == null )
			return;
		if( t.getTeamName() == null )
			throw new WtfException("t.getTeamName() == null");

		t.setMM(mm);
		t.createManagers();
		teams.add(t);
	}

	public void remove(  Team t )
	{
		if( t == null )
			return;

		else
			if( teams.remove( t ) )
			{
			}

	}

	public void remove( @NotNull ArrayList<Team> teams )
	{
		this.teams.removeAll( teams );
	}


	public void act(){
	}



    public ArrayList<Team> getTeams()
	{
		return teams;
	}


	public void setTeams( @NotNull ArrayList<Team> team )
	{
        teams.clear();
        teams.addAll(team);
	}


	@NotNull
    public Team getTeam( Teams team )
	{
		for ( Team t : teams )
			if ( t.getTeamName() == team )
				return t;

	    throw new IllegalArgumentException("No team added for " + team );
	}


	public void finalInit()
	{
		//Log.d( TAG , "finalInit() there are " + teams.size() + " teams.");
		for( Team t: teams )
			t.finalInit();
	}





	public void onBuildingDestroyed( Building b) {
		//Log.v( TAG , "onBuildingDestroyed(" + b + ")");
		if( b == null || b.getTeamName() == null )
		{
			//Log.e( TAG , "b == null || b.getTeamName() == null" );
			return;
		}

		Team team = getTeam( b.getTeamName() );
		if( team != null )
			team.onBuildingDestroyed(b);
		else{
			//Log.e( TAG , "onBuildingDestroyed("+b+") did not find team for " + b.getTeamName() );
		}
	}

	public void onHumanoidDestroyed(  LivingThing lt )
	{
		if( lt == null || lt.getTeamName() == null )
		{
			////Log.d( TAG , "lt == null || lt.getTeamName() == null" );
			return;
		}

		Team team = getTeam( lt.getTeamName() );
		if( team != null )
			team.onHumanoidDestroyed(lt);

	}

	public void onHumanoidCreated(  LivingThing lt )
	{
		if( lt == null || lt.getTeamName() == null )
		{
			////Log.d( TAG , "lt == null || lt.getTeamName() == null" );
			return;
		}

		if( !Settings.loadingLevel )
			if( lt.getTeamName() == Teams.BLUE )
			{

			}

		//TODO Level up the soldier appropriately here! (i think)
		//Log.d( TAG , "onHumanoidCreated() finished");
		//Team team = getTeam( lt.getTeamName() );

		//team.getPlayer().getPersonalResources().incPopCurr( lt.getCosts().getPopCost() );
	}




	public boolean isLoseOnNoTc() {
		return loseOnNoTc;
	}

	public void setLoseOnNoTc(boolean loseOnNoTc) {
		this.loseOnNoTc = loseOnNoTc;
	}


	public void reset() {
		loseOnNoTc = true;
	}




    public Player getPlayer(Teams teamName) {
		for( Team t : teams )
			if( t.getTeamName() == teamName )
				return t.getPlayer();

		return null;
	}



    public BuildingManager getBM(Teams team) {
		Team t = getTeam( team );
		if( t == null )
			return null;
		else
			return t.getBm();
	}



	public void resume()
	{
		synchronized( gameRunningLock ){
			gameRunning = true;
		}
		//Log.d( TAG , "startThreadsAndResumeQueues()");
		for( Team t : teams )
		{
			t.startThread();
			if( !Settings.mapEditingMode )
			{
				t.getPlayer().startThread();
				t.getBm().resume();
			}
			else
			{
				//Log.d( TAG , "Map editing mode is on, not starting queues or AI threads.");
			}
		}
	}


	public void pause() {
		synchronized( gameRunningLock ){
			gameRunning = false;
		}
		//Log.d( TAG , "before looping threw teams to pause threads" );
		for( Team t : teams )
		{
			//Log.d( TAG , "t.pauseThread()" );
			t.pause();
		}
		//Log.d( TAG , "after looping through teams to pause threads" );
	}



	public void saveYourSelf(  BufferedWriter b ) throws IOException
	{
		synchronized( gameRunningLock ){
			if( gameRunning )
				throw new IllegalStateException("Trying to save when game is running");
		}

		String s = "<TeamManager>";

		b.write( s , 0 , s.length() );
		b.newLine();

		for( Team t : teams )
			t.saveYourself( b );


		s = "</TeamManager>";

		b.write( s , 0 , s.length() );
		b.newLine();

	}














}
