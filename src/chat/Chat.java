package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote{
	public boolean sendmessage(String msg, String login) throws RemoteException;
	public boolean whisper(String msg, String login) throws RemoteException;
	public boolean login(String login, String client) throws RemoteException;
	public boolean leave(String login) throws RemoteException;
}
