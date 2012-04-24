package vast.loanranger;

import vast.loanranger.R;
import android.app.TabActivity;
import android.content.res.*;
import android.os.Bundle;
import android.content.Intent;
import android.widget.*;


public class FormMainActivity extends TabActivity 
{
	EditText officerNameEditText, 
			 countyEditText,
			 requestDateEditText,
			 completionDateEditText,
			 contactNameEditText,
			 contactNumberEditText,
			 transactionSizeEditText,
			 propertyAddressEditText,
			 propertyAcresEditText,
			 legalDescriptionEditText,
			 caseNotesTxt;
	
	Spinner requestingBranchSpinner,
			stateSpinner,
			regionalManagerSpinner,
			customerTypeSpinner,
			advanceNoticeSpinner,
			requestReasonSpinner,
			propertyTypeSpinner,
			propertyComplexitySpinner;
			
			
	
    /** Called when the activity is first created. */
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
    	
    	// Set up spinners
    	ArrayAdapter<CharSequence> adapter;
    	
    	requestingBranchSpinner = (Spinner)findViewById(R.id.requestingBranchSpinner);
    	stateSpinner = (Spinner)findViewById(R.id.stateSpinner);
    	regionalManagerSpinner = (Spinner)findViewById(R.id.regionalManagerSpinner);
    	customerTypeSpinner = (Spinner)findViewById(R.id.customerTypeSpinner);
    	advanceNoticeSpinner = (Spinner)findViewById(R.id.advanceNoticeSpinner);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_requesting_branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestingBranchSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_state, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_regional_manager, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionalManagerSpinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.options_customer_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerTypeSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_advance_notice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        advanceNoticeSpinner.setAdapter(adapter);
        
        requestReasonSpinner = (Spinner)findViewById(R.id.requestReasonSpinner);
        propertyTypeSpinner = (Spinner)findViewById(R.id.propertyTypeSpinner);
        propertyComplexitySpinner = (Spinner)findViewById(R.id.propertyComplexitySpinner);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_evaluation_reason, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestReasonSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_property_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyTypeSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_property_complexity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyComplexitySpinner.setAdapter(adapter);
    }
}