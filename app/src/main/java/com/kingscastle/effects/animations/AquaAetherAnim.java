package com.kingscastle.effects.animations;




import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class AquaAetherAnim extends Anim
{

	private static final List<Image> staticImages = null;
	private final int staticTfb = 50;


	public AquaAetherAnim(  vector loc )
	{
		setImages( staticImages );

		setTbf( staticTfb );
		setPaint( Rpg.getXferAddPaint() );
		onlyShowIfOnScreen = true;
	}

	void loadImages()
	{
		//		if( staticImages == null )
		//			staticImages = Assets.loadAnimationImages( R.drawable.aqua_aether , 5 , 3 );

		setImages( staticImages );
	}


	@Override
	public void paint(  Graphics g ,  vector v )
	{
		Image image = getImage();
		if( image != null )
		{
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , getPaint() );
		}

	}
}
