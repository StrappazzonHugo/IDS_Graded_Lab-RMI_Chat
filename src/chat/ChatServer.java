package chat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class ChatServer {
	/**
	 * Main method who only catch a "/exit" type on the console. 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Create a remote object
			ChatImpl c = new ChatImpl ();
			Chat c_stub = (Chat) UnicastRemoteObject.exportObject(c, 0);

			// Register the remote object in RMI registry with a given identifier
			Registry registry= LocateRegistry.getRegistry(); 
			registry.rebind("ChatService", c_stub);

			System.out.println("Server rdy");

			Scanner s = new Scanner(System.in);
			String msg;

			while ((msg = s.nextLine())!=null) {
				if (msg.equals("/exit")) {
					c.exit();
				}
			}
			s.close();
		} catch (Exception e) {
			System.err.println("Error on server: " + e);
		}
	}
}
