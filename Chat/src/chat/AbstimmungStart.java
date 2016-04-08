package chat;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import chat.Abstimmung.MeinWindowListener;

public class AbstimmungStart extends MeinFenster {


	JButton button;

	

	public AbstimmungStart(String titel, int w, int h) {
		super(titel, w, h);
		setSize(w, h);

		
		addWindowListener(new MeinWindowListener());
		
		ActionListener aktion = new Knopfdruck();
		setLayout(new FlowLayout());

		setVisible(true);

	}

	private void Button1Clicked() {

	}

	class Knopfdruck implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// wir behandeln die Ereignisse
			String cmd = e.getActionCommand();
			if (cmd.equals("b3"))
				Button1Clicked();
		}
	}
	
	class MeinWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent event) {

			System.exit(0);
		}
	}

	public static void main(String[] args) {
		AbstimmungStart lol = new AbstimmungStart("Abstimmen!", 500, 300);
	}
}