package vast.loanranger;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class Case 
{
	protected HashMap<Integer, ValuePair> data = new HashMap<Integer, ValuePair>();
	private boolean URIGenerated = false;
 	private String name, URI;
	
	//Constructor for new cases, no parameters are necessary because the data is empty
	//newCase is set to false because the user has seen the case
	public Case()
	{
		// Tab 1 elements
		data.put(R.id.officerNameEditText, 			new ValuePair("LoanOfficerName", ""));
		data.put(R.id.requestingBranchSpinner, 		new ValuePair("BranchCde", ""));
		data.put(R.id.stateSpinner, 				new ValuePair("StateCde", ""));
		data.put(R.id.countyEditText, 				new ValuePair("CountyDesc", ""));
		data.put(R.id.regionalManagerSpinner, 		new ValuePair("RegionalManagerCde", ""));
		
		// Tab 2 elements
		data.put(R.id.requestDateEditText, 			new ValuePair("RequestDte", ""));
		data.put(R.id.completionDateEditText, 		new ValuePair("CompletionDte", ""));
		data.put(R.id.customerTypeSpinner, 			new ValuePair("CustomerTypeCde", ""));
		data.put(R.id.contactNameEditText, 			new ValuePair("ContactName", ""));
		data.put(R.id.contactNumberEditText, 		new ValuePair("ContactPhoneNum", ""));
		data.put(R.id.advanceNoticeSpinner, 		new ValuePair("AdvanceNoticeNecessaryInd", ""));
			
		// Tab 3 elements
		data.put(R.id.requestReasonSpinner, 		new ValuePair("EvaluationReasonCde", ""));
		data.put(R.id.transactionSizeEditText, 		new ValuePair("TransactionAmt", ""));
		data.put(R.id.propertyAddressEditText, 		new ValuePair("PropertyAddressTxt", ""));
		data.put(R.id.propertyTypeSpinner, 			new ValuePair("PropertyTypeCde", ""));
		data.put(R.id.propertyComplexitySpinner, 	new ValuePair("PropertyComplexInd", ""));
		data.put(R.id.propertyAcresEditText, 		new ValuePair("AcresNum", ""));
		data.put(R.id.legalDescriptionEditText, 	new ValuePair("LegalDescTxt", ""));
		
		// Tab 4 element
		data.put(R.id.notesEditText, 				new ValuePair("CaseNotesTxt", ""));
	}
	

	public Case(JSONObject source)
	{
		try 
		{
			// Get the contact name
			name = source.getString("ContactName");
			
			// Tab 1 elements
			data.put(R.id.officerNameEditText, 			new ValuePair("LoanOfficerName", source.getString("LoanOfficerName")));
			data.put(R.id.requestingBranchSpinner, 		new ValuePair("BranchCde", source.getString("BranchCde")));
			data.put(R.id.stateSpinner, 				new ValuePair("StateCde", source.getString("StateCde")));
			data.put(R.id.countyEditText, 				new ValuePair("CountyDesc", source.getString("CountyDesc")));
			data.put(R.id.regionalManagerSpinner, 		new ValuePair("RegionalManagerCde", source.getString("RegionalManagerCde")));
			
			// Tab 2 elements
			data.put(R.id.requestDateEditText, 			new ValuePair("RequestDte", source.getString("RequestDte")));
			data.put(R.id.completionDateEditText, 		new ValuePair("CompletionDte", source.getString("CompletionDte")));
			data.put(R.id.customerTypeSpinner, 			new ValuePair("CustomerTypeCde", source.getString("CustomerTypeCde")));
			data.put(R.id.contactNameEditText, 			new ValuePair("ContactName", source.getString("ContactName")));
			data.put(R.id.contactNumberEditText, 		new ValuePair("ContactPhoneNum", source.getString("ContactPhoneNum")));
			data.put(R.id.advanceNoticeSpinner, 		new ValuePair("AdvanceNoticeNecessaryInd", source.getString("AdvanceNoticeNecessaryInd")));
				
			// Tab 3 elements
			data.put(R.id.requestReasonSpinner, 		new ValuePair("EvaluationReasonCde", source.getString("EvaluationReasonCde")));
			data.put(R.id.transactionSizeEditText, 		new ValuePair("TransactionAmt", source.getString("TransactionAmt")));
			data.put(R.id.propertyAddressEditText, 		new ValuePair("PropertyAddressTxt", source.getString("PropertyAddressTxt")));
			data.put(R.id.propertyTypeSpinner, 			new ValuePair("PropertyTypeCde", source.getString("PropertyTypeCde")));
			data.put(R.id.propertyComplexitySpinner, 	new ValuePair("PropertyComplexInd", source.getString("PropertyComplexInd")));
			data.put(R.id.propertyAcresEditText, 		new ValuePair("AcresNum", source.getString("AcresNum")));
			data.put(R.id.legalDescriptionEditText, 	new ValuePair("LegalDescTxt", source.getString("LegalDescTxt")));
			
			// Tab 4 element
			data.put(R.id.notesEditText, 				new ValuePair("CaseNotesTxt", source.getString("CaseNotesTxt")));
		}
		catch (JSONException e) 
		{ }
	}
	//send will be called by an activity and will return a message containing the status of the send
	public String send()
	{
		JSONObject r;
		boolean success = false;
		
		//check if the URI can be generated
		if (isValid())
		{
			generateURI();
			HttpClient httpclient = new DefaultHttpClient();   
	        HttpGet request = new HttpGet(URI);
	        ResponseHandler<String> handler = new BasicResponseHandler(); 
	        
	        try
	        {
	        	String response = httpclient.execute(request, handler);
	        	r = new JSONObject(response);
	        	success = r.getBoolean("success");
	        	
	        	if (!success)
	        		return "A communication error has occurred.";

	        }
	        catch (JSONException e)
	        {  }
	        catch (ClientProtocolException e) 
	        {  }
	        catch (IOException e)
	        { return ""; }
		}
		
		if (!success)
			return "Sorry, data is missing please complete the form and try again.";

    	else
    		return "Case successfully sent!";
	}
	
	//This method will validate that all required fields are entered into the hashmap
	//if so the URIgenerated variable will be set to true and the value will be returned to the caller
	//also %20 will be added in place of spaces
	private boolean generateURI()
	{
		
		ValuePair current;
		URI = "http://usethedoorknob.endoftheinternet.org:50181" +
				"/JsonServiceWNE.svc/LoanRanger/AddCase?";
		for (HashMap.Entry<Integer, ValuePair> entry : data.entrySet()) {
		    //if(!entry.getValue().isEmpty()){
				current = entry.getValue();
				URI += current.toURISegment();
				URI += "&";
		    //}
		}
		URI = URI.substring(0, URI.length()-1);
		Log.i("Success", URI);
		URIGenerated = true;//or false depending on the results of the validation
		
		return URIGenerated;
	}
	/**
	 * getName
	 * Returns the contact name for this case, to be used to populate 
	 * the list of contacts on the main screen.
	 * @return The "contact name" of this case
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * isValid
	 * @return True if the data in this case is valid, i.e.,
	 * is ready to be submitted to the web service.
	 */
	public boolean isValid()
	{
		boolean flag = true;
		ValuePair current;
		
		for (HashMap.Entry<Integer, ValuePair> entry : data.entrySet())
		{
			current = entry.getValue();
			if (current.getLabel().equals("LoanOfficerName") ||
				current.getLabel().equals("CountyDesc") || 
				current.getLabel().equals("ContactName") || 
				current.getLabel().equals("TransactionAmt") || 
				current.getLabel().equals("PropertyAddressTxt") || 
				current.getLabel().equals("LegalDescTxt"))
			{
				flag = !current.getValue().equals("");
				if (!flag)
					break;
			}
			
			else if (current.getLabel().endsWith("Ind") || current.getLabel().endsWith("Cde"))
			{
				flag = !current.getValue().equals("0");
				if (!flag)
					break;
			}
		}
		
		return flag;
	}
}