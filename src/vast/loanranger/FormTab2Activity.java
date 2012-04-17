package vast.loanranger;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.*;
import android.os.Bundle;

public class FormTab2Activity extends Activity {
   
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tab2);
        
        Spinner customerTypeSpinner = (Spinner)findViewById(R.id.customerTypeSpinner);
    	Spinner advanceNoticeSpinner = (Spinner)findViewById(R.id.advanceNoticeSpinner);
    	
    	ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.options_customer_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerTypeSpinner.setAdapter(adapter);
        
        adapter = ArrayAdapter.createFromResource(this, R.array.options_advance_notice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        advanceNoticeSpinner.setAdapter(adapter);
    }
}