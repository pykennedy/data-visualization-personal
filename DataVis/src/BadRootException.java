// Written by Peter Kennedy

public class BadRootException extends Exception 
{
	String message;
	
	public BadRootException () {}

	public String getError()
	{
		return message;
	}
    public BadRootException (String message) 
    {
        this.message=message;
    }
}