-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 09 mars 2020 à 05:20
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `java_project`
--

-- --------------------------------------------------------

--
-- Structure de la table `address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
  `idaddress` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(30) COLLATE utf8_general_mysql500_ci NOT NULL,
  `street` varchar(100) COLLATE utf8_general_mysql500_ci NOT NULL,
  `town` varchar(60) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  `postCode` int(11) NOT NULL,
  `region` varchar(60) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  `pays` varchar(60) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  PRIMARY KEY (`idaddress`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Déchargement des données de la table `address`
--

INSERT INTO `address` (`idaddress`, `number`, `street`, `town`, `postCode`, `region`, `pays`) VALUES
(13, '45', 'rue des navets', 'Croussi sur mer', 44444, 'France', 'region'),
(12, '44', 'rue des choux', 'Lille', 44444, 'France', 'region'),
(23, '556', 'Rue des avenues', 'City', 44444, 'region', 'Country'),
(15, '1AA', 'Constitution Hill', 'Londre', 44444, 'Angleterre', 'region'),
(18, '666', 'rue des poires', 'Marchou St Gege', 44444, 'region', 'region');

-- --------------------------------------------------------

--
-- Structure de la table `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `idperson` int(11) NOT NULL AUTO_INCREMENT,
  `lastname` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `firstname` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `nickname` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `phone_number` varchar(15) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  `idaddress` int(11) NOT NULL,
  `email_address` varchar(150) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  PRIMARY KEY (`idperson`),
  KEY `idaddress` (`idaddress`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Déchargement des données de la table `person`
--

INSERT INTO `person` (`idperson`, `lastname`, `firstname`, `nickname`, `phone_number`, `idaddress`, `email_address`, `birth_date`) VALUES
(1, 'Bob', 'Bobby', 'BobbyBob', '0606060606', 12, 'e@mail.com', '2012-03-15'),
(2, 'Dupont', 'Jean', 'Jiji', '0695475746', 13, 'email@mail.com', '2005-03-11'),
(3, '2', 'Elisabeth', 'La reine', '+443031237300', 15, 'mail@e.com', '1926-04-21'),
(5, 'Sauran', 'Radouin', 'zetta', '5755444774', 18, 'email@email.email', '1937-04-08'),
(10, 'LastName', 'FirstName', 'NickName', '666', 23, 'email.email@email.email', '1876-04-07');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
