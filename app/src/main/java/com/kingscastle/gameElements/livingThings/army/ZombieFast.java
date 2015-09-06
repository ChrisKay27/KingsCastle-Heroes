package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
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
    private static final Attributes STATIC_ATTRIBUTES;
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

        STATIC_ATTRIBUTES = new Attributes();
        STATIC_ATTRIBUTES.setRequiresBLvl(1);
        STATIC_ATTRIBUTES.setRequiresAge(Age.STONE);
        STATIC_ATTRIBUTES.setRequiresTcLvl(1);
        STATIC_ATTRIBUTES.setRangeOfSight(300);
        STATIC_ATTRIBUTES.setLevel(1);
        STATIC_ATTRIBUTES.setFullHealth(200);
        STATIC_ATTRIBUTES.setHealth(200);
        STATIC_ATTRIBUTES.setdHealthAge(0);
        STATIC_ATTRIBUTES.setdHealthLvl(10); //
        STATIC_ATTRIBUTES.setFullMana(0);
        STATIC_ATTRIBUTES.setMana(0);
        STATIC_ATTRIBUTES.setHpRegenAmount(1);
        STATIC_ATTRIBUTES.setRegenRate(10000);
        STATIC_ATTRIBUTES.setArmor(10);
        STATIC_ATTRIBUTES.setdArmorAge(3);
        STATIC_ATTRIBUTES.setdArmorLvl(1);
        STATIC_ATTRIBUTES.setSpeed(3f * dp);
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
    public Attributes getNewLivingQualities() {
        return new Attributes(STATIC_ATTRIBUTES);
    }

    @NonNull
    @Override
    protected AttackerQualities getStaticAQ() {
        return staticAttackerQualities;
    }
    @NonNull
    @Override
    protected Attributes getStaticLQ() {
        return STATIC_ATTRIBUTES;
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
