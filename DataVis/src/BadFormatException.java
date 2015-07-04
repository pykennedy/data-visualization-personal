// Written by Peter Kennedy

public class BadFormatException extends Exception
{
	String message;
	
	public BadFormatException () {}

	public String getError()
	{
		return message;
	}
    public BadFormatException (String message) 
    {
        this.message=message;
    }
}