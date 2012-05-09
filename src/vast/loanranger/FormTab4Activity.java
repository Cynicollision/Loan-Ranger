package vast.loanranger;

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

        FormMainActivity.tab4 = this;
        FormMainActivity.populate(this);
    }
    
    public void refresh()
    {
    	FormMainActivity.populate(this);
    }
}