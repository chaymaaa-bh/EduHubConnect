-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 05, 2024 at 09:05 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `edu`
--

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `idC` int(11) NOT NULL,
  `c_title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `idP` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE `modules` (
  `module_id` int(11) NOT NULL,
  `module_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`module_id`, `module_name`) VALUES
(20, 'Mathematics'),
(21, 'Science'),
(22, 'History'),
(23, 'Literature'),
(24, 'Geography');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `paymentID` int(11) NOT NULL,
  `courseBought` varchar(255) NOT NULL,
  `PricePaid` int(11) NOT NULL,
  `TimePaid` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `subjectBoughtId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`paymentID`, `courseBought`, `PricePaid`, `TimePaid`, `subjectBoughtId`) VALUES
(25, 'Math 2', 40, '2024-03-04 12:14:09', 19),
(26, 'Math', 30, '2024-03-04 14:04:35', 18),
(27, 'Java', 50, '2024-03-05 07:47:28', 20);

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
  `idP` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `likes` int(11) NOT NULL,
  `dislikes` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `post`
--

INSERT INTO `post` (`idP`, `content`, `likes`, `dislikes`) VALUES
(80, 'Post 1', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Questions`
--

CREATE TABLE `Questions` (
  `question_id` int(11) NOT NULL,
  `question_quiz_id` int(11) NOT NULL,
  `question_text` text NOT NULL,
  `question_difficulty_level` text NOT NULL,
  `question_option1` varchar(255) NOT NULL,
  `question_option2` varchar(255) NOT NULL,
  `question_option3` varchar(255) NOT NULL,
  `question_correct_answer` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Questions`
--

INSERT INTO `Questions` (`question_id`, `question_quiz_id`, `question_text`, `question_difficulty_level`, `question_option1`, `question_option2`, `question_option3`, `question_correct_answer`) VALUES
(1, 2, 'What is the square root of 25?', 'Easy', '3', '5', '6', '5'),
(2, 2, 'Solve the equation: 2x + 3 = 11', 'Medium', '4', '5', '6', '5'),
(3, 2, 'What is the value of pi (π) rounded to two decimal places?', 'Easy', '3.14', '3.16', '3.18', '3.14'),
(4, 2, 'If a triangle has angles measuring 45°, 45°, and 90°, what type of triangle is it?', 'Easy', 'Equilateral', 'Isosceles', 'Right', 'Right'),
(5, 2, 'What is the value of 2^4?', 'Easy', '8', '16', '32', '16'),
(6, 2, 'If a + b = 10 and a - b = 4, what is the value of a?', 'Medium', '3', '5', '7', '7'),
(7, 2, 'What is the next prime number after 7?', 'Medium', '9', '10', '11', '11'),
(8, 2, 'What is the sum of the angles in a triangle?', 'Easy', '90°', '180°', '270°', '180°'),
(9, 2, 'If x + 3 = 7, what is the value of x?', 'Easy', '2', '3', '4', '4'),
(10, 2, 'What is the result of 9 * 8?', 'Easy', '64', '72', '80', '72'),
(11, 3, 'What is the chemical symbol for water?', 'Easy', 'H2O', 'CO2', 'O2', 'H2O'),
(12, 3, 'Who proposed the theory of relativity?', 'Medium', 'Isaac Newton', 'Albert Einstein', 'Stephen Hawking', 'Albert Einstein'),
(13, 3, 'What is the process by which plants make their own food called?', 'Easy', 'Respiration', 'Photosynthesis', 'Transpiration', 'Photosynthesis'),
(14, 3, 'What is the atomic number of oxygen?', 'Easy', '6', '7', '8', '8'),
(15, 3, 'Which planet is known as the Red Planet?', 'Easy', 'Jupiter', 'Venus', 'Mars', 'Mars'),
(16, 3, 'What is the chemical symbol for gold?', 'Medium', 'Au', 'Ag', 'Fe', 'Au'),
(17, 3, 'What is the hardest substance found in nature?', 'Medium', 'Diamond', 'Graphite', 'Coal', 'Diamond'),
(18, 3, 'What is the study of living organisms called?', 'Easy', 'Geology', 'Biology', 'Chemistry', 'Biology'),
(19, 3, 'Which gas is most abundant in Earth\'s atmosphere?', 'Easy', 'Oxygen', 'Carbon dioxide', 'Nitrogen', 'Nitrogen'),
(20, 3, 'What is the formula for Newton\'s second law of motion?', 'Medium', 'F = ma', 'E = mc^2', 'E = hf', 'F = ma'),
(21, 4, 'In which year did World War II end?', 'Easy', '1943', '1945', '1950', '1945'),
(22, 4, 'Who was the first President of the United States?', 'Easy', 'Thomas Jefferson', 'George Washington', 'Abraham Lincoln', 'George Washington'),
(23, 4, 'Which civilization built the Machu Picchu?', 'Medium', 'Aztec', 'Maya', 'Inca', 'Inca'),
(24, 4, 'Who was the first female Prime Minister of the United Kingdom?', 'Medium', 'Margaret Thatcher', 'Theresa May', 'Angela Merkel', 'Margaret Thatcher'),
(25, 4, 'Who wrote \"The Communist Manifesto\"?', 'Easy', 'Karl Marx', 'Friedrich Engels', 'Lenin', 'Karl Marx'),
(26, 4, 'In which country did the Industrial Revolution begin?', 'Easy', 'France', 'Germany', 'United Kingdom', 'United Kingdom'),
(27, 4, 'What year did Christopher Columbus discover America?', 'Medium', '1492', '1505', '1532', '1492'),
(28, 4, 'Who was the first Emperor of Rome?', 'Medium', 'Augustus', 'Julius Caesar', 'Nero', 'Augustus'),
(29, 4, 'Which war is often referred to as the \"War to End All Wars\"?', 'Easy', 'World War I', 'World War II', 'Korean War', 'World War I'),
(30, 4, 'Who painted the Mona Lisa?', 'Easy', 'Vincent van Gogh', 'Leonardo da Vinci', 'Pablo Picasso', 'Leonardo da Vinci'),
(31, 5, 'Who wrote \"Romeo and Juliet\"?', 'Easy', 'William Shakespeare', 'Jane Austen', 'Charles Dickens', 'William Shakespeare'),
(32, 5, 'In which novel would you find the character Atticus Finch?', 'Medium', 'To Kill a Mockingbird', 'The Great Gatsby', 'Pride and Prejudice', 'To Kill a Mockingbird'),
(33, 5, 'Who wrote \"Pride and Prejudice\"?', 'Easy', 'Emily Brontë', 'Charlotte Brontë', 'Jane Austen', 'Jane Austen'),
(34, 5, 'Which Shakespearean play features the character Hamlet?', 'Medium', 'Macbeth', 'King Lear', 'Hamlet', 'Hamlet'),
(35, 5, 'Who wrote \"The Catcher in the Rye\"?', 'Medium', 'J.D. Salinger', 'F. Scott Fitzgerald', 'Ernest Hemingway', 'J.D. Salinger'),
(36, 5, 'In which novel would you find the character Holden Caulfield?', 'Medium', 'To Kill a Mockingbird', 'The Catcher in the Rye', '1984', 'The Catcher in the Rye'),
(37, 5, 'Who wrote \"To Kill a Mockingbird\"?', 'Easy', 'Harper Lee', 'Mark Twain', 'John Steinbeck', 'Harper Lee'),
(38, 5, 'In which novel would you find the character Jay Gatsby?', 'Medium', 'To Kill a Mockingbird', 'The Great Gatsby', 'Pride and Prejudice', 'The Great Gatsby'),
(39, 5, 'Who wrote \"The Great Gatsby\"?', 'Easy', 'F. Scott Fitzgerald', 'Ernest Hemingway', 'J.D. Salinger', 'F. Scott Fitzgerald'),
(40, 5, 'In which novel would you find the character Elizabeth Bennet?', 'Medium', 'To Kill a Mockingbird', 'The Great Gatsby', 'Pride and Prejudice', 'Pride and Prejudice'),
(41, 6, 'What is the capital of France?', 'Easy', 'Paris', 'London', 'Berlin', 'Paris'),
(42, 6, 'Which river is the longest in the world?', 'Medium', 'Nile', 'Amazon', 'Mississippi', 'Nile'),
(43, 6, 'What is the largest desert in the world?', 'Easy', 'Sahara', 'Gobi', 'Arabian', 'Sahara'),
(44, 6, 'What is the highest mountain peak in the world?', 'Medium', 'Mount Everest', 'K2', 'Kangchenjunga', 'Mount Everest'),
(45, 6, 'Which country is known as the Land of the Rising Sun?', 'Easy', 'China', 'India', 'Japan', 'Japan'),
(46, 6, 'What is the smallest continent?', 'Easy', 'Asia', 'Africa', 'Australia', 'Australia'),
(47, 6, 'Which country is both in Europe and Asia?', 'Medium', 'Russia', 'Turkey', 'Kazakhstan', 'Russia'),
(48, 6, 'What is the capital of Canada?', 'Easy', 'Toronto', 'Ottawa', 'Vancouver', 'Ottawa'),
(49, 6, 'Which ocean is the largest?', 'Easy', 'Atlantic', 'Indian', 'Pacific', 'Pacific'),
(50, 6, 'What is the capital of Australia?', 'Medium', 'Sydney', 'Melbourne', 'Canberra', 'Canberra');

-- --------------------------------------------------------

--
-- Table structure for table `Quizzes`
--

CREATE TABLE `Quizzes` (
  `quiz_id` int(11) NOT NULL,
  `quiz_title` varchar(255) NOT NULL,
  `quiz_description` text NOT NULL,
  `quiz_duration` varchar(255) NOT NULL,
  `quiz_subject` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Quizzes`
--

INSERT INTO `Quizzes` (`quiz_id`, `quiz_title`, `quiz_description`, `quiz_duration`, `quiz_subject`) VALUES
(2, 'Math Quiz', 'Test your math skills with this quiz', '15', 'Mathematics'),
(3, 'Science Quiz', 'Explore various scientific concepts in this quiz', '20', 'Science'),
(4, 'History Quiz', 'Test your knowledge of historical events', '30', 'History'),
(5, 'Literature Quiz', 'Explore famous literary works in this quiz', '40', 'Literature'),
(6, 'Geography Quiz', 'Test your geography knowledge with this quiz', '60', 'Geography'),
(7, 'haha', 'haha', '20', 'Science');

-- --------------------------------------------------------

--
-- Table structure for table `rating`
--

CREATE TABLE `rating` (
  `ratingId` int(11) NOT NULL,
  `ratingValue` int(11) NOT NULL,
  `ratingDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ratedCourse` int(11) DEFAULT NULL,
  `ratingUser` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rating`
--

INSERT INTO `rating` (`ratingId`, `ratingValue`, `ratingDate`, `ratedCourse`, `ratingUser`) VALUES
(1, 2, '2024-03-06 22:36:49', 1, 1),
(2, 5, '2024-03-03 23:16:06', 1, 1),
(3, 5, '2024-03-03 23:16:12', 1, 1),
(4, 5, '2024-03-03 23:16:13', 1, 1),
(5, 5, '2024-03-03 23:16:13', 1, 1),
(6, 5, '2024-03-03 23:16:13', 1, 1),
(7, 4, '2024-03-03 23:16:43', 1, 1),
(8, 5, '2024-03-03 23:17:31', 1, 1),
(9, 3, '2024-03-03 23:34:50', 1, 1),
(10, 3, '2024-03-03 23:34:53', 1, 1),
(11, 4, '2024-03-03 23:43:31', 1, 1),
(12, 5, '2024-03-04 12:40:36', 1, 1),
(13, 4, '2024-03-04 14:08:17', 1, 1),
(14, 4, '2024-03-04 14:08:19', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `reportId` int(11) NOT NULL,
  `reportDescription` varchar(255) NOT NULL,
  `reportType` varchar(255) NOT NULL,
  `reportDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `reportedCourse` varchar(255) DEFAULT NULL,
  `reporterUser` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`reportId`, `reportDescription`, `reportType`, `reportDate`, `reportedCourse`, `reporterUser`) VALUES
(2, 'Ok', 'Harassment', '2024-03-03 10:28:30', 'Math', 1),
(5, 'AAAAAAAAA', 'Technical Issues', '2024-03-03 21:21:07', 'Java', 1),
(6, 'fsdhfsdbfsdb', 'Scam & Fraud', '2024-03-04 14:09:42', 'Math', 1),
(7, 'fgsdfd', 'Harassment', '2024-03-04 14:14:56', 'Java', 1);

-- --------------------------------------------------------

--
-- Table structure for table `session`
--

CREATE TABLE `session` (
  `sessionID` int(11) NOT NULL,
  `sessionName` varchar(255) NOT NULL,
  `sessionDescription` varchar(255) NOT NULL,
  `subjectid` int(11) DEFAULT NULL,
  `sessionDriveLink` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `session`
--

INSERT INTO `session` (`sessionID`, `sessionName`, `sessionDescription`, `subjectid`, `sessionDriveLink`) VALUES
(12, 'Chapter 2', 'Second', 6, NULL),
(13, 'Mathh', 'L', 7, NULL),
(15, 'S', 'L', 7, NULL),
(16, 'L', 'dd', 11, NULL),
(17, 'Aaaa', '45', 7, 'https'),
(18, 'a', 'aa', 7, 'aaa'),
(20, '1', '2', 17, '3'),
(21, 'Math', 'Math', 19, 'google.com');

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
  `subjectId` int(11) NOT NULL,
  `subjectName` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `subjectPrice` int(11) NOT NULL,
  `subjectTopic` varchar(255) NOT NULL,
  `NbrSession` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`subjectId`, `subjectName`, `description`, `subjectPrice`, `subjectTopic`, `NbrSession`) VALUES
(18, 'Math', 'Advanced Math', 30, 'Mathematics', NULL),
(20, 'Java', 'Java', 50, 'Mathematics', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `user_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `bio` text NOT NULL,
  `phone_number` varchar(8) NOT NULL,
  `profile_picture` varchar(255) NOT NULL,
  `module_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`user_id`, `first_name`, `last_name`, `email`, `password`, `date_of_birth`, `gender`, `role`, `bio`, `phone_number`, `profile_picture`, `module_name`) VALUES
(1, 'chayma', 'chayma', 'chayma', 'chayma', '2024-03-02', 'female', 'admin', 'aaa', '12345678', 'qqqq', 'qqqqq'),
(2, 'mohamed', 'chayma', 'chayma', 'chayma', '2024-03-02', 'female', 'admin', 'aaa', '12345678', '/Users/macbook/Downloads/logo.jpg', 'qqqqq'),
(3, 'mohamed', 'mohamed', 'chaymabenhmida00@gmail.com', '$2a$10$W2ltB5rZRUS5eQJaJaAbduTOzuSqD/homNOmONoj7opByW.TiK1p2', '2024-03-02', 'Male', 'admin', 'aaaa', '12345678', '/Users/macbook/Downloads/logo.jpg', 'anglais'),
(4, 'ahmed', 'ahmed', 'cbha@ymail.com', 'azerty123', '2024-03-02', 'Male', 'scholar', 'haha', '98263558', '/Users/macbook/Downloads/logo.jpg', 'Mathematics'),
(5, 'a', 'a', 'a@a.com', 'aa', '2024-03-03', 'Male', 'scholar', 'a', '12345678', '/Users/macbook/Downloads/logo.jpg', 'Mathematics'),
(6, 'b', 'b', 'b@b.com', '$2a$10$b8YurvyyL3jCQVTi2tu5nexQ2MsB.nmM1rpoAdwfiBXDbNweJSx1u', '2024-03-03', 'Female', 'scholar', 'b', '12345678', 'dwcwecewc', 'Mathematics'),
(7, 'cyrine', 'gheribi', 'chaymabenhmida00@gmail.com', '$2a$10$W2ltB5rZRUS5eQJaJaAbduTOzuSqD/homNOmONoj7opByW.TiK1p2', '2024-02-29', 'Female', 'scholar', 'cccrrr', '54545454', 'ffff', 'Mathematics'),
(8, 'admin', 'admin', 'admin@admin.tn', '$2a$10$QhswrV1plpsD9W4ETYDMye80XxftkLWUNHPF9wQWSyTRj7ngJ0sau', '2024-02-29', 'Male', 'admin', 'erfrfrf', '23232323', 'rfrfrf', 'Science'),
(9, 'Ghassen', 'Klai', 'ghassen.klai@esprit.tn', '$2a$10$28k/zwU59TsaGHngPj/gE.63dIYgq5kf/A5HiW6anQCrkMlSjRfdq', '2024-03-04', 'Male', 'scholar', 'Bio', '12345678', 'URL', 'Mathematics');

-- --------------------------------------------------------

--
-- Table structure for table `user_results`
--

CREATE TABLE `user_results` (
  `result_user_id` int(11) NOT NULL,
  `result_quiz_id` int(11) NOT NULL,
  `result_score` int(11) NOT NULL,
  `result_date` datetime NOT NULL,
  `result_quiz_title` varchar(255) NOT NULL,
  `result_quiz_subject` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_results`
--

INSERT INTO `user_results` (`result_user_id`, `result_quiz_id`, `result_score`, `result_date`, `result_quiz_title`, `result_quiz_subject`) VALUES
(0, 0, 2, '2024-02-29 00:46:07', '0', '0'),
(3, 6, 4, '2024-03-02 12:51:03', 'Geography Quiz', 'Geography'),
(3, 6, 4, '2024-03-02 12:58:03', 'Geography Quiz', 'Geography'),
(3, 6, 4, '2024-03-02 12:58:04', 'Geography Quiz', 'Geography'),
(3, 6, 4, '2024-03-02 12:58:59', 'Geography Quiz', 'Geography'),
(3, 2, 0, '2024-03-02 13:02:39', 'Math Quiz', 'Mathematics'),
(3, 2, 0, '2024-03-02 13:06:47', 'Math Quiz', 'Mathematics'),
(3, 2, 0, '2024-03-02 13:12:42', 'Math Quiz', 'Mathematics'),
(3, 2, 0, '2024-03-02 13:13:23', 'Math Quiz', 'Mathematics'),
(3, 2, 0, '2024-03-02 13:18:10', 'Math Quiz', 'Mathematics'),
(3, 2, 0, '2024-03-02 13:18:11', 'Math Quiz', 'Mathematics'),
(3, 0, 0, '2024-03-02 13:22:15', 'Math Quiz', 'Mathematics'),
(3, 0, 0, '2024-03-02 13:25:13', 'Math Quiz', 'Mathematics'),
(3, 0, 0, '2024-03-02 13:28:17', 'Math Quiz', 'Mathematics'),
(3, 0, 0, '2024-03-02 13:33:40', 'Math Quiz', 'Mathematics'),
(3, 0, 0, '2024-03-02 15:02:40', 'Math Quiz', 'Mathematics'),
(3, 0, 0, '2024-03-02 15:02:41', 'Math Quiz', 'Mathematics'),
(3, 0, 0, '2024-03-02 15:04:53', 'Geography Quiz', 'Geography'),
(3, 0, 0, '2024-03-02 15:06:22', 'Geography Quiz', 'Geography'),
(3, 0, 1, '2024-03-02 15:09:16', 'Math Quiz', 'Mathematics'),
(3, 0, 0, '2024-03-02 15:12:01', 'Geography Quiz', 'Geography'),
(3, 0, 0, '2024-03-02 15:14:18', 'Science Quiz', 'Science'),
(3, 0, 9, '2024-03-02 18:38:27', 'Math Quiz', 'Mathematics'),
(3, 0, 9, '2024-03-02 18:45:59', 'Geography Quiz', 'Geography'),
(3, 0, 5, '2024-03-02 18:47:24', 'Geography Quiz', 'Geography'),
(4, 0, 8, '2024-03-02 19:36:27', 'Math Quiz', 'Mathematics'),
(3, 0, 1, '2024-03-02 23:03:01', 'Math Quiz', 'Mathematics'),
(3, 0, 1, '2024-03-02 23:07:57', 'Math Quiz', 'Mathematics'),
(3, 0, 1, '2024-03-02 23:29:20', 'Math Quiz', 'Mathematics'),
(3, 0, 1, '2024-03-02 23:41:36', 'Math Quiz', 'Mathematics'),
(3, 0, 1, '2024-03-02 23:49:46', 'Math Quiz', 'Mathematics'),
(3, 0, 1, '2024-03-02 23:51:13', 'Math Quiz', 'Mathematics'),
(6, 0, 1, '2024-03-03 02:53:09', 'Math Quiz', 'Mathematics'),
(6, 0, 1, '2024-03-03 13:33:52', 'Math Quiz', 'Mathematics'),
(6, 0, 0, '2024-03-03 13:46:31', 'Math Quiz', 'Mathematics'),
(6, 0, 0, '2024-03-03 13:47:00', 'Math Quiz', 'Mathematics'),
(6, 0, 0, '2024-03-03 13:51:16', 'Math Quiz', 'Mathematics'),
(6, 0, 1, '2024-03-03 13:52:30', 'Math Quiz', 'Mathematics'),
(6, 0, 1, '2024-03-03 13:52:44', 'Math Quiz', 'Mathematics'),
(6, 0, 1, '2024-03-03 13:52:50', 'Math Quiz', 'Mathematics'),
(6, 0, 1, '2024-03-03 13:53:02', 'Math Quiz', 'Mathematics'),
(6, 0, 8, '2024-03-03 13:54:18', 'Math Quiz', 'Mathematics'),
(6, 0, 7, '2024-03-03 13:56:22', 'Geography Quiz', 'Geography'),
(6, 0, 0, '2024-03-03 14:26:01', 'Math Quiz', 'Mathematics'),
(6, 0, 0, '2024-03-03 14:26:03', 'Math Quiz', 'Mathematics'),
(6, 0, 0, '2024-03-03 14:32:44', 'Geography Quiz', 'Geography'),
(6, 0, 5, '2024-03-04 13:08:28', 'Math Quiz', 'Mathematics'),
(6, 0, 5, '2024-03-04 13:09:10', 'Science Quiz', 'Science'),
(6, 0, 5, '2024-03-04 13:09:20', 'Math Quiz', 'Mathematics'),
(6, 0, 5, '2024-03-04 13:09:30', 'Math Quiz', 'Mathematics'),
(7, 0, 2, '2024-03-04 13:49:40', 'Math Quiz', 'Mathematics'),
(7, 0, 2, '2024-03-04 13:49:42', 'Math Quiz', 'Mathematics'),
(7, 0, 2, '2024-03-04 13:49:54', 'Math Quiz', 'Mathematics'),
(7, 0, 0, '2024-03-04 13:54:14', 'Math Quiz', 'Mathematics'),
(7, 0, 0, '2024-03-04 13:54:17', 'Math Quiz', 'Mathematics'),
(7, 0, 0, '2024-03-04 13:55:20', 'Math Quiz', 'Mathematics'),
(7, 0, 0, '2024-03-04 13:55:23', 'Math Quiz', 'Mathematics'),
(9, 0, 1, '2024-03-04 15:01:10', 'Math Quiz', 'Mathematics'),
(9, 0, 1, '2024-03-04 15:01:11', 'Math Quiz', 'Mathematics'),
(7, 0, 1, '2024-03-05 08:38:50', 'Geography Quiz', 'Geography'),
(7, 0, 1, '2024-03-05 08:39:09', 'Geography Quiz', 'Geography'),
(7, 0, 1, '2024-03-05 08:39:11', 'Geography Quiz', 'Geography');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`idC`);

--
-- Indexes for table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`module_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentID`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`idP`);

--
-- Indexes for table `Questions`
--
ALTER TABLE `Questions`
  ADD PRIMARY KEY (`question_id`),
  ADD KEY `fk_quiz_id` (`question_quiz_id`);

--
-- Indexes for table `Quizzes`
--
ALTER TABLE `Quizzes`
  ADD PRIMARY KEY (`quiz_id`);

--
-- Indexes for table `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`ratingId`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`reportId`);

--
-- Indexes for table `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`sessionID`),
  ADD KEY `subjectid` (`subjectid`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`subjectId`);

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `idC` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `modules`
--
ALTER TABLE `modules`
  MODIFY `module_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `paymentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `post`
--
ALTER TABLE `post`
  MODIFY `idP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=82;

--
-- AUTO_INCREMENT for table `Questions`
--
ALTER TABLE `Questions`
  MODIFY `question_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `Quizzes`
--
ALTER TABLE `Quizzes`
  MODIFY `quiz_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `rating`
--
ALTER TABLE `rating`
  MODIFY `ratingId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `reportId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `session`
--
ALTER TABLE `session`
  MODIFY `sessionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `subject`
--
ALTER TABLE `subject`
  MODIFY `subjectId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Questions`
--
ALTER TABLE `Questions`
  ADD CONSTRAINT `fk_quiz_id` FOREIGN KEY (`question_quiz_id`) REFERENCES `Quizzes` (`quiz_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
