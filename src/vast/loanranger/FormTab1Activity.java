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
    	
        ArrayAdapter<CharSequence> adapter;
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_requesting_branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestingBranchSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_state, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_regional_manager, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionalManagerSpinner.setAdapter(adapter);
        
        FormMainActivity.populate(this);
    }
    
    
}