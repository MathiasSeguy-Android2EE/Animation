/**
 * <ul>
 * <li>ForecastRestYahooSax</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo</li>
 * <li>28 mai 2014</li>
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

package com.android2ee.recyclerview.viewpager;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android2ee.recyclerview.R;


/**
 * Created by Mathias Seguy - Android2EE on 19/05/2015.
 * This class aims to make nice animation when swipping from one view to another
 * Only works with post-honeycomb elements
 * Some resources to do thath:!
 * http://developer.android.com/training/animation/screen-slide.html
 * http://stackoverflow.com/questions/18710561/can-i-use-view-pager-with-views-not-with-fragments
 * https://github.com/codepath/android_guides/wiki/ViewPager-with-FragmentPagerAdapter
 * http://www.tutos-android.com/fragment-slider-page-lautre
 * <p/>
 * And for compatibility see:http://stackoverflow.com/questions/15767729/backwards-compatible-pagetransformer
 * To explains how to change properties on a view under gingerbread
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MyPageTransformer implements ViewPager.PageTransformer {
    /****************************************************************************************
     * ViewPager.PageTransformer
     *****************************************************************************************/
    public MyPageTransformer() {
    }

    RecyclerView myRecyclerView;

    public void transformPage(View view, float position) {
        //Only the main layout is passed here/
        myRecyclerView= (RecyclerView) view.findViewById(R.id.my_recycler_view);
        if(position<0.1&&position>-0.1){
            position=0;
        }
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
        } else if (position < 1) { // [-1,1]
            if (position == 0) {
                //you are centered
                for(int i=0;i<myRecyclerView.getChildCount();i++){
                    myRecyclerView.getChildAt(i).setRotationY(0);
                    myRecyclerView.getChildAt(i).setRotationX(0);
                }
            } else if (position < -0.5) {
                //left
                for(int i=0;i<myRecyclerView.getChildCount();i++){
                    myRecyclerView.getChildAt(i).setRotationY((float) (-90*((position+0.5)*2) ));
                }
            } else if (position > 0.5)  {
                //right
                for(int i=0;i<myRecyclerView.getChildCount();i++){
                    myRecyclerView.getChildAt(i).setRotationY((float) (-90*((position-0.5)*2)));
                }
            }
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
        }
    }

}
