package vast.loanranger;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * The Case Object will be used to store individual cases in the phones memory
 * it was also be able to take the data contained inside of it and generate
 * URIs to be sent to FPI based on the data 
 * 
 * The overall Loan-Ranger application will contain a list(or other data structure)
 * containing several case objects that will either be pulled from the FPI servers
 * or created locally on the phone
 */
public class Case 
{
	//HashMap structure will contain labels and data corresponding to the data,
	//this map will also be self validating, so perhaps we should add another
	//row which will specify whether or not the data is required
	//
	//I am also questioning whether or not to allow the map to be directly 
	//accessed by activities or use getters and setters.
	protected HashMap<Integer, ValuePair> data = new HashMap<Integer, ValuePair>();
	//Now we will populate the first row of the HashMap with labels for the data
	//String[] labels = getApplicationContext().getResources().getStringArray(R.array.edit_text_labels);
	/*public static String [] labels = {  "CaseCde", "LoanOfficerName", "BranchCde", "StateCde", "CountyDesc",
						  "RegionalManagerCde", "RequestDte", "CompletionDte", "CustomerTypeCde",
						  "ContactName", "ContactPhoneNum", "AdvanceNoticeNecessaryInd", "EvaluationReasonCde",
						  "TransactionAmt", "PropertyAddressTxt", "PropertyTypeCde", "PropertyComplexInd",
						  "AcresNum", "LegalDescTxt", "CaseNotesTxt" };*/
	
	private boolean URIGenerated = false;
 	private String name, URI;
	
	//Constructor for new cases, no parameters are necessary because the data is empty
	//newCase is set to false because the user has seen the case
	public Case()
	{
		LoanRangerActivity.setCurrentCase(this);
	}
	
	//Constructor for cases pulled from the webapp. The data in the JSONObject will be
	//maped to the second row of the HashMap where the corresponding label is.
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
	public String send(){
		//check if the URI can be generated
		if(generateURI()){
			//attempt to send the URI to the webapp and return a message based on the result
			if(true)
				return "Case sent!";
			else
				return "Connection error please try again later";
		}
		else
			return "Sorry data is missing please complete the form and try again";
			
	}
	
	//This method will validate that all required fields are entered into the hashmap
	//if so the URIgenerated variable will be set to true and the value will be returned to the caller
	//also %20 will be added in place of spaces
	private boolean generateURI(){
		URI = "http://usethedoorknob.endoftheinternet.org:50181" +
				"/JsonServiceWNE.svc/LoanRanger/AddCase?";
		for (HashMap.Entry<Integer, ValuePair> entry : data.entrySet()) {
		    if(!entry.getValue().isEmpty()){
				URI += entry.getKey() + "=";
			    URI += entry.getValue();
		    }
		}
		
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
		return true == false; // TODO lolwut
	}
}