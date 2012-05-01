package vast.loanranger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class FormTab2Activity extends Activity 
{
	public static EditText requestDateEditText,
	 		 completionDateEditText,
	 		 contactNameEditText,
	 		 contactNumberEditText;
	
	Spinner customerTypeSpinner,
			advanceNoticeSpinner;
   
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tab2);
        
        // Spinners
        customerTypeSpinner = (Spinner)findViewById(R.id.customerTypeSpinner);
    	advanceNoticeSpinner = (Spinner)findViewById(R.id.advanceNoticeSpinner);
        ArrayAdapter<CharSequence> adapter;
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_customer_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerTypeSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_advance_notice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        advanceNoticeSpinner.setAdapter(adapter);

        FormMainActivity.populate(this);
    }
}