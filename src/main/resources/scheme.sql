Starting...
Reverse Engineering....
Reverse engineering ukint_management from def
- Preparing...
- Preparing...
Reverse engineering tables from ukint_management
- Retrieving table ukint_management.pm_project_event...
- Reverse engineering ukint_management.pm_project_event...
- Parsing MySQL SQL Script...
- Processing object: pm_project_event
- Creating foreign key references...
- Finished parsing MySQL SQL script.
- Retrieving table ukint_management.pm_project_id_alias...
- Reverse engineering ukint_management.pm_project_id_alias...
- Parsing MySQL SQL Script...
- Processing object: pm_project_id_alias
- Creating foreign key references...
- Finished parsing MySQL SQL script.
- Retrieving table ukint_management.ukint_project_amount_launches...
- Reverse engineering ukint_management.ukint_project_amount_launches...
- Parsing MySQL SQL Script...
- Processing object: ukint_project_amount_launches
- Creating foreign key references...
- Finished parsing MySQL SQL script.
- Retrieving table ukint_management.ukint_project_amount_launches_by_month...
- Reverse engineering ukint_management.ukint_project_amount_launches_by_month...
- Parsing MySQL SQL Script...
- Processing object: ukint_project_amount_launches_by_month
- Creating foreign key references...
- Finished parsing MySQL SQL script.
- Retrieving table ukint_management.ukint_project_delay_reports...
- Reverse engineering ukint_management.ukint_project_delay_reports...
- Parsing MySQL SQL Script...
- Processing object: ukint_project_delay_reports
- Creating foreign key references...
- Finished parsing MySQL SQL script.
- Retrieving table ukint_management.ukint_project_detailed_delay_reports...
- Reverse engineering ukint_management.ukint_project_detailed_delay_reports...
- Parsing MySQL SQL Script...
- Processing object: ukint_project_detailed_delay_reports
- Creating foreign key references...
- Finished parsing MySQL SQL script.
- Retrieving table ukint_management.ukint_project_timelines_reports...
- Reverse engineering ukint_management.ukint_project_timelines_reports...
- Parsing MySQL SQL Script...
- Processing object: ukint_project_timelines_reports
- Creating foreign key references...
- Finished parsing MySQL SQL script.
Reverse engineering stored procedures from ukint_management
Reverse engineering functions from ukint_management
- Reverse engineered 7 objects
Reverse Engineering finished
Migrating...
- Migrating...
- Migrating schema ukint_management...
- Migrating schema contents for schema ukint_management
- Table ukint_management.pm_project_event migrated
- Table ukint_management.pm_project_id_alias migrated
- View ukint_management.ukint_project_amount_launches migrated
- View ukint_management.ukint_project_amount_launches_by_month migrated
- View ukint_management.ukint_project_delay_reports migrated
- View ukint_management.ukint_project_detailed_delay_reports migrated
- View ukint_management.ukint_project_timelines_reports migrated
- Finalizing foreign key migration...
- Migration finished
Migrating done
Generating Code...
Generating Code done
Creating target schema....
- Creating schema in target MySQL server at Mysql@127.0.0.1:3306...
- Executing preamble script...
Execute statement: SET FOREIGN_KEY_CHECKS = 0
- Creating schema ukint_management...
Execute statement: DROP SCHEMA IF EXISTS `ukint_management` 
Execute statement: 
    
    CREATE SCHEMA IF NOT EXISTS `ukint_management` 
- Creating table ukint_management.pm_project_event
Execute statement: 
    CREATE  TABLE IF NOT EXISTS `ukint_management`.`pm_project_event` (
      `country` ENUM('at','ch','de','in','kw','ru','sg','uk','za') NULL DEFAULT 'uk' ,
      `project_type` ENUM('MIP','Delivery','Maintenance') NULL DEFAULT 'Maintenance' ,
      `project_id` VARCHAR(100) NOT NULL DEFAULT '' ,
      `date` DATE NOT NULL DEFAULT '0000-00-00' ,
      `type` ENUM('delay','launch','milestone','status','baseline','start','info') NULL DEFAULT NULL ,
      `event` VARCHAR(100) NULL DEFAULT '' ,
      `impact` TINYINT(4) NULL DEFAULT '0' ,
      `reason` TEXT NULL DEFAULT NULL ,
      `timelines` VARCHAR(256) NULL DEFAULT '' ,
      `autotimestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP ,
      `event_id` INT(11) NOT NULL AUTO_INCREMENT ,
      PRIMARY KEY (`event_id`) )
    ENGINE = InnoDB
    AUTO_INCREMENT = 282
    DEFAULT CHARACTER SET = utf8
- Creating table ukint_management.pm_project_id_alias
Execute statement: 
    CREATE  TABLE IF NOT EXISTS `ukint_management`.`pm_project_id_alias` (
      `project_id` VARCHAR(100) NOT NULL DEFAULT '' ,
      `name` TEXT NULL DEFAULT NULL ,
      PRIMARY KEY (`project_id`) )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
- Creating view ukint_management.ukint_project_amount_launches
Execute statement: USE `ukint_management`
Execute statement: 
    
    CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_amount_launches` AS select year(`pm_project_event`.`date`) AS `Year`,`pm_project_event`.`country` AS `Country`,`pm_project_event`.`project_type` AS `Project type`,count(0) AS `Total launched` from `pm_project_event` where ((year(`pm_project_event`.`date`) = _utf8'2013') and (`pm_project_event`.`type` = _utf8'launch')) group by `pm_project_event`.`country`,`pm_project_event`.`project_type` order by `pm_project_event`.`country`,`pm_project_event`.`project_type`
- Creating view ukint_management.ukint_project_amount_launches_by_month
Execute statement: USE `ukint_management`
Execute statement: 
    
    CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_amount_launches_by_month` AS select date_format(`pm_project_event`.`date`,_utf8'%Y-%m') AS `Month`,`pm_project_event`.`country` AS `Country`,`pm_project_event`.`project_type` AS `Project type`,count(0) AS `Total launched` from `pm_project_event` where ((year(`pm_project_event`.`date`) = _utf8'2013') and (`pm_project_event`.`type` = _utf8'launch')) group by date_format(`pm_project_event`.`date`,_utf8'%Y-%m'),`pm_project_event`.`country`,`pm_project_event`.`project_type` order by date_format(`pm_project_event`.`date`,_utf8'%Y-%m'),`pm_project_event`.`country`,`pm_project_event`.`project_type`
- Creating view ukint_management.ukint_project_delay_reports
Execute statement: USE `ukint_management`
Execute statement: 
    
    CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_delay_reports` AS select `a`.`country` AS `Country`,ifnull((select date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%b/%Y') AS `date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%b/%Y')`),_utf8'n/a') AS `Month`,`a`.`project_id` AS `Ticket`,`a`.`project_type` AS `Project type`,if(isnull((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch')))),_utf8'no',_utf8'yes') AS `Launched`,ifnull((select cast(date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%d/%m/%Y'), char(20))`),_utf8'n/a') AS `Real launch date`,ifnull((select cast(date_format(substr(`b`.`timelines`,(locate(_utf8'Launch=',`b`.`timelines`) + 7)),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format(SUBSTRING(timelines, LOCATE('Launch=', timelines) + 7), '%d/%m/%Y'), char(20))` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'baseline'))),_utf8'n/a') AS `Planned launch date`,cast(concat(sum(`a`.`impact`),_utf8' days') as char(200) charset utf8) AS `Accumulated launch delay` from `pm_project_event` `a` where (`a`.`type` = _utf8'delay') group by `a`.`project_id` order by `a`.`country`,`a`.`project_type`,`a`.`project_id`
WARNING: Error executing '
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_delay_reports` AS select `a`.`country` AS `Country`,ifnull((select date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%b/%Y') AS `date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%b/%Y')`),_utf8'n/a') AS `Month`,`a`.`project_id` AS `Ticket`,`a`.`project_type` AS `Project type`,if(isnull((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch')))),_utf8'no',_utf8'yes') AS `Launched`,ifnull((select cast(date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%d/%m/%Y'), char(20))`),_utf8'n/a') AS `Real launch date`,ifnull((select cast(date_format(substr(`b`.`timelines`,(locate(_utf8'Launch=',`b`.`timelines`) + 7)),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format(SUBSTRING(timelines, LOCATE('Launch=', timelines) + 7), '%d/%m/%Y'), char(20))` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'baseline'))),_utf8'n/a') AS `Planned launch date`,cast(concat(sum(`a`.`impact`),_utf8' days') as char(200) charset utf8) AS `Accumulated launch delay` from `pm_project_event` `a` where (`a`.`type` = _utf8'delay') group by `a`.`project_id` order by `a`.`country`,`a`.`project_type`,`a`.`project_id`'
Incorrect column name 'date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launc'.
SQL Error: 1166
- Creating view ukint_management.ukint_project_detailed_delay_reports
Execute statement: USE `ukint_management`
Execute statement: 
    
    CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_detailed_delay_reports` AS select `a`.`country` AS `Country`,ifnull((select date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%b/%Y') AS `date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%b/%Y')`),_utf8'n/a') AS `Month`,`a`.`project_id` AS `Ticket`,`a`.`project_type` AS `Project type`,if(isnull((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch')))),_utf8'no',_utf8'yes') AS `Launched`,ifnull((select cast(date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%d/%m/%Y'), char(20))`),_utf8'n/a') AS `Real launch date`,ifnull((select cast(date_format(substr(`b`.`timelines`,(locate(_utf8'Launch=',`b`.`timelines`) + 7)),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format(SUBSTRING(timelines, LOCATE('Launch=', timelines) + 7), '%d/%m/%Y'), char(20))` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'baseline'))),_utf8'n/a') AS `Planned launch date`,(select sum(`b`.`impact`) AS `SUM(impact)` from `pm_project_event` `b` where (`b`.`project_id` = `a`.`project_id`) group by `b`.`project_id`) AS `Total launch delay`,cast(date_format(`a`.`date`,_utf8'%d/%m/%Y') as char(20) charset utf8) AS `Date delay`,cast(concat(`a`.`impact`,_utf8' days') as char(200) charset utf8) AS `Amount delay`,ifnull(`a`.`reason`,_utf8'n/a') AS `Delay reason` from `pm_project_event` `a` where (`a`.`type` = _utf8'delay') order by `a`.`country`,`a`.`project_type`,`a`.`project_id`,cast(date_format(`a`.`date`,_utf8'%d/%m/%Y') as char(20) charset utf8)
WARNING: Error executing '
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_detailed_delay_reports` AS select `a`.`country` AS `Country`,ifnull((select date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%b/%Y') AS `date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%b/%Y')`),_utf8'n/a') AS `Month`,`a`.`project_id` AS `Ticket`,`a`.`project_type` AS `Project type`,if(isnull((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch')))),_utf8'no',_utf8'yes') AS `Launched`,ifnull((select cast(date_format((select `b`.`date` AS `date` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'launch'))),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launch'), '%d/%m/%Y'), char(20))`),_utf8'n/a') AS `Real launch date`,ifnull((select cast(date_format(substr(`b`.`timelines`,(locate(_utf8'Launch=',`b`.`timelines`) + 7)),_utf8'%d/%m/%Y') as char(20) charset utf8) AS `convert(date_format(SUBSTRING(timelines, LOCATE('Launch=', timelines) + 7), '%d/%m/%Y'), char(20))` from `pm_project_event` `b` where ((`a`.`project_id` = `b`.`project_id`) and (`b`.`type` = _utf8'baseline'))),_utf8'n/a') AS `Planned launch date`,(select sum(`b`.`impact`) AS `SUM(impact)` from `pm_project_event` `b` where (`b`.`project_id` = `a`.`project_id`) group by `b`.`project_id`) AS `Total launch delay`,cast(date_format(`a`.`date`,_utf8'%d/%m/%Y') as char(20) charset utf8) AS `Date delay`,cast(concat(`a`.`impact`,_utf8' days') as char(200) charset utf8) AS `Amount delay`,ifnull(`a`.`reason`,_utf8'n/a') AS `Delay reason` from `pm_project_event` `a` where (`a`.`type` = _utf8'delay') order by `a`.`country`,`a`.`project_type`,`a`.`project_id`,cast(date_format(`a`.`date`,_utf8'%d/%m/%Y') as char(20) charset utf8)'
Incorrect column name 'date_format((SELECT date FROM pm_project_event b WHERE a.project_id = b.project_id AND type = 'launc'.
SQL Error: 1166
- Creating view ukint_management.ukint_project_timelines_reports
Execute statement: USE `ukint_management`
Execute statement: 
    
    CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_timelines_reports` AS select `a`.`country` AS `Country`,`a`.`project_id` AS `Ticket`,`a`.`date` AS `Start`,`a`.`event` AS `Phase`,date_format((select vfget(`pm_project_event`.`timelines` AS `timelines`,`a`.`event` AS `a.event`) AS `vfget(timelines, a.event)` from `pm_project_event` where ((`pm_project_event`.`project_id` = `a`.`project_id`) and (`pm_project_event`.`timelines` <> _utf8'')) order by `pm_project_event`.`date` desc,`pm_project_event`.`event_id` desc limit 1),_utf8'%d/%m/%Y') AS `End`,(to_days((select vfget(`pm_project_event`.`timelines` AS `timelines`,`a`.`event` AS `a.event`) AS `vfget(timelines, a.event)` from `pm_project_event` where ((`pm_project_event`.`project_id` = `a`.`project_id`) and (`pm_project_event`.`timelines` <> _utf8'')) order by `pm_project_event`.`date` desc,`pm_project_event`.`event_id` desc limit 1)) - to_days(`a`.`date`)) AS `Days` from `pm_project_event` `a` where (`a`.`type` in (_utf8'start',_utf8'launch')) order by `a`.`country`,`a`.`project_id`,`a`.`date`,`a`.`event_id`
WARNING: Error executing '
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`tech`@`%` SQL SECURITY DEFINER VIEW `ukint_project_timelines_reports` AS select `a`.`country` AS `Country`,`a`.`project_id` AS `Ticket`,`a`.`date` AS `Start`,`a`.`event` AS `Phase`,date_format((select vfget(`pm_project_event`.`timelines` AS `timelines`,`a`.`event` AS `a.event`) AS `vfget(timelines, a.event)` from `pm_project_event` where ((`pm_project_event`.`project_id` = `a`.`project_id`) and (`pm_project_event`.`timelines` <> _utf8'')) order by `pm_project_event`.`date` desc,`pm_project_event`.`event_id` desc limit 1),_utf8'%d/%m/%Y') AS `End`,(to_days((select vfget(`pm_project_event`.`timelines` AS `timelines`,`a`.`event` AS `a.event`) AS `vfget(timelines, a.event)` from `pm_project_event` where ((`pm_project_event`.`project_id` = `a`.`project_id`) and (`pm_project_event`.`timelines` <> _utf8'')) order by `pm_project_event`.`date` desc,`pm_project_event`.`event_id` desc limit 1)) - to_days(`a`.`date`)) AS `Days` from `pm_project_event` `a` where (`a`.`type` in (_utf8'start',_utf8'launch')) order by `a`.`country`,`a`.`project_id`,`a`.`date`,`a`.`event_id`'
Incorrect parameters in the call to stored function 'vfget'.
SQL Error: 1584
Scripts for 2 tables, 2 views and 0 routines were executed for schema ukint_management
- Executing postamble script...
Execute statement: SET FOREIGN_KEY_CHECKS = 1
- Schema created
Creating target schema finished
Selecting tables to copy...
Selecting tables to copy done
Counting table rows to copy....
/usr/lib/mysql-workbench/bin/wbcopytables --count-only --passwords-from-stdin --mysql-source=tech@192.168.122.126:3306 --table `ukint_management` `pm_project_id_alias` --table `ukint_management` `pm_project_event`
Connecting to MySQL server at 192.168.122.126:3306 with user tech
01:03:31 [INF][      copytable]: Connection to MySQL opened

298 total rows in 2 tables need to be copied:
Counting table rows to copy finished
Copying table data....
- Data copy starting

Migrating data...
/usr/lib/mysql-workbench/bin/wbcopytables --mysql-source=tech@192.168.122.126:3306 --target=root@127.0.0.1:3306 --progress --passwords-from-stdin --thread-count=2 --table `ukint_management` `pm_project_id_alias` `ukint_management` `pm_project_id_alias` `project_id`, `name` --table `ukint_management` `pm_project_event` `ukint_management` `pm_project_event` `country`, `project_type`, `project_id`, `date`, `type`, `event`, `impact`, `reason`, `timelines`, `autotimestamp`, `event_id`
3306 with user root
03:31 [INF][      copytable]: Connection to MySQL opened
03:31 [INF][      copytable]: Connecting to MySQL server at 192.168.122.126:3306 with user tech
03:31 [INF][      copytable]: Connection to MySQL opened
03:31 [INF][      copytable]: Connecting to MySQL server at 127.0.0.1:3306 with user root
03:31 [INF][      copytable]: Connection to MySQL opened
03:31 [INF][      copytable]: Connecting to MySQL server at 192.168.122.126:3306 with user tech
03:32 [INF][      copytable]: Connection to MySQL opened
03:32 [INF][      copytable]: Connecting to MySQL server at 127.0.0.1:3306 with user root
03:32 [INF][      copytable]: Connection to MySQL opened
`ukint_management`.`pm_project_id_alias`:Copying 2 columns of 37 rows from table `ukint_management`.`pm_project_id_alias`
`ukint_management`.`pm_project_id_alias`:Finished copying 37 rows in 0m01s
`ukint_management`.`pm_project_event`:Copying 11 columns of 261 rows from table `ukint_management`.`pm_project_event`
`ukint_management`.`pm_project_event`:Finished copying 261 rows in 0m00s
03:32 [INF][      copytable]: Re-enabling triggers for schema 'ukint_management'
03:32 [INF][      copytable]: No triggers found for 'ukint_management'



Copy helper has finished

Data copy results:
- `ukint_management`.`pm_project_id_alias` has succeeded (37 of 37 rows copied)
- `ukint_management`.`pm_project_event` has succeeded (261 of 261 rows copied)
2 tables of 2 were fully copied
Copying table data finished
Tasks finished with warnings and/or errors, view the logs for details
Finished performing tasks.
