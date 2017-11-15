package calendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.*;
import javax.swing.border.Border;



/**
 * 
 * @author Stanislav Yanakiev
 *
 */
public class CalendarComponent extends Component
{
	private JFrame frame;
	private JPanel monthViewPanel;
	private JPanel topPanel;
	private JPanel dayViewPanel;
	private JTextArea dayViewDate;
	private JTextArea monthView;
	private JTextArea dayEventView;
	private JTextField eventName;
	private JTextField eventStartTime;
	private JTextField eventEndTime;
	private JTextField eventDate;
	
	
	private CalendarModel model;
	private GregorianCalendar cal;
	private MONTHS[] arrayOfMonths = MONTHS.values();
	private SHORTMONTHS[] arrayOfShortMonths = SHORTMONTHS.values();
	private DAYS[] arrayOfDays = DAYS.values();
	private LONGDAYS[] arrayOfLongDays = LONGDAYS.values();
	
	
	/**
	 * Creates the main frame of the calendar and initializes all components
	 */
	public CalendarComponent(CalendarModel model)
	{
		cal = model.getCalendar();
		this.model = model;
		model.load();
		
		frame = new JFrame("Simple Calendar");
		frame.setSize(600, 300);
		frame.setLayout(new BorderLayout(20,5));
		frame.getContentPane().setBackground(Color.WHITE);
		
		initializeTopPanel();
		initializeMonthViewPanel();
		initializeDayViewPanel();


		
		//frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initializeTopPanel()
	{
		topPanel = new JPanel();
		GregorianCalendar changedCal = cal;
		JButton backButton = new JButton("<");
		backButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						System.out.println("Previous Button clicked");
						
						//model.getCalendar().add(Calendar.DAY_OF_MONTH, -1);
						cal.add(Calendar.DAY_OF_MONTH, -1);
						if(changedCal.MONTH != cal.MONTH)
						{
							cal.add(Calendar.MONTH, -1);
						}
						
						String day = model.getDateDescription(cal);
						dayViewDate.setText(day);
						dayEventView.setText(model.printDayEvents(model.getCalendar()));
						//updateMonthView();
						updateMonthView();
						repaint();
					}
				});
		JButton nextButton = new JButton(">");
		nextButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						System.out.println("Next Button clicked");
						
						//model.getCalendar().add(Calendar.DAY_OF_MONTH, 1);
						cal.add(Calendar.DAY_OF_MONTH, 1);
						if(changedCal.MONTH != cal.MONTH)
						{
							cal.add(Calendar.MONTH, 1);
						}
						
						String day = model.getDateDescription(cal);
						dayViewDate.setText(day);
						dayEventView.setText(model.printDayEvents(model.getCalendar()));
						updateMonthView();
						repaint();
					}
				});
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						System.out.println("Quit Button clicked");
						model.quit();
						frame.dispose();
					}
				});
		
		topPanel.add(backButton);
		topPanel.add(nextButton);
		topPanel.add(quitButton);
		
		frame.add(topPanel, BorderLayout.NORTH);
		
	}
	public void initializeMonthViewPanel()
	{
		monthViewPanel = new JPanel();
		monthViewPanel.setSize(50, 150);
		monthViewPanel.setLayout(new BorderLayout());
		monthViewPanel.setBackground(Color.BLUE);
		initializeCreateButton();
		initializeMonthView();
		
		
		frame.add(monthViewPanel, BorderLayout.WEST);
	}
	
	public void initializeCreateButton()
	{
		JButton createButton = new JButton("Create");
		createButton.setBackground(Color.RED);
		createButton.setForeground(Color.WHITE);
		createButton.setOpaque(true);
		createButton.setSize(20,20);
		createButton.setBorderPainted(false);
		//createButton.add
		
		createButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						initializeCreateDialog();
					}
				});
			
		
		monthViewPanel.add(createButton, BorderLayout.NORTH);
	}
	
	public void initializeCreateDialog() 
	{
		System.out.println("Create Button clicked");
		JDialog eventDialog = new JDialog();
		eventDialog.setLayout(new BorderLayout(20, 20));
		eventDialog.setSize(450, 100);
		eventDialog.setTitle("Create event");
		JPanel textFieldPanel = new JPanel();

		// Event Name
		eventName = new JTextField();
		eventName.setText("Untitled Event");
		Border eventNameBorder = BorderFactory.createLineBorder(Color.BLUE, 1);
		eventName.setBorder(eventNameBorder);

		// Event Date
		eventDate = new JTextField();
		eventDate.setText(model.getDateDescription2(cal));
		Border eventDateBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		eventDate.setBorder(eventDateBorder);
		eventDate.setEditable(false);

		// EventStartTime
		 eventStartTime = new JTextField();
		String time = cal.get(Calendar.HOUR_OF_DAY) + ":";
		int minute = cal.get(Calendar.MINUTE);
		if (minute < 10) {
			time += "0" + minute;
		} else
			time += cal.get(Calendar.MINUTE);

		eventStartTime.setText(time);
		eventStartTime.setBorder(eventDateBorder);

		// EventEndTime
		eventEndTime = new JTextField();
		eventEndTime.setText("23:59");
		eventEndTime.setBorder(eventDateBorder);

		// Save
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String nameString = eventName.getText();
						String dateString = eventDate.getText();
						String startString = eventStartTime.getText();
						String endString = eventEndTime.getText();
						model.create(nameString, dateString, startString, endString);
						
					}
				});

		// Adds The Text Fields
		textFieldPanel.add(eventDate);
		textFieldPanel.add(eventStartTime);
		textFieldPanel.add(eventEndTime);
		textFieldPanel.add(saveButton);
		// Add All Components to the JDialog
		eventDialog.add(eventName, BorderLayout.NORTH);
		eventDialog.add(textFieldPanel, BorderLayout.CENTER);

		// eventDialog.pack();
		eventDialog.setVisible(true);
	}
	
	public void initializeMonthView()
	{
		JPanel monthCalendarPanel = new JPanel();
		monthCalendarPanel.setLayout(new BorderLayout());

		monthView = new JTextArea();
		// Prints the monthView
		String MonthDateFormat = "";
		MonthDateFormat += arrayOfMonths[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR) + "\n";
		MonthDateFormat += arrayOfDays[0];
		for (int i = 1; i < arrayOfDays.length; i++) {
			MonthDateFormat += String.format("%" + 5 + "s", " ") + arrayOfDays[i];
		}
		monthView.setText(MonthDateFormat);

		//
		GregorianCalendar temp = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		int firstDayOfWeek = temp.get(Calendar.DAY_OF_WEEK);
		int maxDayOfMonth = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
		int i = 1; // The Day of the month
		
		JPanel monthDayPanel = new JPanel();
		monthDayPanel.setLayout(new GridLayout(0,7,9,0));
		monthDayPanel.setBackground(Color.WHITE);
		monthDayPanel.setOpaque(true);
		monthDayPanel.removeAll();

		
        
		for (int j = 1; j < maxDayOfMonth + firstDayOfWeek; j++) {
			JLabel dateLabel = new JLabel();
			dateLabel.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					String temp = dateLabel.getText().replaceAll(" ", "");
					if(temp.length() == 0)
					{
						
					}
					else
					{
						int day = Integer.parseInt(temp);
						System.out.println(day);
						cal.set(Calendar.DAY_OF_MONTH, day);
						dayViewDate.setText(model.getDateDescription(cal));
						dayEventView.setText(model.printDayEvents(model.getCalendar()));
						updateMonthView();
						repaint();
					}
					
					
				}
			});
			
			Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		
			temp.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), i);
			if (j == firstDayOfWeek) 
			{
				if (model.isTheDay(cal, temp) == true) 
				{
					dateLabel.setText("  " + i);
					dateLabel.setBorder(border);
					dateLabel.setBackground(Color.LIGHT_GRAY);
					dateLabel.setOpaque(true);
					i++;
				} else 
				{
					dateLabel.setText(Integer.toString(i));
					System.out.println(i + "hi");
					i++;
				}
			} else if (j > firstDayOfWeek) 
			{
				if (i >= 10) {
					if (model.isTheDay(cal, temp) == true) 
					{
						dateLabel.setText(" " + i);
						dateLabel.setBorder(border);
						dateLabel.setBackground(Color.LIGHT_GRAY);
						dateLabel.setOpaque(true);
						i++;
					} else 
					{
						dateLabel.setText(" " + i);
						i++;
					}
				} else if (model.isTheDay(cal, temp) == true) 
				{
					dateLabel.setText("  " + Integer.toString(i) );
					dateLabel.setBorder(border);
					dateLabel.setBackground(Color.LIGHT_GRAY);
					dateLabel.setOpaque(true);
					i++;
				} else 
				{
					dateLabel.setText(Integer.toString(i));
					i++;
				}
			} else
			{
				// System.out.print(" ");
				dateLabel.setText("");
			}
			monthDayPanel.add(dateLabel);
			
		}
		
		

		// monthView.setText(model.calendarView(cal));
		monthView.setEditable(false);
		
		
		monthCalendarPanel.add(monthView, BorderLayout.NORTH);
		monthCalendarPanel.add(monthDayPanel, BorderLayout.CENTER);
		
		monthViewPanel.add(monthCalendarPanel, BorderLayout.CENTER);
	}

	
	public void initializeDayViewPanel()
	{
		dayViewPanel = new JPanel();
		dayViewPanel.setLayout(new BorderLayout(0, 20));
		dayViewPanel.setBackground(Color.WHITE);
		
		

		// Date Shown
		String today = model.getDateDescription(cal);
		dayViewDate = new JTextArea();
		dayViewDate.setText(today);
		dayViewDate.setEditable(false);
		Border greyBoarder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		dayViewDate.setBorder(greyBoarder);
		
		
	
		
		//Prints the event for current day
		//dayEventView = new JTextArea(model.printAllEvents());
		dayEventView = new JTextArea(model.printDayEvents(model.getCalendar()));
		dayEventView.setEditable(false);
		dayEventView.setBorder(greyBoarder);
		
		
		
		dayViewPanel.add(dayViewDate, BorderLayout.NORTH);
		dayViewPanel.add(dayEventView, BorderLayout.CENTER);
		frame.add(dayViewPanel, BorderLayout.CENTER);
	}
	
	public void updateMonthView()
	{
		//monthView.setText(model.calendarView(cal));
		initializeMonthView();
	}
}
