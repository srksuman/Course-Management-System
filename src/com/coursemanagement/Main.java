package com.coursemanagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends MysqlConnector {
    private final String emailSend;
    private LoginFrame loginFrame = new LoginFrame();
    private JButton loginBtn, resetBtn;
    private ResultSet resultSet;

    public Main() {
        emailSend = loginFrame.getEmailField().toString();
        loginBtnAction();
        resetBtnAction();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void loginBtnAction() {
        loadingFile();
        loginBtn = loginFrame.getLoginButton();
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = loginFrame.getPasswordField().getText().trim();
                String email = loginFrame.getEmailField().getText().toLowerCase().trim();
                String user = loginFrame.getChoice().getSelectedItem().toString().toLowerCase().trim();

                if (password.equals("") || email.equals("")) {
                    JOptionPane.showMessageDialog(loginFrame, "you cannot leave the email or\n password field blank");
                } else {

                    ArrayList emailData = new ArrayList();
                    ArrayList passwordData = new ArrayList();
                    System.out.println(user);
                    if (user.equals("instructor")) {

                        String query = "Select Email,Password from instructorLogin where Email ='" + email + "' and Password ='" + password + "' ";
                        try {
                            resultSet = statement.executeQuery(query);
                            String email1 = "";
                            String password1 = "";


                            while (resultSet.next()) {
                                email1 = resultSet.getString("Email");
                                password1 = resultSet.getString("Password");
                            }

                            if (!email1.isEmpty() && !password1.isEmpty()) {
                                loginFrame.dispose();
                                new Instructor(email1);
                            } else {
                                JOptionPane.showMessageDialog(null, "Email or password is incorrect");
                            }

                        } catch (SQLException exception) {
                            exception.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Something went wrong\nPlease try again later");
                        } catch (NullPointerException exp) {
                            JOptionPane.showMessageDialog(null, "Problem in database connectivity");
                        }

                    } else {


                        try {


                            File student = new File("src/com/coursemanagement/Files/" + user + "Login" + ".txt");
                            Scanner details = new Scanner(student);
                            while (details.hasNextLine()) {
                                String data = details.nextLine();
                                String[] dataE = data.split(" ");
                                emailData.add(dataE[0]);
                                passwordData.add(dataE[1]);
                            }
                            details.close();
                        } catch (FileNotFoundException f) {
                            System.out.println("File not found");
                            f.printStackTrace();
                        }

                        int correctOrNot = 0;
                        if (emailData.size() == passwordData.size()) {
                            for (int i = 0; i < emailData.size(); i++) {
                                if (emailData.get(i).equals(email) && passwordData.get(i).equals(password)) {
                                    correctOrNot++;

                                }
                            }
                            if (correctOrNot == 1) {

                                if (user.equals("administrator")) {
                                    loginFrame.dispose();
                                    new CourseAdministrator();
                                } else if (user.equals("student")) {
                                    loginFrame.dispose();
                                    new Student(email);
                                }
                            } else {
                                JOptionPane.showMessageDialog(loginFrame, "Incorrect email or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }


                    }


                    resetBtn.doClick();
                }


            }
        });
    }

    private void loadingFile() {
        String[] file = {"student", "administrator"};

        try {

            for (int i = 0; i < file.length; i++) {
                File filename = new File("src/com/coursemanagement/Files/" + file[i] + "Login" + ".txt");
                if (filename.createNewFile()) {
                    System.out.println("File created: " + filename.getName());
                    FileWriter writer = new FileWriter("src/com/coursemanagement/Files/" + file[i] + "Login" + ".txt");
                    if (file[i].equals("student")) {

                        writer.write("suman@edu.com.np suman123\n" +
                                "pujan@edu.com.np pujan123\n" +
                                "sujan@edu.com.np sujan123\n" +
                                "bimal@edu.com.np bimal123\n" +
                                "aiti@edu.com.np atit123\n" +
                                "krishna@edu.com.np krishan123\n" +
                                "nikesha@edu.com.np nikisha123\n" +
                                "mahendra@edu.com.np mahendra123\n" +
                                "dipendra@edu.com.np dipendra123\n" +
                                "aayush@edu.com.np aayush123\n" +
                                "sandesh@edu.com.np sandesh123\n" +
                                "pradip@edu.com.np pradip123\n" +
                                "pramod@edu.com.np pramod123\n" +
                                "rama@edu.com.np rama123\n" +
                                "uma@edu.com.np uma123\n" +
                                "science@edu.com.np science123\n" +
                                "unique@edu.com.np unique123\n" +
                                "prince@edu.com.np prince123\n" +
                                "krish@edu.com.np krish123\n" +
                                "paul@edu.com.np paul123\n" +
                                "bishal@edu.com.np bishal123\n" +
                                "prativa@edu.com.np prativa123\n" +
                                "pooja@edu.com.np pooja123\n" +
                                "safalta@edu.com.np safalta123\n" +
                                "anita@edu.com.np anita123\n" +
                                "amrita@edu.com.np amrita123\n" +
                                "sahil@edu.com.np sahil123\n" +
                                "dipesh@edu.com.np dipesh123");
                        writer.close();
                        File fileReg = new File("src/com/coursemanagement/Files/registerStudent.txt");
                        fileReg.createNewFile();
                        System.out.println("File created: " + fileReg.getName());
                        FileWriter writer2 = new FileWriter("src/com/coursemanagement/Files/registerStudent.txt");
                        writer2.write("krish@edu.com.np Krish Tamang NMC,Kathmandu 986457360 Pimple \n" +
                                "paul@edu.com.np Paul Basnet PMC,Kathmandu 986045456350 Dimnple \n" +
                                "bishal@edu.com.np Bishal Aryal NCC,Kathmandu 9860546550 Simplke \n" +
                                "prativa@edu.com.np Prativa Khanal Sankhu,Kathmandu 9865456540 Kunjan \n" +
                                "pooja@edu.com.np Pooja Thapa NMC,Kathmandu 9862374540 Arya \n" +
                                "safalta@edu.com.np Safalta Lamichane Boudha,Kathmandu 9845475450 Janak \n" +
                                "anita@edu.com.np Anita Lama jorpati,Kathmandu 9540566650 Janan \n" +
                                "amrita@edu.com.np Amrita Tamang NMC,Kathmandu 983457360 Janani \n" +
                                "sahil@edu.com.np Sahil Basnet PMC,Kathmandu 9835476350 Sanani ");
                        writer2.close();

                    }
                    if (file[i].equals("administrator")) {
                        writer.write("admin@course.com.np admin");
                        writer.close();
                    }

                }


            }


        } catch (IOException ex) {
            System.out.println("error occurred.");
            ex.printStackTrace();
        }


    }

    private void resetBtnAction() {
        resetBtn = loginFrame.getRestButton();
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.getEmailField().setText("");
                loginFrame.getPasswordField().setText("");
                loginFrame.getChoice().setSelectedIndex(0);
            }
        });
    }

}
