package chat;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.lang.Thread;


public class ChatClient {
	
	private Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;
	private BufferedReader stdin;
	
	public ChatClient(InetAddress host, int port) {
		try {
			clientSocket = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			stdin = new BufferedReader(new InputStreamReader(System.in));
			listenToServer();
			listenToStdin();
			in.close();
			out.close();
			stdin.close();
			clientSocket.close();
		} catch(IOException e) {}
	}

	private void listenToServer() {
		new Thread(new Runnable() {
			public void run()  {
				String line;
				try {
					while ((line = in.readLine()) != null) {
						System.out.println(line);
					}
					System.out.println("FINISHED");
				} catch(IOException e) {}
			}
		}).start();
	}

	private void listenToStdin() {
		String line;
		try {
			while ((line = stdin.readLine()) != null) {
				out.println(line);
			}
		} catch(IOException e) {} 
	}
	
	public static void main(String[] args) {
		int port = args.length > 1 ? Integer.parseInt(args[1]) : 1337;
		try {
			InetAddress host = InetAddress.getByName(args.length > 0 ? args[0] : "localhost");
			new ChatClient(host, port);
		} catch(UnknownHostException e) {}
	}
}
