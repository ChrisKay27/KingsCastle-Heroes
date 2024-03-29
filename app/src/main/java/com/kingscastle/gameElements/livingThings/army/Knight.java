package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;



import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedMeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class Knight extends AdvancedMeleeSoldier {

    private static ImageFormatInfo imageFormatInfo;
    private static Image[] redImages = Assets.loadImages(R.drawable.soldier_deen_red,0,0,1,1), blueImages = Assets.loadImages(R.drawable.soldier_deen_blue, 0, 0, 1, 1), greenImages, orangeImages, whiteImages;

    private static final Attributes STATIC_ATTRIBUTES;
    private static final AttackerQualities staticAttackerQualities;

    private static Cost cost = new Cost(4000, 4000, 0, 2);

    static {
        float dp = Rpg.getDp();

        staticAttackerQualities = new AttackerQualities();

        staticAttackerQualities.setStaysAtDistanceSquared(0);
        staticAttackerQualities.setFocusRangeSquared(5000 * dp * dp);
        staticAttackerQualities.setAttackRangeSquared(Rpg.getMeleeAttackRangeSquared());
        staticAttackerQualities.setDamage(40);
        staticAttackerQualities.setdDamageAge(0);
        staticAttackerQualities.setdDamageLvl(5);
        staticAttackerQualities.setROF(800);

        STATIC_ATTRIBUTES = new Attributes();
        STATIC_ATTRIBUTES.setRequiresBLvl(10);
        STATIC_ATTRIBUTES.setRequiresAge(Age.STEEL);
        STATIC_ATTRIBUTES.setRequiresTcLvl(16);
        STATIC_ATTRIBUTES.setLevel(1);
        STATIC_ATTRIBUTES.setFullHealth(250);
        STATIC_ATTRIBUTES.setHealth(250);
        STATIC_ATTRIBUTES.setdHealthAge(0);
        STATIC_ATTRIBUTES.setdHealthLvl(30);
        STATIC_ATTRIBUTES.setFullMana(0);
        STATIC_ATTRIBUTES.setMana(0);
        STATIC_ATTRIBUTES.setHpRegenAmount(1);
        STATIC_ATTRIBUTES.setRegenRate(1000);
        STATIC_ATTRIBUTES.setArmor(10);
        STATIC_ATTRIBUTES.setdArmorAge(0);
        STATIC_ATTRIBUTES.setdArmorLvl(2);
        STATIC_ATTRIBUTES.setSpeed(1.0f * dp);

        redImages = Assets.loadImages(R.drawable.soldier_deen_red, 0, 0, 1, 1);
        blueImages = Assets.loadImages(R.drawable.soldier_deen_blue, 0, 0, 1, 1);
    }

    {
        setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
    }


    public Knight( vector loc, Teams team) {
        super(team);
        setLoc(loc);

        setTeam(team);
    }


    public Knight() {
    }


    @Override
    public Image[] getImages() {

        switch (getTeamName()) {
            default:
            case RED:
                return redImages;

            case GREEN:
                return greenImages;

            case BLUE:
                return blueImages;

            case ORANGE:
                return orangeImages;

            case WHITE:
                return whiteImages;
        }
    }


    @Override
    public ImageFormatInfo getImageFormatInfo() {
        return imageFormatInfo;
    }
    public void setImageFormatInfo(ImageFormatInfo imageFormatInfo) {
        Knight.imageFormatInfo = imageFormatInfo;
    }


    /**
     * DOES NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null.
     *
     * @return the staticImages
     */

    @Override
    public Image[] getStaticImages() {
        return null;
    }



    @Override
    public void setStaticImages(Image[] staticImages) {
    }


    @Override
    public RectF getStaticPerceivedArea() {
        return Rpg.getNormalPerceivedArea();
    }


    @Override
    public Attributes getNewLivingQualities() {
        return new Attributes(STATIC_ATTRIBUTES);
    }

    @Override
    public void setStaticPerceivedArea(RectF staticPercArea) {

    }


    @Override
    public Cost getCosts() {
        return cost;
    }


    public static void setCost(Cost cost) {
        Knight.cost = cost;
    }

    public static Image[] getRedImages() {
        if (redImages == null) {
            redImages = Assets.loadImages(imageFormatInfo.getRedId(), 3, 4, 0, 0, 1, 1);
        }
        return redImages;
    }

    public static void setRedImages(Image[] redImages) {
        Knight.redImages = redImages;
    }

    public static Image[] getBlueImages() {
        if (blueImages == null) {
            blueImages = Assets.loadImages(imageFormatInfo.getBlueId(), 3, 4, 0, 0, 1, 1);
        }
        return blueImages;
    }

    public static void setBlueImages(Image[] blueImages) {
        Knight.blueImages = blueImages;
    }

    public static Image[] getGreenImages() {
        if (greenImages == null) {
            greenImages = Assets.loadImages(imageFormatInfo.getGreenId(), 3, 4, 0, 0, 1, 1);
        }
        return greenImages;
    }

    public static void setGreenImages(Image[] greenImages) {
        Knight.greenImages = greenImages;
    }

    public static Image[] getOrangeImages() {
        if (orangeImages == null) {
            orangeImages = Assets.loadImages(imageFormatInfo.getOrangeId(), 3, 4, 0, 0, 1, 1);
        }
        return orangeImages;
    }

    public static void setOrangeImages(Image[] orangeImages) {
        Knight.orangeImages = orangeImages;
    }

    public static Image[] getWhiteImages() {
        if (whiteImages == null) {
            whiteImages = Assets.loadImages(imageFormatInfo.getWhiteId(), 3, 4, 0, 0, 1, 1);
        }
        return whiteImages;
    }

    public static void setWhiteImages(Image[] whiteImages) {
        Knight.whiteImages = whiteImages;
    }

    private static final String TAG = "Knight";


    @Override
    public String toString() {
        return TAG;
    }


    @Override
    public String getName() {
        return TAG;
    }



    @Override
    protected AttackerQualities getStaticAQ() {
        return staticAttackerQualities;
    }


    @Override
    protected Attributes getStaticLQ() {
        return STATIC_ATTRIBUTES;
    }
}
