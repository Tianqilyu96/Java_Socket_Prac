package Gui;

import java.io.*;
import java.net.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;



import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class userinterface {
	
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
		static String ip = "";
		static int port = 0;		
	public static void main(String[] args) {
		try {
			ip = args[0];
			port = Integer.parseInt(args[1]);
		} catch (Exception e) {
			System.out.println("illeagle arg...");
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userinterface window = new userinterface();
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
	public userinterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(138, 27, 230, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		

		
		JLabel lblNewLabel = new JLabel("Command:");
		lblNewLabel.setBounds(22, 32, 91, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("GET RESULT");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String command = textField.getText();
					String result = clientfunction(ip, port, command);
					textField_1.setText(result);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnNewButton_1.setBounds(153, 106, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(138, 173, 230, 52);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Result:");
		lblNewLabel_1.setBounds(22, 191, 77, 16);
		frame.getContentPane().add(lblNewLabel_1);
	}



public static String clientfunction(String ip,int port, String message) {
	String result = "";
	try {
		Socket socket = new Socket(ip, port);
		System.out.println("socket create...");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write(message);
		bw.newLine();
		bw.flush();
		System.out.println(message + "is send");
		try {
			Thread.currentThread();
			Thread.sleep(300);
		} catch (Exception e) {
			System.out.println("Thread error...");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		result = br.readLine();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return result;
}
}
