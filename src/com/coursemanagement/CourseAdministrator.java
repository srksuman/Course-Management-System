package com.coursemanagement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseAdministrator extends JFrame implements TableDateFetchAdministration {
    private final ArrayList courseList = new ArrayList();
    private final String temporaryFile = "temporary.txt";
    private final CourseAdministrator info = this;
    private final MysqlConnector connector;
    private DefaultTableModel model, model1, model2;
    private JTable table, table1, table2;
    private JTextField addCourseNew, teacherFullname, teacherPassword, teacherEmail, teacherEmailAdd;
    private JButton updateBtn, deleteBtn, cancelBtn, addBtn, clearBtn, ok,
            addTeacherbtn, deleteTeacherBtn, updateTeacherBtn, clearTeacherBtn;
    private JTextField moduleName, moduleCode, moduleCodeTeacher;
    private JComboBox levelOfStudy, studiedCourse, semesterLevel, subjectType;
    private JRadioButton addCourse, addModule, addTeacher, assignTeacher;
    private ResultSet resultSet;

    public CourseAdministrator() {
        setVisible(true);
        JMenu menu;
        JMenuBar mb;
        JMenuItem m1;
        JMenuItem m2;
        connector = new MysqlConnector();
        setBounds(200, 100, 800, 850);
        setMinimumSize(new Dimension(800, 550));
        setResizable(false);

        getInfo();

        buttonsActions();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fetchingCourses();
        teacherButtonActions();
        fetchTeacherDetails();
        fetchAllStudentDetails();
        setTable2Action();
        pack();


        mb = new JMenuBar();
        menu = new JMenu("Quick Services");
        m1 = new JMenuItem("Refresh");
        m2 = new JMenuItem("Help");
        menu.add(m1);
        menu.add(m2);
        mb.add(menu);
        m1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CourseAdministrator();
            }
        });
        m2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Administration are able to  add new courses, add modules to a course, cancel a course, or\n" +
                        "delete a course altogether. They can edit  course names,module names, etc. An administrator\n" +
                        " is able to generate a report/result slip for a student.  Administration also have Ability to \n" +
                        "edit instructor details or to remove instructors from modules also.");

            }
        });
        setJMenuBar(mb);
    }


    public static void main(String[] args) {
        new CourseAdministrator();
    }

    private JPanel studentPanel() {
        JPanel studentPanel = new JPanel();
        JScrollPane scrollPane2;
        studentPanel.setBorder(new TitledBorder("Student Details"));

        table2 = new JTable();

        Object[] column = {"Student Email", "First Name", "Last Name", "Address", "Parents Name", "Phone Number", "level", "semester", "Course"};

        Object[][] row = {

        };
        model2 = new DefaultTableModel(row, column);
        table2.setModel(model2);
        table2.setRowSelectionAllowed(false);
        scrollPane2 = new JScrollPane(table2);
        studentPanel.add(scrollPane2);
        return studentPanel;
    }

    private void setTable2Action() {
        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    int row = table2.getSelectedRow();
                    String email = model2.getValueAt(row, 0).toString();
                    String fname = model2.getValueAt(row, 1).toString();
                    String lname = model2.getValueAt(row, 2).toString();
                    String address = model2.getValueAt(row, 3).toString();
                    String pName = model2.getValueAt(row, 4).toString();
                    String pNumber = model2.getValueAt(row, 5).toString();
                    String level = model2.getValueAt(row, 6).toString();
                    String sem = model2.getValueAt(row, 7).toString();
                    String course = model2.getValueAt(row, 8).toString();


                    if (JOptionPane.showConfirmDialog(null, course + "\n" +
                                    level + "\n" + "Semester: " + sem + "\n" +
                                    "Full Name:  " + fname + " " + lname + "\n" +
                                    "Do yo want to review result of " + fname + " ?", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        String sql = "Select Subject,Mark from studentMarks where Email ='" + email + "';";
                        try {
                            ArrayList<Integer> markList = new ArrayList();
                            ArrayList subjectList = new ArrayList();
                            resultSet = connector.statement.executeQuery(sql);

                            while (resultSet.next()) {
                                markList.add(resultSet.getInt("Mark"));
                                subjectList.add(resultSet.getString("Subject"));

                            }

                            if (markList.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Mark is still not given by Instructor");
                            } else {
                                String text = course + "\n" + level + "\n" + "Semester " + sem + "\n" + "Full Name:- " + fname + " " + lname + "\nParent/Guardian Name:- " + pName + " " + lname + "\n" + "" +
                                        "EVERY MODULES CONTAIN FULL MARKS OF 100" + "\n";


                                float total = 0;
                                for (int i = 0; i < markList.size(); i++) {
                                    text += i + 1 + ". " + subjectList.get(i).toString() + " :  " + markList.get(i).toString() + "\n";
                                    total += markList.get(i);
                                }
                                float totalModule = markList.size() * 100;
                                Float percentage = (total / totalModule) * 100;
                                text += "percentage : " + percentage + "%\n";
                                if (percentage >= 90) {
                                    text += "A+    Outstanding";
                                } else if (percentage >= 80 && percentage < 90) {
                                    text += "A     Excellent";
                                } else if (percentage >= 70 && percentage < 80) {
                                    text += "B+     Very Good";
                                } else if (percentage >= 60 && percentage < 70) {
                                    text += "B     Good";
                                } else if (percentage >= 50 && percentage < 60) {
                                    text += "C+     Above Average";
                                } else if (percentage >= 40 && percentage < 50) {
                                    text += "C     Average";
                                } else if (percentage >= 25 && percentage < 40) {
                                    text += "D     Below Average";
                                } else if (percentage >= 1 && percentage < 25) {
                                    text += "E     Insufficient";
                                } else {
                                    text += "N     Null";
                                }


                                JTextArea textArea = new JTextArea(5, 20);
                                textArea.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 12));
                                textArea.setText(text);


                                JScrollPane scrollPane = new JScrollPane(textArea);

                                JOptionPane.showMessageDialog(info, scrollPane, "Course Details", JOptionPane.WARNING_MESSAGE, null);
                                String[] options = {"Print Result", "Not ready"};

                                int x = JOptionPane.showOptionDialog(null, "Print result in text file \nAre you ready to print it?",
                                        "Click a button",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                                if (x == 0) {
                                    try {
                                        File result = new File("src/com/coursemanagement/Files/Result_" + fname + " " + lname + ".txt");
                                        if (result.createNewFile()) {
                                            System.out.println("File created: " + result.getName());
                                        } else {
                                            System.out.println("File  exists.");
                                        }
                                    } catch (IOException ex) {
                                        System.out.println(" error occurred.");
                                        ex.printStackTrace();
                                    }
                                    try {
                                        FileWriter writeResult = new FileWriter("src/com/coursemanagement/Files/Result_" + fname + " " + lname + ".txt");
                                        writeResult.write(text);
                                        writeResult.close();
                                        System.out.println("Successfully wrote ");
                                        JOptionPane.showMessageDialog(null, "Result is prented in file named as \nResult_" + fname + " " + lname + ".txt");
                                    } catch (IOException ex) {
                                        System.out.println(" error occurred.");
                                        ex.printStackTrace();
                                    }

                                } else {
                                    System.out.println("not ready ");
                                }

                            }

                        } catch (SQLException exception) {
                            exception.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Something went wrong try again later");
                        }

                    } else {

                    }


                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(info, " something went wrong  try again later..");
                }
            }
        });
    }

    public void fetchAllStudentDetails() {
        try {
            String query = "Select * from enrolledStudent ;";
            resultSet = connector.statement.executeQuery(query);
            while (resultSet.next()) {
                Object[] rowData =
                        {
                                resultSet.getString("Email"),
                                resultSet.getString("fname"),
                                resultSet.getString("lname"),
                                resultSet.getString("address"),
                                resultSet.getString("Pname"),
                                resultSet.getString("Pnumber"),
                                resultSet.getString("level"),
                                resultSet.getString("semester"),
                                resultSet.getString("course")
                        };
                model2.addRow(rowData);
            }
            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "something went wrong please try again later");
        } catch (NullPointerException ex) {
            ex.printStackTrace();

        }
    }

    private void getInfo() {

        JTabbedPane pane = new JTabbedPane(JTabbedPane.LEFT);
        pane.setBackground(new Color(255, 255, 255));
        pane.setFocusable(false);

        ImageIcon icon2 = new ImageIcon("src/com/coursemanagement/icons/logo1.jpg");
        Image img2 = icon2.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(img2);
        pane.addTab("Courses & Modules", icon2, getModulePanel());
        add(pane);

        ImageIcon icon1 = new ImageIcon("src/com/coursemanagement/icons/logo1.jpg");
        Image img1 = icon1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(img1);
        pane.addTab("Details and Results", icon1, studentPanel());


        ImageIcon icon3 = new ImageIcon("src/com/coursemanagement/icons/logo1.jpg");
        Image img3 = icon3.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon3 = new ImageIcon(img3);
        pane.addTab("Teacher Details", icon3, getTeacherPanel());
        add(pane);


    }

    private JPanel getTeacherPanel() {
        JPanel teacherPanel = new JPanel();
        teacherPanel.setBorder(BorderFactory.createBevelBorder(1));
        teacherPanel.setLayout(new GridLayout(1, 2));
        teacherPanel.add((getTeacherPanelOne()));
        teacherPanel.add(getTeacherPanelTwo());


        return teacherPanel;
    }

    private JPanel getTeacherPanelOne() {
        JPanel teacherPanelOne = new JPanel();
        ButtonGroup  bg2;
        teacherPanelOne.setBorder(BorderFactory.createBevelBorder(1));
        teacherPanelOne.setLayout(null);

        addTeacher = new JRadioButton("Add Teacher");
        addTeacher.setBounds(10, 10, 150, 40);
        teacherPanelOne.add(addTeacher);


        assignTeacher = new JRadioButton("Assign teacher");
        assignTeacher.setBounds(160, 10, 150, 40);
        teacherPanelOne.add(assignTeacher);
        assignTeacher.setSelected(true);

        bg2 = new ButtonGroup();
        bg2.add(addTeacher);
        bg2.add(assignTeacher);


        ok = new JButton("OK");
        ok.setFocusable(false);
        ok.setBackground(new Color(2));
        ok.setForeground(new Color(255, 255, 255));
        ok.setBounds(340, 15, 80, 25);
        teacherPanelOne.add(ok);


        JLabel fullName = new JLabel("Teacher Fullname");
        fullName.setBounds(10, 50, 300, 25);
        fullName.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        teacherPanelOne.add(fullName);

        teacherFullname = new JTextField();
        teacherFullname.setBounds(10, 80, 450, 25);
        teacherPanelOne.add(teacherFullname);


        JLabel teacherEmailLabel = new JLabel("Teacher Email");
        teacherEmailLabel.setBounds(10, 110, 300, 15);
        teacherEmailLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        teacherPanelOne.add(teacherEmailLabel);

        teacherEmail = new JTextField();
        teacherEmail.setBounds(10, 140, 450, 25);
        teacherPanelOne.add(teacherEmail);


        JLabel teacherPasswordLabel = new JLabel("Password");
        teacherPasswordLabel.setBounds(10, 170, 300, 25);
        teacherPasswordLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        teacherPanelOne.add(teacherPasswordLabel);

        teacherPassword = new JTextField();
        teacherPassword.setBounds(10, 200, 450, 25);
        teacherPanelOne.add(teacherPassword);

        JSeparator sep = new JSeparator();
        sep.setBackground(new Color(0, 0, 0));
        sep.setBounds(10, 250, 450, 15);
        sep.setOrientation(SwingConstants.HORIZONTAL);
        teacherPanelOne.add(sep);

        JLabel subject = new JLabel("Module Code");
        subject.setBounds(10, 270, 450, 15);
        subject.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        teacherPanelOne.add(subject);

        moduleCodeTeacher = new JTextField();
        moduleCodeTeacher.setBounds(10, 300, 450, 25);
        teacherPanelOne.add(moduleCodeTeacher);


        JLabel teacherEmaillabel = new JLabel("Teacher Email");
        teacherEmaillabel.setBounds(10, 330, 450, 15);
        teacherEmaillabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        teacherPanelOne.add(teacherEmaillabel);

        teacherEmailAdd = new JTextField();
        teacherEmailAdd.setBounds(10, 360, 450, 25);
        teacherPanelOne.add(teacherEmailAdd);

        addTeacherbtn = new JButton("ADD");
        addTeacherbtn.setBounds(10, 390, 180, 30);
        addTeacherbtn.setFocusable(false);
        addTeacherbtn.setBackground(new Color(2));
        addTeacherbtn.setForeground(new Color(255, 255, 255));
        teacherPanelOne.add(addTeacherbtn);

        updateTeacherBtn = new JButton("Update");
        updateTeacherBtn.setBounds(280, 390, 180, 30);
        updateTeacherBtn.setFocusable(false);
        updateTeacherBtn.setBackground(new Color(2));
        updateTeacherBtn.setForeground(new Color(255, 255, 255));
        teacherPanelOne.add(updateTeacherBtn);


        deleteTeacherBtn = new JButton("Delete");
        deleteTeacherBtn.setBounds(10, 430, 180, 30);
        deleteTeacherBtn.setFocusable(false);
        deleteTeacherBtn.setBackground(new Color(2));
        deleteTeacherBtn.setForeground(new Color(255, 255, 255));
        teacherPanelOne.add(deleteTeacherBtn);

        clearTeacherBtn = new JButton("Clear");
        clearTeacherBtn.setBounds(280, 430, 180, 30);
        clearTeacherBtn.setFocusable(false);
        clearTeacherBtn.setBackground(new Color(2));
        clearTeacherBtn.setForeground(new Color(255, 255, 255));
        teacherPanelOne.add(clearTeacherBtn);


        teacherFullname.setEditable(false);
        teacherEmail.setEditable(false);
        teacherPassword.setEditable(false);


        return teacherPanelOne;
    }

    private void teacherButtonActions() {
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addTeacher.isSelected()) {
                    teacherFullname.setEditable(true);
                    teacherEmail.setEditable(true);
                    teacherPassword.setEditable(true);

                    teacherEmailAdd.setEditable(false);
                    moduleCodeTeacher.setEditable(false);

                }
                if (assignTeacher.isSelected()) {
                    teacherFullname.setEditable(false);
                    teacherEmail.setEditable(false);
                    teacherPassword.setEditable(false);


                    teacherEmailAdd.setEditable(true);
                    moduleCodeTeacher.setEditable(true);

                }
            }
        });

        addTeacherbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addTeacher.isSelected()) {
                    String teacherName = teacherFullname.getText();
                    String teacherEmailEntry = teacherEmail.getText();
                    String teacherPasswordEntry = teacherPassword.getText();
                    try {
                        String sql = "INSERT INTO  instructorlogin values ('" + teacherEmailEntry + "','" + teacherPasswordEntry + "','" + teacherName + "');";
                        connector.statement.executeUpdate(sql);
                        clearTeacherBtn.doClick();

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(info, "Something unusual happened");
                        ex.printStackTrace();
                    }
                }

                if (assignTeacher.isSelected()) {


                    String tecEm = teacherEmailAdd.getText().strip();
                    String mdCod = moduleCodeTeacher.getText().strip();


                    if (mdCod.isEmpty() || tecEm.isEmpty()) {
                        JOptionPane.showMessageDialog(info, "Cannot leave the field empty");
                    } else {
                        try {
                            String lvl = "";
                            String sm = "";
                            String coTec = "";
                            String sql2 = "Select Email from instructorlogin where Email = '" + tecEm + "' ";
                            String emailEmpty = "";


                            resultSet = connector.statement.executeQuery(sql2);
                            while (resultSet.next()) {
                                emailEmpty = resultSet.getString("Email");
                            }
                            System.out.println(emailEmpty);
                            String sql3 = "Select ModuleCode from courseandsubject where ModuleCode = '" + mdCod + "' ";
                            String moduleCodeEmpty = "";
                            resultSet = connector.statement.executeQuery(sql3);
                            while (resultSet.next()) {
                                moduleCodeEmpty = resultSet.getString("ModuleCode");
                            }
                            System.out.println(moduleCodeEmpty);
                            if (!emailEmpty.isEmpty() && !moduleCodeEmpty.isEmpty()) {
                                String sql1 = "Select ModuleCode,Course,Level,semester FROM courseandsubject WHERE ModuleCode = '" + mdCod + "';";
                                resultSet = connector.statement.executeQuery(sql1);
                                while (resultSet.next()) {
                                    lvl = resultSet.getString("Level");
                                    sm = resultSet.getString("Semester");
                                    coTec = resultSet.getString("Course");
                                }
                                resultSet.close();
                                String query = "Select ModuleCode, Email from instructordetails where ModuleCode ='" + mdCod + "' and Email ='" + emailEmpty + "';";
                                System.out.println(query);
                                resultSet = connector.statement.executeQuery(query);
                                String md = "";
                                String em = "";
                                while (resultSet.next()) {
                                    md = resultSet.getString("ModuleCode");
                                    em = resultSet.getString("Email");
                                }
                                resultSet.close();
                                if (!md.equals(mdCod) && !emailEmpty.equals(em)) {

                                    String query2 = "SELECT COUNT(Email) as Count FROM instructorDetails  WHERE Email='" + emailEmpty + "';";
                                    int count = 0;
                                    resultSet = connector.statement.executeQuery(query2);
                                    while (resultSet.next()) {
                                        count = resultSet.getInt("Count");
                                    }

                                    System.out.println(count);
                                    if (count < 4) {
                                        String sql = "INSERT INTO instructorDetails values ('" + mdCod + "','" + tecEm + "','" + coTec + "','" + lvl + "','" + sm + "');";

                                        connector.statement.executeUpdate(sql);


                                        Object[] rowData = {
                                                mdCod, tecEm, coTec, lvl, sm
                                        };
                                        model1.addRow(rowData);
                                        clearTeacherBtn.doClick();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Cannot assign one Instructor for more then four module");
                                    }

                                } else {
                                    JOptionPane.showMessageDialog(null, "Same teacher cannot be assign to same module twice");
                                }

                            } else {
                                JOptionPane.showMessageDialog(info, "Instructor email or module code is incorrect");
                            }


                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(info, "Something went unusual try again later " +
                                    "\n  May be you entered wrong Module code\n Try again later and make sure you entered correct details ");
                            ex.printStackTrace();
                        }
                    }
                }

            }
        });

        clearTeacherBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacherFullname.setText("");
                teacherEmail.setText("");
                teacherPassword.setText("");
                teacherEmailAdd.setText("");
                moduleCodeTeacher.setText("");
            }
        });

        deleteTeacherBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (addTeacher.isSelected()) {
                    JOptionPane.showMessageDialog(info, "Cannot delete teacher");
                }
                if (assignTeacher.isSelected()) {


                    try {
                        int i = table1.getSelectedRow();
                        if (i >= 0) {
                            int res = JOptionPane.showConfirmDialog(deleteTeacherBtn, "Teacher data will be removed  ? Are you sure do you want to continue..");
                            if (res == 0) {

                                String deleteSymbolEmail = model1.getValueAt(i, 1).toString();
                                String deleteSymbolModuleCode = model1.getValueAt(i, 0).toString();
                                String query = "delete from   instructordetails where Email = '" + deleteSymbolEmail + "' and ModuleCode ='" + deleteSymbolModuleCode + "';";
                                model1.removeRow(i);
                                try {
                                    connector.statement.executeUpdate(query);
                                } catch (SQLException ex) {
                                    System.out.println("something went wrong in deleting process of course and student");
                                    ex.printStackTrace();
                                }

                                clearTeacherBtn.doClick();
                                JOptionPane.showMessageDialog(info, "Removed data successfully");
                            } else if (res == 1) {
                                JOptionPane.showMessageDialog(info, "Access  cancelled");
                            } else {

                            }
                        } else {
                            JOptionPane.showMessageDialog(info, "Please Select roe delete");
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(info, "something went wrong please try again..");
                    }


                }

            }
        });


        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    int row = table1.getSelectedRow();

                    moduleCodeTeacher.setText(model1.getValueAt(row, 0).toString());
                    teacherEmailAdd.setText(model1.getValueAt(row, 1).toString());

                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(info, " something went wrong  try again later..");
                }
            }
        });


        updateTeacherBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (assignTeacher.isSelected()) {


                    try {
                        int rowNo = table1.getSelectedRow();
                        String updateSymbolEmail = model1.getValueAt(rowNo, 1).toString();
                        String updateSymbolModuleCode = model1.getValueAt(rowNo, 0).toString();
                        if (rowNo >= 0) {
                            try {
                                String tecEm = teacherEmailAdd.getText().strip();
                                String mdCod = moduleCodeTeacher.getText().strip();


                                if (tecEm.isEmpty() || mdCod.isEmpty()) {

                                    JOptionPane.showMessageDialog(info, "Empty field cannot be updated", "Warning", JOptionPane.WARNING_MESSAGE);
                                } else {


                                    int i = JOptionPane.showConfirmDialog(updateTeacherBtn, "Are you sure you want to update data??");
                                    if (i == 0) {
                                        try {

                                            String sql2 = "Select Email from instructorlogin where Email = '" + tecEm + "' ";
                                            String emailEmpty = "";
                                            resultSet = connector.statement.executeQuery(sql2);
                                            while (resultSet.next()) {
                                                emailEmpty = resultSet.getString("Email");
                                            }
                                            String sql3 = "Select ModuleCode from courseandsubject where ModuleCode = '" + mdCod + "' ";
                                            String moduleCodeEmpty = "";
                                            resultSet = connector.statement.executeQuery(sql3);

                                            while (resultSet.next()) {
                                                moduleCodeEmpty = resultSet.getString("ModuleCode");
                                            }
                                            if (!emailEmpty.isEmpty() && !moduleCodeEmpty.isEmpty()) {
                                                String lvl = "";
                                                String sm = "";
                                                String coTec = "";
                                                String query = "Select Course,Level,semester FROM courseandsubject WHERE ModuleCode = '" + mdCod + "'; ";
                                                resultSet = connector.statement.executeQuery(query);
                                                while (resultSet.next()) {
                                                    lvl = resultSet.getString("Level");
                                                    sm = resultSet.getString("Semester");
                                                    coTec = resultSet.getString("Course");
                                                }
                                                if (!lvl.isEmpty() || !sm.isEmpty() || !coTec.isEmpty()) {


                                                    String query2 = "UPDATE instructordetails SET ModuleCode = '" + mdCod + "' , Email = '" + tecEm + "' , Course ='" + coTec + "' , Level ='" + lvl + "' , Semester ='" + sm + "' WHERE " +
                                                            "ModuleCode ='" + updateSymbolModuleCode + "' and Email ='" + updateSymbolEmail + "' ;";
                                                    connector.statement.executeUpdate(query2);


                                                    model1.setValueAt(mdCod, rowNo, 0);
                                                    model1.setValueAt(tecEm, rowNo, 1);
                                                    model1.setValueAt(coTec, rowNo, 2);
                                                    model1.setValueAt(lvl, rowNo, 3);
                                                    model1.setValueAt(sm, rowNo, 4);
                                                    JOptionPane.showMessageDialog(info, " updated Succesfully");
                                                    clearTeacherBtn.doClick();

                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(info, "Problem in email or code check and try again");
                                            }


                                        } catch (SQLException ex) {
                                            JOptionPane.showMessageDialog(info, "something went wrong please try again..");
                                            ex.printStackTrace();
                                            System.out.println("something went wrong");
                                        }


                                    } else if (i == 1) {
                                        JOptionPane.showMessageDialog(info, "Update cancelled");
                                        clearTeacherBtn.doClick();
                                    } else {

                                    }

                                }

                            } catch (NumberFormatException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(info, "Plesae select  first to update", "Warning", JOptionPane.WARNING_MESSAGE);

                        }


                    } catch (ArrayIndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(info, "something went wrong please try again..");
                    }


                }


            }
        });


    }

    public void fetchTeacherDetails() {
        String sql = "select * from instructordetails; ";
        try {
            resultSet = connector.statement.executeQuery(sql);
            while (resultSet.next()) {
                Object[] rowData =
                        {
                                resultSet.getString("ModuleCode"),
                                resultSet.getString("Email"),
                                resultSet.getString("Course"),
                                resultSet.getString("Level"),
                                resultSet.getString("semester")
                        };
                model1.addRow(rowData);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private JPanel getTeacherPanelTwo() {
        JPanel teacherPanelTwo = new JPanel();
        JScrollPane scrollPane1;
        teacherPanelTwo.setBorder(new TitledBorder("Details"));

        table1 = new JTable();
        Object[] column = {"Module Code", "Teacher Email", "Course", "Level", "Semester"};
        Object[][] row = {

        };
        model1 = new DefaultTableModel(row, column);
        table1.setModel(model1);
        scrollPane1 = new JScrollPane(table1);
        teacherPanelTwo.add(scrollPane1);

        return teacherPanelTwo;
    }

    private JPanel getModulePanel() {
       JPanel modulePanel = new JPanel();

        modulePanel.setLayout(new GridLayout(1, 2));

        modulePanel.add(getModulePanelOne());
        modulePanel.add(getModulePanelOne("Admin panel"));


        return modulePanel;
    }

    private JPanel getModulePanelOne() {

       JPanel modulePanelOne = new JPanel();
        ButtonGroup bg;
        JLabel studentDetailsLabel;
        modulePanelOne.setLayout(null);

        modulePanelOne.setBorder(BorderFactory.createBevelBorder(1));


        addCourse = new JRadioButton("Courses");
        addCourse.setBounds(10, 10, 120, 25);
        addCourse.setFont(new Font("Traditional serif", Font.PLAIN, 15));
        addModule = new JRadioButton("Modules");
        addModule.setBounds(150, 10, 120, 25);
        addModule.setFont(new Font("Traditional serif", Font.PLAIN, 15));


        bg = new ButtonGroup();
        bg.add(addCourse);
        bg.add(addModule);

        JButton done = new JButton("Done");
        done.setFocusable(false);
        done.setBounds(350, 10, 80, 25);
        done.setFont(new Font("Traditional serif", Font.PLAIN, 15));
        done.setBackground(new Color(0, 0, 0));
        done.setForeground(new Color(255, 255, 255));


        modulePanelOne.add(addCourse);
        modulePanelOne.add(addModule);
        modulePanelOne.add(done);


        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addCourse.isSelected()) {

                    moduleName.setEditable(false);
                    levelOfStudy.setEnabled(false);
                    semesterLevel.setEnabled(false);
                    addCourseNew.setEditable(true);
                    moduleCode.setEditable(false);
                    table.setEnabled(false);
                    fetchCourses();
                    studiedCourse.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            addCourseNew.setText(studiedCourse.getSelectedItem().toString().strip());
                        }
                    });
                }
                if (addModule.isSelected()) {
                    table.setEnabled(true);
                    moduleName.setEditable(true);
                    levelOfStudy.setEnabled(true);
                    semesterLevel.setEnabled(true);
                    addCourseNew.setEditable(false);
                    moduleCode.setEditable(true);
                    fetchCourses();
                }
            }
        });

        addModule.setSelected(true);

        JLabel addCourseLabel = new JLabel("Enter the name of course to add:-");
        addCourseLabel.setBounds(10, 40, 350, 25);
        addCourseLabel.setFont(new Font("Traditional serif", Font.PLAIN, 15));
        modulePanelOne.add(addCourseLabel);

        addCourseNew = new JTextField();
        addCourseNew.setBounds(10, 70, 180, 25);
        addCourseNew.setEditable(false);
        modulePanelOne.add(addCourseNew);


        studentDetailsLabel = new JLabel("Select course:-");
        studentDetailsLabel.setBounds(10, 100, 180, 25);
        studentDetailsLabel.setFont(new Font("Traditional serif", Font.PLAIN, 15));
        modulePanelOne.add(studentDetailsLabel);


        fetchCourses();
        studiedCourse = new JComboBox(courseList.toArray());
        studiedCourse.setBounds(10, 130, 180, 25);
        modulePanelOne.add(studiedCourse);

        studentDetailsLabel = new JLabel("Select Level to add course:-");
        studentDetailsLabel.setFont(new Font("Traditional serif", Font.PLAIN, 15));
        studentDetailsLabel.setBounds(280, 100, 200, 25);
        modulePanelOne.add(studentDetailsLabel);

        levelOfStudy = new JComboBox(new String[]{"Level 4", "Level 5", "Level 6"});
        levelOfStudy.setBounds(280, 130, 180, 25);
        modulePanelOne.add(levelOfStudy);


        levelOfStudy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String getLevel = levelOfStudy.getSelectedItem().toString();
                subjectType.setEnabled(getLevel.equals("Level 6"));


            }
        });

        studentDetailsLabel = new JLabel("Select Semester to add course:-");
        studentDetailsLabel.setFont(new Font("Traditional serif", Font.PLAIN, 15));
        studentDetailsLabel.setBounds(10, 160, 180, 25);
        modulePanelOne.add(studentDetailsLabel);

        semesterLevel = new JComboBox(new String[]{"1", "2"});
        semesterLevel.setBounds(10, 190, 180, 25);
        modulePanelOne.add(semesterLevel);

        studentDetailsLabel = new JLabel("Select subject type:-");
        studentDetailsLabel.setBounds(280, 160, 180, 25);
        studentDetailsLabel.setFont(new Font("Traditional serif", Font.PLAIN, 15));
        modulePanelOne.add(studentDetailsLabel);

        subjectType = new JComboBox(new String[]{"compulsory", "Optional"});
        subjectType.setBounds(280, 190, 180, 25);
        modulePanelOne.add(subjectType);
        subjectType.setEnabled(false);

        studentDetailsLabel = new JLabel("Enter Module Name:-");
        studentDetailsLabel.setFont(new Font("Traditional serif", Font.PLAIN, 15));
        studentDetailsLabel.setBounds(10, 220, 200, 25);
        modulePanelOne.add(studentDetailsLabel);

        moduleName = new JTextField();
        moduleName.setBounds(10, 250, 180, 25);
        modulePanelOne.add(moduleName);


        studentDetailsLabel = new JLabel("Enter Module Code:-");
        studentDetailsLabel.setFont(new Font("Traditional serif", Font.PLAIN, 15));
        studentDetailsLabel.setBounds(280, 220, 180, 25);
        modulePanelOne.add(studentDetailsLabel);

        moduleCode = new JTextField();
        moduleCode.setBounds(280, 250, 180, 25);
        modulePanelOne.add(moduleCode);


        addBtn = new JButton("Add");
        addBtn.setBackground(Color.BLACK);
        addBtn.setForeground(Color.GRAY);
        addBtn.setFocusable(false);
        addBtn.setBounds(10, 300, 180, 30);
        modulePanelOne.add(addBtn);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(280, 300, 180, 30);
        updateBtn.setBackground(Color.BLACK);
        updateBtn.setForeground(Color.GRAY);
        updateBtn.setFocusable(false);
        modulePanelOne.add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(Color.BLACK);
        deleteBtn.setForeground(Color.GRAY);
        deleteBtn.setFocusable(false);
        deleteBtn.setBounds(10, 350, 180, 30);
        modulePanelOne.add(deleteBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.setForeground(Color.GRAY);
        cancelBtn.setFocusable(false);
        cancelBtn.setBounds(280, 350, 180, 30);
        modulePanelOne.add(cancelBtn);


        clearBtn = new JButton("Clear");
        clearBtn.setBackground(Color.BLACK);
        clearBtn.setForeground(Color.GRAY);
        clearBtn.setFocusable(false);
        clearBtn.setBounds(10, 400, 180, 30);
        modulePanelOne.add(clearBtn);


        return modulePanelOne;
    }

    public void fetchCourses() {
        try {
            courseList.clear();
            File courseData = new File("src/com/coursemanagement/Files/courseFile.txt");
            Scanner getCourseData = new Scanner(courseData);
            while (getCourseData.hasNextLine()) {
                courseList.add(getCourseData.nextLine());
            }
            getCourseData.close();

        } catch (FileNotFoundException ex) {
            System.out.println("sql exception in course Table");
        }
    }

    private void buttonsActions() {

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName;
                if (addCourse.isSelected()) {
                    String cancelCourse = "Select * from cancelcourse;";
                    ArrayList addCourseCancel = new ArrayList();

                    try {
                        resultSet = connector.statement.executeQuery(cancelCourse);
                        while (resultSet.next()) {
                            addCourseCancel.add(resultSet.getString("CancelledCourses"));
                        }


                        courseName = addCourseNew.getText().toUpperCase().strip();

                        if (addCourseCancel.contains(courseName)) {

                            ArrayList cancelCourseList = new ArrayList();
                            try {
                                String sql = "SELECT * FROM cancelCourse";
                                resultSet = connector.statement.executeQuery(sql);
                                while (resultSet.next()) {
                                    cancelCourseList.add(resultSet.getString("CancelledCourses"));
                                }
                                if (cancelCourseList.isEmpty()) {
                                    JOptionPane.showMessageDialog(info, "No cancelled courses");
                                } else {

                                    Object[] courses = cancelCourseList.toArray();
                                    String returnResultCourses = (String) JOptionPane.showInputDialog(info,
                                            "Choose to see Uncancel Courses",
                                            "Select Option",
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            courses,
                                            courses[0]);

                                    FileWriter myCourseWriter = new FileWriter("src/com/coursemanagement/Files/courseFile.txt", true);
                                    myCourseWriter.write(returnResultCourses + "\n");
                                    myCourseWriter.close();


                                    String sql2 = "INSERT INTO courseAndSubject  (ModuleCode, ModuleName, Course, Level, semester)\n" +
                                            "SELECT * FROM cancelcoursesDetails WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql2);

                                    String sql3 = "delete from cancelcoursesDetails where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql3);

                                    String sql4 = "delete from cancelcourse where CancelledCourses = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql4);


                                    String sql12 = "INSERT INTO EnrolledStudent (email, fname,lname,address,Pname,Pnumber, level, semester, course)\n" +
                                            "SELECT * FROM cancelenrolledStudent WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql12);

                                    String sql5 = "delete from cancelenrolledStudent where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql5);


                                    String sql6 = "INSERT INTO InstructorDetails (ModuleCode, Email, Course, Level, semester)\n" +
                                            "SELECT * FROM cancelInstructorDetails WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql6);

                                    String sql7 = "delete from cancelInstructorDetails where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql7);

                                    String sql10 = "INSERT INTO level_6_student (Email, Course, semester,Subject)\n" +
                                            "SELECT * FROM cancellevel_6_student WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql10);

                                    String sql11 = "delete from cancellevel_6_student where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql11);


                                    JOptionPane.showMessageDialog(null, "Refresh choosing the quick service menu option");

                                }


                            } catch (SQLException | IOException ex) {
                                System.out.println("something went wrong");
                                ex.printStackTrace();
                            }


                        } else {
                            try {
                                File fileObj = new File("src/com/coursemanagement/Files/courseFile.txt");
                                if (fileObj.createNewFile()) {
                                    System.out.println("File created:- " + fileObj.getName());
                                } else {
                                    System.out.println("File exists:");
                                }
                            } catch (IOException ex) {
                                System.out.println("something went wrong");
                                ex.printStackTrace();

                            }

                            ArrayList storeCourse = new ArrayList();

                            try {
                                File readingCourse = new File("src/com/coursemanagement/Files/courseFile.txt");
                                Scanner courseRead = new Scanner(readingCourse);
                                while (courseRead.hasNextLine()) {
                                    storeCourse.add(courseRead.nextLine().toLowerCase());

                                }
                                courseRead.close();
                            } catch (FileNotFoundException ex) {
                                System.out.println("something went wrong.");
                                ex.printStackTrace();
                            }

                            if (storeCourse.contains(courseName.toLowerCase())) {
                                JOptionPane.showMessageDialog(info, "The course your trying to enter exists", "Warning", JOptionPane.WARNING_MESSAGE);
                            } else {
                                try {
                                    FileWriter myCourseWriter = new FileWriter("src/com/coursemanagement/Files/courseFile.txt", true);
                                    if (courseName.equals("")) {
                                        JOptionPane.showMessageDialog(null, "Field cannot be empty make sure you enter \n" +
                                                "properly");
                                    } else {
                                        myCourseWriter.write(courseName.toUpperCase() + "\n");
                                        myCourseWriter.close();
                                        JOptionPane.showMessageDialog(null, "Refresh choosing the quick service menu option");
                                    }


                                } catch (IOException ex) {
                                    System.out.println("something went wrong.");
                                    ex.printStackTrace();
                                }

                            }


                        }


                    } catch (SQLException exception) {
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog(null, "something went wrong please try aagain later");
                    }


                }


                if (addModule.isSelected()) {
                    String courseChoosed = "";
                    String moduleAdding = moduleName.getText().strip();
                    String semesterLevelChoosed = semesterLevel.getSelectedItem().toString();
                    String levelOfStudyChoosed = levelOfStudy.getSelectedItem().toString().strip();
                    String moduleCodeAdding = moduleCode.getText().strip();
                    String subjectTypeChoosed = subjectType.getSelectedItem().toString().strip();
                    try {
                        courseChoosed = studiedCourse.getSelectedItem().toString().strip();
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }


                    if (moduleAdding.equals("") || moduleCodeAdding.equals("") || courseChoosed.equals("")) {
                        JOptionPane.showMessageDialog(null, "Empty field cannot be added make sure\n" +
                                "you enter  properly");
                    } else {

                        if (subjectTypeChoosed.equals("Optional")) {

                            moduleAdding = "Opt." + moduleAdding;

                        }

                        try {
                            String query = "Insert into courseAndSubject values ('" + moduleCodeAdding + "','" + moduleAdding + "','" + courseChoosed + "','" + levelOfStudyChoosed + "','" + semesterLevelChoosed + "');";
                            connector.statement.executeUpdate(query);
                            Object[] rowData = {
                                    moduleCodeAdding, moduleAdding, courseChoosed, levelOfStudyChoosed, semesterLevelChoosed
                            };
                            model.addRow(rowData);
                            clearBtn.doClick();

                        } catch (SQLException ex) {
                            System.out.println("something went wrong");
                            ex.printStackTrace();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }


                }


            }
        });


        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studiedCourse.setSelectedIndex(0);
                moduleName.setText("");
                semesterLevel.setSelectedIndex(0);
                levelOfStudy.setSelectedIndex(0);
                moduleCode.setText("");
                subjectType.setSelectedIndex(0);
                addCourseNew.setText("");
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addModule.isSelected()) {
                    try {
                        int getRowIndex = table.getSelectedRow();
                        if (getRowIndex >= 0) {
                            int result = JOptionPane.showConfirmDialog(deleteBtn, "Data will be deleted forever.\nAre yoiu sure you want to delete?");
                            if (result == 0) {

                                String deleteSymbol = model.getValueAt(getRowIndex, 0).toString();
                                String query = "delete from  courseandsubject where ModuleCode = '" + deleteSymbol + "';";
                                String query2 = "delete from instructordetails where ModuleCode = '" + deleteSymbol + "';";
                                String query3 = "delete from  level_6_student where Subject = ( select ModuleName from courseandsubject where ModuleCode = '" + deleteSymbol + "');";
                                model.removeRow(getRowIndex);
                                try {
                                    connector.statement.executeUpdate(query);
                                    connector.statement.executeUpdate(query2);
                                    connector.statement.executeUpdate(query3);
                                } catch (SQLException ex) {
                                    System.out.println("something went wrong in deleting process of course and student");
                                    ex.printStackTrace();
                                }
                                JOptionPane.showMessageDialog(info, "Data is removed successgully from Module and Courses table \n Teacher table will be updated soon!!");
                            } else if (result == 1) {
                                JOptionPane.showMessageDialog(info, "Cancelled deletion");
                            } else {

                            }
                        } else {
                            JOptionPane.showMessageDialog(info, "no data is selected in table to be deleted\nmake sure you select row  to delete");
                        }

                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("something went wrong ");
                        JOptionPane.showMessageDialog(info, "something went wrong please try again later");
                        ex.printStackTrace();
                    }
                }

                if (addCourse.isSelected()) {

                    try {

                        String getSelectedCourse = "";
                        getSelectedCourse = addCourseNew.getText().strip();

                        if (!getSelectedCourse.equals("")) {
                            if (courseList.contains(getSelectedCourse)) {
                                try {
                                    File temp = new File("src/com/coursemanagement/Files/" + temporaryFile);
                                    if (temp.createNewFile()) {
                                        System.out.println("File created: " + temp.getName());
                                    } else {
                                        System.out.println("File  exists.");
                                    }
                                } catch (IOException ex) {
                                    System.out.println("Something went wrong.");
                                    ex.printStackTrace();
                                }
                                FileWriter writeCourse = new FileWriter("src/com/coursemanagement/Files/" + temporaryFile);
                                for (int i = 0; i < courseList.size(); i++) {

                                    if (!getSelectedCourse.equals(courseList.get(i).toString().strip())) {
                                        writeCourse.write(courseList.get(i).toString().strip() + "\n");

                                    }
                                }
                                writeCourse.close();
                                clearBtn.doClick();
                                File tempFile = new File("src/com/coursemanagement/Files/" + temporaryFile);
                                File courseFile = new File("src/com/coursemanagement/Files/courseFile.txt");
                                courseFile.renameTo(tempFile);
                                if (courseFile.delete()) {
                                    System.out.println("Deleted the file: " + courseFile.getName());
                                } else {
                                    System.out.println("Failed to delete file.");
                                }

                                tempFile.renameTo(courseFile);
                                JOptionPane.showMessageDialog(info, "Course is deleted successfully");
                                String query = "delete from  courseandsubject where Course = '" + getSelectedCourse + "';";
                                String query2 = "delete from instructordetails where Course = '" + getSelectedCourse + "';";
                                String query1 = "delete from enrolledstudent where Course = '" + getSelectedCourse + "';";
                                try {
                                    connector.statement.executeUpdate(query);
                                    connector.statement.executeUpdate(query1);
                                    connector.statement.executeUpdate(query2);
                                } catch (SQLException exception) {
                                    exception.printStackTrace();
                                    JOptionPane.showMessageDialog(null, "something went wrong please try again later");
                                }
                                JOptionPane.showMessageDialog(null, "Refresh choosing the quick service menu option");

                            } else {
                                JOptionPane.showMessageDialog(info, "No such course found to delete ");
                            }
                        } else {
                            JOptionPane.showMessageDialog(info, "Select course to delete\nfrom above course list");
                        }


                    } catch (IOException ex) {
                        System.out.println("Something went wrong.");
                        ex.printStackTrace();
                    }


                }

            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    int row = table.getSelectedRow();


                    moduleName.setText(model.getValueAt(row, 1).toString());
                    moduleCode.setText(model.getValueAt(row, 0).toString());
                    studiedCourse.setSelectedItem(model.getValueAt(row, 2).toString());
                    levelOfStudy.setSelectedItem(model.getValueAt(row, 3).toString());
                    semesterLevel.setSelectedItem(model.getValueAt(row, 4).toString());

                    String name = moduleName.getText();
                    if (name.startsWith("Opt.")) {
                        name = name.replaceAll("Opt.", "");
                        subjectType.setSelectedIndex(1);
                    } else {
                        subjectType.setSelectedIndex(0);
                    }
                    moduleName.setText(name);

                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(info, " something went wrong  try again later..");
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addModule.isSelected()) {
                    try {
                        int getRowNumber = table.getSelectedRow();

                        if (getRowNumber >= 0) {
                            String updateSymbol = model.getValueAt(getRowNumber, 0).toString();
                            String updateSymbolCourse = model.getValueAt(getRowNumber, 2).toString();
                            String updateSymbolLevel = model.getValueAt(getRowNumber, 3).toString();
                            String updateSymbolModule = model.getValueAt(getRowNumber, 1).toString();
                            String updateSymbolSemester = model.getValueAt(getRowNumber, 4).toString();
                            String stdCourse = studiedCourse.getSelectedItem().toString();
                            String mdName = moduleName.getText();
                            String sem = semesterLevel.getSelectedItem().toString();
                            String lvlStd = levelOfStudy.getSelectedItem().toString();
                            String mdCode = moduleCode.getText();

                            if (mdName.isEmpty() || mdCode.isEmpty()) {
                                JOptionPane.showMessageDialog(info, "Make sure to fill the form properly");
                            } else {
                                int i = JOptionPane.showConfirmDialog(updateBtn, "Are you sure you want to update data of modules??");
                                if (i == 0) {
                                    try {
                                        String query = "UPDATE courseandsubject SET  ModuleCode ='" + mdCode + "',ModuleName = '" + mdName + "', Course ='" + stdCourse + "',  Level ='" + lvlStd + "', semester ='" + sem + "' WHERE ModuleCode ='" + updateSymbol + "' ; ";
                                        String query1 = "UPDATE enrolledstudent SET  Course ='" + stdCourse + "',  Level ='" + lvlStd + "', semester ='" + sem + "' WHERE Course ='" + updateSymbolCourse + "' and level ='" + updateSymbolLevel + "' and semester ='" + updateSymbolSemester + "' ; ";
                                        String query2 = "UPDATE instructordetails SET ModuleCode ='" + mdCode + "', Course ='" + stdCourse + "',  Level ='" + lvlStd + "', semester ='" + sem + "' WHERE Course ='" + updateSymbolCourse + "' and level ='" + updateSymbolLevel + "' and semester ='" + updateSymbolSemester + "' ; ";
                                        String query3 = "UPDATE level_6_student SET  Course ='" + stdCourse + "', semester ='" + sem + "', Subject ='" + mdName + "' WHERE Course ='" + updateSymbolCourse + "'  and semester ='" + updateSymbolSemester + "' and Subject='" + updateSymbolModule + "'; ";

                                        connector.statement.executeUpdate(query);
                                        connector.statement.executeUpdate(query1);
                                        connector.statement.executeUpdate(query2);
                                        connector.statement.executeUpdate(query3);

                                        model.setValueAt(mdCode, getRowNumber, 0);
                                        model.setValueAt(mdName, getRowNumber, 1);
                                        model.setValueAt(stdCourse, getRowNumber, 2);
                                        model.setValueAt(lvlStd, getRowNumber, 3);
                                        model.setValueAt(sem, getRowNumber, 4);
                                        clearBtn.doClick();

                                        JOptionPane.showMessageDialog(info, "Details are updated in course and module table \n Details in other table will be also be updated");
                                    } catch (SQLException ex) {
                                        System.out.println("something went wrong while updating data");
                                        ex.printStackTrace();
                                    }


                                } else if (i == 1) {
                                    JOptionPane.showMessageDialog(info, "Data to be updation cancelled");
                                    clearBtn.doClick();
                                } else {

                                }
                            }


                        } else {
                            JOptionPane.showMessageDialog(info, "make sure you select an data from table\nto updata it");
                        }


                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("something went wrong");
                    }
                }

                if (addCourse.isSelected()) {

                    try {
                        String getSelectedCourse = "";
                        getSelectedCourse = addCourseNew.getText().strip();
                        if (!getSelectedCourse.equals("")) {
                            try {
                                File temp = new File("src/com/coursemanagement/Files/" + temporaryFile);
                                if (temp.createNewFile()) {
                                    System.out.println("File created: " + temp.getName());
                                } else {
                                    System.out.println("File  exists.");
                                }
                            } catch (IOException ex) {
                                System.out.println("Something went wrong.");
                                ex.printStackTrace();
                            }
                            FileWriter writeCourse = new FileWriter("src/com/coursemanagement/Files/" + temporaryFile);
                            for (int i = 0; i < courseList.size(); i++) {

                                if (studiedCourse.getSelectedItem().toString().equals(courseList.get(i).toString().strip())) {
                                    writeCourse.write(addCourseNew.getText().toUpperCase() + "\n");
                                } else {
                                    writeCourse.write(courseList.get(i).toString().strip() + "\n");
                                }
                            }
                            writeCourse.close();
                            try {
                                String query = "UPDATE courseandsubject SET Course = '" + addCourseNew.getText().toUpperCase() + "' WHERE  Course = '" + studiedCourse.getSelectedItem().toString() + "' ";
                                String query1 = "UPDATE enrolledstudent SET Course = '" + addCourseNew.getText().toUpperCase() + "' WHERE  Course = '" + studiedCourse.getSelectedItem().toString() + "' ";
                                String query2 = "UPDATE instructordetails SET Course = '" + addCourseNew.getText().toUpperCase() + "' WHERE  Course = '" + studiedCourse.getSelectedItem().toString() + "' ";
                                String query3 = "UPDATE level_6_student SET Course = '" + addCourseNew.getText().toUpperCase() + "' WHERE  Course = '" + studiedCourse.getSelectedItem().toString() + "' ";
                                connector.statement.executeUpdate(query);
                                connector.statement.executeUpdate(query1);
                                connector.statement.executeUpdate(query2);
                                connector.statement.executeUpdate(query3);

                            } catch (SQLException ex) {
                                System.out.println("something went wrong ");
                                ex.printStackTrace();
                            }
                            clearBtn.doClick();
                            File tempFile = new File("src/com/coursemanagement/Files/" + temporaryFile);
                            File courseFile = new File("src/com/coursemanagement/Files/courseFile.txt");
                            courseFile.renameTo(tempFile);
                            if (courseFile.delete()) {
                                System.out.println("Deleted the file: " + courseFile.getName());
                            } else {
                                System.out.println("Failed to delete file.");
                            }

                            tempFile.renameTo(courseFile);


                            JOptionPane.showMessageDialog(info, "Course is updated successfully.\nRefresh choosing the quick service menu option");
                            JOptionPane.showMessageDialog(null, "Refresh choosing the quick service menu option");


                        } else {
                            JOptionPane.showMessageDialog(info, "Select course to update\nfrom above course list");
                        }


                    } catch (IOException ex) {
                        System.out.println("Something went wrong.");
                        ex.printStackTrace();
                    }


                }

            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Object[] choices = {"Courses"};
                    String choiceReturn = (String) JOptionPane.showInputDialog(info,
                            "Choose to see cancel data",
                            "Select Option",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            choices,
                            choices[0]);
                    if (choiceReturn.equals("Courses")) {

                        String[] options = {"Add cancel course", "See Cancel Courses"};
                        int result = JOptionPane.showOptionDialog(
                                info,
                                "Choose you option according to your wish?",
                                "User choice", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]
                        );
                        if (result == JOptionPane.YES_OPTION) {
                            Object[] courses = courseList.toArray();
                            String returnResultCourses = (String) JOptionPane.showInputDialog(info,
                                    "Choose to see cancel data",
                                    "Select Option",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    courses,
                                    courses[0]);

                            if (returnResultCourses != null) {
                                try {
                                    String sql = "INSERT INTO cancelCourse value ('" + returnResultCourses + "');";
                                    connector.statement.executeUpdate(sql);


                                    String sql2 = "INSERT INTO cancelcoursesDetails (ModuleCode, ModuleName, Course, Level, semester)\n" +
                                            "SELECT * FROM courseAndSubject WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql2);

                                    String sql3 = "delete from courseandsubject where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql3);


                                    String sql4 = "INSERT INTO cancelEnrolledStudent (email, fname,lname,address,Pname,Pnumber, level, semester, course)\n" +
                                            "SELECT * FROM enrolledStudent WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql4);

                                    String sql5 = "delete from enrolledStudent where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql5);


                                    String sql6 = "INSERT INTO CancelInstructorDetails (ModuleCode, Email, Course, Level, semester)\n" +
                                            "SELECT * FROM InstructorDetails WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql6);

                                    String sql7 = "delete from InstructorDetails where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql7);

                                    String sql10 = "INSERT INTO Cancellevel_6_student (Email, Course, semester,Subject)\n" +
                                            "SELECT * FROM level_6_student WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql10);

                                    String sql11 = "delete from level_6_student where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql11);

                                    try {
                                        try {
                                            File temp = new File("src/com/coursemanagement/Files/" + temporaryFile);
                                            if (temp.createNewFile()) {
                                                System.out.println("File created: " + temp.getName());
                                            } else {
                                                System.out.println("File  exists.");
                                            }
                                        } catch (IOException ex) {
                                            System.out.println("Something went wrong.");
                                            ex.printStackTrace();
                                        }
                                        FileWriter writeCourse = new FileWriter("src/com/coursemanagement/Files/" + temporaryFile);
                                        for (int i = 0; i < courseList.size(); i++) {

                                            if (!returnResultCourses.equals(courseList.get(i).toString().strip())) {
                                                writeCourse.write(courseList.get(i).toString().strip() + "\n");

                                            }
                                        }
                                        writeCourse.close();
                                        clearBtn.doClick();
                                        File tempFile = new File("src/com/coursemanagement/Files/" + temporaryFile);
                                        File courseFile = new File("src/com/coursemanagement/Files/courseFile.txt");
                                        courseFile.renameTo(tempFile);
                                        if (courseFile.delete()) {
                                            System.out.println("Deleted the file: " + courseFile.getName());
                                        } else {
                                            System.out.println("Failed to delete file.");
                                        }

                                        tempFile.renameTo(courseFile);
                                        JOptionPane.showMessageDialog(info, "Course is cancelled successfully\nRefresh choosing the quick service menu option");


                                    } catch (IOException ex) {
                                        System.out.println("Something went wrong.");
                                        ex.printStackTrace();
                                    }


                                } catch (SQLException ex) {
                                    System.out.println("something went wrong");
                                }
                            }


                        } else if (result == JOptionPane.NO_OPTION) {
                            ArrayList cancelCourseList = new ArrayList();
                            try {
                                String sql = "SELECT * FROM cancelCourse";
                                resultSet = connector.statement.executeQuery(sql);
                                while (resultSet.next()) {
                                    cancelCourseList.add(resultSet.getString("CancelledCourses"));
                                }
                                if (cancelCourseList.isEmpty()) {
                                    JOptionPane.showMessageDialog(info, "No cancelled courses");
                                } else {

                                    Object[] courses = cancelCourseList.toArray();
                                    String returnResultCourses = (String) JOptionPane.showInputDialog(info,
                                            "Choose to see Uncancel Courses",
                                            "Select Option",
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            courses,
                                            courses[0]);

                                    FileWriter myCourseWriter = new FileWriter("src/com/coursemanagement/Files/courseFile.txt", true);
                                    myCourseWriter.write(returnResultCourses + "\n");
                                    myCourseWriter.close();


                                    String sql2 = "INSERT INTO courseAndSubject  (ModuleCode, ModuleName, Course, Level, semester)\n" +
                                            "SELECT * FROM cancelcoursesDetails WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql2);

                                    String sql3 = "delete from cancelcoursesDetails where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql3);

                                    String sql4 = "delete from cancelcourse where CancelledCourses = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql4);


                                    String sql12 = "INSERT INTO EnrolledStudent (email, fname,lname,address,Pname,Pnumber, level, semester, course)\n" +
                                            "SELECT * FROM cancelenrolledStudent WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql12);

                                    String sql5 = "delete from cancelenrolledStudent where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql5);


                                    String sql6 = "INSERT INTO InstructorDetails (ModuleCode, Email, Course, Level, semester)\n" +
                                            "SELECT * FROM cancelInstructorDetails WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql6);

                                    String sql7 = "delete from cancelInstructorDetails where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql7);

                                    String sql10 = "INSERT INTO level_6_student (Email, Course, semester,Subject)\n" +
                                            "SELECT * FROM cancellevel_6_student WHERE Course ='" + returnResultCourses + "';";
                                    connector.statement.executeUpdate(sql10);

                                    String sql11 = "delete from cancellevel_6_student where course = '" + returnResultCourses + "'; ";
                                    connector.statement.executeUpdate(sql11);


                                    JOptionPane.showMessageDialog(null, "Refresh choosing the quick service menu option");

                                }


                            } catch (SQLException | IOException ex) {
                                System.out.println("something went wrong");
                                ex.printStackTrace();
                            }
                        } else {

                        }


                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Illegal Action performed");
                }


            }
        });
    }

    private JPanel getModulePanelOne(String panelString) {
        JScrollPane scrollPane ;
        System.out.println("welcome to " + panelString);
       JPanel modulePanelTwo = new JPanel();
        modulePanelTwo.setBorder(new TitledBorder("Details"));

        table = new JTable();
        Object[] column = {"Module Code", "Module Name", "Course", "Level", "Semester"};
        Object[][] row = {

        };
        model = new DefaultTableModel(row, column);
        table.setModel(model);
        scrollPane = new JScrollPane(table);
        modulePanelTwo.add(scrollPane);

        return modulePanelTwo;
    }


    //fetching data from database table
    public void fetchingCourses() {
        String sql = "select * from courseAndSubject; ";
        try {
            resultSet = connector.statement.executeQuery(sql);
            while (resultSet.next()) {
                Object[] rowData =
                        {
                                resultSet.getString("ModuleCode"),
                                resultSet.getString("ModuleName"),
                                resultSet.getString("Course"),
                                resultSet.getString("Level"),
                                resultSet.getString("semester")
                        };
                model.addRow(rowData);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            System.out.println("something went wrong");
        }
    }


}
