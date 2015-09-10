package com.kingscastle.ui;

import com.kingscastle.Game;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.abilities.Ability;
import com.kingscastle.gameElements.livingThings.abilities.Ability.Abilities;
import com.kingscastle.gameElements.livingThings.orders.Order;
import com.kingscastle.gameElements.livingThings.orders.Order.OrderTypes;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.level.Level;
import com.kingscastle.teams.Team;
import com.kingscastle.ui.buttons.AbilityButton;
import com.kingscastle.ui.buttons.OrderButton;
import com.kingscastle.ui.buttons.SButton;

import java.util.ArrayList;
import java.util.List;


public class SoldierButtonsScroller
{
	private static final String TAG = "HumanoidOptions";

	//private final Scroller unitOptionsScroller;

	private final List<SButton> scrollerButtons   = new ArrayList<>();
	private final List<SButton> allAbilityButtons = new ArrayList<>();
	private final List<SButton> allOrderButtons   = new ArrayList<>();


	private final List<Order> orders;
	private final List<OrderTypes> orderTypes;

	private final List<Ability> abilities;
	private final List<Abilities> abilitiesTypes;


	private int pointerID = -1 ;
	private final vector downAt = new vector( -1 , -1 );

	private final UI ui;
	private final SelectedUI selUI;
    private final AbilityCaster ac;


    public SoldierButtonsScroller(SelectedUI selUI_, UI ui_, AbilityCaster ac_){
		ui = ui_;
		selUI = selUI_;
        ac = ac_;
		abilities = new ArrayList<>();
		abilitiesTypes = new ArrayList<>();

		orderTypes = new ArrayList<>();
		orders = new ArrayList<>();
	}



	protected Humanoid lt;
	protected List<? extends Humanoid> lts;


	protected List<SButton> determineIfAndWhatToDisplay( Humanoid lt )	{
		Game game = Rpg.getGame();
		Level level = game.getLevel();

		this.lt = lt;
		lts = null;

		if( lt == null )
			return null;

		List<SButton> buttons = new ArrayList<>();

		buttons.addAll( getAllOrderButtons( lt ) );
		buttons.addAll( getAllAbilityButtons(lt) );

		//Team team = level.getMM().getTeam( lt.getTeamName() );



		//		if( Settings.yourBaseMode && lt instanceof Humanoid && !(lt instanceof Worker)){
		//
		//			buttons.add( AddToArmyButton.getInstance( game , (Humanoid) lt ,  team.getArmy() , team.getArmyManager() ));
		//		}


		return buttons;
	}

	protected List<SButton> determineIfAndWhatToDisplay( List<? extends Humanoid> lts )
	{
		Game game = Rpg.getGame();
		Level level = game.getLevel();

		lt = null;
		this.lts = lts;

		if( lts == null || lts.isEmpty() )
			return null;


		ArrayList<SButton> buttons = new ArrayList<>();

		buttons.addAll( getAllOrderButtons  ( lts ) );
		buttons.addAll( getAllAbilityButtons( lts ) );

		Team team = level.getMM().getTeam( lts.get(0).getTeamName() );


		return buttons;
	}



	protected List<Humanoid> getHumanoids( List<? extends LivingThing> lts2) {

		if( lts2 != null ){
			ArrayList<Humanoid> units = new ArrayList<>();

			for( LivingThing lt : lts2 )
				if(lt instanceof Humanoid )
					units.add( (Humanoid) lt );

			return units;
		}
		return null;
	}




	protected List<SButton> getAllOrderButtons( Humanoid selectedHumanoid )
	{
		Game kc = Rpg.getGame();

		allOrderButtons.clear();
		orders.clear();
		orderTypes.clear();

		if( selectedHumanoid != null )
			if( selectedHumanoid.getPossibleOrders() != null )
				orders.addAll( selectedHumanoid.getPossibleOrders() );


		ArrayList<Humanoid> unitsOrdering = null ;


		for ( Order o : orders ) {
			if( orderTypes.contains( o.getOrderType() ) )
				continue;

			OrderButton abButton = OrderButton.getInstance( kc.getActivity() , o , selectedHumanoid , unitsOrdering , ui.getSoldierOrders());

            allOrderButtons.add( abButton );

			orderTypes.add( o.getOrderType() );
		}

		return allOrderButtons;
	}


	protected List<SButton> getAllOrderButtons( List<? extends Humanoid> selectedHumanoids )
	{
		Game kc = Rpg.getGame();

		allOrderButtons.clear();
		orders.clear();
		orderTypes.clear();

		if( selectedHumanoids == null )	return allOrderButtons;


		for( LivingThing lt : selectedHumanoids )
			if( lt.getPossibleOrders() != null )
				orders.addAll( lt.getPossibleOrders() );


		List<? extends Humanoid> unitsOrdering = selectedHumanoids;

		OrderButton abButton;

		for ( Order o : orders )
		{
			if( orderTypes.contains( o.getOrderType() ) )
				continue;

			abButton = OrderButton.getInstance( kc.getActivity() , o , null , unitsOrdering , ui.getSoldierOrders() );

            allOrderButtons.add( abButton );

			orderTypes.add( o.getOrderType() );
		}


		return allOrderButtons;
	}





	protected List<SButton> getAllAbilityButtons( Humanoid selectedHumanoid  )
	{
        Game kc = Rpg.getGame();
		allAbilityButtons.clear();

		abilities.clear();
		abilitiesTypes.clear();

		if( selectedHumanoid != null )
			abilities.addAll( selectedHumanoid.getAbilities() );


		for ( Ability ab : abilities )
		{
			if( abilitiesTypes.contains( ab.getAbility() ) )
				continue;

			AbilityButton abButton = AbilityButton.getInstance( kc.getActivity() , ab , ac);
			allAbilityButtons.add(abButton);
			abilitiesTypes.add( ab.getAbility() );
		}

		return allAbilityButtons;
	}

	protected List<SButton> getAllAbilityButtons( List<? extends Humanoid> selectedHumanoids )
	{
        Game kc = Rpg.getGame();
		allAbilityButtons.clear();

		abilities.clear();
		abilitiesTypes.clear();


		if( selectedHumanoids != null )
			for(LivingThing lt : selectedHumanoids )
				abilities.addAll( lt.getAbilities() );



		for ( Ability ab : abilities )
		{
			if( abilitiesTypes.contains( ab.getAbility() ) )
				continue;

			AbilityButton abButton = AbilityButton.getInstance( kc.getActivity() , ab , ac );
			allAbilityButtons.add(abButton);
			abilitiesTypes.add( ab.getAbility() );
		}

		return allAbilityButtons;
	}





	public void showScroller( Humanoid lt ){
		List<? extends SButton> buttons = determineIfAndWhatToDisplay( lt );
		if( buttons != null )
			selUI.displayTheseInRightScroller( buttons , TAG );
	}

	public void showScroller( List<? extends Humanoid> lts ){
		List<? extends SButton> buttons = determineIfAndWhatToDisplay( lts );
		if( buttons != null )
			selUI.displayTheseInRightScroller( buttons , TAG );
	}




	public void hideScroller(){
		selUI.hideMyScrollerButtons( TAG );
	}

	public void resetScroller(){
		if( TAG.equals(selUI.getButtonsId()) )
			if( lt != null )
				showScroller( lt );
			else
				showScroller( lts );
	}






	public void pointerLeftScreen(int pointer)
	{
		if( pointer == pointerID ){
			pointerID = -1;
			downAt.set( -1 , -1 );
		}
	}























}
