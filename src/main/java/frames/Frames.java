package frames;

import DAO.MariaDbConnection;
import JContent.LabTF;
import JContent.LabPF;
import JContent.PasswordAuthentication;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sadiya
 */
public class Frames extends JFrame {

    private JButton b1, b2, b3;
    private LabTF log;
    private LabPF psw; //for password input
    private JPanel logLTF, logButt, logLab;

    public Frames() {
        this("Connexion");
    }

    public Frames(String title) {
        super(title);
        initGui();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initGui() {
        Container login = this.getContentPane();
        login.setLayout(new BoxLayout(login, BoxLayout.PAGE_AXIS));

        logLTF = new JPanel();
        logLTF.setLayout(new BoxLayout(logLTF, BoxLayout.PAGE_AXIS));

        log = new LabTF("Login", 20, "Identifiant");
        psw = new LabPF("Password", 20, "Mot de passe");

        logLTF.add(log);
        logLTF.add(psw);

        logButt = new JPanel();

        b1 = new JButton("Valider");
        b2 = new JButton("Annuler");
        b3 = new JButton("Inscrire");

        logButt.add(b1);
        logButt.add(b2);
        logButt.add(b3);

        logLab = new JPanel();

        login.add(logLTF);
        login.add(logButt);
        login.add(logLab);

        // Add an action listener to the "Annuler" button
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.getTextField().setText(""); // Clear login field
                psw.getPasswordField().setText(""); // Clear password field
                b3.setEnabled(true); // Activate the "Inscrire" button
            }
        });

        // Add an action listener to the "Inscrire" button
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameInscription insc = new FrameInscription();
                dispose();
            }
        });

        // Add an action listener to the "Valider" button
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the password from the JPasswordField
                char[] passwordChars = psw.getPasswordField().getPassword();

                String ID = log.getTextField().getText();
                try {
                    // Verify the password and show     a pop-up dialog
                    if (((checkLogin(ID)) && (checkPwd(ID, passwordChars)))) {
                        JOptionPane.showMessageDialog(Frames.this, "Welcome", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(Frames.this, "Invalid password or login", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Frames.class.getName()).log(Level.SEVERE, null, ex);
                }
//                String ID = log.getTextField().getText();
//                try {
//                    checkLogin(ID);
//                } catch (SQLException ex) {
//                    Logger.getLogger(Frames.class.getName()).log(Level.SEVERE, null, ex);
//                }

                // Clear the password field
                //psw.getPasswordField().setText("");
            }
        });

    }

    public boolean checkLogin(String loginSaisi) throws SQLException {

        String sql = "SELECT login FROM user WHERE login=?";
        ResultSet rs = null;
        try {
            PreparedStatement pstmt = MariaDbConnection.getInstance().prepareStatement(sql);
            pstmt.setString(1, loginSaisi);
            rs = pstmt.executeQuery();
            System.out.println("check if login exists : " + rs.first());
        } catch (SQLException ex) {
            Logger.getLogger(Frames.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs.first();
    }

    public boolean checkPwd(String loginSaisi, char[] pwd) throws SQLException {
        String sql = "SELECT password FROM user WHERE login=?";
        PasswordAuthentication pa = new PasswordAuthentication();
        ResultSet rs = null;
        PreparedStatement pstmt = MariaDbConnection.getInstance().prepareStatement(sql);
        try {
            pstmt.setString(1, loginSaisi);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String psd = rs.getString("password");
                boolean authentif = pa.authenticate(pwd,psd);
                if (authentif) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    

}
