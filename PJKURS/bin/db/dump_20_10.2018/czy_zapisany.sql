drop function czy_zapisany;
DELIMITER $$
CREATE FUNCTION `czy_zapisany`(P_EMAIL VARCHAR(40), P_COURSE_ID INT) RETURNS tinyint(1)
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
