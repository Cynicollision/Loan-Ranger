package vast.loanranger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;

import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import vast.loanranger.R;

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
    	PriorityQueue<ContactEntry> contactList = new PriorityQueue<ContactEntry>(cases.length);
    	TextView t;
    	View sep;
    	
    	for (int i = 0; i < cases.length; i++)
    	{
    		contactList.offer(new ContactEntry(cases[i]));
    	}

    	Object[] contacts = contactList.toArray();
    	ContactEntry contact;
    	Arrays.sort(contacts);
    	for (int i = 0; i < contacts.length; i++)
    	{
    		contact = (ContactEntry)contacts[i];
    		
    		if (i != 0)
    		{
    			// Separators
        		sep = new View(this);
        		sep.setBackgroundColor(Color.WHITE);
        		sep.setMinimumHeight(2);
        		contactsTableLayout.addView(sep);
    		}
    		
    		// TextViews
    		t = new TextView(this);
    		t.setOnClickListener(new caseClickListener(contact.getCase()));
    		t.setText(contact.getName());
    		t.setTextSize(18);
    		t.setTextColor(Color.WHITE);
    		t.setHeight(50);
    		contactsTableLayout.addView(t);
    		
    	}
    }

    /**
     * getCurrentCase
     * @return Reference to the currently "open" case object
     */
	public static Case getCurrentCase()
    {
    	return currentCase;
    }
    
	/**
	 * setCurrentCase
	 * @param c Case to "open"
	 * @return False if setting to a null case
	 */
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
    	
    	/**
    	 * Constructor
    	 * @param c Case to link to when this case item is clicked in the list.
    	 */
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
    }
    
    /**
     * ContactEntry
     * Used to heapify the contact names list by last name
     */
    private class ContactEntry implements Comparable<ContactEntry>
    {
    	private Case c;
    	private String fullName, fname, lname;
    	
    	/**
    	 * Constructor
    	 * @param c Case represented by this ContactEntry
    	 */
    	public ContactEntry(Case c)
    	{
    		this.fullName = c.getName();
    		this.c = c;
    		this.lname = fullName;
    		
    		// Parse last name
    		if (lname.contains(","))
    		{
    			fname = fullName.substring(fullName.indexOf(","));
    			lname = fullName.substring(0, fullName.indexOf(","));
    			fname = fname.trim();
    			lname = lname.trim();
    		}
    		else if (lname.contains(" "))
    		{
    			fname = fullName.substring(0, fullName.indexOf(" "));
    			lname = fullName.substring(fullName.indexOf(" "));
    			fname = fname.trim();
    			lname = lname.trim();
    		}
    		
    		// Format full name
    		fullName = lname + " , " + fname;
    	}
    	
    	/**
    	 * compareTo
    	 * @param other Other ContactEntry object to compare to
    	 * @return Results of String.compareTo()
    	 */
		public int compareTo(ContactEntry other) 
		{
			return lname.compareTo(other.getLastName());
		}
		
		/**
		 * getCase
		 * @return Case object represented by this ContactEntry
		 */
    	public Case getCase()
    	{
    		return c;
    	}
    	
    	/**
    	 * getName
    	 * @return Contact's full name
    	 */
    	public String getName()
    	{
    		return fullName;
    	}
    	
    	/**
    	 * getLastName
    	 * @return Contact's last name
    	 */
    	public String getLastName()
    	{
    		return lname;
    	}
    }
}