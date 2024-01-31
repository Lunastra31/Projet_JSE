package JContent;


import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rene
 */
public class LabTF extends JPanel {

    private JLabel label;
    private JTextField tf;

    public LabTF(String label, int size) {
        this.setLayout(new BorderLayout());
        this.label = new JLabel(label);
        this.tf = new JTextField(size);
        this.add(this.label, BorderLayout.WEST);
        this.add(this.tf, BorderLayout.EAST);

    }

    public LabTF(String label, int size, String toolTip) {
        this(label, size);
        this.tf.setToolTipText(toolTip);
    }

    public JTextField getTextField() {
        return tf;
    }

}
