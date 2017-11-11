package calendar;

import java.util.GregorianCalendar;

public class SimpleCalendar {

	public static void main(String[] args) 
	{
		GregorianCalendar cal = new GregorianCalendar();
		CalendarModel model = new CalendarModel(cal);
		CalendarComponent frame = new CalendarComponent();
		model.calendarView(cal);
	}

}
