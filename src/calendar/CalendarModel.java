package calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.io.*;
import java.util.*;

enum MONTHS 
{
	January, February, March, April, May, June, July, August, September, October, November, December;
}

enum SHORTMONTHS
{
	Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec;
}
enum DAYS 
{
	Su, Mo, Tu, We, Th, Fr, Sa;
}

enum LONGDAYS
{
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
}
/**
 * @author Stanislav Yanakiev
 * 
 * GUI SimpleCalendaar
 * Creates a model for the GUI Calendar to function
 * 
 * November 16, 2017
 * All Right Reserved To Author
* 
*/
public class CalendarModel 
{
	private GregorianCalendar cal;
	private MONTHS[] arrayOfMonths = MONTHS.values();
	private SHORTMONTHS[] arrayOfShortMonths = SHORTMONTHS.values();
	private DAYS[] arrayOfDays = DAYS.values();
	private LONGDAYS[] arrayOfLongDays = LONGDAYS.values();
	private TreeMap<GregorianCalendar, TreeSet<Event>> myMap;
	boolean exists = false;
	
	/**
	 * Constructs a calendar
	 */
	public CalendarModel() 
	{
		cal = new GregorianCalendar();
		//cal.set(Calendar.YEAR, 2014);
		//cal.set(Calendar.MONTH, 2);
		myMap = new TreeMap <GregorianCalendar, TreeSet<Event>>();
		
	}

	/**
	 * Compares if two GregorianCalendars are set for the same day
	 * @param temp a GregorianCalendars that is compared to another
	 * @return true if both GregorianCalendars are set for the same day
	 * @return false if the GregorianCalendars are set for  different days
	 */
	public boolean isToday(GregorianCalendar temp) 
	{
		if (cal.get(Calendar.YEAR) == temp.get(Calendar.YEAR)) {
			if (cal.get(Calendar.MONTH) == temp.get(Calendar.MONTH)) {
				if (cal.get(Calendar.DAY_OF_MONTH) == temp.get(Calendar.DAY_OF_MONTH)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Compares if two GregorianCalendars are set for the same day
	 * @param today a GregorianCalendar that represents a day
	 * @param temp a GregorianCalendars that is compared to today to see if they match
	 * @return true if both GregorianCalendars are set for the same day
	 * @return false if the GregorianCalendars are set for  different days
	 */
	public boolean isTheDay(GregorianCalendar today, GregorianCalendar target)
	{
		if (today.get(Calendar.YEAR) == target.get(Calendar.YEAR)) {
			if (today.get(Calendar.MONTH) == target.get(Calendar.MONTH)) {
				if (today.get(Calendar.DAY_OF_MONTH) == target.get(Calendar.DAY_OF_MONTH)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Loads an "events.txt" file if it is the first run,
	 * otherwise it load the text from the file into the treeMap
	 */
	public void load()
	{
		try 
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("events.txt")); 
			TreeMap<GregorianCalendar, TreeSet<Event>> newMap = (TreeMap<GregorianCalendar, TreeSet<Event>>) in.readObject(); 
			in.close();  
			myMap = newMap;
			System.out.println("Loaded");
		}
		catch (IOException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			System.out.println("File does not exist");

		}
		catch (ClassNotFoundException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			System.out.println("No such File becuase of first run");

		}
	}
	
	
	/**
	 * Prints the events scheduled for day
	 * @param c date set in GregorianCalendar
	 */
	public String printDayEvents(GregorianCalendar c)
	{
		String eventsString = "";
		GregorianCalendar dCal = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		for(GregorianCalendar i: myMap.keySet())
		{
			if(i.getTime().equals(dCal.getTime()))
			{
				TreeSet<Event> myTree = myMap.get(i);
				for(Event x: myTree)
				{
					eventsString += x.getTitle() + " ";
					eventsString += x.getStartingTime().get(Calendar.HOUR_OF_DAY) + ":";
					System.out.print(x.getTitle() + " "); 
					System.out.print(x.getStartingTime().get(Calendar.HOUR_OF_DAY) + ":");
					int sminute = x.getStartingTime().get(Calendar.MINUTE); 
					if (sminute == 0)
					{
						System.out.print("00");
						eventsString += "00";
					}
					
					else 
					{
						System.out.print(x.getStartingTime().get(Calendar.MINUTE) + "");
						eventsString += x.getStartingTime().get(Calendar.MINUTE) + "";
					}
					
					if((x.getEndingTime() != null) && !(x.getEndingTime().equals(x.getStartingTime())))
					{
						System.out.print(" - ");
						System.out.print(x.getEndingTime().get(Calendar.HOUR_OF_DAY) + ":");
						eventsString += " - ";
						eventsString += x.getEndingTime().get(Calendar.HOUR_OF_DAY) + ":";
						int eminute = x.getEndingTime().get(Calendar.MINUTE); 
						if (eminute == 0)
						{
							System.out.println("00");
							eventsString += "00\n";
						}
						
						else 
						{
							//System.out.println(x.getEndingTime().get(Calendar.MINUTE) +"");
							eventsString += x.getEndingTime().get(Calendar.MINUTE) + "\n";
						}
					}
						
					
				}
			}
		}
		System.out.println("");
		eventsString += "\n";
		return  eventsString;
	}
	
	/**
	 * Prints the events for a scheduled month
	 * @param c date set in GregorianCalendar
	 */
	public void printMonthEvents(GregorianCalendar c)
	{
		System.out.println("  " + arrayOfMonths[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR));
		for (int i = 0; i < arrayOfDays.length; i++) 
		{
			System.out.printf("%2s ", arrayOfDays[i]);
		}
		System.out.println();
		

		// First Day of the Month
		GregorianCalendar temp = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
		int firstDayOfWeek = temp.get(Calendar.DAY_OF_WEEK);
		int maxDayOfMonth = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
		int i = 1; // The Day of the month
		for (int j = 1; j < maxDayOfMonth + firstDayOfWeek; j++) 
		{
			temp.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), i);
			if (j == firstDayOfWeek) {
				if (myMap.containsKey(temp))
				{
					System.out.print(" {" + i + "} ");
					i++;
				}
				else
				{
					System.out.print(" " + i + " ");
					i++;
				}
			} 
			else if (j > firstDayOfWeek)
			{
				if (i >= 10)
				{
					if (myMap.containsKey(temp))
					{
						System.out.print("{" + i + "} ");
						i++;
					}
					else
					{
					System.out.print(i + " ");
					i++;
					}
				}
				 else if (isToday(temp) == true)
					{
					 System.out.print("{" +i + "}  ");
						i++;
					}
				 else
				{
					System.out.print(i + "  ");
					i++;
				}
			} 
			else
				System.out.print("   ");
			if (j % 7 == 0) 
			{
				System.out.println();
			}
		}
	}
	/** Gets a String with Day Of Week, Month, and Day of the passed in calendar
	 * @param c calendar to get the Date
	 * @return dateDesc Week, Month, Day format
	 */
	public String getDateDescription(GregorianCalendar c)
	{
		//GregorianCalendar viewCal = new GregorianCalendar();
		LONGDAYS longDay = arrayOfLongDays[c.get(Calendar.DAY_OF_WEEK) -1];
		int shortMonth = c.get(Calendar.MONTH ) + 1;
		String dateDesc = "";
		dateDesc += longDay + ", " + shortMonth + "/" + c.get(Calendar.DAY_OF_MONTH);
		return dateDesc;
	}
	
	/**
	 * Gets a String with Month, Day, Year of the passed in calendar
	 * @param c calendar to get the Date
	 * @return dateDesc Month, Day, Year format
	 */
	public String getDateDescription2(GregorianCalendar c)
	{
		//GregorianCalendar viewCal = new GregorianCalendar();
		
		int shortMonth = c.get(Calendar.MONTH ) + 1;
		String dateDesc = "";
		dateDesc +=shortMonth + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR);
		return dateDesc;
	}
	
	
	
	/**
	 * Prompts the user for Event information 
	 * Checks if an event is conflicting with already existing event
	 * Creates a new event 
	 * @param name title of the event
	 * @param date date of the event is on 
	 * @param start start time of the Event
	 * @param end end time of the Event
	 */
	public void create(String name, String date, String start, String end) 
	{

		// Ex 09/12/2017
		// MONTH
		String monthSubstring = "";
		String firstMonthDigit = date.substring(0, 1);
		if (firstMonthDigit.equals("0"))
			monthSubstring = date.substring(1, 2);
		else
			monthSubstring = date.substring(0, 2);
		int monthInt = Integer.parseInt(monthSubstring);
		// DAY OF MONTH
		String daySubstring = date.substring(3, 5);
		int dayInt = Integer.parseInt(daySubstring);
		// YEAR
		String yearSubstring = date.substring(6, date.length());
		int yearInt = Integer.parseInt(yearSubstring);
		// StartingTime
		// HOUR
		String shourSubstring = start.substring(0, 2);
		int shourInt = Integer.parseInt(shourSubstring);
		// MINUTE
		String sminuteSubstring = start.substring(3, 5);
		int sminuteInt = Integer.parseInt(sminuteSubstring);

		GregorianCalendar dateCal = new GregorianCalendar(yearInt, monthInt - 1, dayInt); // to represent date
		GregorianCalendar startTimeCal = new GregorianCalendar(yearInt, monthInt - 1, dayInt, shourInt, sminuteInt); // start
																														// time
																														// calendar

		Event myEvent;
		// Ending Time
		
		if (end.equalsIgnoreCase("n"))
		{
			myEvent = new Event(name, dateCal, startTimeCal);
		} 
		else 
		{
			// HOUR
			String ehourSubstring = end.substring(0, 2);
			int ehourInt = Integer.parseInt(ehourSubstring);
			// MINUTE
			String eminuteSubstring = end.substring(3, 5);
			int eminuteInt = Integer.parseInt(eminuteSubstring);
			GregorianCalendar endTimeCal = new GregorianCalendar(yearInt, monthInt - 1, dayInt, ehourInt, eminuteInt); // end
			// calendar
			myEvent = new Event(name, dateCal, startTimeCal, endTimeCal);

		}

		System.out.println(arrayOfLongDays[dateCal.get(Calendar.DAY_OF_WEEK) - 1] + ", "
				+ arrayOfShortMonths[dateCal.get(Calendar.MONTH)] + " " + dateCal.get(Calendar.DAY_OF_MONTH) + ", "
				+ dateCal.get(Calendar.YEAR));

		boolean alreadyExists = false;
		for (GregorianCalendar y : myMap.keySet()) 
		{
			if (y.equals(dateCal))
			{
				alreadyExists = true; //already at least one event for that day
				TreeSet<Event> mySet = myMap.get(dateCal);
				for (Event x : mySet) 
				{
					
					if (x.equals(myEvent)) 
					{
						alreadyExists = true;
						exists = true;
					}
				}
				mySet.add(myEvent); 
				myMap.put(dateCal, mySet);
			}
		}

		if (alreadyExists == false)
		{
			TreeSet<Event> set = new TreeSet<>();
			set.add(myEvent);
			myMap.put(dateCal, set);
			System.out.print("Event Created! \n");
		}
	}
	
	/**
	 * Writes all created events in a file and quits the program
	 */
	public void quit() 
	{
        try 
        {
        	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("events.txt"));
			out.writeObject(myMap); 
			out.close();
			System.out.println("~~~~~ End ~~~~~");
        } 
        catch (IOException e) 
        {
            System.out.println("Message: " + e.getMessage());
        }
	}
	
	/**
	 * Access the calendar of the model
	 * @return
	 */
	public GregorianCalendar getCalendar()
	{
		return cal;
	}
}
