package com.kingscastle.level.Heroes;

import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.army.KratosArmored;
import com.kingscastle.gameElements.livingThings.army.KratosHeavyArcher;
import com.kingscastle.gameElements.livingThings.army.KratosHeavyArmor;
import com.kingscastle.gameElements.livingThings.army.KratosLightArcher;
import com.kingscastle.gameElements.livingThings.army.KratosLightArm;
import com.kingscastle.gameElements.livingThings.army.KratosLightArmSword;
import com.kingscastle.gameElements.livingThings.army.KratosMage;
import com.kingscastle.gameElements.livingThings.army.KratosMedArm;
import com.kingscastle.gameElements.livingThings.army.KratosShielded;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 9/9/2015 for KingsCastle-Heroes
 */
public class ForestSpawns {

    private static Map<Integer,List<Class<? extends Humanoid>>> spawnsForHeroLevel = new HashMap<>();

    static{
        int heroLevel = 0;
        {
            List<Class<? extends Humanoid>> spawns = new ArrayList<>();
            spawns.add(KratosShielded.class);
            spawns.add(KratosLightArm.class);
            spawnsForHeroLevel.put(heroLevel++, spawns);
        }
        {
            List<Class<? extends Humanoid>> spawns = new ArrayList<>();
            spawns.add(KratosShielded.class);
            spawns.add(KratosLightArmSword.class);
            spawnsForHeroLevel.put(heroLevel++, spawns);
        }
        {
            List<Class<? extends Humanoid>> spawns = new ArrayList<>();
            spawns.add(KratosShielded.class);
            spawns.add(KratosMedArm.class);
            spawns.add(KratosMedArm.class);
            spawns.add(KratosLightArmSword.class);
            spawnsForHeroLevel.put(heroLevel++, spawns);
        }
        {
            List<Class<? extends Humanoid>> spawns = new ArrayList<>();
            spawns.add(KratosMedArm.class);
            spawns.add(KratosArmored.class);
            spawns.add(KratosLightArcher.class);
            spawns.add(KratosLightArmSword.class);
            spawnsForHeroLevel.put(heroLevel++, spawns);
        }
        {
            List<Class<? extends Humanoid>> spawns = new ArrayList<>();
            spawns.add(KratosMedArm.class);
            spawns.add(KratosArmored.class);
            spawns.add(KratosMedArm.class);

            spawnsForHeroLevel.put(heroLevel++, spawns);
        }
        {
            List<Class<? extends Humanoid>> spawns = new ArrayList<>();
            spawns.add(KratosArmored.class);
            spawns.add(KratosMedArm.class);
            spawns.add(KratosHeavyArmor.class);
            spawns.add(KratosArmored.class);
            spawns.add(KratosMage.class);
            spawnsForHeroLevel.put(heroLevel++, spawns);
        }
        {
            List<Class<? extends Humanoid>> spawns = new ArrayList<>();
            spawns.add(KratosArmored.class);
            spawns.add(KratosMedArm.class);
            spawns.add(KratosHeavyArcher.class);
            spawns.add(KratosMage.class);
            spawns.add(KratosArmored.class);
            spawns.add(KratosHeavyArmor.class);
            spawnsForHeroLevel.put(heroLevel++, spawns);
        }
    }


    public static Class<? extends Humanoid> getSpawnClass(int heroLevel){
        List<Class<? extends Humanoid>> spawns = spawnsForHeroLevel.get(heroLevel%spawnsForHeroLevel.size());
        return spawns.get((int)(Math.random()*spawns.size()));
    }
}
