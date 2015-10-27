/**
 * <ul>
 * <li>UsingLinearRV</li>
 * <li>com.android2ee.recyclerview.fragments</li>
 * <li>12/10/2015</li>
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
import android.support.v7.widget.RecyclerView;

import com.android2ee.recyclerview.recyclerview.CardViewRecyclerViewAdapter;

/**
 * Created by Mathias Seguy - Android2EE on 12/10/2015.
 */
public class CardViewRecyclerViewFrag extends GridRecyclerViewFrag {
    /**mandatory*/
    public static CardViewRecyclerViewFrag newInstance() {

        Bundle args = new Bundle();

        CardViewRecyclerViewFrag fragment = new CardViewRecyclerViewFrag();
        fragment.setArguments(args);
        return fragment;
    }

    protected RecyclerView.Adapter getRecyclerViewAdapter(){
        return new CardViewRecyclerViewAdapter(humans,getContext());
    }
}
