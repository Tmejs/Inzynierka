drop function zaloguj_usera;
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
