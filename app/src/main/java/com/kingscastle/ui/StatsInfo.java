package com.kingscastle.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Healer;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MageSoldier;
import com.kingscastle.gameElements.livingThings.SoldierTypes.RangedSoldier;
import com.kingscastle.gameElements.livingThings.buildings.AttackingBuilding;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameUtils.vector;


class StatsInfo
{

	@Nullable
    private static LivingThing selectedHumanoid;

	@NonNull
    private static final TextLabel statsLabel1;
	@NonNull
    private static final TextLabel statsLabel2;

	@NonNull
    private static final vector stats1Location;
	@NonNull
    private static final vector stats2Location;

	@NonNull
    private static final vector finalStats1Location;
	@NonNull
    private static final vector finalStats2Location;

	@NonNull
    private static final Paint statPaint;

	private static final float HEIGHT_PLUS_TWENTY_FIVE_DP;



	static
	{

		int dp = (int) Rpg.getDp();
		HEIGHT_PLUS_TWENTY_FIVE_DP =  Rpg.getHeight() + 25 * dp;

		statPaint = Palette.getPaint(Paint.Align.CENTER, Color.WHITE);//Layout.whiteCenter;



		finalStats1Location = new vector( Rpg.getWidthDiv2() , Rpg.getHeight() - 2*statPaint.getFontSpacing() );
		finalStats2Location = new vector( Rpg.getWidthDiv2() , Rpg.getHeight() - statPaint.getFontSpacing() );

		stats1Location = new vector( Rpg.getWidthDiv2() , HEIGHT_PLUS_TWENTY_FIVE_DP );
		stats2Location = new vector( Rpg.getWidthDiv2() , HEIGHT_PLUS_TWENTY_FIVE_DP );

		statsLabel1 = new TextLabel( "" , stats1Location , statPaint );
		statsLabel2 = new TextLabel( "" , stats2Location , statPaint );


		//statsDisplayOffset = new Vector( HEIGHT_PLUS_HUNDRED_DP , 0 );

	}



	public static void act()
	{

		if( selectedHumanoid == null )
		{
			return;
		}

		if( stats1Location.y > finalStats1Location.y )
		{
			stats1Location.y -= 7;
		}
		else if( stats1Location.y <= finalStats1Location.y )
		{
			stats1Location.y = finalStats1Location.y;
		}

		if( stats2Location.y > finalStats2Location.y )
		{
			stats2Location.y -= 7;
		}
		else if( stats2Location.y <= finalStats2Location.y )
		{
			stats2Location.y = finalStats2Location.y;
		}


		//		if( statsDisplayOffset.x < 0 )
		//		{
		//			statsDisplayOffset.x += 5;
		//		}
		//		else if( statsDisplayOffset.x > 0 )
		//		{
		//			statsDisplayOffset.x = 0;
		//		}


	}



	public static void paint( @NonNull Graphics g )
	{

		if ( selectedHumanoid != null )
		{

			g.drawTextLabel( statsLabel1 );
			g.drawTextLabel( statsLabel2 );
		}

	}



	@Nullable
    public static LivingThing getSelectedHumanoid() {
		return selectedHumanoid;
	}



	@SuppressLint("DefaultLocale")
	public static void setSelectedHumanoid( @Nullable LivingThing selectedHumanoid )
	{


		if( selectedHumanoid != null )
		{
			InfoMessage.getInstance().setMessage( null , 0 );
			int damage = selectedHumanoid.getAQ() != null ? selectedHumanoid.getAQ().getDamage() : 0;

			String speed = String.format( "%.2f" , ( selectedHumanoid.attributes.getSpeed() / Rpg.getDp() ) );
			String msg = "Type: " + getSoldierType( selectedHumanoid ) + "Atk: "+ damage + getOptionalInfo( selectedHumanoid );
			String msg2 = "MaxHp: " + selectedHumanoid.attributes.getFullHealth() + "  Speed: " + speed + getOptionalInfo2( selectedHumanoid );

            statsLabel1.setMsg( msg );
			statsLabel2.setMsg( msg2 );

			stats1Location.y = HEIGHT_PLUS_TWENTY_FIVE_DP;
			stats2Location.y = HEIGHT_PLUS_TWENTY_FIVE_DP + statPaint.getFontSpacing();
		}



		StatsInfo.selectedHumanoid = selectedHumanoid;
	}


	private static final String RANGE = "  Range: ";
	private static final String MELeE    = "Melee";
	private static final String EMPTY    = "";
	private static final String METERS    = "m";

	@NonNull
    private static String getOptionalInfo(LivingThing selHumanoid)
	{
		if( selHumanoid instanceof Building)
		{
			return EMPTY;
		}
		else if( selHumanoid.getAQ() == null )
		{
			return EMPTY;
		}

		int range = (int) (selHumanoid.getAQ().getAttackRange()/10);

		if( selHumanoid instanceof MageSoldier)
		{
			return RANGE + range + METERS;
		}
		else if( selHumanoid instanceof RangedSoldier)
		{
			return RANGE + range + METERS;
		}
		else if( selHumanoid instanceof Healer)
		{
			return RANGE + range + METERS;
		}
		else if( selHumanoid instanceof AttackingBuilding)
		{
			return RANGE + range + METERS;
		}
		else
		{
			return RANGE + MELeE;
		}

	}

	private static final String BUFF = "  Buff: ";
	//	private static final String DAMAGEBUFF = "Damage";
	//	private static final String ARMORBUFF = "Armor";
	//	private static final String ATTACKRATEBUFF = "Attack Rate";
	//	private static final String HEALINGBUFF = "Healing Buff";

	@NonNull
    private static String getOptionalInfo2( LivingThing selHumanoid )
	{

		//		if( selHumanoid instanceof WizardBuffer2 )
		//		{
		//			WizardBuffer2 wb = (WizardBuffer2) selHumanoid;
		//
		//			return BUFF + wb.getAbilityMessage();
		//		}

		return EMPTY;
	}


	private static final String MELEE    = "Melee    ";
	private static final String RANGED   = "Ranged   ";
	private static final String HEALER   = "Healer   ";
	private static final String BUILDING = "Building ";
	private static final String MAGE     = "Mage     ";

	@NonNull
    private static String getSoldierType( LivingThing selHumanoid )
	{
		if( selHumanoid instanceof MageSoldier )
		{
			return MAGE;
		}
		else if( selHumanoid instanceof Healer )
		{
			return HEALER;
		}
		else if( selHumanoid instanceof RangedSoldier )
		{
			return RANGED;
		}
		else if( selHumanoid instanceof AttackingBuilding )
		{
			return RANGED;
		}
		else if( selHumanoid instanceof Building )
		{
			return BUILDING;
		}
		else
		{
			return MELEE;
		}

	}























}
