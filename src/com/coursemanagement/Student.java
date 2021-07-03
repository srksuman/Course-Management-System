package com.coursemanagement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Student extends JFrame {
    private final String studentEmail;
    private final ArrayList details = new ArrayList();
    private final String temporaryFile = "temporary.txt";
    private final Student message = this;
    private final MysqlConnector conn;
    private JTextField firstName, lastName, address, phoneNumber, parentName, email;
    private JComboBox selectLevel, chooseCourse, selectSemester;
    private JCheckBox checkBox;
    private JButton saveData;
    private ResultSet resultSet;

    public Student(String email) {

        this.studentEmail = email;
        conn = new MysqlConnector();
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 255, 255));

        windowCloseOperation();


        int which = 1;
        try {
            File registerFile = new File("src/com/coursemanagement/Files/registerStudent.txt");
            Scanner reeadFile = new Scanner(registerFile);

            while (reeadFile.hasNext()) {
                String data = reeadFile.next();
                details.add(data);
            }

            reeadFile.close();
            Iterator itr = details.iterator();
            while (itr.hasNext()) {

                if (itr.next().equals(studentEmail)) {
                    which++;
                    System.out.println(which);

                }
            }
        } catch (FileNotFoundException x) {
            System.out.println("something went wrong");
        }


        if (which == 2) {
            registerConformation();
            setMinimumSize(new Dimension(600, 400));
            add(panelOne());
            selectLeveAction();
        } else {
            setMinimumSize(new Dimension(600, 330));
            add(panelDetails());
        }
        setResizable(false);
        pack();


    }

    private void windowCloseOperation() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirmedCode = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit the Student Pane?", "Close Student Pane",
                        JOptionPane.YES_NO_OPTION);

                if (confirmedCode == JOptionPane.YES_OPTION) {
                    File temporary = new File("src/com/coursemanagement/Files/" + temporaryFile);
                    if (temporary.exists()) {
                        temporary.delete();
                    }
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    public void registerConformation() {
        int index = 0;
        try {
            File temporary = new File("src/com/coursemanagement/Files/" + temporaryFile);
            FileWriter temp = new FileWriter("src/com/coursemanagement/Files/" + temporaryFile);

            if (temporary.createNewFile()) {
                System.out.println("File created:" + temporary.getName());
            } else {
                System.out.println("File exists");
            }

            index = 0;
            int break_line = 1;
            for (int i = 0; i < details.size() + 1; i++) {
                if (details.get(index).equals(studentEmail)) {
                    index += 6;
                    i = index;

                } else {
                    temp.write(details.get(index) + " ");
                    if (break_line == 6) {
                        temp.write("\n");
                        break_line = 0;
                    }
                    break_line++;
                    index++;
                }


            }
            temp.close();


        } catch (IOException x) {
            System.out.println("something went wrong");
        }
    }

    private JPanel panelOne() {
        ArrayList dataShow = new ArrayList();
        JLabel firstNameLabel, lastNameLabel, addressLabel, phoneNumberLabel, parentNameLabel, selectLevelLabel, emailLabel,
                chooseCourseLabel, studentNameHeading;
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(new TitledBorder(" Student Details:"));


        Iterator itr = details.iterator();

        while (itr.hasNext()) {
            if (itr.next().equals(studentEmail)) {

                for (int i = 0; i < 5; i++) {
                    dataShow.add(itr.next());
                }
            }
        }
        dataShow.add(studentEmail);

        for (int i = 0; i < dataShow.size(); i++) {
            System.out.println(dataShow.get(i));
        }
        String studentFirstName = dataShow.get(0).toString();


        studentNameHeading = new JLabel("'Are you sure details are correct?' ");
        studentNameHeading.setFont(new Font(Font.DIALOG, Font.ITALIC, 13));
        studentNameHeading.setForeground(new Color(255, 0, 0));
        studentNameHeading.setBounds(210, 10, 200, 25);
        panel.add(studentNameHeading);


        firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        firstNameLabel.setBounds(10, 50, 120, 25);
        panel.add(firstNameLabel);


        firstName = new JTextField();
        firstName.setText(dataShow.get(0).toString());
        firstName.setBounds(100, 50, 160, 25);
        panel.add(firstName);


        lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Font.DIALOG", Font.PLAIN, 15));
        lastNameLabel.setBounds(300, 50, 120, 25);
        panel.add(lastNameLabel);


        lastName = new JTextField();
        lastName.setText(dataShow.get(1).toString());
        lastName.setBounds(400, 50, 160, 25);
        panel.add(lastName);


        addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        addressLabel.setBounds(10, 90, 120, 25);
        panel.add(addressLabel);


        address = new JTextField();
        address.setText(dataShow.get(2).toString());
        address.setBounds(100, 90, 160, 25);
        panel.add(address);


        phoneNumberLabel = new JLabel("P.Number:");
        phoneNumberLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        phoneNumberLabel.setBounds(300, 90, 120, 15);
        panel.add(phoneNumberLabel);


        phoneNumber = new JTextField();
        phoneNumber.setText(dataShow.get(3).toString());
        phoneNumber.setBounds(400, 90, 160, 25);
        panel.add(phoneNumber);


        parentNameLabel = new JLabel("P.Name:");
        parentNameLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        parentNameLabel.setBounds(10, 130, 120, 15);
        panel.add(parentNameLabel);


        parentName = new JTextField();
        parentName.setText(dataShow.get(4).toString());
        parentName.setBounds(100, 130, 160, 25);
        panel.add(parentName);


        selectLevelLabel = new JLabel("Level:");
        selectLevelLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        selectLevelLabel.setBounds(300, 130, 120, 25);
        panel.add(selectLevelLabel);


        selectLevel = new JComboBox(new String[]{"Level 4", "Level 5", "Level 6"});
        selectLevel.setBounds(400, 130, 160, 25);
        panel.add(selectLevel);


        chooseCourseLabel = new JLabel("Course:");
        chooseCourseLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        chooseCourseLabel.setBounds(10, 170, 120, 25);
        panel.add(chooseCourseLabel);

        ArrayList courseList = new ArrayList();
        try {
            File courseData = new File("src/com/coursemanagement/Files/courseFile.txt");
            Scanner getCourseData = new Scanner(courseData);
            while (getCourseData.hasNextLine()) {
                courseList.add(getCourseData.nextLine());
            }
            getCourseData.close();

        } catch (FileNotFoundException ex) {
            System.out.println("sql exception in course Table");
        }
        chooseCourse = new JComboBox(courseList.toArray());
        chooseCourse.setBounds(100, 170, 160, 25);
        panel.add(chooseCourse);


        selectLevelLabel = new JLabel("Semester:");
        selectLevelLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        selectLevelLabel.setBounds(300, 170, 120, 25);
        panel.add(selectLevelLabel);


        selectSemester = new JComboBox(new String[]{"1", "2"});
        selectSemester.setBounds(400, 170, 160, 25);
        panel.add(selectSemester);


        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        emailLabel.setBounds(10, 215, 120, 25);
        panel.add(emailLabel);


        email = new JTextField();
        email.setText(dataShow.get(5).toString());
        email.setEditable(false);
        email.setBounds(100, 215, 160, 25);
        panel.add(email);

        checkBox = new JCheckBox("I Approve/Accept that all the information are correct are I will not change any details after.");
        checkBox.setBounds(10, 270, 700, 25);
        panel.add(checkBox);

        saveData = new JButton("Save");
        saveData.setBounds(200, 315, 200, 25);
        panel.add(saveData);


        return panel;
    }

    public void selectLeveAction() {


        saveData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname1, lname1, address1, phone1, pName1, level1, sem1, course1;
                fname1 = firstName.getText().strip();
                lname1 = lastName.getText().strip();
                address1 = address.getText().strip();
                phone1 = phoneNumber.getText().strip();
                pName1 = parentName.getText().strip();
                level1 = selectLevel.getSelectedItem().toString();
                sem1 = selectSemester.getSelectedItem().toString();
                course1 = chooseCourse.getSelectedItem().toString();
                String auto_inc = "";
                System.out.println(fname1 + lname1 + address1 + phone1 + pName1);

                if (fname1.equals("") || lname1.equals("") || address1.equals("") || phone1.equals("") || pName1.equals("")) {

                    JOptionPane.showMessageDialog(message, "Your details field seems to be empty\n" +
                            "make sure to fill details properly", "Warinig Empty details", JOptionPane.WARNING_MESSAGE);
                } else {

                    if (checkBox.isSelected()) {
                        String query = "insert into enrolledstudent values('" + studentEmail + "','" + fname1 + "','" + lname1 + "','" + address1 + "','" + pName1 + "','" + phone1 + "','" + level1 + "','" + sem1 + "','" + course1 + "');";
                        try {
                            conn.statement.executeUpdate(query);


                            File temporary = new File("src/com/coursemanagement/Files/" + temporaryFile);
                            File registerFile = new File("src/com/coursemanagement/Files/registerStudent.txt");
                            if (registerFile.delete()) {
                                System.out.println("Deleted:-" + registerFile.getName() + "File");
                            } else {
                                System.out.println("Failed to delete the file.");
                            }

                            temporary.renameTo(registerFile);


                            ArrayList dataSubjectName = new ArrayList();
                            String query1 = "select ModuleName from courseandsubject where Course = '" + course1 + "' and Level='" + level1 + "' and semester ='" + sem1 + "';";
                            System.out.println(query1);
                            try {
                                resultSet = conn.statement.executeQuery(query1);

                                while (resultSet.next()) {
                                    String data = resultSet.getString("ModuleName");
                                    dataSubjectName.add(data);
                                }
                            } catch (SQLException ex) {
                                System.out.println("something went wrong while fatching data from courseandsubject  table");
                            }

                            if (!level1.equals("Level 6")) {
                                ImageIcon icon = new ImageIcon("src/com/coursemanagement/icons/icon14.jpg");
                                Image img = icon.getImage().getScaledInstance(25, 25, 3);
                                icon = new ImageIcon(img);

                                String text = course1 + " " + level1 + " " + sem1 + "\n";
                                for (int i = 0; i < dataSubjectName.size(); i++) {
                                    text += dataSubjectName.get(i).toString() + "\n";
                                }


                                JTextArea textArea = new JTextArea(5, 20);
                                textArea.setText(text);
                                textArea.setEditable(false);

                                JScrollPane scrollPane = new JScrollPane(textArea);

                                JOptionPane.showMessageDialog(null, scrollPane, "Course Details", JOptionPane.WARNING_MESSAGE, icon);

                            } else {

                                String text = "";
                                //to save subject name in level_6_student table
                                String saveText = "";
                                ArrayList subjectName = new ArrayList();
                                ArrayList optionalSubject = new ArrayList();


                                for (int i = 0; i < dataSubjectName.size(); i++) {
                                    if (dataSubjectName.get(i).toString().startsWith("Opt.")) {

                                        String word = dataSubjectName.get(i).toString();
                                        System.out.println(word);
                                        word = word.replaceAll("Opt.", "");
                                        System.out.println(word);
                                        optionalSubject.add(word);

                                    } else {
                                        text += dataSubjectName.get(i).toString() + "\n";
                                    }
                                }


                                Object[] optionalSub = optionalSubject.toArray();

                                try {
                                    String optionalSubjectSelected = (String) JOptionPane.showInputDialog(null, text + "Choose Subject Semester " + sem1, "Optional Subject", JOptionPane.PLAIN_MESSAGE, null, optionalSub, optionalSub[0]);


                                    try {
                                        String sqlQuery = "insert into Level_6_Student values('" + studentEmail + "','" + course1 + "','" + sem1 + "','" + saveText + "Opt." + optionalSubjectSelected + "');";
                                        conn.statement.executeUpdate(sqlQuery);
                                    } catch (SQLException exce) {
                                        System.out.println(exce);
                                    }
                                } catch (NullPointerException x) {
                                    System.out.println(x.fillInStackTrace());
                                }


                            }

                            dispose();
                            new Student(studentEmail);


                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    } else {
                        JOptionPane.showMessageDialog(message, "Agree to accept all the details are correct and \n" +
                                "chick on check box");
                    }

                }


            }
        });


    }

    private JPanel panelDetails() {
        JPanel panelDetails = new JPanel();
        panelDetails.setLayout(null);
        JLabel addressLabel, phoneNumberLabel, parentNameLabel, selectLevelLabel, emailLabel,
                chooseCourseLabel, fNameLabel, fullname, emailLabelD, phoneD, addressD,
                subjectD, pNameD, courseD;
        ArrayList enrollDataShow = new ArrayList();

        try {

            String query = "select * from enrolledstudent where email='" + studentEmail + "';";
            resultSet = conn.statement.executeQuery(query);

            while (resultSet.next()) {
                for (int i = 1; i <= 9; i++) {
                    String data = resultSet.getString(i);
                    enrollDataShow.add(data);
                }


            }
            resultSet.close();


        } catch (SQLException ex) {
            System.out.println("something went wrong");
        } catch (NullPointerException ex) {
            System.out.println("something went wrong");
            JOptionPane.showMessageDialog(null, "Something went wrong in databases ");
        }

        if (enrollDataShow.isEmpty()) {
            dispose();
            JOptionPane.showMessageDialog(null, " Application is underconstruction\n" +
                    "Wait for sometime or contact to Course Administrator");
        } else {
            String fname = enrollDataShow.get(1).toString();
            String lname = enrollDataShow.get(2).toString();
            String email = studentEmail;
            String sem = enrollDataShow.get(7).toString();
            String phone = enrollDataShow.get(5).toString();
            String pName = enrollDataShow.get(4).toString();
            String level = enrollDataShow.get(6).toString();
            String course = enrollDataShow.get(8).toString();


            ArrayList dataSubject = new ArrayList();

            String query = "select ModuleName from courseandsubject where Level ='" + level + "'and semester='" + sem + "' and course='" + course + "' and ModuleName NOT LIKE 'Opt.%';";
            try {
                resultSet = conn.statement.executeQuery(query);

                while (resultSet.next()) {
                    dataSubject.add(resultSet.getString("ModuleName"));

                }

            } catch (SQLException ex) {
                System.out.println("something went wrong while fatching data from courseandsubject  table level 6");
            }


            if (level.equals("Level 6")) {

                String query1 = "select Subject from level_6_student where Email ='" + studentEmail + "';";
                try {

                    resultSet = conn.statement.executeQuery(query1);
                    while (resultSet.next()) {
                        dataSubject.add(resultSet.getString("Subject"));
                    }
                    resultSet.close();
                } catch (SQLException exe) {
                    System.out.println("problem in sql in level 6 student table while fetching data");
                }

            }


            Object[] subject = dataSubject.toArray();

            if (course.equals("IT")) {
                course = "Information Technology";
            }
            String address = enrollDataShow.get(3).toString();
            String password = enrollDataShow.get(0).toString();

            panelDetails.setBorder(new TitledBorder("Complete Details of " + lname));

            fullname = new JLabel("Full Name:");
            fullname.setBounds(20, 25, 150, 20);
            fullname.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
            panelDetails.add(fullname);

            fNameLabel = new JLabel(fname + " " + lname);
            fNameLabel.setBounds(160, 25, 150, 20);
            fNameLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
            panelDetails.add(fNameLabel);

            chooseCourseLabel = new JLabel("Course Studied:");
            chooseCourseLabel.setBounds(20, 60, 150, 20);
            chooseCourseLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
            panelDetails.add(chooseCourseLabel);

            courseD = new JLabel(course);
            courseD.setBounds(160, 60, 300, 20);
            courseD.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
            panelDetails.add(courseD);

            addressLabel = new JLabel("Address:");
            addressLabel.setBounds(20, 95, 150, 20);
            addressLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
            panelDetails.add(addressLabel);

            addressD = new JLabel(address);
            addressD.setBounds(160, 95, 300, 20);
            addressD.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
            panelDetails.add(addressD);

            emailLabel = new JLabel("Email:");
            emailLabel.setBounds(20, 130, 150, 20);
            emailLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
            panelDetails.add(emailLabel);

            emailLabelD = new JLabel(email);
            emailLabelD.setBounds(160, 130, 300, 17);
            emailLabelD.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
            panelDetails.add(emailLabelD);

            phoneNumberLabel = new JLabel("Phone Number:");
            phoneNumberLabel.setBounds(20, 165, 150, 20);
            phoneNumberLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
            panelDetails.add(phoneNumberLabel);

            phoneD = new JLabel(phone);
            phoneD.setBounds(160, 165, 300, 17);
            phoneD.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
            panelDetails.add(phoneD);

            parentNameLabel = new JLabel("Parent Name:");
            parentNameLabel.setBounds(20, 200, 150, 20);
            parentNameLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
            panelDetails.add(parentNameLabel);

            pNameD = new JLabel(pName + " " + lname);
            pNameD.setBounds(160, 200, 300, 20);
            pNameD.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
            panelDetails.add(pNameD);

            JLabel teacherLabel = new JLabel("Teacher:");
            teacherLabel.setBounds(350, 235, 150, 20);
            teacherLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
            panelDetails.add(teacherLabel);

            JButton teacherBtn = new JButton("Details");
            teacherBtn.setBounds(430, 235, 150, 20);
            teacherBtn.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
            panelDetails.add(teacherBtn);

            int i = 0;


            teacherBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int i = 0;
                    String details = "";
                    for (i = 0; i < subject.length; i++) {
                        String query = "Select Fullname from instructorLogin where Email in (select Email from instructorDetails where ModuleCode = (select ModuleCode from courseandsubject where ModuleName = '" + subject[i] + "'));";

                        try {
                            resultSet = conn.statement.executeQuery(query);
                            ArrayList name = new ArrayList();
                            while (resultSet.next()) {
                                name.add(resultSet.getString("Fullname"));

                            }
                            if (name.isEmpty()) {
                                name.add("Will Assign Soon");
                            }

                            for (int j = 0; j < name.size(); j++) {
                                details += subject[i] + " = " + name.get(j) + "\n";
                            }

                        } catch (SQLException exception) {
                            exception.printStackTrace();
                            JOptionPane.showMessageDialog(message, "something went wrong please try again later");
                        }

                    }
                    JOptionPane.showMessageDialog(message, details);
                }
            });

            if (level.equals("Level 5") && sem.equals("1")) {
                sem = "3";
            } else if (level.equals("Level 5") && sem.equals("2")) {
                sem = "4";
            } else if (level.equals("Level 6") && sem.equals("1")) {
                sem = "5";
            } else if (level.equals("Level 6") && sem.equals("2")) {
                sem = "6";
            }


            selectLevelLabel = new JLabel(level);
            selectLevelLabel.setBounds(400, 25, 300, 18);
            selectLevelLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
            panelDetails.add(selectLevelLabel);

            selectLevelLabel = new JLabel("Semester: " + sem);
            selectLevelLabel.setBounds(400, 50, 300, 18);
            selectLevelLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
            panelDetails.add(selectLevelLabel);

            chooseCourseLabel = new JLabel("Modules:");
            chooseCourseLabel.setBounds(400, 80, 300, 18);
            chooseCourseLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
            panelDetails.add(chooseCourseLabel);

            i = 0;
            int x = 350, y = 110, height = 18, width = 300;
            for (i = 0; i < subject.length; i++) {
                subjectD = new JLabel(" * " + subject[i]);
                subjectD.setBorder(new TitledBorder(""));
                subjectD.setBounds(x, y, width, height);
                y += 30;
                panelDetails.add(subjectD);
            }
        }

        return panelDetails;
    }

}

