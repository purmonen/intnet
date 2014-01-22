package chat;

import java.io.IOException;
import java.net.*;

public class ChatServer {
	public static void main(String[] args) {
		int port = args.length > 0 ? Integer.parseInt(args[0]) : 1337;
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				new Thread(new ClientHandler(clientSocket)).start();
			}
		} catch(IOException e) {
			System.out.println(e);
		}
	}
}
