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
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by Mathias Seguy - Android2EE on 13/10/2015.
 */
public class StaggeredRecyclerViewFrag extends MotherRecyclerViewFrag {
    /**mandatory*/
    public static StaggeredRecyclerViewFrag newInstance() {

        Bundle args = new Bundle();

        StaggeredRecyclerViewFrag fragment = new StaggeredRecyclerViewFrag();
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * @return The linearLayout to use when you are a daughter class
     */
    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        /**
         * @param spanCount   If orientation is vertical, spanCount is number of columns. If
         *                    orientation is horizontal, spanCount is number of rows.
         * @param orientation {@link #VERTICAL} or {@link #HORIZONTAL}
         */
        StaggeredGridLayoutManager stagLayoutManager=new StaggeredGridLayoutManager(2,GridLayoutManager.VERTICAL);
        /**
         * GAP_HANDLING_NONE ne fait rien quand il y a un trou
         * GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS recorrige le trou pour les mettre en fin de ligne
         */
        stagLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        return stagLayoutManager;
    }
}
