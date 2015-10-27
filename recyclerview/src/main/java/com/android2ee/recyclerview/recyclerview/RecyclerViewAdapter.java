/**
 * <ul>
 * <li>RecyclerViewAdapter</li>
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

package com.android2ee.recyclerview.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android2ee.recyclerview.R;
import com.android2ee.recyclerview.model.Human;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 12/10/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    /***********************************************************
     * Attributes
     **********************************************************/
    private ArrayList<Human> humans;
    private LayoutInflater inflater;
    /***********************************************************
     *  TezmpVariables
     *********************************************************/
    private View tNewView;
    private ViewHolder tViewHolder;
    private Human human;
    /***********************************************************
     * Constructor
     **********************************************************/
    public RecyclerViewAdapter(ArrayList<Human> dataSet,Context ctx){
        humans=dataSet;
        inflater=LayoutInflater.from(ctx);
    }


    /**    Create the new View and clue it with the return ViewHolder *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // Log.e("RecyclerViewAdapter"," onCreateViewHolder viewtype="+viewType);
        if(viewType==0){
            tNewView=inflater.inflate(R.layout.simple_item,parent,false);
        }else{
            tNewView=inflater.inflate(R.layout.simple_item_odd,parent,false);
        }
        tViewHolder=new ViewHolder(tNewView);
        return tViewHolder;
    }

    /**    Update the View holds by the ViewHolder gave *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        human=humans.get(position);
        holder.getTxvName().setText(human.getName());
        holder.getTxvFirstName().setText(human.getFirstName());
        holder.getTxvMessage().setText(human.getMessage());
        holder.position=position;
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return humans.size();
    }

    /**
     * Return the view type of the item at <code>position</code> for the purposes
     * of view recycling.
     * <p/>
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     * <code>position</code>. Type codes need not be contiguous.
     */
    @Override
    public int getItemViewType(int position) {
        human=humans.get(position);
      //  Log.e("RecyclerViewAdapter"," onCreateViewHolder human.getFirstName()="+human.getFirstName());
        if(human.getFirstName().equals("Jeanne")){
            return 1;
        }else{
            return 0;
        }
    }

    /***********************************************************
     * ViewHolder pattern
     **********************************************************/
    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txvName=null;
        TextView txvFirstName=null;
        TextView txvMessage=null;
        View.OnClickListener clickListener;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            txvName= (TextView) itemView.findViewById(R.id.txvName);
            txvFirstName= (TextView) itemView.findViewById(R.id.txvFirstName);
            txvMessage= (TextView) itemView.findViewById(R.id.txvMessage);
            clickListener=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTxvMessageVisibilityState();
                }
            };
            itemView.setOnClickListener(clickListener);

        }

        public TextView getTxvFirstName() {
            return txvFirstName;
        }

        public TextView getTxvMessage() {
            return txvMessage;
        }

        public TextView getTxvName() {
            return txvName;
        }
        public void changeTxvMessageVisibilityState(){
            Log.e("RecyclerViewAdapter","change visibility associated with itemView onclick");
//            if(txvMessage.getVisibility()==View.VISIBLE){
//                txvMessage.setVisibility(View.GONE);
//            }else{
//                txvMessage.setVisibility(View.VISIBLE);
//            }
        }
    }
}
