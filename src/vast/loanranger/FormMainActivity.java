package vast.loanranger;

import java.util.Map;

import vast.loanranger.R;
import android.app.Activity;
import android.app.TabActivity;
import android.content.res.*;
import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;


public class FormMainActivity extends TabActivity 
{
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
    	tabHost.setCurrentTab(0);
    }
    
    /**
     * populate
     * Populates the form editor elements on this activity with the data 
     * stored in the current Case object.
     * @param s Activity containing the form elements to be updated.
     */
    public static void populate(Activity s)
    {
    	Map<Integer, ValuePair> data = LoanRangerActivity.currentCase.data;
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
        			// Element is a regular Spinner
        			spinner = (Spinner)s.findViewById(i);
        			spinner.setSelection(Integer.parseInt(currentItem.getValue()));
        			
        			// TODO add spinner change listener
        		}
        		else if (currentItem.getLabel().endsWith("Ind"))
        		{
        			// Element is a true/false Spinner
        			spinner = (Spinner)s.findViewById(i);
        			if (currentItem.getValue().equals("true"))
        				spinner.setSelection(0);
        			else
        				spinner.setSelection(1);
        			
        			// TODO add spinner change listener
        		}
        		else
        		{
        			// Element is an EditText
        			str = currentItem.getValue();
        			edittext = (EditText)s.findViewById(i);
        			
        			// Special case - dates, trim off time stamp
        			if (currentItem.getLabel().equals("RequestDte") || currentItem.getLabel().equals("CompletionDte"))
        				str = str.substring(0, str.indexOf(' '));
        			
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
		public void afterTextChanged(Editable e) {
			label = LoanRangerActivity.currentCase.data.get(id).getLabel();
			LoanRangerActivity.currentCase.data.put(id, new ValuePair(label, parent.getText().toString()));
		}

		// Unimplemented inherited TextWatcher methods...
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
    }
}