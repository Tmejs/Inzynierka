drop function zapisz_do_kursu;
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
