package com.kingscastle.effects.animations;






import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

class MoveToCirclesAnim extends Anim {

	private static final List<Image> staticImages = null;
	private final int staticTfb=60;


	public MoveToCirclesAnim( vector loc){
        super( loc);
        setImages(staticImages );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}



}
