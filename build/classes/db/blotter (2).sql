-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 18, 2025 at 12:42 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blotter`
--

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `report_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `incident_type` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `date_of_incident` date DEFAULT NULL,
  `time_of_incident` time DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`report_id`, `u_id`, `full_name`, `incident_type`, `description`, `location`, `date_of_incident`, `time_of_incident`, `status`) VALUES
(1, 0, 'Ross Sabio', 'Theft', 'TInakbu ang Cellphone ko', 'Ward 2', '2025-05-12', '01:17:00', 'Resolved'),
(2, 3, 'Jay Reyes', 'Assault', 'Stab in the Stomach', 'Ward 1', '2025-05-03', '14:06:00', 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_log`
--

CREATE TABLE `tbl_log` (
  `log_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `u_username` varchar(50) NOT NULL,
  `login_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `u_type` varchar(50) NOT NULL,
  `log_status` enum('Pending','Active','Inactive','') NOT NULL,
  `logout_time` timestamp NULL DEFAULT NULL,
  `log_description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_log`
--

INSERT INTO `tbl_log` (`log_id`, `u_id`, `u_username`, `login_time`, `u_type`, `log_status`, `logout_time`, `log_description`) VALUES
(1, 3, 'rose123', '2025-05-18 10:13:29', 'Success - User Action', 'Inactive', '2025-05-18 10:13:29', 'New user registered: rose123'),
(2, 3, 'rose123', '2025-05-18 10:13:29', 'Failed - Inactive Account', 'Inactive', '2025-05-18 10:13:29', NULL),
(3, 3, 'rose123', '2025-05-18 10:13:29', 'Success - Admin Login', 'Inactive', '2025-05-18 10:13:29', NULL),
(4, 3, 'rose123', '2025-05-18 07:49:14', 'Success - Admin Login', 'Inactive', '2025-05-18 07:49:14', NULL),
(5, 3, 'rose123', '2025-05-18 07:50:02', 'Success - Admin Login', 'Inactive', '2025-05-18 07:50:02', NULL),
(6, 3, 'rose123', '2025-05-18 09:41:32', 'Success - Admin Login', 'Inactive', '2025-05-18 09:41:32', NULL),
(7, 3, 'rose123', '2025-05-18 10:13:29', 'Success - Staff Login', 'Inactive', '2025-05-18 10:13:29', NULL),
(8, 3, 'rose123', '2025-05-18 09:47:34', 'Success - Admin Login', 'Inactive', '2025-05-18 09:47:34', NULL),
(9, 3, 'rose123', '2025-05-18 09:50:36', 'Success - Admin Login', 'Inactive', '2025-05-18 09:50:36', NULL),
(10, 3, 'rose123', '2025-05-18 09:51:37', 'Success - Admin Login', 'Inactive', '2025-05-18 09:51:37', NULL),
(11, 4, 'tupac123', '2025-05-18 10:02:10', 'Success - User Action', 'Active', NULL, 'New user registered: tupac123'),
(12, 4, 'tupac123', '2025-05-18 10:07:19', 'Failed - Inactive Account', 'Inactive', '2025-05-18 10:07:19', NULL),
(13, 4, 'tupac123', '2025-05-18 10:02:30', 'Success - Police Login', 'Inactive', '2025-05-18 10:02:30', NULL),
(14, 4, 'tupac123', '2025-05-18 10:07:16', 'Success - Police Login', 'Inactive', '2025-05-18 10:07:16', NULL),
(15, 3, 'rose123', '2025-05-18 10:13:29', 'Success - User Login', 'Inactive', '2025-05-18 10:13:29', NULL),
(16, 1, 'ross123', '2025-05-18 10:14:14', 'Success - Admin Login', 'Inactive', '2025-05-18 10:14:14', NULL),
(17, 3, 'rose123', '2025-05-18 10:27:20', 'Success - User Login', 'Inactive', '2025-05-18 10:27:20', NULL),
(18, 3, 'rose123', '2025-05-18 10:27:20', 'Success - User Login', 'Inactive', '2025-05-18 10:27:20', NULL),
(19, 3, 'rose123', '2025-05-18 10:29:14', 'Success - User Login', 'Inactive', '2025-05-18 10:29:14', NULL),
(20, 3, 'rose123', '2025-05-18 10:37:07', 'Success - User Login', 'Inactive', '2025-05-18 10:37:07', NULL),
(22, 3, 'rose123', '2025-05-18 10:37:07', 'Success - User Login', 'Inactive', '2025-05-18 10:37:07', NULL),
(23, 3, 'rose123', '2025-05-18 10:37:07', 'Submitted a new blotter report', 'Inactive', '2025-05-18 10:37:07', NULL),
(24, 3, 'rose123', '2025-05-18 10:37:07', 'Success - User Login', 'Inactive', '2025-05-18 10:37:07', NULL),
(25, 3, 'rose123', '2025-05-18 10:39:25', 'Success - User Login', 'Active', NULL, NULL),
(26, 3, 'rose123', '2025-05-18 10:40:12', 'Success - User Login', 'Active', NULL, NULL),
(27, 3, 'rose123', '2025-05-18 10:40:59', 'Success - User Login', 'Active', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `u_id` int(11) NOT NULL,
  `u_fname` varchar(255) NOT NULL,
  `u_lname` varchar(255) NOT NULL,
  `u_username` varchar(255) NOT NULL,
  `u_email` varchar(255) NOT NULL,
  `u_status` varchar(255) NOT NULL,
  `u_type` varchar(2555) NOT NULL,
  `u_password` varchar(255) NOT NULL,
  `u_image` varchar(255) NOT NULL,
  `security_question` varchar(100) NOT NULL,
  `security_answer` varchar(55) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`u_id`, `u_fname`, `u_lname`, `u_username`, `u_email`, `u_status`, `u_type`, `u_password`, `u_image`, `security_question`, `security_answer`) VALUES
(1, 'ross', 'sabio', 'ross123', 'rosssabio@gmail.com', 'Active', 'Admin', 'ky88G1YlfOhTmsJp16q0JVDaz4gY0HXwvfGZBWKq4+8=', '', '', ''),
(2, 'mark', 'pacaldo', 'mark123', 'mark@gmail.com', 'Active', 'User', 'ky88G1YlfOhTmsJp16q0JVDaz4gY0HXwvfGZBWKq4+8=', '', '', ''),
(3, 'rose', 'reyes', 'rose123', 'rose@gmail.com', 'Active', 'User', 'ky88G1YlfOhTmsJp16q0JVDaz4gY0HXwvfGZBWKq4+8=', 'Null', 'What\'s your favorite food?', 'qV/QG0FakhHmXWW/9kfkNE5/GUje75Wbca8h9ippdXE='),
(4, 'tupac', 'pactu', 'tupac123', 'tupac123@gmail.com', 'Active', 'Police', 'ky88G1YlfOhTmsJp16q0JVDaz4gY0HXwvfGZBWKq4+8=', 'Null', 'What\'s the name of your first pet?', 'Fkd2iMDgBpnGz6RJejYS1+g8UyBitkslD+2JCBKO1Ug=');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`report_id`);

--
-- Indexes for table `tbl_log`
--
ALTER TABLE `tbl_log`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `u_id` (`u_id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`u_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `report_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_log`
--
ALTER TABLE `tbl_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_log`
--
ALTER TABLE `tbl_log`
  ADD CONSTRAINT `tbl_log_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `tbl_users` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
