import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class MyServer {
	
    static ArrayList <UtenteServer> uServer = new ArrayList <UtenteServer>();

	public static void main(String[] args) 	{
		try {
			// apertura del socket server
			ServerSocket server = new ServerSocket(8091);
			
			// dichiarazione del socket client e del buffer di ingresso
			Socket client;
			BufferedReader in;
			
			System.out.println("Server ready in ascolto sulla porta 8091 (CTRL-C quits)\n");
			
			// ciclo infinito
			while(true)
			{
				// chiamata bloccante, in attesa di connessione da parte di un client
				client = server.accept();
				System.out.println("Client connected: "+client);
				
		        uServer.add(new UtenteServer(client, uServer));
//				uServer.add(new UtenteServer(client));
				Runnable userRun = uServer.get(uServer.size()-1); 
               
				Thread t = new Thread (userRun);
                t.start();

                System.out.println ("Connessioni correnti: "+uServer.size());
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

