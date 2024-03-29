package com.kingscastle.framework;

import android.graphics.Bitmap;



import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.DeadHumanAnim;
import com.kingscastle.effects.animations.DeadSkeletonAnim;
import com.kingscastle.framework.Graphics.ImageFormat;
import com.kingscastle.framework.implementation.AndroidImage;
import com.kingscastle.heroes.R;
import com.kingscastle.gameElements.ImageFormatInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assets
{
	public static final Image genericDestroyedBuilding = loadImage( R.drawable.rubble );
	public static final Image smallDestroyedBuilding   = loadImage( R.drawable.rubble );

	private static Anim healSparkles;
	public static Anim blueSparks1;
	public static Anim blueSparks2;
	public static Anim yellowSparks1;//,fire1;

	public static int genericNumOfDyingFrames = 3, genericDyingId = R.drawable.genericexplodingperson;

	@NotNull
    public static final Anim genericDyingAnimation = new DeadHumanAnim( null );//new Animation( null , loadAnimationImages( genericDyingId , genericNumOfDyingFrames ) , 150 , 10000 );

	@NotNull
    public static final Anim deadSkeletonAnim = new DeadSkeletonAnim(null);



	@NotNull
    public static Image[] loadImages(int id, int numHorz, int numVert, int xOffs, int yOffs, int numHorzSpriteSets, int numVertSpriteSets )	{
		return loadImages( id , numHorz , numVert , xOffs , yOffs , numHorzSpriteSets , numVertSpriteSets , true );
	}

    @NotNull
	private static Image[] loadImages(int id, int numHorz, int numVert, int xOffs,
			int yOffs, int numHorzSpriteSets, int numVertSpriteSets, boolean disposeImageAfter)	{

		Image[] images = new Image[numHorz*numVert];

        Image image = Assets.loadImage(id);

        int spriteWidth = image.getWidth()/(numHorzSpriteSets*numHorz);
        int spriteHeight = image.getHeight()/(numVertSpriteSets*numVert);
        int imageLocOffsX = xOffs*spriteWidth*numHorz;
        int imageLocOffsY = yOffs*spriteHeight*numVert;


        for (int j = 0; j < numVert; ++j )
            for (int i = 0; i < numHorz; ++i ) {
                images[i + j * numHorz] = new AndroidImage(Bitmap.createBitmap(
                        image.getBitmap(), i * spriteWidth
                        + imageLocOffsX, j * spriteHeight
                        + imageLocOffsY, spriteWidth,
                        spriteHeight), Graphics.ImageFormat.RGB565);
            }


        if( disposeImageAfter )
            image.dispose();

		return images;
	}


	
    public static Image[] loadImages(int id, int xOffs, int yOffs,int numHorzSpriteSets,int numVertSpriteSets )	{
		return loadImages( id , xOffs , yOffs , numHorzSpriteSets , numVertSpriteSets , true );
	}


	private static Image[] loadImages(int id, int xOffs, int yOffs, int numHorzSpriteSets, int numVertSpriteSets, boolean disposeImageAfter)	{

		Image[] images = new Image[12];

        Image image = Assets.loadImage(id);

        int spriteWidth = image.getWidth()/(numHorzSpriteSets*3);
        int spriteHeight = image.getHeight()/(numVertSpriteSets*4);
        int imageLocOffsX = xOffs*spriteWidth*3;
        int imageLocOffsY = yOffs*spriteHeight*4;

        for (int j = 0; j < 4; ++j )
            for (int i = 0; i < 3; ++i )
            {
                images[i + j * 3] = new AndroidImage(Bitmap.createBitmap(
                        image.getBitmap(), i * spriteWidth
                        + imageLocOffsX, j * spriteHeight
                        + imageLocOffsY, spriteWidth,
                        spriteHeight), Graphics.ImageFormat.RGB565);

            }


        if( disposeImageAfter )
            image.dispose();


		return images;
	}


	@NotNull
    private static List<Image> loadAnimationImages(int id, int numHorzSprites, int numVertSprites, ImageFormat ii)	{
		return loadAnimationImages(  id,  numHorzSprites,  numVertSprites,  ii , true );
	}


	@NotNull
    private static List<Image> loadAnimationImages(int id, int numHorzSprites, int numVertSprites, ImageFormat ii, boolean disposeImageAfter)	{

		ArrayList<Image> images = new ArrayList<>();

        Image image = Assets.loadImage(id,ii);

        int spriteWidth = image.getWidth()/(numHorzSprites);
        int spriteHeight = image.getHeight()/(numVertSprites);

        for ( int j = 0 ; j < numVertSprites ; ++j )
            for ( int i = 0 ; i < numHorzSprites ; ++i ) {
                images.add( new AndroidImage(Bitmap.createBitmap(
                        image.getBitmap(), i * spriteWidth, j * spriteHeight, spriteWidth, spriteHeight), ImageFormat.RGB565));
            }


        if( disposeImageAfter )
            image.dispose();


		return images;
	}

	@NotNull
    public static List<Image> loadAnimationImages(int id,int numHorzSprites,int numVertSprites) {
		return loadAnimationImages(id,numHorzSprites,numVertSprites,ImageFormat.RGB565);
	}


    @NotNull
	private static Image loadImage(int id, ImageFormat i){
		return Rpg.getGame().getGraphics().newImage(id,
				i);
	}

    @NotNull
	public static Image loadImage(int id){
		return Rpg.getGame().getGraphics().newImage(id,
				ImageFormat.RGB565);
	}



    public static List<Image> loadAnimationImages( int id , int numOfFrames )	{
		return loadAnimationImages( id , numOfFrames , true );
	}


    private static List<Image> loadAnimationImages(int id, int numOfFrames, boolean disposeImageAfter)	{
        List<Image> dyingImages = new ArrayList<>();


        Image image = Rpg.getGame().getGraphics().newImage(id, ImageFormat.RGB565);
        int xOffs= image.getWidth()/numOfFrames;

        for ( int i = 0 ; i < numOfFrames ; ++i )
        {
            dyingImages.add( new AndroidImage( Bitmap.createBitmap(
                    image.getBitmap(), i * xOffs, 0,
                    xOffs, image.getHeight()),
                    ImageFormat.RGB565 ) );
        }

        if( disposeImageAfter )
            image.dispose();


		return dyingImages;
	}

	//
	//	/*
	//	 * used for loading Clints animations, all one direction is in one row, only loads one row
	//	 * @param fromRow starts at 0... like usual, even tho its counterIntuitive
	//	 */
	//	public static ArrayList<Image> loadAnimationImages(int id,int totalColumns,int totalRows,int fromRow,int totalImages){
	//		ArrayList<Image> images = new ArrayList<Image>();
	//		try {
	//
	//			Image image = Rpg.getGame().getGraphics().newImage(id, ImageFormat.RGB565);
	//			int xOffs= image.getWidth()/totalColumns;
	//			int yOffs= image.getHeight()/totalRows;
	//			for (int i = 0; i < totalImages; ++i) {
	//				images.add(new AndroidImage(Bitmap.createBitmap(
	//						image.getBitmap(), i * xOffs, yOffs*fromRow,
	//						xOffs, yOffs),
	//						ImageFormat.RGB565));
	//			}
	//
	//		} catch (Throwable e) {
	//			e.printStackTrace();
	//			System.exit(0);
	//		}
	//		return images;
	//	}



    public static List<Image> loadAnimationImages( int id , int totalColumns , int totalRows , int fromRow , int totalImages )
	{
		Image image = Rpg.getGame().getGraphics().newImage( id , ImageFormat.RGB565 );

		return loadAnimationImages( image , totalColumns , totalRows , fromRow , totalImages );
	}


    private static List<Image> loadAnimationImages( Image image, int totalColumns, int totalRows, int fromRow, int totalImages)
	{
		return loadAnimationImages( image , totalColumns , totalRows , fromRow , totalImages , true );
	}


    public static List<Image> loadAnimationImages(  Image image , int totalColumns , int totalRows , int fromRow , int totalImages, boolean disposeImageAfter )
	{
        List<Image> images = new ArrayList<>();

        int xOffs= image.getWidth()/totalColumns;
        int yOffs= image.getHeight()/totalRows;

        for ( int i = 0 ; i < totalImages ; ++i )
        {
            images.add(new AndroidImage(Bitmap.createBitmap(
                    image.getBitmap(), i * xOffs, yOffs*fromRow,
                    xOffs, yOffs),
                    ImageFormat.RGB565));
        }

        if( disposeImageAfter )
            image.dispose();


		return images;
	}


	@NotNull
    public static Anim getGenericDyingAnimation(){
		return genericDyingAnimation;
	}




    private static Anim loadHealingAnimation(){
		return healSparkles = new Anim(null,loadAnimationImages(R.drawable.heal_sparkle,6),150);
	}

	public static Anim getHealSparklesAnimation()
	{
		if(healSparkles==null) {
			return loadHealingAnimation();
		} else {
			return healSparkles;
		}
	}




	//	public static Animation loadFire1Animation()
	//	{
	//		return null;
	//		//return fire1=new Animation(null,loadAnimationImages(R.drawable.flame,12,6,0,12),100,120000,true);
	//	}
	//
	//
	//
	//
	//	public static Animation getFire1Animation()
	//	{
	//		if(fire1!=null) {
	//			return fire1;
	//		} else {
	//			return loadFire1Animation();
	//		}
	//	}




	@NotNull
    public static Image[] loadImages(@NotNull ImageFormatInfo imageFormatInfo)
	{
		int id = imageFormatInfo.getAliveId();
		int xOffs = imageFormatInfo.getxOffs();
		int yOffs = imageFormatInfo.getyOffs();

		int numHorzSpriteSets = imageFormatInfo.getNumHorzSpriteSets();
		int numVertSpriteSets = imageFormatInfo.getNumVertSpriteSets();

		int numHorz = imageFormatInfo.getNumHorzImages();
		int numVert = imageFormatInfo.getNumVertImages();

		if( numHorz == 0 )
			numHorz = 3;

		if( numVert == 0 )
			numVert = 4;


		return loadImages( id , numHorz , numVert , xOffs , yOffs , numHorzSpriteSets , numVertSpriteSets );
	}





    public static <T> List<T> convToArrayList( T[] array) {
//
//        ArrayList<T> aList = new ArrayList<>();
//		for( T t : array )
//			aList.add( t );
		return Arrays.asList(array);
	}














}