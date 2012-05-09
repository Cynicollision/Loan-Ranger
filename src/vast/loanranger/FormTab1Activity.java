package vast.loanranger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class FormTab1Activity extends Activity 
{   
	Spinner requestingBranchSpinner,
			stateSpinner,
			regionalManagerSpinner;

	//public void foo() {
	//	HashMap<String, Value> m = new HashMap<String,Value>();
	//	m.put("Label", new Value(R.id.requestingBranchSpinner, str))
	//}
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tab1);
        
        // Spinners
        requestingBranchSpinner = (Spinner)findViewById(R.id.requestingBranchSpinner);
    	stateSpinner = (Spinner)findViewById(R.id.stateSpinner);
    	regionalManagerSpinner = (Spinner)findViewById(R.id.regionalManagerSpinner);
    	
        ArrayAdapter<CharSequence> adapterRequestingBranch,
        						   adapterState,
        						   adapterRegionalManager;
        
        adapterRequestingBranch = ArrayAdapter.createFromResource(this, R.array.options_requesting_branch, android.R.layout.simple_spinner_item);
        adapterRequestingBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestingBranchSpinner.setAdapter(adapterRequestingBranch);
        
        adapterState = ArrayAdapter.createFromResource(this, R.array.options_state, android.R.layout.simple_spinner_item);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapterState);
        
        adapterRegionalManager = ArrayAdapter.createFromResource(this, R.array.options_regional_manager, android.R.layout.simple_spinner_item);
        adapterRegionalManager.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionalManagerSpinner.setAdapter(adapterRegionalManager);
        
        FormMainActivity.populate(this);
    }
}