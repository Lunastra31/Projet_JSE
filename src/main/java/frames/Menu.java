package frames;


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rene
 */
public class Menu extends JMenuBar {
    private JMenu menu;

    public Menu() {
        gitInit();
    }
    
    public void gitInit(){
        menu = new JMenu("Menu");
        JMenuItem login = new JMenuItem("Se connecter");
        JMenuItem inscription = new JMenuItem("S'inscrire");
        login.addActionListener(e -> {
            Frames frame = new Frames();
        });
        inscription.addActionListener(e -> {
            FrameInscription fi = new FrameInscription();
  
        });
        menu.add(login);
        menu.add(inscription);
        this.add(menu);
        
    }
    
    
}
