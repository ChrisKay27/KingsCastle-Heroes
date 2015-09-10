package com.kingscastle.ui;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class SelectedSoldiers {

    private static final String TAG = SelectedSoldiers.class.getSimpleName();

    @Nullable
    private Building selectedBuilding;
	@Nullable
    private Humanoid selectedHumanoid;
	@Nullable
    private GameElement selectedThing;
    @Nullable
    private List<GameElement> selectedGameElements;
	@Nullable
    private List<Humanoid> selectedHumanoids;



	public void setSelected(@NotNull Humanoid u) {
        Log.d(TAG, "setSelected Humanoid:" + u);
        if (selectedHumanoid != null)
            selectedHumanoid.setSelected(false);

        selectedHumanoid = u;
        selectedHumanoid.setSelected(true);
	}

    public void setSelected(@NotNull Building b) {
        setSelectedBuilding(b);
    }


	public void setSelected(@NotNull GameElement ge) {
		if (selectedThing != null)
			selectedThing.setSelected(false);

		selectedThing = ge;
		selectedThing.setSelected(true);
	}


	public void setSelectedGameElements(@NotNull List<? extends GameElement> ges) {
		clearSelected();

        for( GameElement ge : ges) {
            ge.setSelected(true);
            if( ge instanceof LivingThing )
                ((LivingThing)ge).setSelectedColor(Color.YELLOW);
        }
        selectedGameElements = new ArrayList<>(ges);
	}

    public void setSelectedHumanoids(@NotNull List<Humanoid> ges) {
        clearSelected();

        for( GameElement ge : ges) {
            ge.setSelected(true);
            if( ge instanceof LivingThing )
                ((LivingThing)ge).setSelectedColor(Color.YELLOW);
        }
        selectedHumanoids = ges;
    }

/*
	public void setSelectedHumanoid(LivingThing u) {
		clearSelected();

		selectedHumanoid = u;
		selectedThing = u;
		if (selectedHumanoid != null) {

			selectedHumanoid.setSelected(true);
			selectedHumanoid.setSelectedColor(Color.YELLOW);
		}
	}*/


	public void setSelectedBuilding(@NotNull Building b) {
		clearSelected();

		selectedBuilding = b;
		selectedThing = b;

        selectedBuilding.setSelected(true);
        selectedBuilding.setSelectedColor(Color.YELLOW);
    }


	public void clearSelected() {
        Log.d(TAG, "clearSelected");
		if (selectedThing != null) {
			selectedThing.setSelected(false);
			// selectedBuilding.hideHealthPercentage();
			selectedThing = null;
		}


		clearSelectedBuilding();

		clearSelectedHumanoid();

        clearSelectedThings();
	}

	public void clearSelectedBuilding() {
		LivingThing selectedBuilding = this.selectedBuilding;
		if (selectedBuilding == null)
			return;

		selectedBuilding.setSelected(false);
		this.selectedBuilding = null;
	}

	public void clearSelectedHumanoid() {
		LivingThing selectedHumanoid = this.selectedHumanoid;
		if (selectedHumanoid == null)
			return;

		selectedHumanoid.setSelected(false);
		this.selectedHumanoid = null;
	}

	public void clearSelectedThings() {
		List<GameElement> selectedThings = this.selectedGameElements;
		if (selectedThings == null)
			return;

		for (GameElement ge : selectedThings)
			ge.setSelected(false);

		this.selectedGameElements = null;
	}





	public synchronized void setUnSelected(@NotNull GameElement ge) {

		if (selectedHumanoid == ge) {
			selectedHumanoid = null;
			ge.setSelected(false);
			return;
		} else if (selectedBuilding == ge) {
			selectedBuilding = null;
			ge.setSelected(false);
		} else if (selectedThing == ge) {
			selectedThing = null;
			ge.setSelected(false);
		}


		if (selectedGameElements != null) {
            if (selectedGameElements.remove(ge))
                ge.setSelected(false);

            if (selectedGameElements.isEmpty())
                selectedGameElements = null;
        }
	}



    public synchronized void setUnSelected(@Nullable Humanoid u) {
        if( u != null ) {
            if (selectedHumanoid == u) {
                selectedHumanoid = null;
                u.setSelected(false);
            }

            if (selectedHumanoids != null) {
                if (selectedHumanoids.remove(u))
                    u.setSelected(false);

                if (selectedHumanoids.isEmpty())
                    selectedHumanoids = null;
            }
        }
    }

    public synchronized void setUnSelected(@Nullable Building b) {
        if (selectedBuilding == b && b != null) {
            selectedBuilding = null;
            b.setSelected(false);
        }
    }



	public void moveSelected(vector inDirection) {
        Humanoid u = getSelectedHumanoid();
        if (u != null) {
            Log.d(TAG , "moveSelected");
			u.walkToAndStayHereAlreadyCheckedPlaceable(null);
            u.getLegs().act(inDirection, true);
		}
	}

	//	public boolean moveSelectedHumanoids(Vector dest) {
	//		if( getSelectedHumanoids() != null ) {
	//			return SquareFormation.staticMoveTroops(getSelectedHumanoids(), dest);
	//
	//		} else if (getSelectedSquad() != null) {
	//			return getSelectedSquad().setGroupHere(dest);
	//		}
	//
	//		return false;
	//	}



	public boolean somethingIsSelected() {
		if (getSelectedThing() != null || getSelectedHumanoids() != null
				|| getSelectedHumanoid() != null || getSelectedBuilding() != null)
			return true;
		else
			return false;
	}

	public boolean multipleThingsAreSelected() {
		if (getSelectedHumanoids() != null)
			return true;
		else
			return false;
	}



	@Nullable
    public Building getSelectedBuilding() {
		return selectedBuilding;
	}

	@Nullable
    public GameElement getSelectedThing() {
		return selectedThing;
	}

    @Nullable
    public Humanoid getSelectedHumanoid() {
        if (selectedHumanoid != null)
            selectedHumanoid.setSelected(true);

        return selectedHumanoid;
    }

    @Nullable
    public List<Humanoid> getSelectedHumanoids() {
		return selectedHumanoids;
	}

}
