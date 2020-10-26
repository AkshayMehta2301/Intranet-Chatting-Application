package windowbuilder;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class signup {

	private JFrame frame;
	private JTextField textempno;
	private JTextField texthostAddress;
	private JTextField texthostPort;
	private JTextField textusername;
	private JTextField textpassword;
	private JFrame frmLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signup window = new signup();
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
	public signup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 350, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSignUp = new JLabel("Create Account");
		lblSignUp.setBounds(123, 26, 90, 17);
		frame.getContentPane().add(lblSignUp);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(29, 55, 277, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblempno = new JLabel("Employee Number");
		lblempno.setBounds(40, 83, 104, 16);
		frame.getContentPane().add(lblempno);
		
		JLabel lblhostAddress = new JLabel("Host Address");
		lblhostAddress.setBounds(40, 141, 104, 16);
		frame.getContentPane().add(lblhostAddress);
		
		JLabel lblhostPort = new JLabel("Host Port");
		lblhostPort.setBounds(40, 192, 56, 16);
		frame.getContentPane().add(lblhostPort);
		
		JLabel lblusername = new JLabel("Username");
		lblusername.setBounds(40, 246, 75, 16);
		frame.getContentPane().add(lblusername);
		
		JLabel lblpassword = new JLabel("Password");
		lblpassword.setBounds(40, 298, 66, 16);
		frame.getContentPane().add(lblpassword);
		
		textempno = new JTextField();
		textempno.setToolTipText("");
		textempno.setBounds(40, 99, 252, 22);
		frame.getContentPane().add(textempno);
		textempno.setColumns(10);
		
		texthostAddress = new JTextField();
		texthostAddress.setBounds(40, 157, 252, 22);
		frame.getContentPane().add(texthostAddress);
		texthostAddress.setColumns(10);
		
		texthostPort = new JTextField();
		texthostPort.setBounds(40, 211, 252, 22);
		frame.getContentPane().add(texthostPort);
		texthostPort.setColumns(10);
		
		textusername = new JTextField();
		textusername.setBounds(40, 263, 252, 22);
		frame.getContentPane().add(textusername);
		textusername.setColumns(10);
		
		textpassword = new JTextField();
		textpassword.setBounds(40, 316, 252, 22);
		frame.getContentPane().add(textpassword);
		textpassword.setColumns(10);
		
		JButton btnsignup = new JButton("Sign Up");
		btnsignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String hostaddress = texthostAddress.getText();
				String hostport = texthostPort.getText();
				String username = textusername.getText();
				String password = textpassword.getText();
				int empno = Integer.parseInt(textempno.getText());
				
				try {
						String url = "jdbc:mysql://localhost:3306/logindata";
						String uname = "root";
						String pass = "incorrect12";
						String query = "insert into userlogin values(?, ?, ?, ?, ?);";
						
						//Class.forName("Driver");
						Connection con = DriverManager.getConnection(url,uname,pass);
						PreparedStatement st = con.prepareStatement(query);
						st.setInt(1, empno);
						st.setString(2, hostaddress);
						st.setString(3, hostport);
						st.setString(4, username);
						st.setString(5, password);
						
						int count = st.executeUpdate();
						
						if(count>=1)
						{
							frmLogin = new JFrame("Exit");
							JOptionPane.showMessageDialog(frmLogin, "Signed Up");
							
							texthostAddress.setText(null);
							texthostPort.setText(null);
						    textusername.setText(null);
							textpassword.setText(null);
							textempno.setText(null);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Invalid Fields", "Signup Error", JOptionPane.ERROR_MESSAGE);
						}
						
						st.close();
						con.close();
					}
				catch(Exception e){
					
				}
				
			}
		});
		btnsignup.setBounds(40, 371, 128, 25);
		frame.getContentPane().add(btnsignup);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmLogin = new JFrame("Exit");
				if(JOptionPane.showConfirmDialog(frmLogin, "Confirm if you want to Exit" , "SignUp System",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setBounds(178, 371, 114, 25);
		frame.getContentPane().add(btnExit);
	}
}
