/**
 * <ul>
 * <li>MainActivityAnimIntf</li>
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

/**
 * Created by Mathias Seguy - Android2EE on 03/11/2015.
 */
public interface MainActivityAnimIntf {


    /**
     * Initialize the view components
     * @param activity
     */
    void initializeView(MainActivity activity);

    /**
     * Has to be called in onPause of your activity
     */
    void onPause();

    /**
     * Called when the nutSettingMenuItem or the magicianMenuItem is clicked
     * Launch the animation or reverse it
     */
    void launchSettingAnimations();

    /**
     * Call when the btnDoNotPress is clicked
     */
    void flipBtnDoNotPress();

    /**
     * Simple sprites animations
     */
    void animateSprites();

    /**
     * Launching an Activity using the Scene and transition framework
     */
    void launchActivityWithAnimation();

    void startScaleEditMenu();
}
