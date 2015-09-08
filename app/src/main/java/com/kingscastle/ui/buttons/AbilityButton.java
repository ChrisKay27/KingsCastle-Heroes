package com.kingscastle.ui.buttons;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.kingscastle.framework.implementation.ImageDrawable;
import com.kingscastle.gameElements.livingThings.abilities.Ability;
import com.kingscastle.ui.AbilityCaster;

import org.jetbrains.annotations.NotNull;


public class AbilityButton extends SButton
{
	private final Ability ab;
    private final AbilityCaster ac;

	private AbilityButton( Activity a, @NotNull Ability ability ,@NotNull final AbilityCaster ac_ ) {
		super(a);

        ab = ability;
        ac = ac_;

		if( ability.getIconImage() != null ){
			ImageDrawable id = new ImageDrawable( ability.getIconImage().getBitmap() , 0 , 0 , new Paint());
			setBackgroundDrawable(id);
		}

		setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				ac.setPendingAbility(ab);
			}
		});
	}

    @NotNull
	public Ability getAbility() {
		return ab;
	}



	@Nullable
    public static AbilityButton getInstance( Activity a , @NotNull Ability ability ,@NotNull AbilityCaster ac ){
		return new AbilityButton( a , ability , ac);
	}



	@NonNull
    @Override
	public AbilityButton clone(){
		return new AbilityButton( a , ab , ac);
	}









}
