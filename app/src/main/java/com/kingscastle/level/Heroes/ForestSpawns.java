package com.kingscastle.level.Heroes;



import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.abilities.Buff;
import com.kingscastle.gameElements.livingThings.abilities.DOTBuff;
import com.kingscastle.gameElements.livingThings.abilities.DamageBuff;
import com.kingscastle.gameElements.livingThings.abilities.EverythingBuff;
import com.kingscastle.gameElements.livingThings.abilities.FireDOT;
import com.kingscastle.gameElements.livingThings.abilities.Haste;
import com.kingscastle.gameElements.livingThings.abilities.HexArmor;
import com.kingscastle.gameElements.livingThings.abilities.HotBuff;
import com.kingscastle.gameElements.livingThings.abilities.MagicShield;
import com.kingscastle.gameElements.livingThings.abilities.ShieldBuff;
import com.kingscastle.gameElements.livingThings.abilities.Slow;
import com.kingscastle.gameElements.livingThings.abilities.StrongArmorBuff;
import com.kingscastle.gameElements.livingThings.abilities.Stun;
import com.kingscastle.gameElements.livingThings.army.BrownHorned;
import com.kingscastle.gameElements.livingThings.army.BrownTemplar;
import com.kingscastle.gameElements.livingThings.army.Coyote;
import com.kingscastle.gameElements.livingThings.army.Elder;
import com.kingscastle.gameElements.livingThings.army.ExoticRings;
import com.kingscastle.gameElements.livingThings.army.Gaia;
import com.kingscastle.gameElements.livingThings.army.HumanArmoredSoldier;
import com.kingscastle.gameElements.livingThings.army.HumanSoldier;
import com.kingscastle.gameElements.livingThings.army.Knight;
import com.kingscastle.gameElements.livingThings.army.KratosArmored;
import com.kingscastle.gameElements.livingThings.army.KratosHeavyArcher;
import com.kingscastle.gameElements.livingThings.army.KratosHeavyArmor;
import com.kingscastle.gameElements.livingThings.army.KratosLightArcher;
import com.kingscastle.gameElements.livingThings.army.KratosLightArm;
import com.kingscastle.gameElements.livingThings.army.KratosLightArmSword;
import com.kingscastle.gameElements.livingThings.army.KratosMage;
import com.kingscastle.gameElements.livingThings.army.KratosMedArm;
import com.kingscastle.gameElements.livingThings.army.KratosShielded;
import com.kingscastle.gameElements.livingThings.army.Medic;
import com.kingscastle.gameElements.livingThings.army.SkeletonKing;
import com.kingscastle.gameElements.livingThings.army.VampLord;
import com.kingscastle.gameElements.livingThings.army.Warrior;
import com.kingscastle.gameElements.livingThings.army.WhiteWizard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 9/9/2015 for KingsCastle-Heroes
 */
public class ForestSpawns {


    private Map<Integer,List<Class<? extends Humanoid>>> spawnsForHeroLevel = new HashMap<>();
    private Map<Integer,Class<? extends Humanoid>> bossSpawnsForHeroLevel = new HashMap<>();
    private List<Integer> spawnedBossForThisLevelAlready = new ArrayList<>();

    private Map<Integer,List<Class<? extends Humanoid>>> allySpawnsForHeroLevel = new HashMap<>();
    private Map<Integer,List<Class<? extends Humanoid>>> wizardAllySpawnsForHeroLevel = new HashMap<>();
    private Map<Integer,List<Class<? extends Humanoid>>> healerAllySpawnsForHeroLevel = new HashMap<>();

    private List<Class<? extends Buff>> buffPickups = new ArrayList<>();


    {
        {
            int heroLevel = 1;
            {//1
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosShielded.class);
                spawns.add(KratosLightArm.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }
            {//2
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosShielded.class);
                spawns.add(KratosLightArmSword.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }
            {//3
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosShielded.class);
                spawns.add(KratosMedArm.class);
                spawns.add(KratosMedArm.class);
                spawns.add(KratosLightArmSword.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }
            {//4
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosMedArm.class);
                spawns.add(KratosArmored.class);
                spawns.add(KratosLightArcher.class);
                spawns.add(KratosLightArmSword.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }
            {//5
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosMedArm.class);
                spawns.add(KratosArmored.class);
                spawns.add(KratosMedArm.class);

                spawnsForHeroLevel.put(heroLevel++, spawns);
            }
            {//6
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosArmored.class);
                spawns.add(KratosMedArm.class);
                spawns.add(KratosHeavyArmor.class);
                spawns.add(KratosArmored.class);
                spawns.add(KratosMage.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }

            {//7
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosArmored.class);
                spawns.add(KratosMedArm.class);
                spawns.add(KratosHeavyArcher.class);
                spawns.add(KratosMage.class);
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArmor.class);
                spawns.add(BrownHorned.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }

            {
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArcher.class);
                spawns.add(KratosMage.class);
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArmor.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }

            {
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArcher.class);
                spawns.add(KratosMage.class);
                spawns.add(KratosHeavyArmor.class);
                spawns.add(BrownHorned.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }

            {//10
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosMage.class);
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArmor.class);
                spawns.add(BrownHorned.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }

            {
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArcher.class);
                spawns.add(KratosMage.class);
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArmor.class);
                spawns.add(ExoticRings.class);
                spawns.add(BrownHorned.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }

            {
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArcher.class);
                spawns.add(KratosMage.class);
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArmor.class);
                spawns.add(ExoticRings.class);
                spawns.add(BrownHorned.class);
                spawns.add(BrownTemplar.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }

            {
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArcher.class);
                spawns.add(KratosMage.class);
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArmor.class);
                spawns.add(ExoticRings.class);
                spawns.add(BrownTemplar.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }

            {
                List<Class<? extends Humanoid>> spawns = new ArrayList<>();
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArcher.class);
                spawns.add(KratosMage.class);
                spawns.add(KratosArmored.class);
                spawns.add(KratosHeavyArmor.class);
                spawns.add(ExoticRings.class);
                spawns.add(BrownTemplar.class);
                spawnsForHeroLevel.put(heroLevel++, spawns);
            }
        }

        {
            bossSpawnsForHeroLevel.put(5, Coyote.class);
            bossSpawnsForHeroLevel.put(10, Gaia.class);
            bossSpawnsForHeroLevel.put(15, SkeletonKing.class);
            bossSpawnsForHeroLevel.put(20, Elder.class);
        }


        {
            int heroLevel = 1;
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(Warrior.class);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(Warrior.class);
                allySpawns.add(HumanSoldier.class);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(HumanSoldier.class);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(HumanSoldier.class);
                allySpawns.add(HumanArmoredSoldier.class);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(HumanArmoredSoldier.class);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(HumanArmoredSoldier.class);
                allySpawns.add(Knight.class);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(Knight.class);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                allySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
        }

        {
            int heroLevel = 1;
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(WhiteWizard.class);
                wizardAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                wizardAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(WhiteWizard.class);
                allySpawns.add(WhiteWizard.class);
                wizardAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                wizardAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(WhiteWizard.class);
                wizardAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                wizardAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }

        }

        {
            int heroLevel = 1;
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(Medic.class);
                healerAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                healerAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(Medic.class);
                allySpawns.add(Medic.class);
                healerAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                healerAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }
            {
                List<Class<? extends Humanoid>> allySpawns = new ArrayList<>();
                allySpawns.add(Medic.class);
                healerAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
                healerAllySpawnsForHeroLevel.put(heroLevel++, allySpawns);
            }

        }


        buffPickups.addAll(Arrays.asList(
                DOTBuff.class, HotBuff.class, DamageBuff.class, EverythingBuff.class, Haste.class, HexArmor.class, MagicShield.class,
                ShieldBuff.class, Slow.class, StrongArmorBuff.class, Stun.class, FireDOT.class));
    }

    @NotNull
    public Class<? extends Humanoid> getSpawnClass(int heroLevel){
        List<Class<? extends Humanoid>> spawns = spawnsForHeroLevel.get(Math.min(heroLevel, spawnsForHeroLevel.size() - 1) );
        return spawns.get((int)(Math.random()*spawns.size()));
    }


    public Class<? extends Humanoid> getBossSpawnClass(int heroLevel) {
        if( spawnedBossForThisLevelAlready.contains(heroLevel))
            return null;

        if( bossSpawnsForHeroLevel.containsKey(heroLevel) ) {
            spawnedBossForThisLevelAlready.add(heroLevel);
            return bossSpawnsForHeroLevel.get(heroLevel);
        }
        return null;
    }

    @NotNull
    public Class<? extends Humanoid> getAllyClass(int heroLevel){
        List<Class<? extends Humanoid>> spawns = allySpawnsForHeroLevel.get(Math.min(heroLevel, allySpawnsForHeroLevel.size() - 1) );
        return spawns.get((int)(Math.random()*spawns.size()));
    }

    @NotNull
    public Class<? extends Humanoid> getWizardAllyClass(int heroLevel){
        List<Class<? extends Humanoid>> spawns = wizardAllySpawnsForHeroLevel.get(Math.min(heroLevel, wizardAllySpawnsForHeroLevel.size() - 1) );
        return spawns.get((int)(Math.random()*spawns.size()));
    }

    public Class<? extends Humanoid> getHealerAllyClass(int heroLevel){
        List<Class<? extends Humanoid>> spawns = healerAllySpawnsForHeroLevel.get(Math.min(heroLevel, healerAllySpawnsForHeroLevel.size() - 1) );
        return spawns.get((int)(Math.random()*spawns.size()));
    }

    public List<Class<? extends Buff>> getBuffPickups() {
        return buffPickups;
    }
}
