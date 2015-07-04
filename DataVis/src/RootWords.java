// Written by Peter Kennedy

/*
 * A lot of the code is repetitive (mostly just the if/else statements that walk through strings), earlier lines of code will have more detailed explanations
 */

import java.util.ArrayList;

public class RootWords 
{
	// return true if the string is of a numeric type (1st, 52nd, 3:30, etc)
	public boolean isNumeric(String str) 
	{  
		str=str.replace("/", "");
		str=str.replace(":", "");
		str=str.replace(".", "");
		str=str.replace("st", "");
		str=str.replace("nd", "");
		str=str.replace("rd", "");
		str=str.replace("th", "");
		try  
		{  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}
	// fix user formatting (1st=1, 2nd=2, etc)
	public int makeNumeric(String str)
	{
		str=str.replace("st", "");
		str=str.replace("nd", "");
		str=str.replace("rd", "");
		str=str.replace("th", "");
		int n = Integer.parseInt(str);
		return n;
	}
	// rebuilds "chunk" after one rootword + parameter chunk has been completed (usually delimited by "or")
	public ArrayList<String> refillChunk(ArrayList<String> chunk, int start)
	{
		ArrayList<String> newChunk = new ArrayList<String>();
		for(int i=0;i<chunk.size();i++)
		{
			newChunk.add(i,chunk.get(start));
			if(start>=chunk.size()-1)
				break;
			if(start<chunk.size()-1)
				start++;
		}
		return newChunk;
	}
	// root word = (between || from)
	public ArrayList<PagePost> BetweenFrom(ArrayList<PagePost> page, ArrayList<String> chunk, boolean inverseFlag) throws BadFormatException, BadRootException
	{
		Parser parse = new Parser();
		ApplyTimes apply = new ApplyTimes();
		String chunkRoot=null;
		// check if valid <<(between/from) #NUMBER (and/to) #NUMBER>> string
		if((chunk.get(0).equals("between")&&(chunk.get(2).equals("and")||chunk.get(2).equals("to"))&&isNumeric(chunk.get(1))&&isNumeric(chunk.get(3)))||
				(chunk.get(0).equals("from")&&(chunk.get(2).equals("and")||chunk.get(2).equals("to"))&&isNumeric(chunk.get(3))))
		{
			// between 1/1/2011 and 1/1/2012
			if(chunk.get(1).contains("/")&&chunk.get(3).contains("/"))
			{
				String lowDate=chunk.get(1);
				String highDate=chunk.get(3);
				String lowTime=null;
				String highTime=null;
				boolean timeRange=false;
				int i=4;
				// between 1/1/2010 and 1/1/2015 from 2:30 to 5:00
				if(chunk.size()>6&&(chunk.get(4).equals("from")||chunk.get(4).equals("between"))&&chunk.get(5).contains(":")&&(chunk.get(6).contains("and")||chunk.get(6).contains("to"))
						&&chunk.get(7).contains(":"))
				{
					lowTime=chunk.get(5);
					highTime=chunk.get(7);
					timeRange=true; // indicates this chunk also has a time of day range
					i=8;
				}
				
				// sets PagePost includes based on the date/times the user input
				page=apply.betweenFullDateXandY(page, inverseFlag, lowDate, highDate,lowTime,highTime,timeRange); // if timeRange==true then also check if the post is within the correct time of day window
				
				if(chunk.size()==i) // if no more input
					return page;
				if(chunk.get(i).equals("or")) // if end of rootword chunk (a new root word is expected)
				{
					chunk=refillChunk(chunk,i+1); // refill the chunk starting at the next expected root word
					chunkRoot = parse.getChunkRoot(chunk);
					page = parse.callRoot(page, chunk, inverseFlag, chunkRoot); // do another set of filters with the new chunk
				}
				else if(chunk.get(i).equals("except")) // if next rootword chunk is an inverse (if a match, then set to false instead of true)
				{
					inverseFlag=true; // set inverse flag to true)
					chunk=refillChunk(chunk,i+1); // refill the chunk starting at the next expected rootword
					chunkRoot = parse.getChunkRoot(chunk);
					page = parse.callRoot(page, chunk, inverseFlag, chunkRoot); // do another set of filters with the new chunk
				}
				else if(chunk.get(i).equals("and")&&(chunk.get(i+1).equals("between")||chunk.get(i).equals("from"))) // if a continuation of the same rootword
				{
					chunk=refillChunk(chunk,i+1);
					chunkRoot = parse.getChunkRoot(chunk);
					page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
				}
				else
					return page;
			}
			// between 13:30 and 14:30
			if(chunk.get(1).contains(":")&&chunk.get(3).contains(":"))
			{
				String lowTime=chunk.get(1);
				String highTime=chunk.get(3);
		
				page=apply.betweenTimeXandY(page, inverseFlag, lowTime, highTime);
				
				if(chunk.size()==4)
					return page;
				if(chunk.get(4).equals("or")) // if end of rootword chunk (a new root word is expected)
				{
					chunk=refillChunk(chunk,5);
					chunkRoot = parse.getChunkRoot(chunk);
					page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
				}
				else if(chunk.get(4).equals("except")) // if next rootword chunk is an inverse (if a match, then set to false instead of true)
				{
					inverseFlag=true;
					chunk=refillChunk(chunk,5);
					chunkRoot = parse.getChunkRoot(chunk);
					page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
				}
				else if(chunk.get(4).equals("and")&&(chunk.get(5).equals("between")||chunk.get(5).equals("from"))) // if a continuation of the same rootword
				{ 
					chunk=refillChunk(chunk,5);
					chunkRoot = parse.getChunkRoot(chunk);
					page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
				}
				else
					return page;
			}
			return page;
		}
		// between 13:30 1/1/2011 and 14:30 1/1/2012
		else if((chunk.get(0).equals("between")&&isNumeric(chunk.get(1))&&isNumeric(chunk.get(2))&&(chunk.get(3).equals("and")||chunk.get(3).equals("to"))
				&&isNumeric(chunk.get(4))&&isNumeric(chunk.get(5)))||
				(chunk.get(0).equals("from")&&isNumeric(chunk.get(1))&&isNumeric(chunk.get(2))&&(chunk.get(3).equals("and")||chunk.get(3).equals("to"))
						&&isNumeric(chunk.get(4))&&isNumeric(chunk.get(5))))
		{
			String lowTime=chunk.get(1);
			String lowDate=chunk.get(2);
			String highTime=chunk.get(4);
			String highDate=chunk.get(5);
			boolean timeRange=false;
			int i=6;
			
			// between 13:30 1/1/2011 and 14:30 1/1/2012 from 3:30 to 6:30
			if(chunk.size()>=8&&(chunk.get(6).equals("from")||chunk.get(6).equals("between"))&&chunk.get(7).contains(":")&&(chunk.get(8).contains("and")||chunk.get(8).contains("to"))
					&&chunk.get(9).contains(":"))
			{
				lowTime=chunk.get(7);
				highTime=chunk.get(9);
				timeRange=true;
				i=10;
			}
		
			page=apply.betweenFullTimeDateXandY(page, inverseFlag, lowTime, lowDate, highTime, highDate,timeRange);
			
			if(chunk.size()==i)
				return page;
			if(chunk.get(i).equals("or")) // end of rootword chunk
			{
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else if(chunk.get(i).equals("except")) // next rootwork chunk is inverse
			{
				inverseFlag=true;
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else if(chunk.get(i).equals("and")&&(chunk.get(i+1).equals("between")||chunk.get(i+1).equals("from"))) // continuation of the same rootword
			{
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else
				return page;
		}
		// invalid input
		else
		{
			String s = "";
			for(int i=0;i<chunk.size();i++) // build a string to show the approximate location of the user input error
			{
				if(chunk.get(i).equals("or"))
					break;
				s = s + chunk.get(i)+" ";
			}
			throw new BadFormatException("invalid parameters in chunk: \n"+s);
		}
		return page;

	}
	public ArrayList<PagePost> Since(ArrayList<PagePost> page, ArrayList<String> chunk, boolean inverseFlag) throws BadFormatException, BadRootException
	{
		Parser parse = new Parser();
		ApplyTimes apply = new ApplyTimes();
		String chunkRoot=null;
		// since 1/1/2013
		if((chunk.get(0).equals("since")&&isNumeric(chunk.get(1))))
		{
			String lowDate=chunk.get(1);
			String lowTime=null;
			String highTime=null;
			boolean timeRange=false;
			int i=2;
			// since 1/1/2013 from 3:30 to 4:30
			if((chunk.get(2).equals("from")||chunk.get(2).equals("between"))&&chunk.get(3).contains(":")&&(chunk.get(4).contains("and")||chunk.get(4).contains("to"))
					&&chunk.get(5).contains(":"))
			{
				lowTime=chunk.get(3);
				highTime=chunk.get(5);
				timeRange=true;
				i=6;
			}
			
			page=apply.sinceDate(page, inverseFlag, lowDate,lowTime,highTime,timeRange);
			
			if(chunk.size()==i)
				return page;
			if(chunk.get(i).equals("or"))
			{
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else if(chunk.get(i).equals("except"))
			{
				inverseFlag=true;
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else
				return page;
		}
		// since 3:30pm 1/1/2013
		else if((chunk.get(0).equals("since")&&isNumeric(chunk.get(1))&&isNumeric(chunk.get(2))))
		{
			String lowTime=chunk.get(1);
			String lowDate=chunk.get(2);
			String rangeLowTime=null;
			String highTime=null;
			boolean timeRange=false;
			int i=3;
			// since 3:30 1/1/2013 from 2:30 to 6:30
			if((chunk.get(3).equals("from")||chunk.get(3).equals("between"))&&chunk.get(4).contains(":")&&(chunk.get(5).contains("and")||chunk.get(5).contains("to"))
					&&chunk.get(6).contains(":"))
			{
				lowTime=chunk.get(4);
				highTime=chunk.get(6);
				timeRange=true;
				i=7;
			}
			
			page=apply.sinceTimeDate(page, inverseFlag, lowTime, lowDate,rangeLowTime,highTime,timeRange);
			
			if(chunk.size()==i)
				return page;
			if(chunk.get(i).equals("or"))
			{
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else if(chunk.get(i).equals("except"))
			{
				inverseFlag=true;
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else
				return page;
		}
		else
		{
			String s = "";
			for(int i=0;i<chunk.size();i++)
			{
				if(chunk.get(i).equals("or"))
					break;
				s = s + chunk.get(i)+" ";
			}
			throw new BadFormatException("invalid parameters in chunk: \n"+s);
		}
		return page;
	}
	public ArrayList<PagePost> Until(ArrayList<PagePost> page, ArrayList<String> chunk, boolean inverseFlag) throws BadFormatException, BadRootException
	{
		Parser parse = new Parser();
		ApplyTimes apply = new ApplyTimes();
		String chunkRoot=null;
		// until 1/1/2013
		if((chunk.get(0).equals("until")&&isNumeric(chunk.get(1))))
		{
			String highDate=chunk.get(1);
			String lowTime=null;
			String highTime=null;
			boolean timeRange=false;
			int i=2;
			// until 1/1/2013 from 3:30 to 4:30
			if((chunk.get(2).equals("from")||chunk.get(2).equals("between"))&&chunk.get(3).contains(":")&&(chunk.get(4).contains("and")||chunk.get(4).contains("to"))
					&&chunk.get(5).contains(":"))
			{
				lowTime=chunk.get(3);
				highTime=chunk.get(5);
				timeRange=true;
				i=6;
			}
			page=apply.untilDate(page, inverseFlag, highDate,lowTime,highTime,timeRange);
			
			if(chunk.size()==i)
				return page;
			if(chunk.get(i).equals("or"))
			{
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else if(chunk.get(i).equals("except"))
			{
				inverseFlag=true;
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else
				return page;
		}
		// until 3:30pm 1/1/2013
		else if((chunk.get(0).equals("until")&&isNumeric(chunk.get(1))&&isNumeric(chunk.get(2))))
		{
			String highTime=chunk.get(1);
			String highDate=chunk.get(2);
			String lowTime=null;
			String rangeHighTime=null;
			boolean timeRange=false;
			int i=3;
			// until 3:30 1/1/2013 from 5:50 to 2:20
			if((chunk.get(3).equals("from")||chunk.get(3).equals("between"))&&chunk.get(4).contains(":")&&(chunk.get(5).contains("and")||chunk.get(5).contains("to"))
					&&chunk.get(6).contains(":"))
			{
				lowTime=chunk.get(4);
				highTime=chunk.get(6);
				timeRange=true;
				i=7;
			}
			page=apply.untilTimeDate(page, inverseFlag, highTime, highDate,lowTime,rangeHighTime,timeRange);
			
			if(chunk.size()==i)
				return page;
			if(chunk.get(i).equals("or"))
			{
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else if(chunk.get(i).equals("except"))
			{
				inverseFlag=true;
				chunk=refillChunk(chunk,i+1);
				chunkRoot = parse.getChunkRoot(chunk);
				page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
			}
			else
				return page;
		}
		else
		{
			String s = "";
			for(int i=0;i<chunk.size();i++)
			{
				if(chunk.get(i).equals("or"))
					break;
				s = s + chunk.get(i)+" ";
			}
			throw new BadFormatException("invalid parameters in chunk: \n"+s);
		}
		return page;
	}
	public ArrayList<PagePost> AllEveryOnly(ArrayList<PagePost> page, ArrayList<String> chunk, boolean inverseFlag, boolean continuation) throws BadFormatException, BadRootException
	{
		ApplyTimes apply = new ApplyTimes();
		int z=0;
		
		// (all/every/only) even (days/months)
		if(chunk.get(1).equals("even"))
		{
			if(chunk.get(2).equals("days"))
			{
				apply.falseDOM();
				for(int i=1; i<ApplyTimes.dom.length; i+=2)
				{
					ApplyTimes.dom[i]=true;
				}
			}
			else if(chunk.get(2).equals("months"))
			{
				apply.falseMOY();
				for(int i=0; i<ApplyTimes.moy.length; i+=2)
				{
					ApplyTimes.moy[i]=true;
				}
			}
			else
			{
				String s = "";
				for(int i=0;i<chunk.size();i++)
				{
					if(chunk.get(i).equals("or"))
						break;
					s = s + chunk.get(i)+" ";
				}
				throw new BadFormatException("invalid parameters in chunk: \n"+s);
			}
			z=3;
		}
		// (all/every/only) odd (days/months)
		else if(chunk.get(1).equals("odd"))
		{
			if(chunk.get(2).equals("days"))
			{
				apply.falseDOM();
				for(int i=0; i<ApplyTimes.dom.length; i+=2)
				{
					ApplyTimes.dom[i]=true;
				}
			}
			else if(chunk.get(2).equals("months"))
			{
				apply.falseMOY();
				for(int i=1; i<ApplyTimes.moy.length; i+=2)
				{
					ApplyTimes.moy[i]=true;
				}
			}
			else
			{
				String s = "";
				for(int i=0;i<chunk.size();i++)
				{
					if(chunk.get(i).equals("or"))
						break;
					s = s + chunk.get(i)+" ";
				}
				throw new BadFormatException("invalid parameters in chunk: \n"+s);
			}
			z=3;
		}
		// if single day
		else if(isNumeric(chunk.get(1))&&chunk.get(2).equals("of"))
		{
			apply.falseDOM();
			ApplyTimes.dom[makeNumeric(chunk.get(1))-1]=true;
			apply.falseMOY();
			int i=3;
			// if single day and multiple months
			if((chunk.size()>i+1)&&(chunk.get(i).contains("uary")||chunk.get(i).contains("march")||chunk.get(i).contains("april")||chunk.get(i).contains("may")
					||chunk.get(i).contains("june")||chunk.get(i).contains("july")||chunk.get(i).contains("august")||chunk.get(i).contains("ember")||chunk.get(i).contains("october"))
					&&(chunk.get(i+1).contains("uary")||chunk.get(i+1).contains("march")||chunk.get(i+1).contains("april")||chunk.get(i+1).contains("may")
							||chunk.get(i+1).contains("june")||chunk.get(i+1).contains("july")||chunk.get(i+1).contains("august")||chunk.get(i+1).contains("ember")
							||chunk.get(i+1).contains("october")||chunk.get(i+1).equals("and")))
			{
				for(i=i;i<chunk.size();i++)
				{
					String month = chunk.get(i);
					if(month.equals("and"))
						continue;
					else if(month.contains("uary")||month.contains("march")||month.contains("april")||month.contains("may")||month.contains("june")
							||month.contains("july")||month.contains("august")||month.contains("ember")||month.contains("october"))
					{
						switch(month)
						{
						case "january":
							ApplyTimes.moy[0]=true;
							break;
						case "february":
							ApplyTimes.moy[1]=true;
							break;
						case "march":
							ApplyTimes.moy[2]=true;
							break;
						case "april":
							ApplyTimes.moy[3]=true;
							break;
						case "may":
							ApplyTimes.moy[4]=true;
							break;
						case "june":
							ApplyTimes.moy[5]=true;
							break;
						case "july":
							ApplyTimes.moy[6]=true;
							break;
						case "august":
							ApplyTimes.moy[7]=true;
							break;
						case "september":
							ApplyTimes.moy[8]=true;
							break;
						case "october":
							ApplyTimes.moy[9]=true;
							break;
						case "november":
							ApplyTimes.moy[10]=true;
							break;
						case "december":
							ApplyTimes.moy[11]=true;
							break;
						default:
						{
							String s = "";
							for(int k=0;k<chunk.size();k++)
							{
								if(chunk.get(k).equals("or"))
									break;
								s = s + chunk.get(k)+" ";
							}
							throw new BadFormatException("invalid parameters in chunk: \n"+s);
						}
						}
					}
					else
						break;
				}
			}
			// if single day and single month
			else
			{
				switch(chunk.get(i))
				{
				case "january":
					ApplyTimes.moy[0]=true;
					break;
				case "february":
					ApplyTimes.moy[1]=true;
					break;
				case "march":
					ApplyTimes.moy[2]=true;
					break;
				case "april":
					ApplyTimes.moy[3]=true;
					break;
				case "may":
					ApplyTimes.moy[4]=true;
					break;
				case "june":
					ApplyTimes.moy[5]=true;
					break;
				case "july":
					ApplyTimes.moy[6]=true;
					break;
				case "august":
					ApplyTimes.moy[7]=true;
					break;
				case "september":
					ApplyTimes.moy[8]=true;
					break;
				case "october":
					ApplyTimes.moy[9]=true;
					break;
				case "november":
					ApplyTimes.moy[10]=true;
					break;
				case "december":
					ApplyTimes.moy[11]=true;
					break;
				default:
				{
					String s = "";
					for(int k=0;k<chunk.size();k++)
					{
						if(chunk.get(k).equals("or"))
							break;
						s = s + chunk.get(k)+" ";
					}
					throw new BadFormatException("invalid parameters in chunk: \n"+s);
				}
				}
			}
			z=i;
		}
		// if multiple days
		else if(isNumeric(chunk.get(1))&&(isNumeric(chunk.get(2))||chunk.get(2).equals("and")))
		{
			ArrayList<Integer> nums = new ArrayList<Integer>();
			int i;
			for(i=1;i<chunk.size();i++)
			{
				if(chunk.get(i).equals("and"))
					continue;
				else if(isNumeric(chunk.get(i)))
				{
					nums.add(makeNumeric(chunk.get(i)));
				}
				else
				{
					break;
				}	
			}
			if(chunk.get(i).equals("of"))
			{
				i++;
				// if multiple days and multiple months
				if((chunk.size()>i+1)&&(chunk.get(i).contains("uary")||chunk.get(i).contains("march")||chunk.get(i).contains("april")||chunk.get(i).contains("may")||chunk.get(i).contains("june")
						||chunk.get(i).contains("july")||chunk.get(i).contains("august")||chunk.get(i).contains("ember")||chunk.get(i).contains("october"))
						&&(chunk.get(i+1).contains("uary")||chunk.get(i+1).contains("march")||chunk.get(i+1).contains("april")||chunk.get(i+1).contains("may")
								||chunk.get(i+1).contains("june")||chunk.get(i+1).contains("july")||chunk.get(i+1).contains("august")||chunk.get(i+1).contains("ember")
								||chunk.get(i+1).contains("october")||chunk.get(i+1).equals("and")))
				{
					apply.falseDOM();
					for(int k=0; k<nums.size();k++)
					{
						ApplyTimes.dom[nums.get(k)-1]=true;
					}
					apply.falseMOY();
					for(i=i;i<chunk.size();i++)
					{
						String month = chunk.get(i);
						if(month.equals("and"))
							continue;
						else if(month.contains("uary")||month.contains("march")||month.contains("april")||month.contains("may")||month.contains("june")
								||month.contains("july")||month.contains("august")||month.contains("ember")||month.contains("october"))
						{
							switch(month)
							{
							case "january":
								ApplyTimes.moy[0]=true;
								break;
							case "february":
								ApplyTimes.moy[1]=true;
								break;
							case "march":
								ApplyTimes.moy[2]=true;
								break;
							case "april":
								ApplyTimes.moy[3]=true;
								break;
							case "may":
								ApplyTimes.moy[4]=true;
								break;
							case "june":
								ApplyTimes.moy[5]=true;
								break;
							case "july":
								ApplyTimes.moy[6]=true;
								break;
							case "august":
								ApplyTimes.moy[7]=true;
								break;
							case "september":
								ApplyTimes.moy[8]=true;
								break;
							case "october":
								ApplyTimes.moy[9]=true;
								break;
							case "november":
								ApplyTimes.moy[10]=true;
								break;
							case "december":
								ApplyTimes.moy[11]=true;
								break;
							default:
							{
								String s = "";
								for(int k=0;k<chunk.size();k++)
								{
									if(chunk.get(k).equals("or"))
										break;
									s = s + chunk.get(k)+" ";
								}
								throw new BadFormatException("invalid parameters in chunk: \n"+s);
							}
							}
						}
						else
						{
							i--;
							break;
						}
					}
				}
				else
				{
					apply.falseDOM();
					apply.falseMOY();
					for(int k=0; k<nums.size();k++)
					{
						ApplyTimes.dom[nums.get(k)-1]=true;
					}
					switch(chunk.get(i))
					{
					case "january":
						ApplyTimes.moy[0]=true;
						break;
					case "february":
						ApplyTimes.moy[1]=true;
						break;
					case "march":
						ApplyTimes.moy[2]=true;
						break;
					case "april":
						ApplyTimes.moy[3]=true;
						break;
					case "may":
						ApplyTimes.moy[4]=true;
						break;
					case "june":
						ApplyTimes.moy[5]=true;
						break;
					case "july":
						ApplyTimes.moy[6]=true;
						break;
					case "august":
						ApplyTimes.moy[7]=true;
						break;
					case "september":
						ApplyTimes.moy[8]=true;
						break;
					case "october":
						ApplyTimes.moy[9]=true;
						break;
					case "november":
						ApplyTimes.moy[10]=true;
						break;
					case "december":
						ApplyTimes.moy[11]=true;
						break;
					default:
					{
						String s = "";
						for(int k=0;k<chunk.size();k++)
						{
							if(chunk.get(k).equals("or"))
								break;
							s = s + chunk.get(k)+" ";
						}
						throw new BadFormatException("invalid parameters in chunk: \n"+s);
					}
					}
				}
			}
			z=i+1;
		}
		else
		{
			String s = "";
			for(int i=0;i<chunk.size();i++)
			{
				if(chunk.get(i).equals("or"))
					break;
				s = s + chunk.get(i)+" ";
			}
			throw new BadFormatException("invalid parameters in chunk: \n"+s);
		}
		
		// all _______ from 3:30 to 4:40
		if(chunk.size()>z+2&&(chunk.get(z).equals("from")||chunk.get(z).equals("between"))&&chunk.get(z+1).contains(":")&&(chunk.get(z+2).contains("and")||chunk.get(z+2).contains("to"))
				&&chunk.get(z+3).contains(":"))
		{
			String lowTime=chunk.get(z+1);
			String highTime=chunk.get(z+3);
			page = apply.betweenTimeXandY(page, inverseFlag, lowTime, highTime);
		}
		else if(z<chunk.size()&&(chunk.get(z).equals("or"))) // end of rootword chunk
		{
			page=apply.AllEveryOnly(page, inverseFlag);
			chunk=refillChunk(chunk,z+1);
			Parser parse = new Parser();
			String chunkRoot = parse.getChunkRoot(chunk);
			page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
		}
		else if(z<chunk.size()&&(chunk.get(z).equals("except")&&(chunk.get(z+1).equals("all")||chunk.get(z+1).equals("every")||chunk.get(z+1).equals("only")))) // next rootword chunk is inverse
		{
			page=apply.AllEveryOnly(page, inverseFlag);
			inverseFlag=true;
			chunk=refillChunk(chunk,z+1);
			Parser parse = new Parser();
			String chunkRoot = parse.getChunkRoot(chunk);
			page = parse.callRoot(page, chunk, inverseFlag, chunkRoot);
		}
		// all ______ except between ________ (this is continuation of the current chunk)
		else if(z<chunk.size()&&chunk.get(z).equals("except")&&(chunk.get(z+1).equals("between")||chunk.get(z+1).equals("from")))
		{
			inverseFlag=true;
			chunk=refillChunk(chunk,z+1);
			page = BetweenFrom(page,chunk,inverseFlag);
		}
		// all _______ between _________ (this is a continuation of the current chunk)
		else if(z<chunk.size()&&(chunk.get(z).equals("between")||chunk.get(z).equals("from")))
		{
			chunk=refillChunk(chunk,z);
			page = BetweenFrom(page,chunk,inverseFlag);
		}
		// all _____ and all ______ (this is a continuation of the current chunk)
		else if(z<chunk.size()&&(chunk.get(z).equals("and")))
		{
			if(chunk.get(z).equals("all")||chunk.get(z).equals("every")||chunk.get(z).equals("only"))
			{
				chunk=refillChunk(chunk,z);
				continuation = true;
				page = AllEveryOnly(page,chunk,inverseFlag,continuation);
			}
			else if(isNumeric(chunk.get(z))&&chunk.get(z+1).equals("of"))
			{
				chunk=refillChunk(chunk,z-1);
				continuation = true;
				page = AllEveryOnly(page,chunk,inverseFlag,continuation);
			}
			
		}
		// done building the chunk to sent to ApplyTimes.java
		else
			page=apply.AllEveryOnly(page, inverseFlag);
		return page;
	}
}
