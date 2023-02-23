package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Client extends Remote{
	/**
	 * method called by ChatImpl Object when someone put message for everyone on server
	 * @param chat
	 * @throws RemoteException
	 */
	public void getMessage(ArrayList<String> chat) throws RemoteException;

	/**
	 * method called by ChatImpl Object when someone whisper specified client.
	 * @param msg
	 * @throws RemoteException
	 */
	public void getWhisper(String msg) throws RemoteException;
}
