package com.kingscastle.gameElements.livingThings.attacks;



import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Rpg;
import com.kingscastle.framework.Rpg.Direction;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.LookDirectionFinder;
import com.kingscastle.gameElements.livingThings.army.Catapult;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Projectile;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Chris on 7/24/2015 for Tower Defence
 */
public class CatapultAttack extends ProjectileAttack {

    private long doAttackAt;
    private LivingThing target;

    private Direction lookDir = Direction.E;

    public CatapultAttack( MM mm,  LivingThing lt,  Projectile p) {
        super(mm, lt, p);
    }


    @Override
    public void act()
    {
        if( doAttackAt < GameTime.getTime() )
        {
            doAttack();
            doAttackAt = Long.MAX_VALUE;
        }
    }
    @Override
    public boolean attack(@NotNull LivingThing target )
    {
        doAttackAt = GameTime.getTime() + Catapult.animsFramePeriod*8;
        this.target = target;
        lookDir = LookDirectionFinder.getDir(owner.loc, target.loc);
        return true;
    }

    private void doAttack()
    {
        LivingThing target_local = target;
        if( target_local == null )
            return;
        Projectile p = proj.newInstance( owner , new vector(target_local.loc) , target_local );
        p.loc.add( 0 , -Rpg.sixTeenDp );
        mm.add(p);
    }


    public Direction getLookDir() {
        return lookDir;
    }
}
