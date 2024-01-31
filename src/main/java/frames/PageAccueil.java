package frames;


import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rene
 */
public class PageAccueil extends JFrame {

    private Menu menuAccueil;

    public PageAccueil() {
        this("Les 3 mousses de bières");
    }

    public PageAccueil(String title) {
        super(title);
        initGui();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void initGui(){
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        JPanel centerP = new JPanel();
        JLabel label = new JLabel("Bienvenue aventurier");
        JLabel image = new JLabel();
        image.setIcon(new ImageIcon("LogoBiere.png"));
        centerP.add(label);
        centerP.add(image);
        menuAccueil = new Menu();
        this.setJMenuBar(menuAccueil);
        content.add(menuAccueil, BorderLayout.NORTH);
        content.add(centerP);
    }

}
