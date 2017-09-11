import java.net.*;
import java.io.*;

public class MyClient
{
    static Socket clientSocket;
    static int numeroPorta = 8091;
    static String host = "localhost";

    public static void main (String[] args)
    {
        try
        {
            clientSocket = new Socket (host, numeroPorta);
            Runnable r = new UtenteClient (clientSocket);
            Thread t = new Thread(r);
            t.start();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
