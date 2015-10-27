package com.android2ee.recyclerview;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android2ee.recyclerview.viewpager.MyPageTransformer;
import com.android2ee.recyclerview.viewpager.MyPagerAdapter;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);
        //find the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //use it as your action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("RecyclerView");
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
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            viewPager.setPageTransformer(true, new MyPageTransformer());
        }
        //AND CLUE TABLAYOUT AND VIEWPAGER
        tabLayout.setupWithViewPager(viewPager);
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
            Intent otherActivity=new Intent(this, CoordinatorActivity.class);
            startActivity(otherActivity);
            //kill this one
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
