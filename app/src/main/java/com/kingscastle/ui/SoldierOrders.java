package com.kingscastle.ui;


import com.kingscastle.framework.Input;
import com.kingscastle.gameElements.livingThings.orders.Order;
import com.kingscastle.gameUtils.CoordConverter;

public class SoldierOrders implements TouchEventAnalyzer
{
	//private static final String TAG = "HumanoidOrders";


	private Order pendingOrder;

	private final CoordConverter cc;
	private final UI ui;

    SoldierOrders(CoordConverter cc_, UI ui){
		cc = cc_;
		this.ui = ui;
	}

    @Override
    public boolean analyzeTouchEvent(Input.TouchEvent e) {
        return false;
    }

	public boolean isThereAPendingOrder() {
		if( pendingOrder == null )
			return false;
		else
			return true;
	}



	public boolean giveOrders( Input.TouchEvent event )
	{
		if( pendingOrder == null )
			return false;


		if ( pendingOrder.analyseTouchEvent( event , cc, ui.getCD() ) )	{
			pendingOrder = null;
			return true;
		}


		return false;
	}



	public void setPendingOrder( Order order )
	{
		switch( order.getOrderType() )
		{
		default:
			break;
		case CANCEL:
			if( order.analyseTouchEvent( null , cc, ui.getCD() ) )
				pendingOrder = null ;
			return;

		case GO_HERE:
			break;

		case ATTACK_THIS:
			break;

		case CHANGE_STANCE:
			order.analyseTouchEvent( null , cc, ui.getCD() );
			InfoMessage.getInstance().setMessage( "Stance changed."  , 3000 );

			return;
		}

		pendingOrder = order;
	}




	public void cancel()
	{
		pendingOrder = null;
	}



	public Order getPendingOrder()
	{
		return pendingOrder;
	}


	public void clearOrder()
	{
		pendingOrder = null;
	}



}
