package com.kingscastle.effects.animations;




import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class LightningStrikeLargeAnim extends Anim {


    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.lightning_large, 5, 5).subList(0,23);
	private final int staticTfb=60;


	public LightningStrikeLargeAnim( vector loc){
        super( loc);
        setImages(staticImages );
		setTbf(staticTfb);
		setPaint(Rpg.getXferAddPaint());
		onlyShowIfOnScreen = true;
	}



}
