package com.kingscastle.effects.animations;




import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class RainbowStrikeAnim extends Anim
{

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.spell_rainbow, 5, 5);
	private final int staticTfb=50;


	public RainbowStrikeAnim(  vector loc )	{
        super(loc);
		setImages( staticImages );
		setTbf( staticTfb );
		setPaint( Rpg.getXferAddPaint() );
		onlyShowIfOnScreen = true;
	}



	@Override
	public void paint(  Graphics g ,  vector v ) {
		Image image = getImage();
		if( image != null )
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , getPaint() );
	}

}
