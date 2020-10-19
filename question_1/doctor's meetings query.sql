SET GLOBAL time_zone = '+2:00';

SELECT doctor_id ,queue.p_f_name, p_l_name, queue.time 
FROM queue join patients join queue_reserved
WHERE queue_reserved.patient_id = patients.patient_id and queue.p_f_name = patients.p_f_name and doctor_id = 1002 -- or any other number
ORDER BY queue.time