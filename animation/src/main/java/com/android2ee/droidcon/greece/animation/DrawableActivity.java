package com.android2ee.droidcon.greece.animation;

import android.annotation.TargetApi;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android2ee.droidcon.greece.animation.customviews.LinearLayoutScreenRecorder;

/**
 * This class aims to show how Drawables that embedded animations works:
 * ClipDrawable
 * RotateDrawable
 * AnimationDrawable
 * AnimatedStateListDrawable
 * TransitionDrawable
 * AnimatedVectorDrawable
 * As I make bloc for each Drawable type, I did not factorize what should have been
 */
public class DrawableActivity extends MotherActivity {
    LinearLayoutScreenRecorder llScreenRecorder;
    /**
     * Used for VectorDrawableAnimator
     */
    boolean isPostLollipop;
    /***********************************************************
     * Managing Life Cycle
     **********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        llScreenRecorder= (LinearLayoutScreenRecorder) findViewById(R.id.llDrawableScreenRecorder);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));
        getSupportActionBar().setSubtitle(getString(R.string.main_activity_subtitle));
        isPostLollipop=getResources().getBoolean(R.bool.postLollipop);
        //find the clipdrawables
        //if set as background use getBackground/iif set as src use getDrawable
        clipDrawableHorizontal= (ClipDrawable) ((ImageView)findViewById(R.id.imvClipHorizontal)).getDrawable();
        clipDrawableHorizontalTop= (ClipDrawable) ((ImageView)findViewById(R.id.imvClipHorizontalTop)).getDrawable();
        clipDrawableVertical= (ClipDrawable) ((ImageView)findViewById(R.id.imvClipVertical)).getDrawable();
        clipDrawableVerticalLeft= (ClipDrawable) ((ImageView)findViewById(R.id.imvClipVerticalLeft)).getDrawable();
        //find the rotateDrawables
        rotateDrawableAndroid2ee= (RotateDrawable) ((ImageView)findViewById(R.id.imvRotateAndroid2ee)).getDrawable();
        rotateDrawableWheel= (RotateDrawable) ((ImageView)findViewById(R.id.imvRotateWheel)).getDrawable();
        rotateDrawableAntiWheel= (RotateDrawable) ((ImageView)findViewById(R.id.imvRotateAntiClockwiseWheel)).getDrawable();
        //scale drawable
        scaleDrawable= (ScaleDrawable) ((ImageView)findViewById(R.id.imvScale)).getDrawable();
        //find the animationDrawable
        animationDrawable=(AnimationDrawable) ((ImageView)findViewById(R.id.imvAnimationDrawable)).getDrawable();
       //find the transition drawable
        transitionDrawable=(TransitionDrawable) ((ImageView)findViewById(R.id.imvTransition)).getDrawable();
        //add a onClickListener on the AnimatedStateList else it won't work at all
        ((ImageView)findViewById(R.id.imvAnimatedStateList)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawableActivity.this, "touché", Toast.LENGTH_LONG).show();
            }
        });
        if(isPostLollipop) {
            //animate vector drawable
            ImageView imvVectorAnimated = (ImageView) findViewById(R.id.imvAnimatedVector);
            animatedVectorDrawable = (AnimatedVectorDrawable) imvVectorAnimated.getDrawable();
            imvVectorAnimated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAnimatedVectorDrawable();
                }
            });
            ImageView imvVectorAnimated2 = (ImageView) findViewById(R.id.imvAnimatedVector2);
            animatedVectorDrawable2 = (AnimatedVectorDrawable) imvVectorAnimated2.getDrawable();
            imvVectorAnimated2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAnimatedVectorDrawable2();
                    Toast.makeText(DrawableActivity.this, "animatedVector2", Toast.LENGTH_LONG).show();
                }
            });
//            ImageView imvVectorAnimated3 = (ImageView) findViewById(R.id.imvAnimatedVector3);
//            animatedVectorDrawable3 = (AnimatedVectorDrawable) imvVectorAnimated3.getDrawable();
//            imvVectorAnimated3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startAnimatedVectorDrawable3();
//                }
//            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //launch animation
        startClipDrawable();
        startRotateDrawable();
        startAnimationDrawable();
        startScaleDrawable();
        if(isPostLollipop) {
            startAnimatedVectorDrawable();
            startAnimatedVectorDrawable2();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //kill the thread
        stopClipDrawable();
        stopRotateDrawable();
        stopAnimationDrawable();
        stopScaleDrawable();
        llScreenRecorder.stopRecording();
    }

    /***********************************************************
     *  Managing ClipDrawable
     *  you need a ClipDrawable, a Handler and a Runnable
     **********************************************************/
    /**
     * The clipDrawable
     */
    ClipDrawable clipDrawableHorizontal,clipDrawableHorizontalTop,clipDrawableVertical,clipDrawableVerticalLeft;
    /**
     * The Handler to run the ClipdrawableRunnable in the UI thread
     */
    Handler clipDrawableHandler=new Handler();
    /**
     * The Runnbale to change the ClipDrawable level
     */
    Runnable clipDrawableRunnable=new Runnable() {
        /**
         * ClipDrawableLevel is between 0 and 10 000
         */
        int level=0;
        boolean reverse=false;
        private static final int step = 100;
        private static final int maxLevel = 10000;
        private static final int minLevel = 0;
        @Override
        public void run() {
            if(reverse){
                level=level- step;
            }else {
                level=level+step;
            }
            clipDrawableHorizontal.setLevel(level);
            clipDrawableHorizontalTop.setLevel(level);
            clipDrawableVertical.setLevel(level);
            clipDrawableVerticalLeft.setLevel(level);
            if(level+step>maxLevel){
                reverse=true;
            }
            if(level-step<minLevel){
                reverse=false;
            }
            clipDrawableHandler.postDelayed(clipDrawableRunnable,32);
        }
    };


    /**
     * Start the clipDrawable animation
     * Should be called in onResume or after
     */
    private void startClipDrawable(){
        clipDrawableHandler.postDelayed(clipDrawableRunnable,32);
    }

    /**
     * Stop the clipDrawable animation
     * Need to be called in onPause
     */
    private void stopClipDrawable(){
        clipDrawableHandler.removeCallbacks(clipDrawableRunnable);
    }


    /***********************************************************
     *  Managing RotateDrawable
     *  you need a RotateDrawable, a Handler and a Runnable
     **********************************************************/
    /**
     * The RotateDrawable
     */
    RotateDrawable rotateDrawableAndroid2ee,rotateDrawableWheel,rotateDrawableAntiWheel;
    /**
     * The Handler to run the rotateDrawableRunnable in the UI thread
     * In reality you should have use the same handler foir the whole activity
     */
    Handler rotateDrawableHandler=new Handler();
    /**
     * The Runnbale to change the RotateDrawable level
     */
    Runnable rotateDrawableRunnable=new Runnable() {
        /**
         * RotateDrawableLevel is between 0 and 10 000
         */
        int level=0;
        boolean reverse=false;
        private static final int step = 100;
        private static final int maxLevel = 10000;
        private static final int minLevel = 0;
        @Override
        public void run() {
            if(reverse){
                level=level- step;
            }else{
                level=level+step;
            }
            rotateDrawableAndroid2ee.setLevel(level);
            rotateDrawableAntiWheel.setLevel(level);
            rotateDrawableWheel.setLevel(level);
            if(level+step>maxLevel){
                reverse=true;
                startTransitionDrawable();
            }
            if(level-step<minLevel){
                reverse=false;
                reverseTransitionDrawable();
            }
            rotateDrawableHandler.postDelayed(rotateDrawableRunnable,32);
        }
    };


    /**
     * Start the rotateDrawable animation
     * Should be called in onResume or after
     */
    private void startRotateDrawable(){
        rotateDrawableHandler.postDelayed(rotateDrawableRunnable,32);
    }

    /**
     * Stop the rotateDrawable animation
     * Need to be called in onPause
     */
    private void stopRotateDrawable(){
        rotateDrawableHandler.removeCallbacks(rotateDrawableRunnable);
    }

    /***********************************************************
     *  Managing ScaleDrawable
     *  you need a ScaleDrawable, a Handler and a Runnable
     **********************************************************/
    /**
     * The ScaleDrawable
     */
    ScaleDrawable scaleDrawable;
    /**
     * The Handler to run the rotateDrawableRunnable in the UI thread
     * In reality you should have use the same handler foir the whole activity
     */
    Handler scaleDrawableHandler=new Handler();
    /**
     * The Runnbale to change the RotateDrawable level
     */
    Runnable scaleDrawableRunnable=new Runnable() {
        /**
         * RotateDrawableLevel is between 0 and 10 000
         */
        int level=0;
        boolean reverse=false;
        private static final int step = 100;
        private static final int maxLevel = 10000;
        private static final int minLevel = 0;
        @Override
        public void run() {
            if(reverse){
                level=level- step;
            }else{
                level=level+step;
            }
            scaleDrawable.setLevel(level);
            if(level+step>maxLevel){
                reverse=true;
            }
            if(level-step<minLevel){
                reverse=false;
            }
            scaleDrawableHandler.postDelayed(scaleDrawableRunnable,32);
        }
    };


    /**
     * Start the scaleDrawable animation
     * Should be called in onResume or after
     */
    private void startScaleDrawable(){
        scaleDrawableHandler.postDelayed(scaleDrawableRunnable,32);
    }

    /**
     * Stop the scaleDrawable animation
     * Need to be called in onPause
     */
    private void stopScaleDrawable(){
        scaleDrawableHandler.removeCallbacks(scaleDrawableRunnable);
    }
    /***********************************************************
     *  Managing AnimationDrawable
     *  you need a AnimationDrawable and that's all :)
     **********************************************************/
    /**
     * The AnimationDrawable
     */
    AnimationDrawable animationDrawable;


    /**
     * Start the rotateDrawable animation
     * Should be called in onResume or after
     */
    private void startAnimationDrawable(){
        animationDrawable.start();
    }

    /**
     * Stop the rotateDrawable animation
     * Need to be called in onPause
     */
    private void stopAnimationDrawable(){
        animationDrawable.stop();
    }

    /***********************************************************
     *  Manage TransitionDrawable
     *  You need a transition drawable and an event
     **********************************************************/
    /***
     * A transition drawable
     */
    TransitionDrawable transitionDrawable;
    /**
     * Start the transition of the transition drawable
     */
    private void startTransitionDrawable(){
        transitionDrawable.startTransition(3000);
    }

    /**
     * Reverse the transition of the transition drawable
     */
    private void reverseTransitionDrawable(){
        transitionDrawable.reverseTransition(3000);
    }

    /***********************************************************
     *  Managing VectorAnimation
     *  You need VectorDrawable, and events
     **********************************************************/
    AnimatedVectorDrawable animatedVectorDrawable,animatedVectorDrawable2,animatedVectorDrawable3;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startAnimatedVectorDrawable(){
        animatedVectorDrawable.start();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startAnimatedVectorDrawable2(){
        animatedVectorDrawable2.start();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startAnimatedVectorDrawable3(){
        Log.e("DrawableActivity","Animation android2ee started");
        animatedVectorDrawable3.start();
    }

    /***********************************************************
     * Managing Menu
     **********************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawable, menu);
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
        }else if( id== R.id.action_screen_record){
            //start recording the layout
            llScreenRecorder.startRecording();
        }

        return super.onOptionsItemSelected(item);
    }
    /***********************************************************
     *  Mother Activity abstract method
     **********************************************************/
    /**
     * For having the Tag of my daughter class
     *
     * @return
     */
    @Override
    protected String getLogTag() {
        return "DrawableActivity";
    }
}
