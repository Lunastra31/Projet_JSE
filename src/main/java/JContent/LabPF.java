package JContent;


import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rene
 */
public class LabPF extends JPanel {

    // Déclaration d'un JLabel et d'un JPasswordField
    private JLabel label;
    private JPasswordField password;

    // Constructeur prenant en paramètre un string et une taille pour le JPasswordField
    public LabPF(String label, int size) {
        // Pour que le label et le PasswordField soient côte à côte - In order to display the label and the PasswordField next to each other
        this.setLayout(new BorderLayout());
        this.label = new JLabel(label);
        this.password = new JPasswordField(size);
        // Pour afficher le label a gauche et le PasswordField a droite - To display the label on the left , and the PasswordField on the right
        this.add(this.label, BorderLayout.WEST);
        this.add(this.password, BorderLayout.EAST);

    }

    // Constructeur prenant en paramètre un string une taille pour le JPasswordField et un string 
    public LabPF(String label, int size, String toolTip) {
        this(label, size);
        this.password.setToolTipText(toolTip);
    }

    // getter du JPasswordField
    public JPasswordField getPasswordField() {
        return password;
    }
}
