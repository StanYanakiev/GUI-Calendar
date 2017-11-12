package calendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;


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
	private CalendarModel model;
	private JTextArea dayViewDate;
	private GregorianCalendar cal;
	private JTextArea monthView;
	
	/**
	 * Creates the main frame of the calendar and initializes all components
	 */
	public CalendarComponent(CalendarModel model)
	{
		cal = model.getCalendar();
		this.model = model;
		frame = new JFrame("Simple Calendar");
		frame.setSize(400, 400);
		frame.setLayout(new BorderLayout());
		initializeTopPanel();
		initializeMonthViewPanel();
		initializeDayViewPanel();
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initializeTopPanel()
	{
		topPanel = new JPanel();
		
		JButton backButton = new JButton("<");
		backButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						System.out.println("Previous Button clicked");
						
						
						cal.add(Calendar.DAY_OF_MONTH, -1);
						String day = model.getDateDescription(cal);
						dayViewDate.setText(day);
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
						
						
						cal.add(Calendar.DAY_OF_MONTH, 1);
						String day = model.getDateDescription(cal);
						dayViewDate.setText(day);
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
						System.out.println("Create Button clicked");
					}
				});
			
		
		monthViewPanel.add(createButton, BorderLayout.NORTH);
	}
	
	public void initializeMonthView()
	{
		monthView = new JTextArea();
		monthView.setText(model.calendarView(cal));
		monthView.setEditable(false);
		
		
		monthViewPanel.add(monthView, BorderLayout.CENTER);
	}
	
	public void initializeDayViewPanel()
	{
		dayViewPanel = new JPanel();

	
		String today = model.getDateDescription(cal);
		dayViewDate = new JTextArea(today);
		//dayViewDate.setText(today);
		dayViewPanel.add(dayViewDate);
		frame.add(dayViewPanel, BorderLayout.CENTER);
	}
	
	public void updateMonthView()
	{
		monthView.setText(model.calendarView(cal));
	}
}
