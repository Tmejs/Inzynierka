drop view `pjkursdb`.`course_sub_categorries`;
create
VIEW `pjkursdb`.`course_sub_categorries` AS
    SELECT 
        `sc`.`ID` AS `ID`,
        `sc`.`name` AS `name`,
        `sc`.`description` AS `description`,
        `sc`.`category_id` AS `category_id`,
        `csc`.`course_id` AS `course_id`
    FROM
        (`pjkursdb`.`sub_categorries` `sc`
        LEFT JOIN `pjkursdb`.`courses_sub_categories` `csc` ON ((`sc`.`ID` = `csc`.`sub_category_id`)))