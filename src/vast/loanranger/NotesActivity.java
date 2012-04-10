package vast.loanranger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.*;
import android.os.Bundle;

public class NotesActivity extends Activity 
{
	private Button backButton, saveButton;
	private AlertDialog.Builder confirmDialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        
        backButton = (Button) findViewById(R.id.notesCancelButton);
        backButton.setOnClickListener(backButtonListener);
        saveButton = (Button) findViewById(R.id.notesSaveButton);
        saveButton.setOnClickListener(saveButtonListener);
        
       confirmDialog = new AlertDialog.Builder(this);
       confirmDialog.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener);
    }
    
    public OnClickListener backButtonListener = new OnClickListener() 
    {
       public void onClick(View v)
       {
    	   confirmDialog.show();
       }
    };
    
    public OnClickListener saveButtonListener = new OnClickListener() 
    {
       public void onClick(View v)
       {
    	   
       }
    };
    
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() 
    {
        public void onClick(DialogInterface dialog, int which) 
        {
            switch (which){
            case DialogInterface.BUTTON_POSITIVE:
            	Intent i = new Intent(NotesActivity.this, OverviewActivity.class);
            	startActivity(i);
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                break;
            }
        }
    };
}