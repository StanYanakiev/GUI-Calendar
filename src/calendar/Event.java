package calendar;


import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
*Creates an Event that stores a Title, Date, Starting Time, and Ending Time
*/
public class Event implements Comparable<Event>, Serializable
{
	private String title;
	private GregorianCalendar date;
	private GregorianCalendar startingTime;
	private GregorianCalendar endingTime;
	LONGDAYS[] arrayOfLongDays = LONGDAYS.values();
	SHORTMONTHS[] arrayOfShortMonths = SHORTMONTHS.values();
	
	/**
	 * Constructor that takes a Title, Date, Starting Time, and Ending Time
	 * @param name title
	 * @param date1 date (year, month, day)
	 * @param start starting time of the event
	 * @param end ending time of the event
	 */
	public Event(String name, GregorianCalendar date1, GregorianCalendar start, GregorianCalendar end)
	{
		title = name;
		date = date1;
		startingTime = start;
		endingTime = end;
	}
	
	/**
	 * Constructor that takes a Title, Date, and Starting Time
	 * @param name title
	 * @param date1 date (year, month, day)
	 * @param start starting time of the event
	 */
	public Event(String name, GregorianCalendar date1, GregorianCalendar start)
	{
		title = name;
		date = date1;
		startingTime = start;
	}
	
	/**
	 * Get the title 
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Set a new title
	 * @param newTitle the new title
	 */
	public void setTitle(String newTitle)
	{
		title = newTitle;
	}
	
	/**
	 * Get the date calendar
	 * @return the date calendar
	 */
	public GregorianCalendar getDate()
	{
		return date;
	}
	
	/**
	 * Set a new date 
	 * @param newDate the new date
	 */
	public void setDate(GregorianCalendar newDate)
	{
		 date = newDate;
	}
	
	/**
	 * Get the starting time calendar
	 * @return the starting time calendar
	 */
	public GregorianCalendar getStartingTime()
	{
		return startingTime;
	}
	
	/**
	 * Set a new starting time 
	 * @param newST new starting time
	 */
	public void setStartingTIme(GregorianCalendar newST)
	{
		startingTime = newST;
	}
	
	/**
	 * Get the ending date calendar
	 * @return the ending date calendar
	 */
	public GregorianCalendar getEndingTime()
	{
		return endingTime;
	}
	
	/**
	 * Set a new ending time
	 * @param newET new ending time
	 */
	public void setEndingTime(GregorianCalendar newET)
	{
		 endingTime = newET;
	}
	
	/**
	 * Sets true if two objects are equal, false if not
	 * @param o Object
	 * @return true or false
	 */
	public boolean equals(Object o)
	{
		Event x = (Event) o;
		return  this.compareTo(x) == 0;	
	}
	
	/**
	 * Compares first by date, then by starting time
	 * @param o Object
	 * @return negative, 0, or positive number
	 */
	public int compareTo(Event o)
	{
		int date = this.date.compareTo(o.date);
		if( date == 0)
		{
			int startTime = this.startingTime.compareTo(o.getStartingTime());
			return startTime;
		}
		else
			return date;
	}
	
	
	
	
}

