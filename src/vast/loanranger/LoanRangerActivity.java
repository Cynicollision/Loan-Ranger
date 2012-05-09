package vast.loanranger;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.widget.*;

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

public class LoanRangerActivity extends Activity implements TextWatcher
{
	private static Case currentCase;
	private final String URI_GETCASES = "http://usethedoorknob.endoftheinternet.org:50181/JsonServiceWNE.svc/LoanRanger/GetCases";
	private final String URL_GETINDIVIDUALCASE = "http://usethedoorknob.endoftheinternet.org:50181/JsonServiceWNE.svc/LoanRanger/GetIndividualCase?CaseCde=";
	private Button newCaseButton;
	private Case[] cases;
	private EditText filterEditText;
	private TableLayout contactsTableLayout;
	private static String filter;
	
	/**
	 * onCreate
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        filter = "";
        contactsTableLayout = (TableLayout) findViewById(R.id.contactsTableLayout);
        filterEditText = (EditText) findViewById(R.id.filterEditText);
        filterEditText.addTextChangedListener(this);
        newCaseButton = (Button) findViewById(R.id.newCaseButton);
        newCaseButton.setOnClickListener(new OnClickListener() {
           public void onClick(View v) 
           {
        	   Intent i = new Intent(LoanRangerActivity.this, FormMainActivity.class);
        	   try
        	   { 
        		   setCurrentCase(new Case());
        		   startActivity(i);
        	   }
        	   catch (Exception e) { }
           }});
        
        // Load cases and populate the ScrollView
        loadCases();
        populateCaseList();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
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
    	clearCaseList();
    	ContactEntry current;
    	PriorityQueue<ContactEntry> contactList = new PriorityQueue<ContactEntry>();
    	TextView t;
    	
    	for (int i = 0; i < cases.length; i++)
    	{
    		current = new ContactEntry(cases[i]);
    		if (!filter.trim().equals(""))
    		{
    			if ((current.firstName.startsWith(filter) || current.lastName.startsWith(filter)))
        			contactList.add(current);
    		}
    		else
    			contactList.add(current);
    	}

    	Object[] contacts = contactList.toArray();
    	ContactEntry contact;
    	Arrays.sort(contacts);
    	for (int i = 0; i < contacts.length; i++)
    	{
    		contact = (ContactEntry)contacts[i];
    		
    		// TextViews
    		t = new TextView(this);
    		t.setOnClickListener(new caseClickListener(contact.getCase()));
    		t.setText(contact.fullName);
    		t.setTextSize(18);
    		t.setGravity(Gravity.CENTER_VERTICAL);
    		t.setTextColor(Color.BLACK);
    		t.setHeight(60);
    		contactsTableLayout.addView(t);
    	}
    }
    
    /**
     * Clears all the elements of the contacts list
     */
    public void clearCaseList()
    {
    	contactsTableLayout.removeAllViews();
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
    	private String fullName, firstName, lastName;
    	
    	/**
    	 * Constructor
    	 * @param c Case represented by this ContactEntry
    	 */
    	public ContactEntry(Case c)
    	{
    		this.fullName = c.getName();
    		this.c = c;
    		this.lastName = fullName;
    		
    		// Parse last name
    		if (lastName.contains(","))
    		{
    			firstName = fullName.substring(fullName.indexOf(","));
    			lastName = fullName.substring(0, fullName.indexOf(","));
    			firstName = firstName.trim();
    			lastName = lastName.trim();
    		}
    		else if (lastName.contains(" "))
    		{
    			firstName = fullName.substring(0, fullName.indexOf(" "));
    			lastName = fullName.substring(fullName.indexOf(" "));
    			firstName = firstName.trim();
    			lastName = lastName.trim();
    		}
    		
    		// Format full name
    		fullName = lastName + ", " + firstName;
    		lastName = lastName.toLowerCase();
    		firstName = firstName.toLowerCase();
    	}
    	
    	/**
    	 * compareTo
    	 * @param other Other ContactEntry object to compare to
    	 * @return Results of String.compareTo()
    	 */
		public int compareTo(ContactEntry other) 
		{
			return lastName.compareTo(other.lastName);
		}
		
		/**
		 * getCase
		 * @return Case object represented by this ContactEntry
		 */
    	public Case getCase()
    	{
    		return c;
    	}
    }

    /**
     * afterTextChanged
     * Called when the user updates the contents of the search filter field.
     */
	public void afterTextChanged(Editable e) 
	{
		filter = filterEditText.getText().toString().toLowerCase();
		populateCaseList();
	}

	// Unimplemented TextWatcher methods...
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
}