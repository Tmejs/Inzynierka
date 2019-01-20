-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: pjkursdb
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appusers`
--

DROP TABLE IF EXISTS `appusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appusers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) DEFAULT NULL,
  `haslo` varchar(30) DEFAULT NULL,
  `data_dodania` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appusers`
--

LOCK TABLES `appusers` WRITE;
/*!40000 ALTER TABLE `appusers` DISABLE KEYS */;
INSERT INTO `appusers` VALUES (1,'mateusz.rzad@gmail.com','haslo','2018-01-20'),(2,'a@a.a','asdfg','2018-01-20'),(3,'tomek@tomek.pl','haslo','2018-01-20'),(4,'a@a.pl','lkjhg','2018-01-20'),(5,'mati@mati.pl','haslo1234','2018-01-20'),(6,'aa@aa.pl','12345','2018-01-20'),(7,'ania@ania.pl','ania1234',NULL),(8,'mati@mati2.pl','mati1234',NULL),(9,'WW@WW.WW','WWWWW',NULL),(10,'aa@wp.pl','haslo',NULL),(11,'zuzia@z.pl','zuzia',NULL),(12,'mat@mat.mat','mat12',NULL),(13,'tk@tk.pl','tomek',NULL),(14,'szymon@szymon.pl','szymon',NULL),(15,'mattt@wp.pl','mattt',NULL);
/*!40000 ALTER TABLE `appusers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-16 18:05:04
