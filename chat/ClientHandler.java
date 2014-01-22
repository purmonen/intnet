package chat;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler implements Runnable {
	private static ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
	private PrintWriter out;
	private BufferedReader in;
	
	public ClientHandler(Socket clientSocket) throws IOException {
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	
		clientHandlers.add(this);
	}


	@Override
	public void run() {
		String line;
		
		try {
			while ((line = in.readLine()) != null) {
				for (ClientHandler clientHandler : clientHandlers) {
					if (clientHandler != this) {
						clientHandler.out.println(line);
					}
				}
			}
			clientHandlers.remove(this);
		} catch(IOException e) {}
		//clientHandlers.remove(this);		
	}
}
