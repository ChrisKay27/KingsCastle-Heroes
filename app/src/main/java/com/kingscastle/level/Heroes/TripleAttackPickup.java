package com.kingscastle.level.Heroes;

import android.util.Log;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.ExplosionAnim;
import com.kingscastle.framework.GameTime;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MageSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.livingThings.attacks.TripleShotSpellAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Chris on 9/5/2015 for KingsCastle-Heroes
 */
public class TripleAttackPickup extends Pickup {

    private static final String TAG = TripleAttackPickup.class.getSimpleName();
    private final Anim anim;

    private SpellAttack oldAtk;
    private Humanoid byThisPlayer;
    private boolean isPickedUp;


    public TripleAttackPickup(@NotNull vector loc) {
        super(loc);
        anim = new ExplosionAnim(loc);
        anim.setScale(0.25f);
        anim.setLooping(true);
    }

    @Override
    public void pickedUp(@NotNull MM mm, @NotNull Humanoid byThisPlayer) {
        if( isPickedUp ) {
            Log.d(TAG , "triple attack already picked up!");
            return;
        }
        isPickedUp = true;

        Log.d(TAG , "triple attack picked up");
        this.byThisPlayer = byThisPlayer;
        oldAtk = (SpellAttack) byThisPlayer.getAQ().getCurrentAttack();
        byThisPlayer.getAQ().setCurrentAttack(new TripleShotSpellAttack(mm, byThisPlayer, oldAtk));
        setOverAt(GameTime.getTime() + 10000);
        anim.setOver(true);
    }



    @Override
    public void onOver(){
        if( byThisPlayer != null )
            byThisPlayer.getAQ().setCurrentAttack(oldAtk);
        anim.setOver(true);
    }


    @Override
    public Anim getAnim() {
        return anim;
    }


    @Override
    public boolean canPickup(@NotNull Humanoid byThisPlayer) {
        if( byThisPlayer instanceof MageSoldier) {
            AttackerQualities aq = byThisPlayer.getAQ();
            return aq.getCurrentAttack() instanceof SpellAttack && !(aq.getCurrentAttack() instanceof TripleShotSpellAttack);
        }
        return false;
    }
}
