package com.android2ee.droidcon.greece.animation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android2ee.droidcon.greece.animation.customviews.BlueDot;
import com.android2ee.droidcon.greece.animation.transverse.Constant;


/**
 * This activity shows you some animations that can easily be done
 */
public class MainActivity extends MotherActivity {

    /**
     * Duration for each frame of the spritesAnimationDrawable
     */
    public static final int DURATION = 100;
    /***********************************************************
     * Attributes
     **********************************************************/
    /**
     * The Edit button
     */
    Button btnEdit;
    /**
     * The drawable button (the one that looks like android)
     * that launches the DrawableActivity
     */
    Button btnDrawable;
    /**
     * The Scene Button that launches the scene Activity
     * and shows activity transition using scene
     */
    Button btnScene;
    /**
     * The blue dot graphical component, see the BlueDot class
     */
    BlueDot blueDot;
    /**
     * The text view that displays an Hello World
     */
    TextView txvResult;
    /** the Width and Height of the view (used to set blueDot position*/
    int width, height;
    /***********************************************************
     * Managing device version in a readable way
     **********************************************************/
    /**
     * The am I post ICS boolean
     */
    boolean postICS;
    /**
     * The am I post Lollipop boolean
     */
    boolean isPostLollipop;

    /***********************************************************
     * Managing Life cycle
     **********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instanciate the view
        setContentView(R.layout.activity_main);
        //update the actionBar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));
        getSupportActionBar().setSubtitle(getString(R.string.main_activity_subtitle));
        //define if we are running on lollipop and/or ICS
        isPostLollipop = getResources().getBoolean(R.bool.postLollipop);
        postICS = getResources().getBoolean(R.bool.postICS);
        //find the graphical components
        txvResult = (TextView) findViewById(R.id.txvResult);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDrawable = (Button) findViewById(R.id.btnDrawable);
        btnScene = (Button) findViewById(R.id.btnScene);
        //define the listeners for those components
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTxvResult();
            }
        });
        btnDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDrawableActivity();
            }
        });
        btnScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivityWithAnimation();
            }
        });
        onCreateSpriteAnimation();

        blueDot = (BlueDot) findViewById(R.id.blueDot);
        lilRootSettingPanel = (LinearLayout) findViewById(R.id.lilRootSetting);
        btnDoNotPress = (Button) findViewById(R.id.btnDoNotPress);
        blueDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueDot.getLayoutParams().width = width;
                blueDot.getLayoutParams().height = height;
                blueDot.startAnimation(postICS);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        //initialize the view size
        getViewSize();
        //initialize the btnEdit width (usefull trick to know)
        getEditButtonWidth();
        //find the padding of the view
        paddingLeft = getResources().getDimension(R.dimen.activity_horizontal_margin);
        //start the animation of the menuitem edit
        if (scaleEditMenuItemHandler != null) {
            scaleEditMenuItemHandler.postDelayed(scaleEditMenuItemRunnable, 32);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the txvResult value when rotating the device
        outState.putString(Constant.TXV_VALUE, txvResult.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //restore the txvResult value when rotating the device
        txvResult.setText(savedInstanceState.getString(Constant.TXV_VALUE));
    }

    /***********************************************************
     *  Managing dimension
     **********************************************************/

    /**
     * initialize the size of the view
     */
    @SuppressLint("NewApi")
    private void getViewSize() {
        //this is an usual trick when we want to know the dimension of our view
        //initialize dimensions of the view
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (postICS) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();  // deprecated
            height = display.getHeight();  // deprecated
        }
    }

    /**
     * When trying to know the size of a component you should use this method
     */
    private void getEditButtonWidth() {
        //this is an usual trick when we want to know the dimension of the elements of our view
        //find the dimension of the EditButton
        ViewTreeObserver vto = btnEdit.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                btnEdit.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                btnEditWidth = btnEdit.getMeasuredWidth();
            }
        });
    }

    /***********************************************************
     * Managing the Settings Animation :
     * In the actionBar when you click on the nut menuItem a panel go down,
     *
     * This panel is called Setting
     **********************************************************/
    /** The root layout of the panel */
    private LinearLayout lilRootSettingPanel;
    /** A button is that panel*/
    private Button btnDoNotPress;
    /** the nut drawable that rotate clockwise*/
    RotateDrawable nutSettingRotateDrawable = null;
    /** the magician MenuItem that appears to turn the nut*/
    MenuItem magicianMenuItem;
    /** The menuItem that displays the setting panel*/
    MenuItem nutSettingMenuItem;
    /** to know if the SettingPanel is animating */
    boolean isAnimatingSettingPanel = false;
    /** to know if the Setting panle is closing or opening*/
    boolean closingSettingsPanel = false;
    /** The runnable that update the properties of the lilRootSettingPanel */
    Runnable openCloseSettingsPanelRunnable = null;
    /** The handler that drops the openCloseSettingsPanelRunnable in the UI thread*/
    Handler openCloseSettingsPanelHandler = null;

    /**
     * Called when the nutSettingMenuItem or the magicianMenuItem is clicked
     * Launch the animation or reverse it
     */
    private void launchSettingAnimations() {
        //initialize the elements
        if (openCloseSettingsPanelRunnable == null) {
            initializeSettingsAnimation();
        }
        if (nutSettingRotateDrawable == null) {
            nutSettingRotateDrawable = (RotateDrawable) getResources().getDrawable(R.drawable.rotation_settings);
        }
        //then launch the animation
        if (!isAnimatingSettingPanel) {
            //set the icon (rotating clockwize)
            nutSettingMenuItem.setIcon(nutSettingRotateDrawable);
            //drop the openCloseSettingsPanelRunnable in the UI thread
            openCloseSettingsPanelHandler.postDelayed(openCloseSettingsPanelRunnable, 16);
            //set the magicianMenuItem visible and start its animation
            magicianMenuItem.setVisible(true);
            ((AnimationDrawable) magicianMenuItem.getIcon()).start();
            //update the isAnimatingSettingPanel boolean
            isAnimatingSettingPanel = true;
        } else {
            //already animating the screen
            //so reverse
            closingSettingsPanel = true;
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
                btnDoNotPress.getLayoutParams().height = step / 2;
                btnDoNotPress.getLayoutParams().width = step / 2;
                //update the height of the panel
                lilRootSettingPanel.getLayoutParams().height = step;
                //and ask for redraw it (call invalidate on it)
                lilRootSettingPanel.requestLayout();
                if (closingSettingsPanel) {
                    //rotate the nut anti_clockwise
                    nutSettingRotateDrawable.setLevel(-step * 100);
                    step = step - 10;
                    if (step > 0) {
                        //keep going
                        openCloseSettingsPanelHandler.postDelayed(this, 64);
                    } else {
                        //stop we have finished, the panel is hidden
                        closingSettingsPanel = false;
                        //stop the magician animation
                        ((AnimationDrawable) magicianMenuItem.getIcon()).stop();
                        //hide him
                        magicianMenuItem.setVisible(false);
                        //set the definitive state of the layout
                        lilRootSettingPanel.getLayoutParams().height = 0;
                        lilRootSettingPanel.requestLayout();
                        //and update the status
                        isAnimatingSettingPanel = false;
                    }
                } else {
                    //rotate the nut
                    nutSettingRotateDrawable.setLevel(step * 40);
                    step = step + 20;
                    if (step < (height / 2)) {
                        //keep going
                        openCloseSettingsPanelHandler.postDelayed(this, 64);
                    } else {
                        //stop we reach our wanted size, so
                        closingSettingsPanel = true;
                        isAnimatingSettingPanel = false;
                        //manage the magician
                        ((AnimationDrawable) magicianMenuItem.getIcon()).stop();
                        magicianMenuItem.setVisible(false);
                    }
                }
            }
        };
    }

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
    /** The animation to use for the BtnEdit when running on gingerbread*/
    Animation btnEditTranslationTweenAnimation;
    /** The runnable that will changes the properties of the EditButton to make the animation     */
    Runnable btnEditAnimationRunnable = null;
    /** The handler that will drop the runnable btnEditAnimationRunnable in the main thread*/
    Handler btnEditAnimationHandler = null;
    /** Simple boolean to know if we go left to right or right to left */
    boolean moveButtonReverse = false;
    /** The ImageView that will displays the animated drawable (like a gif)     */
    ImageView imvSprites;
    /** The Animated drawable (like a gif) that disaplys the magician     */
    AnimationDrawable spritesAnimationDrawable;
    /**
     * This method is called in onCreate and create the AnimationDrawable
     */
    private void onCreateSpriteAnimation() {
        //first find the component
        imvSprites = (ImageView) findViewById(R.id.imvSprite);
        //define the Animated drawable
        if (postICS) {
            //after ICS you can do dynamic stuff
            spritesAnimationDrawable = new AnimationDrawable();
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic1), DURATION);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic2), DURATION);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic3), DURATION);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic4), DURATION);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic5), DURATION);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic6), DURATION);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic7), DURATION);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic8), DURATION);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic9), DURATION);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic10), DURATION);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic10), DURATION * 3);
            spritesAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.attack_magic1), DURATION);
            imvSprites.setImageDrawable(spritesAnimationDrawable);
            spritesAnimationDrawable.setOneShot(true);
        } else {
            //before ICS, you can not
            spritesAnimationDrawable = (AnimationDrawable) imvSprites.getDrawable();
        }
        //then set the click listener
        imvSprites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateSprites();
            }
        });
    }

    /**
     * Simple sprites animations
     */
    private void animateSprites() {
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
        btnEditAnimationHandler.postDelayed(btnEditAnimationRunnable, DURATION * (spritesAnimationDrawable.getNumberOfFrames() - 2));
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
        if (!postICS) {
            btnEditTranslationTweenAnimation = AnimationUtils.loadAnimation(this, R.anim.btn_edit_animation);
            btnEdit.startAnimation(btnEditTranslationTweenAnimation);
        }else{
            //initialize the handler
            btnEditAnimationHandler = new Handler();
            //and define what the runnable will do
            btnEditAnimationRunnable = new Runnable() {
                //the number of step of the animation (a simple counter)
                int step = 0;
                @Override
                public void run() {
                    Log.d("MainActivity", "Fuck running animation on gingerbread fails" + ViewCompat.getX(btnEdit));
                    postIcsAnimation();
                }

                @SuppressLint("NewApi")
                private void postIcsAnimation() {
                    if (!moveButtonReverse) {
                        //moving from left to right
                        if (btnEdit.getX() > paddingLeft) {
                            //as long as you don't reach the border of the view, go left
                            btnEdit.setX(btnEdit.getX() - step);
                            //and rotate to give the illusion to roll on the floor
                            btnEdit.setRotation(btnEdit.getRotation() - step);
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
                        if (btnEdit.getX() + btnEditWidth < imSpritesX) {
                            //as long as you have not reach the ImageView imvSprites just keep going
                            Log.d("MainActivity", "ReverseButtonMotion btn.x=" + btnEdit.getX() + ", step =" + step);
                            btnEdit.setX(btnEdit.getX() + step);
                            btnEdit.setRotation(btnEdit.getRotation() + step);
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
     * Launching drawable activity (no transition animation)
     **********************************************************/
    Intent startDrawableActivity=null;
    /**
     * Launch the drawable Activity
     */
    private void showDrawableActivity() {
        if(startDrawableActivity==null){
            startDrawableActivity = new Intent(this, DrawableActivity.class);
        }
        startActivity(startDrawableActivity);
    }

    /***********************************************************
     * Launching EditActivity (no transition animation)
     **********************************************************/
    /**The message to give as paramleter to the EditActivity*/
    String messageTemp = null;
    /** The bundle used to transport the parameter*/
    Bundle bundleTemp = null;
    /** the intent*/
    Intent startEdition = null;

    /**
     * Launch the EditionAcvtivity
     */
    private void editTxvResult() {
        //find the content of the textView
        messageTemp = txvResult.getText().toString();
        //build an intent
        if (startEdition == null) {
            startEdition = new Intent(this, EditActivity.class);
        }
        //add data to intent
        if (bundleTemp == null) {
            bundleTemp = new Bundle();
        }
        bundleTemp.putString(Constant.TXV_VALUE, messageTemp);
        startEdition.putExtras(bundleTemp);
        //send the EditActivityFor Result
        startActivityForResult(startEdition, Constant.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //update the TxvResult with the value return by the editActivity
                bundleTemp = data.getExtras();
                messageTemp = bundleTemp.getString(Constant.TXV_VALUE);
                txvResult.setText(messageTemp);
            } else {//resultCode==RESULT_CANCEL
                //do  nothing
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
    private void launchActivityWithAnimation() {
        Intent intent = new Intent(this, SceneActivity.class);
        //  if(options==null) {<-Do not do such a stuff, it's generating bugs
        if (isPostLollipop) {
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    // the context of the activity
                    this,
                    // For each shared element, add to this method a new Pair item,
                    // which contains the reference of the view we are transitioning *from*,
                    // and the value of the transitionName attribute in both layouts
                    new Pair<View, String>(txvResult, getString(R.string.txvText_transition)),
                    new Pair<View, String>(btnEdit, getString(R.string.btnEdit_transition)),
                    new Pair<View, String>(imvSprites, getString(R.string.imvSprite_transition)),
                    new Pair<View, String>(btnDrawable, getString(R.string.btnDrawable_transition))
            );
        } else {
            //do a simple animation (using tween animation) for exemple
            options = ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
    }
    /***********************************************************
     * Managing Menu
     **********************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        nutSettingMenuItem = menu.findItem(R.id.settings);
        magicianMenuItem = menu.findItem(R.id.elf_setting);
        editMenuItem = menu.findItem(R.id.action_edit);
        menuItemEdit_ScaleDrawable = (ScaleDrawable) editMenuItem.getIcon();
        initializeScalingMenuItemEdit();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_edit) {
            editTxvResult();
            return true;
        } else if (id == R.id.settings) {
            launchSettingAnimations();
            return true;
        } else if (id == R.id.elf_setting) {
            launchSettingAnimations();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * For having the Tag of my daughter class
     *
     * @return
     */
    @Override
    protected String getLogTag() {
        return "MainActivity";
    }

    /***********************************************************
     * Managing MenuItem Edition scaling
     **********************************************************/
    /** The MenuItem Edition*/
    MenuItem editMenuItem;
    /** The drawable of the MenuItem to scale the drawable*/
    ScaleDrawable menuItemEdit_ScaleDrawable = null;
    /** The runnable that changes the size of the menuItemEdit_ScaleDrawable */
    Runnable scaleEditMenuItemRunnable = null;
    /** The hanlder that drop the scaleEditMenuItemRunnable in the UI thread*/
    Handler scaleEditMenuItemHandler = null;
    /**The boolean to know is the scaling if up or down*/
    boolean scalingUp = false;

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
