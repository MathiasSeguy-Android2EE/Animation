/**
 * <ul>
 * <li>CoordinatorActivity</li>
 * <li>com.android2ee.recyclerview</li>
 * <li>26/10/2015</li>
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

package com.android2ee.recyclerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android2ee.recyclerview.viewpager.MyPagerAdapter;

/**
 * Created by Mathias Seguy - Android2EE on 26/10/2015.
 */
public class CoordinatorActivity extends AppCompatActivity {
    /**
     * The TabLayout itself :)
     */
    TabLayout tabLayout;
    /**
     * The page Adapter : Manage the list of views (in fact here, it's fragments)
     * And send them to the ViewPager
     */
    private MyPagerAdapter pagerAdapter;
    /**
     * The ViewPager is a ViewGroup that manage the swipe from left to right to left
     * Like a listView with a gesture listener...
     */
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_activity_main_5);
        //find the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //use it as your action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Coordinator");
        updateCollapsingTitle();
        //Find the Tab Layout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //Define its gravity and its mode
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //Define the color to use (depending on the state a different color should be disaplyed)
        //Works only if done before adding tabs
        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tab_selector_color));
        //instanciate the PageAdapter
        pagerAdapter=new MyPagerAdapter(this);
        //Find the viewPager
        viewPager = (ViewPager) super.findViewById(R.id.viewpager);
        // Affectation de l'adapter au ViewPager
        viewPager.setAdapter(pagerAdapter);
        viewPager.setClipToPadding(true);
        //Add animation when the page are swiped
        //this instanciation only works with honeyComb and more
        //if you want it all version use AnimatorProxy of the nineoldAndroid lib
        //@see:http://stackoverflow.com/questions/15767729/backwards-compatible-pagetransformer

        //TODO uncomment those lines and the opengl bug disappears
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
//            viewPager.setPageTransformer(true, new MyPageTransformer());
//        }
        //AND CLUE TABLAYOUT AND VIEWPAGER
        tabLayout.setupWithViewPager(viewPager);
    }


    /**
     * Playing with palette to find a color for the title
     */
    public void updateCollapsingTitle(){
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        if(null!=collapsingToolbar) {
            Palette.Builder palBuilder= new Palette.Builder(((BitmapDrawable) getResources().getDrawable(R.drawable.cardview_background)).getBitmap());
            palBuilder.maximumColorCount(24);
            palBuilder.resizeBitmapSize(256);
            AsyncTask<Bitmap, Void, Palette> AsyncPalette=palBuilder.generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    updateTitleColor(palette);
                }
            });
            collapsingToolbar.setTitle("Coordinator");
            collapsingToolbar.setContentScrimResource(R.drawable.cardview_background_toolbar);
        }
    }

    /**
     * Update the title color when the palette is defined
     * @param palette
     */
    private void updateTitleColor(Palette palette){
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        if(null!=collapsingToolbar) {
            Palette.Swatch swatch=palette.getDarkMutedSwatch();
            collapsingToolbar.setCollapsedTitleTextColor(swatch.getTitleTextColor());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_switch){
            //launch the new activity
            Intent otherActivity=new Intent(this, MainActivity.class);
            startActivity(otherActivity);
            //kill this one
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}