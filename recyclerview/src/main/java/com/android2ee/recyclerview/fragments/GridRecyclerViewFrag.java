/**
 * <ul>
 * <li>GridRecyclerViewFrag</li>
 * <li>com.android2ee.recyclerview.fragments</li>
 * <li>13/10/2015</li>
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

package com.android2ee.recyclerview.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Mathias Seguy - Android2EE on 13/10/2015.
 */
public class GridRecyclerViewFrag extends MotherRecyclerViewFrag {
    /**mandatory*/
    public static GridRecyclerViewFrag newInstance() {

        Bundle args = new Bundle();

        GridRecyclerViewFrag fragment = new GridRecyclerViewFrag();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @return The linearLayout to use when you are a daughter class
     */
    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
         /** @param context Current context, will be used to access resources.
         * @param spanCount The number of columns or rows in the grid
         * @param orientation Layout orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
         * @param reverseLayout When set to true, layouts from end to start.*/
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        //define specific span of specific cells according to a rule
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int arg0) {
                return (arg0 % 3) == 0 ? 2 : 1;
            }
        });
        return gridLayoutManager;
    }

}
