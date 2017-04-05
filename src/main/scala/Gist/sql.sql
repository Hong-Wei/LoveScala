-- 1 login stuff
-- sudo -iu postgres

-- 2 open sql 
-- psql

-- 3 
\c socgen2_obp_api_kafka_sandbox;

-- 4
SELECT * FROM mappedbadloginattempt
ORDER BY id
LIMIT 10 ;

-- 5
UPDATE "mappedbadloginattempt"
SET "mbadattemptssincelastsuccessorreset"=0;

-- 6 —>will list you available databases
\l
-- 7 —> to connect to that database
\c socgen_obp_api_kafka_sandbox7

-- 8 tall tabales
\dt 