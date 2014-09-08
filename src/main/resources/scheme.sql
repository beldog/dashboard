-- MySQL dump 10.13  Distrib 5.5.38, for debian-linux-gnu (x86_64)
--
-- Host: 192.168.124.96    Database: ukint_management
-- ------------------------------------------------------
-- Server version	5.0.87sp1-enterprise-gpl

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
-- Not dumping tablespaces as no INFORMATION_SCHEMA.FILES table on this server
--

-- 
-- IMPORTANT
-- --------- 
-- Below views uses a custopn store function called: vfget(), that aims to get the value given a key from a string cointaining key=values pairs separated by &
-- ie: param1=value1&param2=value2&
--
--

--
-- Table structure for table `pm_project_event`
--

DROP TABLE IF EXISTS `pm_project_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_project_event` (
  `country` enum('at','ch','de','in','kw','ru','sg','uk','za') default 'uk',
  `project_type` enum('MIP','Delivery','Maintenance') default 'Maintenance',
  `project_id` varchar(100) NOT NULL default '',
  `date` date NOT NULL default '0000-00-00',
  `type` enum('delay','launch','milestone','status','baseline','start','info') default NULL,
  `event` varchar(100) default '',
  `impact` tinyint(4) default '0',
  `reason` text,
  `timelines` varchar(256) default '',
  `autotimestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `event_id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`event_id`)
) ENGINE=MyISAM AUTO_INCREMENT=327 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_project_event`
--

LOCK TABLES `pm_project_event` WRITE;
/*!40000 ALTER TABLE `pm_project_event` DISABLE KEYS */;
INSERT INTO `pm_project_event` VALUES ('ru','Maintenance','517474','2012-11-08','start','AS',0,'New ticket','AS=2012-11-15&Dev=&QA=&UAT=&Launch=','2013-01-22 18:13:37',1),('ru','Maintenance','517474','2012-11-21','delay','Operator implementation delayed',3,'Changes on opererator side are not ready','AS=2012-11-30&Dev=&QA=&UAT=&Launch=','2013-01-22 18:13:37',2),('ru','Maintenance','517474','2012-12-04','start','Dev',0,'Starting Development','AS=2012-11-30&Dev=&QA=&UAT=&Launch=','2013-01-27 17:14:53',3),('ru','Maintenance','517474','2012-12-05','baseline','Baseline',0,'','AS=2012-12-01&Dev=2012-12-14&QA=2012-12-17&UAT=2012-12-19&Launch=2012-12-04','2014-07-20 12:30:03',4),('ru','Maintenance','517474','2012-12-18','delay','Team capacity and new requirement',3,'Support schedule changed; Resource at 50% for 1 week; Included general UA filter with exceptions on MI campaigns','AS=2012-12-01&Dev=2012-12-21&QA=2012-12-27&UAT=2013-01-02&Launch=2013-01-03','2013-01-22 18:13:37',7),('ru','Maintenance','517474','2013-01-03','delay','Delay in development',6,'Testing delayed and xmas capacity impact','AS=2012-12-01&Dev=2013-01-04&QA=2013-01-08&UAT=2013-01-11&Launch=2013-01-14','2013-01-27 19:46:03',18),('ru','Maintenance','517474','2013-01-08','start','QA',0,'Starting QA','AS=2012-12-01&Dev=2013-01-04&QA=2013-01-08&UAT=2013-01-11&Launch=2013-01-14','2013-01-27 19:46:06',19),('ru','Maintenance','517474','2013-01-09','start','UAT',0,'Starting UAT','AS=2012-12-01&Dev=2012-12-21&QA=2013-01-08&UAT=2013-01-11&Launch=2013-01-14','2013-01-27 19:46:07',20),('ru','Maintenance','517474','2012-12-20','delay','Delay in development',6,'Testing delayed and xmas capacity impact','AS=2012-12-01&Dev=2013-01-04&QA=2013-01-08&UAT=2013-01-11&Launch=2013-01-14','2013-01-27 19:46:11',22),('ru','Maintenance','517474','2013-01-14','launch','Launch',0,'Services launched','AS=2012-12-01&Dev=2013-01-04&QA=2013-01-08&UAT=2013-01-11&Launch=2013-01-14','2014-09-02 21:05:42',297),('ch','MIP','528976','2013-01-10','start','PE2E',0,'','PE2E=2013-01-11&E2E=2013-01-18&UAT=2013-02-01&Launch=2013-02-04','2013-04-02 02:36:28',116),('ch','MIP','528976','2013-01-10','baseline','Baseline',0,'','PE2E=2013-01-11&E2E=2013-01-18&UAT=2013-02-01&Launch=2013-02-04','2013-04-02 02:36:28',117),('ch','MIP','528976','2013-01-14','start','E2E',0,'','PE2E=2013-01-11&E2E=2013-01-18&UAT=2013-02-01&Launch=2013-02-04','2014-03-30 21:14:18',118),('ch','MIP','528976','2013-01-30','delay','E2E',5,'Eco setup delayed','PE2E=2013-01-11&E2E=2013-02-01&UAT=2013-02-15&Launch=','2013-04-02 04:09:51',119),('ch','MIP','528976','2013-02-05','delay','E2E',2,'Eco fixing issues in the subscription flow','PE2E=2013-01-11&E2E=2013-02-05&UAT=2013-02-19&Launch=','2013-04-02 04:09:51',120),('ch','MIP','528976','2013-02-06','start','UAT',0,'','PE2E=2013-01-11&E2E=2013-02-05&UAT=2013-02-19&Launch=','2013-04-02 04:09:51',121),('ch','MIP','528976','2013-02-19','delay','UAT',3,'QA found issues, reported to Eco team','PE2E=2013-01-11&E2E=2013-02-05&UAT=2013-02-22&Launch=','2013-04-02 04:09:51',122),('ch','MIP','528976','2013-02-27','delay','UAT',3,'Eco team fixing issues raised in QA','PE2E=2013-01-11&E2E=2013-02-05&UAT=2013-03-13&Launch=','2013-04-02 04:09:51',123),('ch','MIP','528976','2013-03-21','delay','UAT',12,'Music team will review issues and work next week','PE2E=2013-01-11&E2E=2013-02-05&UAT=2013-03-29&Launch=','2013-04-02 04:12:11',124),('ch','MIP','527893','2013-03-04','start','PE2E',0,'','PE2E=2013-03-08&E2E=2013-03-22&UAT=2013-04-17&Launch=2013-04-18','2013-04-02 05:00:03',125);
/*!40000 ALTER TABLE `pm_project_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_project_id_alias`
--

DROP TABLE IF EXISTS `pm_project_id_alias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_project_id_alias` (
  `project_id` varchar(100) NOT NULL default '',
  `name` text,
  PRIMARY KEY  (`project_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_project_id_alias`
--

LOCK TABLES `pm_project_id_alias` WRITE;
/*!40000 ALTER TABLE `pm_project_id_alias` DISABLE KEYS */;
INSERT INTO `pm_project_id_alias` VALUES ('517474','RU Update MTS subscription'),('528976','CH Playme');
/*!40000 ALTER TABLE `pm_project_id_alias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `ukint_project_amount_launches`
--

DROP TABLE IF EXISTS `ukint_project_amount_launches`;
/*!50001 DROP VIEW IF EXISTS `ukint_project_amount_launches`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `ukint_project_amount_launches` (
  `Year` tinyint NOT NULL,
  `Country` tinyint NOT NULL,
  `Project type` tinyint NOT NULL,
  `Total launched` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `ukint_project_amount_launches_by_month`
--

DROP TABLE IF EXISTS `ukint_project_amount_launches_by_month`;
/*!50001 DROP VIEW IF EXISTS `ukint_project_amount_launches_by_month`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `ukint_project_amount_launches_by_month` (
  `Month` tinyint NOT NULL,
  `Country` tinyint NOT NULL,
  `Project type` tinyint NOT NULL,
  `Total launched` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `ukint_project_delay_reports`
--

DROP TABLE IF EXISTS `ukint_project_delay_reports`;
/*!50001 DROP VIEW IF EXISTS `ukint_project_delay_reports`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `ukint_project_delay_reports` (
  `Country` tinyint NOT NULL,
  `Month` tinyint NOT NULL,
  `Ticket` tinyint NOT NULL,
  `Project type` tinyint NOT NULL,
  `Launched` tinyint NOT NULL,
  `Real launch date` tinyint NOT NULL,
  `Planned launch date` tinyint NOT NULL,
  `Accumulated launch delay` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `ukint_project_detailed_delay_reports`
--

DROP TABLE IF EXISTS `ukint_project_detailed_delay_reports`;
/*!50001 DROP VIEW IF EXISTS `ukint_project_detailed_delay_reports`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `ukint_project_detailed_delay_reports` (
  `Country` tinyint NOT NULL,
  `Month` tinyint NOT NULL,
  `Ticket` tinyint NOT NULL,
  `Project type` tinyint NOT NULL,
  `Launched` tinyint NOT NULL,
  `Real launch date` tinyint NOT NULL,
  `Planned launch date` tinyint NOT NULL,
  `Total launch delay` tinyint NOT NULL,
  `Date delay` tinyint NOT NULL,
  `Amount delay` tinyint NOT NULL,
  `Delay reason` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `ukint_project_timelines_reports`
--

DROP TABLE IF EXISTS `ukint_project_timelines_reports`;
/*!50001 DROP VIEW IF EXISTS `ukint_project_timelines_reports`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `ukint_project_timelines_reports` (
  `Country` tinyint NOT NULL,
  `Ticket` tinyint NOT NULL,
  `Start` tinyint NOT NULL,
  `Phase` tinyint NOT NULL,
  `End` tinyint NOT NULL,
  `Days` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `ukint_project_amount_launches`
--

/*!50001 DROP TABLE IF EXISTS `ukint_project_amount_launches`*/;
/*!50001 DROP VIEW IF EXISTS `ukint_project_amount_launches`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_amount_launches` AS select year(`pm_project_event`.`date`) AS `Year`,`pm_project_event`.`country` AS `Country`,`pm_project_event`.`project_type` AS `Project type`,count(0) AS `Total launched` from `pm_project_event` where ((year(`pm_project_event`.`date`) = _utf8'2013') and (`pm_project_event`.`type` = _utf8'launch')) group by `pm_project_event`.`country`,`pm_project_event`.`project_type` order by `pm_project_event`.`country`,`pm_project_event`.`project_type` */;

--
-- Final view structure for view `ukint_project_amount_launches_by_month`
--

/*!50001 DROP TABLE IF EXISTS `ukint_project_amount_launches_by_month`*/;
/*!50001 DROP VIEW IF EXISTS `ukint_project_amount_launches_by_month`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_amount_launches_by_month` AS select date_format(`pm_project_event`.`date`,_utf8'%Y-%m') AS `Month`,`pm_project_event`.`country` AS `Country`,`pm_project_event`.`project_type` AS `Project type`,count(0) AS `Total launched` from `pm_project_event` where ((year(`pm_project_event`.`date`) = _utf8'2013') and (`pm_project_event`.`type` = _utf8'launch')) group by date_format(`pm_project_event`.`date`,_utf8'%Y-%m'),`pm_project_event`.`country`,`pm_project_event`.`project_type` order by date_format(`pm_project_event`.`date`,_utf8'%Y-%m'),`pm_project_event`.`country`,`pm_project_event`.`project_type` */;

--
-- Final view structure for view `ukint_project_delay_reports`
--

/*!50001 DROP TABLE IF EXISTS `ukint_project_delay_reports`*/;
/*!50001 DROP VIEW IF EXISTS `ukint_project_delay_reports`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_delay_reports` AS select `a`.`country` AS `Country`,ifnull((select date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%b/%Y') AS `date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%b/%Y')`),_utf8'n/a') AS `Month`,`a`.`project_id` AS `Ticket`,`a`.`project_type` AS `Project type`,if(isnull((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch')))),_utf8'no',_utf8'yes') AS `Launched`,ifnull((select cast(date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%d/%m/%Y'), char(20))`),_utf8'n/a') AS `Real launch date`,ifnull((select cast(date_format(substr(`b`.`timelines`,(locate(_utf8'Launch=',`b`.`timelines`) + 7)),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format(SUBSTRING(timelines, LOCATE('Launch=', timelines) + 7), '%d/%m/%Y'), char(20))` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'baseline'))),_utf8'n/a') AS `Planned launch date`,cast(concat(sum(`a`.`impact`),_utf8' days') as char(200) charset utf8) AS `Accumulated launch delay` from `pm_project_event` `a` where (`a`.`type` = _utf8'delay') group by `a`.`project_id` order by `a`.`country`,`a`.`project_type`,`a`.`project_id` */;

--
-- Final view structure for view `ukint_project_detailed_delay_reports`
--

/*!50001 DROP TABLE IF EXISTS `ukint_project_detailed_delay_reports`*/;
/*!50001 DROP VIEW IF EXISTS `ukint_project_detailed_delay_reports`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_detailed_delay_reports` AS select `a`.`country` AS `Country`,ifnull((select date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%b/%Y') AS `date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%b/%Y')`),_utf8'n/a') AS `Month`,`a`.`project_id` AS `Ticket`,`a`.`project_type` AS `Project type`,if(isnull((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch')))),_utf8'no',_utf8'yes') AS `Launched`,ifnull((select cast(date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%d/%m/%Y'), char(20))`),_utf8'n/a') AS `Real launch date`,ifnull((select cast(date_format(substr(`b`.`timelines`,(locate(_utf8'Launch=',`b`.`timelines`) + 7)),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format(SUBSTRING(timelines, LOCATE('Launch=', timelines) + 7), '%d/%m/%Y'), char(20))` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'baseline'))),_utf8'n/a') AS `Planned launch date`,(select sum(`b`.`impact`) AS `SUM(impact)` from `pm_project_event` `b` where (`b`.`project_id` = `a`.`project_id`) group by `b`.`project_id`) AS `Total launch delay`,cast(date_format(`a`.`date`,_utf8'%d/%m/%Y') as char(20) charset utf8) AS `Date delay`,cast(concat(`a`.`impact`,_utf8' days') as char(200) charset utf8) AS `Amount delay`,ifnull(`a`.`reason`,_utf8'n/a') AS `Delay reason` from `pm_project_event` `a` where (`a`.`type` = _utf8'delay') order by `a`.`country`,`a`.`project_type`,`a`.`project_id`,cast(date_format(`a`.`date`,_utf8'%d/%m/%Y') as char(20) charset utf8) */;

--
-- Final view structure for view `ukint_project_timelines_reports`
--

/*!50001 DROP TABLE IF EXISTS `ukint_project_timelines_reports`*/;
/*!50001 DROP VIEW IF EXISTS `ukint_project_timelines_reports`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_timelines_reports` AS select `a`.`country` AS `Country`,`a`.`project_id` AS `Ticket`,`a`.`date` AS `Start`,`a`.`event` AS `Phase`,date_format((select vfget(`pm_project_event`.`timelines` AS `timelines`,`a`.`event` AS `a.event`) AS `vfget(timelines, a.event)` from `pm_project_event` where ((`pm_project_event`.`project_id` = `a`.`project_id`) and (`pm_project_event`.`timelines` <> _utf8'')) order by `pm_project_event`.`date` desc,`pm_project_event`.`event_id` desc limit 1),_utf8'%d/%m/%Y') AS `End`,(to_days((select vfget(`pm_project_event`.`timelines` AS `timelines`,`a`.`event` AS `a.event`) AS `vfget(timelines, a.event)` from `pm_project_event` where ((`pm_project_event`.`project_id` = `a`.`project_id`) and (`pm_project_event`.`timelines` <> _utf8'')) order by `pm_project_event`.`date` desc,`pm_project_event`.`event_id` desc limit 1)) - to_days(`a`.`date`)) AS `Days` from `pm_project_event` `a` where (`a`.`type` in (_utf8'start',_utf8'launch')) order by `a`.`country`,`a`.`project_id`,`a`.`date`,`a`.`event_id` */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-08 23:44:45
