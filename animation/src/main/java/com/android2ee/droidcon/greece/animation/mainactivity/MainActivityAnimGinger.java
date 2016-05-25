/**
 * <ul>
 * <li>MainActivityAnimGinger</li>
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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android2ee.droidcon.greece.animation.R;
import com.android2ee.droidcon.greece.animation.SceneActivity;

/**
 * Created by Mathias Seguy - Android2EE on 03/11/2015.
 */
public class MainActivityAnimGinger extends MainActivityAnimMother {
    private static final String TAG = "MainActivityAnimGinger";

    @Override
    public void initializeView(MainActivity activity){
        super.initializeView(activity);
    }


    /**
     * Has to be called in onPause of your activity
     */
    @Override
    public void onPause(){
        //stop all the handler; insure their death
        scaleEditMenuItemHandler.removeCallbacks(scaleEditMenuItemRunnable);
        if(openCloseSettingsPanelHandler!=null){
            openCloseSettingsPanelHandler.removeCallbacks(openCloseSettingsPanelRunnable);
        }
        if(btnEditAnimationHandler!=null){
            btnEditAnimationHandler.removeCallbacks(btnEditAnimationRunnable);
        }
        if(spritesAnimationDrawable!=null){
            spritesAnimationDrawable.stop();
        }
    }

    /***********************************************************
     * Managing the Settings Animation :
     * In the actionBar when you click on the nut menuItem a panel go down,
     *
     * This panel is called Setting
     **********************************************************/

    /** The runnable that update the properties of the scvRootSettingPanel */
    Runnable openCloseSettingsPanelRunnable = null;
    /** The handler that drops the openCloseSettingsPanelRunnable in the UI thread*/
    Handler openCloseSettingsPanelHandler = null;
    /** to know if the SettingPanel is animating */
    boolean isAnimatingSettingPanel = false;

    /**
     * Called when the nutSettingMenuItem or the magicianMenuItem is clicked
     * Launch the animation or reverse it
     */
    @Override
    public void launchSettingAnimations() {
        //initialize the elements
        if (openCloseSettingsPanelRunnable == null) {
            initializeSettingsAnimation();
        }
        //then launch the animation
        if (!isAnimatingSettingPanel) {
            //set the icon (rotating clockwize)
            activity.nutSettingMenuItem.setIcon(nutSettingRotateDrawable);
            //drop the openCloseSettingsPanelRunnable in the UI thread
            openCloseSettingsPanelHandler.postDelayed(openCloseSettingsPanelRunnable, 16);
            //update the isAnimatingSettingPanel boolean
            isAnimatingSettingPanel = true;
        } else {
            //already animating the screen
            //so reverse
            settingsPanelOpened = true;
        }
    }

    /** Initialize the elements to animate the SettingsPanel*/
    private void initializeSettingsAnimation() {
        // instanciate the Handler and the runnable that animate
        // the properties of the SettingsPanel
        openCloseSettingsPanelHandler = new Handler();
        openCloseSettingsPanelRunnable = new Runnable() {
            /** A counter for the animation*/
            int step = 0;
            @Override
            public void run() {
                //update the size of the button according to the size of the panel
                btnDoNotPress.getLayoutParams().height = step / 4;
                btnDoNotPress.getLayoutParams().width = step / 4;
                btnDoNotPressBack.getLayoutParams().height = step / 4;
                btnDoNotPressBack.getLayoutParams().width = step / 4;
                lilContentSettingPanel.requestLayout();
                //update the height of the panel
                scvRootSettingPanel.getLayoutParams().height = step;
                //and ask for redraw it (call invalidate on it)
                scvRootSettingPanel.requestLayout();
                if (settingsPanelOpened) {
                    //rotate the nut anti_clockwise
                    nutSettingRotateDrawable.setLevel(step * 100);
                    step = step - collapsingSpeed;
                    if (step > 0) {
                        //keep going
                        openCloseSettingsPanelHandler.postDelayed(this, 64);
                    } else {
                        //stop we have finished, the panel is hidden
                        settingsPanelOpened = false;
                        //set the definitive state of the layout
                        scvRootSettingPanel.getLayoutParams().height = 0;
                        scvRootSettingPanel.requestLayout();
                        step=0;
                        //and update the status
                        isAnimatingSettingPanel = false;
                    }
                } else {
                    //rotate the nut
                    nutSettingRotateDrawable.setLevel(step * 40);
                    step = step + expandingSpeed;
                    if (step < (activity.height / 2)) {
                        //keep going
                        openCloseSettingsPanelHandler.postDelayed(this, 64);
                    } else {
                        //stop we reach our wanted size, so
                        settingsPanelOpened = true;
                        isAnimatingSettingPanel = false;
                    }
                }
            }
        };
    }

    /**************************************************
     * Making the don't push button flipping
     * postHoneyComb
     * **************************************************
     */
    /** The animation that flip the button BtnDoNotPress */
    Animation bumpDownAnim=null;
    /** The animation that flip the button BtnDoNotPress */
    Animation bumpUpAnim=null;

    /**
     * Call when the btnDoNotPress is clicked
     */
    @Override
    public void flipBtnDoNotPress(){
            animateItem();
    }
    @SuppressLint("NewApi")
    private void animateItem(){
        initialiseFlipAnimator();
        btnDoNotPress.startAnimation(bumpDownAnim);
    }
    @SuppressLint("NewApi")
    private void initialiseFlipAnimator(){
        if(bumpDownAnim==null){
            bumpDownAnim=AnimationUtils.loadAnimation(activity,R.anim.bump_down);
            bumpDownAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    btnDoNotPress.setVisibility(View.GONE);
                    btnDoNotPressBack.setVisibility(View.VISIBLE);
                    btnDoNotPressBack.startAnimation(bumpUpAnim);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            bumpUpAnim=AnimationUtils.loadAnimation(activity,R.anim.bump_up);
        }
    }


    /***********************************************************
     * Managing animation of the  bottom button (the magician)
     * When this button is pushed, it will animate the drawable
     * giving the illusion that the magician is animating itself
     * and push the EditButton.
     * The EditButton will then be pushed from left to right
     **********************************************************/

    /** The animation to use for the BtnEdit when running on gingerbread*/
    Animation btnEditTranslationTweenAnimation;
    /** The runnable that will changes the properties of the EditButton to make the animation     */
    Runnable btnEditAnimationRunnable = null;
    /** The handler that will drop the runnable btnEditAnimationRunnable in the main thread*/
    Handler btnEditAnimationHandler = null;
    /** Simple boolean to know if we go left to right or right to left */
    boolean moveButtonReverse = false;
    /**
     * Simple sprites animations
     */
    @Override
    public void animateSprites() {
        if(spritesAnimationDrawable==null){
            onCreateSpriteAnimation();
        }
        //animate the magician
        spritesAnimationDrawable.start();
        //move the button using Handler and runnable
        if (btnEditAnimationRunnable == null) {
            initializeRunnnableAnimation();
        }
        //initialiaze the way to move elements
        moveButtonReverse = false;
        //find (in a retro compatible way) the x position of the ImageView to animate
        imSpritesX = ViewCompat.getX(imvSprites);
        //then launch the animation when the spritesAnimationDrawable stops
        if (activity.postICS) {
            btnEditAnimationHandler.postDelayed(btnEditAnimationRunnable, activity.DURATION * (spritesAnimationDrawable.getNumberOfFrames() - 2));
        }else{
            activity.btnEdit.startAnimation(btnEditTranslationTweenAnimation);
        }
    }
    /**
     * This method is called in onCreate and create the AnimationDrawable
     */
    private void onCreateSpriteAnimation() {
        //define the Animated drawable
            //before ICS, you can not use dynamic
            spritesAnimationDrawable = (AnimationDrawable) imvSprites.getDrawable();

        //then set the click listener
        imvSprites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateSprites();
            }
        });
    }
    /**
     * Initialize the elements used to make the animation
     */
    private void initializeRunnnableAnimation() {
        //For gingerbread, trying to use ViewCompat but it failed
        //because getX always returns 0 in the Gingerbread IMPL
        // if(ViewCompat.getX(btnEdit)> paddingLeft) {
        //      ViewCompat.setX(btnEdit,ViewCompat.getX(btnEdit)-step);
        //      ViewCompat.setRotation(btnEdit,ViewCompat.getRotation(btnEdit)-step);
        //....
        //So if gingerbread (we do our best but no more)
        //but don't loose to much time
        if (!activity.postICS) {
            btnEditTranslationTweenAnimation = AnimationUtils.loadAnimation(activity, R.anim.btn_edit_animation);
        }else{
            //initialize the handler
            btnEditAnimationHandler = new Handler();
            //and define what the runnable will do
            btnEditAnimationRunnable = new Runnable() {
                //the number of step of the animation (a simple counter)
                int step = 0;
                @Override
                public void run() {
                    Log.d("MainActivity", "Fuck running animation on gingerbread fails" + ViewCompat.getX(activity.btnEdit));
                    postIcsAnimation();
                }

                @SuppressLint("NewApi")
                private void postIcsAnimation() {
                    if (!moveButtonReverse) {
                        //moving from left to right
                        if (activity.btnEdit.getX() > paddingLeft) {
                            //as long as you don't reach the border of the view, go left
                            activity.btnEdit.setX(activity.btnEdit.getX() - step);
                            //and rotate to give the illusion to roll on the floor
                            activity.btnEdit.setRotation(activity.btnEdit.getRotation() - step);
                            //post this animation in 64 ms (in 4 frames)
                            btnEditAnimationHandler.postDelayed(this, 64);
                            step++;
                        } else {
                            //else it's time to reverse movement
                            moveButtonReverse = true;
                            step = 0;
                            btnEditAnimationHandler.postDelayed(this, 64);
                        }
                    } else {
                        //moving from right to left
                        if (activity.btnEdit.getX() + btnEditWidth < imSpritesX) {
                            //as long as you have not reach the ImageView imvSprites just keep going
                            Log.d("MainActivity", "ReverseButtonMotion btn.x=" + activity.btnEdit.getX() + ", step =" + step);
                            activity.btnEdit.setX(activity.btnEdit.getX() + step);
                            activity.btnEdit.setRotation(activity.btnEdit.getRotation() + step);
                            btnEditAnimationHandler.postDelayed(this, 64);
                            step++;
                        } else {
                            //it's time to stop the animation
                            spritesAnimationDrawable.stop();
                        }
                    }
                }
            };
        }

    }
    /***********************************************************
     * Managing Activity transition
     **********************************************************/
    /** The activity option to define which animation to launch when
     * transiting from this activity to SceneActivity
     * You have to instanciate it each time you launch the next activity
     * Else it will works only the first time*/
    ActivityOptionsCompat options;
    /**
     * Launching an Activity using the Scene and transition framework
     */
    @Override
    public void launchActivityWithAnimation() {
        Intent intent = new Intent(activity, SceneActivity.class);

            //do a simple animation (using tween animation) for exemple
            options = ActivityOptionsCompat.makeCustomAnimation(activity, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
    /***********************************************************
     * Managing MenuItem Edition scaling
     **********************************************************/
    /** The runnable that changes the size of the menuItemEdit_ScaleDrawable */
    Runnable scaleEditMenuItemRunnable = null;
    /** The hanlder that drop the scaleEditMenuItemRunnable in the UI thread*/
    Handler scaleEditMenuItemHandler = null;
    /**The boolean to know is the scaling if up or down*/
    boolean scalingUp = false;

    @Override
    public void startScaleEditMenu() {
        if (scaleEditMenuItemHandler == null) {
            initializeScalingMenuItemEdit();
        }
        scaleEditMenuItemHandler.postDelayed(scaleEditMenuItemRunnable, 32);
    }
    /**Initialize the scaling*/
    private void initializeScalingMenuItemEdit() {
        //when playing with scaleDrawable always initialize the level
        menuItemEdit_ScaleDrawable.setLevel(10000);
        scaleEditMenuItemHandler = new Handler();
        scaleEditMenuItemRunnable = new Runnable() {
            int step = 0;

            @Override
            public void run() {
                //the goal is to moving the level from 80% to 100% (and reverse) in 2 second
                //it means 10 000<level<8 000 each step is called 60 times per second
                //so with 120 step you walk 2 000 in level => step = 2000/120 let's say 18
                if (scalingUp) {
                    menuItemEdit_ScaleDrawable.setLevel(10000 - (step * 50));
                } else {
                    //you can scale more than that
                    menuItemEdit_ScaleDrawable.setLevel(5000 + (step * 50));
                }
                //after 100 steps reverse
                if (step == 100) {
                    if (scalingUp) {
                        scaleEditMenuItemHandler.postDelayed(scaleEditMenuItemRunnable, 16);
                    } else {
                        scaleEditMenuItemHandler.postDelayed(scaleEditMenuItemRunnable, 1000);
                    }
                    scalingUp = !scalingUp;
                    step = 0;
                } else {
                    step++;
                    scaleEditMenuItemHandler.postDelayed(scaleEditMenuItemRunnable, 16);
                }
                Log.d("MainActivity", "Error: if not removed in onPause a memory leak is done//level=" + menuItemEdit_ScaleDrawable.getLevel());
            }
        };
        //start the animation
        scaleEditMenuItemHandler.postDelayed(scaleEditMenuItemRunnable, 32);
    }
}
