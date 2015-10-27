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

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android2ee.recyclerview.R;
import com.android2ee.recyclerview.model.Human;
import com.android2ee.recyclerview.recyclerview.RecyclerItemClickListener;
import com.android2ee.recyclerview.recyclerview.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 12/10/2015.
 */
public abstract class MotherRecyclerViewFrag extends Fragment {

    private RecyclerView  recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    protected ArrayList<Human> humans;
    private Button btnAdd;
    private Button btnDelete;


    /**
     * The selected item view
     */
    private View selectedItemView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        humans=new ArrayList<Human>();
        for(int i=0;i<5;i++){
            switch(i%3){
                case 0:
                    humans.add(new Human("Doe","John","Another words "+i));
                    break;
                case 1:
                    humans.add(new Human("Doe","Jeanne","Hi a sentence "+i));
                    break;
                case 2:
                    humans.add(new Human("Seguy","Mathias","A stupid list, I know it "+i));
                    break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView=inflater.inflate(R.layout.recyclerview,container,false);
        recyclerView= (RecyclerView) myView.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(false);

        // use a linear layout manager
        recyclerViewLayoutManager = getLayoutManager();
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        // specify an adapter (see also next example)
        recyclerViewAdapter = getRecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        //add an item click listener
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Recycler item has been clicked
                showSnackBar(view,position);
            }
        }));
        //as menu, yep
        setHasOptionsMenu(true);
        return myView ;
    }

    /***
     * Called when a click is done on an Item of the RecyclerView
     * Show a snack bar with the name of the selected human
     * @param position
     */
    public void showSnackBar(View view,int position){
        selectedItemView=view;
        Snackbar
                .make(getView(),"Hello "+humans.get(position).getFirstName(), Snackbar.LENGTH_LONG)
                .setAction("Tint", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBarAction();
                    }
                })
                .show();
    }

    /**
     * Called when the snackBar action is touched
     * Tint the Item touched
     */
    private void snackBarAction() {
        /*This method Tint all the views in fact because I am not managing different pool of recycler view*/
        ImageView imvPitcure= (ImageView) selectedItemView.findViewById(R.id.imvPicture);
        Drawable wrapDrawable = DrawableCompat.wrap(imvPitcure.getDrawable());
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            DrawableCompat.setTintMode(wrapDrawable, PorterDuff.Mode.DARKEN);
        }
        DrawableCompat.setTint(wrapDrawable, getResources().getColor(R.color.colorPrimaryTranslucent));
    }

    /**
     *
     * @return The Adapter to use
     */
    protected RecyclerView.Adapter getRecyclerViewAdapter(){
        return new RecyclerViewAdapter(humans,getActivity());
    }
    /**
     *
     * @return The linearLayout to use when you are a daughter class
     */
    public abstract RecyclerView.LayoutManager getLayoutManager();
    /***********************************************************
     *  Adding deleting elements
     **********************************************************/
    public void addHuman(){
        humans.add(0,new Human("John","Do Added","It's me I am back!"));
        recyclerViewAdapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
    }
    public void deleteHuman(){
        humans.remove(0);
        recyclerViewAdapter.notifyItemRemoved(0);
    }

    /***********************************************************
     *  Managing menu
     **********************************************************/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                addHuman();
                return true;
            case R.id.action_delete:
                deleteHuman();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
