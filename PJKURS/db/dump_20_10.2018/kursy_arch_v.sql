drop view `pjkursdb`.`kursy_arch_v`;
create
VIEW `pjkursdb`.`kursy_arch_v` AS
    SELECT 
        `cs`.`id` AS `id`,
        `cs`.`name` AS `name`,
        `cs`.`description` AS `description`,
        `cst`.`id` AS `statusId`,
        `cst`.`name` AS `statusName`
    FROM
        (`pjkursdb`.`courses` `cs`
        LEFT JOIN `pjkursdb`.`courses_statuses` `cst` ON ((`cst`.`id` = `cs`.`statusId`)))
    WHERE
        (`cs`.`statusId` = 1)