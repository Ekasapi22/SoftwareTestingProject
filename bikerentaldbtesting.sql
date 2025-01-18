-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 18, 2025 at 01:35 PM
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
-- Database: `bikerentaldbtesting`
--

-- --------------------------------------------------------

--
-- Table structure for table `bikes`
--

CREATE TABLE `bikes` (
  `bike_id` int(11) NOT NULL,
  `station_id` int(11) NOT NULL,
  `bike_model` varchar(255) NOT NULL,
  `bike_type` enum('CITY','SPORT','MOUNTAIN','CARGO','E_BIKE') NOT NULL,
  `bike_price` double NOT NULL,
  `bike_status` enum('AVAILABLE','RENTED','MAINTENANCE','CHARGING') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bikes`
--

INSERT INTO `bikes` (`bike_id`, `station_id`, `bike_model`, `bike_type`, `bike_price`, `bike_status`) VALUES
(1, 2, 'Scott Scale', 'MOUNTAIN', 25, 'RENTED'),
(2, 4, 'Gazelle Ultimate C8+', 'E_BIKE', 29, 'AVAILABLE'),
(3, 5, 'Electra Townie Go!', 'E_BIKE', 25, 'AVAILABLE'),
(4, 2, 'Surly Big Dummy', 'CARGO', 27, 'AVAILABLE'),
(5, 2, 'Schwinn Wayfarer', 'E_BIKE', 24, 'AVAILABLE'),
(6, 4, 'Riese & Müller Load', 'E_BIKE', 26, 'RENTED'),
(7, 1, 'Specialized Sirrus', 'SPORT', 26, 'MAINTENANCE'),
(8, 3, 'Trek FX 3', 'CITY', 23, 'RENTED'),
(10, 3, 'Cannondale Trail Neo', 'E_BIKE', 24, 'CHARGING');

-- --------------------------------------------------------

--
-- Table structure for table `bike_rentals`
--

CREATE TABLE `bike_rentals` (
  `rental_id` int(11) NOT NULL,
  `bike_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `start_station_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `rent_price` decimal(10,2) DEFAULT NULL,
  `is_returned` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bike_rentals`
--

INSERT INTO `bike_rentals` (`rental_id`, `bike_id`, `user_id`, `start_station_id`, `start_date`, `return_date`, `rent_price`, `is_returned`) VALUES
(13, 1, 2, 2, '2024-02-28', '2024-02-29', 50.00, 1),
(14, 8, 2, 3, '2024-02-29', '2024-02-29', 23.00, 1),
(18, 6, 1, 4, '2024-03-01', '2024-03-02', 52.00, 0);

-- --------------------------------------------------------

--
-- Table structure for table `stations`
--

CREATE TABLE `stations` (
  `station_id` int(11) NOT NULL,
  `station_address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stations`
--

INSERT INTO `stations` (`station_id`, `station_address`) VALUES
(1, 'Ginnheimer Landstr.14, 60430 Frankfurt'),
(2, 'Schwarzwaldstr. 4A, 65779 Kelkheim'),
(3, 'Debusweg 36, Königstein im Taunus'),
(4, 'Praunheimer 49, 60488 Frankfurt'),
(5, 'Rebstockbad, 60486 Frankfurt');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_phone` varchar(255) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `role` enum('user','admin') NOT NULL DEFAULT 'user',
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `user_phone`, `user_email`, `role`, `password`) VALUES
(1, 'Missy Cooper', '01793421125', 'Missy-C@gmail.com', 'user', '635241'),
(2, 'Lilium', '015247859696', 'amir95@gmail.com', 'user', '654321'),
(4, 'LilacHunter1998', '0711112233', 'RosaKeyka@gmail.com', 'user', '199543200'),
(5, 'lawana', '01789621453', 'lawana-detkova@gmail.com', 'admin', '968574'),
(8, 'Miley Twigg', '017985412853', 'M-twigg32@gmail.com', 'user', '741852'),
(10, 'Luka Pour', '01789589641', 'Luka-Pr@gmail.de', 'user', 'motorstormtest'),
(11, 'Monika DreamCaster99', '0623331471', 'MQ20-albay@gmail.de', 'user', 'jackalhunter3341');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bikes`
--
ALTER TABLE `bikes`
  ADD PRIMARY KEY (`bike_id`),
  ADD KEY `fk_station_id` (`station_id`);

--
-- Indexes for table `bike_rentals`
--
ALTER TABLE `bike_rentals`
  ADD PRIMARY KEY (`rental_id`) USING BTREE,
  ADD KEY `fk_user_id` (`user_id`),
  ADD KEY `fk_start_location` (`start_station_id`),
  ADD KEY `fk_bike_id` (`bike_id`);

--
-- Indexes for table `stations`
--
ALTER TABLE `stations`
  ADD PRIMARY KEY (`station_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_email` (`user_email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bikes`
--
ALTER TABLE `bikes`
  MODIFY `bike_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `bike_rentals`
--
ALTER TABLE `bike_rentals`
  MODIFY `rental_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `stations`
--
ALTER TABLE `stations`
  MODIFY `station_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=395;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bikes`
--
ALTER TABLE `bikes`
  ADD CONSTRAINT `fk_station_id` FOREIGN KEY (`station_id`) REFERENCES `stations` (`station_id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `bike_rentals`
--
ALTER TABLE `bike_rentals`
  ADD CONSTRAINT `fk_bike_id` FOREIGN KEY (`bike_id`) REFERENCES `bikes` (`bike_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_start_location` FOREIGN KEY (`start_station_id`) REFERENCES `stations` (`station_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
