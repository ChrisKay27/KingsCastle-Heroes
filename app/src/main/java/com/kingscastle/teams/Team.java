package com.kingscastle.teams;




import android.util.Log;

import com.kingscastle.heroes.R;
import com.kingscastle.effects.SpecialEffects;
import com.kingscastle.framework.Rpg;
import com.kingscastle.framework.WtfException;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.LevelUpTechnology;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.abilities.AbilityManager;
import com.kingscastle.gameElements.livingThings.army.ArmyManager;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.buildings.BuildingManager;
import com.kingscastle.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.ProjectileManager;
import com.kingscastle.gameElements.spells.SpellManager;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.level.GridUtil;
import com.kingscastle.level.PR;
import com.kingscastle.teams.races.BuildingsLearned;
import com.kingscastle.teams.races.Race;
import com.kingscastle.ui.OnBuildingMovedListener;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Team implements OnBuildingMovedListener
{
	private static final String FIRST = " first.";
	private static final String NO_YOU_MUST_UPGRADE_YOUR_TOWN_CENTER_TO_LEVEL = "No:You must upgrade your town center to level ";
	private static final String NO_YOU_MUST_UPGRADE_YOUR_BARRACKS_TO_LEVEL = "No:You must upgrade your barracks to level ";
	private static final String NO_YOU_MUST_UPGRADE_YOUR_CHURCH_TO_LEVEL = "No:You must upgrade your church to level ";
	private static final String YES = "Yes:";
	private static final String NO_THIS_UNIT_IS_ALREADY_LEVELING_UP = "No:This unit is already leveling up.";
	private static final String NO_THIS_UNIT_HAS_REACHED_THE_MAXIMUM_LEVEL = "No:This unit has reached the maximum level.";
	private static final String TAG = "Team";
	public static final int MAX_LEVEL = Integer.MAX_VALUE;
	private int roundNum = 1;




	@Override
	public void onBuildingMoved(Building b) {

	}

	public void onTechResearchFinished( Building b, LevelUpTechnology levelUpTechnology) {
		b.upgradeLevel();
	}

	public void onTechResearchStarted(Building lt, LevelUpTechnology lut) {

	}

	public SpellManager getSm() {
		return sm;
	}

	public String canPurchase(Object o, Building b) {
		return Buyable.BUYABLE.getMsg();
	}

	public static enum Buyable{

		BUYABLE(YES),
		TO_POOR("No:You do not have the resources to purchase this.\nWould you like to purchase them for "),
		B_TOO_LOW_LVL("No:To purchase this you must upgrade your barracks to level "),
		C_TOO_LOW_LVL("No:To purchase this you must upgrade your church to level "),
		TC_TOO_LOW_LVL("No:To purchase this you must upgrade your town centre to level "),
		AGE_TOO_LOW("No:To purchase this you must advance to the "),
		TOO_MANY_WORKERS("No:You have too many workers already. Advance to the next age to build more."),
		LVL_UP_TC_TO	  ("No:" + Rpg.getGame().getActivity().getString(R.string.you_must_lvl_your_tc_to)),
		CANNOT_BUY_ANYMORE("No:" + Rpg.getGame().getActivity().getString(R.string.you_cannot_buy_anymore));


		private String s;
		private Buyable( String s ){
			this.s=s;
		}
		public String getMsg() {
			return s;
		}
	}




    private final TeamThread thread;

	private final Teams team;

    private final Player player;
	private boolean humanPlayer;



    private final PR pr;


	protected AllowedBuildings abs = new AllowedBuildings();
	//protected AvailableSpells as = new AvailableSpells();

    protected final Race race;

	protected MM mm;

	private ArmyManager armyManager;
	private BuildingManager bm;
	private SpellManager sm;
	private ProjectileManager pm;
	private AbilityManager abm ;



	private boolean hasATc;

	private final Cost reducedTcCosts = new Cost( 0 , 0 , 0 , 0 , 0 );

	private final HashMap<String, Integer> unitsLevels = new HashMap<String, Integer>();
	private final HashMap<String, Boolean> areLevelingUp = new HashMap<String, Boolean>();

	protected int maxWorkersAllowed = Integer.MAX_VALUE;//KingsCastle.rtsVersion ? Integer.MAX_VALUE : 3;
	protected int workerCount = 0;

	private Building barracks;
	private Building church;

	private int tcLevel = 1;
	private int bLevel = 1;
	private int cLevel = 1;


	protected final HashMap<Buildings,Integer> numBuildings = new HashMap<Buildings, Integer>();
	protected final HashMap<Buildings,Integer> numBuildingsAllowed = new HashMap<Buildings, Integer>();



	protected Team( Teams team ,  Player player,  Race race, GridUtil gUtil )
	{
		this.team = team;
		this.player = player;
		this.race = race;//races.newInstance();
		player.setTeam( this );
		player.setTeamName( team );

		thread = new TeamThread( this , Rpg.getGame() );

		pr = new PR();
		pr.setGoldAvailable( 1000 );
		pr.setFoodAvailable( 0 );
		pr.setWoodAvailable( 0 );

		addBuildingsLearned( abs , race );

		setTcLevel(1);
	}


	protected void addBuildingsLearned( AllowedBuildings abs,  Race race2){
		BuildingsLearned.addBuildingsLearned(abs, race2, roundNum);
	}



	/**
	 * 
	 * @param p Can be null, will create a AI player.
	 * @return
	 */

    public static HumanTeam getNewHumanInstance(  HumanPlayer p , Race race, GridUtil gUtil )
	{
		Teams teamName = p.getTeamName();

		if( teamName == null )
		{
			teamName = Teams.BLUE;
			p.setTeamName( Teams.BLUE );
		}

		HumanTeam team =  new HumanTeam( teamName , p , race , gUtil );

		p.setTeam( team );

		return team;
	}

	//	public static Team getNewAIInstance( AIPlayer aiPlayer, Races race , GridUtil gUtil ){
	//		Team team = new AITeam( aiPlayer.getTeamName() , aiPlayer, race , gUtil );
	//
	//		return team;
	//	}
	//
	//	public static Team getNewAIInstance( Teams teams, Races race , Difficulty difficulty , GridUtil gUtil ){
	//
	//		return getNewAIInstance(new AIPlayer( teams, difficulty ),race , gUtil );
	//	}

	
    public static Team getNewInstance( Teams teams,  Race race , GridUtil gUtil ) {

		if( teams == null )
			throw new WtfException( "teams == null" );

		Teams teamName = teams;



		Team team = new Team( teamName , new Player() , race , gUtil );

		return team;
	}







	public void act()
	{
		armyManager.act();
		abm.act();
		bm.act();
		pm.act();
		sm.act();

		player.act();
	}


	public void nullify()
	{
		if( thread != null ) {
			thread.pause();
		}
	}


	public Cost getAdjustedCosts(  Buildings buildingsName , Cost defaultCost )
	{
		if( !hasATc )
		{
			switch( buildingsName )
			{
			case DwarfTownCenter:
			case UndeadTownCenter:
			case TownCenter:
				return reducedTcCosts;
			default:
				break;
			}
		}
		return defaultCost;
	}





	public void startThread(){
		thread.resume();
	}
	public void pauseThread(){
		thread.pause();
	}

	public void pause() {
		pauseThread();
		//////Log.d( TAG , "t.getPlayer().pauseThread()" );

		player.pauseThread();
		//////Log.d( TAG , "t.getBm().pauseBuildQueues()" );
		bm.pause();
		armyManager.pause();
	}








	public void finalInit()
	{
		////Log.d( TAG , team + "'s finalInit()");
		armyManager.finalInit(mm);
		//////Log.d( TAG , "armyManager.finalInit() done");
		bm.finalInit(mm);
		//////Log.d( TAG , "bm.finalInit() done");
	}







	protected int wps = 0, fps = 0, gps = 0, buildingWorkers = 0;

	public void saveYourself(  BufferedWriter b ) throws IOException
	{
		String s;

		Player p = getPlayer();
		boolean aicontrolled = false;//(p instanceof AIPlayer);

		String difficulty;

		//		if( p instanceof AIPlayer )
		//			difficulty = ( (AIPlayer) p ).getDifficulty().toString();
		//		else
		difficulty = "";

		String race = "";
		if( p != null )
			race = p.getRace().getRace().toString();

		String type;
		if( p instanceof HumanPlayer )
			type = "H";
		//		else if( p instanceof AIPlayer )
		//			type = "A";
		else// if( p instanceof Player )
			type = "P";




		s = "<Team name=\"" + getTeamName() + "\" t=\""+type+"\" aicontrolled=\"" + aicontrolled + "\" race=\"" + race + "\" difficulty=\"" + difficulty
				+ "\" tclvl=\""+tcLevel+"\" ww=\""+wps+"\" fw=\""+fps+"\" gw=\""+gps+"\" bw=\""+buildingWorkers+"\" >";
		b.write( s , 0 , s.length() );
		b.newLine();
		////Log.d( TAG , s);

		getPlayer().saveYourSelf( b );


		getArmyManager().saveYourSelf( b );
		getBm().saveYourSelf( b );

		//t.getPm().saveYourSelf( bw );
		//t.getSm().saveYourSelf( bw );

		s = "</Team>";

		b.write( s , 0 , s.length() );
		b.newLine();
	}


	public boolean canAfford( Cost cost) {
		if( cost == null ) return true;
		return pr.canAfford(cost);
	}
	public boolean canAffordIgnorePop( Cost cost) {
		if( cost == null ) return true;
		return pr.canAffordIgnorePop(cost);
	}



	public void onHumanoidPurchased( Building fromHere ,  LivingThing lt ){
		if( lt == null ){
			Log.e(TAG, team + "_Team.onHumanoidPurchased(" + lt + ")");
			return;
		}
	}

	public void onHumanoidCreated(  LivingThing lt ){
		if( lt == null ){
			Log.e( TAG , team+"_Team.onHumanoidCreated("+lt+")");
			return;
		}
		//Log.d( TAG , "onHumanoidCreated( "+lt+" )");
	}


	public void onHumanoidDestroyed(  LivingThing lt ){
		if( lt == null ){
			Log.e( TAG , team+"_Team.onHumanoidDestroyed("+lt+")");
			return;
		}

		synchronized( udls ){
			for( OnHumanoidDestroyedListener udl : udls )
				udl.onHumanoidDestroyed(lt);
		}
	}









	public void onBuildingAddedToMap(  Building b ){
		if( b == null ){
			Log.e( TAG , "onBuildingAddedToMap(null)");
			return;
		}

	}

	public void onBuildingDestroyed( Building b ){

		synchronized( bdls ){
			for( OnBuildingDestroyedListener bdl : bdls )
				bdl.onBuildingDestroyed(b);
		}
	}

	public void onBuildingCompleted(  Building b ){

		vector loc = b.loc;
		float x = loc.x;
		float y = loc.y;
		SpecialEffects.createGroundPounder(mm.getEm(), x, y);

		synchronized( bcls ){
			for( OnBuildingCompleteListener bcl : bcls )
				bcl.onBuildingComplete(b);
		}
	}



	public void sellThisBuilding( Building b) {
		if( b == null ) return;
		if( b.getTeamName() != team ) return;
		if( b.isDead() ) return;

		Cost cost = new Cost( b.getCosts() );
		cost.reduceByPerc(0.75);
		pr.refund( cost );
		b.die();

		if( b.isSelected() )
			mm.getUI().setUnSelected( b );
	}

	public boolean isUpgrading(Building selB) {
		return false;
	}



	public Player getPlayer() {
		return player;
	}

	public Race getRace(){
		return player.getRace();
	}



	public Teams getTeamName()
	{
		return team;
	}





    public PR getPR() {
		return pr;
	}



	public int getTcLevel() {
		return tcLevel;
	}
	public void setTcLevel(int tcLevel) {
		this.tcLevel = tcLevel;
	}




	public Building getBarracks() {
		return barracks;
	}
	public void setBarracks(Building barracks) {
		this.barracks = barracks;
	}





	public void setMM(MM mm) {
		this.mm = mm;
	}
	public void createManagers(){
		if( mm == null ) throw new IllegalStateException("mm not set, Managers require the mm");
		armyManager = new ArmyManager( mm );

		bm = new BuildingManager( mm );

		pm = new ProjectileManager( team , mm );
		sm = new SpellManager( mm );
		abm = new AbilityManager( mm );
	}

	public ArmyManager getArmyManager() {
		return armyManager;
	}
	public void setArmyManager(ArmyManager armyManager) {
		this.armyManager = armyManager;
	}
	public BuildingManager getBm()
	{
		return bm;
	}
	public void setBm(BuildingManager bm) {
		this.bm = bm;
	}

	public ProjectileManager getPm() {
		return pm;
	}
	public void setPm(ProjectileManager pm) {
		this.pm = pm;
	}
	public AbilityManager getAbm() {
		return abm;
	}
	public void setAbm(AbilityManager abm) {
		this.abm = abm;
	}


	public boolean isHasATc() {
		return hasATc;
	}
	public void setHasATc(boolean hasATc) {
		this.hasATc = hasATc;
	}







	///***  Started Listeners  ***///








	///*** Completion Listeners ***///



	//Building Completed
	private final ArrayList<OnBuildingCompleteListener> bcls = new ArrayList<OnBuildingCompleteListener>();

	public static interface OnBuildingCompleteListener{
		void onBuildingComplete(Building b);
	}

	public void addBcl(OnBuildingCompleteListener bcl)		   		{	synchronized( bcls ){	bcls.add( bcl );				}  	}
	public boolean removeBcl(OnBuildingCompleteListener bcl)		{	synchronized( bcls ){	return bcls.remove( bcl );		}	}





	///*** Destruction Listeners ***///


	//Building Destroyed
	private final ArrayList<OnBuildingDestroyedListener> bdls = new ArrayList<OnBuildingDestroyedListener>();

	public static interface OnBuildingDestroyedListener{
		void onBuildingDestroyed(Building b);
	}

	public void addBdl(OnBuildingDestroyedListener bdl)		        {	synchronized( bdls ){	bdls.add( bdl );			}    }
	public boolean removeBdl(OnBuildingDestroyedListener bdl)		{	synchronized( bdls ){	return bdls.remove( bdl );	}	}




	//Humanoid Destroyed
	private final ArrayList<OnHumanoidDestroyedListener> udls = new ArrayList<OnHumanoidDestroyedListener>();

	public static interface OnHumanoidDestroyedListener{
		void onHumanoidDestroyed(LivingThing lt);
	}

	public void addUdl(OnHumanoidDestroyedListener udl)		        {	synchronized( udls ){	udls.add( udl );			}  	}
	public boolean removeUdl(OnHumanoidDestroyedListener udl)		{	synchronized( udls ){	return udls.remove( udl );	}	}
































}
