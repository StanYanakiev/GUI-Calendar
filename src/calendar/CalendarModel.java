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
* Creates a calendar with multiple options
*/
public class CalendarModel 
{
	private GregorianCalendar cal;
	MONTHS[] arrayOfMonths = MONTHS.values();
	SHORTMONTHS[] arrayOfShortMonths = SHORTMONTHS.values();
	DAYS[] arrayOfDays = DAYS.values();
	LONGDAYS[] arrayOfLongDays = LONGDAYS.values();
	TreeMap<GregorianCalendar, TreeSet<Event>> myMap;
	
	/**
	 * Constructs a calendar
	 * @param c a GregorianCalendar is passed in
	 */
	public CalendarModel() 
	{
		cal = new GregorianCalendar();
		//cal.set(Calendar.YEAR, 2014);
		//cal.set(Calendar.MONTH, 2);
		myMap = new TreeMap <GregorianCalendar, TreeSet<Event>>();
		load();
	}

/**
 * Displays the current month in a calendar format
 * @param c a GregorianCalendar is passed in
 */
	// Display calendar view
	public String calendarView(GregorianCalendar c)
	{
		String test = "";
		System.out.println("~~~ Main Menu ~~~");
		//System.out.println(" " + arrayOfMonths[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR));
		test += arrayOfMonths[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR) + "\n";
		test += arrayOfDays[0];
		for (int i = 1; i < arrayOfDays.length; i++) 
		{
			test += String.format("%"+ 1 +"s", " ") + arrayOfDays[i];
			//System.out.printf("%2s ", arrayOfDays[i]);
		}
		System.out.println();
		test += "\n";

		// First Day of the Month
		GregorianCalendar temp = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
		int firstDayOfWeek = temp.get(Calendar.DAY_OF_WEEK);
		int maxDayOfMonth = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
		int i = 1; // The Day of the month
		for (int j = 1; j < maxDayOfMonth + firstDayOfWeek; j++) 
		{
			temp.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), i);
			if (j == firstDayOfWeek) {
				if (isTheDay(c, temp) == true)
				{
					//System.out.print(" [" + i + "] ");
					test += " [" + i + "] ";
					i++;
				}
				else
				{
					//System.out.print(" " + i + " ");
					test += "        " + i + "   ";
					i++;
				}
			} 
			else if (j > firstDayOfWeek)
			{
				if (i >= 10)
				{
					if (isTheDay(c, temp) == true)
					{
						//System.out.print("[" + i + "] ");
						test += "[" + i + "] ";
						i++;
					}
					else
					{
					//System.out.print(i + " ");
						test += i + " ";
					i++;
					}
				}
				 else if (isTheDay(c, temp) == true)
					{
					 //System.out.print("[" +i + "]  ");
					 test +=  "[" +i + "]  ";
						i++;
					}
				 else
				{
					//System.out.print(i + "  ");
					 test += i + "   ";
					i++;
				}
			} 
			else
				//System.out.print("   ");
			test += "   ";
			if (j % 7 == 0) 
			{
				//System.out.println();
				test += "\n";
			}
		}
		System.out.println(test);
		return test;
	}
	
	/**
	 * Prints the main menu options that are avaible to the user
	 */
	public void options()
	{
		System.out.println("\n~~~~~~~~~~~ Main Options ~~~~~~~~~~~");
		System.out.println("Select one of the following options: ");
		System.out.println("[L]oad	[V]iew	[C]reate [G]o to  [E]vent list	[D]elete  [Q]uit");
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
	 * Promts the user if a Day or Month view is wanted
	 */
	public void viewBy()
	{
		System.out.println("[D]ay view or [M]onth view?");
		Scanner sc = new Scanner(System.in);
		String viewChoice = sc.nextLine();

		if (viewChoice.equalsIgnoreCase("d")) // view by day
			dayView(sc); // displays day view
		else
			monthView(sc); // display month view
	}
	
	/**
	 * Prints the events scheduled for day
	 * @param c date set in GregorianCalendar
	 */
	public void printDayEvents(GregorianCalendar c)
	{
		GregorianCalendar dCal = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		for(GregorianCalendar i: myMap.keySet())
		{
			if(i.getTime().equals(dCal.getTime()))
			{
				TreeSet<Event> myTree = myMap.get(i);
				for(Event x: myTree)
				{
					System.out.print(x.getTitle() + " "); 
					System.out.print(x.getStartingTime().get(Calendar.HOUR_OF_DAY) + ":");
					int sminute = x.getStartingTime().get(Calendar.MINUTE); 
					if (sminute == 0)
					{
						System.out.print("00");
					}
					
					else 
					{
						System.out.print(x.getStartingTime().get(Calendar.MINUTE) + "");
					}
					
					if((x.getEndingTime() != null) && !(x.getEndingTime().equals(x.getStartingTime())))
					{
						System.out.print(" - ");
						System.out.print(x.getEndingTime().get(Calendar.HOUR_OF_DAY) + ":");
						int eminute = x.getEndingTime().get(Calendar.MINUTE); 
						if (eminute == 0)
						{
							System.out.println("00");
						}
						
						else 
						{
							System.out.println(x.getEndingTime().get(Calendar.MINUTE) +"");
						}
					}
						
					
				}
			}
		}
		System.out.println("");
		
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
	 * Shows previous or next days with all scheduled events on that day
	 * @param sc scanner
	 */
	public void dayView(Scanner sc)
	{
		//
		GregorianCalendar viewCal = new GregorianCalendar();
		LONGDAYS longDay = arrayOfLongDays[viewCal.get(Calendar.DAY_OF_WEEK) -1];
		SHORTMONTHS shortMonth = arrayOfShortMonths[viewCal.get(Calendar.MONTH )];
		System.out.println(longDay + ", " + shortMonth + " " + viewCal.get(Calendar.DAY_OF_MONTH) + ", " + viewCal.get(Calendar.YEAR));
		//
		// Check for Events for that Day
		printDayEvents(cal);
		
		System.out.println("[P]revious or [N]ext or [M]ain menu");
		String navigate = sc.nextLine();
	
		while (!navigate.equalsIgnoreCase("m"))
		{
			if (navigate.equalsIgnoreCase("p"))
			{
				viewCal.add(Calendar.DAY_OF_MONTH, -1);
				System.out.println(arrayOfLongDays[viewCal.get(Calendar.DAY_OF_WEEK) -1] + ", " + arrayOfShortMonths[viewCal.get(Calendar.MONTH)] + " " + viewCal.get(Calendar.DAY_OF_MONTH) + ", " + viewCal.get(Calendar.YEAR));
				//LIST EVENTS
				printDayEvents(viewCal);

				
			
			}
			else if (navigate.equalsIgnoreCase("n"))
			{
				viewCal.add(Calendar.DAY_OF_MONTH, 1);
				System.out.println(arrayOfLongDays[viewCal.get(Calendar.DAY_OF_WEEK) -1] + ", " + arrayOfShortMonths[viewCal.get(Calendar.MONTH)] + " " + viewCal.get(Calendar.DAY_OF_MONTH) + ", " + viewCal.get(Calendar.YEAR));
				//LIST EVENTS
				printDayEvents(viewCal);

			}
			
			System.out.println("\n[P]revious or [N]ext or [M]ain menu");
			navigate = sc.nextLine();
		}
		if(navigate.equalsIgnoreCase("m"))
		{
			System.out.println("\nSelect one of the following options: ");
			System.out.println("[L]oad	[V]iew	[C]reate [G]o to  [E]vent list	[D]elete  [Q]uit");
			
		}
	}
	
	/**
	 * Shows previous or next months with all scheduled events on that day
	 * @param sc scanner
	 */
	public void monthView(Scanner sc)
	{
		GregorianCalendar viewCal = new GregorianCalendar();
		printMonthEvents(viewCal);
		System.out.println("[P]revious or [N]ext or [M]ain menu");
		String navigate = sc.nextLine(); 
		
		while (!navigate.equalsIgnoreCase("m"))
		{
			if (navigate.equalsIgnoreCase("p"))
			{
				viewCal.add(Calendar.MONTH, -1);
				//LIST EVENTS
				printMonthEvents(viewCal);
			}
			else if (navigate.equalsIgnoreCase("n"))
			{
				viewCal.add(Calendar.MONTH, 1);
				//LIST EVENTS
				printMonthEvents(viewCal);
				
		
			}
			
			System.out.println("\n[P]revious or [N]ext or [M]ain menu");
			navigate = sc.nextLine();
		}
		
		if(navigate.equalsIgnoreCase("m"))
		{
			System.out.println("\nSelect one of the following options: ");
			System.out.println("[L]oad	[V]iew	[C]reate [G]o to  [E]vent list	[D]elete  [Q]uit");
			
		}
		
	}
	
	/** 
	 * Promts the user for Event information 
	 * Checks if an event is conflicting with already existing event
	 * Creates a new event 
	 */
	public void create() {
		System.out.println("Enter Title of Event");
		Scanner sc = new Scanner(System.in);
		String title = sc.nextLine();
		System.out.println("Enter a date in the following format: MM/DD/YYYY");
		String date = sc.nextLine();
		System.out.println("Enter a starting time in a 24 hour clock format");
		String startingTime = sc.nextLine();

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
		String shourSubstring = startingTime.substring(0, 2);
		int shourInt = Integer.parseInt(shourSubstring);
		// MINUTE
		String sminuteSubstring = startingTime.substring(3, 5);
		int sminuteInt = Integer.parseInt(sminuteSubstring);

		GregorianCalendar dateCal = new GregorianCalendar(yearInt, monthInt - 1, dayInt); // to represent date
		GregorianCalendar startTimeCal = new GregorianCalendar(yearInt, monthInt - 1, dayInt, shourInt, sminuteInt); // start
																														// time
																														// calendar

		Event myEvent;
		// Ending Time
		System.out.println("Enter an edning time in a 24 hour clock format (Put N if not applicabale)");
		String endingTime = sc.nextLine();
		if (endingTime.equalsIgnoreCase("n"))
		{
			myEvent = new Event(title, dateCal, startTimeCal);
		} 
		else 
		{
			// HOUR
			String ehourSubstring = endingTime.substring(0, 2);
			int ehourInt = Integer.parseInt(ehourSubstring);
			// MINUTE
			String eminuteSubstring = endingTime.substring(3, 5);
			int eminuteInt = Integer.parseInt(eminuteSubstring);
			GregorianCalendar endTimeCal = new GregorianCalendar(yearInt, monthInt - 1, dayInt, ehourInt, eminuteInt); // end
			// calendar
			myEvent = new Event(title, dateCal, startTimeCal, endTimeCal);

		}

		System.out.println(arrayOfLongDays[dateCal.get(Calendar.DAY_OF_WEEK) - 1] + ", "
				+ arrayOfShortMonths[dateCal.get(Calendar.MONTH)] + " " + dateCal.get(Calendar.DAY_OF_MONTH) + ", "
				+ dateCal.get(Calendar.YEAR));

		boolean exists = false;
		for (GregorianCalendar y : myMap.keySet()) 
		{
			if (y.equals(dateCal))
			{
				exists = true; //already at least one event for that day
				TreeSet<Event> mySet = myMap.get(dateCal);
				for (Event x : mySet) 
				{
					
					if (x.equals(myEvent)) 
					{
						System.out.print("Event Conflicts With Another \n");
						exists = true;
					}
				}
				mySet.add(myEvent); 
				myMap.put(dateCal, mySet);
				System.out.println("Another Event Added To The Specified Date \n");
			}
		}

		if (exists == false)
		{
			TreeSet<Event> set = new TreeSet<>();
			set.add(myEvent);
			myMap.put(dateCal, set);
			System.out.print("Event Created! \n");
		}
	}

	/**
	 * Prints the date of a calendar in a format
	 * @param c calendar
	 */
	public void printCalendar(Calendar c)
	{   
	    System.out.print(arrayOfLongDays[c.get(Calendar.DAY_OF_WEEK)-1]);
	    System.out.print(" ");
		System.out.print(arrayOfShortMonths[c.get(Calendar.MONTH)]);
		System.out.print(" ");
		System.out.print(c.get(Calendar.DAY_OF_MONTH));
		System.out.print("\n ");
				
	}

	/**
	 * Goes to a specific date with events printed
	 */
	public void goTo() 
	{
		System.out.println("Enter a date (MM/DD/YYYY Format)");
		Scanner sc = new Scanner(System.in);
		String date = sc.nextLine();

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

		GregorianCalendar dateCal = new GregorianCalendar(yearInt, monthInt -1, dayInt);

		printCalendar(dateCal);
		printDayEvents(dateCal);
	/*	System.out.println(arrayOfLongDays[dateCal.get(Calendar.DAY_OF_WEEK) -1] + ", "
				+ arrayOfShortMonths[dateCal.get(Calendar.MONTH) - 1] + " " + dateCal.get(Calendar.DAY_OF_MONTH) + ", "
				+ dateCal.get(Calendar.YEAR));
		*/
		
	}
	
	public void printAllEvents(Event y)
	{
		String printEvent = "";
		printEvent += "\n[" + y.getDate().get(Calendar.YEAR) + "] " + arrayOfMonths[y.getDate().get(Calendar.MONTH)];
		printEvent += " " + y.getDate().get(Calendar.DAY_OF_MONTH) + ", " + arrayOfLongDays[y.getDate().get(Calendar.DAY_OF_WEEK) -1] + ", " + y.getTitle() + " ";
		
		System.out.print(printEvent + y.getStartingTime().get(Calendar.HOUR_OF_DAY) + ":");
		int sminute = y.getStartingTime().get(Calendar.MINUTE); 
		if (sminute == 0)
		{
			System.out.print("00");
		}
		
		else 
		{
			System.out.print(y.getStartingTime().get(Calendar.MINUTE) + "");
		}
		
		if((y.getEndingTime() != null) && !(y.getEndingTime().equals(y.getStartingTime())))
		{
			System.out.print(" - ");
			System.out.print(y.getEndingTime().get(Calendar.HOUR_OF_DAY) + ":");
			int eminute = y.getEndingTime().get(Calendar.MINUTE); 
			if (eminute == 0)
			{
				System.out.print("00");
			}
			
			else 
			{
				System.out.print(y.getEndingTime().get(Calendar.MINUTE) + "");
			}
		}
	}

	/** 
	 * Shows all event that have been created 
	 */
	public void eventList() 
	{
		System.out.print("Scheduled Events: ");
		for(GregorianCalendar x: myMap.keySet())
		{
			TreeSet<Event> tree = myMap.get(x);
			for(Event y: tree)
			{
				printAllEvents(y);
				
			}
		}
		System.out.println("\nAll Events Displayed \n");
		
	}
	
	/**
	 * Deletes a specific or all events 
	 */
	public void delete()
	{
		System.out.println("[S]elected or [A]ll ? ");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine().toLowerCase();
		
		
		if(input.equals("s"))
		{
			System.out.println("Enter a Date (MM/DD/YYYY");
			String date = sc.nextLine();
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
			GregorianCalendar dateCal = new GregorianCalendar(yearInt, monthInt - 1, dayInt);
			myMap.remove(dateCal);
			System.out.println("Events On " + date + " Cleared");
		}
		else if(input.equals("a"))
		{
			myMap.clear();
			System.out.println("All Events Cleared");
		}
		else
			System.out.println("Not valid input");
		
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
	
	public GregorianCalendar getCalendar()
	{
		return cal;
	}
}
