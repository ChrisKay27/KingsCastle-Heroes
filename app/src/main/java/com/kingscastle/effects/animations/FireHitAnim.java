package com.kingscastle.effects.animations;




import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class FireHitAnim extends Anim {

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.quick_fire_hit, 10, 3);

	private final int staticTfb = 10;

	public FireHitAnim( vector loc) {
		super(loc);
		setTbf(staticTfb);
		setImages(staticImages);
		onlyShowIfOnScreen = true;
	}

}
