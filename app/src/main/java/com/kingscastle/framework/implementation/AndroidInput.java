package com.kingscastle.framework.implementation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.kingscastle.framework.Input;

import java.util.List;

public class AndroidInput implements Input {
	@NonNull
    private final TouchHandler touchHandler;

	public AndroidInput(Activity context, View view, float scaleX, float scaleY) {
		touchHandler = new MultiTouchHandler(context, view, scaleX, scaleY);
	}


	@Override
	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}

	@Override
	public int getTouchX(int pointer) {
		return touchHandler.getTouchX(pointer);
	}

	@Override
	public int getTouchY(int pointer) {
		return touchHandler.getTouchY(pointer);
	}




	@Override
	public List<TouchEvent> getTouchEvents() {
		return touchHandler.getTouchEvents();
	}



}
