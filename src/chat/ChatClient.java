package chat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClient {
	/**
	 * Main method of Client who can catch on console different command :
	 * 		- /login [yourlogin]
	 * 		- /history
	 * 		- /whisper [login of client you want to whisper]
	 * 
	 * @param args if no argument, default is "localhost" else host = first argument. 
	 */
	public static void main(String[] args) {
		try {

			String host;
			if (args.length < 1) 
				host = "localhost";
			else 
				host = args[0];


			Registry registry = LocateRegistry.getRegistry(host);
			Chat c = (Chat) registry.lookup("ChatService");

			Scanner s = new Scanner(System.in);
			String msg;
			String login="";
			ClientImpl me = new ClientImpl();

			System.out.println("Client rdy");			

			while ((msg = s.nextLine())!=null) {
				String[] option = msg.split(" ");
				if (option[0].equals("/login")) {
					if (option.length>2) {
						System.out.println("error login");
						return;
					}
					login = option[1];
					System.out.println("login : "+login +", id : "+ me.id);
					c.login(login, me.id);
				}else if (option[0].equals("/leave")) {
					if (option.length>2) {
						System.out.println("error login");
						return;
					}
					c.leave(option[1]);
				}else if (option[0].equals("/history")) {
					if (option.length>1) {
						System.out.println("error login");
						return;
					}
					me.gethistory();
				}else if (option[0].equals("/whisper")) {
					String wh = "";
					for (int i = 2; i < option.length; i++) {
						wh = wh + option[i] + " ";
					}
					c.whisper(login + " : " + wh, option[1]);
				}
				else {
					c.sendmessage(msg, login);
				}
				
			}
			s.close();
		} catch (Exception e) {
			System.err.println("Error on client: " + e);
		}
	}
}
