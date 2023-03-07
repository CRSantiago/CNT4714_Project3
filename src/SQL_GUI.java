import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SQL_GUI extends JFrame implements ActionListener {
	
	JComboBox fileComboBox;
	JTextField usernameTextField;
	JPasswordField passwordField;
	
	JButton connectToDBbutton;
	
	JTextArea commandTextArea;
	JButton clearCommandButton;
	JButton executeCommandButton;
	
	JTextField statusTextField;
	
	JButton clearResultsButton;
	
	String fileName;
	
	JTable resultTable;
	
	Connection connection;
	MysqlDataSource dataSource = null;
	boolean connectionEstablished = false;
	DefaultTableModel tableModel;
	
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
		
		passwordField = new JPasswordField(15);
		passwordField.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		passwordField.setBackground(Color.white);
		passwordField.setBounds(140,122,225,25);
		
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
		connectionPanel.add(passwordField);
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
		
		commandTextArea = new JTextArea();
		commandTextArea.setBounds(20, 25, 370, 150);
		commandTextArea.setCaretPosition(0);
		
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
		commandPanel.add(commandTextArea);
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
		
		resultTable = new JTable();
//		resultTable.setBounds(20, 60, 750, 250);
		
		JScrollPane scrollPane = new JScrollPane(resultTable);
		scrollPane.setBounds(20, 60, 750, 250);
		
		clearResultsButton = new JButton("Clear Result Window");
		clearResultsButton.setBounds(10, 315, 170, 30);
		clearResultsButton.setForeground(Color.black);
		clearResultsButton.setBackground(Color.yellow);
		clearResultsButton.addActionListener(this);
		
		southPanel.add(southPanelTitle);
		southPanel.add(scrollPane);
		southPanel.add(clearResultsButton);
		
		
		this.add(northPanel,BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	@Override 
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fileComboBox) {
			fileName = (String) fileComboBox.getSelectedItem();
//			System.out.println(fileName);
		}
		
		if(e.getSource() == connectToDBbutton) {	
			Properties properties = new Properties();
			FileInputStream filein = null;
//			MysqlDataSource dataSource = null;
			boolean usernameMatch;
			boolean passwordMatch;
			
				try {
					//set property and file object
					filein = new FileInputStream("src/" + fileName);
					properties.load(filein);
					// get password from passwordField
					String password = new String(passwordField.getPassword()); // password field return char[] instead os String
					// test username and password with properties fields
					usernameMatch = usernameTextField.getText().equals( (String) properties.getProperty("MYSQL_DB_USERNAME"));
					passwordMatch = password.equals(properties.getProperty("MYSQL_DB_PASSWORD"));
					//if match found - set data source object to allow for database connection
					if (usernameMatch && passwordMatch) {
						dataSource = new MysqlDataSource();
						dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
						dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
						dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
						
						try {
							//establish a connection to the dataSource - the database
							Connection connection = dataSource.getConnection();
							//set status to connected by changing field properties
							statusTextField.setText("Connected To: " + properties.getProperty("MYSQL_DB_URL"));
							statusTextField.setForeground(Color.YELLOW);
							connectionEstablished = true;
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						//inform user of unsuccessful connection
						statusTextField.setText("Not Connected - User Credentials Do Not Match Properties Files!");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			
		}
		
		if(e.getSource() == clearCommandButton) {
			System.out.println("clear command button pressed!");
			commandTextArea.setText("");
		} 
		
		if(e.getSource() == executeCommandButton) {
			System.out.println("Execute command button presses!");
//			Properties properties = new Properties();
//			FileInputStream filein = null;
//			MysqlDataSource dataSource = null;
//			boolean usernameMatch;
//			boolean passwordMatch;
//			
//				try {
//					//set property and file object
//					filein = new FileInputStream("src/" + fileName);
//					properties.load(filein);
//					// get password from passwordField
//					String password = new String(passwordField.getPassword()); // password field return char[] instead os String
//					// test username and password with properties fields
//					usernameMatch = usernameTextField.getText().equals( (String) properties.getProperty("MYSQL_DB_USERNAME"));
//					passwordMatch = password.equals(properties.getProperty("MYSQL_DB_PASSWORD"));
//					if (usernameMatch && passwordMatch) {
//						dataSource = new MysqlDataSource();
//						dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
//						dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
//						dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
				if(connectionEstablished) {
						try {
							Connection connection = dataSource.getConnection();
							 // Execute SQL query and store result set
				            Statement stmt = connection.createStatement();
				            ResultSet rs;
				            int numRowsUpdated;
				            if(commandTextArea.getText().toLowerCase().contains("select")) {
				            	try {
					            	rs = stmt.executeQuery(commandTextArea.getText());
					            	 /// Create a DefaultTableModel to hold the data
						            tableModel = new DefaultTableModel();
	
							         // Get the column count from the result set
							         int columnCount = rs.getMetaData().getColumnCount();
			
							         // Add column names to the table model
							         for (int i = 1; i <= columnCount; i++) {
							             tableModel.addColumn(rs.getMetaData().getColumnName(i));
							         }
	
							         // Add rows to the table model
							         while (rs.next()) {
							             Object[] row = new Object[columnCount];
							             for (int i = 1; i <= columnCount; i++) {
							                 row[i-1] = rs.getObject(i);
							             }
							             tableModel.addRow(row);
							         }
	
						            // Set the table model as the model for the JTable
						            resultTable.setModel(tableModel);
				            	} catch (SQLException e1) {
				            		JOptionPane.showMessageDialog(this, e1.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE, null);
								}
				            } else {
				            	try {
					            	numRowsUpdated = stmt.executeUpdate(commandTextArea.getText());
					            	JOptionPane.showMessageDialog(this, "Successful Update.." + numRowsUpdated + " rows updated.");
				            	} catch (SQLException e1) {
									JOptionPane.showMessageDialog(this, e1.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE, null);
								}
				            }

				           
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(this, e1.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE, null);
						}
					} else {
						statusTextField.setText("Not Connected - User Credentials Do Not Match Properties Files!");
					}
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
			System.out.println(commandTextArea.getText());
		//}
		}
		
		if(e.getSource() == clearResultsButton) {
			System.out.println("Clear Result button pressed!");
			tableModel.setColumnCount(0);
			tableModel.setRowCount(0);
		}
	}
}

