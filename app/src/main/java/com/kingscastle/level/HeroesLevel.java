package com.kingscastle.level;

import android.graphics.RectF;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.DecoAnimation;
import com.kingscastle.effects.animations.StasisZoneAnim;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.GenericGameElement;
import com.kingscastle.gameElements.Tree;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.livingThings.army.KratosMedArm;
import com.kingscastle.gameElements.livingThings.army.UndeadSkeletonBowman;
import com.kingscastle.gameElements.livingThings.army.ZombieMedium;
import com.kingscastle.gameElements.livingThings.army.ZombieStrong;
import com.kingscastle.gameElements.livingThings.army.ZombieWeak;
import com.kingscastle.gameUtils.Difficulty;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.heroes.PlayerAchievements;
import com.kingscastle.teams.HumanPlayer;
import com.kingscastle.teams.Team;
import com.kingscastle.teams.Teams;
import com.kingscastle.teams.races.HumanRace;
import com.kingscastle.teams.races.UndeadRace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public abstract class HeroesLevel extends Level{
    private static final String TAG = HeroesLevel.class.getSimpleName();
    public static final String DONT_ADD_SCORE = "DontAddScore";

    private int startOnRound = 1;
    private long checkMonstersArePathingAt;
    private Difficulty difficulty;
    protected final List<RectF> noBuildZones = new ArrayList<>();
    private boolean roundActive = true;

    private long playersScore;
    private final PlayerAchievements playerAchievements = new PlayerAchievements();



    @Override
    protected void subOnCreate() {

        hPlayer = new HumanPlayer();
        mm.add(Team.getNewHumanInstance(hPlayer, new HumanRace(), mm.getGridUtil()));

        hPlayer.setLives((int) (hPlayer.getLives() / difficulty.multiplier));

        mm.add(Team.getNewInstance(Teams.RED, new UndeadRace(), mm.getGridUtil()));

        hPlayer.addLVCL(new HumanPlayer.onLivesValueChangedListener() {
            @Override
            public void onLivesValueChanged(int newLivesValue) {
                if (newLivesValue <= 0)
                    playerHasLost();
            }
        });


        createBorder();
    }

    protected void setUpStartAndEndLocs(@NonNull List<Pair<vector, vector>> startEndLocPairs){
        for( Pair<vector, vector> startEndLocPair : startEndLocPairs ) {
            vector startLoc = startEndLocPair.first;
            vector endLoc = startEndLocPair.second;

            RectF noBuildZone1 = new RectF(-1 * Rpg.sixTeenDp, -1 * Rpg.sixTeenDp, 1 * Rpg.sixTeenDp, 1 * Rpg.sixTeenDp);
            RectF noBuildZone2 = new RectF(noBuildZone1);
            noBuildZone1.offset(startLoc.x, startLoc.y);
            noBuildZone2.offset(endLoc.x, endLoc.y);
            noBuildZones.add(noBuildZone1);
            noBuildZones.add(noBuildZone2);
            ui.bb.addNoBuildZone(noBuildZone1, noBuildZone2);

            //Setup start and end locations animations
            Anim startAnim = new StasisZoneAnim(startLoc);
            startAnim.setLooping(true);
            startAnim.onlyShowIfOnScreen = false;
            startAnim.setScale(0.25f);
            mm.getEm().add(startAnim, EffectsManager.Position.Behind);

            Anim endAnim = new StasisZoneAnim(endLoc);
            endAnim.setLooping(true);
            endAnim.setScale(0.25f);
            endAnim.onlyShowIfOnScreen = false;
            mm.getEm().add(endAnim, EffectsManager.Position.Behind);
        }
    }

    private long nextSpawn;

    @Override
    protected void subAct() {
        if( paused )
            return;

        if( nextSpawn < GameTime.getTime() ){
            nextSpawn = GameTime.getTime() + 3000;

            Unit spawn;
            double rand = Math.random();
            if( rand < 0.2 )
                spawn = new ZombieWeak(new vector(getLevelWidthInPx()*Math.random(), getLevelHeightInPx()*Math.random()), Teams.RED);
            else if( rand < 0.4 )
                spawn = new ZombieMedium(new vector(getLevelWidthInPx()*Math.random(), getLevelHeightInPx()*Math.random()), Teams.RED);
            else if( rand < 0.6 )
                spawn = new ZombieStrong(new vector(getLevelWidthInPx()*Math.random(), getLevelHeightInPx()*Math.random()), Teams.RED);
            else if( rand < 0.8 )
                spawn = new KratosMedArm(new vector(getLevelWidthInPx()*Math.random(), getLevelHeightInPx()*Math.random()), Teams.RED);
            else
                spawn = new UndeadSkeletonBowman(new vector(getLevelWidthInPx()*Math.random(), getLevelHeightInPx()*Math.random()), Teams.RED);
            mm.add(spawn);
            spawn.aq.setFocusRangeSquared(10000*10000*Rpg.getDpSquared());

        }
    }

    @Override
    protected void createBorder() {
        //Create a border
        final int lvlWidthPx = getLevelWidthInPx();
        final int lvlHeightPx = getLevelHeightInPx();

        GameElement ge = getBorderElement(new vector());
        final int treeWidth = (int) ge.getStaticPerceivedArea().width();
        final int treeHeight = (int) ge.getStaticPerceivedArea().height();
        final int numHorz = lvlWidthPx/treeWidth;
        final int numVert = lvlHeightPx /treeHeight;


        //Top wall
        for( int i = 0 ; i < numHorz ; ++i )
            addDecoGE(getBorderElement(new vector(treeWidth / 2 + i * treeWidth, treeHeight / 2)));


        //Bottom wall of trees
        for( int i = 0 ; i < numHorz ; ++i )
            addDecoGE(getBorderElement(new vector((treeWidth / 2) + (i * treeWidth), lvlHeightPx - (treeHeight / 2))));


        //Left wall of trees
        for( int j = 0 ; j < numVert ; ++j )
            addDecoGE(getBorderElement(new vector((treeWidth / 2) , (treeHeight / 2) + (j * treeHeight))));


        //Right wall of trees
        for( int j = 0 ; j < numVert ; ++j )
            addDecoGE(getBorderElement(new vector(lvlWidthPx - (treeWidth / 2) , (treeHeight / 2) + (j * treeHeight))));
    }

    @NonNull
    protected GameElement getBorderElement(vector v){
        return new Tree(v);
    }

    protected void addDeco(vector loc, int drawable) {
        mm.getEm().add(new DecoAnimation(loc, drawable));
    }

    protected void addDecoGE(@NonNull GameElement ge) {
        ge.updateArea();
        mm.getGridUtil().setProperlyOnGrid(ge.area, Rpg.gridSize);
        GridUtil.getLocFromArea(ge.area, ge.getPerceivedArea(), ge.loc);

        boolean ¤‿¤ = false;
        for( RectF noBuildZone: noBuildZones )
            if( RectF.intersects(ge.area,noBuildZone) ){
                ¤‿¤ = true;
                break;
            }
        if( ¤‿¤ ) return;

        if (mm.getCD().checkPlaceable2(ge.area, false))
            mm.addGameElement(ge);
        else {
            Log.d(TAG, "Deco not placeable!");
        }
    }

    protected void addDecoGE(vector loc, @DrawableRes int drawableID) {
        GenericGameElement f = new GenericGameElement(loc,drawableID);
        addDecoGE(f);
    }


    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public long getPlayersScore() {
        return playersScore;
    }




    //*******************************   Abstract Methods   ***************************************//


    //****************************** End Abstract Methods   **************************************//







    //************************  Listener Methods And Interfaces   ********************************//

    //Round Over Listener
    protected final ArrayList<RoundOverListener> rols = new ArrayList<>();


    public interface RoundOverListener{
        void onRoundOver(int roundJustFinished);
    }

    public void addROL(RoundOverListener gol)		   		{	synchronized( rols ){	rols.add( gol );			}  	}
    public boolean removeROL(RoundOverListener gol)		{	synchronized( rols ){	return rols.remove( gol );		}	}

}
