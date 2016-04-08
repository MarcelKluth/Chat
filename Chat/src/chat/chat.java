// Package muss natürlich angepasst werden
package chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;

public class chat {
	
	JFrame clientFrame;
	JPanel clientPanel;
	JTextArea textArea_Messages;
	JTextField textField_ClientMessage;
	JButton button_SendMessage;
	JButton button_CreateVoting;
	JTextField textField_Username;
	JScrollPane scrollPane_Messages;
	
	Socket client;
	PrintWriter writer;
	BufferedReader reader;
	
	public static void main(String[] args) {
		chat c = new chat();
		c.createGUI();
	}
	
	public void createGUI() {
		clientFrame = new JFrame("NaWi-Chat");
		clientFrame.setSize(800, 600);
		
		// Panel erzeugen, welches alle anderen Inhalte enthält
		clientPanel = new JPanel();
		
		textArea_Messages = new JTextArea();
		textArea_Messages.setEditable(false);
		
		textField_ClientMessage = new JTextField(38);
		textField_ClientMessage.addKeyListener(new SendPressEnterListener());
		
		button_SendMessage = new JButton("Senden");
		button_SendMessage.addActionListener(new SendButtonListener());
		
		button_CreateVoting = new JButton("Abstimmung erstellen");
		button_CreateVoting.addActionListener(new CreateVotingListener());
		
		textField_Username = new JTextField(10);
		
		// Scrollbalken zur textArea hinzufügen
		scrollPane_Messages = new JScrollPane(textArea_Messages);
		scrollPane_Messages.setPreferredSize(new Dimension(700, 500));
		scrollPane_Messages.setMinimumSize(new Dimension(700, 500));
		scrollPane_Messages.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Messages.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);		
		
		
		if(!connectToServer()) {
			// Connect-Label anzeigen ob verbunden oder nicht...
		}
		
		Thread t = new Thread(new MessagesFromServerListener());
		t.start();
		
		clientPanel.add(scrollPane_Messages);
		clientPanel.add(textField_Username);
		clientPanel.add(textField_ClientMessage);
		clientPanel.add(button_SendMessage);
		clientPanel.add(button_CreateVoting);
		
		// Panel zum ContentPane (Inhaltsbereich) hinzufügen
		clientFrame.getContentPane().add(BorderLayout.CENTER, clientPanel);
		
		clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		clientFrame.setVisible(true);
	}
	
	public boolean connectToServer() {
		try {
			client = new Socket("10.24.215.15", 5555);
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			writer = new PrintWriter(client.getOutputStream());
			appendTextMessages("Netzwerkverbindung hergestellt");
			
			return true;
		} catch(Exception e) {
			appendTextMessages("Netzwerkverbindung konnte nicht hergestellt werden");
			e.printStackTrace();
			
			return false;
		}
	}
	
	public void sendMessageToServer() {
		if (textField_Username.getText().length() == 0 || textField_Username.getText().equals("name")) {
			appendTextMessages("Benutzernamen eingeben!");
		} else {
			if (textField_ClientMessage.getText().length() == 0 || textField_ClientMessage.getText().equals("name")) {
			//	appendTextMessages("Nachricht eingeben!");
		} else {
			 Date currentTime = new Date();
			 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			 System.out.println(formatter.format(currentTime));
		writer.println(formatter.format(currentTime) + " | " + textField_Username.getText() + ": " + textField_ClientMessage.getText());
		writer.flush();
		
		
		textField_ClientMessage.setText("");
		textField_ClientMessage.requestFocus();
	}
		}
	}
	
	public void appendTextMessages(String message) {
		textArea_Messages.append(message + "\n");
		//textArea_Messages.add(popup);
	}
	
	public void createVoting(){
		//dauer, stimmanzahl, auswahlmöglichekeiten
		Abstimmung g = new Abstimmung("Abstimmen!", 100, 300);
		
	}
	
	// Listener

	
	public class SendPressEnterListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				sendMessageToServer();
			}	
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	public class SendButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sendMessageToServer();			
		}
		
	}
	
	public class CreateVotingListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			createVoting();			
		}
		
	}
	
	public class MessagesFromServerListener implements Runnable {

		@Override
		public void run() {
			String message;
			
			try {
				while((message = reader.readLine()) != null) {
					appendTextMessages(message);
					textArea_Messages.setCaretPosition(textArea_Messages.getText().length());
				}
			} catch (IOException e) {
				appendTextMessages("Nachricht konnte nicht empfangen werden!");
				e.printStackTrace();
			}
		}
		
	}
}