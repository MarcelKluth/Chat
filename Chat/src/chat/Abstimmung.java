package chat;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Abstimmung extends MeinFenster {
	int amk=0;
JButton button1;
JButton button2;
JTextField textfield1;
JLabel label;
JButton button;
	
	
	

	// Im Konstruktor erzeugen wir die GUI-Elemente
	public Abstimmung(String titel, int w, int h) {
		super(titel, w, h);
		setSize(w, h);
		// Wir registrieren den WindowListener, um auf

		// WindowEvents reagieren zu können
		addWindowListener(new MeinWindowListener());
		// wir bauen einen ActionListener, der nur auf Knopfdruck
		// reagiert
		ActionListener aktion = new Knopfdruck();
		setLayout(new FlowLayout());
		
		
		textfield1 = new JTextField("Möglichkeit A", 10);
		add(textfield1);
		button1 = new JButton("Hinzufügen");
		add(button1);
		button1.addActionListener(aktion);
		button1.setActionCommand("b1");
		button2 = new JButton("Start");
		add(button2);
		button2.addActionListener(aktion);
		button2.setActionCommand("b2");
		
		
		setVisible(true);
		
		
		
	}
	
	public int getAmk() {
		return amk;
	}

	public void setAmk(int amk) {
		this.amk = amk;
	}

	private void Button1Clicked() {
		
		
		String txt = textfield1.getText();
		
		button = new JButton();
		add(button);
		button.setText(txt);
			
		
		
		
		
		
	}

private void Button2Clicked() {
		
	AbstimmungStart lol = new AbstimmungStart("Abstimmen!", 500, 300);
		
	}

	
	// Innere Klassen für das Eventmanagement
	class MeinWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent event) {

			System.exit(0);
		}
	}

	class Knopfdruck implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// wir behandeln die Ereignisse
			String cmd = e.getActionCommand();
			if (cmd.equals("b1"))
				Button1Clicked();
			if(cmd.equals("b2"))
				Button2Clicked();
		}
	}

	public static void main(String[] args) {
		Abstimmung f = new Abstimmung("Abstimmung", 100, 300);
	}
}