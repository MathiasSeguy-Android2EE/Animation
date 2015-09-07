/**
 * <ul>
 * <li>MotherActivity</li>
 * <li>com.android2ee.formation.paris.atos</li>
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

package com.android2ee.droidcon.greece.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Mathias Seguy - Android2EE on 07/07/2015.
 */
public abstract class  MotherActivity extends AppCompatActivity {
    /**
     * For having the Tag of my daughter class
     * @return
     */
    protected abstract String getLogTag();



    /***********************************************************
     *  Life cycle logging
     **********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getLogTag(),"onCreate called");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e(getLogTag(),"onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(getLogTag(),"onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(getLogTag(),"onPause called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.e(getLogTag(),"onStop called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(getLogTag(),"onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(getLogTag(),"onSaveInstanceState called");
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(getLogTag(),"onRestoreInstanceState called");
    }
}
