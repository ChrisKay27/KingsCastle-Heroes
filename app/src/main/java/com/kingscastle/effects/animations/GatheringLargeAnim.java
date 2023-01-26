package com.kingscastle.effects.animations;




import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class GatheringLargeAnim extends Anim {


    private static final List<Image> staticImages = Assets.loadAnimationImages(0/*R.drawable.gathering_large */, 5, 4);
	private final int staticTfb = 40;

	public GatheringLargeAnim( vector loc) {
        super(loc);
        setImages(staticImages);
		setTbf(staticTfb);
		setPaint(Rpg.getXferAddPaint());
		onlyShowIfOnScreen = true;
        throw new RuntimeException("Not implemented exception");
	}

}
