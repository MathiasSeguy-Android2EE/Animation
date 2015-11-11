package com.android2ee.droidcon.greece.animation.mainactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android2ee.droidcon.greece.animation.DrawableActivity;
import com.android2ee.droidcon.greece.animation.EditActivity;
import com.android2ee.droidcon.greece.animation.MotherActivity;
import com.android2ee.droidcon.greece.animation.R;
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
     * Attributes :
     * Only the ones that are usefull (not the ones only needed for animations)
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
    /** The MenuItem Edition*/
    MenuItem editMenuItem;
    /** The menuItem that displays the setting panel*/
    MenuItem nutSettingMenuItem;
    /**
     * The animation
     */
    MainActivityAnimMother anim;

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
                anim.launchActivityWithAnimation();
            }
        });

        blueDot = (BlueDot) findViewById(R.id.blueDot);

        blueDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueDot.getLayoutParams().width = width;
                blueDot.getLayoutParams().height = height;
                blueDot.startAnimation(postICS);
            }
        });
        //the factory for the animations (you can create a Class to do that if you want) :
        if(isPostLollipop){
            anim=new MainActivityAnimICS();
        }else if(postICS){
            anim=new MainActivityAnimICS();
        }else{
            anim=new MainActivityAnimGinger();
        }
        anim.initializeView(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        //initialize the view size
        getViewSize();
        //initialize the btnEdit width (usefull trick to know)
        getEditButtonWidth();
        //find the padding of the view
        anim.paddingLeft = getResources().getDimension(R.dimen.activity_horizontal_margin);
        //relaunch option menu animation (gingerbread bug)
        invalidateOptionsMenu();
    }



    @Override
    protected void onPause() {
        super.onPause();
       anim.onPause();
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
                anim.btnEditWidth = btnEdit.getMeasuredWidth();
            }
        });
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
     * Managing Menu
     **********************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        nutSettingMenuItem = menu.findItem(R.id.settings);
        anim.nutSettingRotateDrawable = (RotateDrawable) getResources().getDrawable(R.drawable.rotation_settings);
        nutSettingMenuItem.setIcon(anim.nutSettingRotateDrawable);
        editMenuItem = menu.findItem(R.id.action_edit);
        anim.menuItemEdit_ScaleDrawable = (ScaleDrawable) editMenuItem.getIcon();

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //start the animation of the menuitem edit
        anim.startScaleEditMenu();
        return super.onPrepareOptionsMenu(menu);
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
            anim.launchSettingAnimations();
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


}
