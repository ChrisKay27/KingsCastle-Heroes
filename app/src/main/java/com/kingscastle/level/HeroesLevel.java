package com.kingscastle.level;

import android.graphics.RectF;


import android.util.Log;

import com.kingscastle.effects.animations.DecoAnimation;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.GenericGameElement;
import com.kingscastle.gameElements.Tree;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.LivingThingListenerAdapter;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.abilities.Buff;
import com.kingscastle.gameElements.livingThings.abilities.DamageBuff;
import com.kingscastle.gameElements.livingThings.army.HumanWizard;
import com.kingscastle.gameUtils.Difficulty;
import com.kingscastle.gameUtils.LevelUpChecker;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.heroes.PlayerAchievements;
import com.kingscastle.level.Heroes.BuffPickup;
import com.kingscastle.level.Heroes.ForestSpawns;
import com.kingscastle.level.Heroes.Pickup;
import com.kingscastle.level.Heroes.TripleAttackPickup;
import com.kingscastle.teams.HumanPlayer;
import com.kingscastle.teams.Team;
import com.kingscastle.teams.Teams;
import com.kingscastle.teams.races.HumanRace;
import com.kingscastle.teams.races.UndeadRace;
import com.kingscastle.util.ManagerListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public abstract class HeroesLevel extends Level{
    private static final String TAG = HeroesLevel.class.getSimpleName();
    public static final String DONT_ADD_SCORE = "DontAddScore";

    public static final float ENEMY_FOCUS_RANGE_SQUARED = 10000*10000*Rpg.getDpSquared();
    public static final float ALLIED_FOCUS_RANGE_SQUARED = Float.MAX_VALUE;


    protected Humanoid hero;

    private int startOnRound = 1;
    private long checkMonstersArePathingAt;
    private Difficulty difficulty;
    protected final List<RectF> noBuildZones = new ArrayList<>();
    private boolean roundActive = true;

    private long playersScore;
    private final PlayerAchievements playerAchievements = new PlayerAchievements();

    private List<Pickup> pickups = new LinkedList<>();

    private ForestSpawns forestSpawns = new ForestSpawns();




    @Override
    protected void subOnCreate() {
        createBorder();
        hPlayer = new HumanPlayer();
        mm.add(Team.getNewHumanInstance(hPlayer, new HumanRace(), mm.getGridUtil()));

        hPlayer.setLives((int) (hPlayer.getLives() / difficulty.multiplier));

        mm.add(Team.getNewInstance(Teams.RED, new UndeadRace(), mm.getGridUtil()));

        hPlayer.addLVCL(newLivesValue -> {
            if (newLivesValue <= 0)
                playerHasLost();
        });


        if( !fromSavedState ) {
            hero = new HumanWizard(new vector(getLevelWidthInPx() / 2, getLevelHeightInPx() / 2), Teams.BLUE);
            mm.add(hero);
        }
        hero.addLTL(new LivingThingListenerAdapter() {
            @Override
            public void onLevelUp(LivingThing lt) {
                if (lt.attributes.getLevel() == 2)
                    lt.addAbility(new DamageBuff(lt, lt));
                mm.getUI().refreshSelectedUI();
            }

            @Override
            public void onDeath(LivingThing lt) {
                tdg.onPlayerLost();
            }
        });


        getBackground().setCenteredOn(hero.loc);


        //Setup exp adder
        final LivingThingListenerAdapter odl = new LivingThingListenerAdapter() {
            @Override
            public void onDeath(final LivingThing lt) {
                if( lt.getLastHurter() == hero )
                    hero.doOnYourThread(new Runnable() {
                        @Override
                        public void run() {
                            hero.addExp(lt.attributes.getExpGiven());
                        }
                });
            }
        };

        mm.getTeam(Teams.RED).getArmyManager().addListener(new ManagerListener<Humanoid>() {
            @Override
            public boolean onAdded(Humanoid h) {
                h.addLTL(odl);
                h.aq.setFocusRangeSquared(ENEMY_FOCUS_RANGE_SQUARED);
                return false;
            }
            @Override
            public boolean onRemoved(Humanoid humanoid) {
                return false;
            }
        });


    }


    private long nextSpawn;
    private boolean bossisNotAlive = true;
    private long nextPickupSpawn;

    @Override
    protected void subAct() {
        if( paused )
            return;
        getBackground().setCenteredOn(hero.loc);

        updateExpBar(LevelUpChecker.getLevelPercent(hero.attributes.getExp()));

        if( nextSpawn < GameTime.getTime() && bossisNotAlive){
            nextSpawn = GameTime.getTime() + 1000;

            try{
                Humanoid spawn = forestSpawns.getSpawnClass(hero.attributes.getLevel()).getConstructor(vector.class, Teams.class).newInstance(getRandomLocOnMapAndNotOnScreen(), Teams.RED);
                spawn.addLTL(new LivingThingListenerAdapter(){
                    @Override
                    public void onDeath(LivingThing lt) {
                        super.onDeath(lt);

                        if( lt instanceof Humanoid ) {
                            getHumanPlayer().refundCosts(new Cost(((Humanoid) lt).getGoldDropped()));
                            ui.updateScore();
                        }
                    }
                });
                mm.add(spawn);


                //Spawn a boss if the level is right
                Class<? extends Humanoid> bossClass = forestSpawns.getBossSpawnClass(hero.attributes.getLevel());
                if( bossClass != null ) {
                    Humanoid boss = bossClass.getConstructor(vector.class, Teams.class).newInstance(getRandomLocOnMapAndNotOnScreen(), Teams.RED);
                    mm.add(boss);
                    bossisNotAlive = false;
                    boss.addLTL(new LivingThingListenerAdapter(){
                        @Override
                        public void onDeath(LivingThing lt) {
                            bossisNotAlive = true;
                        }
                    });
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if( Math.random() < 0.85 ) {
                try {
                    Humanoid ally = forestSpawns.getAllyClass(hero.attributes.getLevel()).getConstructor(vector.class, Teams.class).newInstance(getRandomLocOnMapAndNotOnScreen(),Teams.BLUE);
                    mm.add(ally);
                    ally.aq.setFocusRangeSquared(ALLIED_FOCUS_RANGE_SQUARED);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }



        if( nextPickupSpawn < GameTime.getTime() ){
            nextPickupSpawn = GameTime.getTime() + 2000 + (long) (Math.random()* 2000);

            vector pickupLoc = new vector(getLevelWidthInPx() * Math.random(), getLevelHeightInPx() * Math.random());


            if( Math.random() < 0.8 ) {
                List<Class<? extends Buff>> buffPickups = forestSpawns.getBuffPickups();
                int buffIndex = (int)(Math.random()*buffPickups.size());
                try {
                    Buff b = buffPickups.get(buffIndex).getConstructor(LivingThing.class, LivingThing.class).newInstance(null,null);
                    BuffPickup bp = new BuffPickup(pickupLoc, b);
                    pickups.add(bp);
                    mm.getEm().add(bp.getAnim());
                } catch (Exception e) {
                    Log.e(TAG, "Exception thrown while trying to create a " + buffPickups.get(buffIndex).getSimpleName());
                   throw new RuntimeException(e);
                }
            }
            else{
                TripleAttackPickup bp = new TripleAttackPickup(pickupLoc);
                pickups.add(bp);
                mm.getEm().add(bp.getAnim());
            }

        }


        Iterator<Pickup> pIt = pickups.iterator();
        while( pIt.hasNext() ){
            Pickup p = pIt.next();
            if(p.isWithinRange(hero.loc) && p.canPickup(hero)) {
                p.pickedUp(mm, hero);
            }
            if( p.isOver() ) {
                pIt.remove();
                p.onOver();
            }
        }
    }

    private void updateExpBar(double levelPercent) {
        ui.updateExpBar(levelPercent);
    }

    public void spawnSoldiers(int num){
        vector randomLocOnMap = getRandomLocOnMap();
        for (int i = 0; i < num; i++) {
            try {
                Humanoid ally = forestSpawns.getAllyClass(hero.attributes.getLevel()).getConstructor(vector.class, Teams.class).newInstance(new vector(randomLocOnMap),Teams.BLUE);
                mm.add(ally);
                ally.aq.setFocusRangeSquared(ALLIED_FOCUS_RANGE_SQUARED);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void spawnWizards(int num){
        vector randomLocOnMap = getRandomLocOnMap();
        for (int i = 0; i < num; i++) {
            try {
                Humanoid ally = forestSpawns.getWizardAllyClass(hero.attributes.getLevel()).getConstructor(vector.class, Teams.class).newInstance(new vector(randomLocOnMap),Teams.BLUE);
                mm.add(ally);
                ally.aq.setFocusRangeSquared(ALLIED_FOCUS_RANGE_SQUARED);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void spawnHealers(int num){

        for (int i = 0; i < num; i++) {
            try {
                Humanoid ally = forestSpawns.getHealerAllyClass(hero.attributes.getLevel()).getConstructor(vector.class, Teams.class).newInstance(new vector(hero.loc),Teams.BLUE);
                mm.add(ally);
                ally.aq.setFocusRangeSquared(ALLIED_FOCUS_RANGE_SQUARED);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private vector getRandomLocOnMapAndNotOnScreen() {
        vector loc = new vector(getLevelWidthInPx()*Math.random(), getLevelHeightInPx()*Math.random());
        RectF screenArea = mm.getUI().getOnScreenArea();
        while(!mm.getCD().checkPlaceable(loc) || screenArea.contains(loc.x, loc.y))
            loc.set(getLevelWidthInPx() * Math.random(), getLevelHeightInPx() * Math.random());


        return loc;
    }

    private vector getRandomLocOnMap() {
        vector loc = new vector(getLevelWidthInPx()*Math.random(), getLevelHeightInPx()*Math.random());
        while(!mm.getCD().checkPlaceable(loc))
            loc.set(getLevelWidthInPx()*Math.random(), getLevelHeightInPx()*Math.random());

        return loc;
    }


    @Override
    protected void subOnStart() {
        super.subOnStart();
        ui.setSelected(hero);
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




    protected GameElement getBorderElement(vector v){
        return new Tree(v);
    }

    protected void addDeco(vector loc, int drawable) {
        mm.getEm().add(new DecoAnimation(loc, drawable));
    }

    protected void addDecoGE( GameElement ge) {
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

    protected void addDecoGE(vector loc,  int drawableID) {
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

    public Humanoid getHero() {
        return hero;
    }

    public void setHero(Humanoid hero) {
        this.hero = hero;
    }


    //*******************************   Abstract Methods   ***************************************//


    //****************************** End Abstract Methods   **************************************//







    //************************  Listener Methods And Interfaces   ********************************//

//    //Round Over Listener
//    protected final ArrayList<RoundOverListener> rols = new ArrayList<>();
//
//
//    public interface RoundOverListener{
//        void onRoundOver(int roundJustFinished);
//    }
//
//    public void addROL(RoundOverListener gol)		   		{	synchronized( rols ){	rols.add( gol );			}  	}
//    public boolean removeROL(RoundOverListener gol)		{	synchronized( rols ){	return rols.remove( gol );		}	}

}
