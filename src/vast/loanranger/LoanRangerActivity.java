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
import org.json.JSONObject;

import vast.loanranger.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;


public class LoanRangerActivity extends Activity 
{
	public static Case currentCase;
	public static Case[] cases;
	private final String URI_GETCASES = "http://usethedoorknob.endoftheinternet.org:50181/JsonServiceWNE.svc/LoanRanger/GetCases";
	private final String URL_GETINDIVIDUALCASE = "http://usethedoorknob.endoftheinternet.org:50181/JsonServiceWNE.svc/LoanRanger/GetIndividualCase?CaseCde=";
	private Button newCaseButton;
	private TableLayout contactsTableLayout;
	
	
	/**
	 * onCreate
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        contactsTableLayout = (TableLayout) findViewById(R.id.contactsTableLayout);
        newCaseButton = (Button) findViewById(R.id.newCaseButton);
        newCaseButton.setOnClickListener(new OnClickListener() {
           public void onClick(View v) 
           {
        	   Intent i = new Intent(LoanRangerActivity.this, FormMainActivity.class);
        	   try
        	   { 
        		   startActivity(i);
        	   }
        	   catch (Exception e) { }
           }});
        
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
        HttpGet request = new HttpGet(URI_GETCASES);
        ResponseHandler<String> handler = new BasicResponseHandler(); 
        
        try
        {
        	String response = httpclient.execute(request, handler);
        	casesJ = new JSONArray(response);
        	cases = new Case[casesJ.length()];
        	
        	for (int i = 0; i < cases.length; i++)
        	{
        		request = new HttpGet(URL_GETINDIVIDUALCASE+(i+1));
        		response = httpclient.execute(request, handler);
        		casesJ = new JSONArray(response);
        		cases[i] = new Case(casesJ.getJSONObject(0));
        	}	
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
    		t.setOnClickListener(new caseClickListener(cases[i]));
    		t.setText(cases[i].data.get("ContactName"));
    		t.setTextSize(18);
    		t.setHeight(50);
    		contactsTableLayout.addView(t);
    	}
    }


	public static Case getCurrentCase()
    {
    	return currentCase;
    }
    
    public static boolean setCurrentCase(Case c)
    {
    	currentCase = c;
    	return (c == null);
    }
    
    /**
     * newCaseButtonListener
     * Click listener for the "new case" button
     */
    private class caseClickListener implements OnClickListener
    {
    	private Case c;
    	public caseClickListener(Case c)
    	{
    		super();
    		this.c = c;
    	}
       public void onClick(View v)
       {
    	   currentCase = c;
    	   Intent i = new Intent(LoanRangerActivity.this, FormMainActivity.class);
    	   try
    	   {
    		   startActivity(i);
    	   }
    	   catch (Exception e) { }
       }
    };
}