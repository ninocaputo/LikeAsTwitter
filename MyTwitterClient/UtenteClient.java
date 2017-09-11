import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class UtenteClient implements Runnable {
    private Socket incoming;
    private PrintWriter out;
    private Scanner in;

    public UtenteClient(Socket s) {
        incoming = s;
    }

    public void run() {
        try  {
			
				InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                in = new Scanner (inStream);
                out = new PrintWriter (outStream, true);

                Scanner tastiera = new Scanner (System.in);

                boolean done = false;

                String line;

                // Inserire il nome
                if (in.hasNextLine()) {
                    line = in.nextLine();
                    System.out.println (line);
                    line = tastiera.nextLine();
                    out.println(line);
                }


 				ClientFrame clientFrame = new ClientFrame(outStream);
 				while (!done && in.hasNextLine())    {
                    line = in.nextLine();
                    clientFrame.stampa(line);
 				}
 	 				incoming.close();
 	                in.close();
 	                out.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
        }
    
    private class ClientFrame extends JFrame {
    	private OutputStream outStream;
    	private PrintWriter out;
    	
    	private JPanel panel1;
    	private JPanel panel2;
    	JTextField txt1, txt2; 
    	JLabel comando;
         
    	ClientFrame (OutputStream outputStream) throws IOException    {

	        super ("PulsantiJFrame"); // costruzione finestra 
	        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	        setSize (400, 800); // dimensionamento della finestra 
	        setLocation (200, 100);  // posizionamento della finestra

	        outStream = outputStream;
            out = new PrintWriter (outStream, true);

	        
	        JFrame f = new JFrame("LikeAsTwitter"); 
			f.setLayout(new GridLayout(2, 1));
	        panel1 = new JPanel();  //creazione pannello
	        add(panel1);   // inserimento pannello nella finestra     
	        panel1.setLayout (null); // layout del pannello: posizionamento assoluto
	
	        JLabel lbl = new JLabel ("<html>Scegli:<br>add follower: 'user' follows 'another user'<br>delete follower: 'user' de-follow 'another user'<br>posting: 'user' message<br>wall: 'user' wall<br>reading: 'user'</html>", SwingConstants.CENTER); // creazione del pulsante
	        lbl.setBounds (10, 20, 300, 110); // dimensionamento e posizionamento      
	        panel1.add (lbl); // inserzione del pulsante nel pannello
	        f.add(panel1);
	
	        
	        panel2 = new JPanel();  //creazione pannello
			comando = new JLabel("digita il comando");
			comando.setHorizontalAlignment(JLabel.LEFT); // dimensionamento e posizionamento      
	        txt1 = new JTextField("", 25); 
			txt2 = new JTextField(25); // larghezza in caratt. 
			txt2.setEditable(false); // non modificabile 
			panel2.add(comando);  
			panel2.add(txt1);  
			panel2.add(txt2);  
			
	        JButton button = null;

	        button = initButton(button, new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		System.out.println(txt1.getText());
	        		out.println(txt1.getText()); 
	        	}

	        });
	        panel2.add(button);
	        f.add(panel2);       
	
	        f.setSize(350,400);    
			f.setVisible(true); 
		
    	} 

        private JButton initButton (JButton buttonTemp, ActionListener listener) {
        	buttonTemp = new JButton ("Invio");
        	buttonTemp.setFont(new Font ("Arial", Font.BOLD, 25));
        	buttonTemp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        	buttonTemp.addActionListener(listener);

            return buttonTemp;
        }
        
        public void stampa (String line)    {
        	txt2.setText(line.substring(4));
        	txt2.setEnabled(false);
        }

    
    }	

 
  
}