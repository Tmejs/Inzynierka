drop view `pjkursdb`.`moje_kursy_v`;
create
VIEW `pjkursdb`.`moje_kursy_v` AS
    SELECT 
        `cs`.`id` AS `id`,
        `cs`.`name` AS `name`,
        `cs`.`description` AS `description`,
        IF(`uc`.`isPaid`, 'true', 'false') AS `isPaid`,
        `apu`.`email` AS `username`,
        (SELECT 
                COUNT(0)
            FROM
                `pjkursdb`.`appusers_courses`
            WHERE
                (`pjkursdb`.`appusers_courses`.`course_id` = `cs`.`id`)) AS `paricipants`
    FROM
        ((`pjkursdb`.`courses` `cs`
        LEFT JOIN `pjkursdb`.`appusers_courses` `uc` ON ((`cs`.`id` = `uc`.`course_id`)))
        LEFT JOIN `pjkursdb`.`appusers` `apu` ON ((`apu`.`id` = `uc`.`user_id`)))