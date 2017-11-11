package calendar;

import java.awt.*;
import java.util.GregorianCalendar;

import javax.swing.*;


public class CalendarComponent extends Component
{
	private JFrame frame;
	private JPanel monthViewPanel;
	private JPanel topPanel;
	private CalendarModel model;
	private GregorianCalendar cal;
	
	public CalendarComponent()
	{
		cal = new GregorianCalendar();
		model = new CalendarModel(cal);
		model.load();
		frame = new JFrame("Simple Calendar");
		frame.setSize(400, 400);
		frame.setLayout(new BorderLayout());
		initializeTopPanel();
		initializeMonthViewPanel();
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initializeTopPanel()
	{
		topPanel = new JPanel();
		JButton backButton = new JButton("<");
		JButton nextButton = new JButton(">");
		JButton quitButton = new JButton("Quit");
		
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
		monthViewPanel.add(createButton, BorderLayout.NORTH);
		
	}
	
	public void initializeMonthView()
	{
		JTextArea monthView = new JTextArea();
		monthView.setText(model.calendarView(cal));
		monthView.setEditable(false);
		
		
		monthViewPanel.add(monthView, BorderLayout.CENTER);
	}
}
