package com.brejza.matt.habmodem;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ukhas.Payload;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class GraphsFragment extends DialogFragment {
	
	List<String> activePayloads = new ArrayList<String>();
	boolean[] selected;
	View v;
	ConcurrentHashMap<String,Payload> _data;
	
	
	public void onAttach(Activity activity) {
        super.onAttach(activity);

    //   mListener = (NoticeDialogListener) activity;
      
	} 
	
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	LayoutInflater inflater = getActivity().getLayoutInflater();
	    v = inflater.inflate(R.layout.fragment_graphs, null);
        builder.setTitle(R.string.dialog_graphs_title);
        builder.setView(v);
    	
        builder /*.setMultiChoiceItems(R.array.location_dialog_items, new boolean[]  { enPos, enChase },
        		 new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which,
                    boolean isChecked) {
                if (isChecked) {
                    // If the user checked the item, add it to the selected items
                    mSelectedItems.add(which);
                } else if (mSelectedItems.contains(Integer.valueOf(which))) {
                    // Else, if the item is already in the array, remove it 
                    mSelectedItems.remove(Integer.valueOf(which));
                }
            }
        }) */
        
     
        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MISSILES!
                	    
                	  
                	  // mListener.onDialogPositiveClick(LocationSelectFragment.this, enPos, enChase); 
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        		  android.R.layout.simple_list_item_1, activePayloads);
        ListView lv = (ListView) v.findViewById(R.id.lvActivePayloadsGraph);
        lv.setAdapter(adapter);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, _list_log);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
        	  @Override
        	  public void onItemClick(AdapterView<?> parent, View view,
        	    int position, long id) {
        	    Toast.makeText(getActivity().getApplicationContext(),
        	      "Click ListItem Number " + position, Toast.LENGTH_LONG)
        	      .show();
        	    drawGraph();
        	  }
        	}); 
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
    
    public void drawGraph()
    {
    	//LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.llGraphsN);
    	LineGraph line = new LineGraph(_data);
    	for (int i = 0; i < activePayloads.size(); i++)
    		line.putPayload(activePayloads.get(i));
    	View vg = line.getView(getActivity());
    	//v.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
    	((LinearLayout)v.findViewById(R.id.llGraphsN)).addView(vg);
    }
    
    public void setActivePayloads(List<String> ap, ConcurrentHashMap<String,Payload> data)
    {
    	_data = data;
    	activePayloads = ap;
    	selected = new boolean[ap.size()];
    }
}
