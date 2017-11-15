package calendar;

import java.util.GregorianCalendar;

public class SimpleCalendar {

	public static void main(String[] args) 
	{
		
		CalendarModel model = new CalendarModel();
		CalendarComponent frame = new CalendarComponent(model);
		//model.calendarView(model.getCalendar());
		//model.load();
		//model.printAllEvents();
		
	
		model.quit();
	}
	
	//MODEL == calendar 
}
