package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientImpl implements Client{

	ArrayList<String> chat ;
	String login;
	String id =""; 
	
	/**
	 * Constructor of ClientImpl Initialize attribute and generate a pseudo random id,
	 * used to create Service name in rmiregistry.
	 * 
	 */
	public ClientImpl() {
		Client c_stub;
		try {
			c_stub = (Client) UnicastRemoteObject.exportObject(this, 0);
			id= "Service"+Math.random()*10000;//pseudo random id 
			Registry registry= LocateRegistry.getRegistry(); 
			registry.rebind(id, c_stub);
			chat = new ArrayList<>();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  Method called by server to notify current client to there is a new message on server
	 *  the server give in parameter the whole current history of message with the new one
	 *  one the last position of the List. (probably not the best implementation in the case
	 *   where the history is getting big not gonna lie.)
	 *  
	 *  @param chat an ArraList of all message on current history of server.
	 */
	@Override
	public void getMessage(ArrayList<String> chat) throws RemoteException {
		this.chat = chat;
		System.out.println(chat.get(chat.size()-1)+"\n");
	}
	
	/**
	 * print a history of message given by the server,
	 * 
	 * /!\ WARNING /!\ to have the history current client need to send at least one message.
	 * 
	 */
	public void gethistory() {
		System.out.println("--------HISTORY--------");

		for (int i = 0; i < chat.size(); i++) {
			System.out.println(chat.get(i)+"\n");
		}
		System.out.println("----------END----------");
	}

	/**
	 * Simple Remotely called method who print String msg given in parameter to client; 
	 * 
	 * @param msg Simple String variable with msg, example : "login : message" 
	 */
	@Override
	public void getWhisper(String msg) throws RemoteException{
		System.out.println(msg+"\n");
	}
}
