package vast.loanranger;

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
}
