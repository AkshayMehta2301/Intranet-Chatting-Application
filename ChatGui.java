package windowbuilder;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class ChatGui implements Runnable {

	JFrame frame;
	private JTextField texthostAddress;
	private JLabel lblhostPort;
	private JTextField texthostPort;
	private JTextField textusername;
	private JTextArea textchat;
	private JTextArea textonlineUser; 
	private String hostaddress;
	private int hostport;
	private DatagramSocket socket;
	private InetAddress ip;
	private Thread send;
	private Client client;
	static String name;
	private Thread run, listen;
	private OnlineUsers users;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		show("localhost", 5445, "mohit");
	}

	public static void show(String ip, int port, String nam) {
		// TODO Auto-generated method stub
		
		name = nam;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGui window = new ChatGui(ip, port);
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
	private boolean running = false;
	
	public ChatGui(String ip, int port) {
		users = new OnlineUsers();
		hostaddress = ip;
		hostport = 5445;
		client = new Client(name, hostaddress, hostport);
		boolean connect = client.openConnection(hostaddress);
		if (!connect) {
			System.err.println("Connection failed!");
			console("Connection failed!");
		}
		
		initialize();
		String connection = "/c/" +name + "/e/";
		client.send(connection.getBytes());
		
		running = true;
		run = new Thread(this, "Running");
		run.start();
		
	}
	
	
	public void run() {
		listen();
	}

	

	public void listen() {
		listen = new Thread("Listen") {
			public void run() {
				while (running) {
					String message = client.receive();
					if (message.startsWith("/c/")) {
		
						client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
						console("Successfully connected to server! ID: " + client.getID()+ "\n");
					}
					else if (message.startsWith("/m/")) {
						 
							String text = message.substring(3);
							text = text.split("/e/")[0];
							console(text);
					}
					else if (message.startsWith("/i/")) {
							String text = "/i/" + client.getID() + "/e/";
							send(text, false);
					}
					else if (message.startsWith("/u/")) {
							String[] u = message.split("/u/|/n/|/e/");
							users.update(Arrays.copyOfRange(u, 1, u.length - 1));	
						
						}

				}
			}
		};
		listen.start();
	}
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	
	
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				String disconnect = "/d/" + client.getID() + "/e/";
				send(disconnect, false);
				running = false;
				client.close();
			}
		});
		frame.setBounds(400, 500, 950, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblhostAddress = new JLabel("Host Address");
		lblhostAddress.setBounds(73, 44, 79, 16);
		frame.getContentPane().add(lblhostAddress);
		
		texthostAddress = new JTextField();
		texthostAddress.setBounds(164, 41, 235, 22);
		frame.getContentPane().add(texthostAddress);
		texthostAddress.setColumns(10);
		
		lblhostPort = new JLabel("Host Port");
		lblhostPort.setBounds(441, 44, 56, 16);
		frame.getContentPane().add(lblhostPort);
		
		texthostPort = new JTextField();
		texthostPort.setBounds(531, 41, 191, 22);
		frame.getContentPane().add(texthostPort);
		texthostPort.setColumns(10);
		
		JButton btnconnect = new JButton("Connect");
		btnconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnconnect.setBounds(498, 92, 97, 25);
		frame.getContentPane().add(btnconnect);
		
		textusername = new JTextField();
		textusername.setBounds(164, 93, 286, 22);
		frame.getContentPane().add(textusername);
		textusername.setColumns(10);
		
		JLabel lblusername = new JLabel("Username");
		lblusername.setBounds(73, 93, 75, 16);
		frame.getContentPane().add(lblusername);
		
		JButton btndisconnect = new JButton("Disconnect");
		btndisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btndisconnect.setBounds(628, 92, 99, 25);
		frame.getContentPane().add(btndisconnect);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(73, 168, 603, 404);
		frame.getContentPane().add(scrollPane);
		
		textchat = new JTextArea();
		scrollPane.setViewportView(textchat);
		textchat.setEditable(false);
		textchat.setLineWrap(true);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(169, 330, 4, 22);
		frame.getContentPane().add(textArea);
		
		textonlineUser = new JTextArea();
		textonlineUser.setEditable(false);
		textonlineUser.setBounds(695, 206, 197, 366);
		frame.getContentPane().add(textonlineUser);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(73, 599, 487, 31);
		frame.getContentPane().add(scrollPane_1);
		
		JTextArea textmessage = new JTextArea();
		scrollPane_1.setViewportView(textmessage);
		textmessage.setFont(new Font("Monospaced", Font.PLAIN, 15));
		
		JButton btnsend = new JButton("Send");
		btnsend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send(textmessage.getText(), true);
				textmessage.setText(null);
			}
		});
		btnsend.setBounds(584, 598, 97, 34);
		frame.getContentPane().add(btnsend);
		
		JSeparator separator = new JSeparator();	
		separator.setBounds(73, 654, 819, 2);
		frame.getContentPane().add(separator);
		
		JButton btnOnline = new JButton("Online");
		btnOnline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				users.setVisible(true);
			}
		});
		btnOnline.setBounds(699, 168, 193, 25);
		frame.getContentPane().add(btnOnline);
	}
	
	private void send(String message, boolean text) {
		if (message.equals(""))
			return;
		if (text) {
			message = client.getName() + ":  " + message + "\n";
			message = "/m/" + message + "/e/";
		}
		
		client.send(message.getBytes());
		
	}
	
	public void console(String message){
		textchat.append(message+ "\r");
	}
}
