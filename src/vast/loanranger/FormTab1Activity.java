package vast.loanranger;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class FormTab1Activity extends Activity 
{   
	Spinner requestingBranchSpinner,
			stateSpinner,
			regionalManagerSpinner;

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
        
        HashMap data = LoanRangerActivity.currentCase.data;
        
        ((EditText)findViewById(R.id.officerNameEditText)).setText((CharSequence)data.get("LoanOfficerName"));
        ((EditText)findViewById(R.id.countyEditText)).setText((CharSequence)data.get("CountyDesc"));
        
        /*
        View v = new View(this);
        EditText w;
        for (String s : Case.labels)
        {
        	//w = (EditText)findViewById(R.id.officerNameEditText);
        	//w.setText(w.getTag().toString());
        	if (v !=null)//(!s.substring(s.length()-3).equals("Cde"))
        	{
        		w = (EditText)v.findViewWithTag(s);
        		w.setText("lol");
        	}
        }
        */
    }
}