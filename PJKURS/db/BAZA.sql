use pjkursdb;
CREATE TABLE `appusers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `contact_number` varchar(45) DEFAULT NULL,
  `place_of_birth` varchar(45) DEFAULT NULL,
  `isActive` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `appusers_courses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `appusers_trainigs` (
  `appuser_id` int(11) NOT NULL,
  `training_id` int(11) NOT NULL,
  `is_paid` tinyint(4) DEFAULT NULL,
  `is_contract_signed` tinyint(4) DEFAULT NULL,
  `finalPrice` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `categorries` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `courses_statuses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `isVisibleForUsers` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `courses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` longtext,
  `statusId` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `price` double DEFAULT NULL,
  `description_file` varchar(45) DEFAULT NULL,
  `discountPrice` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_status_idx` (`statusId`),
  CONSTRAINT `fk_status` FOREIGN KEY (`statusId`) REFERENCES `courses_statuses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `courses_categories` (
  `course_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `courses_graduates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `appuser_id` int(11) NOT NULL,
  `createDate` date NOT NULL,
  `certificateFile` varchar(45) DEFAULT NULL,
  `training_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `deanery_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `admin_grant` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `discounts` (
  `appusers_id` int(11) NOT NULL,
  `isConfirmed` tinyint(4) DEFAULT NULL,
  `userDescription` varchar(200) DEFAULT NULL,
  `grantedDescription` varchar(200) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trening_id` int(11) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `is_percentValue` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `teachers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appUserId` int(11) NOT NULL,
  `typeOfWork` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `teachers_trainings` (
  `training_id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `numberOfHours` int(11) DEFAULT NULL,
  PRIMARY KEY (`teacher_id`,`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `training_statuses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `training_terms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `training_id` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `trainings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` date DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `trainings_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `training_id` int(11) DEFAULT NULL,
  `path` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `visibleForUsers` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into pjkursdb.courses_statuses(name, description)
values ('Archiwalny', 'Oznaczenie dla zakończonych kursów');

insert into pjkursdb.courses_statuses(name, description)
values ('Otwarty', 'Kurs oczekujący na zapisanie się członków');

insert into pjkursdb.training_statuses(name, description)
values('Trwające', "Trwające szkolenie");

insert into pjkursdb.training_statuses(name, description)
values('Zakończone', "Zakończone szkolenie");

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `pjkursdb`.`dostepne_kursy_v` AS select `cs`.`id` AS `id`,`cs`.`name` AS `name`,`cs`.`description` AS `description`,`cs`.`statusId` AS `statusId`,`cs`.`price` AS `price`,(select count(0) from `pjkursdb`.`appusers_courses` where (`pjkursdb`.`appusers_courses`.`course_id` = `cs`.`id`)) AS `paricipants` from (`pjkursdb`.`courses` `cs` left join `pjkursdb`.`courses_statuses` `cst` on((`cs`.`statusId` = `cst`.`id`))) where (`cs`.`statusId` <> 1);

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `pjkursdb`.`kursy_arch_v` AS select `cs`.`id` AS `id`,`cs`.`name` AS `name`,`cs`.`description` AS `description`,`cst`.`id` AS `statusId`,`cst`.`name` AS `statusName` from (`pjkursdb`.`courses` `cs` left join `pjkursdb`.`courses_statuses` `cst` on((`cst`.`id` = `cs`.`statusId`))) where (`cs`.`statusId` = 1);

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `pjkursdb`.`kursy_v` AS select `cs`.`id` AS `id`,`cs`.`name` AS `name`,`cs`.`description` AS `description`,`cs`.`statusId` AS `statusId`,`cs`.`price` AS `price`,`cs`.`description_file` AS `description_file`,`cs`.`discountPrice` AS `discountPrice`,(select count(0) from `pjkursdb`.`appusers_courses` where (`pjkursdb`.`appusers_courses`.`course_id` = `cs`.`id`)) AS `paricipants` from (`pjkursdb`.`courses` `cs` left join `pjkursdb`.`courses_statuses` `cst` on((`cs`.`statusId` = `cst`.`id`)));

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `pjkursdb`.`moje_kursy_v` AS select `cs`.`id` AS `id`,`cs`.`name` AS `name`,`cs`.`description` AS `description`,`uc`.`discount` AS `discount`,`apu`.`email` AS `username`,(select count(0) from `pjkursdb`.`appusers_courses` where (`pjkursdb`.`appusers_courses`.`course_id` = `cs`.`id`)) AS `paricipants` from ((`pjkursdb`.`courses` `cs` left join `pjkursdb`.`appusers_courses` `uc` on((`cs`.`id` = `uc`.`course_id`))) left join `pjkursdb`.`appusers` `apu` on((`apu`.`id` = `uc`.`user_id`)));

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `pjkursdb`.`teachers_trainings_v` AS select `a`.`id` AS `id`,`a`.`email` AS `email`,`a`.`password` AS `password`,`a`.`create_date` AS `create_date`,`a`.`name` AS `name`,`a`.`surname` AS `surname`,`a`.`birth_date` AS `birth_date`,`a`.`contact_number` AS `contact_number`,`ttr`.`teacher_id` AS `teacher_id`,`ttr`.`training_id` AS `training_id`,`tt`.`typeOfWork` AS `typeOfWork` from ((`pjkursdb`.`teachers` `tt` left join `pjkursdb`.`appusers` `a` on((`tt`.`appUserId` = `a`.`id`))) left join `pjkursdb`.`teachers_trainings` `ttr` on((`ttr`.`teacher_id` = `tt`.`id`)));

DELIMITER $$
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
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `czy_zarejestrowany_email`(P_EMAIL VARCHAR(40)) RETURNS tinyint(1)
BEGIN
    DECLARE V_EXIST_COUNT INTEGER;
    
    SELECT COUNT(*) INTO V_EXIST_COUNT
		FROM appusers
        WHERE EMAIL=P_EMAIL;
    
    RETURN V_EXIST_COUNT>0;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `new_training_from_course`(P_COURSE_ID INT) RETURNS tinyint(1)
BEGIN
   DECLARE v_startDate DATE;
	declare v_exist_count integer;
    declare v_training_id int;
    declare v_final_price int;
    declare v_course_price int;
    select NOW() into v_startDate;
    
    select price into v_course_price 
    from pjkursdb.courses where id = P_COURSE_ID;
    
   INSERT INTO `pjkursdb`.`trainings`
			(
			`start_date`,
			`course_id`,
            status_id)
	VALUES
		(v_startDate, p_COURSE_ID,1);
    
         
    SELECT LAST_INSERT_ID() into v_training_id;
    insert into pjkursdb.appusers_trainigs( appuser_id, training_id, finalPrice) 
		select ac.user_id, v_training_id, (v_course_price - COALESCE(ac.discount,0))
        from pjkursdb.appusers_courses ac 
        where ac.course_id = P_COURSE_ID;
        
	delete from pjkursdb.appusers_courses 
		where course_id = P_COURSE_ID;
RETURN 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `zaloguj_usera`(P_EMAIL VARCHAR(40), P_HASLO VARCHAR(40)) RETURNS tinyint(1)
BEGIN
    DECLARE V_EXIST_COUNT INTEGER;
    
    SELECT COUNT(*) INTO V_EXIST_COUNT
		FROM appusers a
        where a.email=p_email
        and a.password = P_HASLO;
        
    RETURN V_EXIST_COUNT>0;
END$$
DELIMITER ;

DELIMITER $$
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
END$$
DELIMITER ;
