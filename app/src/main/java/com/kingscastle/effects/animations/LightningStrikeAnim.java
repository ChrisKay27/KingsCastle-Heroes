package com.kingscastle.effects.animations;




import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.heroes.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class LightningStrikeAnim extends Anim {

	
    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.lightning2, 2, 2);
	private final int staticTfb=60;


	public LightningStrikeAnim( vector loc){
        super( loc );
        setImages(staticImages );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}



}
