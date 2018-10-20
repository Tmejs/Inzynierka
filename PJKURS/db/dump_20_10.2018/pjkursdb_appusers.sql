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
  `password` varchar(30) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `surdname` varchar(45) DEFAULT NULL,
  `bitrth_date` date DEFAULT NULL,
  `contact_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appusers`
--

LOCK TABLES `appusers` WRITE;
/*!40000 ALTER TABLE `appusers` DISABLE KEYS */;
INSERT INTO `appusers` VALUES (1,'mateusz.rzad@gmail.com','haslo','2018-01-20',NULL,NULL,NULL,NULL),(2,'a@a.a','asdfg','2018-01-20',NULL,NULL,NULL,NULL),(3,'tomek@tomek.pl','haslo','2018-01-20',NULL,NULL,NULL,NULL),(4,'a@a.pl','lkjhg','2018-01-20',NULL,NULL,NULL,NULL),(5,'mati@mati.pl','haslo1234','2018-01-20',NULL,NULL,NULL,NULL),(6,'aa@aa.pl','12345','2018-01-20',NULL,NULL,NULL,NULL),(7,'ania@ania.pl','ania1234',NULL,NULL,NULL,NULL,NULL),(8,'mati@mati2.pl','mati1234',NULL,NULL,NULL,NULL,NULL),(9,'WW@WW.WW','WWWWW',NULL,NULL,NULL,NULL,NULL),(10,'aa@wp.pl','haslo',NULL,NULL,NULL,NULL,NULL),(11,'zuzia@z.pl','zuzia',NULL,NULL,NULL,NULL,NULL),(12,'mat@mat.mat','mat12',NULL,NULL,NULL,NULL,NULL),(13,'tk@tk.pl','tomek',NULL,NULL,NULL,NULL,NULL),(14,'szymon@szymon.pl','szymon',NULL,NULL,NULL,NULL,NULL),(15,'mattt@wp.pl','mattt',NULL,NULL,NULL,NULL,NULL),(17,'email@email.com','email',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `appusers` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `pjkursdb`.`appusers_BEFORE_INSERT` BEFORE INSERT ON `appusers` FOR EACH ROW
BEGIN
	set @create_date := curdate();
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-20 13:39:04
