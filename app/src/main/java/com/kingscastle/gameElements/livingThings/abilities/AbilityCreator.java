package com.kingscastle.gameElements.livingThings.abilities;




public class AbilityCreator
{


    public static Ability getAbility(  AbilityParams params )
	{
		return params.getAbilityToBeCopied().newInstance(params.getTarget());
	}
}
