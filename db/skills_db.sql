-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 21, 2019 at 08:36 PM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `skills_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `counties`
--

CREATE TABLE `counties` (
  `id` int(11) NOT NULL,
  `code` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `country_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `counties`
--

INSERT INTO `counties` (`id`, `code`, `name`, `country_name`) VALUES
(1, 1, 'Mombasa', ''),
(2, 47, 'Nairobi', 'Kenya'),
(3, 23, 'Kericho', ''),
(5, 34, 'Turkana', ''),
(7, 56, 'Machakos', ''),
(8, 2, 'Kilifi', 'Kenya'),
(9, 37, 'Uasin Gishu', 'Kenya'),
(10, 202, 'Examlpe Ua', 'Uganda'),
(11, 787, 'tzn', 'Tanzanita'),
(13, 45, 'Homa Bay', 'Kenya');

-- --------------------------------------------------------

--
-- Table structure for table `countries`
--

CREATE TABLE `countries` (
  `id` int(11) NOT NULL,
  `code` int(11) NOT NULL,
  `country` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `countries`
--

INSERT INTO `countries` (`id`, `code`, `country`) VALUES
(1, 254, 'Kenya'),
(2, 256, 'Uganda'),
(3, 253, 'Tanzanita'),
(5, 354, 'Zambia');

-- --------------------------------------------------------

--
-- Table structure for table `estates`
--

CREATE TABLE `estates` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `county` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `estates`
--

INSERT INTO `estates` (`id`, `name`, `county`) VALUES
(1, 'Mountain View', ''),
(2, 'Karen', 'Nairobi'),
(3, 'Westlands', 'Nairobi'),
(4, 'Fahari garden estate', ''),
(7, 'Elgon View', 'Uasin Gishu'),
(8, 'Runda', 'Nairobi'),
(9, 'Entebbe', 'Examlpe Ua');

-- --------------------------------------------------------

--
-- Table structure for table `expert`
--

CREATE TABLE `expert` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `expert`
--

INSERT INTO `expert` (`id`, `name`) VALUES
(1, 'Cooking'),
(2, 'Database Administation'),
(3, 'MySQL');

-- --------------------------------------------------------

--
-- Table structure for table `skills`
--

CREATE TABLE `skills` (
  `id` int(11) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `county` varchar(160) NOT NULL,
  `estate` varchar(160) NOT NULL,
  `neigh` varchar(160) NOT NULL,
  `skills` varchar(50) NOT NULL,
  `idnumber` int(12) NOT NULL,
  `image` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `skills`
--

INSERT INTO `skills` (`id`, `user_id`, `country`, `county`, `estate`, `neigh`, `skills`, `idnumber`, `image`) VALUES
(3, '', 'Kenya', 'Mombasa', 'Karen', 'yyy', 'Cooking', 64646, 'http://192.168.43.113/skill_rest_api/uploads/.jpg'),
(5, '15', 'Kenya', 'Mombasa', 'Karen', 'jjjssj', 'Cooking', 1111111, 'http://192.168.43.113/skill_rest_api/uploads/1111111.jpg'),
(6, '15', 'Kenya', 'Mombasa', 'Karen', 'nnnssnn', 'Cooking', 46646644, 'http://192.168.43.113/skill_rest_api/uploads/46646644.jpg'),
(8, '15', 'Kenya', 'Mombasa', 'Karen', 'dsss', 'Cooking', 444444, 'http://192.168.43.113/skill_rest_api/uploads/444444.jpg'),
(12, '15', 'Kenya', 'Mombasa', 'Karen', 'dsss', 'Cooking', 123456, 'http://192.168.43.113/skill_rest_api/uploads/123456.jpg'),
(13, '', 'Kenya', 'Mombasa', 'Karen', 'nsjs', 'Cooking', 646464646, 'http://192.168.43.113/skill_rest_api/uploads/646464646.jpg'),
(14, '15', 'Kenya', 'Mombasa', 'Karen', 'demo', 'Database Administation', 66464, 'http://192.168.43.113/skill_rest_api/uploads/66464.jpg'),
(15, '15', 'Kenya', 'Nairobi', 'Mountain View', 'demo', 'Android Dev', 64466446, 'http://192.168.43.113/skill_rest_api/uploads/64466446.jpg'),
(16, '28', 'Kenya', 'Nairobi', 'Karen', 'Langata', 'Cooking', 2147483647, 'http://192.168.43.113/skill_rest_api/uploads/3131131313.jpg'),
(19, '29', 'Kenya', 'Mombasa', 'Fahari garden estate', 'kinoo', 'Database Administation', 11111110, 'http://192.168.43.113/skill_rest_api/uploads/11111110.jpg'),
(28, '30', 'Kenya', 'Machakos', 'Karen', 'Rongai', 'MySQL', 646466466, 'http://192.168.43.113/skill_rest_api/uploads/646466466.jpg'),
(29, '30', 'Kenya', 'Mombasa', 'Karen', 'ssjsjsjjw', 'Cooking', 6446464, 'http://192.168.43.113/skill_rest_api/uploads/6446464.jpg'),
(30, '15', 'Kenya', 'Mombasa', 'Fahari garden estate', 'iiiqi', 'Cooking', 646464, 'http://192.168.43.113/skill_rest_api/uploads/646464.jpg'),
(31, '32', 'Kenya', 'Nairobi', 'Karen', 'demo', 'Cooking', 1661611616, 'http://192.168.43.113/skill_rest_api/uploads/1661611616.jpg'),
(32, '32', 'Kenya', 'Nairobi', 'Karen', 'hzjsjs', 'Cooking', 64664664, 'http://192.168.43.113/skill_rest_api/uploads/64664664.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `gender` varchar(200) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(200) NOT NULL,
  `role` enum('0','1') NOT NULL DEFAULT '0',
  `token` varchar(250) NOT NULL DEFAULT '0',
  `password` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `firstname`, `lastname`, `username`, `gender`, `phone`, `email`, `role`, `token`, `password`) VALUES
(31, 'hhhy', 'ggv', 'hhhh', 'Male', '+254566656666', 'vgghhhg', '0', '0', '06c623fa1d06a721ca1bfb5ce04c1718'),
(32, 'hhhy', 'ggv', 'evans', 'Male', '+254703587475', 'vggh@gmail.com', '0', '29549b8791c11d20e7687837a374df84451c1357af386b789871333561ada0aa', '81dc9bdb52d04dc20036dbd8313ed055');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `counties`
--
ALTER TABLE `counties`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `countries`
--
ALTER TABLE `countries`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `estates`
--
ALTER TABLE `estates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `expert`
--
ALTER TABLE `expert`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `skills`
--
ALTER TABLE `skills`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idnumber` (`idnumber`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `counties`
--
ALTER TABLE `counties`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `countries`
--
ALTER TABLE `countries`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `estates`
--
ALTER TABLE `estates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `expert`
--
ALTER TABLE `expert`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `skills`
--
ALTER TABLE `skills`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
