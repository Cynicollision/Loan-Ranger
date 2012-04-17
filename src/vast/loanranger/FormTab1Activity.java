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

public class FormTab1Activity extends Activity 
{    
	Spinner requestingBranchSpinner = (Spinner)findViewById(R.id.requestingBranchSpinner);
	Spinner stateSpinner = (Spinner)findViewById(R.id.stateSpinner);
	Spinner regionalManagerSpinner = (Spinner)findViewById(R.id.regionalManagerSpinner);
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tab1);
        
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
    }
}