package vast.loanranger;

import vast.loanranger.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.*;


public class LoanRangerActivity extends Activity 
{
	private SharedPreferences savedContacts;
	private EditText searchEditText;
	private Button newCaseButton;
	private TableLayout contactsTableLayout;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        savedContacts = getSharedPreferences("contacts", MODE_PRIVATE);
        contactsTableLayout = (TableLayout) findViewById(R.id.contactsTableLayout);
        searchEditText = (EditText) findViewById(R.id.searchEditText);

    }
}