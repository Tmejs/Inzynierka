drop view `pjkursdb`.`kursy_v`;
create
VIEW `pjkursdb`.`kursy_v` AS
    SELECT 
        `cs`.`id` AS `id`,
        `cs`.`name` AS `name`,
        `cs`.`description` AS `description`,
        `cs`.`statusId` AS `statusId`,
        `cs`.`minimumParticipants` AS `minimumParticipants`,
        (SELECT 
                COUNT(0)
            FROM
                `pjkursdb`.`appusers_courses`
            WHERE
                (`pjkursdb`.`appusers_courses`.`course_id` = `cs`.`id`)) AS `paricipants`
    FROM
        (`pjkursdb`.`courses` `cs`
        LEFT JOIN `pjkursdb`.`courses_statuses` `cst` ON ((`cs`.`statusId` = `cst`.`id`)))