package com.kingscastle.effects.animations;




import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class MagicShieldAnim extends Anim {

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.magic_shield, 4);
	private final int staticTfb=50;


	public MagicShieldAnim( vector loc){
        super(loc);
		setImages( staticImages );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}


}
