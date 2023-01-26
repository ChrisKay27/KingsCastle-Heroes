package com.kingscastle.effects.animations;




import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.heroes.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class HealSparklesAnim extends Anim {

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.heal_sparkle, 6, 1);
	private final int staticTfb=50;


	public HealSparklesAnim( vector loc){
        super(loc);
		setImages( staticImages );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}





}
