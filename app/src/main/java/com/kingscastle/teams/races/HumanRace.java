package com.kingscastle.teams.races;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.SoldierType;
import com.kingscastle.gameElements.livingThings.army.Catapult;
import com.kingscastle.gameElements.livingThings.army.HumanArcher;
import com.kingscastle.gameElements.livingThings.army.HumanArmoredSoldier;
import com.kingscastle.gameElements.livingThings.army.HumanBoresArcher;
import com.kingscastle.gameElements.livingThings.army.HumanElementalWizard;
import com.kingscastle.gameElements.livingThings.army.HumanLongBowMan;
import com.kingscastle.gameElements.livingThings.army.HumanScout;
import com.kingscastle.gameElements.livingThings.army.HumanSoldier;
import com.kingscastle.gameElements.livingThings.army.HumanWizard;
import com.kingscastle.gameElements.livingThings.army.Knight;
import com.kingscastle.gameElements.livingThings.army.Medic;
import com.kingscastle.gameElements.livingThings.army.Priestess;
import com.kingscastle.gameElements.livingThings.army.Warrior;
import com.kingscastle.gameElements.livingThings.army.WhiteWizard;
import com.kingscastle.gameElements.livingThings.buildings.Barracks;
import com.kingscastle.gameElements.livingThings.buildings.BasicHealingShrine;
import com.kingscastle.gameElements.livingThings.buildings.BuildableSoldiers;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.gameElements.livingThings.buildings.Church;
import com.kingscastle.gameElements.livingThings.buildings.GuardTower;
import com.kingscastle.gameElements.livingThings.buildings.HealingShrine;
import com.kingscastle.gameElements.livingThings.buildings.LaserCatShrine;
import com.kingscastle.gameElements.livingThings.buildings.StoneTower;
import com.kingscastle.gameElements.livingThings.buildings.StorageDepot;
import com.kingscastle.gameElements.livingThings.buildings.Wall;
import com.kingscastle.gameElements.livingThings.buildings.WatchTower;
import com.kingscastle.gameUtils.Age;

public class HumanRace extends Race
{
	private static HumanRace singularity;

	public static HumanRace getInstance()
	{
		if( singularity == null )
			singularity = new HumanRace();

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

		Warrior  warrior = new Warrior();
		HumanScout scout = new HumanScout();
		Medic      medic = new Medic();

		HumanSoldier soldier    = new HumanSoldier();
		HumanArcher archer           = new HumanArcher();
		WhiteWizard whiteWizard = new WhiteWizard();


		HumanArmoredSoldier has = new HumanArmoredSoldier();
		HumanElementalWizard hew = new HumanElementalWizard();
		HumanBoresArcher hba     = new HumanBoresArcher();


		HumanLongBowMan armoredArcher = new HumanLongBowMan();
		Knight knight                 = new Knight();
		Catapult catapult             = new Catapult();
		Priestess priestess           = new Priestess();
		HumanWizard galdolf           = new HumanWizard();


		//		stoneAgeBarracksHumanoids  = new BuildableSoldiers( warrior , scout );
		//		bronzeAgeBarracksHumanoids = new BuildableSoldiers( warrior , scout , soldier , archer );
		//		ironAgeBarracksHumanoids   = new BuildableSoldiers( warrior , scout , soldier , archer , has , hba );
		steelAgeBarracksHumanoids  = new BuildableSoldiers( warrior , scout , soldier , archer , has , hba , catapult , knight , armoredArcher );
		stoneAgeBarracksHumanoids = bronzeAgeBarracksHumanoids = ironAgeBarracksHumanoids = steelAgeBarracksHumanoids;

		stoneAgeTownCenterHumanoids = new BuildableSoldiers( warrior );
		bronzeAgeTownCenterHumanoids = ironAgeTownCenterHumanoids = steelAgeTownCenterHumanoids = stoneAgeTownCenterHumanoids;
		//bronzeAgeTownCenterHumanoids = new BuildableSoldiers( worker , scout );
		//ironAgeTownCenterHumanoids = new BuildableSoldiers( worker , scout );
		//steelAgeTownCenterHumanoids = new BuildableSoldiers( worker , scout );


		//		stoneAgeChurchHumanoids  = new BuildableSoldiers( medic );
		//		bronzeAgeChurchHumanoids = new BuildableSoldiers( medic , whiteWizard );
		//		ironAgeChurchHumanoids   = new BuildableSoldiers( medic , whiteWizard , hew );
		steelAgeChurchHumanoids  = new BuildableSoldiers( medic , whiteWizard , hew , priestess , galdolf );
		stoneAgeChurchHumanoids = bronzeAgeChurchHumanoids = ironAgeChurchHumanoids = steelAgeChurchHumanoids;
	}



	@Nullable
    @Override
	public SoldierType getMy( @NonNull GeneralSoldierType soldierType )
	{

		switch( soldierType )
		{


		case BASIC_HEALER:
			return SoldierType.MEDIC;
		case ADVANCED_HEALER:
			return SoldierType.PRIESTESS;



		case BASIC_MELEE_SOLDIER:
			return SoldierType.WARRIOR;
		case MEDIUM_MELEE_SOLDIER:
			return SoldierType.SOLDIER;
		case UPPER_MELEE_SOLDIER:
			return SoldierType.KNIGHT;
		case ADVANCED_MELEE_SOLDIER:
			return SoldierType.KNIGHT;



		case BASIC_RANGED_SOLDIER:
			return SoldierType.SCOUT;
		case MEDIUM_RANGED_SOLDIER:
			return SoldierType.ARCHER;
		case ADVANCED_RANGED_SOLDIER:
			return SoldierType.LONGBOWMAN;




		case UPPER_MAGE_SOLDIER:
			return SoldierType.WIZARD;
		case ADVANCED_MAGE_SOLDIER:
			return SoldierType.WIZARD;

		case CATAPULT:
			return SoldierType.CATAPULT;

		default:
			break;

		}
		return null;
	}



	@Nullable
    @Override
	public LivingThing getMyVersionOfA( @NonNull GeneralSoldierType soldierType )
	{
		switch( soldierType )
		{

		case BASIC_HEALER:
			return new Medic();
		case ADVANCED_HEALER:
			return new Priestess();



		case BASIC_MELEE_SOLDIER:
			return new Warrior();
		case MEDIUM_MELEE_SOLDIER:
			return new HumanSoldier();
		case UPPER_MELEE_SOLDIER:
			return new HumanArmoredSoldier();
		case ADVANCED_MELEE_SOLDIER:
			return new Knight();



		case BASIC_RANGED_SOLDIER:
			return new HumanScout();
		case MEDIUM_RANGED_SOLDIER:
			return new HumanArcher();
		case UPPER_RANGED_SOLDIER:
			return new HumanBoresArcher();
		case ADVANCED_RANGED_SOLDIER:
			return new HumanLongBowMan();



		case MEDIUM_MAGE_SOLDIER:
			return new WhiteWizard();
		case UPPER_MAGE_SOLDIER:
			return new HumanElementalWizard();
		case ADVANCED_MAGE_SOLDIER:
			return new HumanWizard();


		case CATAPULT:
			return new Catapult();



		default:
			return null;
		}
	}



	@NonNull
    @Override
	public Building getMyVersionOfA(@NonNull Buildings building)
	{
		switch( building )
		{
		case UndeadBarracks:
		case DwarfBarracks:
		case Barracks:
			return new Barracks();

		case DwarfMushroomFarm:
		case UndeadChurch:
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



		case BasicHealingShrine:
			return new BasicHealingShrine();

		case EvilHealingShrine:
		case HealingShrine:
			return new HealingShrine();

		case LaserCatShrine:
			return new LaserCatShrine();

		case GuardTower:
			return new GuardTower();

		case Wall:
			return new Wall();

		case UndeadStoneTower:
		case DwarfTower:
		case StoneTower:
			return new StoneTower();

		case UndeadStorageDepot:
		case StorageDepot:
			return new StorageDepot();

//		case UndeadTownCenter:
//		case DwarfTownCenter:
//		case TownCenter:
//			return new TownCenter();


		case UndeadWatchTower:
		case WatchTower:
			return new WatchTower();

		default:
			break;

		}
		return new WatchTower();
	}


	@Nullable
    @Override
	public BuildableSoldiers getHumanoidsFor( @NonNull Buildings building , @NonNull Age age )
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



	BuildableSoldiers getChurchHumanoidsFor(@NonNull Age age)
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




	BuildableSoldiers getTownCenterHumanoidsFor(@NonNull Age age)
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




	BuildableSoldiers getBarracksHumanoidsFor(@NonNull Age age)
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



	@NonNull
    @Override
	public Races getRace()
	{
		return Races.HUMAN;
	}



	@Override
	public Buildings getMyRacesBuildingType( Buildings building )
	{
		return building;
	}





}
