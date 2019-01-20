drop function czy_zarejestrowany_email;
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
