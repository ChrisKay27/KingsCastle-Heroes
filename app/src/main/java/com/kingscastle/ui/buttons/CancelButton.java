package com.kingscastle.ui.buttons;

import android.app.Activity;
import android.graphics.Paint;



import com.kingscastle.framework.Image;
import com.kingscastle.framework.implementation.ImageDrawable;


public class CancelButton extends SButton
{

    private static final Image buttonIcon = null;//Assets.loadImage(R.drawable.stop_icon);
	protected static final ImageDrawable buttonIconDrawable = new ImageDrawable( buttonIcon.getBitmap() , new Paint());


	public CancelButton( Activity daveOsborn , OnClickListener ocl ){
		super(daveOsborn);
		setForeground(buttonIconDrawable);
		setOnClickListener(ocl);
	}



    public static SButton getInstance(Activity a,
			OnClickListener ocl) {
		return new CancelButton( a , ocl );
	}

}
