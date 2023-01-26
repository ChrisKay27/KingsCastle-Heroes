package com.kingscastle.effects.animations;




import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class EruptionAnim extends Anim {

	
    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.spell_eruption_large, 5, 6);
	private final int staticTfb=40;

	public EruptionAnim( vector loc){
        super(loc);
		setImages(staticImages);
		setTbf(staticTfb);
		setPaint(Rpg.getXferAddPaint());
	}




}
