// Written by Peter Kennedy

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class RawToArr 
{
	public static ArrayList<PagePost> txtToArr(ArrayList<PagePost> page, String path)
	{	
		try
		{
			BufferedReader br = null;
			br=new BufferedReader(new FileReader(path));
			while(true)
			{
				String line = null;
				while(true)
				{
					// variables to load into PagePost object
					boolean include=true;
					String type=null;
					int timestamp=0;
					long id=0;
					String postURL=null;
					String sourceURL=null;
					int notes=0;
					ArrayList tags=new ArrayList();

					Calendar calendar = null;
					
					int year=0;
					int month=0;
					int dayOfMonth=0;
					int dayOfWeek=0;
					int weekOfYear=0;
					int weekOfMonth=0;
				 
					int hour=0;
					int hourOfDay=0;
					int minute=0;
					int second=0;

					// set type
					if((line = br.readLine()) != null) // line 1
					{
						type=line;
					}
					
					// set timestamp
					if((line = br.readLine()) != null) // line 2
					{
						timestamp=Integer.parseInt(line);
					}
					
					// set date and time
					if((line = br.readLine()) != null) // line 3
					{
						String delims = "[ \\-\\:]";
						
						String[] tokens = line.split(delims);

						// build calendar object from year, month, day, hours, minutes, and seconds of each post
						calendar = new GregorianCalendar(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),
																	Integer.parseInt(tokens[3]),Integer.parseInt(tokens[4]),Integer.parseInt(tokens[5]));
					 
						year       = calendar.get(Calendar.YEAR);
						month      = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
						dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // 23 = the 23rd
						dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK); // 1 = sunday, 7 = saturday
						weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR); // 1 = first week, 52 = last week
						weekOfMonth= calendar.get(Calendar.WEEK_OF_MONTH); // 1 = first week, 5 = last possible week
					 
						hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
						hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
						minute     = calendar.get(Calendar.MINUTE);
						second     = calendar.get(Calendar.SECOND);
					}
					// set id
					if((line = br.readLine()) != null) // line 4
					{
						id=Long.parseLong(line);
					}
					// set postURL
					if((line = br.readLine()) != null) // line 5
					{
						postURL=line;
					}
					// set sourceURL
					if((line = br.readLine()) != null) // line 6
					{
						sourceURL=line;
					}
					// set notes
					if((line = br.readLine()) != null) // line 7
					{
						try{
							notes=Integer.parseInt(line);
						} catch (NumberFormatException e) {
							notes = 0;
						}
					}
					// set tags
					if((line = br.readLine()) != null) // line 8
					{
						String delims = "[,(:) +]";
						String[] tokens;
						line = line.substring(1, line.length() - 1);
						tokens = line.split(delims);
						
						for(int i=0; i<tokens.length;i++)
						{
							tags.add(tokens[i]);
						}
					}
					
					// creates a PagePost object with variables we just set
					PagePost post = new PagePost(include, type, timestamp, year, month, dayOfMonth, dayOfWeek, weekOfYear, 
													weekOfMonth, hour, hourOfDay, minute, second, id, postURL, sourceURL, notes, tags, calendar);
					// adds PagePost object to ArrayList of PagePost objects (each object is 1 tumblr post)
					page.add(post);
					if(line==null) // if end of .txt file
						break;
				}
				if(line==null)// if end of .txt file
					break;
			}
			br.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	
		return page;
	}
}