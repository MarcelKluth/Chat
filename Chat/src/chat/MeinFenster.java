package chat;

import java.awt.*;
public class MeinFenster extends Frame {
public MeinFenster(String titel, int w, int h){
this.setTitle(titel);
this.setSize(w, h);
// zentriere das Fenster
Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
this.setLocation((d.width-this.getSize().width)/2,
(d.height-this.getSize().height)/2);
}
}