package com.kingscastle.effects.animations;




import com.kingscastle.heroes.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class IcicleAnim extends Anim {

	private static final List<Image> Wimages, NWimages, Nimages,
	NEimages, Eimages, SEimages, Simages, SWimages;
	private static final int staticTfb=80;
	private static final int imageid = R.drawable.icicles;



	static
	{
		Image icicles = Assets.loadImage(imageid);
		Wimages = Assets.loadAnimationImages( icicles, 8, 8, 0, 8 , false );
		NWimages = Assets.loadAnimationImages( icicles, 8, 8, 1, 8 , false );
		Nimages = Assets.loadAnimationImages( icicles, 8, 8, 2, 8 , false );
		NEimages = Assets.loadAnimationImages( icicles , 8, 8, 3, 8 , false );
		Eimages = Assets.loadAnimationImages( icicles , 8, 8, 4, 8 , false );
		SEimages = Assets.loadAnimationImages( icicles , 8, 8, 5, 8 , false );
		Simages = Assets.loadAnimationImages( icicles , 8, 8, 6, 8 , false );
		SWimages = Assets.loadAnimationImages( icicles , 8, 8, 7, 8 , true );
	}



	public IcicleAnim( vector loc, int dir){
        super(loc);
        setImages(getImages(dir));

		setTbf(staticTfb);
		setLooping(true);
		onlyShowIfOnScreen = true;
		LightEffect le = new LightEffect(this.loc, LightEffect.LightEffectColor.LIGHT_BLUE);
		le.setScale(0.5f);
		add(le, true);
	}


	@Override
	public void paint( Graphics g,  vector v)
	{
		vTemp.set( v );
		vTemp.add( offs );


		for(int i = addedBehind.size() - 1 ; i > -1 ; --i )
			addedBehind.get( i ).paint( g , vTemp );

		Image image = getImage();
		if( image != null )
			g.drawImage( image , v.getIntX() - image.getWidthDiv2() , v.getIntY() - image.getHeightDiv2() );


		for( int i = addedInFront.size() - 1 ; i > -1 ; --i )
			addedInFront.get(i).paint( g , vTemp );

	}

    private static List<Image> getImages(int dir){
        switch (dir) {
            default:
            case 0:
                return Wimages;
            case 1:
                return NWimages;
            case 2:
                return Nimages;
            case 3:
                return NEimages;
            case 4:
                return Eimages;
            case 5:
                return SEimages;
            case 6:
                return Simages;
            case 7:
                return SWimages;
        }
    }
}