package frames;

import JContent.PasswordAuthentication;
import JContent.LabTF;
import JContent.LabPF;
import DAO.DaoUser;
import DAO.MariaDbConnection;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.util.Objects.hash;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import models.User;

public class FrameInscription extends JFrame {

    private JButton b1, b2, accueil;
    private LabTF log;
    private LabPF psw, confirm;
    private JPanel inscrLTF, inscrButt, inscrLab;

    public FrameInscription() {
        this("Inscription");
    }

    public FrameInscription(String title) {
        super(title);
        initGui();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void initGui() {
        // Création du container login qui est la page d'accueil 
        Container inscription = this.getContentPane();
        // Affichage de la page en horizontale
        inscription.setLayout(new BoxLayout(inscription, BoxLayout.PAGE_AXIS));

        // Création du JPanel contenant les LabelTextfield de la page d'inscription
        inscrLTF = new JPanel();
        inscrLTF.setLayout(new BoxLayout(inscrLTF, BoxLayout.PAGE_AXIS));
        log = new LabTF("Login", 25, "Nom d'utilisateur");
        psw = new LabPF("Password", 25, "Mot de passe");
        confirm = new LabPF("Confirmation", 25, "Confirmation de votre mot de passe");
        // Ajout des LabTF dans le JPanel associé
        inscrLTF.add(log);
        inscrLTF.add(psw);
        inscrLTF.add(confirm);

        // Création du JPanel contenant les boutons de la page d'inscription
        inscrButt = new JPanel();
        b1 = new JButton("Valider");
        b2 = new JButton("Annuler");
        // Ajout des boutons dans le JPanel associé
        inscrButt.add(b1);
        inscrButt.add(b2);

        // Création du JPanel contenant le label de la pas d'inscription
        inscrLab = new JPanel();
        accueil = new JButton("Accueil");
        // Ajout du label dans le JPanel associé
        inscrLab.add(accueil);
        b2.addActionListener(new buttEvents());
        accueil.addActionListener(new buttEvents());
        // Ajout des différents JPanels dans la page
        inscription.add(inscrLTF);
        inscription.add(inscrButt);
        inscription.add(inscrLab);

        //Add an action listener to the "Valider" button 
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Retrive the password and confirmation from the JPasswordFields
                String login = log.getTextField().getText();
                char[] passwordChars = psw.getPasswordField().getPassword();
                char[] confirmChars = confirm.getPasswordField().getPassword();
                String password = new String(passwordChars);
                String confirmation = new String(confirmChars);
                
                DaoUser du = new DaoUser();
                User person = new User();
                try {
                    boolean found = checkLogin(login);
                    if (!found) {
                        // Verify if the password meets the minimum length requirement (3 characters)
                        if (password.length() < 3) {
                            JOptionPane.showMessageDialog(FrameInscription.this, "Le mot de passe doit comporter au moins 3 caractères", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (password.equals(confirmation)) {
                            //Password match, show a 'Bienvenue' pop up page
                            JOptionPane.showMessageDialog(FrameInscription.this, "Bienvenue", "Les 3 mousses de bières", JOptionPane.INFORMATION_MESSAGE);
                            person.setLogin(log.getTextField().getText());
                            PasswordAuthentication pa = new PasswordAuthentication();
                            String hashedPassword = pa.hash(password);
                            person.setPassword(hashedPassword);
                            
                            du.create(person);
                        } else {
                            //Password do not match, show an error pop-up page
                            JOptionPane.showMessageDialog(FrameInscription.this, "Le mot de passe ou le login ne correspond pas ", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        //Clear the password and confirmation fields
                        psw.getPasswordField().setText("");
                        confirm.getPasswordField().setText("");
                    } else{
                        JOptionPane.showMessageDialog(FrameInscription.this, "Le nom d'utilisateur existe déja", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FrameInscription.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private class buttEvents implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Close the current frame
            dispose();
        }
    }

    public boolean checkLogin(String loginSaisi) throws SQLException {

        String sql = "SELECT login FROM user WHERE login=?";
        ResultSet rs = null;
        try {
            String ID = log.getTextField().getText();
            PreparedStatement pstmt = MariaDbConnection.getInstance().prepareStatement(sql);
            pstmt.setString(1, loginSaisi);
            rs = pstmt.executeQuery();
            System.out.println("check if login exists : " + rs.first());
        } catch (SQLException ex) {
            Logger.getLogger(Frames.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs.first();
    }

}
