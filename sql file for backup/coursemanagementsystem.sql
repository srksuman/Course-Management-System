-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 16, 2021 at 07:51 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `coursemanagementsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `cancelcourse`
--

CREATE TABLE `cancelcourse` (
  `CancelledCourses` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `cancelcoursesdetails`
--

CREATE TABLE `cancelcoursesdetails` (
  `ModuleCode` varchar(10) NOT NULL,
  `ModuleName` varchar(150) NOT NULL,
  `Course` varchar(50) NOT NULL,
  `Level` varchar(10) NOT NULL,
  `semester` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `cancelenrolledstudent`
--

CREATE TABLE `cancelenrolledstudent` (
  `email` varchar(100) NOT NULL,
  `fname` varchar(20) NOT NULL,
  `lname` varchar(20) NOT NULL,
  `address` varchar(50) NOT NULL,
  `Pname` varchar(30) NOT NULL,
  `Pnumber` varchar(30) NOT NULL,
  `level` varchar(20) NOT NULL,
  `semester` varchar(20) NOT NULL,
  `course` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `cancelinstructordetails`
--

CREATE TABLE `cancelinstructordetails` (
  `ModuleCode` varchar(20) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Course` varchar(20) NOT NULL,
  `Level` varchar(20) NOT NULL,
  `Semester` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `cancellevel_6_student`
--

CREATE TABLE `cancellevel_6_student` (
  `Email` varchar(100) NOT NULL,
  `Course` varchar(20) NOT NULL,
  `Semester` varchar(10) NOT NULL,
  `Subject` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `courseandsubject`
--

CREATE TABLE `courseandsubject` (
  `ModuleCode` varchar(10) NOT NULL,
  `ModuleName` varchar(100) NOT NULL,
  `Course` varchar(50) NOT NULL,
  `Level` varchar(10) NOT NULL,
  `semester` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `courseandsubject`
--

INSERT INTO `courseandsubject` (`ModuleCode`, `ModuleName`, `Course`, `Level`, `semester`) VALUES
('BBA10TU', 'Business Statistics', 'BBA', 'Level 5', '1'),
('BBA11TU', 'Financial Accounting', 'BBA', 'Level 5', '1'),
('BBA12TU', 'Business Finance', 'BBA', 'Level 5', '1'),
('BBA13TU', 'Business Law', 'BBA', 'Level 5', '2'),
('BBA14TU', 'Business Environment in Nepal', 'BBA', 'Level 5', '2'),
('BBA15TU', 'Cost and Management Accounting', 'BBA', 'Level 5', '2'),
('BBA16TU', 'Fundamentals of Marketing', 'BBA', 'Level 5', '2'),
('BBA17TU', 'Operations Management', 'BBA', 'Level 6', '1'),
('BBA18TU', 'Basic Financial Management', 'BBA', 'Level 6', '1'),
('BBA19TU', 'International Business', 'BBA', 'Level 6', '1'),
('BBA1TU', 'English-I', 'BBA', 'Level 4', '1'),
('BBA20TU', 'Opt.Sociology', 'BBA', 'Level 6', '1'),
('BBA21TU', 'Opt.Corporate Taxation in Nepal', 'BBA', 'Level 6', '1'),
('BBA22TU', 'Organizational Behavior', 'BBA', 'Level 6', '2'),
('BBA23TU', 'Entrepreneurship', 'BBA', 'Level 6', '2'),
('BBA24TU', 'Business Research Methods', 'BBA', 'Level 6', '2'),
('BBA25TU', 'Opt.Nepalese Society and Politics', 'BBA', 'Level 6', '2'),
('BBA26TU', 'Opt.E- Commerce', 'BBA', 'Level 6', '2'),
('BBA2TU', 'Principles of Management', 'BBA', 'Level 4', '1'),
('BBA3TU', 'Micro Economics', 'BBA', 'Level 4', '1'),
('BBA4TU', 'Basic Mathematics - I', 'BBA', 'Level 4', '1'),
('BBA5TU', 'English-II', 'BBA', 'Level 4', '2'),
('BBA6TU', 'Human Resource Management', 'BBA', 'Level 4', '2'),
('BBA7TU', 'Macro Economics', 'BBA', 'Level 4', '2'),
('BBA8TU', 'Business Mathematics - II', 'BBA', 'Level 4', '2'),
('BBA9TU', 'Business Communication', 'BBA', 'Level 5', '1'),
('SM10TU', 'Database Management System', 'IT', 'Level 5', '1'),
('SM11TU', 'Numerical Methods', 'IT', 'Level 5', '1'),
('SM12TU', 'Operating Systems', 'IT', 'Level 5', '1'),
('SM13TU', 'Web Technology I', 'IT', 'Level 5', '2'),
('SM14TU', 'Artificial Intelligence', 'IT', 'Level 5', '2'),
('SM15TU', 'Systems Analysis and Design', 'IT', 'Level 5', '2'),
('SM16TU', 'Network and Data Communications', 'IT', 'Level 5', '2'),
('SM17TU', 'Web Technology II', 'IT', 'Level 6', '1'),
('SM18TU', 'Software Engineering', 'IT', 'Level 6', '1'),
('SM19TU', 'Information Security', 'IT', 'Level 6', '1'),
('SM1TU', 'Introduction to Information Technology', 'IT', 'Level 4', '1'),
('SM20TU', 'Opt.Computer Graphics', 'IT', 'Level 6', '1'),
('SM21TU', 'Opt.Technical Writing', 'IT', 'Level 6', '1'),
('SM22TU', 'NET Centric Computing', 'IT', 'Level 6', '2'),
('SM23TU', 'Database Administration', 'IT', 'Level 6', '2'),
('SM24TU', 'Opt.Research Methodology', 'IT', 'Level 6', '2'),
('SM2TU', 'C Programming', 'IT', 'Level 4', '1'),
('SM30TU', 'Opt.Elective I', 'IT', 'Level 6', '2'),
('SM34TU', 'Management Information System', 'IT', 'Level 6', '2'),
('SM3TU', 'Digital Logic', 'IT', 'Level 4', '1'),
('SM4TU', 'Basic Mathematics', 'IT', 'Level 4', '1'),
('SM5TU', 'Microprocessor and Computer Architecture', 'IT', 'Level 4', '2'),
('SM6TU', 'Discrete Structure', 'IT', 'Level 4', '2'),
('SM7TU', 'Object Oriented Programming', 'IT', 'Level 4', '2'),
('SM8TU', 'Basic Statistics', 'IT', 'Level 4', '2'),
('SM9TU', 'Data Structures and Algorithms', 'IT', 'Level 5', '1');

-- --------------------------------------------------------

--
-- Table structure for table `enrolledstudent`
--

CREATE TABLE `enrolledstudent` (
  `email` varchar(100) NOT NULL,
  `fname` varchar(20) NOT NULL,
  `lname` varchar(20) NOT NULL,
  `address` varchar(50) NOT NULL,
  `Pname` varchar(30) NOT NULL,
  `Pnumber` varchar(30) NOT NULL,
  `level` varchar(20) NOT NULL,
  `semester` varchar(20) NOT NULL,
  `course` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `enrolledstudent`
--

INSERT INTO `enrolledstudent` (`email`, `fname`, `lname`, `address`, `Pname`, `Pnumber`, `level`, `semester`, `course`) VALUES
('aayush@edu.com.np', 'Aayush', 'Lamichane', 'Boudha,Kathmandu', 'Prince', '9834475450', 'Level 6', '2', 'IT'),
('aiti@edu.com.np', 'Atit', 'Tamang', 'NMC,Kathmandu', 'Normal', '986047360', 'Level 5', '1', 'IT'),
('bimal@edu.com.np', 'Bimal', 'Lama', 'jorpati,Kathmandu', 'Sujana', '9820476650', 'Level 4', '2', 'IT'),
('dipendra@edu.com.np', 'Dipendra', 'Thapa', 'NMC,Kathmandu', 'Krish', '9862374550', 'Level 6', '1', 'IT'),
('krishna@edu.com.np', 'Krishna', 'Basnet', 'PMC,Kathmandu', 'Smile', '9860476350', 'Level 5', '2', 'IT'),
('mahendra@edu.com.np', 'Mahendra', 'Khanal', 'Sankhu,Kathmandu', 'Puajn', '9865456650', 'Level 5', '2', 'IT'),
('pradip@edu.com.np', 'pradip', 'Tamang', 'NMC,Kathmandu', 'Hydrogen', '983247360', 'Level 6', '1', 'BBA'),
('pramod@edu.com.np', 'Promod', 'Basnet', 'PMC,Kathmandu', 'Helium', '9832476350', 'Level 6', '2', 'BBA'),
('pujan@edu.com.np', 'Pujan', 'Thapa', 'Chabel,Kathmandu', 'Kamala', '9862376650', 'Level 4', '1', 'BBA'),
('sahil@edu.com.np', 'Sahil', 'Basnet', 'PMC,Kathmandu', 'Sanani', '9835476350', 'Level 6', '2', 'BBA'),
('sandesh@edu.com.np', 'Sandesh', 'Lama', 'jorpati,Kathmandu', 'Mul', '9820566650', 'Level 5', '1', 'BBA'),
('sujan@edu.com.np', 'sujan', 'Khanal', 'Boudha,Kathmandu', 'Bimala', '9834476650', 'Level 4', '2', 'BBA'),
('suman@edu.com.np', 'Suman', 'Khanal', 'Thali,Kathmandu', 'Kamal', '9865676650', 'Level 4', '1', 'IT');

-- --------------------------------------------------------

--
-- Table structure for table `instructordetails`
--

CREATE TABLE `instructordetails` (
  `ModuleCode` varchar(20) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Course` varchar(20) NOT NULL,
  `Level` varchar(20) NOT NULL,
  `Semester` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `instructordetails`
--

INSERT INTO `instructordetails` (`ModuleCode`, `Email`, `Course`, `Level`, `Semester`) VALUES
('BBA1TU', 'ram@gmail.com', 'BBA', 'Level 4', '1'),
('BBA2TU', 'rohan@gmail.com', 'BBA', 'Level 4', '1'),
('BBA3TU', 'sahaj@gmail.com', 'BBA', 'Level 4', '1'),
('BBA4TU', 'samir@gmail.com', 'BBA', 'Level 4', '1'),
('BBA5TU', 'santosh@gmail.com', 'BBA', 'Level 4', '2'),
('BBA6TU', 'shiva@gmail.com', 'BBA', 'Level 4', '2'),
('BBA7TU', 'shyam@gmail.com', 'BBA', 'Level 4', '2'),
('BBA8TU', 'swikar@gmail.com', 'BBA', 'Level 4', '2'),
('BBA9TU', 'shyam@gmail.com', 'BBA', 'Level 5', '1'),
('BBA10TU', 'samir@gmail.com', 'BBA', 'Level 5', '1'),
('BBA11TU', 'sahaj@gmail.com', 'BBA', 'Level 5', '1'),
('BBA12TU', 'samir@gmail.com', 'BBA', 'Level 5', '1'),
('BBA13TU', 'santosh@gmail.com', 'BBA', 'Level 5', '2'),
('BBA14TU', 'samir@gmail.com', 'BBA', 'Level 5', '2'),
('BBA15TU', 'sahaj@gmail.com', 'BBA', 'Level 5', '2'),
('BBA16TU', 'rohan@gmail.com', 'BBA', 'Level 5', '2'),
('BBA17TU', 'ram@gmail.com', 'BBA', 'Level 6', '1'),
('BBA8TU', 'ram@gmail.com', 'BBA', 'Level 4', '2'),
('BBA19TU', 'sahaj@gmail.com', 'BBA', 'Level 6', '1'),
('BBA20TU', 'santosh@gmail.com', 'BBA', 'Level 6', '1'),
('BBA21TU', 'santosh@gmail.com', 'BBA', 'Level 6', '1'),
('BBA22TU', 'shiva@gmail.com', 'BBA', 'Level 6', '2'),
('BBA23TU', 'shiva@gmail.com', 'BBA', 'Level 6', '2'),
('BBA24TU', 'rohan@gmail.com', 'BBA', 'Level 6', '2'),
('BBA25TU', 'rohan@gmail.com', 'BBA', 'Level 6', '2'),
('BBA26TU', 'shyam@gmail.com', 'BBA', 'Level 6', '2'),
('SM1TU', 'sumanrajkhanal@gmail.com', 'IT', 'Level 4', '1'),
('SM2TU', 'pujan@gmail.com', 'IT', 'Level 4', '1'),
('SM3TU', 'roshan@gmail.com', 'IT', 'Level 4', '1'),
('SM4TU', 'atit@gmail.com', 'IT', 'Level 4', '1'),
('SM5TU', 'bishow@gmail.com', 'IT', 'Level 4', '2'),
('SM6TU', 'dipen@gmail.com', 'IT', 'Level 4', '2'),
('SM7TU', 'gita@gmail.com', 'IT', 'Level 4', '2'),
('SM8TU', 'himal@gmail.com', 'IT', 'Level 4', '2'),
('SM9TU', 'kamal@gmail.com', 'IT', 'Level 5', '1'),
('SM10TU', 'Kishan@gmail.com', 'IT', 'Level 5', '1'),
('SM11TU', 'nikesh@gmail.com', 'IT', 'Level 5', '1'),
('SM12TU', 'paras@gmail.com', 'IT', 'Level 5', '1'),
('SM13TU', 'prakash@gmail.com', 'IT', 'Level 5', '2'),
('SM14TU', 'rahul@gmail.com', 'IT', 'Level 5', '2'),
('SM15TU', 'rajat@gmail.com', 'IT', 'Level 5', '2'),
('SM16TU', 'sumanrajkhanal@gmail.com', 'IT', 'Level 5', '2'),
('SM17TU', 'pujan@gmail.com', 'IT', 'Level 6', '1'),
('SM18TU', 'roshan@gmail.com', 'IT', 'Level 6', '1'),
('SM19TU', 'atit@gmail.com', 'IT', 'Level 6', '1'),
('SM20TU', 'bishow@gmail.com', 'IT', 'Level 6', '1'),
('SM21TU', 'dipen@gmail.com', 'IT', 'Level 6', '1'),
('SM22TU', 'gita@gmail.com', 'IT', 'Level 6', '2'),
('SM23TU', 'himal@gmail.com', 'IT', 'Level 6', '2'),
('SM24TU', 'kamal@gmail.com', 'IT', 'Level 6', '2'),
('SM30TU', 'bishow@gmail.com', 'IT', 'Level 6', '2'),
('SM34TU', 'himal@gmail.com', 'IT', 'Level 6', '2');

-- --------------------------------------------------------

--
-- Table structure for table `instructorlogin`
--

CREATE TABLE `instructorlogin` (
  `Email` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Fullname` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `instructorlogin`
--

INSERT INTO `instructorlogin` (`Email`, `Password`, `Fullname`) VALUES
('atit@gmail.com', 'atit123', 'Atit Basnet'),
('bishow@gmail.com ', 'bishow123', ' Bishow Adhakari'),
('dipen@gmail.com', 'dipen123', 'Dipen Sapkota'),
('gita@gmail.com', 'gita123', ' Gita Khanal'),
('himal@gmail.com', 'himal123', 'Himal Dahal '),
('kamal@gmail.com ', 'kamal123', ' kamal Thapa '),
('Kishan@gmail.com', 'kishan123', 'Kishan Thapa '),
('krishna@gmail.com', 'krishna123', 'Krishna Lama '),
('nikesh@gmail.com', 'nikesh123', 'Nikesh Lama'),
('paras@gmail.com', 'paras123', 'Paras Malla '),
('prakash@gmail.com', 'prakash123', 'Prakash Shah'),
('pujan@gmail.com', 'pujan123', 'Pujan Khatiwada'),
('rahul@gmail.com', 'rahul123', 'Rahul Jha'),
('rajat@gmail.com', 'rajat123', 'Rajat Budathoki'),
('ram@gmail.com', 'ram123', 'Ram Thapa'),
('rohan@gmail.com', 'rohan123', 'Rohan Karki '),
('roshan@gmail.com', 'roshan123', 'Roshan Parajuli'),
('sahaj@gmail.com', 'sahaj123', 'Sahaj Rokka '),
('samir@gmail.com', 'samir123', 'Samir Giri '),
('santosh@gmail.com', 'santosh123', 'Santosh Rokka '),
('shiva@gmail.com', 'shiva123', 'Shiva Kunwar '),
('shyam@gmail.com', 'shyam123', ' Ram Thapa '),
('sumanrajkhanal@gmail.com', 'suman123', 'Suman Raj Khanal'),
('swikar@gmail.com ', 'swikar123', ' Swikar Thapa ');

-- --------------------------------------------------------

--
-- Table structure for table `level_6_student`
--

CREATE TABLE `level_6_student` (
  `Email` varchar(100) NOT NULL,
  `Course` varchar(20) NOT NULL,
  `Semester` varchar(10) NOT NULL,
  `Subject` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `level_6_student`
--

INSERT INTO `level_6_student` (`Email`, `Course`, `Semester`, `Subject`) VALUES
('aayush@edu.com.np', 'IT', '2', 'Opt.Elective I'),
('dipendra@edu.com.np', 'IT', '1', 'Opt.Computer Graphics'),
('pradip@edu.com.np', 'BBA', '1', 'Opt.Sociology'),
('pramod@edu.com.np', 'BBA', '2', 'Opt.E- Commerce'),
('sahil@edu.com.np', 'BBA', '2', 'Opt.E- Commerce');

-- --------------------------------------------------------

--
-- Table structure for table `studentmarks`
--

CREATE TABLE `studentmarks` (
  `Email` varchar(100) NOT NULL,
  `Subject` varchar(150) NOT NULL,
  `Mark` int(11) NOT NULL,
  `Level` varchar(50) NOT NULL,
  `Course` varchar(50) NOT NULL,
  `Semester` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `studentmarks`
--

INSERT INTO `studentmarks` (`Email`, `Subject`, `Mark`, `Level`, `Course`, `Semester`) VALUES
('suman@edu.com.np', 'Introduction to Information Technology', 85, 'Level 4', 'IT', '1'),
('mahendra@edu.com.np', 'Network and Data Communications', 83, 'Level 5', 'IT', '2'),
('krishna@edu.com.np', 'Network and Data Communications', 78, 'Level 5', 'IT', '2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cancelcourse`
--
ALTER TABLE `cancelcourse`
  ADD UNIQUE KEY `CancelledCourses` (`CancelledCourses`);

--
-- Indexes for table `cancelcoursesdetails`
--
ALTER TABLE `cancelcoursesdetails`
  ADD UNIQUE KEY `ModuleCode` (`ModuleCode`),
  ADD UNIQUE KEY `ModuleName` (`ModuleName`);

--
-- Indexes for table `cancelenrolledstudent`
--
ALTER TABLE `cancelenrolledstudent`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `cancellevel_6_student`
--
ALTER TABLE `cancellevel_6_student`
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Indexes for table `courseandsubject`
--
ALTER TABLE `courseandsubject`
  ADD UNIQUE KEY `ModuleCode` (`ModuleCode`),
  ADD UNIQUE KEY `ModuleName` (`ModuleName`);

--
-- Indexes for table `enrolledstudent`
--
ALTER TABLE `enrolledstudent`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `instructorlogin`
--
ALTER TABLE `instructorlogin`
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Indexes for table `level_6_student`
--
ALTER TABLE `level_6_student`
  ADD UNIQUE KEY `Email` (`Email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
