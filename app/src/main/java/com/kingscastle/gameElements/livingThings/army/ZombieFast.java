package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class ZombieFast extends MeleeSoldier {

    private static final String TAG = ZombieFast.class.getSimpleName();
    private static final String NAME = TAG;

    private static Image[] staticImages = Assets.loadImages(R.drawable.zombie, 0, 0, 1, 1);

    @NonNull
    private static final LivingQualities staticLivingQualities;
    @NonNull
    private static final AttackerQualities staticAttackerQualities;

    static {
        float dp = Rpg.getDp();

        staticAttackerQualities = new AttackerQualities();

        staticAttackerQualities.setStaysAtDistanceSquared(0);
        staticAttackerQualities.setFocusRangeSquared(5000 * dp * dp);
        staticAttackerQualities.setAttackRangeSquared(Rpg.getMeleeAttackRangeSquared());
        staticAttackerQualities.setDamage(3);
        staticAttackerQualities.setdDamageAge(0);
        staticAttackerQualities.setdDamageLvl(1);
        staticAttackerQualities.setROF(100);

        staticLivingQualities = new LivingQualities();
        staticLivingQualities.setRequiresBLvl(1);
        staticLivingQualities.setRequiresAge(Age.STONE);
        staticLivingQualities.setRequiresTcLvl(1);
        staticLivingQualities.setRangeOfSight(300);
        staticLivingQualities.setLevel(1);
        staticLivingQualities.setFullHealth(200);
        staticLivingQualities.setHealth(200);
        staticLivingQualities.setdHealthAge(0);
        staticLivingQualities.setdHealthLvl(10); //
        staticLivingQualities.setFullMana(0);
        staticLivingQualities.setMana(0);
        staticLivingQualities.setHpRegenAmount(1);
        staticLivingQualities.setRegenRate(10000);
        staticLivingQualities.setArmor(10);
        staticLivingQualities.setdArmorAge(3);
        staticLivingQualities.setdArmorLvl(1);
        staticLivingQualities.setSpeed(3f * dp);
    }

    {
        setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
        setGoldDropped(10);
        super.setCosts(new Cost(30, 0, 0, 1));
    }


    public ZombieFast(@NonNull vector loc, Teams team) {
        super(team);
        setLoc(loc);
    }

    public ZombieFast() {
    }


    @Override
    public Image[] getImages() {
        return staticImages;
    }


    @Override
    public Anim getDyingAnimation() {
        return Assets.deadSkeletonAnim;
    }


    @Override
    public RectF getStaticPerceivedArea() {
        return Rpg.getNormalPerceivedArea();
    }



    @NonNull
    @Override
    public LivingQualities getNewLivingQualities() {
        return new LivingQualities(staticLivingQualities);
    }

    @NonNull
    @Override
    protected AttackerQualities getStaticAQ() {
        return staticAttackerQualities;
    }
    @NonNull
    @Override
    protected LivingQualities getStaticLQ() {
        return staticLivingQualities;
    }



    @NonNull
    @Override
    public String toString() {
        return TAG;
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

}
