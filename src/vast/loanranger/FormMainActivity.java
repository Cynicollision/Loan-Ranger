package vast.loanranger;

import java.util.Map;

import vast.loanranger.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.res.*;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;


public class FormMainActivity extends TabActivity 
{
	private final int MENUITEM_SEND = Menu.FIRST;
	private final int MENUITEM_REVERT = Menu.FIRST+1;
	private final int MENUITEM_CLEAR = Menu.FIRST+2;
	private final String ALERT_CONFIRM_CLEAR = "Are you sure you want to clear all case data?";
	
	public static FormTab1Activity tab1;
	public static FormTab2Activity tab2;
	public static FormTab3Activity tab3;
	public static FormTab4Activity tab4;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.form_main);

    	// Required Variables
    	Resources res = getResources();
    	TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
    	Intent intent;
    	TabHost.TabSpec spec;

    	// Adding tab1
    	intent = new Intent().setClass(this, FormTab1Activity.class);
    	spec = tabHost.newTabSpec("tab1").setIndicator("Loan Officer", res.getDrawable(R.drawable.ic_tab1)).setContent(intent);
    	tabHost.addTab(spec);

    	// Adding tab2
    	intent = new Intent().setClass(this, FormTab2Activity.class);
    	spec = tabHost.newTabSpec("tab2").setIndicator("Customer", res.getDrawable(R.drawable.ic_tab2)).setContent(intent);
    	tabHost.addTab(spec);

    	// Adding tab3
    	intent = new Intent().setClass(this, FormTab3Activity.class);
    	spec = tabHost.newTabSpec("tab3").setIndicator("Property", res.getDrawable(R.drawable.ic_tab3)).setContent(intent);
    	tabHost.addTab(spec);
    	
    	// Adding tab4
    	intent = new Intent().setClass(this, FormTab4Activity.class);
    	spec = tabHost.newTabSpec("tab4").setIndicator("Notes", res.getDrawable(R.drawable.ic_tab4)).setContent(intent);
    	tabHost.addTab(spec);

    	// Set default tab
    	for (int i = 3; i >= 0; i--)
    		tabHost.setCurrentTab(i);
    }
    
    public boolean onCreateOptionsMenu(Menu menu) 
    {

       super.onCreateOptionsMenu(menu); // call super's method
       
       // add options to menu
       menu.add(Menu.NONE, MENUITEM_CLEAR, Menu.NONE, R.string.menuitem_clear);
       menu.add(Menu.NONE, MENUITEM_REVERT, Menu.NONE, R.string.menuitem_revert);
       menu.add(Menu.NONE, MENUITEM_SEND, Menu.NONE,  R.string.menuitem_send);

       return true; // options menu creation was handled
    }

    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	String results = "";
       // switch based on the MenuItem id
    	switch (item.getItemId()) 
    	{
    		case MENUITEM_CLEAR:
    			clearCurrentCase();
    		break;

    		case MENUITEM_SEND:
    			results = LoanRangerActivity.getCurrentCase().send();
    			AlertDialog.Builder builder = new AlertDialog.Builder(this)
    	    	.setMessage(results)
    	    	.setCancelable(false)
    	    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	    		public void onClick(DialogInterface dialog, int id) {
    	    			dialog.cancel();
    	    		}
    	    	});
    	   		
    	   		AlertDialog alert = builder.create();
    	   		alert.show();
    		break;
    		
    		default: // REVERT
    			
    		break;
       }

       return super.onOptionsItemSelected(item);

    }


    
    private void clearCurrentCase() 
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this)
    	.setMessage(ALERT_CONFIRM_CLEAR)
    	.setCancelable(false)
    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			LoanRangerActivity.setCurrentCase(new Case());
    			tab1.refresh();
    			tab2.refresh();
    			tab3.refresh();
    			tab4.refresh();
    		}
    	})
   		.setNegativeButton("No", new DialogInterface.OnClickListener() {
   			public void onClick(DialogInterface dialog, int id) {
   				dialog.cancel();
   			}
       });
   		AlertDialog alert = builder.create();
   		alert.show();
	}

	/**
     * populate
     * Populates the form editor elements on this activity with the data 
     * stored in the current Case object.
     * @param s Activity containing the form elements to be updated.
     */
    public static void populate(Activity s)
    {
    	Map<Integer, ValuePair> data = LoanRangerActivity.getCurrentCase().data;
        EditText edittext;
        Spinner spinner;
        String str;
        ValuePair currentItem;
        
        for (int i : data.keySet())
        {
        	if (s.findViewById(i) != null)
        	{
        		// Element IS on this tab
        		currentItem = data.get(i);
        		if (currentItem.getLabel().endsWith("Cde"))
        		{
        			// 1) Element is a regular Spinner
        			spinner = (Spinner)s.findViewById(i);
        			int n;
        			if (currentItem.getValue().length() == 0)
        				n = 0;
        			else
        				n = Integer.parseInt(currentItem.getValue());
        			spinner.setSelection(n);

        			spinner.setOnItemSelectedListener(new SpinnerListener(i));
        		}
        		else if (currentItem.getLabel().endsWith("Ind"))
        		{
        			// 2) Element is a true/false Spinner
        			spinner = (Spinner)s.findViewById(i);
        			if (currentItem.getValue().equals("true"))
        				spinner.setSelection(1);
        			else if (currentItem.getValue().equals("false"))
        				spinner.setSelection(2);
        			else
        				spinner.setSelection(0);
        			
        			spinner.setOnItemSelectedListener(new SpinnerListenerInd(i));
        		}
        		else
        		{
        			// 3) Element is an EditText
        			str = currentItem.getValue();
        			edittext = (EditText)s.findViewById(i);
        			
        			// Special case - dates, trim off time stamp
        			if (currentItem.getLabel().equals("RequestDte") || currentItem.getLabel().equals("CompletionDte"))
        			{
        				if (str.length() != 0)
        					str = str.substring(0, str.indexOf(' '));
        				else
        					str = "";
        			}
        			
        			edittext.setText(str);
        			edittext.addTextChangedListener(new FieldListener(i, edittext));
        		}
        	}
        }
    }
    
    /**
     * FieldListener
     * TextWatcher listener for EditText elements. 
     */
    private static class FieldListener implements TextWatcher
    {
    	int id;
    	EditText parent;
    	String label;
    	
    	/**
    	 * Constructor
    	 * @param id Form element ID (R.id...)
    	 * @param parent Reference to the EditText from which to pull text
    	 */
    	public FieldListener(int id, EditText parent)
    	{
    		this.id = id;
    		this.parent = parent;
    	}

    	/**
    	 * afterTextChanged
    	 * Updates the current Case object with the appropriate data as it is entered in by the user.
    	 */
		public void afterTextChanged(Editable e) 
		{
			label = LoanRangerActivity.getCurrentCase().data.get(id).getLabel();
			LoanRangerActivity.getCurrentCase().data.put(id, new ValuePair(label, parent.getText().toString()));
		}

		// Unimplemented inherited TextWatcher methods...
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
    }
    
    private static class SpinnerListener implements OnItemSelectedListener
    {
    	private int id;
    	String label;
    	
    	public SpinnerListener(int id)
    	{
    		this.id = id;
    	}
		public void onItemSelected(AdapterView<?> adapter, View parent, int position, long n) 
		{
			label = LoanRangerActivity.getCurrentCase().data.get(id).getLabel();
			LoanRangerActivity.getCurrentCase().data.put(id, new ValuePair(label, "" + position));
		}

		public void onNothingSelected(AdapterView<?> arg0) { }
    }
    
    private static class SpinnerListenerInd implements OnItemSelectedListener
    {
    	private int id;
    	String label;
    	
    	public SpinnerListenerInd(int id)
    	{
    		this.id = id;
    	}
		public void onItemSelected(AdapterView<?> adapter, View parent, int position, long n) 
		{
			label = LoanRangerActivity.getCurrentCase().data.get(id).getLabel();
			LoanRangerActivity.getCurrentCase().data.put(id, new ValuePair(label, "" + (position == 1)));
		}

		public void onNothingSelected(AdapterView<?> arg0) { }
    }
}