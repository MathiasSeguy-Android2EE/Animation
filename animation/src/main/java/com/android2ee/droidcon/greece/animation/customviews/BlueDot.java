/**
 * <ul>
 * <li>RondBleu</li>
 * <li>com.android2ee.formation.paris.atos.customview</li>
 * <li>07/07/2015</li>
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

package com.android2ee.droidcon.greece.animation.customviews;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.android2ee.droidcon.greece.animation.R;

/**
 * Created by Mathias Seguy - Android2EE on 07/07/2015.
 */
public class BlueDot extends View {
    /**
     * To initialized variable when the animation is launched for the first time
     */
    private static final int NOT_INITIALIZED = -1;
    //Width and height of the view
    int w, h;
    //center and radius of the dot
    int centerX;
    int centerY;
    int radius;
    float smallDotsRadius, initialDotsRadius;
    //Pain used to draw the dot
    private Paint paintUsual, dotPaint;
    //String when gingerbread
    private String gingerbread = "Gingerdread :(";

    /***********************************************************
     * Managing initialization
     **********************************************************/

    public BlueDot(Context context) {
        super(context);
        init();
    }

    public BlueDot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlueDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paintUsual = new Paint();
        dotPaint = new Paint();
        paintUsual.setColor(Color.BLUE);
        // Define how to draw text
        paintUsual.setStyle(Paint.Style.FILL_AND_STROKE);
        paintUsual.setAntiAlias(true);
    }

    /**
     * @return
     * @chiuki http://chiuki.github.io/android-shaders-filters/#/5
     */
    private int[] getRainbowColors() {
        return new int[]{
                getResources().getColor(R.color.rainbow_red),
                getResources().getColor(R.color.rainbow_yellow),
                getResources().getColor(R.color.rainbow_green),
                getResources().getColor(R.color.rainbow_turquoise),
                getResources().getColor(R.color.rainbow_blue),
                getResources().getColor(R.color.rainbow_purple)
        };
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        centerX = w / 2;
        centerY = h / 2;
        radius = Math.min(w / 8, h / 8);
        //initialize the shader (stuff that make the color of the paint depending on the location in the screen and a set of colors)
        //@chiuki at droidcon london
        int[] rainbow = getRainbowColors();
        Shader shader = new LinearGradient(0, 0, 0, w, rainbow,
                null, Shader.TileMode.MIRROR);
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        shader.setLocalMatrix(matrix);
        dotPaint.setShader(shader);
    }

    /***********************************************************
     * Managing Animation
     **********************************************************/
    /***
     * The ObjectAnimator that handles the animation
     */
    ObjectAnimator animToto, animTata;
    /***
     * Is post IceCreamSandwich
     */
    boolean postICS = true;
    /***
     * Is animating right now ?
     */
    boolean animating = false;
    /**
     * Is Toto Animation has been reversed ?
     */
    boolean animTotoRepeating = false;
    /***
     * Number of blue dits when splitted
     */
    int numberOfSplittedCircle = NOT_INITIALIZED;
    /***
     * X,Y coordinates of each dots (colorFilterArray of primitive is the leightweigth way to do that)
     */
    int[] splittedCirlceX, splittedCirlceY;
    /***
     * Direction way of each dots to stay inside the area of the View
     */
    boolean[] positiveXDirection, positiveYDirection;
    /***
     * Direction of each dots
     */
    double[] cosDirI, sinDirI;
    /***
     * Direction of each dots
     */
    float[] deltaX, deltaY;

    /**
     * Start animating the rondBleu
     *
     * @param postICS if postICS version
     */
    @SuppressLint("NewApi")
    public void startAnimation(boolean postICS) {
        Log.e("BlueDot", "StartAnimating postICS=" + postICS);
        this.postICS = postICS;
        //will be done only for ICS and above
        if (postICS) {
            //do chet haaze animation(https://www.youtube.com/watch?v=vCTcmPIKgpM)
            if (numberOfSplittedCircle == NOT_INITIALIZED) {
                //initialize all the elements needed for the animation
                numberOfSplittedCircle = (int) (Math.random() * 11) + 7;
                splittedCirlceX = new int[numberOfSplittedCircle];
                splittedCirlceY = new int[numberOfSplittedCircle];
                positiveXDirection = new boolean[numberOfSplittedCircle];
                positiveYDirection = new boolean[numberOfSplittedCircle];
                deltaX = new float[numberOfSplittedCircle];
                deltaY = new float[numberOfSplittedCircle];
                //calculate the cos and sin in the i direction (360*i/numberOfCircle) is the direction
                cosDirI = new double[numberOfSplittedCircle];
                sinDirI = new double[numberOfSplittedCircle];
                for (int i = 0; i < numberOfSplittedCircle; i++) {
                    splittedCirlceX[i] = centerX;
                    splittedCirlceY[i] = centerY;
                    cosDirI[i] = Math.cos((i * (2 * Math.PI / (float) numberOfSplittedCircle)));
                    sinDirI[i] = Math.sin((i * (2 * Math.PI / (float) numberOfSplittedCircle)));
                    positiveXDirection[i] = true;
                    positiveYDirection[i] = true;
                }
                smallDotsRadius = initialDotsRadius = (radius / (float) numberOfSplittedCircle);
                animToto = getObjectAnimator();
                animToto.setDuration(5000);
                animToto.setRepeatMode(ValueAnimator.REVERSE);
                animToto.setRepeatCount(1);
                animToto.setInterpolator(new AccelerateInterpolator());
                animToto.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        animating = true;
                        animTotoRepeating = false;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animating = false;
                        launchReturnToCenterAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        animating = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        animating = true;
                        animTotoRepeating = true;
                    }
                });
            }
            if (!animating) {
                for (int i = 0; i < numberOfSplittedCircle; i++) {
                    positiveXDirection[i] = true;
                    positiveYDirection[i] = true;
                }
                animToto.start();
            }
        } else {
            //do nothing fucking gingerbread
        }
    }

    /**
     * The main Method that returns a ObjectAnimator
     *
     * @return The ObjectAnimator that animates the views
     */
    @SuppressLint("NewApi")
    ObjectAnimator getObjectAnimator() {
        return ObjectAnimator.ofInt(this, "toto", 0, 110);
    }

    /**
     * The property to animate
     *
     * @param parameter value of the state to calculate the animation of the object
     */
    private void setToto(int parameter) {
        //important to define the dirty area not to have to redraw every pixel
        Rect dirtyRect = new Rect(splittedCirlceX[0] - radius, splittedCirlceY[0] - radius, splittedCirlceX[0] + radius, splittedCirlceY[0] + radius);
        //the current dirty rectangle for one dot
        Rect currentRect = new Rect(splittedCirlceX[0] - radius, splittedCirlceY[0] - radius, splittedCirlceX[0] + radius, splittedCirlceY[0] + radius);
        //for each dot, updates its property (direction, color...)
        for (int i = 0; i < numberOfSplittedCircle; i++) {
            //important to define the dirty area not to have to redraw every pixel
            currentRect.left = splittedCirlceX[i] - radius;
            currentRect.top = splittedCirlceY[i] - radius;
            currentRect.right = splittedCirlceX[i] + radius;
            currentRect.bottom = splittedCirlceY[i] + radius;
            dirtyRect.union(currentRect);
            //find the x coordinate of the dot i
            if (positiveXDirection[i]) {
                splittedCirlceX[i] = (int) (splittedCirlceX[i] + cosDirI[i] * parameter);
            } else {
                splittedCirlceX[i] = (int) (splittedCirlceX[i] - cosDirI[i] * parameter);
            }
            //find if we need to reverse the movement in x of dot i to stay in the area of the view
            if (splittedCirlceX[i] < 0 || splittedCirlceX[i] > w) {
                positiveXDirection[i] = !positiveXDirection[i];
            }
            //find the y coordinates of the dot i
            if (positiveYDirection[i]) {
                splittedCirlceY[i] = (int) (splittedCirlceY[i] + sinDirI[i] * parameter);
            } else {
                splittedCirlceY[i] = (int) (splittedCirlceY[i] - sinDirI[i] * parameter);
            }
            //find if we need to reverse the movement in y of dot i to stay in the area of the view
            if (splittedCirlceY[i] < 0 || splittedCirlceY[i] > h) {
                positiveYDirection[i] = !positiveYDirection[i];
            }
            //update dirty zone
            currentRect.left = splittedCirlceX[i] - radius;
            currentRect.top = splittedCirlceY[i] - radius;
            currentRect.right = splittedCirlceX[i] + radius;
            currentRect.bottom = splittedCirlceY[i] + radius;
            dirtyRect.union(currentRect);
        }
        if (parameter < 25 && !animTotoRepeating) {
            smallDotsRadius = ((25 - parameter) * radius + parameter * initialDotsRadius) / 25;
        }
//        smallDotsRadius =((radius- initialDotsRadius)*(101-parameter))/100;
        //then invalidate the dirty rectangle
        invalidate(dirtyRect);
    }

    /**
     * This method changes animations parameter to redfine movement
     * such as all balls go back to the center
     */
    @SuppressLint("NewApi")
    private void launchReturnToCenterAnimation() {
        animTata = getTataAnimator();
        animTata.setDuration(5000);
        animTata.setInterpolator(new DecelerateInterpolator());
        if (!animating) {
            animTata.start();
            animating = true;
        }
        animTata.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                animating = true;
            }
        });
    }

    /**
     * The main Method that returns a ObjectAnimator
     *
     * @return The ObjectAnimator that animates the views
     */
    @SuppressLint("NewApi")
    ObjectAnimator getTataAnimator() {
        //define value
        for (int i = 0; i < numberOfSplittedCircle; i++) {
            deltaX[i] = ((float) (splittedCirlceX[i] - centerX)) / 100;
            deltaY[i] = ((float) (splittedCirlceY[i] - centerY)) / 100;
            Log.e("BlueDot", "calcul pour X " + i + " : " + splittedCirlceX[i] + "-" + centerX + "/100=" + deltaX[i]);
            Log.e("BlueDot", "calcul pour Y " + i + " : " + splittedCirlceY[i] + "-" + centerY + "/100=" + deltaY[i]);
        }
        return ObjectAnimator.ofInt(this, "tata", 0, 100);
    }

    /**
     * Gather the balls in the middle
     *
     * @param parameter Between 0 and 100
     */
    private void setTata(int parameter) {
        //important to define the dirty area not to have to redraw every pixel
        Rect dirtyRect = new Rect(splittedCirlceX[0] - radius, splittedCirlceY[0] - radius, splittedCirlceX[0] + radius, splittedCirlceY[0] + radius);
        //the current dirty rectangle for one dot
        Rect currentRect = new Rect(splittedCirlceX[0] - radius, splittedCirlceY[0] - radius, splittedCirlceX[0] + radius, splittedCirlceY[0] + radius);
        //for each dot, updates its property (direction, color...)
        for (int i = 0; i < numberOfSplittedCircle; i++) {
            //update dirty zone
            currentRect.left = splittedCirlceX[i] - radius;
            currentRect.top = splittedCirlceY[i] - radius;
            currentRect.right = splittedCirlceX[i] + radius;
            currentRect.bottom = splittedCirlceY[i] + radius;
            dirtyRect.union(currentRect);
            //find the x coordinate of the dot i
            splittedCirlceX[i] = (int) (centerX + ((100 - parameter) * deltaX[i]));
            //find the y coordinates of the dot i
            splittedCirlceY[i] = (int) (centerY + ((100 - parameter) * deltaY[i]));

            //find if we need to reverse the movement in y of dot i to stay in the area of the view
            //update dirty zone
            currentRect.left = splittedCirlceX[i] - radius;
            currentRect.top = splittedCirlceY[i] - radius;
            currentRect.right = splittedCirlceX[i] + radius;
            currentRect.bottom = splittedCirlceY[i] + radius;
            dirtyRect.union(currentRect);
        }
        // smallDotsRadius =((radius- initialDotsRadius)*parameter)/100;
        if (parameter > 50) {
            smallDotsRadius = ((radius - initialDotsRadius) * parameter / 50) + (2 * initialDotsRadius - radius);
        }
        //then invalidate the dirty rectangle
        invalidate(dirtyRect);
    }

    /***********************************************************
     * OnDraw
     **********************************************************/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!animating) {
            canvas.drawCircle(centerX, centerY, radius, paintUsual);
            dotPaint.setStyle(Paint.Style.STROKE);
        } else {
            dotPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        if (animating && !postICS) {
            canvas.drawText(gingerbread, centerX - w, centerY, paintUsual);
        }
        if (animating && postICS) {
            for (int i = 0; i < numberOfSplittedCircle; i++) {
                canvas.drawCircle(splittedCirlceX[i], splittedCirlceY[i], smallDotsRadius, dotPaint);
            }
        }
    }
}
