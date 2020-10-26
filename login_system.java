package windowbuilder;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class login_system {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JFrame frmLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login_system window = new login_system();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public login_system() {
		initialize();
	}
		
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(206, 13, 56, 16);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(53, 68, 69, 16);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(53, 121, 69, 16);
		frame.getContentPane().add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(147, 65, 184, 22);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(147, 118, 184, 19);
		frame.getContentPane().add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int flag=0;
				String dbusername=null,dbpassword = null, ip = null, name = null;
				int port;
				@SuppressWarnings("deprecation")
				String password = txtPassword.getText();
				String username = txtUsername.getText();
				try {
					String url = "jdbc:mysql://localhost:3306/logindata";
					String uname = "root";
					String pass = "incorrect12";
					String query = "select * from userlogin";
					
					//Class.forName("Driver");
					Connection con=DriverManager.getConnection(url,uname,pass);
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery(query);
					while((rs.next()))
					{
							dbusername = rs.getString("username");
							dbpassword = rs.getString("password");
							
						
						if(username.contains(dbusername) && password.contains(dbpassword))
						{
							txtPassword.setText(null);
							txtUsername.setText(null);
							ip = rs.getString("hostaddress");
							port = rs.getInt("hostport"); 
							name = rs.getString("username");
							flag=1;
							try {
								//ChatGui windowop = new ChatGui
								ChatGui.show("localhost", port, name);
								//windowop.frame.setVisible(true);
								frame.dispose();
								
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							
							st.close();
							con.close();	
							break;
						}
						
						
					}
					if(flag==0)
					{
						JOptionPane.showMessageDialog(null, "Invalid Login Details", "Login Error", JOptionPane.ERROR_MESSAGE);
						txtPassword.setText(null);
						txtUsername.setText(null);

					}
				}
				catch(Exception ex){
					
				}
				
			}
		});
		btnLogin.setBounds(53, 185, 97, 25);
		frame.getContentPane().add(btnLogin);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText(null);
				txtPassword.setText(null);
				
			}
		});
		btnReset.setBounds(189, 185, 97, 25);
		frame.getContentPane().add(btnReset);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frmLogin = new JFrame("Exit");
				if(JOptionPane.showConfirmDialog(frmLogin, "Confirm if you want to Exit" , "Login Systems",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		
		
		btnExit.setBounds(323, 185, 97, 25);
		frame.getContentPane().add(btnExit);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(53, 40, 367, 2);
		frame.getContentPane().add(separator);
	}
}
