package vast.loanranger;

import java.util.HashMap;

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
public class Case{
	//HashMap structure will contain labels and data corresponding to the data,
	//this map will also be self validating, so perhaps we should add another
	//row which will specify whether or not the data is required
	//
	//I am also questioning whether or not to allow the map to be directly 
	//accessed by activities or use getters and setters.
	protected HashMap<String, String> data = new HashMap<String, String>();
	//Now we will populate the first row of the HashMap with labels for the data
	
	private boolean newCase = true;
	private boolean URIGenerated = false;
	private String URI;
	
	//Constructor for new cases, no parameters are necessary because the data is empty
	//newCase is set to false because the user has seen the case
	public Case(){
		newCase = false;
	}
	
	//Constructor for cases pulled from the webapp. The data in the JSONObject will be
	//maped to the second row of the HashMap where the corresponding label is.
	public Case(JSONObject source){
		//populate second row of hashmap
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
		//URI = whatever the string resulting from the validation is
		URIGenerated = true;//or false depending on the results of the validation
		
		return URIGenerated;
	}
}