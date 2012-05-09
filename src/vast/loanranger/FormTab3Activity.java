package vast.loanranger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class FormTab3Activity extends Activity 
{
	EditText transactionSizeEditText,
	 		 propertyAddressEditText,
	 		 propertyAcresEditText,
	 		 legalDescriptionEditText;
	
	Spinner requestReasonSpinner,
			propertyTypeSpinner,
			propertyComplexitySpinner;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tab3);
        
        // Spinners
        requestReasonSpinner = (Spinner)findViewById(R.id.requestReasonSpinner);
        propertyTypeSpinner = (Spinner)findViewById(R.id.propertyTypeSpinner);
        propertyComplexitySpinner = (Spinner)findViewById(R.id.propertyComplexitySpinner);
        ArrayAdapter<CharSequence> adapter;
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_evaluation_reason, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestReasonSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_property_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyTypeSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_property_complexity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyComplexitySpinner.setAdapter(adapter);
        
        FormMainActivity.tab3 = this;
        FormMainActivity.populate(this);
    }
    
    public void refresh()
    {
    	FormMainActivity.populate(this);
    }
}