package com.kingscastle.teams.races;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.SoldierType;
import com.kingscastle.gameElements.livingThings.army.Catapult;
import com.kingscastle.gameElements.livingThings.army.UndeadDeathKnight;
import com.kingscastle.gameElements.livingThings.army.UndeadDemogorgon;
import com.kingscastle.gameElements.livingThings.army.UndeadDullahan;
import com.kingscastle.gameElements.livingThings.army.UndeadGoldenArcher;
import com.kingscastle.gameElements.livingThings.army.UndeadHealer;
import com.kingscastle.gameElements.livingThings.army.UndeadMarshall;
import com.kingscastle.gameElements.livingThings.army.UndeadPossessed;
import com.kingscastle.gameElements.livingThings.army.UndeadPossessedKnight;
import com.kingscastle.gameElements.livingThings.army.UndeadSkeletonArcher;
import com.kingscastle.gameElements.livingThings.army.UndeadSkeletonBowman;
import com.kingscastle.gameElements.livingThings.army.UndeadSkeletonScout;
import com.kingscastle.gameElements.livingThings.army.UndeadSkeletonWarrior;
import com.kingscastle.gameElements.livingThings.army.UndeadSkullFucqued;
import com.kingscastle.gameElements.livingThings.buildings.BasicHealingShrine;
import com.kingscastle.gameElements.livingThings.buildings.BuildableSoldiers;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.gameElements.livingThings.buildings.EvilHealingShrine;
import com.kingscastle.gameElements.livingThings.buildings.LaserCatShrine;
import com.kingscastle.gameElements.livingThings.buildings.UndeadBarracks;
import com.kingscastle.gameElements.livingThings.buildings.UndeadGuardTower;
import com.kingscastle.gameElements.livingThings.buildings.UndeadStoneTower;
import com.kingscastle.gameElements.livingThings.buildings.UndeadStorageDepot;
import com.kingscastle.gameElements.livingThings.buildings.UndeadWatchTower;
import com.kingscastle.gameUtils.Age;

public class UndeadRace extends Race
{
	private static UndeadRace singularity;

	public static UndeadRace getInstance()
	{
		if( singularity == null )
		{
			singularity = new UndeadRace();
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
		UndeadSkeletonScout     scout = new UndeadSkeletonScout();
		UndeadSkeletonWarrior warrior = new UndeadSkeletonWarrior();
		UndeadHealer medic = new UndeadHealer();


		UndeadDullahan         dull = new UndeadDullahan();
		UndeadMarshall     marshall = new UndeadMarshall();
		UndeadSkeletonArcher archer = new UndeadSkeletonArcher();


		UndeadSkullFucqued     skullFucked = new UndeadSkullFucqued();
		UndeadSkeletonBowman armoredArcher = new UndeadSkeletonBowman();
		UndeadPossessedKnight blackDeath   = new UndeadPossessedKnight();

		UndeadDeathKnight dknight = new UndeadDeathKnight();
		UndeadGoldenArcher uga = new UndeadGoldenArcher();
		UndeadDemogorgon demogordon = new UndeadDemogorgon();
		UndeadPossessed   possessed = new UndeadPossessed();
		Catapult c = new Catapult();


		stoneAgeBarracksHumanoids  = new BuildableSoldiers( warrior , scout );
		bronzeAgeBarracksHumanoids = new BuildableSoldiers( warrior , scout , marshall , archer );
		ironAgeBarracksHumanoids   = new BuildableSoldiers( warrior , scout , marshall , archer , skullFucked , armoredArcher );
		steelAgeBarracksHumanoids  = new BuildableSoldiers( warrior , scout , marshall , archer , skullFucked , armoredArcher , dknight , uga , c );





		stoneAgeTownCenterHumanoids = new BuildableSoldiers( warrior );
		bronzeAgeTownCenterHumanoids = ironAgeTownCenterHumanoids = steelAgeTownCenterHumanoids = stoneAgeTownCenterHumanoids;
		//bronzeAgeTownCenterHumanoids = new BuildableSoldiers( worker , scout );
		//ironAgeTownCenterHumanoids = new BuildableSoldiers( worker , scout );
		//steelAgeTownCenterHumanoids = new BuildableSoldiers( worker , scout );



		stoneAgeChurchHumanoids  = new BuildableSoldiers( medic );
		bronzeAgeChurchHumanoids = new BuildableSoldiers( medic , dull );
		ironAgeChurchHumanoids   = new BuildableSoldiers( medic , dull , blackDeath );
		steelAgeChurchHumanoids  = new BuildableSoldiers( medic , dull , blackDeath , possessed , demogordon );
	}



	@Nullable
    @Override
	public SoldierType getMy( @NonNull GeneralSoldierType soldierType )
	{
		switch( soldierType )
		{

		case WORKER:
			return SoldierType.UNDEADWORKER;


		case BASIC_HEALER:
			return SoldierType.UNDEADHEALER;

		case ADVANCED_HEALER:
			return SoldierType.UNDEADDEMOGORGON;


		case BASIC_MELEE_SOLDIER:
			return SoldierType.UNDEADSKELETONWARRIOR;

		case MEDIUM_MELEE_SOLDIER:
			return SoldierType.UNDEADMARSHALL;

		case ADVANCED_MELEE_SOLDIER:
			return SoldierType.UNDEADSKULLFUCQUED;


		case BASIC_RANGED_SOLDIER:
			return SoldierType.UNDEADSKELETONSCOUT;

		case MEDIUM_RANGED_SOLDIER:
			return SoldierType.UNDEADSKELETONARCHER;

		case ADVANCED_RANGED_SOLDIER:
			return SoldierType.UNDEADSKELETONBOWMAN;

		case UPPER_MAGE_SOLDIER:
			return SoldierType.BLACKDEATH;

		case ADVANCED_MAGE_SOLDIER:
			return SoldierType.BLACKDEATH;

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
			return new UndeadHealer();
		case ADVANCED_HEALER:
			return new UndeadDemogorgon();




		case BASIC_MELEE_SOLDIER:
			return new UndeadSkeletonWarrior();
		case MEDIUM_MELEE_SOLDIER:
			return new UndeadMarshall();
		case UPPER_MELEE_SOLDIER:
			return new UndeadSkullFucqued();
		case ADVANCED_MELEE_SOLDIER:
			return new UndeadSkullFucqued();



			// Ranged
		case BASIC_RANGED_SOLDIER:
			return new UndeadSkeletonScout();
		case MEDIUM_RANGED_SOLDIER:
			return new UndeadSkeletonArcher();
		case UPPER_RANGED_SOLDIER:
			return new UndeadSkeletonBowman();
		case ADVANCED_RANGED_SOLDIER:
			return new UndeadGoldenArcher();




		case MEDIUM_MAGE_SOLDIER:
			return new UndeadDullahan();
		case UPPER_MAGE_SOLDIER:
			return new UndeadPossessedKnight();
		case ADVANCED_MAGE_SOLDIER:
			return new UndeadPossessed();



		case CATAPULT:
			return new Catapult();


		default:
			return null;

		}
	}





	@NonNull
    @Override
	public Buildings getMyRacesBuildingType( @NonNull Buildings building )
	{

		switch( building )
		{
		case UndeadBarracks:
		case DwarfBarracks:
		case Barracks:
			return Buildings.UndeadBarracks;

		case DwarfMushroomFarm:
		case UndeadChurch:
		case Church:
			return Buildings.UndeadChurch;



		case PileOCorps:
		case Farm:
			return Buildings.PileOCorps;


		case UndeadGuardTower:
		case GuardTower:
			return Buildings.UndeadGuardTower;



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


		case PendingBuilding:
			return Buildings.PendingBuilding;


		case UndeadStoneTower:
		case DwarfTower:
		case StoneTower:
			return Buildings.UndeadStoneTower;

		case UndeadStorageDepot:
		case StorageDepot:
			return Buildings.UndeadStorageDepot;

		case UndeadTownCenter:
		case DwarfTownCenter:
		case TownCenter:
			return Buildings.UndeadTownCenter;



		case UndeadWatchTower:
		case WatchTower:
			return Buildings.UndeadWatchTower;


		case BasicHealingShrine:
			return Buildings.BasicHealingShrine;

		case EvilHealingShrine:
		case HealingShrine:
			return Buildings.EvilHealingShrine;

		case LaserCatShrine:
			return Buildings.LaserCatShrine;


		default:
			break;

		}

		return Buildings.PileOCorps;
	}



	@Nullable
    @Override
	public Building getMyVersionOfA( @NonNull Buildings building )
	{
		switch( building )
		{

		case UndeadBarracks:
		case Barracks:
			return new UndeadBarracks();

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



		case PileOCorps:



		case UndeadGuardTower:
		case GuardTower:
			return new UndeadGuardTower();

		case UndeadStoneTower:
		case StoneTower:
			return new UndeadStoneTower();

		case UndeadStorageDepot:
		case StorageDepot:
			return new UndeadStorageDepot();


		case UndeadWatchTower:
		case WatchTower:
			return new UndeadWatchTower();

		case BasicHealingShrine:
			return new BasicHealingShrine();

		case EvilHealingShrine:
		case HealingShrine:
			return new EvilHealingShrine();

		case LaserCatShrine:
			return new LaserCatShrine();

		default:
			break;

		}

		return null;
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
		return Races.UNDEAD;
	}








}
