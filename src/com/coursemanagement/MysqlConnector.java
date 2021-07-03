package com.coursemanagement;

import java.sql.*;
import java.util.ArrayList;

public class MysqlConnector {
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String username = "root";
    private final String password = "";
    private final String databaseName = "CourseManagementSystem";
    public Connection connect;
    public Statement statement;
    public MysqlConnector() {
        connectMysql();
    }

    private void connectMysql() {
        try {

            connect = DriverManager.getConnection(url, username, password);
            statement = connect.createStatement();
            ArrayList databases = new ArrayList();
            ResultSet resultSet = connect.getMetaData().getCatalogs();
            while (resultSet.next()) {
                databases.add(resultSet.getString(1));
            }
            resultSet.close();
            if (!databases.contains(databaseName.toLowerCase())) {
                String sql = "CREATE DATABASE " + databaseName;
                statement.executeUpdate(sql);
                String sql2 = "USE " + databaseName;
                statement.executeUpdate(sql2);
                //Table structure for table enrolledstudent
                String queryTableEnrolledStudent = "CREATE TABLE IF NOT EXISTS EnrolledStudent (\n" +
                        "  email varchar(100) PRIMARY KEY NOT NULL,\n" +
                        "  fname varchar(20) NOT NULL,\n" +
                        "  lname varchar(20) NOT NULL,\n" +
                        "  address varchar(50) NOT NULL,\n" +
                        "  Pname varchar(30) NOT NULL,\n" +
                        "  Pnumber varchar(30) NOT NULL,\n" +
                        "  level varchar(20) NOT NULL,\n" +
                        "  semester varchar(20) NOT NULL,\n" +
                        "  course varchar(20) NOT NULL\n" +
                        ") ;";

                statement.executeUpdate(queryTableEnrolledStudent);

                //Table structure for table instructordetails
                String queryTableInstructorDetails = "CREATE TABLE IF NOT EXISTS InstructorDetails (\n" +
                        "  ModuleCode varchar(20) NOT NULL,\n" +
                        "  Email varchar(100) NOT NULL,\n" +
                        "  Course varchar(20) NOT NULL,\n" +
                        "  Level varchar(20) NOT NULL,\n" +
                        "  Semester varchar(20) NOT NULL\n" +
                        ") ;";

                statement.executeUpdate(queryTableInstructorDetails);

                //Table structure for table courseandsubject
                String queryTableCourseAndSubject = "CREATE TABLE IF NOT EXISTS CourseAndSubject (\n" +
                        "  ModuleCode varchar(10) UNIQUE  NOT NULL,\n" +
                        "  ModuleName varchar(100) UNIQUE  NOT NULL,\n" +
                        "  Course varchar(50) NOT NULL,\n" +
                        "  Level varchar(10) NOT NULL,\n" +
                        "  semester varchar(5) NOT NULL\n" +
                        ") ;";
                statement.executeUpdate(queryTableCourseAndSubject);

                //Table structure for table instructorlogin
                String queryTableInstructorLogin = "CREATE TABLE IF NOT EXISTS instructorlogin (\n" +
                        "  Email varchar(100) UNIQUE  NOT NULL,\n" +
                        "  Password varchar(100) NOT NULL,\n" +
                        "  Fullname varchar(100) NOT NULL\n" +
                        ") ;";
                statement.executeUpdate(queryTableInstructorLogin);

                //Table structure for table level_6_student
                String queryTableLevel_6_Student = "CREATE TABLE IF NOT EXISTS level_6_student (\n" +
                        "  Email varchar(100) UNIQUE NOT NULL,\n" +
                        "  Course varchar(20) NOT NULL,\n" +
                        "  Semester varchar(10) NOT NULL,\n" +
                        "  Subject varchar(200) NOT NULL\n" +
                        ") ;";
                statement.executeUpdate(queryTableLevel_6_Student);

                //Table structure for table studentmarks

                String queryTableStudentMarks = "CREATE TABLE IF NOT EXISTS studentmarks (\n" +
                        "  Email varchar(100) NOT NULL,\n" +
                        "  Subject varchar(150) NOT NULL,\n" +
                        "  Mark int(11) NOT NULL,\n" +
                        "  Level varchar(50) NOT NULL,\n" +
                        "  Course varchar(50) NOT NULL,\n" +
                        "  Semester varchar(50) NOT NULL\n" +
                        ") ;";
                statement.executeUpdate(queryTableStudentMarks);


                //Table structure for table Cancelinstructordetails
                String queryTableCancelInstructorDetails = "CREATE TABLE IF NOT EXISTS CancelInstructorDetails (\n" +
                        "  ModuleCode varchar(20) NOT NULL,\n" +
                        "  Email varchar(100) NOT NULL,\n" +
                        "  Course varchar(20) NOT NULL,\n" +
                        "  Level varchar(20) NOT NULL,\n" +
                        "  Semester varchar(20) NOT NULL\n" +
                        ") ;";

                statement.executeUpdate(queryTableCancelInstructorDetails);

                //Table structure for table CancelCoursesDetails
                String queryTableCancelCourseDetails = "CREATE TABLE IF NOT EXISTS CancelCoursesDetails (\n" +
                        "  ModuleCode varchar(10) UNIQUE  NOT NULL ,\n" +
                        "  ModuleName varchar(150) UNIQUE  NOT NULL,\n" +
                        "  Course varchar(50) NOT NULL,\n" +
                        "  Level varchar(10) NOT NULL,\n" +
                        "  semester varchar(5) NOT NULL\n" +
                        ") ;";
                statement.executeUpdate(queryTableCancelCourseDetails);


                // Table structure for table cancelcourse
                String queryTableCancelCourse = "CREATE TABLE IF NOT EXISTS CancelCourse (\n" +
                        "  CancelledCourses varchar(100) UNIQUE NOT NULL \n" +
                        ") ;";
                statement.executeUpdate(queryTableCancelCourse);


                //Table structure for table Cancelenrolledstudent
                String queryTableCancelEnrolledStudent = "CREATE TABLE IF NOT EXISTS CancelEnrolledStudent (\n" +
                        "  email varchar(100) PRIMARY KEY NOT NULL,\n" +
                        "  fname varchar(20) NOT NULL,\n" +
                        "  lname varchar(20) NOT NULL,\n" +
                        "  address varchar(50) NOT NULL,\n" +
                        "  Pname varchar(30) NOT NULL,\n" +
                        "  Pnumber varchar(30) NOT NULL,\n" +
                        "  level varchar(20) NOT NULL,\n" +
                        "  semester varchar(20) NOT NULL,\n" +
                        "  course varchar(20) NOT NULL\n" +
                        ") ;";

                statement.executeUpdate(queryTableCancelEnrolledStudent);

                //Table structure for table level_6_student
                String queryTableCancelLevel_6_Student = "CREATE TABLE IF NOT EXISTS Cancellevel_6_student (\n" +
                        "  Email varchar(100) UNIQUE  NOT NULL,\n" +
                        "  Course varchar(20) NOT NULL,\n" +
                        "  Semester varchar(10) NOT NULL,\n" +
                        "  Subject varchar(150) NOT NULL\n" +
                        ") ;";
                statement.executeUpdate(queryTableCancelLevel_6_Student);

                //inserting dummies data into tables

                String dummiesEntryCourseAndSubject = "INSERT INTO courseandsubject (ModuleCode, ModuleName, Course, Level, semester) VALUES\n" +
                        "('BBA10TU', 'Business Statistics', 'BBA', 'Level 5', '1'),\n" +
                        "('BBA11TU', 'Financial Accounting', 'BBA', 'Level 5', '1'),\n" +
                        "('BBA12TU', 'Business Finance', 'BBA', 'Level 5', '1'),\n" +
                        "('BBA13TU', 'Business Law', 'BBA', 'Level 5', '2'),\n" +
                        "('BBA14TU', 'Business Environment in Nepal', 'BBA', 'Level 5', '2'),\n" +
                        "('BBA15TU', 'Cost and Management Accounting', 'BBA', 'Level 5', '2'),\n" +
                        "('BBA16TU', 'Fundamentals of Marketing', 'BBA', 'Level 5', '2'),\n" +
                        "('BBA17TU', 'Operations Management', 'BBA', 'Level 6', '1'),\n" +
                        "('BBA18TU', 'Basic Financial Management', 'BBA', 'Level 6', '1'),\n" +
                        "('BBA19TU', 'International Business', 'BBA', 'Level 6', '1'),\n" +
                        "('BBA1TU', 'English-I', 'BBA', 'Level 4', '1'),\n" +
                        "('BBA20TU', 'Opt.Sociology', 'BBA', 'Level 6', '1'),\n" +
                        "('BBA21TU', 'Opt.Corporate Taxation in Nepal', 'BBA', 'Level 6', '1'),\n" +
                        "('BBA22TU', 'Organizational Behavior', 'BBA', 'Level 6', '2'),\n" +
                        "('BBA23TU', 'Entrepreneurship', 'BBA', 'Level 6', '2'),\n" +
                        "('BBA24TU', 'Business Research Methods', 'BBA', 'Level 6', '2'),\n" +
                        "('BBA25TU', 'Opt.Nepalese Society and Politics', 'BBA', 'Level 6', '2'),\n" +
                        "('BBA26TU', 'Opt.E- Commerce', 'BBA', 'Level 6', '2'),\n" +
                        "('BBA2TU', 'Principles of Management', 'BBA', 'Level 4', '1'),\n" +
                        "('BBA3TU', 'Micro Economics', 'BBA', 'Level 4', '1'),\n" +
                        "('BBA4TU', 'Basic Mathematics - I', 'BBA', 'Level 4', '1'),\n" +
                        "('BBA5TU', 'English-II', 'BBA', 'Level 4', '2'),\n" +
                        "('BBA6TU', 'Human Resource Management', 'BBA', 'Level 4', '2'),\n" +
                        "('BBA7TU', 'Macro Economics', 'BBA', 'Level 4', '2'),\n" +
                        "('BBA8TU', 'Business Mathematics - II', 'BBA', 'Level 4', '2'),\n" +
                        "('BBA9TU', 'Business Communication', 'BBA', 'Level 5', '1'),\n" +
                        "('SM10TU', 'Database Management System', 'IT', 'Level 5', '1'),\n" +
                        "('SM11TU', 'Numerical Methods', 'IT', 'Level 5', '1'),\n" +
                        "('SM12TU', 'Operating Systems', 'IT', 'Level 5', '1'),\n" +
                        "('SM13TU', 'Web Technology I', 'IT', 'Level 5', '2'),\n" +
                        "('SM14TU', 'Artificial Intelligence', 'IT', 'Level 5', '2'),\n" +
                        "('SM15TU', 'Systems Analysis and Design', 'IT', 'Level 5', '2'),\n" +
                        "('SM16TU', 'Network and Data Communications', 'IT', 'Level 5', '2'),\n" +
                        "('SM17TU', 'Web Technology II', 'IT', 'Level 6', '1'),\n" +
                        "('SM18TU', 'Software Engineering', 'IT', 'Level 6', '1'),\n" +
                        "('SM19TU', 'Information Security', 'IT', 'Level 6', '1'),\n" +
                        "('SM1TU', 'Introduction to Information Technology', 'IT', 'Level 4', '1'),\n" +
                        "('SM20TU', 'Opt.Computer Graphics', 'IT', 'Level 6', '1'),\n" +
                        "('SM21TU', 'Opt.Technical Writing', 'IT', 'Level 6', '1'),\n" +
                        "('SM22TU', 'NET Centric Computing', 'IT', 'Level 6', '2'),\n" +
                        "('SM23TU', 'Database Administration', 'IT', 'Level 6', '2'),\n" +
                        "('SM24TU', 'Opt.Research Methodology', 'IT', 'Level 6', '2'),\n" +
                        "('SM2TU', 'C Programming', 'IT', 'Level 4', '1'),\n" +
                        "('SM30TU', 'Opt.Elective I', 'IT', 'Level 6', '2'),\n" +
                        "('SM34TU', 'Management Information System', 'IT', 'Level 6', '2'),\n" +
                        "('SM3TU', 'Digital Logic', 'IT', 'Level 4', '1'),\n" +
                        "('SM4TU', 'Basic Mathematics', 'IT', 'Level 4', '1'),\n" +
                        "('SM5TU', 'Microprocessor and Computer Architecture', 'IT', 'Level 4', '2'),\n" +
                        "('SM6TU', 'Discrete Structure', 'IT', 'Level 4', '2'),\n" +
                        "('SM7TU', 'Object Oriented Programming', 'IT', 'Level 4', '2'),\n" +
                        "('SM8TU', 'Basic Statistics', 'IT', 'Level 4', '2'),\n" +
                        "('SM9TU', 'Data Structures and Algorithms', 'IT', 'Level 5', '1');";

                statement.executeUpdate(dummiesEntryCourseAndSubject);

                //inserting dummies entries for InstructorDetails
                String dummiesEntryInstructorDetails = "INSERT INTO instructordetails (ModuleCode, Email, Course, Level, Semester) VALUES\n" +
                        "('SM1TU', 'sumanrajkhanal@gmail.com', 'IT', 'Level 4', '1'),\n" +
                        "('SM2TU', 'pujan@gmail.com', 'IT', 'Level 4', '1'),\n" +
                        "('SM3TU', 'roshan@gmail.com', 'IT', 'Level 4', '1'),\n" +
                        "('SM4TU', 'atit@gmail.com', 'IT', 'Level 4', '1'),\n" +
                        "('SM5TU', 'bishow@gmail.com', 'IT', 'Level 4', '2'),\n" +
                        "('SM6TU', 'dipen@gmail.com', 'IT', 'Level 4', '2'),\n" +
                        "('SM7TU', 'gita@gmail.com', 'IT', 'Level 4', '2'),\n" +
                        "('SM8TU', 'himal@gmail.com', 'IT', 'Level 4', '2'),\n" +
                        "('SM9TU', 'kamal@gmail.com', 'IT', 'Level 5', '1'),\n" +
                        "('SM10TU', 'Kishan@gmail.com', 'IT', 'Level 5', '1'),\n" +
                        "('SM11TU', 'nikesh@gmail.com', 'IT', 'Level 5', '1'),\n" +
                        "('SM12TU', 'paras@gmail.com', 'IT', 'Level 5', '1'),\n" +
                        "('SM13TU', 'prakash@gmail.com', 'IT', 'Level 5', '2'),\n" +
                        "('SM14TU', 'rahul@gmail.com', 'IT', 'Level 5', '2'),\n" +
                        "('SM15TU', 'rajat@gmail.com', 'IT', 'Level 5', '2'),\n" +
                        "('SM16TU', 'sumanrajkhanal@gmail.com', 'IT', 'Level 5', '2'),\n" +
                        "('SM17TU', 'pujan@gmail.com', 'IT', 'Level 6', '1'),\n" +
                        "('SM18TU', 'roshan@gmail.com', 'IT', 'Level 6', '1'),\n" +
                        "('SM19TU', 'atit@gmail.com', 'IT', 'Level 6', '1'),\n" +
                        "('SM20TU', 'bishow@gmail.com', 'IT', 'Level 6', '1'),\n" +
                        "('SM21TU', 'dipen@gmail.com', 'IT', 'Level 6', '1'),\n" +
                        "('SM22TU', 'gita@gmail.com', 'IT', 'Level 6', '2'),\n" +
                        "('SM23TU', 'himal@gmail.com', 'IT', 'Level 6', '2'),\n" +
                        "('SM24TU', 'kamal@gmail.com', 'IT', 'Level 6', '2'),\n" +
                        "('BBA1TU', 'ram@gmail.com', 'BBA', 'Level 4', '1'),\n" +
                        "('BBA2TU', 'rohan@gmail.com', 'BBA', 'Level 4', '1'),\n" +
                        "('BBA3TU', 'sahaj@gmail.com', 'BBA', 'Level 4', '1'),\n" +
                        "('BBA4TU', 'samir@gmail.com', 'BBA', 'Level 4', '1'),\n" +
                        "('BBA5TU', 'santosh@gmail.com', 'BBA', 'Level 4', '2'),\n" +
                        "('BBA6TU', 'shiva@gmail.com', 'BBA', 'Level 4', '2'),\n" +
                        "('BBA7TU', 'shyam@gmail.com', 'BBA', 'Level 4', '2'),\n" +
                        "('BBA8TU', 'swikar@gmail.com', 'BBA', 'Level 4', '2'),\n" +
                        "('BBA9TU', 'shyam@gmail.com', 'BBA', 'Level 5', '1'),\n" +
                        "('BBA10TU', 'samir@gmail.com', 'BBA', 'Level 5', '1'),\n" +
                        "('BBA11TU', 'sahaj@gmail.com', 'BBA', 'Level 5', '1'),\n" +
                        "('BBA12TU', 'samir@gmail.com', 'BBA', 'Level 5', '1'),\n" +
                        "('BBA13TU', 'santosh@gmail.com', 'BBA', 'Level 5', '2'),\n" +
                        "('BBA14TU', 'samir@gmail.com', 'BBA', 'Level 5', '2'),\n" +
                        "('BBA15TU', 'sahaj@gmail.com', 'BBA', 'Level 5', '2'),\n" +
                        "('BBA16TU', 'rohan@gmail.com', 'BBA', 'Level 5', '2'),\n" +
                        "('BBA17TU', 'ram@gmail.com', 'BBA', 'Level 6', '1'),\n" +
                        "('BBA8TU', 'ram@gmail.com', 'BBA', 'Level 4', '2'),\n" +
                        "('BBA19TU', 'sahaj@gmail.com', 'BBA', 'Level 6', '1'),\n" +
                        "('BBA20TU', 'santosh@gmail.com', 'BBA', 'Level 6', '1'),\n" +
                        "('BBA21TU', 'santosh@gmail.com', 'BBA', 'Level 6', '1'),\n" +
                        "('BBA22TU', 'shiva@gmail.com', 'BBA', 'Level 6', '2'),\n" +
                        "('BBA23TU', 'shiva@gmail.com', 'BBA', 'Level 6', '2'),\n" +
                        "('BBA24TU', 'rohan@gmail.com', 'BBA', 'Level 6', '2'),\n" +
                        "('BBA25TU', 'rohan@gmail.com', 'BBA', 'Level 6', '2'),\n" +
                        "('BBA26TU', 'shyam@gmail.com', 'BBA', 'Level 6', '2'),\n" +
                        "('SM30TU', 'bishow@gmail.com', 'IT', 'Level 6', '2'),\n" +
                        "('SM34TU', 'himal@gmail.com', 'IT', 'Level 6', '2');";

                statement.executeUpdate(dummiesEntryInstructorDetails);

                //dummies entry for instructorLogin

                String dummiesEntryInstructorLogin = "INSERT INTO instructorlogin (Email, Password, Fullname) VALUES\n" +
                        "('atit@gmail.com', 'atit123', 'Atit Basnet'),\n" +
                        "('bishow@gmail.com ', 'bishow123', ' Bishow Adhakari'),\n" +
                        "('dipen@gmail.com', 'dipen123', 'Dipen Sapkota'),\n" +
                        "('gita@gmail.com', 'gita123', ' Gita Khanal'),\n" +
                        "('himal@gmail.com', 'himal123', 'Himal Dahal '),\n" +
                        "('kamal@gmail.com ', 'kamal123', ' kamal Thapa '),\n" +
                        "('Kishan@gmail.com', 'kishan123', 'Kishan Thapa '),\n" +
                        "('krishna@gmail.com', 'krishna123', 'Krishna Lama '),\n" +
                        "('nikesh@gmail.com', 'nikesh123', 'Nikesh Lama'),\n" +
                        "('paras@gmail.com', 'paras123', 'Paras Malla '),\n" +
                        "('prakash@gmail.com', 'prakash123', 'Prakash Shah'),\n" +
                        "('pujan@gmail.com', 'pujan123', 'Pujan Khatiwada'),\n" +
                        "('rahul@gmail.com', 'rahul123', 'Rahul Jha'),\n" +
                        "('rajat@gmail.com', 'rajat123', 'Rajat Budathoki'),\n" +
                        "('ram@gmail.com', 'ram123', 'Ram Thapa'),\n" +
                        "('rohan@gmail.com', 'rohan123', 'Rohan Karki '),\n" +
                        "('roshan@gmail.com', 'roshan123', 'Roshan Parajuli'),\n" +
                        "('sahaj@gmail.com', 'sahaj123', 'Sahaj Rokka '),\n" +
                        "('samir@gmail.com', 'samir123', 'Samir Giri '),\n" +
                        "('santosh@gmail.com', 'santosh123', 'Santosh Rokka '),\n" +
                        "('shiva@gmail.com', 'shiva123', 'Shiva Kunwar '),\n" +
                        "('shyam@gmail.com', 'shyam123', ' Ram Thapa '),\n" +
                        "('sumanrajkhanal@gmail.com', 'suman123', 'Suman Raj Khanal'),\n" +
                        "('swikar@gmail.com ', 'swikar123', ' Swikar Thapa ');";
                statement.executeUpdate(dummiesEntryInstructorLogin);

                //inserting dummies data into enrolledstudent
                String dummiesEntryEnrolledStudent = "INSERT INTO enrolledstudent (email, fname, lname, address, Pname, Pnumber, level, semester, course) VALUES\n" +
                        "('aayush@edu.com.np', 'Aayush', 'Lamichane', 'Boudha,Kathmandu', 'Prince', '9834475450', 'Level 6', '2', 'IT'),\n" +
                        "('aiti@edu.com.np', 'Atit', 'Tamang', 'NMC,Kathmandu', 'Normal', '986047360', 'Level 5', '1', 'IT'),\n" +
                        "('bimal@edu.com.np', 'Bimal', 'Lama', 'jorpati,Kathmandu', 'Sujana', '9820476650', 'Level 4', '2', 'IT'),\n" +
                        "('dipendra@edu.com.np', 'Dipendra', 'Thapa', 'NMC,Kathmandu', 'Krish', '9862374550', 'Level 6', '1', 'IT'),\n" +
                        "('krishna@edu.com.np', 'Krishna', 'Basnet', 'PMC,Kathmandu', 'Smile', '9860476350', 'Level 5', '2', 'IT'),\n" +
                        "('mahendra@edu.com.np', 'Mahendra', 'Khanal', 'Sankhu,Kathmandu', 'Puajn', '9865456650', 'Level 5', '2', 'IT'),\n" +
                        "('pradip@edu.com.np', 'pradip', 'Tamang', 'NMC,Kathmandu', 'Hydrogen', '983247360', 'Level 6', '1', 'BBA'),\n" +
                        "('pramod@edu.com.np', 'Promod', 'Basnet', 'PMC,Kathmandu', 'Helium', '9832476350', 'Level 6', '2', 'BBA'),\n" +
                        "('pujan@edu.com.np', 'Pujan', 'Thapa', 'Chabel,Kathmandu', 'Kamala', '9862376650', 'Level 4', '1', 'BBA'),\n" +
                        "('sandesh@edu.com.np', 'Sandesh', 'Lama', 'jorpati,Kathmandu', 'Mul', '9820566650', 'Level 5', '1', 'BBA'),\n" +
                        "('sujan@edu.com.np', 'sujan', 'Khanal', 'Boudha,Kathmandu', 'Bimala', '9834476650', 'Level 4', '2', 'BBA'),\n" +
                        "('suman@edu.com.np', 'Suman', 'Khanal', 'Thali,Kathmandu', 'Kamal', '9865676650', 'Level 4', '1', 'IT');";
                statement.executeUpdate(dummiesEntryEnrolledStudent);

                //dummies entry for level_6_student

                String dummiesEntryLevel_6_student = "INSERT INTO level_6_student (Email, Course, Semester, Subject) VALUES\n" +
                        "('aayush@edu.com.np', 'IT', '2', 'Opt.Elective I'),\n" +
                        "('dipendra@edu.com.np', 'IT', '1', 'Opt.Computer Graphics'),\n" +
                        "('pradip@edu.com.np', 'BBA', '1', 'Opt.Sociology'),\n" +
                        "('pramod@edu.com.np', 'BBA', '2', 'Opt.E- Commerce');";

                statement.executeUpdate(dummiesEntryLevel_6_student);

                //dummies entry for StudentMarks
                String dummiesEntryStudentMarks = "INSERT INTO studentmarks (Email, Subject, Mark, Level, Course, Semester) VALUES\n" +
                        "('suman@edu.com.np', 'Introduction to Information Technology', 85, 'Level 4', 'IT', '1'),\n" +
                        "('mahendra@edu.com.np', 'Network and Data Communications', 83, 'Level 5', 'IT', '2'),\n" +
                        "('krishna@edu.com.np', 'Network and Data Communications', 78, 'Level 5', 'IT', '2');";

                statement.executeUpdate(dummiesEntryStudentMarks);
            } else {
                String sql2 = "USE " + databaseName;
                statement.executeUpdate(sql2);
            }


        } catch (SQLException f) {
            System.out.println("something went wrong");
        }

    }
}
