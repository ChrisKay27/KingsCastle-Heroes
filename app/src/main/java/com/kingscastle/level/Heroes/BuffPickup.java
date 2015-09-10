package com.kingscastle.level.Heroes;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.abilities.Ability;
import com.kingscastle.gameElements.livingThings.abilities.Buff;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Chris on 9/5/2015 for KingsCastle-Heroes
 */
public class BuffPickup extends Pickup {

    private final Buff buff;
    private final Anim anim;


    public BuffPickup(@NotNull vector loc, @NotNull Buff buff) throws Exception {
        super(loc);
        this.buff = buff;
        anim = buff.getAnimClass().getConstructor(vector.class).newInstance(loc);
        anim.setLooping(true);
        anim.setLoc(loc);
    }

    @Override
    public void pickedUp(@NotNull MM mm, @NotNull Humanoid byThisPlayer) {
        Ability b = buff.newInstance(byThisPlayer);
        mm.add(b);
        anim.setOver(true);
        setOverAt(0);
    }

    @Override
    public void onOver(){
        anim.setOver(true);
    }


    @Override
    public Anim getAnim() {
        return anim;
    }


    @Override
    public boolean canPickup(@NotNull Humanoid byThisPlayer) {
        return !byThisPlayer.getActiveAbilities().containsInstanceOf(buff);
    }
}
