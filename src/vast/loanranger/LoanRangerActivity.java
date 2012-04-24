package vast.loanranger;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import vast.loanranger.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.*;


public class LoanRangerActivity extends Activity 
{
	private final String LOAD_CASES_URL = "http://usethedoorknob.endoftheinternet.org:50181/JsonServiceWNE.svc/LoanRanger/GetCases";
	private Button newCaseButton;
	private TableLayout contactsTableLayout;
	private Case[] cases;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        contactsTableLayout = (TableLayout) findViewById(R.id.contactsTableLayout);
        newCaseButton = (Button) findViewById(R.id.newCaseButton);
        newCaseButton.setOnClickListener(newCaseButtonListener);
        
        // Load cases and populate the ScrollView
        loadCases();
        populateCaseList();
    }
    
    /**
     * loadCases
     * Returns an array of Case objects
     */
    public void loadCases()
    {
    	JSONArray casesJ;
    	HttpClient httpclient = new DefaultHttpClient();   
        HttpGet request = new HttpGet(LOAD_CASES_URL);
        ResponseHandler<String> handler = new BasicResponseHandler(); 
        
        try
        {
        	String response = httpclient.execute(request, handler);
        	casesJ = new JSONArray(response);
        	cases = new Case[casesJ.length()];
        	
        	for (int i = 0; i < cases.length; i++)
        		cases[i] = new Case(casesJ.getJSONObject(i));
        }
        catch (JSONException e)
        { }
        catch (ClientProtocolException e) 
        { /*HTTP error*/  }
        catch (IOException e)
        { }
    }
    
    /**
     * populateCaseList
     * Fills the ScrollView with entries for each case
     */
    public void populateCaseList()
    {
    	TextView t;
    	for (int i = 0; i < cases.length; i++)
    	{
    		t = new TextView(this);
    		//t.setText(cases[i].soLive());
    		contactsTableLayout.addView(t);
    	}
    }
    
    /**
     * Click listener for the "new case" button
     */
    public OnClickListener newCaseButtonListener = new OnClickListener() 
    {
    	/**
    	 * onClick
    	 * @param v View
    	 */
       public void onClick(View v)
       {
    	   Intent i = new Intent(LoanRangerActivity.this, FormMainActivity.class);
    	   try
    	   {
    		   startActivity(i);
    	   }
    	   catch (Exception e) { }
       }
    };
}