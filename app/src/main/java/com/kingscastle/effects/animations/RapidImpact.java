package com.kingscastle.effects.animations;




import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.heroes.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class RapidImpact extends Anim {


    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.rapid_impact , 5, 4);
	private final int staticTfb = 40;

	public RapidImpact( vector loc) {
        super(loc);
		setImages( staticImages );
		setTbf(staticTfb);
		setPaint(Rpg.getXferAddPaint());
		onlyShowIfOnScreen = true;
	}

}
