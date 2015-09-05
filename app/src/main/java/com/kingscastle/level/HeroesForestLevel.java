package com.kingscastle.level;

import com.kingscastle.gameElements.livingThings.army.HumanWizard;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

/**
 * Created by Chris on 8/31/2015 for Heroes
 */
public class HeroesForestLevel extends HeroesLevel {

    private HumanWizard hero;
    @Override
    protected void subOnCreate() {
        super.subOnCreate();

        hero = new HumanWizard(new vector(getLevelWidthInPx()/2,getLevelHeightInPx()/2), Teams.BLUE);
        mm.add(hero);


    }

    @Override
    protected void subOnStart() {
        super.subOnStart();
        ui.setSelected(hero);
    }
}
