package com.kingscastle.teams.races;





import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.SoldierType;
import com.kingscastle.gameElements.livingThings.army.HumanArcher;
import com.kingscastle.gameElements.livingThings.army.HumanLongBowMan;
import com.kingscastle.gameElements.livingThings.army.HumanScout;
import com.kingscastle.gameElements.livingThings.army.HumanSoldier;
import com.kingscastle.gameElements.livingThings.army.Knight;
import com.kingscastle.gameElements.livingThings.army.Medic;
import com.kingscastle.gameElements.livingThings.army.Priestess;
import com.kingscastle.gameElements.livingThings.army.Warrior;
import com.kingscastle.gameElements.livingThings.buildings.Barracks;
import com.kingscastle.gameElements.livingThings.buildings.BuildableSoldiers;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.gameElements.livingThings.buildings.Church;
import com.kingscastle.gameElements.livingThings.buildings.GuardTower;
import com.kingscastle.gameElements.livingThings.buildings.StoneTower;
import com.kingscastle.gameElements.livingThings.buildings.StorageDepot;
import com.kingscastle.gameElements.livingThings.buildings.WatchTower;
import com.kingscastle.gameUtils.Age;

public class OrcRace extends Race
{
	private static OrcRace singularity;

	public static OrcRace getInstance()
	{
		if( singularity == null )
		{
			singularity = new OrcRace();
		}

		return singularity;

	}

	private BuildableSoldiers stoneAgeBarracksHumanoids;
	private BuildableSoldiers bronzeAgeBarracksHumanoids;
	private BuildableSoldiers ironAgeBarracksHumanoids;
	private BuildableSoldiers steelAgeBarracksHumanoids;


	private BuildableSoldiers stoneAgeChurchHumanoids;
	private BuildableSoldiers bronzeAgeChurchHumanoids;
	private BuildableSoldiers ironAgeChurchHumanoids;
	private BuildableSoldiers steelAgeChurchHumanoids;



	private BuildableSoldiers stoneAgeTownCenterHumanoids;
	private BuildableSoldiers bronzeAgeTownCenterHumanoids;
	private BuildableSoldiers ironAgeTownCenterHumanoids;
	private BuildableSoldiers steelAgeTownCenterHumanoids;





	{
		Warrior warrior = new Warrior();

		HumanSoldier soldier = new HumanSoldier();
		HumanArcher archer = new HumanArcher();

		HumanLongBowMan armoredArcher = new HumanLongBowMan();
		Knight knight = new Knight();

		stoneAgeBarracksHumanoids = new BuildableSoldiers( warrior );
		bronzeAgeBarracksHumanoids = new BuildableSoldiers( warrior , soldier, archer );
		ironAgeBarracksHumanoids = new BuildableSoldiers( soldier , archer , armoredArcher );
		steelAgeBarracksHumanoids = new BuildableSoldiers( soldier , knight , archer , armoredArcher );

		HumanScout scout = new HumanScout();

		stoneAgeTownCenterHumanoids = new BuildableSoldiers( scout );
		bronzeAgeTownCenterHumanoids = ironAgeTownCenterHumanoids = steelAgeTownCenterHumanoids = stoneAgeTownCenterHumanoids;
		//		bronzeAgeTownCenterHumanoids = new BuildableSoldiers( worker , scout );
		//		ironAgeTownCenterHumanoids = new BuildableSoldiers( worker , scout );
		//		steelAgeTownCenterHumanoids = new BuildableSoldiers( worker , scout );

		Medic medic = new Medic();
		Priestess priestess = new Priestess();

		stoneAgeChurchHumanoids = new BuildableSoldiers( medic );
		bronzeAgeChurchHumanoids = new BuildableSoldiers( medic );
		ironAgeChurchHumanoids = new BuildableSoldiers( medic , priestess );
		steelAgeChurchHumanoids = new BuildableSoldiers( priestess );
	}



	
    @Override
	public SoldierType getMy( GeneralSoldierType soldierType )
	{
		return null;
	}



	
    @Override
	public LivingThing getMyVersionOfA( GeneralSoldierType soldierType )
	{
		return null;
	}



    @Override
	public Buildings getMyRacesBuildingType(  Buildings building )
	{


		switch( building )
		{
		case Barracks:
			return Buildings.DwarfBarracks;

		case Church:
			return Buildings.DwarfMushroomFarm;

		case Farm:
			return Buildings.Farm;


		case WallHorzA:
			return Buildings.WallHorzA;
		case WallHorzB:
			return Buildings.WallHorzB;
		case WallHorzC:
			return Buildings.WallHorzC;



		case WallVertA:
			return Buildings.WallVertA;
		case WallVertB:
			return Buildings.WallVertB;


		case GuardTower:

			return Buildings.GuardTower;



		case PendingBuilding:
			return Buildings.PendingBuilding;


		case StoneTower:
			return Buildings.StoneTower;

		case StorageDepot:
			return Buildings.StorageDepot;

		case TownCenter:
			return Buildings.DwarfTownCenter;



		case WatchTower:
			return Buildings.WatchTower;

		default:
			break;

		}
		return building;
	}



    @Override
	public Building getMyVersionOfA( Buildings building)
	{
		switch( building )
		{
		case Barracks:
			return new Barracks();

		case Church:
			return new Church();

			//
			//		case WallHorzA:
			//			return new WallHorzA();
			//		case WallHorzB:
			//			return new WallHorzB();
			//		case WallHorzC:
			//			return new WallHorzC();
			//
			//
			//		case WallVertA:
			//			return new WallVertA();
			//		case WallVertB:
			//			return new WallVertB();



		case GuardTower:

			return new GuardTower();


		case StoneTower:
			return new StoneTower();

		case StorageDepot:
			return new StorageDepot();

//		case TownCenter:
//			return new TownCenter();



		case WatchTower:
			return new WatchTower();

		default:
			break;

		}
		return new WatchTower();
	}

	
    @Override
	public BuildableSoldiers getHumanoidsFor(  Buildings building ,  Age age )
	{

		switch( building )
		{
		case UndeadBarracks:
		case Barracks:
		case DwarfBarracks:
			return getBarracksHumanoidsFor ( age );

		case DwarfMushroomFarm:
		case UndeadChurch:
		case Church:
			return getChurchHumanoidsFor ( age );


		case DwarfTownCenter:
		case UndeadTownCenter:
		case TownCenter:
			return getTownCenterHumanoidsFor ( age );


		default:
			return null;
		}


	}



	BuildableSoldiers getChurchHumanoidsFor( Age age)
	{
		switch( age )
		{
		default:
		case STONE:
			return getStoneAgeChurchHumanoids();

		case BRONZE:
			return getBronzeAgeChurchHumanoids();

		case IRON:
			return getIronAgeChurchHumanoids();

		case STEEL:
			return getSteelAgeChurchHumanoids();

		}
	}




	BuildableSoldiers getTownCenterHumanoidsFor( Age age)
	{
		switch( age )
		{
		default:
		case STONE:
			return getStoneAgeTownCenterHumanoids();

		case BRONZE:
			return getBronzeAgeTownCenterHumanoids();

		case IRON:
			return getIronAgeTownCenterHumanoids();

		case STEEL:
			return getSteelAgeTownCenterHumanoids();

		}
	}




	BuildableSoldiers getBarracksHumanoidsFor( Age age)
	{
		switch( age )
		{
		default:
		case STONE:
			return getStoneAgeBarracksHumanoids();

		case BRONZE:
			return getBronzeAgeBarracksHumanoids();

		case IRON:
			return getIronAgeBarracksHumanoids();

		case STEEL:
			return getSteelAgeBarracksHumanoids();

		}
	}












	BuildableSoldiers getStoneAgeBarracksHumanoids() {
		return stoneAgeBarracksHumanoids;
	}

	public  void setStoneAgeBarracksHumanoids(BuildableSoldiers stoneAgeBarracksHumanoids) {
		this.stoneAgeBarracksHumanoids = stoneAgeBarracksHumanoids;
	}

	BuildableSoldiers getBronzeAgeBarracksHumanoids() {
		return bronzeAgeBarracksHumanoids;
	}

	public  void setBronzeAgeBarracksHumanoids(BuildableSoldiers bronzeAgeBarracksHumanoids) {
		this.bronzeAgeBarracksHumanoids = bronzeAgeBarracksHumanoids;
	}

	BuildableSoldiers getIronAgeBarracksHumanoids() {
		return ironAgeBarracksHumanoids;
	}

	public  void setIronAgeBarracksHumanoids(BuildableSoldiers ironAgeBarracksHumanoids) {
		this.ironAgeBarracksHumanoids = ironAgeBarracksHumanoids;
	}

	BuildableSoldiers getSteelAgeBarracksHumanoids() {
		return steelAgeBarracksHumanoids;
	}

	public  void setSteelAgeBarracksHumanoids(BuildableSoldiers steelAgeBarracksHumanoids) {
		this.steelAgeBarracksHumanoids = steelAgeBarracksHumanoids;
	}



	BuildableSoldiers getStoneAgeChurchHumanoids() {
		return stoneAgeChurchHumanoids;
	}



	public  void setStoneAgeChurchHumanoids(BuildableSoldiers stoneAgeChurchHumanoids) {
		this.stoneAgeChurchHumanoids = stoneAgeChurchHumanoids;
	}



	BuildableSoldiers getBronzeAgeChurchHumanoids() {
		return bronzeAgeChurchHumanoids;
	}



	public  void setBronzeAgeChurchHumanoids(BuildableSoldiers bronzeAgeChurchHumanoids) {
		this.bronzeAgeChurchHumanoids = bronzeAgeChurchHumanoids;
	}



	BuildableSoldiers getIronAgeChurchHumanoids() {
		return ironAgeChurchHumanoids;
	}



	public  void setIronAgeChurchHumanoids(BuildableSoldiers ironAgeChurchHumanoids) {
		this.ironAgeChurchHumanoids = ironAgeChurchHumanoids;
	}



	BuildableSoldiers getSteelAgeChurchHumanoids() {
		return steelAgeChurchHumanoids;
	}



	public  void setSteelAgeChurchHumanoids(BuildableSoldiers steelAgeChurchHumanoids) {
		this.steelAgeChurchHumanoids = steelAgeChurchHumanoids;
	}



	BuildableSoldiers getStoneAgeTownCenterHumanoids() {
		return stoneAgeTownCenterHumanoids;
	}



	public  void setStoneAgeTownCenterHumanoids(
			BuildableSoldiers stoneAgeTownCenterHumanoids) {
		this.stoneAgeTownCenterHumanoids = stoneAgeTownCenterHumanoids;
	}



	BuildableSoldiers getBronzeAgeTownCenterHumanoids() {
		return bronzeAgeTownCenterHumanoids;
	}



	public  void setBronzeAgeTownCenterHumanoids(
			BuildableSoldiers bronzeAgeTownCenterHumanoids) {
		this.bronzeAgeTownCenterHumanoids = bronzeAgeTownCenterHumanoids;
	}



	BuildableSoldiers getIronAgeTownCenterHumanoids() {
		return ironAgeTownCenterHumanoids;
	}



	public  void setIronAgeTownCenterHumanoids(
			BuildableSoldiers ironAgeTownCenterHumanoids) {
		this.ironAgeTownCenterHumanoids = ironAgeTownCenterHumanoids;
	}



	BuildableSoldiers getSteelAgeTownCenterHumanoids() {
		return steelAgeTownCenterHumanoids;
	}



	public  void setSteelAgeTownCenterHumanoids(
			BuildableSoldiers steelAgeTownCenterHumanoids) {
		this.steelAgeTownCenterHumanoids = steelAgeTownCenterHumanoids;
	}



    @Override
	public Races getRace()
	{
		return Races.ORC;
	}



}
