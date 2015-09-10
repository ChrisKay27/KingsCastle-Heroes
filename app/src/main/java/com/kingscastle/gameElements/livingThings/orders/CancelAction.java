package com.kingscastle.gameElements.livingThings.orders;

import com.kingscastle.framework.Image;
import com.kingscastle.framework.Input;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameUtils.CoordConverter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class CancelAction extends Order
{

	private static Image iconImage;
    private final List<Humanoid> units = new ArrayList<>();

    //private static CancelAction cancelAction;




	public static CancelAction getInstance()
	{
		//		if( cancelAction == null )
		//		{
		//			cancelAction =
		//		}
		return new CancelAction();
	}




	@Override
	public Image getIconImage()
	{
//		if( iconImage == null )
//			iconImage = Assets.loadImage(R.drawable.stop_icon);

		return iconImage;
	}



	@Override
	public List<? extends Humanoid> getHumanoidsToBeOrdered()
	{
		return units;
	}




	@Override
	public void setHumanoidsToBeOrdered(List<? extends Humanoid> units)
	{
        this.units.clear();
		this.units.addAll(units);
	}



	@Override
	public void setHumanoidToBeOrdered(Humanoid u)
	{
		units.clear();
		units.add( u);
	}



	@Override
	public boolean analyseTouchEvent( Input.TouchEvent event , CoordConverter cc, CD cd)
	{
		makeEveryoneCancel(units);
		//HumanoidOptions.get().resetScroller();
		return true;
	}


	public void makeEveryoneCancel( )
	{
		makeEveryoneCancel(units);
	}



	void makeEveryoneCancel(List<Humanoid> units)
	{

		for( Humanoid u : units )
		{

		}

	}




	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException
	{
	}



	@Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.CANCEL;
	}



	private static final String name = "";//"Cancel Action";

	@Override
	public String toString()
	{
		return name;
	}


}
