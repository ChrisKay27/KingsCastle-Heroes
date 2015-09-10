package com.kingscastle.level;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.army.ArmyManager;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;
import com.kingscastle.util.ManagerListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 7/10/2015.
 */
public class HeroesSaverLoader {

    private static final String TAG = "TDLevelSaverLoader";
    public static final String CLASSNAME = "CN";
    public static final String TEAM = "Team";
    public static final String LOC = "Loc";
    public static final String LVL = "Lvl";
    public static final String HEALTH = "Health";
    public static final String HERO = "Hero";

    public static void loadGame(@NonNull ObjectInputStream ois, @NonNull HeroesLevel lvl) throws IOException, ClassNotFoundException {
        Log.v(TAG, "Loading Level: " + lvl);

        int heroLevel = (Integer) ois.readObject();
        Log.v(TAG, "Hero Level  " + heroLevel );


        MM mm = lvl.getMM();
        {
            ArmyManager am = mm.getTeam(Teams.BLUE).getArmyManager();

            ArrayList<HashMap<String,Object>> armyInfo = (ArrayList<HashMap<String,Object>>) ois.readObject();

            for (final HashMap<String,Object> attr : armyInfo) {

                final Humanoid lt = Humanoid.getFromString((String) attr.get(CLASSNAME), (Teams) attr.get(TEAM), (vector) attr.get(LOC));

                Log.d(TAG, "Loading " + lt + " with team:" + attr.get(TEAM));

                am.add(lt, new ManagerListener<Humanoid>() {
                    @Override
                    public boolean onAdded(Humanoid humanoid) {
                        lt.attributes.setHealth((Integer) attr.get(HEALTH));
                        lt.upgradeToLevel((int) attr.get(LVL));

                        //if( !attr.containsKey(HERO) || attr.get(HERO) != true ){
                        lt.aq.setFocusRangeSquared(HeroesLevel.ALLIED_FOCUS_RANGE_SQUARED);
                        return true;
                    }

                    @Override
                    public boolean onRemoved(Humanoid humanoid) {
                        return false;
                    }
                });


                //}
            }
        }
//        {
//            BuildingManager bm = mm.getTeam(Teams.BLUE).getBm();
//
//            ArrayList<HashMap<String,Object>> buildingsInfo = (ArrayList<HashMap<String,Object>>) ois.readObject();
//
//            Log.v(TAG,"Loading " + buildingsInfo.size() + " buildings");
//
//            for( final HashMap<String,Object> attr : buildingsInfo ) {
//                Building buildin = Building.getFromString((String) attr.get(CLASSNAME),(Teams)attr.get(TEAM),(vector)attr.get(LOC));
//                //Log.v(TAG, "Loaded " + buildin);
//                bm.add(buildin,new BuildingManager.OnBuildingAddedListener() {
//                    @Override
//                    public boolean onBuildingAdded(@NonNull Building b) {
//                        b.upgradeToLevel((int) attr.get(LVL));
//                        return true;
//                    }
//                });
//            }
//        }

        {
            ArmyManager am = mm.getTeam(Teams.RED).getArmyManager();

            ArrayList<HashMap<String,Object>> armyInfo = (ArrayList<HashMap<String,Object>>) ois.readObject();

            for (final HashMap<String,Object> attr : armyInfo) {

                final Humanoid lt = Humanoid.getFromString((String) attr.get(CLASSNAME), (Teams) attr.get(TEAM), (vector) attr.get(LOC));

                Log.d(TAG, "Loading " + lt + " with team:" + attr.get(TEAM));

                am.add(lt, new ManagerListener<Humanoid>() {
                    @Override
                    public boolean onAdded(Humanoid humanoid) {

                        lt.upgradeToLevel((int) attr.get(LVL));
                        lt.attributes.setHealth((Integer) attr.get(HEALTH));


                        //if( !attr.containsKey(HERO) || attr.get(HERO) != true ){
                        lt.aq.setFocusRangeSquared(HeroesLevel.ALLIED_FOCUS_RANGE_SQUARED);
                        return true;
                    }

                    @Override
                    public boolean onRemoved(Humanoid humanoid) {
                        return false;
                    }
                });

                if( attr.containsKey(HERO) && attr.get(HERO) == true ){
                    lvl.setHero(lt);
                }
            }
        }
        ois.close();
        Log.v(TAG,"Loading complete");
    }


    public static void saveLevel(FileOutputStream fos , @NonNull HeroesLevel lvl) throws IOException {

        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(lvl.getClass().getName());
        oos.writeObject(lvl.getHero().attributes.getLevel());

        Log.v(TAG, "Saving  " + lvl.getClass().getName());
        Log.v(TAG, "Saving Level: " + lvl);

        MM mm = lvl.getMM();
        {
            ArmyManager am = mm.getTeam(Teams.BLUE).getArmyManager();
            List<Humanoid> army = am.getArmy();
            //Log.v(TAG, "Saving " +army.size() + " allies.");
            ArrayList<Map<String,Object>> armyInfo = new ArrayList<>();

            for (int i = 0; i < army.size(); ++i) {
                LivingThing lt = army.get(i);
                if( lt.isDead() ){
                    // Log.i(TAG,"Not saving a dead " + lt );
                    continue;
                }
                HashMap<String,Object> attr = new HashMap<>();

                attr.put(CLASSNAME,lt.getClass().getSimpleName());
                attr.put(TEAM,lt.getTeamName());
                attr.put(LOC, lt.getLoc());
                attr.put(LVL, lt.getLQ().getLevel());
                attr.put(HEALTH, lt.getLQ().getHealth());


//                attr.put(AbstractRound.START_END_LOC_INDEX, lt.getExtras().get(AbstractRound.START_END_LOC_INDEX));
//
//                if( lt.getExtras().get(AbstractRound.START_END_LOC_INDEX) == null )
//                    throw new WtfException("lt.getExtras().get(START_END_LOC_INDEX)==null");

//                for( String s : attr.keySet() )
//                    Log.v(TAG, "Saving " + attr.get(s));

                armyInfo.add(attr);
            }
            oos.writeObject(armyInfo);
        }


//        {
//            BuildingManager bm = mm.getTeam(Teams.BLUE).getBm();
//            ListPkg<Building> pkg = bm.getBuildings();
//
//            Log.v(TAG, "Saving " + pkg.size + " buildings.");
//            ArrayList<Map<String,Object>> buildingInfo = new ArrayList<>();
//
//            for( int i = 0 ; i < pkg.size ; ++i ) {
//                Building building = pkg.list[i];
//                HashMap<String,Object> attr = new HashMap<>();
//
//                attr.put(CLASSNAME,building.getClass().getSimpleName());
//                attr.put(TEAM,building.getTeamName());
//                attr.put(LOC, building.getLoc());
//                attr.put(LVL, building.getLQ().getLevel());
//
////                for( String s : attr.keySet() )
////                    Log.v(TAG, "Saving " + attr.get(s));
//
//                buildingInfo.add(attr);
//            }
//            oos.writeObject(buildingInfo);
//        }

        {
            ArmyManager am = mm.getTeam(Teams.RED).getArmyManager();
            List<Humanoid> army = am.getArmy();
            //Log.v(TAG, "Saving " +army.size() + " monsters.");
            ArrayList<Map<String,Object>> armyInfo = new ArrayList<>();

            for (int i = 0; i < army.size(); ++i) {
                LivingThing lt = army.get(i);
                if( lt.isDead() ){
                   // Log.i(TAG,"Not saving a dead " + lt );
                    continue;
                }
                HashMap<String,Object> attr = new HashMap<>();

                attr.put(CLASSNAME,lt.getClass().getSimpleName());
                attr.put(TEAM,lt.getTeamName());
                attr.put(LOC, lt.getLoc());
                attr.put(LVL, lt.getLQ().getLevel());
                attr.put(HEALTH, lt.getLQ().getHealth());
//                attr.put(AbstractRound.START_END_LOC_INDEX, lt.getExtras().get(AbstractRound.START_END_LOC_INDEX));
//
//                if( lt.getExtras().get(AbstractRound.START_END_LOC_INDEX) == null )
//                    throw new WtfException("lt.getExtras().get(START_END_LOC_INDEX)==null");

//                for( String s : attr.keySet() )
//                    Log.v(TAG, "Saving " + attr.get(s));

                armyInfo.add(attr);
            }
            oos.writeObject(armyInfo);
        }
        oos.close();
    }



}
