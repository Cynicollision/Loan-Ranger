package vast.loanranger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ValuePair 
{
	private String label;
	private String value;
	
	public ValuePair(String label, String value)
	{
		this.label = label;
		this.value = value;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public boolean isEmpty()
	{
		return (value.equals(""));
	}
	
	public String toURISegment(){
		String segment = "";
		try {
			segment = getLabel() + "=" + URLEncoder.encode(getValue(), "ISO-8859-1");
			segment = segment.replace("+", "%20");
		} catch (UnsupportedEncodingException e) { }
		
		return segment;
	}
}
