// Written by Peter Kennedy

import java.util.ArrayList;

public class Parser 
{
	// converts user input into military time format
	public String fixTime(String time, String apm)
	{
		if(apm==null)
		{
			if(time.contains("am"))
			{
				apm="am";
				time=time.replace("am", "");
			}
			else if(time.contains("pm"))
			{
				apm="pm";
				time=time.replace("pm", "");
			}
		}
		String delims = "[:]";
		String[] timeTokens = time.split(delims);
		if(apm.equals("am")&&timeTokens[0].equals("12"))
		{
			timeTokens[0]="00";
		}
			else if(apm.equals("pm"))
		{
			int hour=Integer.parseInt(timeTokens[0]);
			if(hour<12)
			{
				hour=hour+12;
				timeTokens[0]=Integer.toString(hour);
			}
		}
		time=timeTokens[0]+":"+timeTokens[1];

		return time;
	}
	// walks through user input for time filter and figures out the root word to filter the following input by
	public String getChunkRoot(ArrayList<String> chunk) throws BadFormatException, BadRootException
	{
		String chunkRoot=null;
		
		if(!chunk.isEmpty()&&(chunk.get(0).equals("between")||chunk.get(0).equals("from")))
			chunkRoot="between/from";
		else if(!chunk.isEmpty()&&(chunk.get(0).equals("since")))
			chunkRoot="since";
		else if(!chunk.isEmpty()&&(chunk.get(0).equals("until")))
			chunkRoot="until";
		else if(!chunk.isEmpty()&&(chunk.get(0).equals("all")||chunk.get(0).equals("every")||chunk.get(0).equals("only")))
			chunkRoot="all/every/only";
		else
		{
			if(chunk.size()<2) // if user input is too short to possibly be valid
				return null;
			String s = "";
			for(int i=0;i<chunk.size();i++)
				s = s + chunk.get(i)+" ";
			throw new BadRootException("invalid root word in chunk: \n"+s);
		}
		return chunkRoot;
	}
	// calls a different function in RootWords.java depending on the current root word
	public ArrayList<PagePost> callRoot(ArrayList<PagePost> page, ArrayList<String> chunk, boolean inverseFlag, String chunkRoot) throws BadFormatException, BadRootException
	{
		RootWords roots = new RootWords();
		
		if(chunkRoot!=null)
		{
			switch(chunkRoot)
			{
			case "between/from":
				page=roots.BetweenFrom(page,chunk,inverseFlag);
				break;
			case "since":
				page=roots.Since(page,chunk,inverseFlag);
				break;
			case "until":
				page=roots.Until(page, chunk, inverseFlag);
				break;
			case "all/every/only":
				boolean continuation = false;
				page=roots.AllEveryOnly(page, chunk, inverseFlag, continuation);
				break;
			default:
			{
				if(chunk.size()<2) // if chunk is to short to possibly be valid
					return page;
				String s = "";
				for(int i=0;i<chunk.size();i++)
					s = s + chunk.get(i)+" ";
				throw new BadRootException("invalid root word in chunk: \n"+s);
			}
			}
		}
		else
			return page;
		return page;
	}
	public boolean fbType(ArrayList<PagePost> page, String[] types, int i)
	{
		boolean include=false; // sets default to not include this post
		if(types[0]!=null) // if array isn't empty
		{
			int k=0;
			while(true) // walks through types[] to see if any of them match the posts' tags
			{
				if(types[k]==null) // if end of types array
					return include;
				else if(page.get(i).getType().equalsIgnoreCase(types[k])) // if there is a match
				{
					include=true;
					k++;
				}
				else
					k++;
			}
		}
		else // if types array is empty (means we aren't filtering by types) then include this post in drawing
			return true;
	}

	public ArrayList<PagePost> fbTime(ArrayList<PagePost> page, String timeParams) throws BadFormatException, BadRootException
	{
		// fix any formatting that will throw errors
		timeParams=timeParams.toLowerCase();
		timeParams=timeParams.replace(",","");
		String chunkRoot=null;
		String delims="[, +]";
		String[] tokenParams=timeParams.split(delims);
		ArrayList<String> chunk=new ArrayList<String>(); // create variable to hold array of strings of "chunks" of user input
		chunk.clear();
		
		boolean inverseFlag=false; // flag to indicate whether or not the user is "inverting" the filter (eg: between 1/1/2010 and 1/1/2015 except all even days)
		int a=0,b=0,c=0; // variables to handle potential nested increments
		ApplyTimes apply = new ApplyTimes();
		
		
		while(true) // walk through entire array of PagePosts and send "chunks" to RootWords.java to handle the rest
		{
			inverseFlag=false;
			apply.trueBools(); // set day/month/year/etc flags to true
			chunk.clear(); // empty the ArrayList for chunks
			chunkRoot=null;
			b=0;
			//fill chunk to be analyzed and call functions to fix format issues
			for(int i=a;i<tokenParams.length;i++)
			{
				a=i;
				chunk.add(b,tokenParams[i]);
				
				if(chunk.get(b).equals("am")||chunk.get(b).equals("pm"))
				{
					chunk.set((b-1),(fixTime(chunk.get(b-1),chunk.get(b))));
					chunk.remove(b);
					b--;
				}
				else if(chunk.get(b).contains("am")||chunk.get(b).contains("pm"))
				{
					chunk.set(b,fixTime(chunk.get(b),null));
				}
				b++;
			}
			if(chunk.size()<2) // if chunk is to small to be valid return page
				return page;
			chunkRoot = getChunkRoot(chunk);
			if(chunkRoot==null)
			{
				System.out.println("breakout");
				return page;
			}
			page=callRoot(page,chunk,inverseFlag,chunkRoot); // sets pages value (the includes within) to the result of callRoot()->RootWords.java->ApplyTimes.java
		}
	}
	// sets include to true if posts notes is greater or equal to the # of notes specified in the filter
	public boolean fbNotes(ArrayList<PagePost> page, int notes, int i)
	{
		if(notes>=0)
		{
			if(page.get(i).getNotes()>=notes)
				return true;
			else
				return false;
		}
		else
			return true;
	}
	// sets include to true if posts tags match at least one of the tags specified in the filter
	public boolean fbTags(ArrayList<PagePost> page, String tags, int i)
	{
		if(tags!=null)
		{
			int j=0;
			
			while(true)
			{
				if(j<(page.get(i).getTags().size()))
				{
					if(page.get(i).getTags().get(j)==null)
						return false;
					else
					{
						String word=("(\\b"+page.get(i).getTags().get(j)+"\\b)");
						if(tags.matches(word))
							return true;
						else
							j++;
					}
				}
				else
					return false;
			}
		}
		else
			return true;
	}	
	// sets include to true if source URL matches the sourceURL specified in the filter
	public boolean fbSourceURL(ArrayList<PagePost> page, String sourceURL, int i)
	{
		if(sourceURL!=null)
		{
			if(page.get(i).getSourceURL().contains(sourceURL))
				return true;
			else
				return false;
		}
		else
			return true;
	}
	// sets all includes to true
	public ArrayList<PagePost> includeAll(ArrayList<PagePost> page)
	{
		int numPosts=page.size()-1;
		
		for(int i=0;i<numPosts;i++)
			page.get(i).setInclude(true);
		return page;
	}
	// sets all includes to false
	public ArrayList<PagePost> excludeAll(ArrayList<PagePost> page)
	{
		int numPosts=page.size()-1;
		
		for(int i=0;i<numPosts;i++)
			page.get(i).setInclude(false);
		return page;
	}
	// calls above functions to figure out which posts to include
	public ArrayList<PagePost> runFilter(ArrayList<PagePost> page, String[] types, int notes, String sourceURL, String timeParams, String tags) throws BadFormatException, BadRootException
	{
		int numPosts=page.size()-1;
		page=excludeAll(page);
		if(timeParams!=null)
			page=fbTime(page, timeParams);
		
		for(int i=0;i<numPosts;i++)
		{
			if(timeParams!=null) // if we previously filtered by time
			{
				if(page.get(i).getInclude()) // if within time parameters
				{
					if(fbType(page,types,i)&&fbNotes(page,notes,i)&&fbTags(page,tags,i)&&fbSourceURL(page,sourceURL,i)) // if all of these functions returned true(meaning the post should be included)
						page.get(i).setInclude(true); // then set include to true
					else
						page.get(i).setInclude(false); // otherwise false
				}
				else
					continue;
			}
			else // if we never filtered by time
			{
				if(fbType(page,types,i)&&fbNotes(page,notes,i)&&fbTags(page,tags,i)&&fbSourceURL(page,sourceURL,i)) // if all of these functions returned true(meaning the post should be include)
					page.get(i).setInclude(true); // then set include to true
				else
					page.get(i).setInclude(false); // otherwise false
			}
				
		}

		return page; // return the page after includes have been set
	}
}