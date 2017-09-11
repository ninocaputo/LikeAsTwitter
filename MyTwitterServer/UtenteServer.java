import java.io.*;
import java.net.*;
import java.util.*;

public class UtenteServer implements Runnable {
    private Socket incoming;
    private ArrayList <UtenteServer> users;
    private PrintWriter out;
    private Scanner in;
    private String name;
    private int port;
    private InetAddress ipAddr;
    private ArrayList <String> followers = new ArrayList <String>();


    public UtenteServer(Socket incoming, ArrayList users)     {   
        	this.incoming = incoming;
        	this.ipAddr = incoming.getInetAddress();
        	this.port = incoming.getPort();
      	    this.users = users;
    }

    public void run()  {
            try  {
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                in = new Scanner (inStream);
                out = new PrintWriter(outStream, true);

                boolean done = false;

                out.println ("Scrivi il tuo nome: ");

                if (in.hasNextLine()) {
                	this.name = in.nextLine();
                 	this.ipAddr = incoming.getInetAddress();
                	this.port = incoming.getPort();

                }
                System.out.println ("Ho ricevuto il nome "+this.name);
                System.out.println ("clientAddress :" +incoming.getInetAddress());
                System.out.println ("clientPort :" +incoming.getPort());
                System.out.println ("localAddress :" +incoming.getLocalAddress());
                System.out.println ("localPort :" +incoming.getLocalPort());
                System.out.println ("localSocketAddress :" +incoming.getLocalSocketAddress());
                System.out.println ("remoteSocketAddress :" +incoming.getRemoteSocketAddress());
                
                
                while (!done && in.hasNextLine()){
//                	 out.println ("Scrivi qualcosa: ");
                	 String line;
                     line = in.nextLine();
                     System.out.println ("Ho ricevuto : "+line);
                     Scanner input = new  Scanner(line);
                     System.out.println ("sono : "+this.name);
                     if(input.hasNext()) {
                    	 if(!input.next().equals(this.name))  
                    		 continue;
                    	 String data = input.next();
                    	 System.out.println ("comando : "+data);
                    	 String follows = null;
                    	 if(data.equalsIgnoreCase("follows")) {
                    		 follows = input.next();
                    		 System.out.println ("leggo nome follow : "+ follows);
                    	     this.followers.add(follows);
                             for(int i=0;i<this.followers.size();i++)
                                 System.out.println ("followers :"+this.followers.get(i));

                    	 }
                     	 if(data.equalsIgnoreCase("de-follows")) {
                    		 System.out.println ("comando corretto");
                        	 follows = input.next();
                    		 System.out.println ("leggo nome follow : "+ follows);
                    		 for(int i=0;i<this.followers.size();i++) {
                    			 if(this.followers.get(i).equalsIgnoreCase(follows))
                    				 this.followers.remove(i);
                    		 }
                             System.out.println ("tot followers :"+this.followers.size());
                     	 }
                     	 if((!data.equalsIgnoreCase("follows"))&&(!data.equalsIgnoreCase("de-follows"))&&(!data.equalsIgnoreCase("reading"))&&(!data.equalsIgnoreCase("wall"))) {
                    		 System.out.println ("comando 'posting'");
                    		 System.out.println (this.name+" "+data);
                    		 String msg = data;
                    		 while(input.hasNext()) {
       						 		msg += " ";
       						 	    msg += input.next();
                    		 }
                    		 System.out.println (msg);
                    		 
                   		     for(int i=0;i<users.size();i++) {
                    			 if(users.get(i).name.equalsIgnoreCase(this.name)) {
                        			 for(int x=0;x<users.get(i).followers.size();x++) {
                        				 String nameFollower = users.get(i).followers.get(x);
                        				 for(int n=0;n<users.size();n++) {
                        					 if(users.get(n).name.equalsIgnoreCase(nameFollower)) {
                        						 Socket incomingFollower = users.get(n).incoming;
                        						 OutputStream outStreamFollower = incomingFollower.getOutputStream();
                        						 PrintWriter outFollower = new PrintWriter(outStreamFollower, true);
                                           		 System.out.println (msg);
                       						     outFollower.println(msg);
                        					 }

                        				 }
                        			 }

                    			 }

                    		 }
                     	 }
                     	 if(data.equalsIgnoreCase("wall")) {
                    		 System.out.println ("comando 'wall'");
                    		 System.out.println (this.name+" "+data);
                    		 String msg = data;
                    		 while(input.hasNext()) {
       						 		msg += " ";
       						 	    msg += input.next();
                    		 }
                       		 System.out.println (msg);

                    		 for(int i=0;i<users.size();i++) {
                    			 if(!users.get(i).name.equalsIgnoreCase(this.name)) {
                        				Socket incomingFollower = users.get(i).incoming;
                        				OutputStream outStreamFollower = incomingFollower.getOutputStream();
                        				PrintWriter outFollower = new PrintWriter(outStreamFollower, true);
               						 	outFollower.println(msg);
                        		}
                   			 }
                     	 }
                      	 if(data.equalsIgnoreCase("reading")) {
                     		 System.out.println ("comando reading non implementato");
                     	 }
                   }
               }

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
					incoming.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                in.close();
                out.close();
                System.out.println ("end! Aspetto nuove connessioni...");
	
            }
        }
}

