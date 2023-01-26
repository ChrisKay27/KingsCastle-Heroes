package com.kingscastle.effects.animations;




import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

import java.util.List;


public class BlackSummonSmokeAnim extends Anim {

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.black_explosion, 5, 4);

	private final int staticTfb = 50;

	public BlackSummonSmokeAnim(  vector loc )	{

		getLoc().set( loc.x , loc.y - Rpg.fiveDp );
		setTbf(staticTfb);
		setImages(staticImages);
		onlyShowIfOnScreen = true;
	}




}
