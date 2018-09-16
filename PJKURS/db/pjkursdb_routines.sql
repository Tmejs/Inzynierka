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
-- Temporary view structure for view `course_sub_categorries`
--

DROP TABLE IF EXISTS `course_sub_categorries`;
/*!50001 DROP VIEW IF EXISTS `course_sub_categorries`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `course_sub_categorries` AS SELECT 
 1 AS `ID`,
 1 AS `name`,
 1 AS `description`,
 1 AS `category_id`,
 1 AS `course_id`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `dostepne_kursy_v`
--

DROP TABLE IF EXISTS `dostepne_kursy_v`;
/*!50001 DROP VIEW IF EXISTS `dostepne_kursy_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `dostepne_kursy_v` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `description`,
 1 AS `statusId`,
 1 AS `paricipants`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `kursy_v`
--

DROP TABLE IF EXISTS `kursy_v`;
/*!50001 DROP VIEW IF EXISTS `kursy_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `kursy_v` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `description`,
 1 AS `statusId`,
 1 AS `paricipants`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `kursy_arch_v`
--

DROP TABLE IF EXISTS `kursy_arch_v`;
/*!50001 DROP VIEW IF EXISTS `kursy_arch_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `kursy_arch_v` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `description`,
 1 AS `statusId`,
 1 AS `statusName`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `moje_kursy_v`
--

DROP TABLE IF EXISTS `moje_kursy_v`;
/*!50001 DROP VIEW IF EXISTS `moje_kursy_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `moje_kursy_v` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `description`,
 1 AS `isPaid`,
 1 AS `username`,
 1 AS `paricipants`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `course_sub_categorries`
--

/*!50001 DROP VIEW IF EXISTS `course_sub_categorries`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `course_sub_categorries` AS select `sc`.`ID` AS `ID`,`sc`.`name` AS `name`,`sc`.`description` AS `description`,`sc`.`category_id` AS `category_id`,`csc`.`course_id` AS `course_id` from (`sub_categorries` `sc` left join `courses_sub_categories` `csc` on((`sc`.`ID` = `csc`.`sub_category_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `dostepne_kursy_v`
--

/*!50001 DROP VIEW IF EXISTS `dostepne_kursy_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `dostepne_kursy_v` AS select `cs`.`id` AS `id`,`cs`.`name` AS `name`,`cs`.`description` AS `description`,`cs`.`statusId` AS `statusId`,(select count(0) from `appusers_courses` where (`appusers_courses`.`course_id` = `cs`.`id`)) AS `paricipants` from (`courses` `cs` left join `courses_statuses` `cst` on((`cs`.`statusId` = `cst`.`id`))) where (`cs`.`statusId` <> 1) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `kursy_v`
--

/*!50001 DROP VIEW IF EXISTS `kursy_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `kursy_v` AS select `cs`.`id` AS `id`,`cs`.`name` AS `name`,`cs`.`description` AS `description`,`cs`.`statusId` AS `statusId`,(select count(0) from `appusers_courses` where (`appusers_courses`.`course_id` = `cs`.`id`)) AS `paricipants` from (`courses` `cs` left join `courses_statuses` `cst` on((`cs`.`statusId` = `cst`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `kursy_arch_v`
--

/*!50001 DROP VIEW IF EXISTS `kursy_arch_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `kursy_arch_v` AS select `cs`.`id` AS `id`,`cs`.`name` AS `name`,`cs`.`description` AS `description`,`cst`.`id` AS `statusId`,`cst`.`name` AS `statusName` from (`courses` `cs` left join `courses_statuses` `cst` on((`cst`.`id` = `cs`.`statusId`))) where (`cs`.`statusId` = 1) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `moje_kursy_v`
--

/*!50001 DROP VIEW IF EXISTS `moje_kursy_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `moje_kursy_v` AS select `cs`.`id` AS `id`,`cs`.`name` AS `name`,`cs`.`description` AS `description`,if(`uc`.`isPaid`,'true','false') AS `isPaid`,`apu`.`email` AS `username`,(select count(0) from `appusers_courses` where (`appusers_courses`.`course_id` = `cs`.`id`)) AS `paricipants` from ((`courses` `cs` left join `appusers_courses` `uc` on((`cs`.`id` = `uc`.`course_id`))) left join `appusers` `apu` on((`apu`.`id` = `uc`.`user_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Dumping events for database 'pjkursdb'
--

--
-- Dumping routines for database 'pjkursdb'
--
/*!50003 DROP FUNCTION IF EXISTS `czy_zapisany` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `czy_zapisany`(P_EMAIL VARCHAR(40), P_COURSE_ID INT) RETURNS tinyint(1)
BEGIN
   DECLARE v_result Boolean;
    declare v_user_id integer;
    declare v_exist_count integer;
    select id into v_user_id 
    from pjkursdb.appusers
    where email=p_EMAIl;
    
    SELECT COUNT(*) INTO V_EXIST_COUNT
		FROM appusers_courses
        WHERE course_id = p_course_id
        and user_id = v_user_id;
        
	IF V_EXIST_COUNT!=1 THEN Set v_result=false;
    ELSE 
		set v_result=true;
    end if;
    return v_result;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `czy_zarejestrowany_email` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `czy_zarejestrowany_email`(P_EMAIL VARCHAR(40)) RETURNS tinyint(1)
BEGIN
    DECLARE V_EXIST_COUNT INTEGER;
    
    SELECT COUNT(*) INTO V_EXIST_COUNT
		FROM appusers
        WHERE EMAIL=P_EMAIL;
    
    RETURN V_EXIST_COUNT>0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `zaloguj_usera` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `zaloguj_usera`(P_EMAIL VARCHAR(40), P_HASLO VARCHAR(40)) RETURNS tinyint(1)
BEGIN
    DECLARE V_EXIST_COUNT INTEGER;
    
    SELECT COUNT(*) INTO V_EXIST_COUNT
		FROM appusers a
        where a.email=p_email
        and a.haslo = P_HASLO;
        
    RETURN V_EXIST_COUNT>0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `zapisz_do_kursu` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `zapisz_do_kursu`(P_EMAIL VARCHAR(40), P_COURSE_ID INT) RETURNS tinyint(1)
BEGIN
   DECLARE V_EXIST_COUNT INTEGER;
   DECLARE V_USER_ID INTEGER;
   DECLARE v_result Boolean;
    
    select id into v_user_id 
    from pjkursdb.appusers
    where email=p_EMAIl;
    
    SELECT COUNT(*) INTO V_EXIST_COUNT
		FROM appusers_courses
        WHERE course_id = p_course_id
        and user_id = v_user_id;
        
	IF V_EXIST_COUNT>0 THEN Set v_result=false;
    ELSE 
		insert into appusers_courses(user_id,course_id)
        values(v_user_id,p_course_id);
        
        set v_result = true;
    end if;
    return v_result;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `zarejestruj_usera` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `zarejestruj_usera`(P_EMAIL VARCHAR(40), P_HASLO VARCHAR(40)) RETURNS tinyint(1)
BEGIN
    DECLARE V_EXIST_COUNT INTEGER;
    insert into appusers (email,haslo) values (P_EMAIL,P_HASLO);
    RETURN true;
END ;;
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

-- Dump completed on 2018-09-16 18:05:05
