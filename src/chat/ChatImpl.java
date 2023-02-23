package chat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ChatImpl implements Chat {

	/**
	 * Map of Clients with key = login and Value = remote Object of Client.
	 */
	HashMap<String, Client> listeClient;

	/**
	 * ArrayList used to save all message transfered between everyone, whisper are not saved in this List.
	 */
	ArrayList<String> chat;

	/**
	 * Reader used to read the file "history.txt" who needs to be in the src folder of the project.
	 */
	BufferedReader reader;
	


	/**
	 * Constructor
	 * Initialize all arguments and get history of server from "history.txt".
	 * @throws IOException
	 */
	public ChatImpl() throws IOException {
		listeClient = new HashMap<>();
		chat = new ArrayList<>();

		//file needs to be in the same folder of this project
		//or change the string value by the complete path to the txt file.
		reader = new BufferedReader(new FileReader("history.txt"));	
		String line;
		
		//getting all precedent saved messages from the reader.
		while ((line = reader.readLine()) != null) {
			chat.add(line);
		}
		reader.close();	
	}

	/**
	 * sendmessage(String msg, String login)
	 * save the string msg send by client designed by login in the chat ArrayList
	 * Call getMessage method on all registered clients.
	 * 
	 * @param msg String with wanted message.
	 * @param login String with login of sender.
	 */
	@Override
	public boolean sendmessage(String msg, String login) throws RemoteException {
		chat.add(login + " : " + msg);
		for (Entry<String, Client> entry : listeClient.entrySet()) {
			Client val = entry.getValue();
			val.getMessage(chat);
		}
		return false;
	}
	
	/**
	 * call method getWhisper on specified Client designed by login String.
	 * 
	 * @param msg String with wanted message.
	 * @param login String with login of the client you want to whisper.
	 */
	@Override
	public boolean whisper(String msg, String login) throws RemoteException {
		listeClient.get(login).getWhisper("[whisper] "+ msg);
		return false;
	}


	/**
	 * When client log to the server with a "/login Itsme" the server get the remote object
	 *  of the client and put it in listeClient.
	 *  
	 * @param login String of login of the new client.
	 * @param cl String of the name of Service of the new client registered on the rmiregistry.
	 */
	@Override
	public boolean login(String login, String cl) throws RemoteException {
		Registry registry = LocateRegistry.getRegistry("localhost");
		try {
			Client c = (Client) registry.lookup(cl);
			listeClient.put(login, c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * Just removing designed client from listeClient.
	 * 
	 * @param login String of login of client you wants to logout of server.
	 */
	@Override
	public boolean leave(String login) throws RemoteException {
		listeClient.remove(login);
		return false;
	}

	/**
	 * "/exit" command on server will shut it down properly and save all current message history in "history.txt"
	 */	
	public void exit() throws IOException {
		// TODO Auto-generated method stub
		FileWriter fw = new FileWriter("history.txt");

		for (int i = 0; i < chat.size(); i++) {
			fw.write(chat.get(i)+"\n");
		}
		fw.close();

		System.exit(0);
	}

}
