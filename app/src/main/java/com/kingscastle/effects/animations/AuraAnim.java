package com.kingscastle.effects.animations;




import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class AuraAnim extends Anim {


    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.aura, 8, 4);
	//private static final Vector staticOffs = new Vector();
	private final int staticTfb = 30;


	public AuraAnim( vector loc)	{
        super(loc);
		setImages( staticImages );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
		//setOffs( staticOffs );
	}





}
