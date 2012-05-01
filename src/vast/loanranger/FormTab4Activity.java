package vast.loanranger;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class FormTab4Activity extends Activity 
{   
	EditText caseNotesTxt;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tab4);

        HashMap data = LoanRangerActivity.currentCase.data;
        
        ((EditText)findViewById(R.id.notesEditText)).setText((CharSequence)data.get("CaseNotesTxt"));
    }
}