/**
 * <ul>
 * <li>MainActivityAnimICS</li>
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

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import com.android2ee.droidcon.greece.animation.R;
import com.android2ee.droidcon.greece.animation.SceneActivity;

/**
 * Created by Mathias Seguy - Android2EE on 03/11/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivityAnimICS extends MainActivityAnimMother {
    @Override
    public void initializeView(MainActivity activity) {
        super.initializeView(activity);
    }

    /**
     * Has to be called in onPause of your activity
     */
    @Override
    public void onPause() {
        //NOthing to do :) thanks chet
    }
    /***********************************************************
     * Managing the Settings Animation :
     * In the actionBar when you click on the nut menuItem a panel go down,
     *
     * This panel is called Setting
     **********************************************************/
    /**
     * The objectAnimator that animates the setting
     */
    ObjectAnimator animOpenSettingOA = null, animCloseSettingOA = null;
    /**
     * The animation that flip the button BtnDoNotPress
     */
    Animator flipAnimatorIn;
    /**
     * The animation that flip the button BtnDoNotPress
     */
    Animator flipAnimatorOut;


    /**
     * Called when the nutSettingMenuItem or the magicianMenuItem is clicked
     * Launch the animation or reverse it
     */
    @Override
    public void launchSettingAnimations() {
        if (animOpenSettingOA == null) {
            animOpenSettingOA = ObjectAnimator.ofInt(this, "AnimateSettingAnimation", 0, 100);
            animCloseSettingOA = ObjectAnimator.ofInt(this, "AnimateSettingAnimation", 0, 100);
        }
        animOpenSettingOA.addListener(new SimpleAnimatorListenert() {
            @Override
            public void onAnimationOver(Animator animation) {
                //The panel is open
                settingsPanelOpened = true;
            }
        });
        animCloseSettingOA.addListener(new SimpleAnimatorListenert() {
            @Override
            public void onAnimationOver(Animator animation) {
                //The panel is closed
                settingsPanelOpened = false;

            }
        });
        //manage the opened-closed state
        if (settingsPanelOpened) {
            //start animation
            animCloseSettingOA.start();
        } else {
            //start animation
            animOpenSettingOA.start();
        }

    }

    /**
     * You have to animate me between 0 and 100
     *
     * @param value
     */
    private void setAnimateSettingAnimation(int value) {
        //convert from value to step:
        //0<step<(activity.height/2) and activity.height/2== 100
        //so value==100 => step==activity.height/2
        //=>100*step=value*(activity.height/2)
        if (settingsPanelOpened) {
            //close it
            defineSettingAnimation(((100 - value) * activity.height) / 200);
        } else {
            //open it
            defineSettingAnimation((value * activity.height) / 200);

        }
    }

    /***
     * Change the beahvior/ the state of the object you want to animate
     * Here we change the size of the linearLayout and the level of the drawable (menuitem)
     *
     * @param step
     */
    private void defineSettingAnimation(int step) {
        //update the size of the button according to the size of the panel
        btnDoNotPress.getLayoutParams().height = step / 4;
        btnDoNotPress.getLayoutParams().width = step / 4;
        btnDoNotPressBack.getLayoutParams().height = step / 4;
        btnDoNotPressBack.getLayoutParams().width = step / 4;
        btnDoNotPress.setTextSize(step / 40);
        lilContentSettingPanel.requestLayout();
        //update the height of the panel
        scvRootSettingPanel.getLayoutParams().height = step;//
        //and ask for redraw it (call invalidate on it)
        scvRootSettingPanel.requestLayout();
        if (settingsPanelOpened) {
            //rotate the nut anti_clockwise
            nutSettingRotateDrawable.setLevel(step * 100);
        } else {
            //rotate the nut
            nutSettingRotateDrawable.setLevel(-40 * step);
        }
    }

    /**
     * Call when the btnDoNotPress is clicked
     */
    @Override
    public void flipBtnDoNotPress() {
        animateItem();
    }

    private void animateItem() {
        initialiseFlipAnimator();
        flipAnimatorOut.start();
    }


    private void initialiseFlipAnimator() {
        if (flipAnimatorIn == null) {
            btnDoNotPressBack.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            btnDoNotPress.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            flipAnimatorOut = AnimatorInflater.loadAnimator(activity, R.animator.flip_out);
            flipAnimatorOut.setTarget(btnDoNotPress);
            flipAnimatorIn = AnimatorInflater.loadAnimator(activity, R.animator.flip_in);
            flipAnimatorIn.setTarget(btnDoNotPressBack);
            flipAnimatorOut.addListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            btnDoNotPress.setVisibility(btnDoNotPress.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                            btnDoNotPressBack.setVisibility(btnDoNotPressBack.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            btnDoNotPress.setVisibility(View.GONE);
                            btnDoNotPressBack.setVisibility(View.VISIBLE);
//                            btnDoNotPress.setVisibility(btnDoNotPress.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
//                            btnDoNotPressBack.setVisibility(btnDoNotPressBack.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);

                            flipAnimatorIn.start();

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

            flipAnimatorIn.addListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            btnDoNotPressBack.setLayerType(View.LAYER_TYPE_NONE, null);
                            btnDoNotPress.setLayerType(View.LAYER_TYPE_NONE, null);

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
        }
    }

    /***********************************************************
     * Managing animation of the  bottom button (the magician)
     * When this button is pushed, it will animate the drawable
     * giving the illusion that the magician is animating itself
     * and push the EditButton.
     * The EditButton will then be pushed from left to right
     **********************************************************/
    /**
     * The objectAnimator that animates the setting
     */
    ObjectAnimator btnEditObjectAnimator = null;

    /**
     * Simple sprites animations
     */
    @Override
    public void animateSprites() {
        if (spritesAnimationDrawable == null) {
            Log.e("MainActivityAnimICS", "creation des anims");
            initializeSpritesAnimation();
            btnEditObjectAnimator = ObjectAnimator.ofInt(this,
                    "BtnEditAnimation",
                    0,
                    100)
                    .setDuration(3000);
            btnEditObjectAnimator.setStartDelay(spritesAnimationDrawable.getNumberOfFrames()*activity.DURATION );
        }
        //animate the magician
        if (spritesAnimationDrawable.isRunning()) {
            //if you don't stop your animation, even if it's one shot and over
            //you need to stop it else it won't run again
            spritesAnimationDrawable.stop();
        }
        spritesAnimationDrawable.start();
        btnEditObjectAnimator.start();
    }

    /**
     * This method is called in onCreate and create the AnimationDrawable
     */
    private void initializeSpritesAnimation() {
        //define the Animated drawable
        //after ICS you can do dynamic stuff
        if(spritesAnimationDrawable==null) {
            spritesAnimationDrawable = new AnimationDrawable();
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic1), activity.DURATION);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic2), activity.DURATION);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic3), activity.DURATION);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic4), activity.DURATION);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic5), activity.DURATION);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic6), activity.DURATION);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic7), activity.DURATION);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic8), activity.DURATION);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic9), activity.DURATION);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic10), activity.DURATION);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic10), activity.DURATION * 3);
            spritesAnimationDrawable.addFrame(activity.getResources().getDrawable(R.drawable.attack_magic1), activity.DURATION);
            spritesAnimationDrawable.setOneShot(true);

            imvSprites.setImageDrawable(spritesAnimationDrawable);
        }
//        imvSprites.setBackgroundDrawable(spritesAnimationDrawable);

        //then find the value you need to animate the button
        btnEditX0 = activity.btnEdit.getX() ;
        btnEditXMax = paddingLeft;
        btnEditPathLenght = (btnEditX0 - btnEditXMax) ;
    }

    /**
     * Measure to define the animation
     */
    float btnEditX0, btnEditXMax, btnEditPathLenght;

    /**
     * Animate the button Edit
     * value expected from 0 to 100
     * Is caaled more than once for each step
     * (I mean can be called twice with step == 0||n)
     * So use step notthe number of time the method is called
     * @param step
     */
    public void setBtnEditAnimation(int step) {
        if (step <= 50) {
            //moving from  right to left to
            //as long as you don't reach the border of the view, go left
            activity.btnEdit.setX(btnEditX0 - step * (btnEditPathLenght / 50));
            //and rotate to give the illusion to roll on the floor
            activity.btnEdit.setRotation((float) (-5*Math.PI*step));
        } else if(step<=100){
            //moving from left to right
            //as long as you have not reach the ImageView imvSprites just keep going
            activity.btnEdit.setX((btnEditXMax + ((step - 50) * (btnEditPathLenght / 50))));
            activity.btnEdit.setRotation((float) (250*Math.PI-(5*Math.PI*(step-50))));
            activity.btnEdit.setRotation((float) (-5 * Math.PI * (100 - step)));
        }
        //restore inital state
        if(step==100){
            activity.btnEdit.setX(btnEditX0);
            activity.btnEdit.setRotation(0);
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
        //  if(options==null) {<-Do not do such a stuff, it's generating bugs
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    // the context of the activity
                    activity,
                    // For each shared element, add to this method a new Pair item,
                    // which contains the reference of the view we are transitioning *from*,
                    // and the value of the transitionName attribute in both layouts
                    new Pair<View, String>(activity.txvResult, activity.getString(R.string.txvText_transition)),
                    new Pair<View, String>(activity.btnEdit, activity.getString(R.string.btnEdit_transition)),
                    new Pair<View, String>(imvSprites, activity.getString(R.string.imvSprite_transition)),
                    new Pair<View, String>(activity.btnDrawable, activity.getString(R.string.btnDrawable_transition))
            );

        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    /***********************************************************
     * Managing MenuItem Edition scaling
     **********************************************************/

    @Override
    public void startScaleEditMenu() {
            //when playing with scaleDrawable always initialize the level
            menuItemEdit_ScaleDrawable.setLevel(10000);
        ObjectAnimator scaleEditMenuItemOA=ObjectAnimator.ofInt(this,"ScaleEditMenuItem",0,20000)
                .setDuration(3000);
        scaleEditMenuItemOA.setRepeatMode(ObjectAnimator.REVERSE);
        scaleEditMenuItemOA.setRepeatCount(ObjectAnimator.INFINITE);
        scaleEditMenuItemOA.setInterpolator(new AnticipateOvershootInterpolator());
        scaleEditMenuItemOA.start();
    }

    /**
     * Value expected between 0 and 15 000
     * from 0 to 5 000 it scales up from 50% to 100%
     * from 5000 to 15 000 it scales down to 0
     * @param step
     */
    private void setScaleEditMenuItem(int step){
        /*Value expected between 0 and 15 000
     * from 0 to 5 000 it scales up from 50% to 100%
     * from 5000 to 15 000 it scales down to 0*/
        //il est fun avec un linearInterpolator
//        if(step<5000){
//            menuItemEdit_ScaleDrawable.setLevel(5000+step);
//        }else{
//            menuItemEdit_ScaleDrawable.setLevel(15000-step);
//        }
        /*Value expected between 0 and 20 000
        * from 0 to 10 000 it scales down to 0
        * from 10 000 to 20 000 it scales up to 0*/
        if(step<10000){
            menuItemEdit_ScaleDrawable.setLevel(10000-step);
        }else{
            menuItemEdit_ScaleDrawable.setLevel(step-10000);
        }

    }


    /***********************************************************
     * Simple Animation Listener
     **********************************************************/

    protected abstract class SimpleAnimatorListenert implements Animator.AnimatorListener {

        /**
         * <p>Notifies the start of the animation.</p>
         *
         * @param animation The started animation.
         */
        @Override
        public void onAnimationStart(Animator animation) {
        }

        /**
         * <p>Notifies the end of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.</p>
         *
         * @param animation The animation which reached its end.
         */
        @Override
        public void onAnimationEnd(Animator animation) {
            onAnimationOver(animation);
        }

        /**
         * <p>Notifies the cancellation of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.</p>
         *
         * @param animation The animation which was canceled.
         */
        @Override
        public void onAnimationCancel(Animator animation) {
            onAnimationOver(animation);
        }

        public abstract void onAnimationOver(Animator animation);

        /**
         * <p>Notifies the repetition of the animation.</p>
         *
         * @param animation The animation which was repeated.
         */
        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
