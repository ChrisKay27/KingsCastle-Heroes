package com.kingscastle.effects.animations;



import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.heroes.R;
import com.kingscastle.gameUtils.vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris_000 on 7/9/2015.
 */
public class DestroyedBuildingAnim extends Anim {

    private static final List<Image> staticImages = new ArrayList<>();
    static{
        staticImages.add(Assets.loadImage(R.drawable.rubble));
    }


    public DestroyedBuildingAnim( vector loc) {
        super( loc );
        onlyShowIfOnScreen = true;
        setAliveTime(60);

        setImage(staticImages.get((int) (Math.random() * staticImages.size())));

        add(new RapidImpact(loc),true);
    }

    @Override

    public Anim newInstance( vector loc){
        return new DestroyedBuildingAnim(loc);
    }



    @Override
    public String toString() {
        return "DestroyedBuildingAnim";
    }
}
