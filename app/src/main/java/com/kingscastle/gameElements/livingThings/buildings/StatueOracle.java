package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.heroes.R;
import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.LivingQualities;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StatueOracle extends Building {

	private static final String TAG = "StatueOracle";

	private static final RectF staticPerceivedArea = new RectF( -Rpg.sixTeenDp , 0 , Rpg.sixTeenDp , Rpg.thirtyTwoDp );

	private static final Image image = Assets.loadImage(R.drawable.statue_oracle);

	@NonNull
    private static final LivingQualities staticLivingQualities;

	private static Cost cost = new Cost( 500000 , 500000 , 500000 , 0 );

	public static final Buildings name = Buildings.StatueOracle;

	static{
		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresAge(Age.STEEL); staticLivingQualities.setRequiresTcLvl(16);

		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth(1000);
		staticLivingQualities.setHealth(1000);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);


		staticLivingQualities.setAge( Age.STONE );
		staticLivingQualities.setdHealthLvl(100);
		staticLivingQualities.setdRegenRateAge( 0 );
		staticLivingQualities.setdRegenRateLvl(0);
	}


	private final Image damagedImage = image;
	private final Image deadImage = Assets.loadImage( R.drawable.small_rubble );
	@NonNull
    private RectF percArea = staticPerceivedArea;





	@NonNull
    @Override
	protected LivingQualities getStaticLQ() {
		return staticLivingQualities;
	}



	public StatueOracle(){
		super(name);
	}

	public StatueOracle(@NonNull vector v, Teams t)
	{
		super(name );

		setTeam(t);
		setLoc(v);
	}



	@Override
	public boolean canLevelUp() {
		return false;
	}


	@Override
	public void upgrade(){
	}






	@Override
	public void addFlagToAnim(){
	}



	@Override
	public void loadAnimation( @NotNull MM mm )
	{
		super.loadAnimation( mm );
		if( buildingAnim != null )
			buildingAnim.setMaxDamagedAnimations( 1 );
		backing.setSize(Backing.SMALL);
		adjustBackingOffs( backing );
	}

	@Override
	protected void addAnimationToEm(@NonNull Anim a, boolean sorted, @NonNull EffectsManager em)
	{
		em.add( a , EffectsManager.Position.Sorted );
		em.add( backing, EffectsManager.Position.Behind );
	}




	private static void adjustBackingOffs( @Nullable Backing backing ){
		if( backing != null )
			backing.setOffs(0,Rpg.sixTeenDp);
	}




	@Override
	public Image getImage() {
		return image;
	}
	@Override
	public Image getDamagedImage() {
		return damagedImage;
	}
	@Override
	public Image getDeadImage() {

		return deadImage;
	}

	@Override
	public void loadImages()
	{
	}






	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@NonNull
    @Override
	public RectF getPerceivedArea()
	{
		loadPerceivedArea();
		return percArea;
	}

	public void setPerceivedSpriteArea( RectF perceivedSpriteArea2 ){
	}

	@NonNull
    @Override
	public RectF getStaticPerceivedArea(){
		return percArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea){
	}


	@Override
	protected void loadPerceivedArea(){
		percArea = staticPerceivedArea; //This might cause them to sort properly
	}








	@Override
	public Cost getCosts() {
		return cost;
	}
	public static void setCost(Cost cost) {
		StatueOracle.cost = cost;
	}





	private static ArrayList<vector> staticDamageOffsets;
	void loadDamageOffsets(){
		float dp = Rpg.getDp();
		staticDamageOffsets = new ArrayList<vector>();
		staticDamageOffsets.add(new vector(-4*dp,4*dp));
		staticDamageOffsets.add(new vector(-2*dp,2*dp));
		staticDamageOffsets.add(new vector(4*dp,-4*dp));
		staticDamageOffsets.add(new vector(2*dp,-2*dp));

	}
	@Override
	public ArrayList<vector> getDamageOffsets()
	{
		if( staticDamageOffsets == null )
		{
			loadDamageOffsets();
		}
		return staticDamageOffsets;
	}





	@NonNull
    @Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}




	@NonNull
    @Override
	public String toString() {
		return TAG;
	}
	@Override
	public String getName() {
		return name.getPrintableName();
	}























}