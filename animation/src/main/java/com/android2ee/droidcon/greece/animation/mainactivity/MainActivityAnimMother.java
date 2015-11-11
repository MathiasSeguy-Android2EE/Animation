/**
 * <ul>
 * <li>MainActivityAnimMother</li>
 * <li>com.android2ee.droidcon.greece.animation.mainactivity</li>
 * <li>03/11/2015</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.droidcon.greece.animation.mainactivity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android2ee.droidcon.greece.animation.R;

/**
 * Created by Mathias Seguy - Android2EE on 03/11/2015.
 * Declare in this class all the attributes you need for managing your animation for all versions
 */
public abstract class MainActivityAnimMother implements MainActivityAnimIntf{
    /**
     * The activity associated
     */
    MainActivity activity;
    /***********************************************************
     * Managing the Settings Animation :
     * In the actionBar when you click on the nut menuItem a panel go down,
     *
     * This panel is called Setting
     **********************************************************/
    /** The root layout of the panel */
    ScrollView scvRootSettingPanel =null;
    /** The Layout thats contains the button (to invalidate it when buttons change size) */
    LinearLayout lilContentSettingPanel =null;
    /** A button is that panel*/
    Button btnDoNotPress=null,btnDoNotPressBack=null;
    /** the nut drawable that rotate clockwise*/
    RotateDrawable nutSettingRotateDrawable = null;
    /** to know if the Setting panle is closing or opening*/
    boolean settingsPanelOpened = false;
    /**     * The speed used when expanding the layout     */
    int expandingSpeed=50;
    /**     * The speed used when expanding the layout     */
    int collapsingSpeed=20;
    /***********************************************************
     * Managing animation of the  bottom button (the magician)
     * When this button is pushed, it will animate the drawable
     * giving the illusion that the magician is animating itself
     * and push the EditButton.
     * The EditButton will then be pushed from left to right
     **********************************************************/
    /** the size of the Edit button (the one at the bottom to move it properly)
     *  it's initialiazed in onResume*/
    int btnEditWidth;
    /** The paddingLeft of the view and the x-position of the ImageView Sprites
     * It's used to know the bound of the EditButton movement*/
    float paddingLeft, imSpritesX;
    /** The ImageView that will displays the animated drawable (like a gif)     */
    ImageView imvSprites=null;
    /** The Animated drawable (like a gif) that disaplys the magician     */
    AnimationDrawable spritesAnimationDrawable=null;
    /***********************************************************
     * Managing MenuItem Edition scaling
     **********************************************************/

    /** The drawable of the MenuItem to scale the drawable*/
    ScaleDrawable menuItemEdit_ScaleDrawable = null;

    /***********************************************************
     * Initialize your attributes
     **********************************************************/

    @Override
    public void initializeView(MainActivity activity){
        this.activity=activity;
        scvRootSettingPanel = (ScrollView) activity.findViewById(R.id.scvRootSetting);
        lilContentSettingPanel = (LinearLayout) activity.findViewById(R.id.lilContent);
        btnDoNotPress = (Button) activity.findViewById(R.id.btnDoNotPress);
        btnDoNotPressBack= (Button) activity.findViewById(R.id.btnDoNotPressBack);
        imvSprites = (ImageView) activity.findViewById(R.id.imvSprite);
        btnDoNotPress.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                flipBtnDoNotPress();
            }
        });
        imvSprites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateSprites();
            }
        });
    }

}
