package com.kingscastle.level;



import com.kingscastle.Game;

import java.lang.reflect.Constructor;

/**
 * Created by Chris on 7/10/2015 for Tower Defence
 */
public class Levels {

    public enum TDLevels{
        Forest(HeroesForestLevel.class,null);


        public final Class<? extends HeroesLevel> klass;
        public final TDLevels previousLevel;

        TDLevels(Class<? extends HeroesLevel> aClass, TDLevels prevLevel) {
            klass = aClass;
            previousLevel = prevLevel;
        }

        public TDLevels getNextLevel(){
            switch(this){
                default:
                case Forest:return null;
            }
        }

    }



    public static HeroesLevel get(String levelClass) {

        try
        {
            Class<?> levelKlass = Class.forName(levelClass);

            Constructor<?>[] constrs = levelKlass.getConstructors();

            for( Constructor<?> c : constrs ){
                try{
                    return (HeroesLevel) c.newInstance();
                }catch(Exception e)
                {
                    if( Game.testingVersion ){
                        e.printStackTrace();
                    }
                }
            }

            return new HeroesForestLevel();
        }
        catch( NoClassDefFoundError e )
        {
            if( Game.testingVersion ){
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            if( Game.testingVersion ){
                e.printStackTrace();
            }
            //Log.v( TAG , "Did not find the Class in the livingThing.army. folder " );
        }


        return new HeroesForestLevel();
    }
}
