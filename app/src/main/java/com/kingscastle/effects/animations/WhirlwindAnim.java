package com.kingscastle.effects.animations;






import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class WhirlwindAnim extends Anim {

	private static final List<Image> staticImages = null; //= Assets.loadAnimationImages(R.drawable.wirlwind,4,4);
	private final int staticTfb=50;


	public WhirlwindAnim( vector loc){
        super(loc);
		setImages( staticImages );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}



}
