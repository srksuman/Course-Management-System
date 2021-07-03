package com.coursemanagement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Instructor extends JFrame implements TableDataFetchTeacher {
    private final String teacherEmail;
    private MysqlConnector connector ;
    private JPanel panelOne, panelTwo;
    private JLabel teacherLabel, subjectLabel;
    private JButton studentReport;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private ResultSet resultSet;
    private JComboBox subjectList;

    public Instructor(String teacherEmail) {
        this.teacherEmail = teacherEmail;
        connector = new MysqlConnector();
        setVisible(true);
        setMinimumSize(new Dimension(600, 350));
        setBounds(120, 100, 600, 350);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));
        add(panelOne());
        add(panelTwo());
        fetchStudentMarks();

        JMenuBar ref = new JMenuBar();
        JMenu menu = new JMenu("Quick service");
        JMenuItem i1 = new JMenuItem("Refresh");
        JMenuItem i2 = new JMenuItem("Help");

        menu.add(i1);
        menu.add(i2);
        ref.add(menu);
        this.setJMenuBar(ref);

        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Instructor(teacherEmail);
            }
        });

        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "instructor can view what modules they are on by clicking drop down box, and the\n" +
                        "students registered on those modules by seeing the right hand side table. Instructors\n" +
                        "are able to add marks to each module they  are teaching by clicking enter mark button");
            }
        });

        pack();
    }

    private JPanel panelOne() {
        String teacherName = "";
        ArrayList subjectNameList = new ArrayList();

        String query = "Select Fullname from instructorLogin where Email = '" + teacherEmail + "';";
        try {
            resultSet = connector.statement.executeQuery(query);
            while (resultSet.next()) {
                teacherName = resultSet.getString("Fullname");
            }
            String query1 = "Select ModuleName from courseandsubject where ModuleCode IN (Select ModuleCode from instructordetails  where email ='" + teacherEmail + "');";
            resultSet = connector.statement.executeQuery(query1);
            while (resultSet.next()) {
                subjectNameList.add(resultSet.getString("ModuleName"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong please try again later");
        }


        String quote = "A Teacher is responsible for preparing\n " +
                "lesson plans and educating students at all levels.\n" +
                " Their duties include assigning homework, grading\n" +
                " tests, and documenting progress.Teachers must be \n" +
                "able to instruct in a variety of subjects and reach\n" +
                " students with engaging lesson plans.";
        panelOne = new JPanel();
        panelOne.setLayout(null);
        panelOne.setBackground(new Color(255, 255, 255));
        panelOne.setBorder(new TitledBorder("Teacher Details"));
        JLabel quotes = new JLabel("<html>" + quote + "<html>", JLabel.CENTER);
        quotes.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 12));
        quotes.setBounds(5, 0, 450, 100);
        quotes.setBorder(new TitledBorder(""));
        panelOne.add(quotes);


        ImageIcon img = new ImageIcon("src/com/coursemanagement/icons/teacher.jpg");
        Image icon = img.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        img = new ImageIcon(icon);
        JLabel labelLogoImage = new JLabel();
        labelLogoImage.setIcon(img);
        labelLogoImage.setBounds(150, 80, 200, 200);

        panelOne.add(labelLogoImage);


        teacherLabel = new JLabel("Name:");
        teacherLabel.setBounds(100, 320, 80, 20);
        teacherLabel.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        panelOne.add(teacherLabel);

        teacherLabel = new JLabel(teacherName.toUpperCase());
        teacherLabel.setBounds(170, 320, 250, 30);
        teacherLabel.setFont(new Font("TimesRoman", Font.PLAIN | Font.BOLD, 15));
        teacherLabel.setBorder(BorderFactory.createBevelBorder(1));
        panelOne.add(teacherLabel);

        subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(100, 350, 120, 20);
        subjectLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        panelOne.add(subjectLabel);

        subjectList = new JComboBox(subjectNameList.toArray());
        subjectList.setBounds(170, 350, 250, 30);
        subjectList.setFont(new Font("TimesRoman", Font.PLAIN | Font.BOLD, 15));
        subjectList.setBorder(BorderFactory.createBevelBorder(1));
        panelOne.add(subjectList);

        studentReport = new JButton("Enter marks");
        studentReport.setBounds(140, 410, 120, 30);
        panelOne.add(studentReport);
        studentReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subjectName = subjectList.getSelectedItem().toString();
                new AddMarks(subjectName);
            }
        });

        return panelOne;
    }


    private JPanel panelTwo() {
        panelTwo = new JPanel();
        panelTwo.setBorder(new TitledBorder("Students Marks"));
        model = new DefaultTableModel();
        String[] columns = {"Email", "Subject", "Marks", "Level", "Course", "Semester"};
        model.setColumnIdentifiers(columns);
        table = new JTable(model);
        table.setEnabled(false);
        table.setRowHeight(20);
        scrollPane = new JScrollPane(table);
        panelTwo.add(scrollPane);
        return panelTwo;

    }

    public void fetchStudentMarks() {
        try {
            String query = "Select * from studentMarks Where subject in (select ModuleName from courseandsubject where moduleCode in (Select ModuleCode from instructorDetails where  Email = '" + teacherEmail + "'));";
            System.out.println(query);
            resultSet = connector.statement.executeQuery(query);
            while (resultSet.next()) {
                Object[] rowData =
                        {
                                resultSet.getString("Email"),
                                resultSet.getString("Subject"),
                                resultSet.getInt("Mark"),
                                resultSet.getString("Level"),
                                resultSet.getString("Course"),
                                resultSet.getString("Semester")
                        };
                model.addRow(rowData);
            }
            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "something went wrong please try again later");
        }
    }


}

class AddMarks extends JFrame {
    private final ArrayList studentList = new ArrayList();
    private final String subjectName;
    private final ArrayList studentsEmailShowList = new ArrayList();
    private final ArrayList studentsMarkShowList = new ArrayList();
    private final ArrayList emailFromMarkTable = new ArrayList();
    private MysqlConnector connector ;
    private String levelSubject;
    private String course;
    private String sem;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JButton  entrmarksBtn;
    private ResultSet resultSet;

    public AddMarks(String subjectName) {
        this.subjectName = subjectName;
        connector = new MysqlConnector();
        setVisible(true);
        setLocationRelativeTo(null);
        setTitle("Enter a students Marks");
        setMinimumSize(new Dimension(400, 400));
        setResizable(false);
        add(panel());
        actionMarks();
    }

    private JPanel panel() {
        panel = new JPanel();
        panel.setBorder(new TitledBorder("Students Marks"));

        String query = "Select Level,Course,semester from courseandsubject where ModuleName ='" + subjectName + "'; ";
        System.out.println(query);
        try {
            resultSet = connector.statement.executeQuery(query);
            while (resultSet.next()) {
                levelSubject = resultSet.getString("Level");
                course = resultSet.getString("Course");
                sem = resultSet.getString("semester");
            }
            resultSet.close();
            System.out.println(levelSubject + course + sem);
            String query3 = "Select Email from studentMarks where  Subject ='" + subjectName + "';";
            ResultSet resultSet1 = connector.statement.executeQuery(query3);

            while (resultSet1.next()) {
                emailFromMarkTable.add(resultSet1.getString("Email"));
            }
            if (subjectName.startsWith("Opt.")) {

                String query1 = "Select email from level_6_student where Subject ='" + subjectName + "';";
                resultSet = connector.statement.executeQuery(query1);
                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    if (!emailFromMarkTable.contains(email)) {
                        studentList.add(email);
                    }
                }
                resultSet.close();

            } else {
                String query1 = "Select email from enrolledStudent where course ='" + course + "' and semester ='" + sem + "' and level ='" + levelSubject + "';";
                System.out.println(query1);
                resultSet = connector.statement.executeQuery(query1);
                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    if (!emailFromMarkTable.contains(email)) {
                        studentList.add(email);
                    }
                }
                resultSet.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong please try again later");
        }


        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        int i = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 3;
        gbc.ipady = 3;
        gbc.gridheight = 1;
        JTextArea stu = new JTextArea(10, 10);

        stu.setFont(new Font("Times Roman", Font.BOLD, 12));
        try {
            String query1 = "Select Email,Mark from  studentMarks where Subject ='" + subjectName + "';";
            resultSet = connector.statement.executeQuery(query1);
            while (resultSet.next()) {
                studentsEmailShowList.add(resultSet.getString("Email"));
                studentsMarkShowList.add(resultSet.getInt("Mark"));
            }
            resultSet.close();
        } catch (SQLException ex) {
            System.out.println("something went wrong please try again");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "something went wrong please try again");
        }
        String text = course + "\n" + levelSubject + " Semester " + sem + "\n" + subjectName + "\n";
        for (i = 0; i < studentsEmailShowList.size(); i++) {
            text += studentsEmailShowList.get(i).toString() + "\t" + studentsMarkShowList.get(i).toString() + "\n";
        }
        stu.setText(text);
        stu.setEditable(false);
        scrollPane = new JScrollPane(stu);

        panel.add(scrollPane, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        entrmarksBtn = new JButton("ENTER MARKS OF STUDENTS:");
        entrmarksBtn.setFont(new Font("Times Roman", Font.BOLD, 15));
        entrmarksBtn.setFocusable(false);
        entrmarksBtn.setBackground(new Color(0, 0, 0));
        entrmarksBtn.setForeground(new Color(255, 255, 255));
        panel.add(entrmarksBtn, gbc);


        return panel;
    }

    private void actionMarks() {
        entrmarksBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] listStd = studentList.toArray();
                if (studentList.size() <= 0) {
                    JOptionPane.showMessageDialog(null, "No student are present in this subject");
                } else {
                    try {
                        String input = (String) JOptionPane.showInputDialog(null, "Select Mechanic: ",
                                "Select Mechanic", JOptionPane.QUESTION_MESSAGE, null, listStd, listStd[0]);
                        if (!input.isEmpty()) {
                            int mark = parseInt(JOptionPane.showInputDialog(null, "Enter the student mark;-"));
                            System.out.println(levelSubject);
                            String sql = "INSERT INTO  studentMarks VALUES('" + input + "','" + subjectName + "','" + mark + "','" + levelSubject + "','" + course + "','" + sem + "');";
                            try {
                                connector.statement.executeUpdate(sql);
                                JOptionPane.showMessageDialog(null, "The student data will be seen when you refresh by clicking quick service option from menu \n" +
                                        "Thank you for your patience ✔");
                                dispose();
                                new AddMarks(subjectName);
                            } catch (SQLException exception) {
                                exception.printStackTrace();
                                System.out.println("Something went wrong");
                                JOptionPane.showMessageDialog(null, "Something went wrong please try again later");
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "You tried to input empty ");
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(null, "You cancelled ");
                    }
                }


            }
        });
    }

}