-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 23, 2026 at 10:47 PM
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
-- Database: `disaster_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `assignments`
--

CREATE TABLE `assignments` (
  `id` bigint(20) NOT NULL,
  `disaster_id` bigint(20) NOT NULL,
  `volunteer_id` bigint(20) NOT NULL,
  `status` enum('IN_PROGRESS','COMPLETED','FAILED') NOT NULL DEFAULT 'IN_PROGRESS',
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `completion_notes` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `assignments`
--

INSERT INTO `assignments` (`id`, `disaster_id`, `volunteer_id`, `status`, `start_time`, `end_time`, `completion_notes`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'COMPLETED', '2026-01-05 15:45:46', '2026-01-10 17:42:12', NULL, '2026-01-05 15:45:46', '2026-01-10 17:42:12'),
(2, 1, 5, 'COMPLETED', '2026-01-05 15:45:46', '2026-01-10 17:42:12', NULL, '2026-01-05 15:45:46', '2026-01-10 17:42:12'),
(8, 2, 1, 'COMPLETED', '2026-01-10 17:42:34', '2026-01-10 17:44:49', NULL, '2026-01-10 17:42:34', '2026-01-10 17:44:49'),
(9, 2, 4, 'COMPLETED', '2026-01-10 17:42:34', '2026-01-10 17:44:49', NULL, '2026-01-10 17:42:34', '2026-01-10 17:44:49'),
(10, 2, 5, 'COMPLETED', '2026-01-10 17:42:34', '2026-01-10 17:44:49', NULL, '2026-01-10 17:42:34', '2026-01-10 17:44:49'),
(11, 2, 10, 'COMPLETED', '2026-01-10 17:42:34', '2026-01-10 17:44:49', NULL, '2026-01-10 17:42:34', '2026-01-10 17:44:49'),
(12, 2, 12, 'COMPLETED', '2026-01-10 17:42:34', '2026-01-10 17:44:49', NULL, '2026-01-10 17:42:34', '2026-01-10 17:44:49'),
(13, 5, 2, 'COMPLETED', '2026-01-10 17:42:34', '2026-01-10 17:43:59', NULL, '2026-01-10 17:42:34', '2026-01-10 17:43:59'),
(14, 5, 3, 'COMPLETED', '2026-01-10 17:42:34', '2026-01-10 17:43:59', NULL, '2026-01-10 17:42:34', '2026-01-10 17:43:59'),
(15, 5, 6, 'COMPLETED', '2026-01-10 17:42:34', '2026-01-10 17:43:59', NULL, '2026-01-10 17:42:34', '2026-01-10 17:43:59'),
(16, 5, 14, 'COMPLETED', '2026-01-10 17:42:34', '2026-01-10 17:43:59', NULL, '2026-01-10 17:42:34', '2026-01-10 17:43:59'),
(17, 3, 1, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(18, 3, 4, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(19, 3, 2, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(20, 3, 3, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(21, 3, 5, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(22, 3, 6, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(23, 3, 8, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(24, 3, 10, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(25, 3, 9, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(26, 3, 12, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(27, 3, 14, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(28, 3, 13, 'COMPLETED', '2026-01-10 17:44:56', '2026-01-13 14:17:24', NULL, '2026-01-10 17:44:56', '2026-01-13 14:17:24'),
(29, 6, 15, 'COMPLETED', '2026-01-13 14:15:46', '2026-01-13 14:17:38', NULL, '2026-01-13 14:15:46', '2026-01-13 14:17:38'),
(30, 7, 1, 'COMPLETED', '2026-01-13 14:23:05', '2026-01-13 14:24:08', NULL, '2026-01-13 14:23:05', '2026-01-13 14:24:08'),
(31, 7, 12, 'COMPLETED', '2026-01-13 14:23:05', '2026-01-13 14:24:08', NULL, '2026-01-13 14:23:05', '2026-01-13 14:24:08'),
(32, 8, 1, 'COMPLETED', '2026-01-14 03:31:19', '2026-01-14 03:32:20', NULL, '2026-01-14 03:31:19', '2026-01-14 03:32:20'),
(33, 9, 1, 'COMPLETED', '2026-01-14 03:39:02', '2026-01-14 03:40:03', NULL, '2026-01-14 03:39:02', '2026-01-14 03:40:03'),
(34, 9, 4, 'COMPLETED', '2026-01-14 03:39:02', '2026-01-14 03:40:03', NULL, '2026-01-14 03:39:02', '2026-01-14 03:40:03'),
(35, 9, 5, 'COMPLETED', '2026-01-14 03:39:02', '2026-01-14 03:40:03', NULL, '2026-01-14 03:39:02', '2026-01-14 03:40:03'),
(36, 9, 10, 'COMPLETED', '2026-01-14 03:39:02', '2026-01-14 03:40:03', NULL, '2026-01-14 03:39:02', '2026-01-14 03:40:03'),
(37, 9, 12, 'COMPLETED', '2026-01-14 03:39:02', '2026-01-14 03:40:03', NULL, '2026-01-14 03:39:02', '2026-01-14 03:40:03'),
(38, 10, 1, 'COMPLETED', '2026-01-14 04:30:43', '2026-01-14 04:31:44', NULL, '2026-01-14 04:30:43', '2026-01-14 04:31:44'),
(39, 10, 4, 'COMPLETED', '2026-01-14 04:30:43', '2026-01-14 04:31:44', NULL, '2026-01-14 04:30:43', '2026-01-14 04:31:44'),
(40, 10, 5, 'COMPLETED', '2026-01-14 04:30:43', '2026-01-14 04:31:44', NULL, '2026-01-14 04:30:43', '2026-01-14 04:31:44'),
(41, 10, 10, 'COMPLETED', '2026-01-14 04:30:43', '2026-01-14 04:31:44', NULL, '2026-01-14 04:30:43', '2026-01-14 04:31:44'),
(42, 10, 12, 'COMPLETED', '2026-01-14 04:30:43', '2026-01-14 04:31:44', NULL, '2026-01-14 04:30:43', '2026-01-14 04:31:44');

-- --------------------------------------------------------

--
-- Table structure for table `disasters`
--

CREATE TABLE `disasters` (
  `id` bigint(20) NOT NULL,
  `type` enum('FLOOD','EARTHQUAKE','FIRE','HURRICANE','TSUNAMI') NOT NULL,
  `location` varchar(100) NOT NULL,
  `severity` enum('LOW','MEDIUM','HIGH','CRITICAL') NOT NULL,
  `status` enum('PENDING','ACTIVE','RESOLVED','CANCELLED') NOT NULL DEFAULT 'PENDING',
  `description` text DEFAULT NULL,
  `estimated_casualties` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `disasters`
--

INSERT INTO `disasters` (`id`, `type`, `location`, `severity`, `status`, `description`, `estimated_casualties`, `created_at`, `updated_at`) VALUES
(1, 'FLOOD', 'Downtown Area', 'HIGH', 'RESOLVED', 'Major flooding due to heavy rainfall. Multiple areas affected.', 50, '2026-01-05 15:45:46', '2026-01-10 17:42:12'),
(2, 'EARTHQUAKE', 'Northern Region', 'MEDIUM', 'RESOLVED', 'Moderate earthquake measuring 5.8 on Richter scale.', 25, '2026-01-05 15:45:46', '2026-01-10 17:44:49'),
(3, 'FIRE', 'Industrial Zone', 'CRITICAL', 'RESOLVED', 'Massive fire outbreak in chemical factory.', 100, '2026-01-05 15:45:46', '2026-01-13 14:17:16'),
(5, 'FIRE', 'UK', 'MEDIUM', 'RESOLVED', 'FIREEEEEEEEEE', 5, '2026-01-10 17:03:59', '2026-01-10 17:43:59'),
(6, 'FLOOD', 'UK', 'LOW', 'RESOLVED', 'flood', 3, '2026-01-13 14:15:35', '2026-01-13 14:17:31'),
(7, 'EARTHQUAKE', 'UK', 'LOW', 'RESOLVED', 'earthquake', 10, '2026-01-13 14:22:08', '2026-01-13 14:24:08'),
(8, 'FLOOD', 'UK', 'LOW', 'RESOLVED', 'Flood In Uk !! Hurry up', 3, '2026-01-14 03:30:56', '2026-01-14 03:32:20'),
(9, 'EARTHQUAKE', 'UK', 'MEDIUM', 'RESOLVED', 'EarthQuake in uk , Hurry up plz!!!!!', 10, '2026-01-14 03:38:10', '2026-01-14 03:40:03'),
(10, 'EARTHQUAKE', 'UK', 'MEDIUM', 'RESOLVED', 'hurry up , ', 10, '2026-01-14 04:30:16', '2026-01-14 04:31:44');

-- --------------------------------------------------------

--
-- Table structure for table `simulation_logs`
--

CREATE TABLE `simulation_logs` (
  `id` bigint(20) NOT NULL,
  `log_type` varchar(50) NOT NULL,
  `message` text NOT NULL,
  `disaster_id` bigint(20) DEFAULT NULL,
  `volunteer_id` bigint(20) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `simulation_logs`
--

INSERT INTO `simulation_logs` (`id`, `log_type`, `message`, `disaster_id`, `volunteer_id`, `created_at`) VALUES
(1, 'SYSTEM', 'Database initialized successfully', NULL, NULL, '2026-01-05 15:45:46'),
(2, 'DISASTER', 'Flood detected in Downtown Area', 1, NULL, '2026-01-05 15:45:46'),
(3, 'VOLUNTEER', 'John Rescue assigned to flood disaster', 1, 1, '2026-01-05 15:45:46'),
(4, 'VOLUNTEER', 'Dr. Smith assigned to flood disaster', 1, 5, '2026-01-05 15:45:46');

-- --------------------------------------------------------

--
-- Table structure for table `threshold_rules`
--

CREATE TABLE `threshold_rules` (
  `id` bigint(20) NOT NULL,
  `disaster_type` enum('FLOOD','EARTHQUAKE','FIRE','HURRICANE','TSUNAMI') NOT NULL,
  `severity` enum('LOW','MEDIUM','HIGH','CRITICAL') NOT NULL,
  `rescue_required` int(11) NOT NULL DEFAULT 0,
  `medical_required` int(11) NOT NULL DEFAULT 0,
  `supply_required` int(11) NOT NULL DEFAULT 0,
  `engineer_required` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `threshold_rules`
--

INSERT INTO `threshold_rules` (`id`, `disaster_type`, `severity`, `rescue_required`, `medical_required`, `supply_required`, `engineer_required`, `created_at`, `updated_at`) VALUES
(1, 'FLOOD', 'LOW', 1, 0, 0, 0, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(2, 'FLOOD', 'MEDIUM', 1, 1, 1, 0, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(3, 'FLOOD', 'HIGH', 2, 1, 2, 1, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(4, 'FLOOD', 'CRITICAL', 3, 2, 2, 2, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(5, 'EARTHQUAKE', 'LOW', 1, 0, 0, 1, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(6, 'EARTHQUAKE', 'MEDIUM', 2, 1, 1, 1, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(7, 'EARTHQUAKE', 'HIGH', 3, 2, 1, 2, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(8, 'EARTHQUAKE', 'CRITICAL', 4, 3, 2, 3, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(9, 'FIRE', 'LOW', 1, 0, 0, 0, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(10, 'FIRE', 'MEDIUM', 2, 1, 0, 1, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(11, 'FIRE', 'HIGH', 3, 2, 1, 2, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(12, 'FIRE', 'CRITICAL', 4, 3, 2, 3, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(13, 'HURRICANE', 'LOW', 2, 0, 1, 0, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(14, 'HURRICANE', 'MEDIUM', 3, 1, 2, 1, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(15, 'HURRICANE', 'HIGH', 4, 2, 3, 2, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(16, 'HURRICANE', 'CRITICAL', 5, 3, 4, 3, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(17, 'TSUNAMI', 'LOW', 2, 1, 1, 1, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(18, 'TSUNAMI', 'MEDIUM', 3, 2, 2, 2, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(19, 'TSUNAMI', 'HIGH', 4, 3, 3, 3, '2026-01-05 15:45:46', '2026-01-05 15:45:46'),
(20, 'TSUNAMI', 'CRITICAL', 5, 4, 4, 4, '2026-01-05 15:45:46', '2026-01-05 15:45:46');

-- --------------------------------------------------------

--
-- Table structure for table `volunteers`
--

CREATE TABLE `volunteers` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `category` enum('RESCUE','MEDICAL','SUPPLY','ENGINEER') NOT NULL,
  `status` enum('AVAILABLE','ASSIGNED','BUSY','RESTING') NOT NULL DEFAULT 'AVAILABLE',
  `experience_level` int(11) DEFAULT 1,
  `phone_number` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `volunteers`
--

INSERT INTO `volunteers` (`id`, `name`, `category`, `status`, `experience_level`, `phone_number`, `email`, `created_at`, `updated_at`) VALUES
(1, 'Abid', 'RESCUE', 'AVAILABLE', 5, '555-0101', 'john@rescue.com', '2026-01-05 15:45:46', '2026-01-14 04:31:44'),
(2, 'Sarah', 'RESCUE', 'AVAILABLE', 4, '555-0102', 'sarah@rescue.com', '2026-01-05 15:45:46', '2026-01-14 08:09:00'),
(3, 'Mehmood', 'RESCUE', 'AVAILABLE', 3, '555-0103', 'mike@rescue.com', '2026-01-05 15:45:46', '2026-01-14 08:09:13'),
(4, 'Amna', 'RESCUE', 'AVAILABLE', 5, '555-0104', 'emma@rescue.com', '2026-01-05 15:45:46', '2026-01-14 04:31:44'),
(5, 'Dr. khan', 'MEDICAL', 'AVAILABLE', 5, '555-0201', 'dr.smith@medical.com', '2026-01-05 15:45:46', '2026-01-14 04:31:44'),
(6, 'Nurse', 'MEDICAL', 'AVAILABLE', 4, '555-0202', 'nurse.j@medical.com', '2026-01-05 15:45:46', '2026-01-14 08:09:28'),
(7, 'Dr. ilyas', 'MEDICAL', 'AVAILABLE', 3, '555-0203', 'dr.wilson@medical.com', '2026-01-05 15:45:46', '2026-01-14 08:09:33'),
(8, 'Parveen', 'MEDICAL', 'AVAILABLE', 4, '555-0204', 'paramedic@medical.com', '2026-01-05 15:45:46', '2026-01-14 08:09:43'),
(9, 'Sohaib', 'SUPPLY', 'AVAILABLE', 3, '555-0301', 'alex@supply.com', '2026-01-05 15:45:46', '2026-01-14 08:09:51'),
(10, 'Saad', 'SUPPLY', 'AVAILABLE', 4, '555-0302', 'lisa@supply.com', '2026-01-05 15:45:46', '2026-01-14 04:31:44'),
(11, 'Tom', 'SUPPLY', 'AVAILABLE', 2, '555-0303', 'tom@supply.com', '2026-01-05 15:45:46', '2026-01-14 08:09:59'),
(12, 'Engineer Saif', 'ENGINEER', 'AVAILABLE', 5, '555-0401', 'carter@engineer.com', '2026-01-05 15:45:46', '2026-01-14 04:31:44'),
(13, 'Tausif', 'ENGINEER', 'AVAILABLE', 3, '555-0402', 'davis@engineer.com', '2026-01-05 15:45:46', '2026-01-14 08:10:21'),
(14, 'Builder khan', 'ENGINEER', 'AVAILABLE', 4, '555-0403', 'evans@engineer.com', '2026-01-05 15:45:46', '2026-01-14 08:10:30'),
(15, 'ALI BHAI', 'RESCUE', 'AVAILABLE', 2, '0300245887', 'ali@gmail.com', '2026-01-10 17:27:48', '2026-01-13 14:17:31');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assignments`
--
ALTER TABLE `assignments`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_disaster_volunteer` (`disaster_id`,`volunteer_id`),
  ADD KEY `idx_disaster_id` (`disaster_id`),
  ADD KEY `idx_volunteer_id` (`volunteer_id`),
  ADD KEY `idx_status` (`status`);

--
-- Indexes for table `disasters`
--
ALTER TABLE `disasters`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_status` (`status`),
  ADD KEY `idx_type` (`type`),
  ADD KEY `idx_severity` (`severity`);

--
-- Indexes for table `simulation_logs`
--
ALTER TABLE `simulation_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `disaster_id` (`disaster_id`),
  ADD KEY `volunteer_id` (`volunteer_id`),
  ADD KEY `idx_log_type` (`log_type`),
  ADD KEY `idx_created_at` (`created_at`);

--
-- Indexes for table `threshold_rules`
--
ALTER TABLE `threshold_rules`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_disaster_severity` (`disaster_type`,`severity`),
  ADD KEY `idx_disaster_type` (`disaster_type`),
  ADD KEY `idx_severity` (`severity`);

--
-- Indexes for table `volunteers`
--
ALTER TABLE `volunteers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_category` (`category`),
  ADD KEY `idx_status` (`status`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assignments`
--
ALTER TABLE `assignments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `disasters`
--
ALTER TABLE `disasters`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `simulation_logs`
--
ALTER TABLE `simulation_logs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `threshold_rules`
--
ALTER TABLE `threshold_rules`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `volunteers`
--
ALTER TABLE `volunteers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assignments`
--
ALTER TABLE `assignments`
  ADD CONSTRAINT `assignments_ibfk_1` FOREIGN KEY (`disaster_id`) REFERENCES `disasters` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `assignments_ibfk_2` FOREIGN KEY (`volunteer_id`) REFERENCES `volunteers` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `simulation_logs`
--
ALTER TABLE `simulation_logs`
  ADD CONSTRAINT `simulation_logs_ibfk_1` FOREIGN KEY (`disaster_id`) REFERENCES `disasters` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `simulation_logs_ibfk_2` FOREIGN KEY (`volunteer_id`) REFERENCES `volunteers` (`id`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
