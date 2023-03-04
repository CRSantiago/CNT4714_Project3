import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SQL_GUI extends JFrame implements ActionListener {
	
	JComboBox fileComboBox;
	JTextField usernameTextField;
	JTextField passwordTextField;
	
	JButton connectToDBbutton;
	
	JTextField commandTextField;
	JButton clearCommandButton;
	JButton executeCommandButton;
	
	JTextField statusTextField;
	
	JButton clearResultsButton;
	
	SQL_GUI(){
		this.setTitle("SQL Client App");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // exit out of application
		this.setSize(800,600);
		this.setResizable(false);
		
		
		JPanel northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(800,250));
		northPanel.setBackground(Color.red);
		northPanel.setLayout(new BorderLayout());
		
		JPanel connectionPanel = new JPanel();
		connectionPanel.setLayout(null);
		connectionPanel.setPreferredSize(new Dimension(400,250));
		JLabel connectionTitle = new JLabel("Connection Details");
		connectionTitle.setOpaque(true);
		connectionTitle.setForeground(Color.BLUE);
		connectionTitle.setBounds(10,10,180,20);
		connectionTitle.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		
		JLabel fileLabel = new JLabel("Properties File");
		fileLabel.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		fileLabel.setForeground(Color.BLACK);
		fileLabel.setBounds(10,40,125,30);
		fileLabel.setOpaque(true);
		fileLabel.setBackground(Color.LIGHT_GRAY);
		fileLabel.setBorder(new EmptyBorder(0,10,0,0));
		
		String[] fileOptions = {"root.properties", "client.properties"};
		fileComboBox = new JComboBox(fileOptions);
		fileComboBox.setBackground(Color.white);
		fileComboBox.setBounds(140,42,225,25);
		fileComboBox.addActionListener(this);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		usernameLabel.setForeground(Color.black);
		usernameLabel.setBounds(10,80,125,30);
		usernameLabel.setOpaque(true);
		usernameLabel.setBackground(Color.LIGHT_GRAY);
		usernameLabel.setBorder(new EmptyBorder(0,10,0,0));
		
		usernameTextField = new JTextField();
		usernameTextField.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		usernameTextField.setBackground(Color.white);
		usernameTextField.setBounds(140,82,225,25);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		passwordLabel.setForeground(Color.BLACK);
		passwordLabel.setBounds(10,120,125,30);
		passwordLabel.setOpaque(true);
		passwordLabel.setBackground(Color.LIGHT_GRAY);
		passwordLabel.setBorder(new EmptyBorder(0,10,0,0));
		
		passwordTextField = new JTextField();
		passwordTextField.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		passwordTextField.setBackground(Color.white);
		passwordTextField.setBounds(140,122,225,25);
		
		connectToDBbutton = new JButton("Connect to Database");
		connectToDBbutton.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		connectToDBbutton.setForeground(Color.YELLOW);
		connectToDBbutton.setBounds(20,180,175,30);
		connectToDBbutton.setOpaque(true);
		connectToDBbutton.setBackground(Color.BLUE);
		connectToDBbutton.addActionListener(this);
		
		
		connectionPanel.add(connectionTitle);
		connectionPanel.add(fileLabel);
		connectionPanel.add(fileComboBox);
		connectionPanel.add(usernameLabel);
		connectionPanel.add(usernameTextField);
		connectionPanel.add(passwordLabel);
		connectionPanel.add(passwordTextField);
		connectionPanel.add(connectToDBbutton);
		
		JPanel commandPanel = new JPanel();
		commandPanel.setLayout(null);
		commandPanel.setPreferredSize(new Dimension(400,250));
//		commandPanel.setBackground(Color.darkGray);
		
		JLabel commandTitle = new JLabel("Enter An SQL Command");
		commandTitle.setOpaque(true);
		commandTitle.setForeground(Color.BLUE);
		commandTitle.setBounds(20,5,200,20);
		commandTitle.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		
		commandTextField = new JTextField();
		commandTextField.setBounds(20, 25, 370, 150);
		
		clearCommandButton = new JButton("Clear SQL Command");
		clearCommandButton.setBounds(20, 180, 170, 30);
		clearCommandButton.setForeground(Color.red);
		clearCommandButton.setBackground(Color.white);
		clearCommandButton.addActionListener(this);
		
		executeCommandButton = new JButton("Execute SQL Command");
		executeCommandButton.setBounds(215, 180, 170, 30);
		executeCommandButton.setForeground(Color.black);
		executeCommandButton.setBackground(Color.green);
		executeCommandButton.addActionListener(this);
		
		commandPanel.add(commandTitle);
		commandPanel.add(commandTextField);
		commandPanel.add(clearCommandButton);
		commandPanel.add(executeCommandButton);
		
		JPanel statusPanel = new JPanel();
		statusPanel.setPreferredSize(new Dimension(800,30));
		statusPanel.setBorder(new EmptyBorder(0,0,20,0));
//		statusPanel.setBackground(Color.yellow);
		
		statusTextField = new JTextField();
		statusTextField.setText("No Connection");
		statusTextField.setEditable(false);
		statusTextField.setPreferredSize(new Dimension(750,30));
		statusTextField.setOpaque(true);
		statusTextField.setBackground(Color.BLACK);
		statusTextField.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		statusTextField.setForeground(Color.red);
		statusPanel.add(statusTextField);
		
		northPanel.add(connectionPanel, BorderLayout.WEST);
		northPanel.add(commandPanel, BorderLayout.EAST);
		northPanel.add(statusPanel, BorderLayout.SOUTH);
		
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(800,350));
//		southPanel.setBackground(Color.GRAY);
		southPanel.setLayout(null);
		
		JLabel southPanelTitle = new JLabel("SQL Execution Result Window");
		southPanelTitle.setOpaque(true);
		southPanelTitle.setForeground(Color.BLUE);
		southPanelTitle.setBounds(20,40,300,20);
		southPanelTitle.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		
		JTextField resultTextField = new JTextField();
		resultTextField.setEditable(false);
		resultTextField.setBounds(20, 60, 750, 250);
		
		clearResultsButton = new JButton("Clear Result Window");
		clearResultsButton.setBounds(10, 315, 170, 30);
		clearResultsButton.setForeground(Color.black);
		clearResultsButton.setBackground(Color.yellow);
		clearResultsButton.addActionListener(this);
		
		southPanel.add(southPanelTitle);
		southPanel.add(resultTextField);
		southPanel.add(clearResultsButton);
		
		
		this.add(northPanel,BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	@Override 
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fileComboBox) {
			System.out.println(fileComboBox.getSelectedItem());
		}
		
		if(e.getSource() == connectToDBbutton) {
			System.out.println("try to connect to db");
		}
		
		if(e.getSource() == clearCommandButton) {
			System.out.println("clear ommand button pressed!");
		} 
		
		if(e.getSource() == executeCommandButton) {
			System.out.println("Eexecute command button presses!");
		}
		
		if(e.getSource() == clearResultsButton) {
			System.out.println("Clear Result button pressed!");
		}
	}
}

