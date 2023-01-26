package com.kingscastle.effects.animations;


import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;



import com.kingscastle.effects.EffectsManager;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.heroes.R;
import com.kingscastle.gameUtils.vector;

public class LightEffect2 extends Anim
{
	private final static Image img = Assets.loadImage(R.drawable.light_effect);
	
    private static Paint clearPaint = new Paint();
	static{
		clearPaint.setXfermode(new PorterDuffXfermode( PorterDuff.Mode.MULTIPLY ));
	}


	public LightEffect2(  vector loc ){
        super( loc);
        setImage(img);
		setLooping(true);
		setAliveTime(Integer.MAX_VALUE);
		setPaint(clearPaint);
	}




	@Override
	public void paint(  Graphics g ,  vector v ) {
		Image image = getImage();
		if( image != null )
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , getPaint() );
	}

	
	@Override
	public EffectsManager.Position getRequiredPosition() {
		return EffectsManager.Position.LightEffectsInFront;

	}
}
