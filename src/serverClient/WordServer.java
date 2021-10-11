package serverClient;
import java.io.*;
import java.net.*;
import java.nio.*;

import serverClient.WordService;


public class WordServer {
private static int clientNumber = 0;
static String filePathString = "";
static int port = 0;
static String portString = "";
protected Socket socket;



public static void main(String[] args) throws Exception{
	try {
		portString = args[0];
		filePathString = args[1];
		port = Integer.parseInt(portString);
		
	} catch (Exception e) {
		System.out.println("It happens!(Args error)");// TODO: handle exception
	}
	System.out.println("Word Server is running...");
	try {
		ServerSocket severSocket = new ServerSocket(port);
		System.out.println("server socket created...");
		String ip = severSocket.getInetAddress().getHostAddress();
		System.out.println("Host address is: " + ip);
		while (true) {
			Socket socket = severSocket.accept();
			String cip = socket.getInetAddress().getHostAddress();
			socket.setSoTimeout(14000);
			clientNumber ++;
			System.out.println("Client " + clientNumber +":"+cip +" is connecting...");
			Mythread thread = new Mythread();
			thread.getSocket(socket);
			thread.getPath(filePathString);
			thread.start();
			
		}
		
	} catch (Exception e) {
		System.out.println("It happens!(Connect error)");// TODO: handle exception
	}
	
}
}
class Mythread extends Thread {
	Socket socket = new Socket();
	String pathString = "";
	WordService wordService  = new WordService();
	public void getSocket(Socket socket) {
		this.socket = socket;
		
	}
	public void getPath(String filename) {
		this.pathString = filename;
	}
	public void run() {
		try {
			String result = "parse excution fail...";
			BufferedReader reader = new BufferedReader(
			new InputStreamReader(socket.getInputStream()));
			//read the message from client and parse the execution
			String line = reader.readLine();
			System.out.println("line is:"+line+", path is:" + pathString);
			result = parseExcution(line,pathString);
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			writer.write(" " + result);
			writer.newLine();
			writer.flush();
			System.out.println("result send:"+ result);
			//close the stream 
			reader.close(); 
			writer.close();
					
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
	}
	private String parseExcution (String line, String pathString) throws Exception {
		String result = "";
		String command;
		String word;
		String meaning;

		String [] elements = line.split(" ");
		System.out.println("Command received...");
		if (elements.length != 2) {
			if (elements.length == 3) {
				command = elements[0];
				word = elements[1];
				meaning = elements[2];
				System.out.println("Command is:"+ command + ", word is:"+ word + ", meaning is:" + meaning);
				if (command.equalsIgnoreCase("add")){
					synchronized (wordService) {
						result = wordService.addword(word, meaning, pathString);
					}
				
				}
				else {
					result = "Command error...";
				}
				
			}
			else {
				result = "Command error..";
				System.out.println(result);
			}
		}
		else {
		command = elements[0];
		word = elements[1];
		
		if (command.equalsIgnoreCase("search")) {
			synchronized(wordService) {
				result = wordService.searchword(word, pathString);
			}
		}
		else if (command.equalsIgnoreCase("delete")) {
			synchronized (wordService) {
				result = wordService.deleteword(word, pathString);
			}
		}
		else {
			result = "Unknown command...";
			System.out.println("Unknown command...");
		}
		}
		
		return result;
	}
}
