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

public class OverviewActivity extends Activity {

	private Button backButton, notesButton, editButton, picturesButton, mapButton, sendButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        
        backButton = (Button) findViewById(R.id.overviewBackButton);
        backButton.setOnClickListener(backButtonListener);
        notesButton = (Button) findViewById(R.id.overviewNotesButton);
        notesButton.setOnClickListener(notesButtonListener);
        editButton = (Button) findViewById(R.id.overviewEditButton);
        picturesButton = (Button) findViewById(R.id.overviewPicturesButton);
        mapButton = (Button) findViewById(R.id.overviewMapsButton);
        sendButton = (Button) findViewById(R.id.overviewSendButton);
    }
    
    /**
     * Click listener for "back" button
     */
    public OnClickListener backButtonListener = new OnClickListener() 
    {
       public void onClick(View v)
       {
    	   Intent i = new Intent(OverviewActivity.this, LoanRangerActivity.class);
    	   startActivity(i);
       }
    };
    
    /**
     * Click listener for the "notes" button
     */
    public OnClickListener notesButtonListener = new OnClickListener() 
    {
       public void onClick(View v)
       {
    	   Intent i = new Intent(OverviewActivity.this, NotesActivity.class);
    	   startActivity(i);
       }
    };
}