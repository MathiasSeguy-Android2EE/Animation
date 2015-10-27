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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.android2ee.recyclerview.fragments.CardViewRecyclerViewFrag;
import com.android2ee.recyclerview.fragments.GridRecyclerViewFrag;
import com.android2ee.recyclerview.fragments.LinearRecyclerViewFrag;
import com.android2ee.recyclerview.fragments.StaggeredRecyclerViewFrag;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 19/05/2015.
 * This class is an Adapter, it's goal is to give at the right time the right view
 * So nothing complex here
 * This class only deal with Fragments !!!
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    /**
     * The list of ordered fragments
     */
    private final ArrayList<Fragment> fragments;

    //On fournit à l'adapter la liste des fragments à afficher

    /***
     * The constructor
     * @param ctx The Context
     */
    public MyPagerAdapter(AppCompatActivity ctx) {
        super(ctx.getSupportFragmentManager());
        fragments = new ArrayList<Fragment>();
        //A stuff I never did before, instanciate my fragment
        fragments.add(new LinearRecyclerViewFrag());
        fragments.add(new GridRecyclerViewFrag());
        fragments.add(new StaggeredRecyclerViewFrag());
        fragments.add(new CardViewRecyclerViewFrag());

    }
    @Override
    public int getCount() {
        return fragments.size();
    }
    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Linear";
            case 1: return "Grid";
            case 2: return "Stag";
            case 3: return "CardView";
        }
        return "This is the Page "+position;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }



}

