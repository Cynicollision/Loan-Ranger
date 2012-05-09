package vast.loanranger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class FormTab2Activity extends Activity 
{
	private TextView phoneNumber;
	
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
        
        phoneNumber = (TextView)findViewById(R.id.contactNumberTextView);
        phoneNumber.setOnLongClickListener(new AdapterView.OnLongClickListener() {
			public boolean onLongClick(View arg0) 
			{
				EditText e = (EditText)findViewById(R.id.contactNumberEditText);
				String number = e.getText().toString();
				if (number.length() == 10)
				{
					startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+(e.getText()).toString())));
				}
				
				return false;
			}
        });

        
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

        FormMainActivity.tab2 = this;
        FormMainActivity.populate(this);
        
    }
    
    public void refresh()
    {
    	FormMainActivity.populate(this);
    }
}