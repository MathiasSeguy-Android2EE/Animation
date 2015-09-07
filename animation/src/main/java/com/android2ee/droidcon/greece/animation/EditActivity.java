package com.android2ee.droidcon.greece.animation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android2ee.droidcon.greece.animation.transverse.Constant;

public class EditActivity extends MotherActivity {

    ImageButton btnEditOk;
    ImageButton btnEditNOk;
    EditText edtValues;
    TextView txvViewProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create the view
        setContentView(R.layout.activity_edit);
        //instanciate the graphical components
        txvViewProperty= (TextView) findViewById(R.id.txvViewProperty);
        btnEditNOk= (ImageButton) findViewById(R.id.btnEditNOk);
        btnEditOk= (ImageButton) findViewById(R.id.btnEditOk);
        edtValues= (EditText) findViewById(R.id.edtValues);
        //add listeners
        btnEditNOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editionCanceled();
            }
        });
        btnEditOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editionOk();
            }
        });
        //initialize the activity
        if(getIntent().getExtras()!=null){
            String message=getIntent().getExtras().getString(Constant.TXV_VALUE);
            edtValues.setText(message);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        //View property is donefor making animation obvious, simple and simple
        if(getResources().getBoolean(R.bool.postICS)){
            Log.e("EditActivity","txvViewProperty started");
            txvViewProperty.animate().setDuration(3000).rotationX(360).start();
        }
    }

    /***
     * The edition is ok, send back the value of the edittext
     */
    private void editionOk(){
        //return the data to the calling activity
        Intent data =new Intent();
        data.putExtra(Constant.TXV_VALUE,edtValues.getText().toString());
        setResult(RESULT_OK,data);
        finish();
    }

    /**
     * Edition canceled return nada to the caller
     */
    private void editionCanceled(){
        //return nada to the calling activity
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * For having the Tag of my daughter class
     *
     * @return
     */
    @Override
    protected String getLogTag() {
        return "EditActivity";
    }
}
