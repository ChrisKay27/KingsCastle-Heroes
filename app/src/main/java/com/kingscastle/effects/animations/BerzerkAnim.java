package com.kingscastle.effects.animations;




import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class BerzerkAnim extends Anim
{

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.berzerk_rune, 4, 1);;
	private final int staticTfb = 50;


	public BerzerkAnim(  vector loc ){
        super(loc);
		setImages( staticImages );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
		//setPaint( Rpg.getXferAddPaint() );
	}


}
