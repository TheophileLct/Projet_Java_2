-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  ven. 13 mars 2020 à 13:27
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
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Déchargement des données de la table `address`
--

INSERT INTO `address` (`idaddress`, `number`, `street`, `town`, `postCode`, `region`, `pays`) VALUES
(13, '45', 'rue des navets', 'Croussi sur mer', 44444, 'France', 'region'),
(12, '44', 'rue des choux', 'Lille', 44444, 'France', 'region'),
(23, 'efee', 'fefe', 'fefef', 44444, 'region', 'efefef'),
(15, '1AA', 'Constitution Hill', 'Londre', 44444, 'Angleterre', 'region'),
(18, '666', 'rue des poires', 'Marchou St Gege', 44444, 'region', 'region'),
(24, 'efee', 'fefe', 'fefef', 44444, 'region', 'efefef'),
(25, '1', 'place des résident', 'tortimerville', 44444, 'region', 'Ile deserte'),
(26, '2', 'boulevard du chinchard', 'Voldia', 44444, 'region', 'ile déserte'),
(27, '3', 'boulevard du Machaon', 'Enghein ', 44444, 'region', 'Ile deserte'),
(28, '5', 'Place des résidents', 'Monarque', 44444, 'region', 'Ile déserte');

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
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Déchargement des données de la table `person`
--

INSERT INTO `person` (`idperson`, `lastname`, `firstname`, `nickname`, `phone_number`, `idaddress`, `email_address`, `birth_date`) VALUES
(15, 'Laglisse', 'Keke', 'Jack', '0658954256', 28, 'laglisse.keke@animalcrossing.com', '2002-12-10'),
(14, 'Blanche', 'Whitney', 'Louve', '0608854521', 27, 'blanche.whitney@animalcrossing.com', '2020-09-17'),
(12, 'Nook', 'Tom', 'Tommy', '0604425689', 25, 'tom.nook@animalcrossing.com', '2001-05-30'),
(13, 'Marie', 'Isabelle', 'Mary', '0602055884', 26, 'MarieIsabelle@animalcrossing.com', '2001-12-20');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
