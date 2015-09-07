package com.android2ee.droidcon.greece.animation;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * This Activity aims to show how Activities transitions work
 * And how the android:animateLayoutChanges="true" helps you animating your ui
 * when you change it (removing/adding/changing size of views)
 */
public class SceneActivity extends AppCompatActivity {
    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     * The button to change the size of the ImageView ImvSprites
     */
    Button btnChangeImvSpriteSize;
    /**
     * The button to show/hide the ImageView ImvSprites
     */
    ImageButton ibtnToggleImvSprite;
    /**
     * The ImageView ImvSprites
     */
    ImageView imvSprite1;
    /**
     * Managing post ICS code
     */
    boolean postICS;

    /***********************************************************
     * Managing Life cycle
     **********************************************************/
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize the view and the components
        setContentView(R.layout.activity_scene);
        ibtnToggleImvSprite = (ImageButton) findViewById(R.id.ibtnSprite);
        imvSprite1= (ImageView) findViewById(R.id.imvSprite1);
        btnChangeImvSpriteSize = (Button) findViewById(R.id.btnDrawable);
        postICS=getResources().getBoolean(R.bool.postICS);
        //get the imageView size
        getImvSprite1Height();
        //if post ICS play with transition
        if(postICS){
            LayoutTransition transition = ((ViewGroup)findViewById(R.id.llSceneRoot)).getLayoutTransition();
            // New capability as of Jellybean; monitor the container for *all* layout changes
            // (not just add/remove/visibility changes) and animate these changes as well.
            transition.enableTransitionType(LayoutTransition.CHANGING);
        }
        //add listeners
        ibtnToggleImvSprite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImvSprite1Visibility();
            }
        });

        btnChangeImvSpriteSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImvSprite1Size();
            }
        });
    }
    /***********************************************************
     *  Managing ImvSpriteSize size and visibility
     **********************************************************/
    /**
     * The Height of the ImvSprite
     */
    int imvSprite1Height;
    /**
     * The state of the ImvSprite 'expanded or normal.
     */
    boolean isImvSprite1Expended=false;
    /**
     * The layout parameter to use for the normal size of ImvSprite
     */
    private LinearLayout.LayoutParams imvSpritesLayoutParamNormal ;

    /**
     * The layout parameter to use for the expanded size of ImvSprite
     */
    private LayoutParams imvSpritesLayoutParamExpanded;
    /**
     * When trying to know the size of a component you should use this method
     */
    private void getImvSprite1Height() {
        //this is an usual trick when we want to know the dimension of the elements of our view
        //find the dimension of the EditButton
        ViewTreeObserver vto = imvSprite1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imvSprite1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                initializeImvSpriteSize();
            }
        });
    }
    /**
     * As we know the size of the ImvSprites we initialize the LayoutParameters
     * To change its size at runtime
     */
    private void initializeImvSpriteSize() {
        //get the real size of the components
        imvSprite1Height = imvSprite1.getMeasuredHeight();
        //initialize the layout parameter for the normal size
        imvSpritesLayoutParamNormal = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, imvSprite1Height);
        //initialize the layout parameter for the expanded size
        imvSpritesLayoutParamExpanded= new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 2*imvSprite1Height);
    }

    /**
     * Changing the visibility of ImvSprites
     */
    private void changeImvSprite1Visibility(){
        if(imvSprite1.getVisibility()==View.VISIBLE){
            imvSprite1.setVisibility(View.GONE);
        }else{
            imvSprite1.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Changing the size of ImvSprites
     */
    private void changeImvSprite1Size(){
        //Change the LayoutParameter to change the size of view
        if(isImvSprite1Expended){
            imvSprite1.setLayoutParams(imvSpritesLayoutParamNormal);
        }else{
            imvSprite1.setLayoutParams(imvSpritesLayoutParamExpanded);
        }
        isImvSprite1Expended=!isImvSprite1Expended;
    }

    /***********************************************************
     * Managing Menu
     **********************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scene, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

}
