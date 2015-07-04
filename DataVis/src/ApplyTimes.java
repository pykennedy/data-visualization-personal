// Written by Peter Kennedy

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ApplyTimes 
{
	// lowest year
	static int year1=0, month1=0, day1=0, hour1=0, minute1=0;
	// not used but keeping just in case, and due to numbering scheme already in place
	static int year2=0, month2=0, day2=0, hour2=0, minute2=0;
	// not used but keeping just in case, and due to numbering scheme already in place
	static int year3=0, month3=0, day3=0, hour3=0, minute3=0;
	// highest year
	static int year4=0, month4=0, day4=0, hour4=0, minute4=0;
	
	// EXCLUDE = TRUE; flags for DAY of the month (1-31)
	static boolean[] dom = new boolean[31];

	// EXCLUDE = TRUE; flags for DAY of the week (sunday-saturday)
	static boolean[] dow = new boolean[7];
	
	// EXCLUDE = TRUE; flags for WEEK of the year (1-52)
	static boolean[] woy = new boolean[52];
	
	// EXCLUDE = TRUE; flags for WEEK of the month (1-5)
	static boolean[] wom = new boolean[6];
	
	// EXCLUDE = TRUE; flags for MONTH of YEAR (1-12)
	static boolean[] moy = new boolean[12];
	
	// sets all bools to true
	public void trueBools()
	{
		year1=0; month1=0; day1=0; hour1=0; minute1=0;
		year2=0; month2=0; day2=0; hour2=0; minute2=0;
		year3=0; month3=0; day3=0; hour3=0; minute3=0;
		year4=0; month4=0; day4=0; hour4=0; minute4=0;
		
		for(int i=0;i<dom.length;i++)
			dom[i]=true;
		for(int i=0;i<dow.length;i++)
			dow[i]=true;
		for(int i=0;i<woy.length;i++)
			woy[i]=true;
		for(int i=0;i<wom.length;i++)
			wom[i]=true;
		for(int i=0;i<moy.length;i++)
			moy[i]=true;

	}
	public void falseDOM()
	{
		for(int i=0;i<dom.length;i++)
			dom[i]=false;
	}
	public void falseDOW()
	{
		for(int i=0;i<dow.length;i++)
			dow[i]=false;
	}
	public void falseWOY()
	{
		for(int i=0;i<woy.length;i++)
			woy[i]=false;
	}
	public void falseWOM()
	{
		for(int i=0;i<wom.length;i++)
			wom[i]=false;
	}
	public void falseMOY()
	{
		for(int i=0;i<moy.length;i++)
			moy[i]=false;
	}
	// depending on which bools were set to true or false, sets the PagePosts include to true or false
	public ArrayList<PagePost> setIncludes(ArrayList<PagePost> page, int i, boolean inverseFlag)
	{
		if(page.get(i).getMonth()>0)
		if(dom[page.get(i).getDayOfMonth()-1]==true&&dow[page.get(i).getDayOfWeek()-1]==true
				&&woy[page.get(i).getWeekOfYear()-1]==true&&wom[page.get(i).getWeekOfMonth()-1]==true&&moy[page.get(i).getMonth()-1]==true)
		{
			if(!inverseFlag)
				page.get(i).setInclude(true);
			else
				page.get(i).setInclude(false);
		}
		return page;
	}
	// if rootword is all/every/only
	public ArrayList<PagePost> AllEveryOnly(ArrayList<PagePost> page, boolean inverseFlag)
	{
		int numPosts=page.size()-1;
		for(int i=0;i<numPosts;i++)
		{
				page = setIncludes(page,i,inverseFlag);
		}
		
		return page;
	}
	// if rootword is until and parameters is date
	public ArrayList<PagePost> untilDate(ArrayList<PagePost> page, boolean inverseFlag, String highDate, String lowTime, String highTime, boolean timeRange)
	{
		String delims="[/ \\-\\:]";
		String[] highTokens = highDate.split(delims);
		Calendar highCal = new GregorianCalendar(Integer.parseInt(highTokens[2]),Integer.parseInt(highTokens[0]),Integer.parseInt(highTokens[1]));
		
		year1=highCal.get(Calendar.YEAR);
		month1=highCal.get(Calendar.MONTH);
		day1=highCal.get(Calendar.DAY_OF_MONTH);
		
		int numPosts=page.size()-1;
		for(int i=0;i<numPosts;i++)
		{
			if(page.get(i).getCalendar().before(highCal))
			{
				if(timeRange)
					page=timeRange(page,inverseFlag,lowTime,highTime,i);
				else
					page = setIncludes(page,i,inverseFlag);
			}
		}
		
		return page;
	}
	// if rootword is until and parameters is time and date
	public ArrayList<PagePost> untilTimeDate(ArrayList<PagePost> page, boolean inverseFlag, String highTime, String highDate, String lowTime, String rangeHighTime, boolean timeRange)
	{
		String delims = "[/ \\-\\:]";
		String[] highTimeTokens = highTime.split(delims);
		String[] highDateTokens = highDate.split(delims);
		Calendar highCal = new GregorianCalendar(Integer.parseInt(highDateTokens[2]),Integer.parseInt(highDateTokens[0]),Integer.parseInt(highDateTokens[1])
				,Integer.parseInt(highTimeTokens[0]),Integer.parseInt(highTimeTokens[1]));
		
		year1=highCal.get(Calendar.YEAR);
		month1=highCal.get(Calendar.MONTH);
		day1=highCal.get(Calendar.DAY_OF_MONTH);
		hour1=highCal.get(Calendar.HOUR_OF_DAY);
		minute1=highCal.get(Calendar.MINUTE);
		
		int numPosts=page.size()-1;
		for(int i=0;i<numPosts;i++)
		{
			if(page.get(i).getCalendar().before(highCal))
			{
				if(timeRange)
					page=timeRange(page,inverseFlag,lowTime,rangeHighTime,i);
				else
					page = setIncludes(page,i,inverseFlag);
			}
		}
		
		return page;
	}
	// if rootword is since and parameters is date
	public ArrayList<PagePost> sinceDate(ArrayList<PagePost> page, boolean inverseFlag, String lowDate, String lowTime, String highTime, boolean timeRange)
	{
		String delims="[/ \\-\\:]";
		String[] lowTokens = lowDate.split(delims);
		Calendar lowCal = new GregorianCalendar(Integer.parseInt(lowTokens[2]),Integer.parseInt(lowTokens[0]),Integer.parseInt(lowTokens[1]));
		
		year1=lowCal.get(Calendar.YEAR);
		month1=lowCal.get(Calendar.MONTH);
		day1=lowCal.get(Calendar.DAY_OF_MONTH);
		
		int numPosts=page.size()-1;
		for(int i=0;i<numPosts;i++)
		{
			if(page.get(i).getCalendar().after(lowCal))
			{
				if(timeRange)
					page=timeRange(page,inverseFlag,lowTime,highTime,i);
				else
					page = setIncludes(page,i,inverseFlag);
			}
		}
		
		return page;
	}
	// if rootword is since and parameters is time and date
	public ArrayList<PagePost> sinceTimeDate(ArrayList<PagePost> page, boolean inverseFlag, String lowTime, String lowDate, String rangeLowTime, String highTime, boolean timeRange)
	{
		String delims = "[/ \\-\\:]";
		String[] lowTimeTokens = lowTime.split(delims);
		String[] lowDateTokens = lowDate.split(delims);
		Calendar lowCal = new GregorianCalendar(Integer.parseInt(lowDateTokens[2]),Integer.parseInt(lowDateTokens[0]),Integer.parseInt(lowDateTokens[1])
				,Integer.parseInt(lowTimeTokens[0]),Integer.parseInt(lowTimeTokens[1]));
		
		year1=lowCal.get(Calendar.YEAR);
		month1=lowCal.get(Calendar.MONTH);
		day1=lowCal.get(Calendar.DAY_OF_MONTH);
		hour1=lowCal.get(Calendar.HOUR_OF_DAY);
		minute1=lowCal.get(Calendar.MINUTE);
		
		int numPosts=page.size()-1;
		for(int i=0;i<numPosts;i++)
		{
			if(page.get(i).getCalendar().after(lowCal))
			{
				if(timeRange)
					page=timeRange(page,inverseFlag,rangeLowTime,highTime,i);
				else
					page = setIncludes(page,i,inverseFlag);
			}
		}
		
		return page;
	}
	// if we are also filtering by a time of day range
	public ArrayList<PagePost> timeRange(ArrayList<PagePost> page, boolean inverseFlag, String lowTime, String highTime, int i)
	{
		String delims="[/ \\-\\:]";
		String[]lowTokens=lowTime.split(delims);
		String[]highTokens=highTime.split(delims);

			if(page.get(i).getHourOfDay()>=Integer.parseInt(lowTokens[0])&&page.get(i).getHourOfDay()<=Integer.parseInt(highTokens[0]))
			{
				// if same hour, but minutes to low
				if(page.get(i).getHourOfDay()==Integer.parseInt(lowTokens[0])&&page.get(i).getMinute()<Integer.parseInt(lowTokens[1]))
					return page;
				// if same hour, but minutes to high
				else if(page.get(i).getHourOfDay()==Integer.parseInt(highTokens[0])&&page.get(i).getMinute()>=Integer.parseInt(highTokens[1]))
					return page;
				else
				{
					page = setIncludes(page,i,inverseFlag);
				}
			}
		return page;
	}
	// if rootword is between/from and parameters is time and time
	public ArrayList<PagePost> betweenTimeXandY(ArrayList<PagePost> page, boolean inverseFlag, String lowTime, String highTime)
	{		
		int numPosts=page.size()-1;
		for(int i=0;i<numPosts;i++)
		{
			page=timeRange(page,inverseFlag,lowTime,highTime,i);
		}
		
		return page;
	}
	// if rootword is between/from and parameters is time date and time date
	public ArrayList<PagePost> betweenFullTimeDateXandY(ArrayList<PagePost> page, boolean inverseFlag, String lowTime, String lowDate, String highTime, String highDate, boolean timeRange)
	{
		String delims = "[/ \\-\\:]";
		String[] lowTimeTokens = lowTime.split(delims);
		String[] highTimeTokens = highTime.split(delims);
		String[] lowDateTokens = lowDate.split(delims);
		String[] highDateTokens = highDate.split(delims); // [year] [month] [day] / [hour] [minute]
		Calendar lowCal = new GregorianCalendar(Integer.parseInt(lowDateTokens[2]),Integer.parseInt(lowDateTokens[0]),Integer.parseInt(lowDateTokens[1])
												,Integer.parseInt(lowTimeTokens[0]),Integer.parseInt(lowTimeTokens[1]));
		Calendar highCal = new GregorianCalendar(Integer.parseInt(highDateTokens[2]),Integer.parseInt(highDateTokens[0]),Integer.parseInt(highDateTokens[1])
												,Integer.parseInt(highTimeTokens[0]),Integer.parseInt(highTimeTokens[1]));
		
		year1=lowCal.get(Calendar.YEAR);
		month1=lowCal.get(Calendar.MONTH);
		day1=lowCal.get(Calendar.DAY_OF_MONTH);
		hour1=lowCal.get(Calendar.HOUR_OF_DAY);
		minute1=lowCal.get(Calendar.MINUTE);
		
		year4=highCal.get(Calendar.YEAR);
		month4=highCal.get(Calendar.MONTH);
		day4=highCal.get(Calendar.DAY_OF_MONTH);
		hour4=highCal.get(Calendar.HOUR_OF_DAY);
		minute4=highCal.get(Calendar.MINUTE);
		
		int numPosts=page.size()-1;
		
		for(int i=0;i<numPosts;i++)
		{
			if(page.get(i).getCalendar().after(lowCal)&&page.get(i).getCalendar().before(highCal))
			{
				if(timeRange)
					page=timeRange(page,inverseFlag,lowTime,highTime,i);
				else
					page = setIncludes(page,i,inverseFlag);
			}
		}
		
		return page;
	}
	// if rootword is between/from and parameters is date and date
	public ArrayList<PagePost> betweenFullDateXandY(ArrayList<PagePost> page, boolean inverseFlag, String lowDate, String highDate, String lowTime, String highTime, boolean timeRange)
	{
		String delims = "[/ \\-\\:]";
		String[] lowTokens = lowDate.split(delims);
		String[] highTokens = highDate.split(delims);          //   [year]                 //   [month]                      //  [day]
		Calendar lowCal = new GregorianCalendar(Integer.parseInt(lowTokens[2]),Integer.parseInt(lowTokens[0]),Integer.parseInt(lowTokens[1]));
		Calendar highCal = new GregorianCalendar(Integer.parseInt(highTokens[2]),Integer.parseInt(highTokens[0]),Integer.parseInt(highTokens[1]));
		
		year1=lowCal.get(Calendar.YEAR);
		month1=lowCal.get(Calendar.MONTH);
		day1=lowCal.get(Calendar.DAY_OF_MONTH);
		
		year4=highCal.get(Calendar.YEAR);
		month4=highCal.get(Calendar.MONTH);
		day4=highCal.get(Calendar.DAY_OF_MONTH);
		
		int numPosts=page.size()-1;
		
		for(int i=0;i<numPosts;i++)
		{
			if(page.get(i).getCalendar().after(lowCal)&&page.get(i).getCalendar().before(highCal))
			{
				if(timeRange)
					page=timeRange(page,inverseFlag,lowTime,highTime,i);
				else
					page = setIncludes(page,i,inverseFlag);
			}
		}
		
		return page;
	}
}