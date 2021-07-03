package com.coursemanagement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JLabel labelLogoImage, passwordLabel, emailLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPanel panelSide;
    private JButton loginButton, restButton;
    private JComboBox choice;

    public LoginFrame() {
        setVisible(true);
        setTitle("Course Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(450, 270));
        ImageIcon icon = new ImageIcon("src/com/coursemanagement/icons/icon14.jpg");
        setIconImage(icon.getImage());
        getContentPane().setBackground(new Color(255, 255, 255));
        setLayout(new GridLayout(1, 2));
        add(labelLogoImage());
        add(panelSide());


        JMenuBar ref = new JMenuBar();
        JMenu menu = new JMenu("Quick service");
        JMenuItem i2 = new JMenuItem("Help");
        menu.add(i2);
        ref.add(menu);
        this.setJMenuBar(ref);
        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "If you are using this application" +
                        "for the first time then you may be confused how to use this but no worries😃😃.\n" +
                        "Firstly, if you are student then your email and password should be in studentLogin.txt file.\n" +
                        "and if 'NEW USER' then student more information should be contained in registerStudent.txt file.\n" +
                        "If you are teacher then course administrator must have provided your password and login email.\n" +
                        "If not provided then consult to your course administrator to see email and password from \n" +
                        "Course Administrator teacher table and If you are Administrator then your email and password \n" +
                        "should be in administratorLogin.txt file");
            }
        });


        pack();
    }

    private JLabel labelLogoImage() {
        ImageIcon img = new ImageIcon("src/com/coursemanagement/icons/logo1.jpg");
        Image icon = img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        img = new ImageIcon(icon);
        labelLogoImage = new JLabel();
        labelLogoImage.setIcon(img);
        return labelLogoImage;
    }

    private JPanel panelSide() {
        panelSide = new JPanel();
        panelSide.setBackground(new Color(255, 255, 255));
        panelSide.setLayout(null);

        choice = new JComboBox(new String[]{"Student", "Instructor", "Administrator"});
        choice.setFont(new Font("Playful stencil", Font.BOLD, 15));
        choice.setForeground(new Color(0, 0, 0));
        choice.setBounds(10, 20, 185, 25);
        panelSide.add(choice);


        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        emailLabel.setForeground(new Color(0, 0, 0));
        emailLabel.setBounds(10, 50, 100, 25);
        panelSide.add(emailLabel);


        emailField = new JTextField();
        emailField.setForeground(new Color(0, 0, 0));
        emailField.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        emailField.setBounds(10, 80, 185, 25);
        panelSide.add(emailField);


        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        passwordLabel.setBounds(10, 110, 100, 25);
        passwordLabel.setForeground(new Color(0, 0, 0));
        panelSide.add(passwordLabel);
//
        passwordField = new JPasswordField();
        passwordField.setForeground(new Color(0, 0, 0));
        passwordField.setBounds(10, 140, 185, 25);
        passwordField.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        panelSide.add(passwordField);


        loginButton = new JButton("Login");
        loginButton.setBounds(10, 180, 80, 25);
        panelSide.add(loginButton);


        restButton = new JButton("Reset");
        restButton.setBounds(113, 180, 80, 25);
        panelSide.add(restButton);


        panelSide.setBorder(new TitledBorder("Login"));

        return panelSide;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRestButton() {
        return restButton;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JComboBox getChoice() {
        return choice;
    }

}
